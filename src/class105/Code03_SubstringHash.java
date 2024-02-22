package class105;

// 字符串哈希得到子串哈希
// 利用字符串哈希的便利性替代KMP算法
// 测试链接 : https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/
public class Code03_SubstringHash {

	public static int strStr(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		build(s1, n);
		long h2 = s2[0] - 'a' + 1;
		for (int i = 1; i < m; i++) {
			h2 = h2 * base + s2[i] - 'a' + 1;
		}
		for (int l = 0, r = m - 1; r < n; l++, r++) {
			if (hash(l, r) == h2) {
				return l;
			}
		}
		return -1;
	}

	// 如下代码是字符串哈希的原理和模版
	// 比如，base = 499, 也就是课上说的选择的质数进制
	// 再比如字符串s如下
	// " c a b e f "
	//   0 1 2 3 4
	// hash[0] = 3 * base的0次方
	// hash[1] = 3 * base的1次方 + 1 * base的0次方
	// hash[2] = 3 * base的2次方 + 1 * base的1次方 + 2 * base的0次方
	// hash[3] = 3 * base的3次方 + 1 * base的2次方 + 2 * base的1次方 + 5 * base的0次方
	// hash[4] = 3 * base的4次方 + 1 * base的3次方 + 2 * base的2次方 + 5 * base的1次方 + 6 *
	// base的0次方
	// hash[i] = hash[i-1] * base + s[i] - 'a' + 1，就是上面说的意思
	// 想计算子串"be"的哈希值 -> 2 * base的1次方 + 5 * base的0次方
	// 子串"be"的哈希值 = hash[3] - hash[1] * base的2次方(子串"be"的长度次方)
	// hash[1] = 3 * base的1次方 + 1 * base的0次方
	// hash[1] * base的2次方 = 3 * base的3次方 + 1 * base的2次方
	// hash[3] = 3 * base的3次方 + 1 * base的2次方 + 2 * base的1次方 + 5 * base的0次方
	// hash[3] - hash[1] * base的2次方 = 2 * base的1次方 + 5 * base的0次方
	// 这样就得到子串"be"的哈希值了
	// 子串s[l...r]的哈希值 = hash[r] - hash[l-1] * base的(r-l+1)次方，就是上面说的意思
	public static int MAXN = 100005;

	public static int base = 499;

	public static long[] pow = new long[MAXN];

	public static long[] hash = new long[MAXN];

	public static void build(char[] s, int n) {
		pow[0] = 1;
		for (int i = 1; i < n; i++) {
			pow[i] = pow[i - 1] * base;
		}
		hash[0] = s[0] - 'a' + 1;
		for (int i = 1; i < n; i++) {
			hash[i] = hash[i - 1] * base + s[i] - 'a' + 1;
		}
	}

	// 返回s[l...r]的哈希值
	public static long hash(int l, int r) {
		long ans = hash[r];
		if (l > 0) {
			ans -= hash[l - 1] * pow[r - l + 1];
		}
		return ans;
	}

}
