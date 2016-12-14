'use strict';

const fs = require('fs');

// Convert array to set consisting ONLY of unique items in the original array
Array.prototype.toUniqueSet = function () {
  const newSet = new Set();

  for (const elem of this) {
    if (newSet.has(elem)) newSet.delete(elem);
    else newSet.add(elem)
  }
  return newSet;
}

// Read text file and output array of ints
function readFile (filename) {
  return new Promise((resolve, reject) => {
    fs.readFile(filename, 'utf8', (err, data) => 
      resolve(data.split('\n').map(Number))
    )
  })
}

// Can x, y be found in uniqueSet such that x + y = target?
function isTargetSatisfied (target, set) {
  for (const elem of set) {
    const value = target - elem;
    if (value !== elem && set.has(value)) {
      // console.log(`Target: ${target}, elem: ${elem}, value: ${value}`);
      return true;
    }
  }
  return false;
}

// 2SUM procedure for range (-10000, 10000)
function twoSum (array) {
  const uniqueSet = array.toUniqueSet();
  let count = 0;

  for (let i = -10000; i <= 10000; i++) {
    if (isTargetSatisfied(i, uniqueSet)) count++;
  }

  return count;
}

readFile('data/2sum.txt').then(data => console.log(twoSum(data)));
