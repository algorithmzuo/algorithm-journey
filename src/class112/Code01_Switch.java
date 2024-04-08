package class112;

// 开关
// 现有n盏灯排成一排，从左到右依次编号为1~n，一开始所有的灯都是关着的
// 操作分两种
// 操作 0 l r : 改变l~r范围上所有灯的状态，开着的灯关上、关着的灯打开
// 操作 1 l r : 查询l~r范围上有多少灯是打开的
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

public class Code01_Switch {

	public static int MAXN = 100001;

	public static int[] light = new int[MAXN << 2];

	public static boolean[] reverse = new boolean[MAXN << 2];

	public static void up(int i) {
		light[i] = light[i << 1] + light[i << 1 | 1];
	}

	public static void down(int i, int ln, int rn) {
		if (reverse[i]) {
			lazy(i << 1, ln);
			lazy(i << 1 | 1, rn);
			reverse[i] = false;
		}
	}

	public static void lazy(int i, int n) {
		light[i] = n - light[i];
		reverse[i] = !reverse[i];
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			light[i] = 0;
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
		reverse[i] = false;
	}

	public static void reverse(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, r - l + 1);
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				reverse(jobl, jobr, l, mid, i << 1);
			}
			if (jobr > mid) {
				reverse(jobl, jobr, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return light[i];
		}
		int mid = (l + r) >> 1;
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
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			jobl = (int) in.nval;
			in.nextToken();
			jobr = (int) in.nval;
			if (op == 0) {
				reverse(jobl, jobr, 1, n, 1);
			} else {
				out.println(query(jobl, jobr, 1, n, 1));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
