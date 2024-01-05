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
		int[] cnts = new int['9' + 1];
		for (char a : num.toCharArray()) {
			cnts[a]++;
		}
		char[] ans = new char[n];
		int len = 0;
		char bestmid = 0;
		for (char i = '9'; i >= '0'; i--) {
			if ((cnts[i] & 1) == 1 && bestmid == 0) {
				bestmid = i;
			}
			for (int j = cnts[i] / 2; j > 0; j--) {
				ans[len++] = i;
			}
		}
		// len == 0 : 说明任何字符最多出现1次
		// ans[0] == '0' : 说明只有'0'出现次数>=2次，其他字符最多出现1次
		if (len == 0 || ans[0] == '0') {
			if (bestmid == 0) {
				// 到这里一定说明
				// num中只有'0'且出现了偶数次(包括0次)，其他字符都没有出现
				return "0";
			} else {
				// 到这里一定说明
				// num中除了'0'之外，其他字符最多出现了1次
				// 所有出现过的字符中，最大的字符一定是bestmid
				return String.valueOf(bestmid);
			}
		}
		if (bestmid != 0) {
			ans[len++] = bestmid;
		}
		// 左部分逆序拷贝给右部分
		for (int i = bestmid != 0 ? (len - 2) : (len - 1); i >= 0; i--) {
			ans[len++] = ans[i];
		}
		return new String(ans, 0, len);
	}

}
