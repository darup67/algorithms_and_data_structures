Array.prototype.swap = function swap (a, b) {
	const temp = this[a];
	this[a] = this[b];
	this[b] = temp;
};

function quicksort (arr, lo=0, hi=arr.length) {
	// If the values 
	if (lo < hi) {
		const pivot = partition(arr, lo, hi);   // Sort current partition and get new pivot value
		quicksort(arr, lo, pivot - 1);          // Sort everything left of pivot
		quicksort(arr, pivot, hi);              // Sort everything right of pivot
	}
}

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
// for (let i = 0; i < 20; i++) {
// 	while (testArr.length === i) {
// 		const val = Math.floor(Math.random() * 50);
// 		if (testArr.indexOf(val) === -1) testArr.push(val);
// 	}
// }

// console.log();
// console.log(testArr);
// quicksort(testArr);
// console.log();
// console.log(testArr);