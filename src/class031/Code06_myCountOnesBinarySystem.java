package class031;

// 返回n的二进制中有几个1
// 两个整数之间的 汉明距离 指的是这两个数字对应二进制位不同的位置的数目。
// 给你两个整数 x 和 y，计算并返回它们之间的汉明距离
// 测试链接 : https://leetcode.cn/problems/hamming-distance/
public class Code06_myCountOnesBinarySystem {

	public static void hammingDistance(int x, int y) {
		System.out.println(cntOnes1(x ^y));
		System.out.println(cntOnes2(x ^y));
	}

	// 返回n的二进制中有几个1
	// 这个实现脑洞太大了
	public static int cntOnes1(int n) {
		n = (n & 0x55555555) + ((n >>> 1) & 0x55555555);
		n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
		n = (n & 0x0f0f0f0f) + ((n >>> 4) & 0x0f0f0f0f);
		n = (n & 0x00ff00ff) + ((n >>> 8) & 0x00ff00ff);
		n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff);
		return n;
	}

	public static int cntOnes2(int n) {
		int ans = 0 ;
		while(n > 0){
			n -= n & (-n);
			ans++;
		}
		return ans;
	}

	public static void main(String[] args) {
		hammingDistance(1,4);
	}

}
