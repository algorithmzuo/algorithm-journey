package class165;

// 可持久化并查集模版题，java版
// 数字从1到n，一开始每个数字所在的集合只有自己
// 实现如下三种操作，第i条操作发生后，所有数字的状况记为i版本，操作一共发生m次
// 操作 1 x y : 基于上个操作生成的版本，将x的集合与y的集合合并，生成当前的版本
// 操作 2 x   : 拷贝第x号版本的状况，生成当前的版本
// 操作 3 x y : 拷贝上个操作生成的版本，生成当前的版本，查询x和y是否属于一个集合
// 1 <= n <= 10^5
// 1 <= m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3402
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_PersistentUnionFind1 {

	public static int MAXM = 200001;
	public static int MAXT = 8000001;
	public static int n, m;

	// 可持久化线段树
	public static int[] rootfa = new int[MAXM];
	public static int[] rootsiz = new int[MAXM];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] val = new int[MAXT];
	public static int cnt = 0;

	// 建立可持久化的father数组
	public static int buildfa(int l, int r) {
		int rt = ++cnt;
		if (l == r) {
			val[rt] = l;
		} else {
			int mid = (l + r) / 2;
			ls[rt] = buildfa(l, mid);
			rs[rt] = buildfa(mid + 1, r);
		}
		return rt;
	}

	// 建立可持久化的siz数组
	public static int buildsiz(int l, int r) {
		int rt = ++cnt;
		if (l == r) {
			val[rt] = 1;
		} else {
			int mid = (l + r) / 2;
			ls[rt] = buildsiz(l, mid);
			rs[rt] = buildsiz(mid + 1, r);
		}
		return rt;
	}

	// 来自讲解157，题目1，修改数组中一个位置的值，生成新版本的树
	public static int update(int jobi, int jobv, int l, int r, int i) {
		int rt = ++cnt;
		ls[rt] = ls[i];
		rs[rt] = rs[i];
		if (l == r) {
			val[rt] = jobv;
		} else {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				ls[rt] = update(jobi, jobv, l, mid, ls[rt]);
			} else {
				rs[rt] = update(jobi, jobv, mid + 1, r, rs[rt]);
			}
		}
		return rt;
	}

	// 来自讲解157，题目1，查询数组中一个位置的值
	public static int query(int jobi, int l, int r, int i) {
		if (l == r) {
			return val[i];
		}
		int mid = (l + r) / 2;
		if (jobi <= mid) {
			return query(jobi, l, mid, ls[i]);
		} else {
			return query(jobi, mid + 1, r, rs[i]);
		}
	}

	// 基于v版本，查询x的集合头节点，不做扁平化
	public static int find(int x, int v) {
		int fa = query(x, 1, n, rootfa[v]);
		while (x != fa) {
			x = fa;
			fa = query(x, 1, n, rootfa[v]);
		}
		return x;
	}

	// 基于v版本，合并x所在的集合和y所在的集合，生成新版本的树
	public static void union(int x, int y, int v) {
		int fx = find(x, v);
		int fy = find(y, v);
		if (fx != fy) {
			int xsiz = query(fx, 1, n, rootsiz[v]);
			int ysiz = query(fy, 1, n, rootsiz[v]);
			if (xsiz >= ysiz) {
				rootfa[v] = update(fy, fx, 1, n, rootfa[v]);
				rootsiz[v] = update(fx, xsiz + ysiz, 1, n, rootsiz[v]);
			} else {
				rootfa[v] = update(fx, fy, 1, n, rootfa[v]);
				rootsiz[v] = update(fy, xsiz + ysiz, 1, n, rootsiz[v]);
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
		rootfa[0] = buildfa(1, n);
		rootsiz[0] = buildsiz(1, n);
		for (int v = 1, op, x, y; v <= m; v++) {
			in.nextToken();
			op = (int) in.nval;
			rootfa[v] = rootfa[v - 1];
			rootsiz[v] = rootsiz[v - 1];
			if (op == 1) {
				in.nextToken();
				x = (int) in.nval;
				in.nextToken();
				y = (int) in.nval;
				union(x, y, v);
			} else if (op == 2) {
				in.nextToken();
				x = (int) in.nval;
				rootfa[v] = rootfa[x];
				rootsiz[v] = rootsiz[x];
			} else {
				in.nextToken();
				x = (int) in.nval;
				in.nextToken();
				y = (int) in.nval;
				if (find(x, v) == find(y, v)) {
					out.println(1);
				} else {
					out.println(0);
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
