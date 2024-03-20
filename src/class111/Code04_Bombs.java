package class111;

// 贪婪大陆
// 一共有n个格子，编号1~n，一开始所有格子上都没有地雷
// 实现以下两种操作，一共调用m次
// 操作 1 l r : 在l~r范围的格子上放置同一种地雷，该种地雷和之前放置的地雷都不一样
// 操作 2 l r : 查询l~r范围的格子上一共有几种不同地雷
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

public class Code04_Bombs {

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

	public static void update(int jobt, int jobi, int l, int r, int i) {
		if (l == r) {
			if (jobt == 0) {
				left[i]++;
			} else {
				right[i]++;
			}
		} else {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				update(jobt, jobi, l, mid, i << 1);
			} else {
				update(jobt, jobi, mid + 1, r, i << 1 | 1);
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
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		build(1, n, 1);
		for (int i = 1, op, jobl, jobr; i <= m; i++) {
			in.nextToken(); op = (int) in.nval;
			in.nextToken(); jobl = (int) in.nval;
			in.nextToken(); jobr = (int) in.nval;
			if (op == 1) {
				update(0, jobl, 1, n, 1);
				update(1, jobr, 1, n, 1);
			} else {
				int a = query(0, 1, jobr, 1, n, 1);
				int b = query(1, 1, jobl - 1, 1, n, 1);
				out.println(a - b);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
