'use strict';

Array.prototype.swap = function swap (a, b) {
	const temp = this[a];
	this[a] = this[b];
	this[b] = temp;
};

function quicksort (arr, lo=0, hi=arr.length) {
	// Partition size = hi - lo
	// Base case: partition size <= 0
	// Recursive case: partition size > 0
	if (hi > lo) {

		// "Median of three" pivot choice optimization
		// Chooses the pivot index based on the median of the lo, hi, and partition midpoint indexes
		// See https://en.wikipedia.org/wiki/Quicksort#Choice_of_pivot
		const mid = Math.floor((hi - lo) / 2) + lo;
		const median = [lo, mid, hi].sort((a,b) => a-b)[1];
		arr.swap(lo, median);                   // Since partition() always pivots at lo, this swaps the "median" into the pivot position

		// Main algorithm
		const pivot = partition(arr, lo, hi);   // Sort partition {arr[lo] ... arr[hi]} and get new pivot value
		quicksort(arr, lo, pivot - 1);          // Sort left partition (everything left of pivot)
		return quicksort(arr, pivot, hi);       // Sort right partition - void return for ES6 tail call optimization
	}
}

// Sorts the partition of arr bounded by (lo, hi) and returns the final index of the pivot value
function partition (arr, lo, hi) {
	const pivot = lo;
	let swapPoint = lo + 1;  // Swap-to index, starts at first index after pivot

	// Iterate from pivot - 1 to end of partition
	for (let i = swapPoint; i < hi; i++) {
		// If the current index value is less than the pivot value
		if (arr[i] < arr[pivot]) {
			// Swap the current index with the current swap point, then move the swap point forward
			arr.swap(i, swapPoint);
			swapPoint++;
		}
	}

	// Swap the pivot value to its correct final position
	arr.swap(pivot, swapPoint - 1);
	return swapPoint;
}

// let testArr = [];
// for (let i = 0; i < 10000; i++) {
// 	while (testArr.length === i) {
// 		const val = Math.floor(Math.random() * 999999);
// 		if (testArr.indexOf(val) === -1) testArr.push(val);
// 	}
// }

// quicksort(testArr);
// console.log(testArr);