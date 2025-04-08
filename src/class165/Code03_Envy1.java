package class165;

// 同在最小生成树里，java版
// 一共有n个点，m条无向边，每条边有边权
// 一共有q次查询，每条查询都给定参数k，表示该查询涉及k条边
// 然后依次给出k条边的编号，打印这k条边能否同时出现在一颗最小生成树上
// 1 <= n、m、q、所有查询涉及边的总量 <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF891C
// 测试链接 : https://codeforces.com/problemset/problem/891/C
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_Envy1 {

	public static int MAXN = 500001;
	public static int n, m, q, k;

	// 节点u、节点v、边权w
	public static int[][] edge = new int[MAXN][3];
	// 节点u、节点v、边权w、问题编号i
	public static int[][] queryEdge = new int[MAXN][4];

	// 可撤销并查集
	public static int[] father = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[][] rollback = new int[MAXN << 1][2];
	public static int opsize;

	// 答案数组
	public static boolean[] ans = new boolean[MAXN];

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

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			siz[i] = 1;
		}
		Arrays.sort(edge, 1, m + 1, (a, b) -> a[2] - b[2]);
		Arrays.sort(queryEdge, 1, k + 1, (a, b) -> a[2] != b[2] ? (a[2] - b[2]) : (a[3] - b[3]));
		Arrays.fill(ans, 1, q + 1, true);
	}

	public static void compute() {
		int ei = 1;
		for (int l = 1, r = 1; l <= k; l = ++r) {
			for (; ei <= m && edge[ei][2] < queryEdge[l][2]; ei++) {
				if (find(edge[ei][0]) != find(edge[ei][1])) {
					union(edge[ei][0], edge[ei][1]);
				}
			}
			while (r + 1 <= k && queryEdge[l][2] == queryEdge[r + 1][2] && queryEdge[l][3] == queryEdge[r + 1][3]) {
				r++;
			}
			int unionCnt = 0;
			for (int i = l; i <= r; i++) {
				if (find(queryEdge[i][0]) == find(queryEdge[i][1])) {
					ans[queryEdge[i][3]] = false;
					break;
				} else {
					union(queryEdge[i][0], queryEdge[i][1]);
					unionCnt++;
				}
			}
			for (int i = 1; i <= unionCnt; i++) {
				undo();
			}
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
		for (int i = 1; i <= m; i++) {
			in.nextToken();
			edge[i][0] = (int) in.nval;
			in.nextToken();
			edge[i][1] = (int) in.nval;
			in.nextToken();
			edge[i][2] = (int) in.nval;
		}
		in.nextToken();
		q = (int) in.nval;
		k = 0;
		for (int i = 1, s; i <= q; i++) {
			in.nextToken();
			s = (int) in.nval;
			for (int j = 1, ei; j <= s; j++) {
				in.nextToken();
				ei = (int) in.nval;
				queryEdge[++k][0] = edge[ei][0];
				queryEdge[k][1] = edge[ei][1];
				queryEdge[k][2] = edge[ei][2];
				queryEdge[k][3] = i;
			}
		}
		prepare();
		compute();
		for (int i = 1; i <= q; i++) {
			if (ans[i]) {
				out.println("YES");
			} else {
				out.println("NO");
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
