package class111;

// 开关
// 现有n盏灯排成一排，从左到右依次编号为1~n，一开始所有的灯都是关着的
// 操作分两种
// 操作0 : 指定一个区间[l,r]，在这个区间内把开着的灯关上、关着的灯打开
// 操作1 : 指定一个区间[l,r]，查询有多少灯是打开的
// 测试链接 : https://www.luogu.com.cn/problem/P3870
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_Switch {

	public static int MAXN = 100001;

	public static int[] sum = new int[MAXN << 2];

	public static boolean[] reverse = new boolean[MAXN << 2];

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = 0;
		} else {
			int mid = (l + r) / 2;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
		reverse[i] = false;
	}

	public static void up(int i) {
		sum[i] = sum[i << 1] + sum[i << 1 | 1];
	}

	public static void down(int i, int ln, int rn) {
		if (reverse[i]) {
			sum[i << 1] = ln - sum[i << 1];
			sum[i << 1 | 1] = rn - sum[i << 1 | 1];
			reverse[i << 1] = !reverse[i << 1];
			reverse[i << 1 | 1] = !reverse[i << 1 | 1];
			reverse[i] = false;
		}
	}

	public static void change(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			sum[i] = (r - l + 1) - sum[i];
			reverse[i] = !reverse[i];
		} else {
			int mid = (l + r) / 2;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				change(jobl, jobr, l, mid, i << 1);
			}
			if (jobr > mid) {
				change(jobl, jobr, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		int mid = (l + r) / 2;
		down(i, mid - l + 1, r - mid);
		int ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, i << 1);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
		}
		return ans;
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
			if (op == 0) {
				change(jobl, jobr, 1, n, 1);
			} else {
				out.println(query(jobl, jobr, 1, n, 1));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
