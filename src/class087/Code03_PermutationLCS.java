package class087;

// 两个排列的最长公共子序列长度
// 给出由1~n这些数字组成的两个排列
// 求它们的最长公共子序列长度
// n <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P1439
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_PermutationLCS {

	public static int MAXN = 100001;

	public static int[] a = new int[MAXN];

	public static int[] b = new int[MAXN];

	public static int[] where = new int[MAXN];

	public static int[] ends = new int[MAXN];

	public static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			for (int i = 0; i < n; i++) {
				in.nextToken();
				a[i] = (int) in.nval;
			}
			for (int i = 0; i < n; i++) {
				in.nextToken();
				b[i] = (int) in.nval;
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		for (int i = 0; i < n; i++) {
			where[a[i]] = i;
		}
		for (int i = 0; i < n; i++) {
			b[i] = where[b[i]];
		}
		return lis();
	}

	// 讲解072 - 最长递增子序列及其扩展
	public static int lis() {
		int len = 0;
		for (int i = 0, find; i < n; i++) {
			find = bs(len, b[i]);
			if (find == -1) {
				ends[len++] = b[i];
			} else {
				ends[find] = b[i];
			}
		}
		return len;
	}

	public static int bs(int len, int num) {
		int l = 0, r = len - 1, m, ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (ends[m] >= num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

}
