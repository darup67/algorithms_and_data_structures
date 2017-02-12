'use strict';

const fs = require('fs');

function tspNearest (cities) {
  const startingCity = cities.shift();
  let currentCity = startingCity,
      totalDist   = 0;

  function squareDist (a, b) {
    return Math.pow(a[1] - b[1], 2) + Math.pow(a[2] - b[2], 2);
  }

  while (cities.length > 0) {
    let destCity  = [],
        destIndex = 0,
        minDist   = Infinity;

    for (let i = 0; i < cities.length; i++) {
      let dist = squareDist(currentCity, cities[i]);
      
      if (dist < minDist || (dist === minDist && cities[i][0] < destCity[0])) {
        destCity  = cities[i];
        destIndex = i;
        minDist   = dist;
      }
    }

    currentCity = cities.splice(destIndex, 1)[0];
    totalDist += Math.sqrt(minDist);
  }

  totalDist += Math.sqrt(squareDist(currentCity, startingCity));
  return totalDist;
}

// Read file and process data set
fs.readFile('data/nn.txt', "utf8", (err, data) => {
  const lines   = data.split('\r\n'),
        cities  = [];

  // Fill cities array
  for (let i = 1; i < lines.length - 1; i++) cities.push(lines[i].split(' ').map(Number));
  
  console.log(tspNearest(cities));
})
