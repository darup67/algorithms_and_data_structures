'use strict';

// Undirected test graph
const graph = {

	// Adjacency list
	list: [
		[1, 5],
		[0, 2],
		[1, 3, 6],
		[2],
		[5],
		[0, 4],
		[2]
	],

	// Visited nodes array
	init: function () {
		this.visited = Array(this.list.length).fill(false);
		return this;
	}
}.init();

// console.log(graph);

function dfs (g, startVertex) {
	g.visited[startVertex] = true;
	// console.log(g.visited);

	for (const endVertex of g.list[startVertex])  {
		if (!g.visited[endVertex]) {
			dfs(g, endVertex);
		}
	}

	console.log('Finished at:', startVertex);
}

dfs(graph, 0);