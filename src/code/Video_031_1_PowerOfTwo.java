package code;

// Brian Kernighan算法
// 提取出二进制里最右侧的1
// 判断一个整数是不是2的幂
// 测试链接 : https://leetcode.cn/problems/power-of-two/
public class Video_031_1_PowerOfTwo {

	public static boolean isPowerOfTwo(int n) {
		return n > 0 && n == (n & -n);
	}

}
