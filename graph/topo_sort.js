'use strict';

// Edge constructor
function Vertex (edges) {
	this.edges = edges;
	this.visited = false;
}

// Test graph
const graph = {

	// Adjacency list
	vertexes: [
		new Vertex([5]),
		new Vertex([0]),
		new Vertex([1, 3]),
		new Vertex([]),
		new Vertex([5]),
		new Vertex([]),
		new Vertex([2])
	]
};

function dfs_loop (g) {
	let currentLabel = g.vertexes.length - 1;

	for (let i = g.vertexes.length - 1; i >= 0; i--) {
		if (!g.vertexes[i].visited) {
			console.log()
			console.log('DFS at', i);
			console.log('currentLabel:', currentLabel)
			dfs(g, i);
		}
	}

	function dfs (g, startVertex) {
		g.vertexes[startVertex].visited = true;

		for (const endVertex of g.vertexes[startVertex].edges)  {
			if (!g.vertexes[endVertex].visited) {
				dfs(g, endVertex);
			}
		}

		g.vertexes[startVertex].order = currentLabel;
		currentLabel--;

		console.log('Node:', startVertex);
		console.log(g.vertexes[startVertex]);
		console.log();
	}
}

dfs_loop(graph, 0);