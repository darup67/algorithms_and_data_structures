const bigInt   = require('big-integer'),
      MIN_ACBD = 8,
      MIN_ADBC = 8;
[, , input1, input2, cheat, debug] = process.argv;

function pad (num) {
	let str = '';
	for (let i = 0; i < num; i++) {
		str += '0';
	}
	return str;
}

function karatsuba (num1, num2, debug) {
	let temp;
	if (bigInt(num1).lt(num2)) {
		temp = num1;
		num1 = num2;
		num2 = temp;
	}

	num1 = num1.length % 2 != 0 ? '0' + num1 : num1;
	num2 = pad(num1.length - num2.length) + num2;

	if (debug) {
		console.log('===== begin =====')
		console.log('num1, num2:', num1, num2);
		console.log();
	}

	const a = num1.substring(0, num1.length / 2) || '0',
	      b = num1.substring(num1.length / 2, num1.length),
	      c = num2.substring(0, num1.length / 2) || '0',
	      d = num2.substring(num1.length / 2, num1.length);

	if (debug) {
		console.log('a, b, c, d:', a, b, c, d);
		console.log();
	}

	const ac = (a.length < MIN_ACBD && c.length < MIN_ACBD) ? (+a * +c).toString() : karatsuba(a, c, debug),
	      bd = (b.length < MIN_ACBD && d.length < MIN_ACBD) ? (+b * +d).toString() : karatsuba(b, d, debug);

	if (debug) {
		console.log('ac, bd:', ac, bd);
		console.log();
	}
	      
	const a_plus_b   = bigInt(a).add(b).toString(),
	      c_plus_d   = bigInt(c).add(d).toString(),
	      gaussSum   = (a_plus_b.length < MIN_ADBC && c_plus_d.length < MIN_ADBC) ? (+a_plus_b * +c_plus_d).toString() : karatsuba(a_plus_b, c_plus_d, debug),
	      ad_plus_bc = bigInt(gaussSum).subtract(ac).subtract(bd).toString();

	if (debug) {
		console.log('a_plus_b, c_plus_d, gaussSum, ad_plus_bc:', a_plus_b, c_plus_d, gaussSum, ad_plus_bc);
		console.log();
	}

	const result = bigInt(ac + pad(num1.length)).add(ad_plus_bc + pad(Math.ceil(num1.length / 2))).add(bd).toString();
	if (debug) {
		console.log('result:', result);
		console.log('=====  end  =====');
		console.log();
	}
	return result;
}

if (cheat) console.log(bigInt(input1).times(input2).toString())
else {
	const result = karatsuba(input1.toString(), input2.toString(), debug);
	console.log(result);
	console.log('Verified:', bigInt(input1).times(input2).toString() === result);
}