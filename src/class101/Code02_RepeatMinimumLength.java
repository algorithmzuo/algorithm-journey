package class101;

// 最短重复串的长度
// 给你一个字符串s1，它是由某个字符串s2不断自我连接形成的
// 题目保证至少重复2次，但是字符串s2是不确定的
// 现在只想知道它的最短长度是多少
// 测试链接 : https://www.luogu.com.cn/problem/P4391
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_RepeatMinimumLength {

	public static int MAXN = 1000001;

	public static int[] next = new int[MAXN];

	public static int n;

	public static char[] s;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = Integer.valueOf(in.readLine());
		s = in.readLine().toCharArray();
		out.println(compute());
		out.flush();
		out.close();
		in.close();
	}

	public static int compute() {
		nextArray();
		return n - next[n];
	}

	public static void nextArray() {
		next[0] = -1;
		next[1] = 0;
		int i = 2, cn = 0;
		while (i <= n) {
			if (s[i - 1] == s[cn]) {
				next[i++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[i++] = 0;
			}
		}
	}

}
