package class049;

// 替换子串得到平衡字符串
// 有一个只含有 'Q', 'W', 'E', 'R' 四种字符，且长度为 n 的字符串。
// 假如在该字符串中，这四个字符都恰好出现 n/4 次，那么它就是一个「平衡字符串」。
// 给你一个这样的字符串 s，请通过「替换一个子串」的方式，使原字符串 s 变成一个「平衡字符串」。
// 你可以用和「待替换子串」长度相同的 任何 其他字符串来完成替换。
// 请返回待替换子串的最小可能长度。
// 如果原字符串自身就是一个平衡字符串，则返回 0。
// 测试链接 : https://leetcode.cn/problems/replace-the-substring-for-balanced-string/
public class Code05_ReplaceTheSubstringForBalancedString {

	public static int balancedString(String str) {
		int n = str.length();
		int[] s = new int[n];
		int[] cnts = new int[4];
		for (int i = 0; i < n; i++) {
			char c = str.charAt(i);
			s[i] = c == 'W' ? 1 : (c == 'E' ? 2 : (c == 'R' ? 3 : 0));
			cnts[s[i]]++;
		}
		int debt = 0;
		for (int i = 0; i < 4; i++) {
			if (cnts[i] < n / 4) {
				cnts[i] = 0;
			} else {
				cnts[i] = n / 4 - cnts[i];
				debt -= cnts[i];
			}
		}
		if (debt == 0) {
			return 0;
		}
		int ans = Integer.MAX_VALUE;
		for (int l = 0, r = 0; r < n; r++) {
			if (cnts[s[r]]++ < 0) {
				debt--;
			}
			if (debt == 0) {
				while (cnts[s[l]] > 0) {
					cnts[s[l++]]--;
				}
				ans = Math.min(ans, r - l + 1);
			}
		}
		return ans;
	}

}
