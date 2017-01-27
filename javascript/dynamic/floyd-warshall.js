'use strict';

const fs = require('fs');

// Does edge(i,j) exist?
function findEdge (vertex, endPoint) {
  for (const edge of vertex) {
    if (edge[0] === endPoint) return edge[1];
  }
  return false;
}

function floydWarshall (distArr) {
  const n = distArr.length;

  // Main loop
  for (let k = 0; k < n; k++) {   
    for (let i = 0; i < n; i++) {
      for (let j = 0; j < n; j++) {
        distArr[i][j] = Math.min(distArr[i][j], distArr[i][k] + distArr[k][j]);
      }
    }
  }

  // console.log(currArr);

  // let str = '';
  // for (const elem of currArr) str += '[ ' + elem.join(', ') + ' ]\n';
  // fs.writeFile('graph3out.txt', str, err => { if (err) throw err; });

  // Find and return 'shortest shortest path'
  let min = Number.POSITIVE_INFINITY;
  
  for (let i = 0; i < n; i++) {
    
    // Negative cost cycle check
    if (distArr[i][i] < 0) return 'Negative cycle detected at ' + i;

    for (let j = 0; j < n; j++) {
      if (i !== j && distArr[i][j] < min) min = distArr[i][j];
    }
  }
  return min;
}

// Read file and process data set
fs.readFile('data/large.txt', "utf8", (err, data) => {
  const lines             = data.split('\n'),
        [vertices, edges] = lines[0].split(' ').map(Number),
        distArr           = [];

  // Initialize all distances to Infinity
  for (let i = 0; i < vertices; i++) {
    distArr[i] = [];
    for (let j = 0; j < vertices; j++) distArr[i].push(Number.POSITIVE_INFINITY);
  }

  // Store edges from file data
  for (let i = 0; i < edges; i++) {
    const [startVertex, endVertex, weight] = lines[i + 1].split(' ').map(Number);
    distArr[startVertex - 1][endVertex - 1] = weight;
  }

  // Zero distances to self
  for (let i = 0; i < vertices; i++) distArr[i][i] = 0;
  
  console.log(floydWarshall(distArr));
})