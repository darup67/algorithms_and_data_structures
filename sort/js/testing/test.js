'use strict';

const quicksort = require('./../quicksort.js'),
      mergesort = require('./../mergesort.js'),
      heapsort  = require('./../heapsort.js');

let testArr = [];
for (let i = 0; i < 10; i++) {
	// while (testArr.length === i) {
	// 	const val = Math.floor(Math.random() * 10000000);
	// 	if (testArr.indexOf(val) === -1) testArr.push(val);
	// }
	testArr.push(Math.floor(Math.random() * 100));
}

console.log(testArr);
heapsort(testArr);
console.log(testArr);

// let startTime, diff;
// startTime = process.hrtime();
// diff = process.hrtime(startTime);

// startTime = process.hrtime();

// // quicksort(testArr);
// // mergesort(testArr);
// heapsort(testArr);
// // testArr.sort((a,b) => a-b);

// diff = process.hrtime(startTime);
// console.log(diff);