package class105;

// 根据匹配定义求匹配子串的数量
// 给定长为n的字符串s，以及长度为m的字符串p，还有一个正数k
// s'与s匹配的定义为，s'与s长度相同，且最多有k个位置字符不同
// 要求查找字符串s中有多少子串与字符串p匹配
// 测试链接 : https://www.luogu.com.cn/problem/P3763
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_LikeLotusRoot {

	public static int MAXN = 100001;

	public static int base = 499;

	public static long[] pow = new long[MAXN];

	public static long[] hashs = new long[MAXN];

	public static long[] hashp = new long[MAXN];

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = Integer.valueOf(in.readLine());
		for (int i = 0; i < n; i++) {
			String s = in.readLine();
			String p = in.readLine();
			out.println(compute(s.toCharArray(), p.toCharArray(), 3));
		}
		out.flush();
		out.close();
		in.close();
	}

	// s中有多少子串修改最多k个位置的字符就可以变成p
	// s长度为n，p长度为m，时间复杂度O(n * k * logm)
	public static int compute(char[] s, char[] p, int k) {
		int n = s.length;
		int m = p.length;
		if (n < m) {
			return 0;
		}
		build(s, n, p, m);
		int ans = 0;
		for (int i = 0; i <= n - m; i++) {
			// s[i...i+m-1] -> m
			// p[0.....m-1]
			if (check(i, i + m - 1, k)) {
				ans++;
			}
		}
		return ans;
	}

	// s[l1...r1] 和 p[0...m-1] 取等长的两段
	// 返回这两段上字符不一样的位置是不是<=k个
	public static boolean check(int l1, int r1, int k) {
		int diff = 0;
		int l2 = 0;
		while (l1 <= r1 && diff <= k) {
			int l = 1;
			int r = r1 - l1 + 1;
			int m, len = 0;
			while (l <= r) {
				m = (l + r) / 2;
				if (same(l1, l2, m)) {
					len = m;
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			if (l1 + len <= r1) {
				diff++;
			}
			l1 += len + 1;
			l2 += len + 1;
		}
		return diff <= k;
	}

	public static boolean same(int l1, int l2, int len) {
		return hash(hashs, l1, l1 + len - 1) == hash(hashp, l2, l2 + len - 1);
	}

	public static void build(char[] s, int n, char[] p, int m) {
		pow[0] = 1;
		for (int j = 1; j < MAXN; j++) {
			pow[j] = pow[j - 1] * base;
		}
		hashs[0] = s[0] - 'a' + 1;
		for (int j = 1; j < n; j++) {
			hashs[j] = hashs[j - 1] * base + s[j] - 'a' + 1;
		}
		hashp[0] = p[0] - 'a' + 1;
		for (int j = 1; j < m; j++) {
			hashp[j] = hashp[j - 1] * base + p[j] - 'a' + 1;
		}
	}

	public static long hash(long[] hash, int l, int r) {
		long ans = hash[r];
		ans -= l == 0 ? 0 : (hash[l - 1] * pow[r - l + 1]);
		return ans;
	}

}
