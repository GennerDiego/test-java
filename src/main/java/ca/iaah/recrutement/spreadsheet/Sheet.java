package ca.iaah.recrutement.spreadsheet;

import java.util.HashMap;
import java.util.Map;

public class Sheet {

    private static final String EMPTY_STRING = "";
    private Map<String, String> values = new HashMap<String, String>();
    private boolean isNotLiteral;
    StringBuilder builder;

    public String operation(String operation) {

        if (isNecessaryOperation(operation))
            return operation.substring(1);

        builder = new StringBuilder();
        operation = operation.substring(1);
        String[] result = operation.split("(?<=[-+*^/])|(?=[-+*^/])");

        int sum = 0;
        for (int i = 0; i < result.length; i++) {
            int previous = 0;
            int next = 0;
            if (!isNumeric(result[i])) {
                previous = sum == 0 ? Integer.parseInt(result[i - 1]) : sum;
                next = Integer.parseInt(result[i + 1]);
                sum = Operation.calculate(result[i], previous, next);
            }
        }

        return sum == 0 ? operation : builder.append(sum).toString();
    }

    public String get(String theCell) {
        isNotLiteral = Boolean.TRUE;
        String value = getLiteral(theCell);
        return isNumeric(value) ? Integer.valueOf(value.trim()).toString() : value;
    }

    public void put(String theCell, String value) {
        values.put(theCell, operation(value));
    }

    private boolean isNumeric(String value) {
        return value != null && value.matches("^[0-9]+$");
    }

    private boolean isBlankOrNull(String value) {
        return value == null || EMPTY_STRING.equals(value);
    }

    private boolean existMathematicalOperators(String value) {
        return value.contains("^") || value.contains("*") || value.contains("+") || value.contains("/");
    }

    private boolean isNecessaryOperation(String operation){
        return isBlankOrNull(operation) || (operation.contains("=") && !existMathematicalOperators(operation));
    }

    public String getLiteral(String theCell) {
        builder = new StringBuilder();
        builder.append(values.containsKey(theCell) ? values.get(theCell) : EMPTY_STRING);
        return isNotLiteral ? builder.toString(): builder.insert(0,"=").toString();
    }

    public enum Operation {

        DIVISION("/") {
            @Override
            public int calc(int numberOne, int numberTwo) {
                return numberOne / numberTwo;
            }
        },
        ADDITION("+") {
            @Override
            public int calc(int numberOne, int numberTwo) {
                return numberOne + numberTwo;
            }
        },
        EXPONENTIATION("^") {
            @Override
            public int calc(int numberOne, int numberTwo) {
                return (int) Math.pow(numberOne, numberTwo);
            }
        },
        MULTIPLICATION("*") {
            @Override
            public int calc(int numberOne, int numberTwo) {
                return numberOne * numberTwo;
            }
        };

        private String symbol;

        Operation(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }

        public static int calculate(String operation, int numberOne, int numberTwo) {
            for (Operation element : Operation.values()) {
                if (element.getSymbol().equals(operation))
                    return element.calc(numberOne, numberTwo);
            }
            return 0;
        }

        public abstract int calc(int numberOne, int numberTwo);

    }

}
