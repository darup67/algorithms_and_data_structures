'use strict';

const fs = require('fs');

// Graph constructor
function Graph (edgeArr, debug) {
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

	if (debug) {
		for (var i = 0; i < this.vertices.length; i++) {
			console.log('Vertex:', i);
			console.log(graph.vertices[i]);
			console.log();
		}
	}
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
		// Visit only previously unvisited vertices and only traverse forward edges
		for (const edge of g.vertices[currentVertex].edges) {
			if (!g.vertices[edge.endVertex].visited && edge.forward) {
				dfs(g, edge.endVertex);
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



// Test functions
fs.readFile("scc_test.txt", "utf8", (err, data) => {
	const lines     = data.split('\r\n'),
	      graphData = Array(lines.length);

	// Create edges from file data
	for (let i = 0; i < lines.length; i++) {
		const edge = lines[i].split(' ');
		edge[0] = +edge[0];
		edge[1] = +edge[1];
		graphData[i] = edge;
	}

	const graph = new Graph(graphData);

	console.log(dfs_loop(graph, process.argv[2]));
})