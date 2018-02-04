package ca.iaah.recrutement.spreadsheet;

import static com.google.common.truth.Truth.ASSERT;

import org.junit.Test;

public class SpreadsheetPart2Test {

    private final Sheet sheet = new Sheet();

    @Test
    public void testSupportsConstantFormula() {
        sheet.put("A1", "=7");
        ASSERT.that(sheet.getLiteral("A1")).isEqualTo("=7");
        ASSERT.that(sheet.get("A1")).isEqualTo("7");
    }

    @Test
    public void testFormulasSupportMultiplications() {
        sheet.put("A1", "=2*3*4");
        ASSERT.that(sheet.get("A1")).isEqualTo("24");
    }

    @Test
    public void testSupportFormulasMixingOperations() {
        sheet.put("A1", "=24/8*2");
        ASSERT.that(sheet.get("A1")).isEqualTo("6");
    }

    @Test
    public void testSupportAdditionsInFormulas() {
        sheet.put("A1", "=3+4+2");
        ASSERT.that(sheet.get("A1")).isEqualTo("9");
    }

    @Test
    public void testFormulasSupportDivisions() {
        sheet.put("A1", "=24/3/2");
        ASSERT.that(sheet.get("A1")).isEqualTo("4");
    }

    @Test
    public void testSupportsExponentiation() {
        sheet.put("A1", "=3^2^2");
        ASSERT.that(sheet.get("A1")).isEqualTo("81");
    }

}