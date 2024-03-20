package class111;

// 贪婪大陆
// 一共有n个格子，编号1~n，一开始所有格子上都没有地雷
// 实现以下两种操作，一共调用m次
// 操作 1 l r : 在l~r范围的格子上放置一种新型地雷，每次放置的地雷和之前的地雷都不一样
// 操作 2 l r : 查询l~r范围的格子上一共放置过多少种不同的地雷
// 操作 1 并不是替换，而是新增，注意如下的例子
// 执行 1 3 6，表示3~6范围上新加了一种地雷，假设地雷类型是A
// 执行 1 3 4，表示3~4范围上又新加了一种地雷，和上次新加的地雷不同，假设地雷类型是B
// 此时3~6范围上 : 
// 格子3有两种地雷(A、B)
// 格子4有两种地雷(A、B)
// 格子5有一种地雷(A)
// 格子6有一种地雷(A)
// 执行 2 4 5，表示查询4~5范围上一共放置过多少种不同的地雷，答案返回2
// 执行 2 5 6，表示查询5~6范围上一共放置过多少种不同的地雷，答案返回1
// 1 <= n、m <= 10^5
// 1 <= l <= r <= n
// 测试链接 : https://www.luogu.com.cn/problem/P2184
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_Bombs {

	public static int MAXN = 100001;

	public static int[] left = new int[MAXN << 2];

	public static int[] right = new int[MAXN << 2];

	public static void up(int i) {
		left[i] = left[i << 1] + left[i << 1 | 1];
		right[i] = right[i << 1] + right[i << 1 | 1];
	}

	public static void build(int l, int r, int i) {
		if (l < r) {
			int mid = (l + r) / 2;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
		}
		left[i] = 0;
		right[i] = 0;
	}

	public static void add(int jobt, int jobi, int l, int r, int i) {
		if (l == r) {
			if (jobt == 0) {
				left[i]++;
			} else {
				right[i]++;
			}
		} else {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				add(jobt, jobi, l, mid, i << 1);
			} else {
				add(jobt, jobi, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static int query(int jobt, int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return jobt == 0 ? left[i] : right[i];
		} else {
			int mid = (l + r) / 2;
			int ans = 0;
			if (jobl <= mid) {
				ans += query(jobt, jobl, jobr, l, mid, i << 1);
			}
			if (jobr > mid) {
				ans += query(jobt, jobl, jobr, mid + 1, r, i << 1 | 1);
			}
			return ans;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken(); int n = (int) in.nval;
		in.nextToken(); int m = (int) in.nval;
		build(1, n, 1);
		for (int i = 1, op, jobl, jobr; i <= m; i++) {
			in.nextToken(); op = (int) in.nval;
			in.nextToken(); jobl = (int) in.nval;
			in.nextToken(); jobr = (int) in.nval;
			if (op == 1) {
				add(0, jobl, 1, n, 1);
				add(1, jobr, 1, n, 1);
			} else {
				int starts = query(0, 1, jobr, 1, n, 1);
				int ends = jobl == 1 ? 0 : query(1, 1, jobl - 1, 1, n, 1);
				out.println(starts - ends);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
