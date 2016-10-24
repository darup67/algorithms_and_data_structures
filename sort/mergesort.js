'use strict';

Array.prototype.swap = function swap (a, b) {
	const temp = this[a];
	this[a] = this[b];
	this[b] = temp;
};

// Base case:      subarray size <= 1
// Recursive case: subarray size > 1
function mergesort (arr) {
	if (arr.length > 1) {
		const midpoint = Math.floor(arr.length / 2);
		return merge(mergesort(arr.slice(0, midpoint)), mergesort(arr.slice(midpoint, arr.length)));

	} else return arr;
}

function merge (arr1, arr2) {
	let result = [];
	while (arr1.length > 0 || arr2.length > 0) {
		result.push( arr1[0] < arr2[0] || arr2.length === 0 ? arr1.shift() : arr2.shift() );
	}

	arr1 = arr2 = null;

	return result;
}

module.exports = mergesort;