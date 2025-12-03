using System;
using System.IO;

var lines = await File.ReadAllLinesAsync("./input.sdx");

(int _, int zeroes) = lines.Select(line => (line.StartsWith("L") ? -1 : 1) * Int32.Parse(line.Substring(1)))
                           .Aggregate((50, 0), (acc, rotate) =>
                            {
                              // dumb
                              var pos = acc.Item1;
                              var cnt = acc.Item2;
                              for (int i = 0; i < Math.Abs(rotate); i++)
                              {
                                pos += (rotate < 0 ? -1 : 1);
                                if (pos == 100)
                                  pos = 0;
                                cnt += pos == 0 ? 1 : 0;
                                if (pos == -1)
                                  pos = 99;
                              }
                              return (pos, cnt);
                           });

Console.WriteLine(zeroes);
