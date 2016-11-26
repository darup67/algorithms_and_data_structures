'use strict';

// Vertex constructor
function Vertex (edges) {
	this.edges = edges;
	this.visited = false;
}

// Graph constructor
function Graph (vertices) {
	const tempVertexArr = Array(vertices.length);

	for (let i = 0; i < vertices.length; i++) {
		tempVertexArr[i] = new Vertex(vertices[i]);
	}

	this.vertices = tempVertexArr;
};

// Outer loop for topo sort
function dfs_loop (g, debug) {
	let currentLabel = g.vertices.length - 1,
	    ordering     = [];

	// DFS modified for topo sort
	function dfs (g, currentVertex) {
		
		// Set this vertex to visited
		g.vertices[currentVertex].visited = true;

		// Recursive DFS loop
		for (const endVertex of g.vertices[currentVertex].edges) {
			if (!g.vertices[endVertex].visited) {
				dfs(g, endVertex);
			}
		}

		// Add ordering to vertex and ordering array, decrement currentLabel
		g.vertices[currentVertex].order = currentLabel;
		ordering.unshift(currentVertex);
		currentLabel--;

		if (debug) {
			console.log('Node:', currentVertex);
			console.log(g.vertices[currentVertex]);
			console.log();
		}
	}

	// Outer loop
	for (let i = g.vertices.length - 1; i >= 0; i--) {
		if (!g.vertices[i].visited) {
			if (debug) {
				console.log();
				console.log('DFS at', i);
				console.log('currentLabel:', currentLabel);
			}

			dfs(g, i);
		}
	}

	return ordering;
}


const graph = new Graph([
	[5],
	[0],
	[1,3],
	[],
	[5],
	[],
	[2]
]);

console.log(dfs_loop(graph, process.argv[2] || false));