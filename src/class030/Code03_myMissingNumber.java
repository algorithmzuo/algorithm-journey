package class030;

// 找到缺失的数字
// 测试链接 : https://leetcode.cn/problems/missing-number/
public class Code03_myMissingNumber {

	public static int missingNumber(int[] nums) {
		// 1- get xor for all
		// 2- get xor for current
		// 3- xor-missing = xor-all ^ xor-current
		int xorAll = 0, xorCurrent = 0;
		for (int i = 0; i < nums.length; i++) {
			xorAll ^= i;
			xorCurrent ^= nums[i];
		}
		xorAll ^= nums.length;
		return xorAll ^ xorCurrent;
	}

}
