package class165;

// 团建，java版
// 一共有n个人，编号1~n，一共有k种颜色，编号1~k
// 每个人给定一种颜色，颜色相同的人认为是一个小组，可能有的颜色的组是没人的
// 给定m条边，每条边连接两个人，代表这两人之间有矛盾
// 假设组a和组b，两个组的人一起去团建，组a和组b的所有人，可以重新打乱
// 目的是所有人最多分成两个集团，并且每个集团的内部不存在矛盾，每人都要参加
// 那么组a和组b就叫做一个"合法组对"，注意，组b和组a就不用重复计算了
// 一共有k个组，随意选两个组的情况很多，计算一共有多少个"合法组对"
// 1 <= n、m、k <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1444C
// 测试链接 : https://codeforces.com/problemset/problem/1444/C
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_TeamBuilding1 {

	public static int MAXN = 500001;
	public static int n, m, k;

	public static int[] color = new int[MAXN];
	public static int[][] edge = new int[MAXN][2];
	public static int[][] crossEdge = new int[MAXN][4];
	public static int cnt = 0;

	public static boolean[] conflict = new boolean[MAXN];

	public static int[] father = new int[MAXN << 1];
	public static int[] siz = new int[MAXN << 1];
	public static int[][] rollback = new int[MAXN << 1][2];
	public static int opsize;

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

	public static void filter() {
		for (int i = 1; i <= 2 * n; ++i) {
			father[i] = i;
			siz[i] = 1;
		}
		for (int i = 1, u, v; i <= m; i++) {
			u = edge[i][0];
			v = edge[i][1];
			if (color[u] < color[v]) {
				crossEdge[++cnt][0] = u;
				crossEdge[cnt][1] = color[u];
				crossEdge[cnt][2] = v;
				crossEdge[cnt][3] = color[v];
			} else if (color[u] > color[v]) {
				crossEdge[++cnt][0] = v;
				crossEdge[cnt][1] = color[v];
				crossEdge[cnt][2] = u;
				crossEdge[cnt][3] = color[u];
			} else {
				if (conflict[color[u]]) {
					continue;
				}
				if (find(u) == find(v)) {
					k--;
					conflict[color[u]] = true;
				} else {
					union(u, v + n);
					union(v, u + n);
				}
			}
		}
	}

	public static long compute() {
		Arrays.sort(crossEdge, 1, cnt + 1, (a, b) -> a[1] != b[1] ? (a[1] - b[1]) : (a[3] - b[3]));
		long ans = (long) k * (k - 1) / 2;
		for (int l = 1, r = 1; l <= cnt; l = ++r) {
			while (r + 1 <= cnt && crossEdge[r + 1][1] == crossEdge[l][1] && crossEdge[r + 1][3] == crossEdge[l][3]) {
				r++;
			}
			int u, ucolor, v, vcolor, unionCnt = 0;
			for (int i = l; i <= r; i++) {
				u = crossEdge[i][0];
				ucolor = crossEdge[i][1];
				v = crossEdge[i][2];
				vcolor = crossEdge[i][3];
				if (conflict[ucolor] || conflict[vcolor]) {
					break;
				}
				if (find(u) == find(v)) {
					ans--;
					break;
				} else {
					union(u, v + n);
					union(v, u + n);
					unionCnt += 2;
				}
			}
			for (int i = 1; i <= unionCnt; i++) {
				undo();
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
		in.nextToken();
		k = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			color[i] = (int) in.nval;
		}
		for (int i = 1; i <= m; i++) {
			in.nextToken();
			edge[i][0] = (int) in.nval;
			in.nextToken();
			edge[i][1] = (int) in.nval;
		}
		filter();
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
