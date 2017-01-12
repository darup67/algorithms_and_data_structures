'use strict';

const fs = require('fs');
const testArr = [1, 2, 3, 4, 5];

function mwis (arr) {
  const memo     = [0, arr[0]],
        solution = [];
  let i;

  for (i = 1; i < arr.length; i++) {
    memo.push(Math.max(memo[i], memo[i - 1] + arr[i]));
  }

  // console.log(memo);

  while (i > 1) {
    // console.log(memo[i - 1], memo[i - 2], arr[i - 1])
    if (memo[i - 1] >= memo[i - 2] + arr[i - 1]) i--;
    else {
      solution.push(i);
      i -= 2;
    }
  }
  if (solution[solution.length - 1] > 2) solution.push(1);

  return solution.reverse();
}


// console.log(mwis(testArr));

fs.readFile('data/mwis.txt', 'utf8', (err, data) => {
  const lines = data.split('\n').slice(1,-1).map(Number);

  const solution = mwis(lines);
  console.log((solution.indexOf(1)   > -1 ? "1" : "0") + 
              (solution.indexOf(2)   > -1 ? "1" : "0") +
              (solution.indexOf(3)   > -1 ? "1" : "0") +
              (solution.indexOf(4)   > -1 ? "1" : "0") +
              (solution.indexOf(17)  > -1 ? "1" : "0") +
              (solution.indexOf(117) > -1 ? "1" : "0") +
              (solution.indexOf(517) > -1 ? "1" : "0") +
              (solution.indexOf(997) > -1 ? "1" : "0"));
});
