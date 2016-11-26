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

	for (let i = 0; i < tempVertexArr.length; i++) {
		if (tempVertexArr[i]) tempVertexArr[i].label = i;
	}

	this.vertices = tempVertexArr;

	if (debug) {
		for (var i = 0; i < this.vertices.length; i++) {
			console.log('Vertex:', this.vertices[i].label);
			console.log(this.vertices[i]);
			console.log();
		}
	}
};



// Outer loop for topo sort
function findSCC (g, debug) {
	let finishingTime       = 0,
	    currentSourceVertex = undefined,
	    orderArr            = [];

	// DFS modified for SCC
	function dfs (g, currentVertex, isLoopTwo) {
		
		// Set this vertex to visited
		g.vertices[currentVertex].visited = true;

		// If this is the 2nd pass, set leader for this vertex to currentSourceVertex
		if (isLoopTwo) g.vertices[currentVertex].leader = currentSourceVertex;

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

	console.log(orderArr);

	// Reset all vertices to unvisited
	for (let i = 0; i < g.vertices.length; i++) {
		if (g.vertices[i]) g.vertices[i].visited = false;
	}

	// Loop #2 (leader labelling)
	if (debug) console.log('Loop 2: Forward')
	for (let i = orderArr.length - 1; i >= 0; i--) {
		if (g.vertices[orderArr[i]] && !g.vertices[orderArr[i]].visited) {
			currentSourceVertex = orderArr[i];

			if (debug) {
				console.log();
				console.log('DFS at', currentSourceVertex);
			}
			
			dfs(g, currentSourceVertex, true);
		}
	}
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

	findSCC(graph, process.argv[2]);
})