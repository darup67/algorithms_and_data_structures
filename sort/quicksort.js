'use strict';

Array.prototype.swap = function swap (a, b) {
	const temp = this[a];
	this[a] = this[b];
	this[b] = temp;
};

// Partition size = hi - lo
// Base case: partition size <= 0
// Recursive case: partition size > 0
function quicksort (arr, lo=0, hi=arr.length) {
	// "Median of three" pivot choice optimization
	// Chooses the pivot index based on the median of the lo, hi, and partition midpoint indexes
	// See https://en.wikipedia.org/wiki/Quicksort#Choice_of_pivot
	const mid = Math.floor((hi - lo) / 2) + lo;
	const median = [lo, mid, hi].sort((a,b) => a-b)[1];
	arr.swap(lo, median);                                      // Swaps the "median" into the pivot position (partition() pivots at lo)


	// ===== Main algorithm =====
	// Sort partition {arr[lo] ... arr[hi]} and get new pivot value
	const pivot = partition(arr, lo, hi);

	// If the left partition size > 0, sort left partition
	if (pivot - 1 > lo)        quicksort(arr, lo, pivot - 1);

	// If the right partition size > 0, sort right partition
	// Void return used for ES6 tail call optimization
	if (hi > pivot)     return quicksort(arr, pivot, hi);
}

// Sorts the partition {arr[lo] ... arr[hi]} and returns the final index of the pivot value
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
// for (let i = 0; i < 100000; i++) {
// 	while (testArr.length === i) {
// 		const val = Math.floor(Math.random() * 99999999);
// 		if (testArr.indexOf(val) === -1) testArr.push(val);
// 	}
// }

// let startTime, diff;
// startTime = process.hrtime();
// diff = process.hrtime(startTime);

// startTime = process.hrtime();
// quicksort(testArr);
// // testArr.sort((a,b) => a-b);
// diff = process.hrtime(startTime);
// console.log(diff);