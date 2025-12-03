using System;
using System.IO;

var lines = await File.ReadAllLinesAsync("./input.sdx");

(int _, int zeroes) = lines.Select(line => (line.StartsWith("L") ? -1 : 1) * Int32.Parse(line.Substring(1)))
                           .Aggregate((50, 0), (acc, rotate) => ((acc.Item1 + rotate) % 100, acc.Item2 + ((acc.Item1 + rotate) % 100 == 0 ? 1 : 0)));

Console.WriteLine(zeroes);


