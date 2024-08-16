package class032;

import javax.swing.plaf.IconUIResource;
import java.util.Arrays;

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
public class Code02_myDesignBitsetTest {

	// 测试链接 : https://leetcode-cn.com/problems/design-bitset/
	public static class Bitset {
		private int[] set;
		private final int size;
		private int zeros;
		private int ones;
		private boolean reverse;

		public Bitset(int n) {
			size = n;
			set = new int[(n + 31)/ 32];
			zeros = n;
			ones = 0;
			reverse = false;
		}

		// 把i这个数字加入到位图
		public void fix(int idx) {
			int index = idx / 32;
			int bit = idx % 32;
			if (!reverse && (set[index] & (1 << bit)) == 0){
			 // 0 - 无效位
			 // 1 - 有效位
			 set[index] |= (1<<bit);
			 ones++;
			 zeros--;
			}else if (reverse &&(set[index] & (1 << bit)) != 0){
				// 0 - 有效位 1 -无效位
			 set[index] ^= (1<<bit);
			 ones++;
			 zeros--;
			}
		}

		// 把i这个数字从位图中移除
		public void unfix(int idx) {
			int index = idx / 32;
			int bit = idx % 32;
			if(!reverse &&(set[index] & (1 << bit)) != 0){
				// 0 - 无效位 1 - 有效位
				set[index] ^= (1<<bit);
				ones--;
				zeros++;
			}else if (reverse && ((set[index] & (1 << bit)) == 0)){
				// 0 - 有效位 1 - 无效位
				set[index] ^= (1<<bit);
				ones--;
				zeros++;
			}
		}

		public void flip() {
			reverse = !reverse;
			zeros = zeros ^ ones;
			ones = zeros ^ ones;
			zeros = zeros ^ ones;
		}

		public boolean all() {
			return ones == size;
		}

		public boolean one() {
			return ones>0;
		}

		public int count() {
			return ones;
		}

		public String toString() {
			StringBuilder str = new StringBuilder();
			char curBit;
			int count = 0;
			for (int i = 0; i < set.length; i++) {
				for (int j = 0; j < 32 && count < size; j++, count++) {
					if(!reverse){
						curBit = (set[i] & (1 << j)) == 0 ? '0' : '1';
					}else{
						curBit = (set[i] & (1 << j)) == 0 ? '1' : '0';
					}
					str.append(curBit);
				}
			}
			return str.toString();
		}

	}

	public static void main(String[] args) {
		Bitset bitset = new Bitset(2);
		bitset.flip();
		bitset.unfix(1);
		System.out.println(bitset.all());
		bitset.fix(1);
		bitset.fix(1);
		bitset.unfix(1);

		System.out.println(bitset.count());
		System.out.println(bitset);
		System.out.println(bitset);
		System.out.println(bitset);



	}

}
