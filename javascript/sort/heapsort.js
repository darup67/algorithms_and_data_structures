// Future optimization:
// - use faster heap construction algorithm
// - early exit for bubbleDown
// - zero-index heap -> create in-place?

'use strict';

const Heap = require('./../binary_trees/heap.js');

function heapsort (arr) {
	let heap = new Heap();

	function bubbleDown (queue, priority) {
		const childIndex = priority * 2;
		let lowestPriority = priority;

		// Find lowest priority node (check both possible child nodes)
		for (let i = 0; i <= 1; i++) {

			// If the possible child index is an index in the queue
			if (childIndex + i <= queue.length) {

				// If the child priority is lower than the current lowest
				if (queue[lowestPriority] > queue[childIndex + i]) lowestPriority = childIndex + i;
			}
		}

		// If any child node has lower priority, swap and repeat on child
		if (lowestPriority !== priority) {
			queue.swap(lowestPriority, priority);
			return bubbleDown(queue, lowestPriority);  // Will this use optimized tail recursion?
		}
	}



	for (var i = 0; i < arr.length; i++) {
		heap.queue[i + 1] = arr[i];
	}
	heap.length = arr.length;
	// console.log(heap.queue);

	for (let i = Math.floor(heap.queue.length / 2); i >= 1; i--) {
		bubbleDown(heap.queue, i);
	}
	// console.log(heap.queue);

	for (let i = 0; i < arr.length; i++) {
		arr[i] = heap.extractMin();
	}
}

module.exports = heapsort;