'use strict';

Array.prototype.swap = function (i, j) {
  const temp = this[i];
  this[i] = this[j];
  this[j] = temp;
}

function mergesort (arr, lo, hi) {
  function partition (lo, hi) {
    if (lo >= hi) return;

    const mid = Math.floor((hi - lo) / 2) + lo;
    partition(lo, mid);
    partition(mid + 1, hi);
    merge(lo, mid, hi);
  }

  function merge (lo, mid, hi) {
    if (lo >= hi || arr[mid] <= arr[mid + 1]) return;

    const buf = arr.slice(lo, mid + 1), bufLen = buf.length;
    let leftIndex = 0, rightIndex = mid + 1;

    for (let i = lo; i <= hi; i++) {
      if      (leftIndex >= bufLen) arr[i] = arr[rightIndex++];
      else if (rightIndex > hi)     arr[i] = buf[leftIndex++];
      else                          arr[i] = arr[rightIndex] < buf[leftIndex] ? arr[rightIndex++] : buf[leftIndex++];
    }
  }

  partition(0, arr.length - 1);
  return arr;
}

const testArr = [5,2,78,5,0,13,6];
console.log(mergesort(testArr));