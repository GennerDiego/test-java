package ca.iaah.recrutement.spreadsheet;

import static com.google.common.truth.Truth.*;

import org.junit.Test;

public class SpreadsheetPart1Test {

    private static final String EMPTY_CELL = "";

    @Test
    public void testThatCellsAreEmptyByDefault() {
        Sheet sheet = new Sheet();

        ASSERT.that(sheet.get("A1")).isEqualTo(EMPTY_CELL);
        ASSERT.that(sheet.get("ZX347")).isEqualTo(EMPTY_CELL);
    }

    @Test
    public void testThatCellsAreStored() {
        Sheet sheet = new Sheet();
        String theCell = "A21";

        sheet.put(theCell, "A string");
        ASSERT.that(sheet.get(theCell)).isEqualTo("A string");

        sheet.put(theCell, "A different string");
        ASSERT.that(sheet.get(theCell)).isEqualTo("A different string");

        sheet.put(theCell, "");
        ASSERT.that(sheet.get(theCell)).isEqualTo(EMPTY_CELL);
    }

    @Test
    public void testThatManyCellsExist() {
        Sheet sheet = new Sheet();
        sheet.put("A1", "First");
        sheet.put("X27", "Second");
        sheet.put("ZX901", "Third");

        ASSERT.that(sheet.get("A1")).isEqualTo("First");
        ASSERT.that(sheet.get("X27")).isEqualTo("Second");
        ASSERT.that(sheet.get("ZX901")).isEqualTo("Third");

        sheet.put("A1", "Fourth");
        ASSERT.that(sheet.get("A1")).isEqualTo("Fourth");
        ASSERT.that(sheet.get("X27")).isEqualTo("Second");
        ASSERT.that(sheet.get("ZX901")).isEqualTo("Third");
    }

    @Test
    public void testThatCellLiteralValuesArePreservedForEditing() {
        Sheet sheet = new Sheet();
        String theCell = "A21";

        sheet.put(theCell, "Some string");
        ASSERT.that(sheet.getLiteral(theCell)).isEqualTo("Some string");

        sheet.put(theCell, " 1234 ");
        ASSERT.that(sheet.getLiteral(theCell)).isEqualTo(" 1234 ");

        sheet.put(theCell, "=7"); // Foreshadowing formulas:)
        ASSERT.that(sheet.getLiteral(theCell)).isEqualTo("=7");
    }

    @Test
    public void testThatNumericCellsAreRecognized() {
        Sheet sheet = new Sheet();
        String theCell = "A21";

        sheet.put(theCell, "X99"); // "Obvious" string
        ASSERT.that(sheet.get(theCell)).isEqualTo("X99");

        sheet.put(theCell, "14"); // "Obvious" number
        ASSERT.that(sheet.get(theCell)).isEqualTo("14");

        sheet.put(theCell, " 99 X"); // Whole string must be numeric
        ASSERT.that(sheet.get(theCell)).isEqualTo(" 99 X");

        sheet.put(theCell, " 1234 "); // Blanks ignored
        ASSERT.that(sheet.get(theCell)).isEqualTo("1234");

        sheet.put(theCell, " "); // Just a blank
        ASSERT.that(sheet.get(theCell)).isEqualTo(" ");
    }

}