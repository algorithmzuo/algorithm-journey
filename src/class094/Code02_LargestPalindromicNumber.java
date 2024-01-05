package class094;

// 最大回文数字
// 给你一个仅由数字（0 - 9）组成的字符串num
// 请你找出能够使用num中数字形成的最大回文整数
// 并以字符串形式返回，该整数不含前导零
// 你无需使用num中的所有数字，但你必须使用至少一个数字，数字可以重新排列
// 测试链接 : https://leetcode.cn/problems/largest-palindromic-number/
public class Code02_LargestPalindromicNumber {

	public static String largestPalindromic(String num) {
		int n = num.length();
		// '0' ~ '9'字符对应整数为48 ~ 57
		int[] cnts = new int[58];
		for (char a : num.toCharArray()) {
			cnts[a]++;
		}
		char[] ans = new char[n];
		int leftSize = 0;
		char mid = 0;
		for (char i = '9'; i >= '1'; i--) {
			if ((cnts[i] & 1) == 1 && mid == 0) {
				mid = i;
			}
			for (int j = cnts[i] / 2; j > 0; j--) {
				ans[leftSize++] = i;
			}
		}
		if (leftSize == 0) { // '1'~'9'每一种字符出现次数 <= 1
			if (mid == 0) { // '1'~'9'每一种字符出现次数 == 0
				return "0";
			} else { // '1'~'9'有若干字符出现次数 == 1，其中最大的字符是mid
				return String.valueOf(mid);
			}
		}
		// '1'~'9'有若干字符出现次数 >= 2，左部分已经建立，再考虑把'0'字符填进来
		for (int j = cnts['0'] / 2; j > 0; j--) {
			ans[leftSize++] = '0';
		}
		int len = leftSize;
		// 把中点安插进去
		if (mid == 0 && (cnts['0'] & 1) == 1) {
			mid = '0';
		}
		if (mid != 0) {
			ans[len++] = mid;
		}
		// 左部分逆序拷贝给右部分
		for (int i = leftSize - 1; i >= 0; i--) {
			ans[len++] = ans[i];
		}
		return new String(ans, 0, len);
	}

}
