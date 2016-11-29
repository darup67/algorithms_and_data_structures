'use strict';

// Base case:      subarray size <= 1
// Recursive case: subarray size > 1
function mergesort (arr, compare=(a,b)=>a-b, lo=0, hi=arr.length-1) {
	const mid = Math.floor((hi - lo) / 2) + lo;

	if (mid > lo) mergesort(arr, compare, lo, mid);
	if (hi > mid + 1) mergesort(arr, compare, mid + 1, hi);

	merge(arr, compare, lo, mid, hi);
}

function merge (arr, compare, lo, mid, hi) {
	// Stores copy of left subarray so it is not overwritten
	const buffer   = arr.slice(lo, mid + 1);

	// Swaps in from left -> right over merged subarray starting at arr[lo]
	// Left subarray in buffer, indexes from 0 to buffer.length - 1
	// Right subarray in arr, indexes from mid + 1 to hi
	let swapPoint  = lo,
	    leftPoint  = 0,
	    rightPoint = mid + 1;

	// For the length of the merged subarray
	for (; swapPoint <= hi; swapPoint++) {

		// If right subarray is used up, OR (left subarray still has unused values AND comparison picks left value)
		if (rightPoint > hi || (leftPoint < buffer.length && compare(buffer[leftPoint], arr[rightPoint]) < 0)) {
			arr[swapPoint] = buffer[leftPoint];
			leftPoint++;
		
		} else {
			arr[swapPoint] = arr[rightPoint];
			rightPoint++;
		}
	}
}

module.exports = mergesort;