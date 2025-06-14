package pl.put.poznan.tools.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextComparerTest {
    @Test
    void checkIdenticalText() {
        String string1 = "This is a test\n" +
                "It shouldn't find any differences\n" +
                "If it finds something, that's bad";
        TextComparer comparer = new TextComparer(string1, string1);
        String result = comparer.compare();
        assertEquals("", result);
    }

    @Test
    void checkDifferences() {
        String string1 = "This is a test\n" +
                "It should find some differences\n" +
                "If it doesn't find anything, that's bad";
        String string2 = "This is a test\n" +
                "It shoudl find some differences\n" +
                "If it doesn't find anything, that's bad";
        TextComparer comparer = new TextComparer(string1, string2);
        String result = comparer.compare();
        String expectedResult = "Difference in line 2:\n" +
                "  File 1: It should find some differences\n" +
                "  File 2: It shoudl find some differences";
        assertEquals(expectedResult, result);
    }

    @Test
    void checkMultipleDifferences() {
        String string1 = "This is a test\n" +
                "It should find multiple differences\n" +
                "If it doesn't find anything, that's bad\n" +
                "If it finds only one difference that's also bad";
        String string2 = "This test\n" +
                "It should find many differences\n" +
                "If it doesn't find anything, that's bad\n" +
                "If it finds only 1 difference that's also bad";
        TextComparer comparer = new TextComparer(string1, string2);
        String result = comparer.compare();
        String expectedResult = "Difference in line 1:\n" +
                "  File 1: This is a test\n" +
                "  File 2: This test\n" +
                "Difference in line 2:\n" +
                "  File 1: It should find multiple differences\n" +
                "  File 2: It should find many differences\n" +
                "Difference in line 4:\n" +
                "  File 1: If it finds only one difference that's also bad\n" +
                "  File 2: If it finds only 1 difference that's also bad";
        assertEquals(expectedResult, result);
    }

    @Test
    void checkDifferentLengths() {
        String string1 = "This is a test";
        String string2 = "This is a test\n" +
                "This text has additional lines\n" +
                "They should be noticed by the comparer";
        TextComparer comparer = new TextComparer(string1, string2);
        String result = comparer.compare();
        String expectedResult = "Difference in line 2:\n" +
                "  File 1: \n" +
                "  File 2: This text has additional lines\n" +
                "Difference in line 3:\n" +
                "  File 1: \n" +
                "  File 2: They should be noticed by the comparer";
        assertEquals(expectedResult, result);
    }

    @Test
    void testEmptyStrings() {
        String string1 = "";
        TextComparer comparer = new TextComparer(string1, string1);
        String result = comparer.compare();
        assertEquals("", result);
    }
}