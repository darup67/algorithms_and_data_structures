'use strict';

const fs = require('fs');
const testArr = [[0,0], [3,4], [2,3], [4,2], [4,3]],
      testWgt = 6;

function knapsack (arr, weight) {
  let prevCol,
      currCol = [];
  for (let j = 0; j <= weight; j++) currCol[j] = 0;

  for (let i = 1; i < arr.length; i++) {
    prevCol = currCol;
    currCol = [];
    
    for (let j = 0; j <= weight; j++) {
      const caseOne = prevCol[j],
            caseTwo = prevCol[j - arr[i][1]] + arr[i][0];

      if (j - arr[i][1] < 0) currCol[j] = caseOne;
      else currCol[j] = Math.max(caseOne, caseTwo);
    }
  }

  return currCol[weight];
}


// console.log(knapsack(testArr, testWgt));

fs.readFile('data/knapsack_big.txt', 'utf8', (err, data) => {
  const lines  = data.split('\n'),
        arr    = [],
        weight = lines[0].split(' ').map(Number)[0];

  for (let i = 1; i < lines.length - 1; i++) {
    arr[i] = lines[i].split(' ').map(Number);
  } 
  console.log(knapsack(arr, weight));
});
