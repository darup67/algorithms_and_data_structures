function insertion_sort (arr) {
	// Starts at 2nd element (1st is already sorted)
	for (let i = 1; i < arr.length; i++) {

		// Store the i-th element, before it is overwritten
		const x = arr[i];
		
		// Start each iteration at the element before arr[i]
		let j = i - 1;

		// Iterate through elements in reverse order, until one is found that is less than arr[i]
		while (j >= 0 && arr[j] > x) {
			arr[j + 1] = arr[j];  // Copy arr[j] -> arr[j + 1] (not a swap)
			j--;
		}

		arr[j + 1] = x;  // Assign the original value for arr[i] to its correct insertion point
	}

	return arr;
}

let testArr = [8, 4, 7, 2, 9];
insertion_sort(testArr);
console.log(testArr);