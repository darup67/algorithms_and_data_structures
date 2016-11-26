'use strict';

const fs           = require('fs'),
      Graph        = require('./data/graph.js').Graph,
      processEdges = require('./data/graph.js').processEdges;

function dfs (g, v) {
  if (g.vertices[v]) {

    // Create vertex stack
    const stack  = [g.vertices[v]],
          result = [];

    // Visit only previously unvisited vertices and only traverse edges in desired direction
    while (stack.length > 0) {
      const currentVertex = stack.pop();

      currentVertex.visited = true;

      for (let i = 0; i < currentVertex.edges.length; i++) {
        if (currentVertex.edges[i].forward && !g.vertices[currentVertex.edges[i].endVertex].visited) {
          g.vertices[currentVertex.edges[i].endVertex].visited = true;
          stack.push(g.vertices[currentVertex.edges[i].endVertex]);
        }
      }

      result.unshift(currentVertex);
    }

    return result;
  }

  return [g.vertices[v]];
}

// Read file and process data set
fs.readFile("data/dfs_test.txt", "utf8", (err, data) => {
  const graph = new Graph(processEdges(data, true)),
        res   = dfs(graph, 6);

  for (const vertex of res) console.log(vertex);
})
