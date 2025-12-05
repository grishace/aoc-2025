using System;
using System.Collections.Generic;
using System.IO;

var lines = await File.ReadAllLinesAsync("./input.sdx");

var ranges = new List<decimal[]>();
var lineIndex = 0;
do
{
  ranges.Add(lines[lineIndex].Split('-').Select(n => Decimal.Parse(n)).ToArray());
  lineIndex++;
} while (!String.IsNullOrEmpty(lines[lineIndex]));

// Part 1
lineIndex++;
var fresh = 0;
do {
  var id = Decimal.Parse(lines[lineIndex]);
  if (ranges.Any(r => r[0] <= id && id <= r[1]))
    fresh++;
  
  lineIndex++;
} while (lineIndex < lines.Length);

Console.WriteLine(fresh);

// Part 2
var merged = new List<decimal[]>();
var sortedRanges = ranges.OrderBy(r => r[0]).ToArray();

merged.Add(sortedRanges[0]);

for (int i = 1; i < sortedRanges.Length; i++)
{
  {
    var last = merged[merged.Count - 1];

    // If current interval overlaps with last, merge them
    if (last[1] >= sortedRanges[i][0])
      last[1] = Math.Max(last[1], sortedRanges[i][1]);
    else
      merged.Add(sortedRanges[i]);
  }
}

Console.WriteLine(merged.Aggregate(0M, (cnt, r) => cnt += (r[1] - r[0] + 1)));
