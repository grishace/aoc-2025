package day08;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

final class Day08 {

    public static void main(String[] args) {

        List<Point> playground = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("./input (1).sdx"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                long [] coordinates = Arrays.stream((line.split(",", 3))).mapToLong(Long::parseLong).toArray();
                playground.add(new Point(coordinates[0], coordinates[1], coordinates[2]));
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        List<Junction> junctions = new ArrayList<>();
        for (int box = 0; box < playground.size() - 1; box++) {

            Point start = playground.get(box);

            for (int connection = box + 1; connection < playground.size(); connection++){
                Point end = playground.get(connection);
                double distance = Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2) + Math.pow(end.z - start.z, 2));

                junctions.add(new Junction(start, end, distance));
            }
        }

        System.out.println(solveWith(junctions, playground.size(), 1000).part1);
        System.out.println(solveWith(junctions, playground.size(), junctions.size()).part2.orElse(-1L));
    }

    private static Result solveWith(List<Junction> junctions, int playgroundSize, int limit)  {
        List<Junction> workingSet = junctions.stream().sorted(Comparator.comparingDouble(j -> j.distance)).limit(limit).toList();

        int group = 0;
        Map<Point, Integer> groups = new HashMap<>();
        Optional<Junction> lastJunction = Optional.empty();

        for (Junction junction : workingSet) {
            if (!groups.containsKey(junction.start) && !groups.containsKey(junction.end)) {
                group++;
                groups.put(junction.start, group);
                groups.put(junction.end, group);
            }
            else if (groups.containsKey(junction.start) && !groups.containsKey(junction.end)) {
                groups.put(junction.end, groups.get(junction.start));
            }
            else if (!groups.containsKey(junction.start) && groups.containsKey(junction.end)) {
                groups.put(junction.start, groups.get(junction.end));
            }
            else if (!groups.get(junction.start).equals(groups.get(junction.end)))
            {
                int startGroup = groups.get(junction.start);
                int endGroup = groups.get(junction.end);
                for (var entry : groups.entrySet()) {
                    groups.replace(entry.getKey(), endGroup, startGroup);
                }
            }

            if (lastJunction.isEmpty() && groups.size() == playgroundSize && groups.values().stream().distinct().limit(2).count() == 1)
                lastJunction = Optional.of(junction);
        }

        long part1 = 0;
        if (limit == 1000)
           part1 = groups.values().stream()
                .collect(Collectors.groupingBy(Function.identity()))
                .values()
                .stream()
                .map(List::size)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(1, (a, b) -> a * b);

        Optional<Long> part2 = lastJunction.map(l -> l.start.x * l.end.x);

        return new Result(part1, part2);
    }

    record Point(long x, long y, long z) {}
    record Junction(Point start, Point end, double distance) {}
    record Result(long part1, Optional<Long> part2) {}
}
