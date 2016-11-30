'use strict';

const fs           = require('fs'),
      Graph        = require('./data/graph.js').Graph,
      processEdges = require('./data/graph.js').processEdges,
      debug        = process.argv[2];

// Outer loop for SCC
function findSCC (g) {
  let orderArr            = [],
      count               = 0,
      countArr            = []; 

  // DFS modified for SCC
  function dfs (g, v, isLoopTwo) {
    
    const stack  = [g.vertices[v]],
          result = [];

    while (stack.length > 0) {
      const currentVertex = stack.pop();

      currentVertex.visited = true;

      if (isLoopTwo) {
        currentVertex.leader = v;
        count++;
      }

      for (let i = 0; i < currentVertex.edges.length; i++) {
        const edgeCondition = isLoopTwo ? currentVertex.edges[i].forward : !currentVertex.edges[i].forward;

        if (edgeCondition && !g.vertices[currentVertex.edges[i].endVertex].visited) {
          g.vertices[currentVertex.edges[i].endVertex].visited = true;
          stack.push(g.vertices[currentVertex.edges[i].endVertex]);
        }
      }

      result.push(currentVertex.label);
    }

    if (!isLoopTwo) {
      for (let i = result.length - 1; i >= 0; i--) {
        orderArr.push(result[i]);
      }
    }
  }



  // Loop #1 (ordering)
  if (debug) console.log('Loop 1: Reverse');
  for (let i = g.vertices.length - 1; i >= 0; i--) {
    if (g.vertices[i] && !g.vertices[i].visited) {
      dfs(g, i, false);
    }
  }

  if (debug) {
    console.log('orderArr:');
    console.log(orderArr);
    console.log();
  }

  // Reset all vertices to unvisited
  for (let i = 0; i < g.vertices.length; i++) {
    if (g.vertices[i]) g.vertices[i].visited = false;
  }

  // Loop #2 (leader labelling)
  if (debug) console.log('Loop 2: Forward');
  for (let i = orderArr.length - 1; i >= 0; i--) {
    if (g.vertices[orderArr[i]] && !g.vertices[orderArr[i]].visited) {
      count = 0;
      
      dfs(g, orderArr[i], true);
      countArr.push(count);
    }
  }

  return countArr;
}



// Read file and process data set
fs.readFile("data/SCC.txt", "utf8", (err, data) => {
  const graph    = new Graph(processEdges(data, false)),
        sccCount = findSCC(graph, process.argv[2]);
        
  console.log('SCC Counts:');
  console.log(sccCount.sort((a,b) => b-a));
})