'use strict';

const fs           = require('fs'),
      Graph        = require('./data/graph.js').Graph,
      processEdges = require('./data/graph.js').processEdges;

// Outer loop for topo sort
function findSCC (g) {
  let finishingTime       = 0,
      orderArr            = [],
      count               = 0,
      countArr            = [];

  // DFS modified for SCC
  function dfs (g, currentVertex, isLoopTwo) {
    
    // Set this vertex to visited
    g.vertices[currentVertex].visited = true;

    // If this is the 2nd pass, set leader for this vertex to currentSourceVertex
    if (isLoopTwo) {
      count++;
    }

    // Recursive DFS loop
    // Visit only previously unvisited vertices and only traverse edges in desired direction
    for (const edge of g.vertices[currentVertex].edges) {
      const edgeCondition = isLoopTwo ? edge.forward : !edge.forward;
    
      if (!g.vertices[edge.endVertex].visited && edgeCondition) {
        dfs(g, edge.endVertex, isLoopTwo);
      }
    }

    // If this is the 1st pass, set finishingTime to finishingTime and increment
    if (!isLoopTwo) {
      orderArr.push(currentVertex);
      finishingTime++;
    }
  }



  // Loop #1 (ordering)
  for (let i = g.vertices.length - 1; i >= 0; i--) {
    if (g.vertices[i] && !g.vertices[i].visited) {
      dfs(g, i, false);
    }
  }

  // Reset all vertices to unvisited
  for (let i = 0; i < g.vertices.length; i++) {
    if (g.vertices[i]) g.vertices[i].visited = false;
  }

  // Loop #2 (leader labelling)
  for (let i = orderArr.length - 1; i >= 0; i--) {
    const index = orderArr[i];

    if (g.vertices[index] && !g.vertices[index].visited) {
      count = 0;
      
      dfs(g, index, true);
      countArr.push(count);
    }
  }

  return countArr;
}



// Read file and process data set
fs.readFile("data/scc_test.txt", "utf8", (err, data) => {
  const graph    = new Graph(processEdges(data, true)),
        sccCount = findSCC(graph, process.argv[2]);
        
  console.log(sccCount.sort((a,b) => a-b));
})