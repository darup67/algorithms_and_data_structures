'use strict';

function makeList (arr) {
	let node = null;

	for (let i = arr.length - 1; i >= 0; i--) {
		node = {
			data: arr[i],
			next: node
		};
	}

	return node;
}

function List (listArr=[]) {
	this.length = listArr.length;
	this.head = makeList(listArr);
}

List.prototype = {
	constructor: List,

	toArray: function toArray () {
		let arr = [];
		let node = this.head;

		while (node) {
			arr.push(node.data);
			node = node.next;
		}
		return arr;
	},

	nodeAt: function nodeAt (index) {
		// if (index > this.length - 1 || index < 0) throw 'SinglyList: get(index) out of range';

		let node = this.head;
		for (let i = 0; i < index; i++) { node = node.next; }
		return node;
	},

	indexOf: function indexOf (data) {
		let node = this.head;
		let i = 0;

		while (node) {
			if (node.data === data) return i;
			node = node.next;
			i++;
		}
		return -1;
	},

	insert: function insert (data, index=0) {
		// if (index > this.length || index < 0) throw 'SinglyList: insert(index) out of range';
		this.length++;

		if (index === 0 || index === this.head) {
			return this.head = {
				data: data, 
				next: this.head
			};
		}

		let node = index;
		if (Number.isInteger(index)) {
			node = this.head;
			for (let i = 0; i < index; i++) { node = node.next; }		
		}

		return node.next = {
			data: data, 
			next: node.next
		};
	},

	replace: function replace (data, index) {
		let node = index;

		if (Number.isInteger(index)) {
			node = this.head;
			for (let i = 0; i < index; i++) { node = node.next; }
		}

		node.data = data;
		return node;
	},

	delete: function del (index) {
		// if (index > this.length - 1 || index < 0) throw 'SinglyList: delete(index) out of range';
		let deletedNode;

		if (index === 0 || index === this.head) {
			deletedNode = this.head;
			this.head = this.head.next;

		} else {
			let prevNode = this.head;
			if (Number.isInteger(index)) prevNode = this.nodeAt(index - 1);
			else {
				for (let i = 0; i < this.length; i++) {
					if (prevNode.next.data === index.data) break;
					prevNode = prevNode.next;
				}
			}

			deletedNode = prevNode.next;
			prevNode.next = prevNode.next.next;
		}
		
		deletedNode = null;
		this.length--;
	}
}

module.exports = List;