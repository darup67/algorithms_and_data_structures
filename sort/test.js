'use strict';

const quicksort = require('./quicksort.js'),
      mergesort = require('./mergesort.js');

let testArr = [];
for (let i = 0; i < 10000; i++) {
	// while (testArr.length === i) {
	// 	const val = Math.floor(Math.random() * 10000000);
	// 	if (testArr.indexOf(val) === -1) testArr.push(val);
	// }
	testArr.push(Math.floor(Math.random() * 10000000));
}

// console.log(testArr);
// console.log(mergesort(testArr));

let startTime, diff;
startTime = process.hrtime();
diff = process.hrtime(startTime);

startTime = process.hrtime();
mergesort(testArr);
// testArr.sort((a,b) => a-b);
diff = process.hrtime(startTime);
console.log(diff);
// console.log(testArr);