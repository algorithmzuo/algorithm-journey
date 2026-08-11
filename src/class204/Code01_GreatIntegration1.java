package class204;

// 大融合，java版
// 本题就是讲解166，题目5，讲了线段树分治的解法，这里用lct的解法
// 一共有n个点，一共有q条操作，每条操作是如下两种类型中的一种
// 操作 A x y : 点x和点y之间连一条边，保证之前x和y是不联通的
// 操作 Q x y : 打印点x和点y之间这条边的负载，保证x和y之间有一条边
// 边负载定义为，这条边两侧端点各自连通区大小的乘积
// 1 <= n、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4219
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_GreatIntegration1 {

	public static int MAXN = 100001;
	public static int n, q;

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// vir[x]表示x所有虚子树的大小总和
	public static int[] vir = new int[MAXN];

	// sum[x]表示以x为根的辅助splay中，汇总所有节点的数量
	// 包括x自己、x的所有虚子树大小、左右儿子的贡献
	public static int[] sum = new int[MAXN];

	// 汇总贡献
	public static void up(int x) {
		sum[x] = sum[ls[x]] + sum[rs[x]] + vir[x] + 1;
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

	// 原右儿子由实儿子变成虚儿子，加入贡献
	// 新右儿子由虚儿子变成实儿子，删除贡献
	public static void access(int x) {
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			vir[x] += sum[rs[x]];
			vir[x] -= sum[y];
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

	// 连接x和y，x和y成为各自树的头，然后x作为y的虚儿子
	public static void link(int x, int y) {
		makeroot(x);
		if (findroot(y) != x) {
			access(y);
			splay(y);
			fa[x] = y;
			vir[y] += sum[x];
			up(y);
		}
	}

	// cut方法和lct模版代码完全一样，但是注意
	// 判断x和y之间是否存在直接边，如果判定成立
	// 此时x和y之间的边已经是实边，所以删除该边不需要修改vir
	public static void cut(int x, int y) {
		makeroot(x);
		if (findroot(y) == x && fa[y] == x && ls[y] == 0 && rs[x] == y) {
			fa[y] = rs[x] = 0;
			up(x);
		}
	}

	// 临时删除询问边
	// 将两个端点分别作为各自原树的根，得到各自连通块的大小
	// 然后恢复询问边
	public static long query(int x, int y) {
		cut(x, y);
		makeroot(x);
		int sizx = sum[x];
		makeroot(y);
		int sizy = sum[y];
		link(x, y);
		return 1L * sizx * sizy;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		q = in.nextInt();
		for (int i = 1; i <= n; i++) {
			sum[i] = 1;
		}
		for (int i = 1; i <= q; i++) {
			char op = in.nextChar();
			int x = in.nextInt();
			int y = in.nextInt();
			if (op == 'A') {
				link(x, y);
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
				if (len <= 0) {
					return -1;
				}
			}
			return buffer[ptr++];
		}

		char nextChar() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			return (char) c;
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
				val = val * 10 + c - '0';
				c = readByte();
			}
			return neg ? -val : val;
		}

	}

}
