'use strict';

const fs        = require('fs'),
      testInput = [[[1, 5]], [[2, 10]], [[0, 15]]];

function floydWarshall (vertices) {
  const n = vertices.length - 1;
  let prevArr,
      currArr = [];

  // Does edge(i,j) exist?
  function findEdge (vertex, endPoint) {
    for (const edge of vertex) {
      if (edge[0] === endPoint) return edge[1];
    }
    return false;
  }

  // Initialization step
  for (let i = 0; i < n; i++) {
    currArr[i] = [];

    for (let j = 0; j < n; j++) {
      if (i === j) currArr[i][j] = 0;
      else {
        const weight = findEdge(vertices[i + 1], j + 1);
        if (weight) currArr[i][j] = weight;
        else currArr[i][j] = Number.POSITIVE_INFINITY;
      }
    }
  }

  // Main loop
  for (let k = 0; k < n; k++) {
    let temp = prevArr;
    prevArr = currArr;
    currArr = [];
    temp = null;
    
    for (let i = 0; i < n; i++) {
      currArr[i] = [];

      for (let j = 0; j < n; j++) {
        currArr[i][j] = Math.min(prevArr[i][j], prevArr[i][k] + prevArr[k][j]);
      }
    }
  }

  console.log(currArr);

  // Find and return 'shortest shortest path'
  let min = Number.MAX_SAFE_INTEGER;
  
  for (let i = 0; i < n; i++) {
    
    // Negative cost cycle check
    if (currArr[i][i] < 0) return 'Negative cycle detected';

    for (let j = 0; j < n; j++) {
      if (currArr[i][j] < min) min = currArr[i][j];
    }
  }
  return min;
}

// Read file and process data set
fs.readFile('data/graph3.txt', "utf8", (err, data) => {
  const lines    = data.split('\n'),
        vertices = new Array(parseInt(lines[0].split(' ')[0]) + 1);

  // Create vertices from file data
  // Renames vertices (vertex = vertex - 1) to align with zero indexed arrays
  for (let i = 1; i < lines.length - 1; i++) {
    const [startVertex, endVertex, weight] = lines[i].split(' ').map(Number);
    vertices[startVertex] ? vertices[startVertex].push([endVertex, weight]) : vertices[startVertex] = [[endVertex, weight]];
  }
  
  console.log(floydWarshall(vertices));
})