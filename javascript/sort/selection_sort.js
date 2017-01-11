'use strict';

Array.prototype.swap = function swap (a, b) {
	const temp = this[a];
	this[a] = this[b];
	this[b] = temp;
};

function selection_sort (arr) {
  for (let i = 0; i < arr.length; i++) {
    let min = i;
    for (let j = i + 1; j < arr.length; j++) {
      if (arr[j] < arr[min]) min = j;
    }
    arr.swap(i, min);
  }
}

let testArr = [];
for (let i = 0; i < 100; i++) {
  testArr.push(Math.floor(Math.random() * 1000));
}
selection_sort(testArr);
console.log(testArr);