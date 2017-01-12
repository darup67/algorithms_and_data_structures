'use strict';

const fs = require('fs');
const testArr = [
  {
    weight: 1,
    length: 2
  },
  {
    weight: 3,
    length: 4
  },
  {
    weight: 5,
    length: 6
  }
];

function weightedSumByDiff (arr) {
  let compT = 0,
      sum   = 0;

  arr.sort((a,b) => {
    const diff = (b.weight - b.length) - (a.weight - a.length);
    return diff === 0 ? b.weight - a.weight : diff;
  }).forEach(elem => {
    compT += elem.length;
    sum   += elem.weight * compT;
  });

  return sum;
}

function weightedSumByRatio (arr) {
  let compT = 0,
      sum   = 0;

  arr.sort((a,b) => {
    const ratioDiff = (b.weight / b.length) - (a.weight / a.length);
    return ratioDiff === 0 ? b.weight - a.weight : ratioDiff;
  }).forEach(elem => {
    compT += elem.length;
    sum   += elem.weight * compT;
  });

  return sum;
}


fs.readFile('data/jobs.txt', 'utf8', (err, data) => {
  const lines = data.split('\n').slice(1,-1).map(a => {
    const line = a.split(' ');
    return {
      weight: parseInt(line[0]),
      length: parseInt(line[1])
    };
  });

  console.log(weightedSumByDiff(lines));
  console.log(weightedSumByRatio(lines));
})