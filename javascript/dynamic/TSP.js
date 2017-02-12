'use strict';

const fs = require('fs');

// function dist (arrOne, arrTwo) {
//   return Math.sqrt(Math.pow(arrOne[0] - arrTwo[0], 2) + Math.pow(arrOne[1] - arrTwo[1], 2));
// }

// Read file and process data set
fs.readFile('data/tsp.txt', "utf8", (err, data) => {
  const lines   = data.split('\n'),
        cities  = [],
        distArr = [];

  // Fill cities array
  for (let i = 1; i < lines.length - 1; i++) cities.push(lines[i].split(' '));

  // Fill distArr
  for (let i = 0; i < cities.length; i++) {
    distArr.push([]);

    for (let j = 0; j < cities.length; j++) {
      distArr[i][j] = Math.sqrt(Math.pow(cities[i][0] - cities[j][0], 2) + Math.pow(cities[i][1] - cities[j][1], 2));
    }
  }
})
