package class101;

// 不停删除之后剩下的字符串
// 给定一个字符串s1，如果其中含有s2字符串，就删除最左出现的那个
// 删除之后s1剩下的字符重新拼接在一起，再删除最左出现的那个
// 如此周而复始，返回最终剩下的字符串
// 测试链接 : https://www.luogu.com.cn/problem/P4824
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_DeleteAgainAndAgain {

	public static char[] s1, s2;

	public static int MAXN = 1000001;

	public static int[] next = new int[MAXN];

	public static int[] stack1 = new int[MAXN];

	public static int[] stack2 = new int[MAXN];

	public static int size;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		s1 = in.readLine().toCharArray();
		s2 = in.readLine().toCharArray();
		compute();
		for (int i = 0; i < size; i++) {
			out.print(s1[stack1[i]]);
		}
		out.println();
		out.flush();
		out.close();
		in.close();
	}

	public static void compute() {
		size = 0;
		int n = s1.length, m = s2.length, x = 0, y = 0;
		nextArray(m);
		while (x < n) {
			if (s1[x] == s2[y]) {
				stack1[size] = x;
				stack2[size] = y;
				size++;
				x++;
				y++;
			} else if (y == 0) {
				stack1[size] = x;
				stack2[size] = -1;
				size++;
				x++;
			} else {
				y = next[y];
			}
			if (y == m) {
				// 相当于栈直接弹出了m条记录
				size -= m;
				y = size > 0 ? (stack2[size - 1] + 1) : 0;
			}
		}
	}

	public static void nextArray(int m) {
		next[0] = -1;
		next[1] = 0;
		int i = 2, cn = 0;
		while (i < m) {
			if (s2[i - 1] == s2[cn]) {
				next[i++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[i++] = 0;
			}
		}
	}

}
