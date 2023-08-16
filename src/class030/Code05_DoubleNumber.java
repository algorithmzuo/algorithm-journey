package class030;

// 数组中有2种数出现了奇数次，其他的数都出现了偶数次
// 返回这2种出现了奇数次的数
// 测试链接 : https://leetcode.cn/problems/single-number-iii/
public class Code05_DoubleNumber {

	public static int[] singleNumber(int[] nums) {
		int eor1 = 0;
		for (int num : nums) {
			// nums中有2种数a、b出现了奇数次，其他的数都出现了偶数次
			eor1 ^= num;
		}
		// eor1 : a ^ b
		// Brian Kernighan算法
		// 提取出二进制里最右侧的1
		int rightOne = eor1 & (-eor1);
		int eor2 = 0;
		for (int num : nums) {
			if ((num & rightOne) == 0) {
				eor2 ^= num;
			}
		}
		return new int[] { eor2, eor1 ^ eor2 };
	}

}
