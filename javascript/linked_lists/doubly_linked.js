'use strict';

function makeList (arr, tail) {
	let node = tail;

	for (let i = arr.length - 2; i >= 0; i--) {
		node = {
			data: arr[i],
			next: node,
		};
		node.next.prev = node;
	}

	node.prev = null;
	return node;
}

function List (listArr=[]) {
	if (listArr.length > 0) {
		this.tail = {
			data: listArr[listArr.length - 1],
			next: null
		}
		this.head = makeList(listArr, this.tail);
	}

	this.length = listArr.length;
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
		let node;

		if (index < this.length / 2) {
			node = this.head;
			for (let i = 0; i < index; i++) { node = node.next; }

		} else {
			node = this.tail;
			for (let i = this.length - 1; i > index; i--) { node = node.prev }
		}

		return node;
	},

	indexOf: function indexOf (data) {
		if (this.tail.data === data) return this.length - 1;

		let node = this.head;
		let i = 0;

		while (node) {
			if (node.data === data) return i;
			node = node.next;
			i++;
		}
		return -1;
	},

	insert: function insert (data, index=this.length) {
		this.length++;

		if (this.length === 0) {
			return this.head = this.tail = {
				data: data,
				next: null,
				prev: null
			}

		} else if (index === 0) {
			this.head = {
				data: data, 
				next: this.head,
				prev: null
			};
			return this.head.next.prev = this.head;
			
		} else if (index === this.length) {
			this.tail = {
				data: data,
				next: null,
				prev: this.tail
			};
			return this.tail.prev.next = this.tail;

		} else if (index < this.length / 2) {
			let node = this.head;
			let nodeAfter = node.next;

			for (let i = 0; i < index - 1; i++) {
				node = node.next;
				nodeAfter = nodeAfter.next;
			}
			
			return node.next = nodeAfter.prev = {
				data: data, 
				next: node.next,
				prev: node
			};

		} else {
			let node = this.tail;
			let nodeBefore = node.prev;

			for (let i = this.length - 1; i > index + 1; i--) {
				node = node.prev;
				nodeBefore = nodeBefore.prev;
			}

			return node.prev = nodeBefore.next = {
				data: data, 
				next: node,
				prev: node.prev
			};
		}
	},

	replace: function replace (data, index) {
		let node;

		if (index < this.length / 2) {
			node = this.head;
			for (let i = 0; i < index; i++) { node = node.next; }

		} else {
			node = this.tail;
			for (let i = this.length - 1; i > index; i--) { node = node.prev }
		}

		node.data = data;
		return node;
	},

	delete: function del (index) {
		let deletedNode;

		if (this.length === 1) {
			this.head = this.tail = null;
		}

		else if (index === this.length - 1) {
			deletedNode = this.tail;
			this.tail = this.tail.prev;
			this.tail.next = null;

		} else if (index === 0) {
			deletedNode = this.head;
			this.head = this.head.next;
			this.head.prev = null;

		} else if (index < this.length / 2) {
			let node = this.head;
			let nodeAfter = node.next;

			for (let i = 0; i < index - 1; i++) { node = node.next; }
			
			deletedNode = node.next;
			node.next.next.prev = node;
			node.next = node.next.next;

		} else {
			let node = this.tail;
			let nodeBefore = node.prev;

			for (let i = this.length - 1; i > index + 1; i--) { node = node.prev; }

			deletedNode = node.prev;
			node.prev.prev.next = node;
			node.prev = node.prev.prev;
		}
		
		deletedNode = null;
		this.length--;
	}
}

module.exports = List;