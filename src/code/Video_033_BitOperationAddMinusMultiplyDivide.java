package code;

// 不用任何算术运算，只用位运算实现加减乘除
// 代码实现中你找不到任何一个算术运算符
// 测试链接 : https://leetcode.cn/problems/divide-two-integers/
public class Video_033_BitOperationAddMinusMultiplyDivide {

	public static int add(int a, int b) {
		int ans = a;
		while (b != 0) {
			ans = a ^ b;
			b = (a & b) << 1;
			a = ans;
		}
		return ans;
	}

	public static int minus(int a, int b) {
		return add(a, neg(b));
	}

	public static int neg(int n) {
		return add(~n, 1);
	}

	public static int multiply(int a, int b) {
		int ans = 0;
		while (b != 0) {
			if ((b & 1) != 0) {
				ans = add(ans, a);
			}
			a <<= 1;
			b >>>= 1;
		}
		return ans;
	}

	public static int MIN = Integer.MIN_VALUE;

	public static int divide(int a, int b) {
		if (a == MIN && b == MIN) {
			return 1;
		}
		if (a != MIN && b != MIN) {
			return div(a, b);
		}
		if (b == MIN) {
			return 0;
		}
		// a是整数最小，b是-1，根据题目的要求，返回整数最大
		if (b == neg(1)) {
			return Integer.MAX_VALUE;
		}
		// a是整数最小，b不是整数最小也不是-1
		a = add(a, b < 0 ? neg(b) : b);
		int ans = div(a, b);
		int offset = a < 0 ^ b < 0 ? neg(1) : 1;
		return add(ans, offset);
	}

	public static int div(int a, int b) {
		int x = a < 0 ? neg(a) : a;
		int y = b < 0 ? neg(b) : b;
		int ans = 0;
		for (int i = 30; i >= 0; i = minus(i, 1)) {
			if ((x >> i) >= y) {
				ans |= (1 << i);
				x = minus(x, y << i);
			}
		}
		return a < 0 ^ b < 0 ? neg(ans) : ans;
	}

}
