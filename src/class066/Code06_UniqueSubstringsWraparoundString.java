package class066;

// 环绕字符串中唯一的子字符串
// 定义字符串 base 为一个 "abcdefghijklmnopqrstuvwxyz" 无限环绕的字符串
// 所以 base 看起来是这样的：
// "..zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd.."
// 给你一个字符串 s ，请你统计并返回 s 中有多少 不同非空子串 也在 base 中出现
// 测试链接 : https://leetcode.cn/problems/unique-substrings-in-wraparound-string/
public class Code06_UniqueSubstringsWraparoundString {

	public static int findSubstringInWraproundString(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		int[] dp = new int[256];
		dp[s[0]] = 1;
		int len = 1;
		for (int i = 1; i < n; i++) {
			char cur = s[i];
			char pre = s[i - 1];
			if ((pre == 'z' && cur == 'a') || pre + 1 == cur) {
				len++;
			} else {
				len = 1;
			}
			dp[cur] = Math.max(dp[cur], len);
		}
		int ans = 0;
		for (int i = 0; i < 256; i++) {
			ans += dp[i];
		}
		return ans;
	}

}
