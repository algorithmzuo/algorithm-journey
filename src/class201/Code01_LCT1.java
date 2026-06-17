package class201;

// LCT模版题，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3690
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_LCT1 {

	public static int MAXN = 100001;
	public static int n, m;
	public static int[] arr = new int[MAXN];

	// 既保存辅助splay内，每个节点的父节点
	// 也保存沿虚边向上跳到的原树父节点，认父不认子
	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static int[] sta = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];

	// 本题需要异或和
	public static int[] xor = new int[MAXN];

	// 不同题目实现不同的up方法
	public static void up(int x) {
		xor[x] = xor[ls[x]] ^ xor[rs[x]] ^ arr[x];
	}

	// 判断节点x是不是辅助splay的顶部节点
	public static boolean isroot(int x) {
		return ls[fa[x]] != x && rs[fa[x]] != x;
	}

	// x不是辅助splay的顶部节点才能调用，x是父节点的哪侧儿子
	public static int lr(int x) {
		return ls[fa[x]] == x ? 0 : 1;
	}

	// 翻转以x为根的辅助splay，交换左右儿子，打上翻转标记
	public static void reverse(int x) {
		if (x != 0) {
			int tmp = ls[x];
			ls[x] = rs[x];
			rs[x] = tmp;
			rev[x] = !rev[x];
		}
	}

	// 处理翻转懒更新
	public static void down(int x) {
		if (rev[x]) {
			reverse(ls[x]);
			reverse(rs[x]);
			rev[x] = false;
		}
	}

	// x向上旋转
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

	// x提到辅助splay的顶部
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

	// 打通当前原树根到x的路径，使其成为一条实链
	public static void access(int x) {
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			rs[x] = y;
			up(x);
		}
	}

	// 把x变成所在原树的根，但是不改变连通结构和边集合
	public static void makeroot(int x) {
		access(x);
		splay(x);
		reverse(x);
	}

	// 找到节点x所在原树的根，并把根提到当前辅助splay的顶部
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

	// 先让x变成原树的根，然后让x到y的路径变成实链，最后让y提到当前辅助splay的顶部
	public static void split(int x, int y) {
		makeroot(x);
		access(y);
		splay(y);
	}

	// 原树中连接x和y，如果原本连通则忽略
	public static void link(int x, int y) {
		makeroot(x);
		if (findroot(y) != x) {
			fa[x] = y;
		}
	}

	// 原树中切断x和y之间的直接边，如果不连通或没有直接边则忽略
	public static void cut(int x, int y) {
		makeroot(x);
		if (findroot(y) == x && fa[y] == x && rs[x] == y && ls[y] == 0) {
			fa[y] = rs[x] = 0;
			up(x);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
			xor[i] = arr[i];
		}
		for (int i = 1, op, x, y; i <= m; i++) {
			op = in.nextInt();
			x = in.nextInt();
			y = in.nextInt();
			if (op == 0) {
				split(x, y);
				out.println(xor[y]);
			} else if (op == 1) {
				link(x, y);
			} else if (op == 2) {
				cut(x, y);
			} else {
				splay(x);
				arr[x] = y;
				up(x);
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