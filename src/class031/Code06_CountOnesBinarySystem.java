package class031;

// 返回n的二进制中有几个1
// 两个整数之间的 汉明距离 指的是这两个数字对应二进制位不同的位置的数目。
// 给你两个整数 x 和 y，计算并返回它们之间的汉明距离
// 测试链接 : https://leetcode.cn/problems/hamming-distance/
public class Code06_CountOnesBinarySystem {

	public static int hammingDistance(int x, int y) {
		return cntOnes(x ^ y);
	}

	// 返回n的二进制中有几个1
	// 这个实现脑洞太大了
	public static int cntOnes(int n) {
        //todo 这个有点神奇，解析下。首先n是一个数，需要先看成二进制的很多个1，
        // 然后二进制的1保留右侧的值，然后加上左侧的值，变成计数的值。
        // 然后计数的值不断从右侧累加，变成实际的统计值
        // 比如 10&01变成00，然后加上10》1&01=01；00+01=01；统计值就是1；
		n = (n & 0x55555555) + ((n >>> 1) & 0x55555555);
		n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
		n = (n & 0x0f0f0f0f) + ((n >>> 4) & 0x0f0f0f0f);
		n = (n & 0x00ff00ff) + ((n >>> 8) & 0x00ff00ff);
		n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff);
		return n;
	}

}
