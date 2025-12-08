package day07;

import java.io.*;
import java.util.*;

final class Day07 {

    public static void main(String[] args) {

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("./input.sdx"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }


        int splitCount = 0;
        Map<Integer, Long> beams = new HashMap<>();
        beams.put(lines.get(0).indexOf('S'), Long.valueOf(1));

        for (int i = 1; i < lines.size(); i++) {

            Map<Integer, Long> nextBeams = new HashMap<>();

            for (int beamPosition : beams.keySet()) {
                char beamChar = lines.get(i).charAt(beamPosition);
                long pathCount = beams.get(beamPosition);

                switch (beamChar) {
                    case '.':
                        addBeam(nextBeams, beamPosition, pathCount);
                        break;
                    case '^':
                        int leftBeamPosition = beamPosition - 1;
                        int rightBeamPosition = beamPosition + 1;

                        if (leftBeamPosition >= 0)
                            addBeam(nextBeams, leftBeamPosition, pathCount);
                        if (rightBeamPosition < lines.get(i).length())
                            addBeam(nextBeams, rightBeamPosition, pathCount);

                        splitCount++;
                        break;
                    default:
                }
            }

            beams = nextBeams;
        }

        // Part 1
        System.out.println(splitCount);

        // Part 2
        System.out.println(beams.values().stream().mapToLong(Long::longValue).sum());
    }

    private static void addBeam(Map<Integer, Long> beams, int beamPosition, long pathCount) {
        if (beams.containsKey(beamPosition)) {
            beams.replace(beamPosition, beams.get(beamPosition) + pathCount);
        }
        else {
            beams.put(beamPosition, pathCount);
        }
    }
}
