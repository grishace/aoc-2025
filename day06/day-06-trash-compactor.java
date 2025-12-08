package day06;

import java.io.*;
import java.util.*;
import java.lang.Math;

final class Day06 {

    public static void main(String[] args) {

        List<String[]> lines = new ArrayList<String[]>();
        try (BufferedReader reader = new BufferedReader(new FileReader("./input (1).sdx"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.split("\\s+"));
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        String[] ops = lines.get(lines.size() - 1);

        // Part 1
        long result = 0;
        for (int c = 0; c < ops.length; c++)
        {
            final boolean isMultiply = ops[c].equals("*");
            long columnResult = isMultiply ? 1 : 0;

            for (int r = 0; r < lines.size() - 1; r++)
            {
                long cell = Long.parseLong(lines.get(r)[c]);
                columnResult = isMultiply ? columnResult * cell : columnResult + cell;
            }

            result += columnResult;
        }

        System.out.println(result);

        // Part 2

        // 1. Column widths
        List<Integer> widths = new ArrayList<Integer>();
        for (int c = 0; c < ops.length; c++) {
            int maxLen = 0;
            for (int r = 0; r < lines.size() - 1; r++)
                maxLen = Math.max(maxLen, lines.get(r)[c].length());

            widths.add(Integer.valueOf(maxLen));
        }

        // 2. Aligned columns
        lines = new ArrayList<String[]>();
        try (BufferedReader reader = new BufferedReader(new FileReader("./input (1).sdx"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int pos = 0;
                String[] cells = new String[ops.length];
                for (int c = 0; c < ops.length; c++)
                {
                    cells[c] = line.substring(pos, pos + widths.get(c));
                    pos = pos + widths.get(c) + 1;
                }
                lines.add(cells);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        // 3. Calculate
        result = 0;
        for (int c = 0; c < ops.length; c++)
        {
            final boolean isMultiply = ops[c].equals("*");
            long columnResult = isMultiply ? 1 : 0;

            List<Long> columnValues = new ArrayList<Long>();
            for (var s = widths.get(c) - 1; s >= 0; s--)
            {
                StringBuilder valueBuilder = new StringBuilder();
                for (int r = 0; r < lines.size() - 1; r++)
                    if (lines.get(r)[c].charAt(s) != ' ')
                        valueBuilder.append(lines.get(r)[c].charAt(s));

                columnValues.add(Long.valueOf(Long.parseLong(valueBuilder.toString())));
            }

            for (long cell : columnValues)
                columnResult = isMultiply ? columnResult * cell : columnResult + cell;

            result += columnResult;
        }

        System.out.println(result);
    }
}