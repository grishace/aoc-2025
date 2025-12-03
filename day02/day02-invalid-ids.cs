using System;
using System.IO;
using System.Linq;

var lines = await File.ReadAllLinesAsync("./input.sdx");
var rangeStr = lines.FirstOrDefault(String.Empty);

var ranges = rangeStr.Split(',')
                     .Select(r => r.Split('-', 2).Select(UInt64.Parse).ToArray())
                     .ToList();

Console.WriteLine(SolveDay2(ranges, IsInvalidNumberPart1));
Console.WriteLine(SolveDay2(ranges, IsInvalidNumberPart2));

ulong SolveDay2(List<ulong[]> ranges, Func<ulong, bool> invalidFn)
{
  return ranges.Aggregate(0UL, (sum, range) =>
                {
                  for (var r = range[0]; r <= range[1]; r++)
                    if (invalidFn(r))
                      sum += r;

                  return sum;
                });
}

bool IsInvalidNumberPart1(ulong number)
{
  var pow = Math.Truncate((Math.Log10(number) + 1) / 2);
  var div = Convert.ToUInt64(Math.Pow(10, pow));

  return (number / div) == (number % div);
}

bool IsInvalidNumberPart2(ulong number)
{
  var str = number.ToString();
  for (var chunk = 1; chunk <= str.Length/2; chunk++)
    if (str.Chunk(chunk).Select(ch => new String(ch)).ToHashSet().Count == 1)
      return true;

  return false;
}
