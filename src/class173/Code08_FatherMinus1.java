package class173;

// 区间父变小，java版
// 一棵大小为n树，节点1是树头，给定fa[2..n]表示父亲节点编号
// 对于每个i > 1，都有fa[i] < i，下来有m条操作，操作类型如下
// 操作 1 x y z : [x..y]范围上任何一点i，fa[i] = max(1, fa[i] - z)
// 操作 2 x y   : 查询点x和点y的最低公共祖先
// 2 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1491H
// 测试链接 : https://codeforces.com/problemset/problem/1491/H
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code08_FatherMinus1 {

	public static int MAXN = 100001;
	public static int MAXB = 501;
	public static int n, m;

	// 节点的父亲节点
	public static int[] fa = new int[MAXN];
	// 节点如果从所在块出去，会去往的最近节点
	public static int[] out = new int[MAXN];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];
	// 块内所有节点的父亲编号，统一减少的幅度
	public static int[] lazy = new int[MAXB];
	// 块内所有节点的父亲编号，统一削减的次数
	public static int[] minusCnt = new int[MAXB];

	public static void innerUpdate(int b) {
		for (int i = bl[b]; i <= br[b]; i++) {
			fa[i] = Math.max(1, fa[i] - lazy[b]);
		}
		lazy[b] = 0;
		for (int i = bl[b]; i <= br[b]; i++) {
			if (fa[i] < bl[b]) {
				out[i] = fa[i];
			} else {
				out[i] = out[fa[i]];
			}
		}
	}

	public static void update(int l, int r, int v) {
		if (bi[l] == bi[r]) {
			for (int i = l; i <= r; i++) {
				fa[i] = Math.max(1, fa[i] - v);
			}
			innerUpdate(bi[l]);
		} else {
			for (int i = l; i <= br[bi[l]]; i++) {
				fa[i] = Math.max(1, fa[i] - v);
			}
			innerUpdate(bi[l]);
			for (int i = bl[bi[r]]; i <= r; i++) {
				fa[i] = Math.max(1, fa[i] - v);
			}
			innerUpdate(bi[r]);
			for (int b = bi[l] + 1; b <= bi[r] - 1; b++) {
				// 减少的幅度最多到n，不会更大
				// 这样还可以让lazy保持int类型并且不溢出
				lazy[b] = Math.min(n, lazy[b] + v);
				if (++minusCnt[b] <= blen) {
					innerUpdate(b);
				}
			}
		}
	}

	public static int jumpFa(int i) {
		return Math.max(1, fa[i] - lazy[bi[i]]);
	}

	public static int jumpOut(int i) {
		return Math.max(1, out[i] - lazy[bi[i]]);
	}

	public static int lca(int x, int y) {
		while (bi[x] != bi[y] || jumpOut(x) != jumpOut(y)) {
			if (bi[x] != bi[y]) {
				if (bi[x] < bi[y]) {
					int tmp = x;
					x = y;
					y = tmp;
				}
				x = jumpOut(x);
			} else {
				x = jumpOut(x);
				y = jumpOut(y);
			}
		}
		while (x != y) {
			if (x < y) {
				int tmp = x;
				x = y;
				y = tmp;
			}
			x = jumpFa(x);
		}
		return x;
	}

	public static void prepare() {
		blen = (int) Math.sqrt(n);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, n);
			innerUpdate(i);
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 2; i <= n; i++) {
			fa[i] = in.nextInt();
		}
		prepare();
		for (int i = 1, op, x, y, z; i <= m; i++) {
			op = in.nextInt();
			x = in.nextInt();
			y = in.nextInt();
			if (op == 1) {
				z = in.nextInt();
				update(x, y, z);
			} else {
				out.println(lca(x, y));
			}
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 20];
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
