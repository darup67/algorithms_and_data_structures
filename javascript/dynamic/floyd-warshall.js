'use strict';

const fs        = require('fs'),
      testInput = [[[1, 5]], [[2, 10]], [[0, 15]]];

function floydWarshall (vertices) {
  const arr = new Array(vertices.length);

  // Does edge(i,j) exist?
  function findEdge (vertex, endPoint) {
    for (const edge of vertex) {
      if (edge[0] === endPoint) return edge[1];
    }
    return false;
  }

  // Initialization step
  for (let i = 0; i < vertices.length; i++) {
    arr[i] = [];

    for (let j = 0; j < vertices.length; j++) {
      arr[i][j] = [];
      
      if (i === j) arr[i][j][0] = 0;
      else {
        const weight = findEdge(vertices[i], j);
        if (weight) arr[i][j][0] = weight;
        else arr[i][j][0] = Infinity;
      }
    }
  }

  // Main loop
  for (let k = 1; k < vertices.length; k++) {
    for (let i = 0; i < vertices.length; i++) {
      for (let j = 0; j < vertices.length; j++) {
        arr[i][j][k] = Math.min(arr[i][j][k - 1], arr[i][k][k - 1] + arr[k][j][k - 1]);
      }
    }
  }

  return arr;
}

// Read file and process data set
fs.readFile("data/graph1.txt", "utf8", (err, data) => {
  const lines    = data.split('\n'),
        vertices = new Array(parseInt(lines[0].split(' ')[0]));

  // Create vertices from file data
  // Renames vertices (vertex = vertex - 1) to align with zero indexed arrays
  for (let i = 1; i < lines.length - 1; i++) {
    const [startVertex, endVertex, weight] = lines[i].split(' ').map(Number);
    vertices[startVertex - 1] ? vertices[startVertex - 1].push([endVertex - 1, weight]) : vertices[startVertex - 1] = [[endVertex - 1, weight]];
  }
  
  const output = floydWarshall(testInput);
  console.log(output);
})