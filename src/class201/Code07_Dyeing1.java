package class201;

// 染色，java版
// 本题在讲解161，讲述了树链剖分的解法，这里用lct的解法
// 测试链接 : https://www.luogu.com.cn/problem/P2486
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_Dyeing1 {

	public static int MAXN = 100001;
	public static int n, m;

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static int[] sta = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];

	public static int[] arr = new int[MAXN];
	public static int[] lcol = new int[MAXN];
	public static int[] rcol = new int[MAXN];
	public static int[] cnt = new int[MAXN];
	public static int[] tag = new int[MAXN];

	public static void up(int x) {
		lcol[x] = ls[x] == 0 ? arr[x] : lcol[ls[x]];
		rcol[x] = rs[x] == 0 ? arr[x] : rcol[rs[x]];
		cnt[x] = cnt[ls[x]] + cnt[rs[x]] + 1;
		if (ls[x] != 0 && rcol[ls[x]] == arr[x]) {
			cnt[x]--;
		}
		if (rs[x] != 0 && arr[x] == lcol[rs[x]]) {
			cnt[x]--;
		}
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
			tmp = lcol[x];
			lcol[x] = rcol[x];
			rcol[x] = tmp;
			rev[x] = !rev[x];
		}
	}

	public static void effect(int x, int c) {
		if (x != 0) {
			arr[x] = c;
			lcol[x] = c;
			rcol[x] = c;
			cnt[x] = 1;
			tag[x] = c;
		}
	}

	public static void down(int x) {
		if (rev[x]) {
			reverse(ls[x]);
			reverse(rs[x]);
			rev[x] = false;
		}
		if (tag[x] != 0) {
			effect(ls[x], tag[x]);
			effect(rs[x], tag[x]);
			tag[x] = 0;
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
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			rs[x] = y;
			up(x);
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

	public static void link(int x, int y) {
		makeroot(x);
		if (findroot(y) != x) {
			fa[x] = y;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
			lcol[i] = arr[i];
			rcol[i] = arr[i];
			cnt[i] = 1;
		}
		for (int i = 1, x, y; i < n; i++) {
			x = in.nextInt();
			y = in.nextInt();
			link(x, y);
		}
		for (int i = 1, x, y, c; i <= m; i++) {
			String op = in.nextString();
			if (op.equals("C")) {
				x = in.nextInt();
				y = in.nextInt();
				c = in.nextInt();
				split(x, y);
				effect(y, c);
			} else {
				x = in.nextInt();
				y = in.nextInt();
				split(x, y);
				out.println(cnt[y]);
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