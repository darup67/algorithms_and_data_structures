'use strict';

Array.prototype.swap = function (i, j) {
  const temp = this[i];
  this[i] = this[j];
  this[j] = temp;
}

function qsort (arr, lo=0, hi=arr.length - 1) {
  if (lo >= hi) return;
  
  arr.swap(lo, Math.floor(Math.random() * (hi - lo)) + lo);

  const pivot     = arr[lo];
  let   swapPoint = lo;

  for (let i = lo + 1; i <= hi; i++) {
    if (arr[i] < pivot) arr.swap(i, ++swapPoint);
  }
  arr.swap(lo, swapPoint);

  qsort(arr, lo, swapPoint - 1);
  qsort(arr, swapPoint + 1, hi);

  // return arr;
}

const testArr = [5,2,78,5,0,13,6];
qsort(testArr);
console.log(testArr);