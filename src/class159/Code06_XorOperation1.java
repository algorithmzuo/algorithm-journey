package class159;

// 异或运算，java版
// 给定一个长度n的数组x，还有一个长度为m的数组y
// 想象一个二维矩阵mat，数组x作为行，数组y作为列，mat[i][j] = x[i] ^ y[j]
// 一共有p条查询，每条查询格式如下
// xl xr yl yr k : 划定mat的范围是，行从xl~xr，列从yl~yr，打印其中第k大的值
// 1 <= n <= 1000
// 1 <= m <= 3 * 10^5
// 1 <= p <= 500
// 0 <= x[i]、y[i] < 2^31
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

	public static int[] root = new int[MAXN];

	public static int[][] tree = new int[MAXT][2];

	public static int[] pass = new int[MAXT];

	public static int cnt = 0;

	public static int[][] xroad = new int[MAXN][2];

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
		// 基于哪两个节点的pass值查询，一开始x[xl...xr]每个数字，都是一样的
		for (int i = xl; i <= xr; i++) {
			xroad[i][0] = root[yl - 1];
			xroad[i][1] = root[yr];
		}
		int ans = 0;
		for (int b = BIT, path, best, sum; b >= 0; b--) {
			sum = 0;
			// 统计x[xl...xr]范围上
			// 每个数字 ^ y[yl...yr]任意一个数字，在第b位上能取得1的结果，有多少个
			// 结果数量累加起来
			for (int i = xl; i <= xr; i++) {
				path = (x[i] >> b) & 1;
				best = path ^ 1;
				sum += pass[tree[xroad[i][1]][best]] - pass[tree[xroad[i][0]][best]];
			}
			// 如果sum >= k
			// 说明x[xl...xr]对应y[yl...yr]，第k大的异或结果，在第b位上能是1
			// 如果sum < k
			// 说明x[xl...xr]对应y[yl...yr]，第k大的异或结果，在第b位上只能是0
			// x[xl...xr]每个数字，都有自己专属的跳转，要记录好！
			for (int i = xl; i <= xr; i++) {
				path = (x[i] >> b) & 1;
				best = path ^ 1;
				if (sum >= k) {
					xroad[i][0] = tree[xroad[i][0]][best];
					xroad[i][1] = tree[xroad[i][1]][best];
				} else {
					xroad[i][0] = tree[xroad[i][0]][path];
					xroad[i][1] = tree[xroad[i][1]][path];
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
		for (int i = 1, yi; i <= m; i++) {
			in.nextToken();
			yi = (int) in.nval;
			root[i] = insert(yi, root[i - 1]);
		}
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
