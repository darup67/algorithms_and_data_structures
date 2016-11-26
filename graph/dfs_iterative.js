'use strict';

const fs           = require('fs'),
      Graph        = require('./data/graph.js').Graph,
      processEdges = require('./data/graph.js').processEdges;

function dfs (g, currentVertex) {
  if (g.vertices[currentVertex]) {

    // Set this vertex to visited
    g.vertices[currentVertex].visited = true;

    // Recursive DFS loop
    // Visit only previously unvisited vertices and only traverse edges in desired direction
    for (const edge of g.vertices[currentVertex].edges) {
      if (!g.vertices[edge.endVertex].visited && edge.forward) {
        dfs(g, edge.endVertex);
      }
    }
  }

  console.log('Finished at:', currentVertex);
}

// Read file and process data set
fs.readFile("data/dfs_test.txt", "utf8", (err, data) => {
  const graph = new Graph(processEdges(data, true));
  
  console.log(graph);
  dfs(graph, 6)
})
