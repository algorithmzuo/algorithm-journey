package class104;

// 前k名奇数长度回文子串的长度乘积
// 给定一个字符串s和数值k，只关心所有奇数长度的回文子串
// 返回其中长度前k名的回文子串长度的乘积是多少
// 如果奇数长度的回文子串个数不够k个，返回-1
// 测试链接 : https://www.luogu.com.cn/problem/P1659
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_TopkOddLengthProduct {

	public static int MOD = 19930726;

	public static int MAXN = 10000001;

	public static String[] mk;

	public static int m, n;

	public static long k;

	public static char[] ss = new char[MAXN << 1];

	public static int[] p = new int[MAXN << 1];

	public static int[] cnt = new int[MAXN];

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		mk = in.readLine().split(" ");
		m = Integer.valueOf(mk[0]);
		k = Long.valueOf(mk[1]);
		manacher(in.readLine());
		out.println(compute());
		out.flush();
		out.close();
		in.close();
	}

	public static int manacher(String str) {
		manacherss(str.toCharArray());
		int max = 0;
		for (int i = 0, c = -1, r = -1, len; i < n; i++) {
			len = r > i ? Math.min(p[2 * c - i], r - i) : 1;
			while (i + len < n && i - len >= 0 && ss[i + len] == ss[i - len]) {
				len++;
			}
			if (i + len > r) {
				r = i + len;
				c = i;
			}
			max = Math.max(max, len);
			p[i] = len;
		}
		return max - 1;
	}

	public static void manacherss(char[] a) {
		n = a.length * 2 + 1;
		for (int i = 0, j = 0; i < n; i++) {
			ss[i] = (i & 1) == 0 ? '#' : a[j++];
		}
	}

	public static int compute() {
		for (int i = 1; i < n; i += 2) {
			cnt[p[i] - 1]++;
		}
		long ans = 1;
		long sum = 0;
		for (int len = (m & 1) == 1 ? m : (m - 1); len >= 0 && k >= 0; len -= 2) {
			sum += cnt[len];
			ans = (ans * power(len, Math.min(k, sum))) % MOD;
			k -= sum;
		}
		return k < 0 ? (int) ans : -1;
	}

	public static long power(long x, long n) {
		long ans = 1;
		while (n > 0) {
			if ((n & 1) == 1) {
				ans = (ans * x) % MOD;
			}
			x = (x * x) % MOD;
			n >>= 1;
		}
		return ans;
	}

}
