package class109;

// HH的项链
// 一共有n个位置，每个位置颜色给定，i位置的颜色是arr[i]
// 一共有m个查询，question[i] = {li, ri}
// 表示第i条查询想查arr[li..ri]范围上一共有多少种不同颜色
// 返回每条查询的答案
// 1 <= n、m、arr[i] <= 10^6
// 1 <= li <= ri <= n
// 测试链接 : https://www.luogu.com.cn/problem/P1972
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_DifferentColors {

	public static int MAXN = 1000010;

	public static int[] arr = new int[MAXN];

	public static int[][] query = new int[MAXN][3];

	public static int[] ans = new int[MAXN];

	public static int[] map = new int[MAXN];

	public static int[] tree = new int[MAXN];

	public static int n, m;

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		while (i <= n) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static int sum(int i) {
		int ans = 0;
		while (i > 0) {
			ans += tree[i];
			i -= lowbit(i);
		}
		return ans;
	}

	public static int range(int l, int r) {
		return sum(r) - sum(l - 1);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= m; i++) {
			in.nextToken();
			query[i][0] = (int) in.nval;
			in.nextToken();
			query[i][1] = (int) in.nval;
			query[i][2] = i;
		}
		compute();
		for (int i = 1; i <= m; i++) {
			out.println(ans[i]);
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void compute() {
		Arrays.sort(query, 1, m + 1, (a, b) -> a[1] - b[1]);
		for (int s = 1, q = 1, l, r, i; q <= m; q++) {
			r = query[q][1];
			for (; s <= r; s++) {
				int color = arr[s];
				if (map[color] != 0) {
					add(map[color], -1);
				}
				add(s, 1);
				map[color] = s;
			}
			l = query[q][0];
			i = query[q][2];
			ans[i] = range(l, r);
		}
	}

}
