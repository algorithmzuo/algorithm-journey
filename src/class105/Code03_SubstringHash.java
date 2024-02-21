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
	// 比如，p = 499, 也就是课上说的选择的质数进制
	// 再比如字符串s如下
	// " 3 1 2 5 6 ..."
	//   0 1 2 3 4
	// hash[0] = 3 * p的0次方
	// hash[1] = 3 * p的1次方 + 1 * p的0次方
	// hash[2] = 3 * p的2次方 + 1 * p的1次方 + 2 * p的0次方
	// hash[3] = 3 * p的3次方 + 1 * p的2次方 + 2 * p的1次方 + 5 * p的0次方
	// hash[4] = 3 * p的4次方 + 1 * p的3次方 + 2 * p的2次方 + 5 * p的1次方 + 6 * p的0次方
	// 所以hash[i] = hash[i-1] * p + s[i]，就是上面说的意思
	// 此时如果想得到子串"56"的哈希值
	// 子串"56"的哈希值 = hash[4] - hash[2]*p的2次方(就是子串"56"的长度次方)
	// hash[4] = 3 * p的4次方 + 1 * p的3次方 + 2 * p的2次方 + 5 * p的1次方 + 6 * p的0次方
	// hash[2] = 3 * p的2次方 + 1 * p的1次方 + 2 * p的0次方
	// hash[2] * p的2次方 = 3 * p的4次方 + 1 * p的3次方 + 2 * p的2次方
	// 所以hash[4] - hash[2] * p的2次方 = 5 * p的1次方 + 6 * p的0次方
	// 这样就得到子串"56"的哈希值了
	// 所以，子串s[l...r]的哈希值 = hash[r] - hash[l-1] * p的(r-l+1)次方
	// 也就是说，hash[l-1] * p的(r-l+1)次方，正好和hash[r]所代表的信息，前面对齐了
	// 减完之后，正好就是子串s[l...r]的哈希值
	public static int MAXN = 100005;

	public static int base = 499;

	public static long[] pow = new long[MAXN];

	public static long[] hash = new long[MAXN];

	public static void build(char[] s1, int n) {
		pow[0] = 1;
		for (int i = 1; i < n; i++) {
			pow[i] = pow[i - 1] * base;
		}
		hash[0] = s1[0] - 'a' + 1;
		for (int i = 1; i < n; i++) {
			hash[i] = hash[i - 1] * base + s1[i] - 'a' + 1;
		}
	}

	// 返回s[l...r]的哈希值
	public static long hash(int l, int r) {
		long ans = hash[r];
		ans -= l == 0 ? 0 : (hash[l - 1] * pow[r - l + 1]);
		return ans;
	}

}
