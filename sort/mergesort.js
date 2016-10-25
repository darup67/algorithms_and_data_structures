'use strict';

Array.prototype.swap = function swap (a, b) {
	const temp = this[a];
	this[a] = this[b];
	this[b] = temp;
};

// Base case:      subarray size <= 1
// Recursive case: subarray size > 1
function mergesort (arr, lo=0, hi=arr.length-1) {
	if (hi > lo) {
		const mid = Math.floor((hi - lo) / 2) + lo;
		
		mergesort(arr, lo, mid);
		mergesort(arr, mid + 1, hi);

		merge(arr, lo, mid, hi);
	}
}

function merge (arr, lo, mid, hi) {
	// Stores copy of left subarray so it is not overwritten
	const buffer   = arr.slice(lo, mid + 1);

	// Swaps in from left -> right over merged subarray starting at arr[lo]
	// Left subarray in buffer, indexes from 0 to buffer.length - 1
	// Right subarray in arr, indexes from mid + 1 to hi
	let swapPoint  = lo,
	    leftPoint  = 0,
	    rightPoint = mid + 1;

	// For the length of the merged subarray
	while (swapPoint <= hi) {

		// If right subarray is used up, OR (left subarray still has unused values AND comparison picks left value)
		if (rightPoint > hi || (leftPoint < buffer.length) && (buffer[leftPoint] < arr[rightPoint])) {
			arr[swapPoint] = buffer[leftPoint];
			leftPoint++;
		
		} else {
			arr[swapPoint] = arr[rightPoint];
			rightPoint++;
		}

		swapPoint++;
	}
}

module.exports = mergesort;