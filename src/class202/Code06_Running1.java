package class202;

// 长跑，java版
// 一共n个点，每个点有点权，初始没有边，接下来有m条操作，格式如下
// 操作 1 x y : 点x和点y之间增加无向边，已经连通则忽略
// 操作 2 x y : 点x的点权变成y
// 操作 3 x y : 每条边需要指定方向变成单向边，希望点x到点y的过程中
//              获得最多的点权累加和，重复经过某点只获得一次点权
//              打印能获得的点权累加和最大值，如果不连通打印-1
//              指定边的方向仅影响本次操作，不会影响后续操作
// 1 <= n <= 1.5 * 10^5    1 <= m <= 5 * n
// 测试链接 : https://www.luogu.com.cn/problem/P10658
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_Running1 {

	public static int MAXN = 200001;
	public static int n, m;

	public static int[] father = new int[MAXN];

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// 点权
	public static long[] arr = new long[MAXN];

	// sum[x]表示x代表的边双连通分量中，点权累加和
	public static long[] sum = new long[MAXN];

	// sumOfSum[x]表示以x为根的辅助splay中，点权累加和
	public static long[] sumOfSum = new long[MAXN];

	public static int find(int x) {
		if (x != father[x]) {
			father[x] = find(father[x]);
		}
		return father[x];
	}

	public static void up(int x) {
		sumOfSum[x] = sumOfSum[ls[x]] + sumOfSum[rs[x]] + sum[x];
	}

	public static boolean isroot(int x) {
		return ls[fa[x]] != x && rs[fa[x]] != x;
	}

	public static int lr(int x) {
		return ls[fa[x]] == x ? 0 : 1;
	}

	public static void reverse(int x) {
		if (x != 0) {
			int tmp = ls[x];
			ls[x] = rs[x];
			rs[x] = tmp;
			rev[x] = !rev[x];
		}
	}

	public static void down(int x) {
		if (rev[x]) {
			reverse(ls[x]);
			reverse(rs[x]);
			rev[x] = false;
		}
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
		up(f);
		up(x);
	}

	public static void splay(int x) {
		int siz = 0;
		sta[++siz] = x;
		for (int y = x; !isroot(y); y = fa[y]) {
			sta[++siz] = fa[y];
		}
		while (siz != 0) {
			down(sta[siz--]);
		}
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

	public static void access(int x) {
		x = find(x);
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			rs[x] = y;
			up(x);
			fa[x] = find(fa[x]);
		}
	}

	public static void makeroot(int x) {
		access(x);
		splay(x);
		reverse(x);
	}

	public static int findroot(int x) {
		access(x);
		splay(x);
		down(x);
		while (ls[x] != 0) {
			x = ls[x];
			down(x);
		}
		splay(x);
		return x;
	}

	public static void split(int x, int y) {
		makeroot(x);
		access(y);
		splay(y);
	}

	public static void condense(int x, int root) {
		if (x != 0) {
			father[x] = root;
			sum[root] += sum[x];
			condense(ls[x], root);
			condense(rs[x], root);
		}
	}

	// 如果两点不连通就连接，如果连通，说明形成环，缩成一个边双
	public static void link(int x, int y) {
		x = find(x);
		y = find(y);
		if (x == y) {
			return;
		}
		makeroot(x);
		if (findroot(y) != x) {
			fa[x] = y;
		} else {
			condense(rs[x], x);
			rs[x] = 0;
			up(x);
		}
	}

	// 修改点权
	public static void update(int x, int y) {
		long delta = y - arr[x];
		arr[x] = y;
		x = find(x);
		makeroot(x);
		sum[x] += delta;
		up(x);
	}

	// 查询两点路径上，所有边双连通分量的权值和
	public static long query(int x, int y) {
		x = find(x);
		y = find(y);
		if (findroot(x) != findroot(y)) {
			return -1;
		}
		split(x, y);
		return sumOfSum[y];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			arr[i] = in.nextInt();
			sum[i] = arr[i];
			sumOfSum[i] = arr[i];
		}
		for (int i = 1, op, x, y; i <= m; i++) {
			op = in.nextInt();
			x = in.nextInt();
			y = in.nextInt();
			if (op == 1) {
				link(x, y);
			} else if (op == 2) {
				update(x, y);
			} else {
				out.println(query(x, y));
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

	}

}