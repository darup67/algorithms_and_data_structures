'use strict';

const fs = require('fs');
const testArr = [[0,0], [3,4], [2,3], [4,2], [4,3]],
      testWgt = 6;

function knapsack (arr, weight) {
  const solution = [[]];
  for (let j = 0; j <= weight; j++) solution[0][j] = 0;

  for (let i = 1; i < arr.length; i++) {
    solution[i] = [];

    for (let j = 0; j <= weight; j++) {
      const caseOne = solution[i - 1][j],
            caseTwo = solution[i - 1][j - arr[i][1]] + arr[i][0];

      if (j - arr[i][1] < 0) solution[i][j] = caseOne;
      else solution[i][j] = Math.max(caseOne, caseTwo);
    }
  }

  return solution[arr.length - 1][weight];
}


// console.log(knapsack(testArr, testWgt));

fs.readFile('data/knapsack100.txt', 'utf8', (err, data) => {
  const lines  = data.split('\n'),
        arr    = [],
        weight = lines[0].split(' ').map(Number)[0];

  for (let i = 1; i < lines.length - 1; i++) {
    arr[i] = lines[i].split(' ').map(Number);
  } 
  console.log(knapsack(arr, weight));
});
