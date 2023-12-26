package class092;

// 最大回文数字
// 给你一个仅由数字（0 - 9）组成的字符串num
// 请你找出能够使用num中数字形成的最大回文整数
// 并以字符串形式返回，该整数不含前导零
// 注意：
// 你无需使用num中的所有数字，但你必须使用至少一个数字
// 数字可以重新排序
// 测试链接 : https://leetcode.cn/problems/largest-palindromic-number/
public class Code07_LargestPalindromicNumber {

	public static String largestPalindromic(String num) {
		int n = num.length();
		int[] cnts = new int['9' + 1];
		for (char a : num.toCharArray()) {
			cnts[a]++;
		}
		char middle = ' ';
		char[] ans = new char[n];
		ans[0] = '0';
		int len = 0;
		int cnt;
		for (char i = '9'; i >= '0'; i--) {
			if ((cnts[i] & 1) == 1 && middle == ' ') {
				middle = i;
			}
			cnt = cnts[i] /= 2;
			for (int j = cnt; j > 0; j--) {
				ans[len++] = i;
			}
		}
		if (ans[0] == '0') {
			return middle == ' ' ? "0" : "" + middle;
		}
		if (middle != ' ') {
			ans[len++] = middle;
		}
		for (char i = '0'; i <= '9'; i++) {
			for (int j = cnts[i]; j > 0; j--) {
				ans[len++] = i;
			}
		}
		return new String(ans, 0, len);
	}

}
