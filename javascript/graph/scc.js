'use strict';

const fs           = require('fs'),
      Graph        = require('./data/graph.js').Graph,
      processEdges = require('./data/graph.js').processEdges;

// Outer loop for SCC
function findSCC (g, debug) {
  let finishingTime       = 0,
      currentSourceVertex = undefined,
      orderArr            = [],
      count               = 0,
      countArr            = [];

  for (let i = 0; i < g.vertices.length; i++) {
    if (g.vertices[i]) g.vertices[i].label = i;
  }  

  // DFS modified for SCC
  function dfs (g, currentVertex, isLoopTwo) {
    
    // Set this vertex to visited
    g.vertices[currentVertex].visited = true;

    // If this is the 2nd pass, set leader for this vertex to currentSourceVertex
    if (isLoopTwo) {
      g.vertices[currentVertex].leader = currentSourceVertex;
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
      g.vertices[currentVertex].finishingTime = finishingTime;
      finishingTime++;
      orderArr.push(g.vertices[currentVertex].label);
    }

    if (debug) {
      console.log('Vertex:', g.vertices[currentVertex].label);
      console.log(g.vertices[currentVertex]);
      console.log();
    }
  }



  // Loop #1 (ordering)
  if (debug) console.log('Loop 1: Reverse')
  for (let i = g.vertices.length - 1; i >= 0; i--) {
    if (g.vertices[i] && !g.vertices[i].visited) {
      if (debug) {
        console.log();
        console.log('DFS at', g.vertices[i].label);
        console.log('finishingTime:', finishingTime);
      }

      dfs(g, i, false);
    }
  }

  // Reset all vertices to unvisited
  for (let i = 0; i < g.vertices.length; i++) {
    if (g.vertices[i]) g.vertices[i].visited = false;
  }

  // Loop #2 (leader labelling)
  if (debug) console.log('Loop 2: Forward')
  for (let i = orderArr.length - 1; i >= 0; i--) {
    if (g.vertices[orderArr[i]] && !g.vertices[orderArr[i]].visited) {
      count = 0;
      currentSourceVertex = orderArr[i];

      if (debug) {
        console.log();
        console.log('DFS at', currentSourceVertex);
      }
      
      dfs(g, currentSourceVertex, true);
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