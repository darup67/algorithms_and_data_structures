'use strict';

Array.prototype.swap = function swap (a, b) {
	const temp = this[a];
	this[a] = this[b];
	this[b] = temp;
};

function insertion_sort (arr) {
  for (let i = 0; i < arr.length; i++) {
    let j = i;
    while (j > 0 && arr[j-1] > arr[j]) arr.swap(j, --j);
  }
}

let testArr = [];
for (let i = 0; i < 100; i++) {
  testArr.push(Math.floor(Math.random() * 1000));
}
insertion_sort(testArr);
console.log(testArr);