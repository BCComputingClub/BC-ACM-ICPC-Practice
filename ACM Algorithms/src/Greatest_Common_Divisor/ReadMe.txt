Greatest Common Denominator Algorithm

Page # 933 in Introduction to Algorithms, 3rd ed. by Cormen, et.al,

Problem:
Find the Greatest Common Denominator of two numbers (a,b), where a and b are > 0

Restrictions/Edge Cases:
a >= 0
b >= 0
a and b must be integers

Summary:
We use Euclids theorem that states
gcd(a,b) = gcd(b, a mod b)
See proof of this theorem in the text

Algorithm
GCD(a,b)
if b==0
	return a
else
	return GCD(b, a mod b)

Example Problem:
Find the GCD of 30 and 21
Using the formula shown above, we see
gcd(30,21) = gcd(21, 9) //30 mod 21 = 9
gcd(21,9) = gcd(9,3) //21 mod 9 = 3
gcd(9,3) = gcd(3, 0) //9 mod 3 = 0
gcd = 3 