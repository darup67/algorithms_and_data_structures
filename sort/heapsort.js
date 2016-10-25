// Future optimization:
// - use faster heap construction algorithm
// - inline parent and child functions
// - early exit for bubbleDown
// - inline swap
// - externalize bubbleDown / bubbleUp (prevent closure creation)?
// - zero-index heap -> create in-place?
// - iterative bubbleUp / bubbleDown?

'use strict';

Array.prototype.swap = function swap (a, b) {
	const temp = this[a];
	this[a] = this[b];
	this[b] = temp;
};

// Returns the index of the parent of the supplied index
function parent (index) {
	return index === 1 ? -1 : Math.floor(index / 2);
}

// Returns the index of the child of the supplied index
function child (index) {
	return 2 * index;
}

function Heap (arr) {
	this.length = 0;
	this.queue = [];

	for (let i = 0; i < arr.length; i++) {
		this.insert(arr[i]);
	}
}

Heap.prototype = {
	constructor: Heap,

	insert: function (data) {
		this.length++;
		this.queue[this.length] = data;
		bubbleUp(this.queue, this.length);

		// Start at leaf node position and bubble up until satisfied
		function bubbleUp (queue, priority) {
			// If queue[priority] is the root, stop bubbling
			if (parent(priority) === -1) return;

			// If parent node has higher priority, swap and repeat on parent
			if (queue[parent(priority)] > queue[priority]) {
				queue.swap(priority, parent(priority));
				bubbleUp(queue, parent(priority));
			}
		};
	},

	findMin: function () {
		return this.queue[1];
	},

	extractMin: function () {
		const min = this.length > 0 ? this.queue[1] : -1;

		this.queue[1] = this.queue[this.length];
		this.length--;
		bubbleDown(this.queue, 1, this.length);

		// Start at root node position and bubble down until satisfied
		function bubbleDown (queue, priority, length) {
			const childIndex = child(priority);
			let lowestPriority = priority;

			// Find lowest priority node (check both possible child nodes)
			for (let i = 0; i <= 1; i++) {

				// If the possible child index is an index in the queue
				if (childIndex + i <= length) {

					// If the child priority is lower than the current lowest
					if (queue[lowestPriority] > queue[childIndex + i]) lowestPriority = childIndex + i;
				}
			}

			// If any child node has lower priority, swap and repeat on child
			if (lowestPriority !== priority) {
				queue.swap(lowestPriority, priority);
				return bubbleDown(queue, lowestPriority, length);  // Will this use optimized tail recursion?
			}
		}

		return min;
	},
};

function heapsort (arr) {
	const heap = new Heap(arr);

	for (let i = 0; i < arr.length; i++) {
		arr[i] = heap.extractMin();
	}
}

module.exports = heapsort;