using System;
using System.Collections.Generic;
using System.IO;

var lines = await File.ReadAllLinesAsync("./input (1).sdx");

Console.WriteLine(lines.Select(b => JoltagePart1(b)).Sum());
Console.WriteLine(lines.Select(b => JoltagePart2(b)).Sum());

decimal JoltagePart1(string line) => Joltage(line, 2);
decimal JoltagePart2(string line) => Joltage(line, 12);

decimal Joltage(string line, int maxDigits)
{
  var digits = new[] { '9', '8', '7', '6', '5', '4', '3', '2', '1', '0' };

  var currentOffset = 0;
  var v = new Stack<int>();

  for (var n = 1; n <= maxDigits; ++n)
  {
    var index = -1;
    foreach (var d in digits)
    {
      index = line.IndexOf(d, currentOffset);
      if (index >= line.Length - (maxDigits - n))
        continue;
      if (index >= 0)
        break;
    }

    v.Push(line[index] - '0');
    currentOffset = index + 1;
  }

  var res = 0M;
  var m = 1M;
  while (v.Any())
  {
    res = res + v.Pop() * m;
    m = m * 10M;
  }

  return res;
}
