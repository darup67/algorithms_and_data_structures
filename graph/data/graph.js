'use strict';

// Graph constructor
function Graph (edgeArr) {
  const tempVertexArr = [];

  for (let i = 0; i < edgeArr.length; i++) {
    const startVertex = edgeArr[i][0],
          endVertex   = edgeArr[i][1];

    // If a vertex has not been created for the given start vertex
    if (!tempVertexArr[startVertex]) {
      tempVertexArr[startVertex] = {
        edges: [{
          endVertex: endVertex,
          forward: true
        }],
        visited: false
      };
    
    // If start vertex already exists
    } else {
      tempVertexArr[startVertex].edges.push({
        endVertex: endVertex,
        forward: true
      })
    }

    // If a vertex has not been created for the given end vertex
    if (!tempVertexArr[endVertex]) {
      tempVertexArr[endVertex] = {
        edges: [{
          endVertex: startVertex,
          forward: false
        }],
        visited: false
      };
    
    // If end vertex already exists
    } else {
      tempVertexArr[endVertex].edges.push({
        endVertex: startVertex,
        forward: false
      })
    }
  }

  this.vertices = tempVertexArr;
};

// File read function
function processEdges (data, cr) {
  const lines     = data.split(`${cr ? '\r' : ''}\n`),
        graphData = Array(lines.length);

  // Create edges from file data
  for (let i = 0; i < lines.length; i++) {
    const edge = lines[i].split(' ');
    graphData[i] = [+edge[0], +edge[1]];
  }

  console.log(graphData)

  return graphData;
}

exports.Graph = Graph;
exports.processEdges = processEdges;