let arr = [];
while (arr.length < 15) {
	let rand = Math.floor(Math.random() * 100);
	if (arr.indexOf(rand) === -1) arr.push(rand);
}

let str = '[ ';
for (let i=0; i < arr.length; i++) {
	str += i < 10 && arr[i] > 9 ? ` ${i}` : i;
	if (i < arr.length - 1) str += i > 9 && arr[i] < 10 ? ',' : ', ';
	else str += ' ]';
}

console.log(str);
console.log(arr);
console.log('');

let i = 0;
arr = arr.map(a => [a, i++]).sort((a,b) => a[0] - b[0])
console.log(arr);