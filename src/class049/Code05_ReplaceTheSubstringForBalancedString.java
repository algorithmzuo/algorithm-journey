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

	// Q W E R
	// 0 1 2 3
	// "W Q Q R R E"
	// 1 0 0 3 3 2
	// cnts[1] = 1;
	// cnts[0] = 2;
	// cnts[2] = 1;
	// cnts[3] = 2;
	public static int balancedString(String str) {
		int n = str.length();
		int[] arr = new int[n];
		int[] cnts = new int[4];
		for (int i = 0; i < n; i++) {
			char c = str.charAt(i);
			arr[i] = c == 'W' ? 1 : (c == 'E' ? 2 : (c == 'R' ? 3 : 0));
			cnts[arr[i]]++;
		}
		// str : 长度是4的整数倍，n
		// 每种字符出现的个数 : n/4
		int require = n / 4;
		// 至少要修改多长的子串，才能做到四种字符一样多
		int ans = n;
		// 自由变化的窗口l....r
		for (int l = 0, r = 0; l < n; l++) {
			// l = 0, r= 0, 窗口0长度
			// l...r-1 : [l,r)
			while (!ok(cnts, r - l, require) && r < n) {
				// cnts : 窗口之外的统计
				cnts[arr[r++]]--;
			}
			// 1) l...r-1 [l,r) ，做到了！
			// 2) r == n，也没做到
			if (ok(cnts, r - l, require)) {
				ans = Math.min(ans, r - l);
			}
			// [l,r)，不被cnts统计到的
			//   l+1
			cnts[arr[l]]++;
		}
		return ans;
	}

	// cnts : l...r范围上的字符不算！在自由变化的窗口之外，每一种字符的词频统计
	// len : 自由变化窗口的长度
	// require : 每一种字符都要达到的数量
	// 返回值 : 请问能不能做到
	public static boolean ok(int[] cnts, int len, int require) {
		for (int i = 0; i < 4; i++) {
			// 0 1 2 3
			if (cnts[i] > require) {
				return false;
			}
			// require - cnts[i] : 20 - 16 = 4
			len -= require - cnts[i];
		}
		return len == 0;
	}

}
