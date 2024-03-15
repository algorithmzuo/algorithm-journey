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

public class Code02_Switch {

	public static int MAXN = 100001;

	public static int[] sum = new int[MAXN << 2];

	public static boolean[] reverse = new boolean[MAXN << 2];

	public static void build(int l, int r, int rt) {
		if (l == r) {
			sum[rt] = 0;
		} else {
			int mid = (l + r) / 2;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
			up(rt);
		}
		reverse[rt] = false;
	}

	public static void up(int rt) {
		sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
	}

	public static void down(int rt, int ln, int rn) {
		if (reverse[rt]) {
			sum[rt << 1] = ln - sum[rt << 1];
			sum[rt << 1 | 1] = rn - sum[rt << 1 | 1];
			reverse[rt << 1] = !reverse[rt << 1];
			reverse[rt << 1 | 1] = !reverse[rt << 1 | 1];
			reverse[rt] = false;
		}
	}

	public static void change(int jobl, int jobr, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			sum[rt] = (r - l + 1) - sum[rt];
			reverse[rt] = !reverse[rt];
		} else {
			int mid = (l + r) / 2;
			down(rt, mid - l + 1, r - mid);
			if (jobl <= mid) {
				change(jobl, jobr, l, mid, rt << 1);
			}
			if (jobr > mid) {
				change(jobl, jobr, mid + 1, r, rt << 1 | 1);
			}
			up(rt);
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			return sum[rt];
		}
		int mid = (l + r) / 2;
		down(rt, mid - l + 1, r - mid);
		int ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, rt << 1);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, rt << 1 | 1);
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			build(1, n, 1);
			for (int i = 1, op, jobl, jobr; i <= m; i++) {
				in.nextToken();
				op = (int) in.nval;
				in.nextToken();
				jobl = (int) in.nval;
				in.nextToken();
				jobr = (int) in.nval;
				if (op == 0) {
					change(jobl, jobr, 1, n, 1);
				} else {
					out.println(query(jobl, jobr, 1, n, 1));
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
