package class166;

// 线段树分治模版题，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5787
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_SegmentTreeDivideAndConquer1 {

	public static int MAXN = 100001;
	public static int MAXM = 200001;
	public static int MAXT = 3000001;
	public static int n, m, k;
	public static int[][] edge = new int[MAXM][2];

	public static int[] father = new int[MAXN << 1];
	public static int[] siz = new int[MAXN << 1];
	public static int[][] rollback = new int[MAXN << 1][2];
	public static int opsize;

	public static int[] head = new int[MAXT];
	public static int[] next = new int[MAXT];
	public static int[] to = new int[MAXT];
	public static int cnt = 0;

	public static boolean[] ans = new boolean[MAXN];

	public static void addQuery(int u, int v) {
		next[++cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt;
	}

	public static int find(int i) {
		while (i != father[i]) {
			i = father[i];
		}
		return i;
	}

	public static void union(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (siz[fx] < siz[fy]) {
			int tmp = fx;
			fx = fy;
			fy = tmp;
		}
		father[fy] = fx;
		siz[fx] += siz[fy];
		rollback[++opsize][0] = fx;
		rollback[opsize][1] = fy;
	}

	public static void undo() {
		int fx = rollback[opsize][0];
		int fy = rollback[opsize--][1];
		father[fy] = fy;
		siz[fx] -= siz[fy];
	}

	public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (l > jobr || r < jobl) {
			return;
		}
		if (jobl <= l && r <= jobr) {
			addQuery(i, jobv);
		} else {
			int mid = (l + r) / 2;
			add(jobl, jobr, jobv, l, mid, i << 1);
			add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
		}
	}

	public static void dfs(int l, int r, int i) {
		boolean check = true;
		int x, y, fx, fy, unionCnt = 0;
		for (int e = head[i]; e > 0; e = next[e]) {
			x = edge[to[e]][0];
			y = edge[to[e]][1];
			fx = find(x);
			fy = find(y);
			if (fx == fy) {
				check = false;
				break;
			} else {
				union(x, y + n);
				union(y, x + n);
				unionCnt += 2;
			}
		}
		if (check) {
			if (l == r) {
				ans[l] = true;
			} else {
				int mid = (l + r) / 2;
				dfs(l, mid, i << 1);
				dfs(mid + 1, r, i << 1 | 1);
			}
		} else {
			for (int k = l; k <= r; k++) {
				ans[k] = false;
			}
		}
		for (int k = 1; k <= unionCnt; k++) {
			undo();
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
		in.nextToken();
		k = (int) in.nval;
		for (int i = 1, l, r; i <= m; i++) {
			in.nextToken();
			edge[i][0] = (int) in.nval;
			in.nextToken();
			edge[i][1] = (int) in.nval;
			in.nextToken();
			l = (int) in.nval + 1;
			in.nextToken();
			r = (int) in.nval;
			add(l, r, i, 1, k, 1);
		}
		for (int i = 1; i <= n * 2; i++) {
			father[i] = i;
			siz[i] = 1;
		}
		dfs(1, k, 1);
		for (int i = 1; i <= k; i++) {
			if (ans[i]) {
				out.println("Yes");
			} else {
				out.println("No");
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
