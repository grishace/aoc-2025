using System;
using System.IO;

var lines = await File.ReadAllLinesAsync("./input.sdx ");

var removedRolls = 0;
var rollsToRemove = new List<(int row, int col)>();

do
{
  rollsToRemove.Clear();

  var rolls = 0;
  for (var r = 0; r < lines.Length; r++)
  {
    for (var c = 0; c < lines[0].Length; c++)
    {
      if (lines[r][c] != '@')
        continue;

      if (AdjacentRolls(lines, r, c) < 4)
      {
        rollsToRemove.Add((r, c));
        rolls++;
      }
    }
  }

  // Part 1
  if (removedRolls == 0)
    Console.WriteLine(rolls);

  removedRolls += rollsToRemove.Count;
  foreach ((int row, int col) in rollsToRemove)
    lines[row] = lines[row].Substring(0, col) + 'x' + lines[row].Substring(col + 1);
} while (rollsToRemove.Count > 0);

// Part 2
Console.WriteLine(removedRolls);

int AdjacentRolls(string[] lines, int row, int column)
{
  var offsets = new[] {
    (-1, -1), (-1, 0), (-1, 1),
    (0, - 1),          ( 0, 1),
    (1,  -1), ( 1, 0), ( 1, 1)
  };
  
  var res = 0;
  foreach ((int rowOffset, int colOffset) in offsets)
  {
    var neighbourRow = row + rowOffset;
    if (neighbourRow < 0 || neighbourRow >= lines.Length)
      continue;
    
    var neighbourCol = column + colOffset;
    if (neighbourCol < 0 || neighbourCol >= lines[0].Length)
      continue;

    if (lines[neighbourRow][neighbourCol] == '@')
      res++;
  }

  return res;
}
