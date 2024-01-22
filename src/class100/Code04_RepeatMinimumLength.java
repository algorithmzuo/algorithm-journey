package class100;

// 测试链接 : https://www.luogu.com.cn/problem/P4391

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_RepeatMinimumLength {

	public static int MAXN = 1000001;

	public static int[] next = new int[MAXN];

	public static int n;

	public static char[] s;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = Integer.valueOf(br.readLine());
		s = br.readLine().toCharArray();
		out.println(compute());
		out.flush();
		out.close();
		br.close();
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
