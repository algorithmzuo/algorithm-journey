package class165;

// 可持久化并查集，java版
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

	public static int[] rootfa = new int[MAXM];
	public static int[] rootsiz = new int[MAXM];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] val = new int[MAXT];
	public static int cnt = 0;

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

	public static int find(int x, int version) {
		int fa = query(x, 1, n, rootfa[version]);
		if (x == fa) {
			return x;
		}
		return find(fa, version);
	}

	public static void merge(int x, int y, int version) {
		int fx = find(x, version);
		int fy = find(y, version);
		if (fx != fy) {
			int xsiz = query(fx, 1, n, rootsiz[version]);
			int ysiz = query(fy, 1, n, rootsiz[version]);
			if (xsiz >= ysiz) {
				rootfa[version] = update(fy, fx, 1, n, rootfa[version]);
				rootsiz[version] = update(fx, xsiz + ysiz, 1, n, rootsiz[version]);
			} else {
				rootfa[version] = update(fx, fy, 1, n, rootfa[version]);
				rootsiz[version] = update(fy, xsiz + ysiz, 1, n, rootsiz[version]);
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
		for (int version = 1, op, x, y; version <= m; version++) {
			in.nextToken();
			op = (int) in.nval;
			rootfa[version] = rootfa[version - 1];
			rootsiz[version] = rootsiz[version - 1];
			if (op == 1) {
				in.nextToken();
				x = (int) in.nval;
				in.nextToken();
				y = (int) in.nval;
				merge(x, y, version);
			} else if (op == 2) {
				in.nextToken();
				x = (int) in.nval;
				rootfa[version] = rootfa[x];
				rootsiz[version] = rootsiz[x];
			} else {
				in.nextToken();
				x = (int) in.nval;
				in.nextToken();
				y = (int) in.nval;
				if (find(x, version) == find(y, version)) {
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
