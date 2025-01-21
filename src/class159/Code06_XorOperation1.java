package class159;

// 异或运算，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5795
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code06_XorOperation1 {

	public static int MAXN = 300001;

	public static int MAXT = MAXN * 32;

	public static int BIT = 30;

	public static int n, m, p;

	public static int[] x = new int[MAXN];

	public static int[] y = new int[MAXN];

	public static int[] root = new int[MAXN];

	public static int[][] tree = new int[MAXT][2];

	public static int[] pass = new int[MAXT];

	public static int cnt = 0;

	public static int[][] rtpath = new int[MAXN][2];

	public static int insert(int num, int i) {
		int rt = ++cnt;
		tree[rt][0] = tree[i][0];
		tree[rt][1] = tree[i][1];
		pass[rt] = pass[i] + 1;
		for (int b = BIT, path, pre = rt, cur; b >= 0; b--, pre = cur) {
			path = (num >> b) & 1;
			i = tree[i][path];
			cur = ++cnt;
			tree[cur][0] = tree[i][0];
			tree[cur][1] = tree[i][1];
			pass[cur] = pass[i] + 1;
			tree[pre][path] = cur;
		}
		return rt;
	}

	public static int maxKth(int xl, int xr, int yl, int yr, int k) {
		for (int i = xl; i <= xr; i++) {
			rtpath[i][0] = root[yl - 1];
			rtpath[i][1] = root[yr];
		}
		int ans = 0;
		for (int b = BIT, path, best, sum; b >= 0; b--) {
			sum = 0;
			for (int i = xl; i <= xr; i++) {
				path = (x[i] >> b) & 1;
				best = path ^ 1;
				sum += pass[tree[rtpath[i][1]][best]] - pass[tree[rtpath[i][0]][best]];
			}
			for (int i = xl; i <= xr; i++) {
				path = (x[i] >> b) & 1;
				best = path ^ 1;
				if (sum >= k) {
					rtpath[i][0] = tree[rtpath[i][0]][best];
					rtpath[i][1] = tree[rtpath[i][1]][best];
				} else {
					rtpath[i][0] = tree[rtpath[i][0]][path];
					rtpath[i][1] = tree[rtpath[i][1]][path];
				}
			}
			if (sum >= k) {
				ans += 1 << b;
			} else {
				k -= sum;
			}
		}
		return ans;
	}

	public static void prepare() {
		for (int i = 1; i <= m; i++) {
			root[i] = insert(y[i], root[i - 1]);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			x[i] = (int) in.nval;
		}
		for (int i = 1; i <= m; i++) {
			in.nextToken();
			y[i] = (int) in.nval;
		}
		prepare();
		in.nextToken();
		p = (int) in.nval;
		for (int i = 1, xl, xr, yl, yr, k; i <= p; i++) {
			in.nextToken();
			xl = (int) in.nval;
			in.nextToken();
			xr = (int) in.nval;
			in.nextToken();
			yl = (int) in.nval;
			in.nextToken();
			yr = (int) in.nval;
			in.nextToken();
			k = (int) in.nval;
			out.println(maxKth(xl, xr, yl, yr, k));
		}
		out.flush();
		out.close();
		br.close();
	}

}
