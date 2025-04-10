package class165;

// 团建，java版
// 一共有n个人，每个人给定一种颜色，一共有m条边，每条边连接两个人，代表这两人之间有矛盾
// 一共有k种颜色，颜色相同的人在一个小组，一种颜色代表一个组，可能有的组没人，但组是存在的
// 假设组a和组b，两个组的人一起去团建，组a和组b的所有人，可以重新打乱
// 如果所有人最多分成两个集团，每人都要参加划分，并且每个集团的内部不存在矛盾
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

	// 每个节点的颜色
	public static int[] color = new int[MAXN];
	// 每条边有两个端点
	public static int[][] edge = new int[MAXN][2];

	// 两个端点为不同颜色的边，u、ucolor、v、vcolor
	public static int[][] crossEdge = new int[MAXN][4];
	// 两个端点为不同颜色的边的数量
	public static int cnt = 0;

	// conflict[i] = true，表示颜色为i的组，组内即便是二分图，也无法调和矛盾
	// conflict[i] = false，表示颜色为i的组，组内构成二分图，可以调和矛盾
	public static boolean[] conflict = new boolean[MAXN];

	// 可撤销并查集
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
		int u, ucolor, v, vcolor, unionCnt;
		for (int l = 1, r = 1; l <= cnt; l = ++r) {
			ucolor = crossEdge[l][1];
			vcolor = crossEdge[l][3];
			while (r + 1 <= cnt && crossEdge[r + 1][1] == ucolor && crossEdge[r + 1][3] == vcolor) {
				r++;
			}
			if (conflict[ucolor] || conflict[vcolor]) {
				continue;
			}
			unionCnt = 0;
			for (int i = l; i <= r; i++) {
				u = crossEdge[i][0];
				v = crossEdge[i][2];
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
