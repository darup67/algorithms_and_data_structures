Array.prototype.withValue = function withValue (data, index=this.length) {
	return this.slice(0, index).concat(data, this.slice(index));
}

Array.prototype.insert = function insert (data, index=this.length) {
	for (var i = this.length; i > index; i--) { this[i] = this[i-1]; }
	this[i] = data;
}

// const runs = 100000;

// let startTime;
// let diff = [];

// let arr = [];
// for (let i = 0; i < runs; i++) {
// 	arr.push(i);
// }
// let list = new List();

// console.log(`\n${runs} list insertions`)
// console.log(list.length);

// startTime = process.hrtime();
// diff = process.hrtime(startTime);

// startTime = process.hrtime();
// for (let i = 0; i < runs; i++) {
// 	let rand = Math.floor(Math.random() * (list.length + 1));
// 	list.insert(i, i);
// }
// diff = process.hrtime(startTime);
// console.log(list.length);
// console.log(diff);
// console.log(list.toArray());
// console.log(list.head);
// console.log(list.tail);

// let list = new List([0,1,2,3,4,5,6,7,8,9]);
// console.log(list.insert('X', 3));
// list.replace('X', 7);
// console.log(list.toArray());

// const runs = 100000;

// let startTime;
// let diff = [];

// let arr = [];
// for (let i = 0; i < runs; i++) {
// 	arr.push(i);
// }
// let list = new List();

// console.log(`\n${runs} list insertions`)
// console.log(list.length);

// startTime = process.hrtime();
// diff = process.hrtime(startTime);

// startTime = process.hrtime();
// for (let i = 0; i < runs; i++) {
// 	let rand = Math.floor(Math.random() * (list.length + 1));
// 	list.insert(i, i);
// }
// diff = process.hrtime(startTime);
// console.log(list.length);
// console.log(diff);
// console.log(list.toArray());
// console.log(list.head);

// startTime = process.hrtime();
// for (let i = 0; i < runs; i++) {
// 	let rand = Math.floor(Math.random() * arr.length);
// 	arr.insert(rand, 1);
// }
// diff = process.hrtime(startTime);
// console.log(diff);
// console.log(arr.length);

const List = require('../singly_linked.js');

let list = new List([0,1,2,3,4,5,6,7,8,9]);
list.delete(list.nodeAt(0));
console.log(list.toArray());
// console.log(list.nodeAt(3));