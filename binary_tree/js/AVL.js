'use strict';

function Node (data, left=null, right=null) {
	this.data = data;
	this.left = left;
	this.right = right;
}

function bTree () {
	this.root = null;
	this.length = 0;
}

bTree.prototype = {
	constructor: bTree,

	insert: function insert (data) {
		this.length++;

		if (!this.root) {
			return this.root = new Node(data);
		}

		let node = this.root;

		while (true) {
			if (data <= node.data) { 
				if (node.left) node = node.left;
				else { node.left = new Node(data); break; }
			
			} else {
				if (node.right) node = node.right;
				else { node.right = new Node(data); break; }
			}
		}
	},

	search: function search (data) {
		let node = this.root;

		while (true) {
			if (data === node.data) return node;

			else if (data < node.data) { 
				if (node.left) node = node.left;
				else return -1;
			
			} else {
				if (node.right) node = node.right;
				else return -1;
			}
		}
	},

	toArray: function toArray () {
		let dataArr = [];

		function copy (node) {
			if (node.left) copy(node.left);
			dataArr.push(node.data);
			if (node.right) copy(node.right);
		}

		copy(this.root);
		return dataArr;
	}
}

let tree = new bTree;
tree.insert(100);
//
tree.insert(50);
tree.insert(150);
//
tree.insert(120);
tree.insert(30);
tree.insert(180);
tree.insert(70);
//
tree.insert(20);
tree.insert(40);
tree.insert(60);
tree.insert(80);
tree.insert(110);
tree.insert(130);
tree.insert(160);
tree.insert(666);
//
// tree.insert(15);
// tree.insert(25);
// tree.insert(35);
// tree.insert(45);
// tree.insert(55);
// tree.insert(65);
// tree.insert(75);
// tree.insert(85);
// tree.insert(105);
// tree.insert(115);
// tree.insert(125);
// tree.insert(135);
// tree.insert(155);
// tree.insert(165);
// tree.insert(420);
// tree.insert(999);

// console.log(tree.root);
// console.log();

console.log(tree.toArray());
console.log();

// console.log(tree.search(110));

function convert (node, parentNode) {
	// if (!node) return;

	// console.log(node);
	// console.log();

	function hasGrandchildren () {
		// if (node.left.left || node.left.right || node.right.left || node.right.right) return true;

		if (node.left) {
			if (node.left.left || node.left.right) return true;
		}
		if (node.right) {
			if (node.right.left || node.right.right) return true;
		}
		return false;
	}

	if (!hasGrandchildren()) {
		let head;

		// Remove all (empty) grandchild references
		delete node.left.left;
		delete node.left.right;
		delete node.right.left;
		delete node.right.right;

		// Add next references for children
		node.left.next = node;
		node.next = node.right;
		node.right.next = null;

		// If this node is not the root
		if (parentNode) {

			// If this node is the left child of the parent
			if (parentNode.left === node) {

				// Change the link from parent.left -> node, to head of this list -> parent
				node.right.next = parentNode;
				delete parentNode.left;

				// If this node is the left child of the parent, then return the head of this list
				head = node.left;

			// If this node is the right child of the parent
			} else {

				// Change the link from parent.right -> node, to parent.next -> head of this list
				parentNode.next = node.left;
				delete parentNode.right;
			}

		// If this node is the root, return the head of this list as the head of the result list
		}	else {
			head = node.left;
		}

		delete node.left;
		delete node.right;

		return head;
	}

	let head = convert(node.left, node);
	let tail = convert(node.right, node);

	// If this node is the grandparent of two parents of all subtrees
	// Tail will be defined iff the right subtree was the parent of two subtrees
	if (tail) {
		// Iterate the left sub-list until we get to the end
		let temp = head;
		while (temp.next) temp = temp.next;

		// In this context node = parent of two lists (or, the root)
		// We want to link the left sub-list to node, then node to the right sub-list
		temp.next = node;
		node.next = tail;

		delete node.left;
		delete node.right;

		return head;
	}

	// If this node is not the parent of two subtrees
	// Head will be defined iff this node is the left child of its parent, or is the root
	if (head) return head;
}

function reverseConvert (tree) {
	let temp = {};
	let length = 0;
	let head;

	function listify (node) {
		if (node.left) listify(node.left);

		temp.next = node;

		if (length === 0) head = temp.next;

		delete node.left;
		delete node.right;

		length++;

		if (node === tree.root) node.next = null;

		temp = temp.next;

		if (node.right) listify(node.right);
	}

	listify(tree.root);

	let list = {
		head: head,
		length: length
	};

	// delete tree.root.left;
	// delete tree.root.right;
	// delete tree.root;

	return list;
}

function simpleConvert (tree) {
	const list = {
		head: null,
		length: tree.length
	};
	let currentNode = null;

	function makeNewListNode (node) {
		const newNode = {
			data: node.data,
			next: null
		};

		if (!currentNode) {
			list.head = currentNode = newNode;
		} else {
			currentNode.next = newNode;
			currentNode = currentNode.next;
		}
	}

	function traverseTree (node) {
		if (node.left) traverseTree(node.left);
		makeNewListNode(node);
		if (node.right) traverseTree(node.right);
	}

	traverseTree(tree.root);

	return list;
}

// let list = convert(tree.root);
let list = simpleConvert(tree);

console.log();
console.log(list);

let temp = list.head;
while (temp) console.log(temp = temp.next);