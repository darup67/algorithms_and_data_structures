'use strict';

const fs = require('fs');
const testArr = [1, 2, 3, 4, 5];

function huffmanMinMax (arr) {
  let nodes       = arr.sort((a,b) => b-a).map(e => { return { weight: e, parent: null } }),
      minPriority = nodes[arr.length - 1],
      maxPriority = nodes[0];

  function countDepth (node, count=0) {
    if (node.parent !== null) return countDepth(node.parent, count + 1);
    return count;
  }

  for (let i = 1; i < arr.length; i++) {
    const left    = nodes.pop(),
          right   = nodes[nodes.length - 1],
          newNode = { weight: left.weight + right.weight, parent: null };

    left.parent = right.parent = newNode;
    nodes[nodes.length - 1] = newNode;
    nodes.sort((a,b) => b.weight - a.weight);
  }

  return {minLength: countDepth(maxPriority), maxLength: countDepth(minPriority)};
}


// console.log(huffmanMinMax(testArr));

fs.readFile('data/huffman.txt', 'utf8', (err, data) => {
  const lines = data.split('\n').slice(1,-1).map(Number);

  console.log(huffmanMinMax(lines));
})
