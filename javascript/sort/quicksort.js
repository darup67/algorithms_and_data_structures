// Further optimization:
// - inline swap
// - try pivoting around lo, like older builds: was faster

'use strict';

Array.prototype.swap = function swap (a, b) {
	const temp = this[a];
	this[a] = this[b];
	this[b] = temp;
};

const insertion_sort = require('./insertion_sort.js'),
      MAX_MEDIAN     = 50,  // Seems to perform best unused
      MAX_QSORT      = 20;  // 20 seems to be the sweet spot for arrays of length 1M


// Partition size = hi - lo
// Base case:      partition size <= 0
// Recursive case: partition size > 0
function quicksort (arr, compare, lo=0, hi=arr.length-1) {

	const partSize = hi - lo;

	// Switches to insertion sort for small partitions
	if (partSize <= MAX_QSORT) {
		insertion_sort(arr, compare, lo, hi);


	} else {
		// "Median of three" pivot choice optimization (see https://en.wikipedia.org/wiki/Quicksort#Choice_of_pivot)
		// Chooses the pivot index based on the median of the lo, hi, and partition midpoint values
		// Improves worst-case performance (sorted / near-sorted data)
		// On highly random data or small arrays, can cause performance hit.  Comment out as needed.
		// if (partSize >= MAX_MEDIAN) {
			const mid = Math.floor((hi - lo) / 2) + lo;
			const median =
				(arr[mid] >= arr[lo] && arr[mid] <= arr[hi]) || (arr[mid] >= arr[hi] && arr[mid] <= arr[lo]) ? mid
				 : ((arr[lo] >= arr[mid] && arr[lo] <= arr[hi]) || (arr[lo] >= arr[hi] && arr[lo] <= arr[mid]) ? lo
				 : hi
				);
			arr.swap(hi, median);  // Swaps the "median" into the pivot position (partition() pivots at hi)
		// }

		// ===== Main algorithm =====

		// Sort partition {arr[lo] ... arr[hi]} and get new pivot value
		const pivot = partition(arr, compare, lo, hi);

		// If the left partition size > 1, sort left partition
		if (pivot - 2 > lo)        quicksort(arr, compare, lo, pivot - 1);

		// If the right partition size > 1, sort right partition
		// Void return used for ES6 tail call optimization
		if (hi > pivot + 2) return quicksort(arr, compare, pivot + 1, hi);
	}
}

// Sorts the partition {arr[lo] ... arr[hi]} and returns the final index of the pivot value
function partition (arr, compare=(a,b) => a-b, lo, hi) {
	const pivot = hi;
	let swapPoint = lo;  // Swap-to index, starts at first index after pivot

	// Iterate from pivot - 1 to end of partition
	for (let i = swapPoint; i < pivot; i++) {
		// If the current index value is less/more than the pivot value
		if (compare(arr[pivot], arr[i]) > 0) {  // if (arr[i] < arr[pivot])

			// Swap the current index with the current swap point, then move the swap point forward
			arr.swap(i, swapPoint);
			swapPoint++;
		}
	}

	// Swap the pivot value to its correct final position (at the final swap point)
	arr.swap(pivot, swapPoint);
	return swapPoint;
}

module.exports = quicksort;