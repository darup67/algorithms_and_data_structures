'use strict';

const fs        = require('fs'),
      testInput = [[[1, 5]], [[2, 10]], [[0, 15]]];

function floydWarshall (vertices) {
  let prevArr,
      currArr = new Array(vertices.length);

  // Does edge(i,j) exist?
  function findEdge (vertex, endPoint) {
    for (const edge of vertex) {
      if (edge[0] === endPoint) return edge[1];
    }
    return false;
  }

  // Initialization step
  for (let i = 0; i < vertices.length - 1; i++) {
    currArr[i] = [];

    for (let j = 0; j < vertices.length - 1; j++) {
      if (i === j) currArr[i][j] = 0;
      else {
        const weight = findEdge(vertices[i + 1], j + 1);
        if (weight) currArr[i][j] = weight;
        else currArr[i][j] = Infinity;
      }
    }
  }

  // Main loop
  for (let k = 0; k < vertices.length - 1; k++) {
    let temp = prevArr;
    prevArr = currArr;
    currArr = new Array(vertices.length);
    temp = null;
    
    for (let i = 0; i < vertices.length - 1; i++) {
      currArr[i] = [];

      for (let j = 0; j < vertices.length - 1; j++) {
        currArr[i][j] = Math.min(prevArr[i][j], prevArr[i][k] + prevArr[k][j]);
      }
    }
  }

  return currArr;
}

// Read file and process data set
fs.readFile("data/test1.txt", "utf8", (err, data) => {
  const lines    = data.split('\r\n'),
        vertices = new Array(parseInt(lines[0].split(' ')[0]));

  // Create vertices from file data
  // Renames vertices (vertex = vertex - 1) to align with zero indexed arrays
  for (let i = 1; i < lines.length - 1; i++) {
    const [startVertex, endVertex, weight] = lines[i].split(' ').map(Number);
    vertices[startVertex] ? vertices[startVertex].push([endVertex, weight]) : vertices[startVertex] = [[endVertex, weight]];
  }
  
  const output = floydWarshall(vertices);
  console.log(output);
})