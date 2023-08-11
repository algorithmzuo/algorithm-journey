package code;

// 位图的实现
// Bitset是一种能以紧凑形式存储位的数据结构
// Bitset(int n) : 初始化n个位，所有位都是0
// void fix(int i) : 将下标i的位上的值更新为1
// void unfix(int i) : 将下标i的位上的值更新为0
// void flip() : 翻转所有位的值
// boolean all() : 是否所有位都是1
// boolean one() : 是否至少有一位是1
// int count() : 返回所有位中1的数量
// String toString() : 返回所有位的状态
public class Video_032_Bitset {

	// 测试链接 : https://leetcode-cn.com/problems/design-bitset/
	class Bitset {
		private int[] bits;
		private final int size;
		private int zeros;
		private int ones;
		private boolean reverse;

		public Bitset(int n) {
			// a/b如果结果想向上取整，可以写成 : (a+b-1)/b
			// 前提是a和b都是非负数
			bits = new int[(n + 31) / 32];
			size = n;
			zeros = n;
			ones = 0;
			reverse = false;
		}

		// 把i位置的状态设置成1
		public void fix(int i) {
			int index = i / 32;
			int bit = i % 32;
			if (!reverse) {
				if ((bits[index] & (1 << bit)) == 0) {
					zeros--;
					ones++;
					bits[index] |= (1 << bit);
				}
			} else {
				if ((bits[index] & (1 << bit)) != 0) {
					zeros--;
					ones++;
					bits[index] ^= (1 << bit);
				}
			}
		}

		// 把i位置的状态设置成0
		public void unfix(int i) {
			int index = i / 32;
			int bit = i % 32;
			if (!reverse) {
				if ((bits[index] & (1 << bit)) != 0) {
					ones--;
					zeros++;
					bits[index] ^= (1 << bit);
				}
			} else {
				if ((bits[index] & (1 << bit)) == 0) {
					ones--;
					zeros++;
					bits[index] |= (1 << bit);
				}
			}
		}

		public void flip() {
			reverse = !reverse;
			int tmp = zeros;
			zeros = ones;
			ones = tmp;
		}

		public boolean all() {
			return ones == size;
		}

		public boolean one() {
			return ones > 0;
		}

		public int count() {
			return ones;
		}

		public String toString() {
			StringBuilder builder = new StringBuilder();
			for (int i = 0, k = 0, number, status; i < size; k++) {
				number = bits[k];
				for (int j = 0; j < 32 && i < size; j++, i++) {
					status = (number >> j) & 1;
					status ^= reverse ? 1 : 0;
					builder.append(status);
				}
			}
			return builder.toString();
		}

	}

}
