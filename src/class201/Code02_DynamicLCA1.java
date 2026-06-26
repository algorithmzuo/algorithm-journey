package class201;

// 动态树LCA，java版
// 一共n个点，初始时每个点各自是一棵单点有根树，一共m条操作，操作类型如下
// 操作 link x y : 让点x成为点y的儿子，保证x是所在树的根，并且x和y不连通
// 操作 cut x    : 删除点x和它父亲节点之间的直接边，保证x不是根
// 操作 lca x y  : 查询点x和点y的最低公共祖先，保证x和y在同一棵树中
// 1 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/SP8791
// 测试链接 : https://www.spoj.com/problems/DYNALCA/
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_DynamicLCA1 {

	public static int MAXN = 100001;
	public static int n, m;

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	public static boolean isroot(int x) {
		return ls[fa[x]] != x && rs[fa[x]] != x;
	}

	public static int lr(int x) {
		return ls[fa[x]] == x ? 0 : 1;
	}

	public static void rotate(int x) {
		int f = fa[x], g = fa[f];
		if (lr(x) == 0) {
			ls[f] = rs[x];
			if (ls[f] != 0) {
				fa[ls[f]] = f;
			}
			rs[x] = f;
		} else {
			rs[f] = ls[x];
			if (rs[f] != 0) {
				fa[rs[f]] = f;
			}
			ls[x] = f;
		}
		if (!isroot(f)) {
			if (lr(f) == 0) {
				ls[g] = x;
			} else {
				rs[g] = x;
			}
		}
		fa[f] = x;
		fa[x] = g;
	}

	public static void splay(int x) {
		while (!isroot(x)) {
			int f = fa[x];
			if (!isroot(f)) {
				if (lr(x) == lr(f)) {
					rotate(f);
				} else {
					rotate(x);
				}
			}
			rotate(x);
		}
	}

	// 返回最后一次接上的点
	public static int access(int x) {
		int ans = 0;
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			rs[x] = y;
			ans = x;
		}
		return ans;
	}

	// 让x成为y的孩子，保证x和y不连通
	public static void makeson(int x, int y) {
		access(x);
		splay(x);
		fa[x] = y;
	}

	// 切断x和原树父亲之间的边，保证x不是根
	public static void cutfa(int x) {
		access(x);
		splay(x);
		fa[ls[x]] = 0;
		ls[x] = 0;
	}

	// 第二次的返回值就是lca
	public static int lca(int x, int y) {
		if (x == y) {
			return x;
		}
		access(x);
		return access(y);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, x, y; i <= m; i++) {
			String op = in.nextString();
			if (op.equals("link")) {
				x = in.nextInt();
				y = in.nextInt();
				makeson(x, y);
			} else if (op.equals("cut")) {
				x = in.nextInt();
				cutfa(x);
			} else {
				x = in.nextInt();
				y = in.nextInt();
				out.println(lca(x, y));
			}
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {

		private final byte[] buffer = new byte[1 << 16];
		private int ptr = 0, len = 0;
		private final InputStream in;

		FastReader(InputStream in) {
			this.in = in;
		}

		private int readByte() throws IOException {
			if (ptr >= len) {
				len = in.read(buffer);
				ptr = 0;
				if (len <= 0)
					return -1;
			}
			return buffer[ptr++];
		}

		int nextInt() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			int val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}

		String nextString() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			if (c == -1) {
				return null;
			}
			StringBuilder sb = new StringBuilder();
			while (c > ' ' && c != -1) {
				sb.append((char) c);
				c = readByte();
			}
			return sb.toString();
		}

	}

}
