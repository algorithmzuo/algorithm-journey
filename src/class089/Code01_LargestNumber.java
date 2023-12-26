package class089;

import java.util.Arrays;

// 最大数
// 给定一组非负整数nums
// 重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数
// 测试链接 : https://leetcode.cn/problems/largest-number/
public class Code01_LargestNumber {

	public static String largestNumber(int[] nums) {
		int n = nums.length;
		String[] arr = new String[n];
		for (int i = 0; i < n; i++) {
			arr[i] = String.valueOf(nums[i]);
		}
		Arrays.sort(arr, (a, b) -> (b + a).compareTo(a + b));
		if (arr[0].equals("0")) {
			return "0";
		}
		StringBuilder path = new StringBuilder();
		for (String s : arr) {
			path.append(s);
		}
		return path.toString();
	}

}
