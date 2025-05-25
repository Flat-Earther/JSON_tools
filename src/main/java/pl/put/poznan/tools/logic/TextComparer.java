package pl.put.poznan.tools.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TextComparer {
    private final String text1;
    private final String text2;

    public TextComparer(String text1, String text2  ) {
        this.text1 = text1;
        this.text2 = text2;
    }

    public List<String> compareFiles() {
        List<String> differences = new ArrayList<>();

        List<String> lines1 = new ArrayList<>(text1.lines().toList());
        List<String> lines2 = new ArrayList<>(text2.lines().toList());

        int maxLines = Math.max(lines1.size(), lines2.size());

        // Extend shorter list with empty lines to avoid IndexOutOfBounds
        while (lines1.size() < maxLines) lines1.add("");
        while (lines2.size() < maxLines) lines2.add("");

        for (int i = 0; i < maxLines; i++) {
            String line1 = lines1.get(i);
            String line2 = lines2.get(i);

            if (!line1.equals(line2)) {
                differences.add("Difference in line " + (i + 1) + ":\n  File 1: " + line1 + "\n  File 2: " + line2);
            }
        }
        return(differences);
    }
}

