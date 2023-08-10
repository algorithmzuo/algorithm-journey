package code;

// 找到缺失的数字
// 测试链接 : https://leetcode.cn/problems/missing-number/
public class Video_030_3_MissingNumber {

	public static int missingNumber(int[] nums) {
		int eorAll = 0, eorHas = 0;
		for (int i = 0; i < nums.length; i++) {
			eorAll ^= i;
			eorHas ^= nums[i];
		}
		eorAll ^= nums.length;
		return eorAll ^ eorHas;
	}

}
