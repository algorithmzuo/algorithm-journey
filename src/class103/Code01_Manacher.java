package class103;

// Manacher算法(模版)
// 求字符串s中最长回文子串的长度
// 测试链接 : https://www.luogu.com.cn/problem/P3805
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_Manacher {

	public static int MAXN = 11000001;

	public static char[] s = new char[MAXN << 1];

	public static int[] p = new int[MAXN << 1];

	public static int n;

	public static int manacher(String str) {
		manacherss(str.toCharArray());
		int max = 0;
		for (int i = 0, c = -1, r = -1; i < n; i++) {
			p[i] = r > i ? Math.min(p[2 * c - i], r - i) : 1;
			while (i + p[i] < n && i - p[i] >= 0 && s[i + p[i]] == s[i - p[i]]) {
				p[i]++;
			}
			if (i + p[i] > r) {
				r = i + p[i];
				c = i;
			}
			max = Math.max(max, p[i]);
		}
		return max - 1;
	}

	public static void manacherss(char[] a) {
		n = a.length * 2 + 1;
		for (int i = 0, j = 0; i < n; i++) {
			s[i] = (i & 1) == 0 ? '#' : a[j++];
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		out.println(manacher(in.readLine()));
		out.flush();
		out.close();
		in.close();
	}

}
