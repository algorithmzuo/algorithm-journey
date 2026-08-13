package class204;

// 首都，java版
// 初始时有n个互不连通的点，然后有m条操作，操作类型如下
// 操作 A x y : 点x和点y之间连一条边，保证之前x和y是不连通的
// 操作 Q x   : 打印点x所在连通区的重心编号
// 操作 Xor   : 打印当前所有连通区，重心编号的异或和
// 本题的一个连通区就是一棵树，重心是到其他点的距离总和最小的点
// 为了保证重心唯一，本题规定，如果有多个重心，选择编号最小的点作为重心
// 1 <= n <= 10^5
// 1 <= m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4299
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Capital1 {

	public static int MAXN = 100001;
	public static int n, m;

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// 并查集维护连通性，连通块的代表节点就是连通块的重心
	public static int[] father = new int[MAXN];

	public static int[] vir = new int[MAXN];
	public static int[] sum = new int[MAXN];

	public static int xorsum;

	// 查询x所在连通块当前的重心
	public static int find(int x) {
		if (x != father[x]) {
			father[x] = find(father[x]);
		}
		return father[x];
	}

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

	// 根据两个旧重心a和b，寻找并返回新重心
	public static int newCenter(int a, int b) {
		// 打通两个旧重心的路径，新重心一定在这条路径上
		split(a, b);
		int half = sum[b] >> 1;
		int lpass = 0;
		int rpass = 0;
		int ans = n + 1;
		int cur = b;
		while (cur != 0) {
			// 先要处理翻转标记，才能正确的向左或者向右移动
			down(cur);
			int lsiz = sum[ls[cur]] + lpass;
			int rsiz = sum[rs[cur]] + rpass;
			// 课上重点讲解了，只需要检查路径的两个方向的子树即可
			if (lsiz <= half && rsiz <= half) {
				ans = Math.min(ans, cur);
			}
			// 向节点数量较多的一侧移动
			if (lsiz < rsiz) {
				lpass += sum[ls[cur]] + vir[cur] + 1;
				cur = rs[cur];
			} else {
				rpass += sum[rs[cur]] + vir[cur] + 1;
				cur = ls[cur];
			}
		}
		// 新重心旋转上去，保证平衡性
		splay(ans);
		return ans;
	}

	public static void road(int x, int y) {
		int a = find(x);
		int b = find(y);
		link(x, y);
		int cur = newCenter(a, b);
		father[cur] = father[a] = father[b] = cur;
		xorsum ^= a ^ b ^ cur;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			sum[i] = 1;
			father[i] = i;
			xorsum ^= i;
		}
		String op;
		int x, y;
		for (int i = 1; i <= m; i++) {
			op = in.nextString();
			if (op.equals("A")) {
				x = in.nextInt();
				y = in.nextInt();
				road(x, y);
			} else if (op.equals("Q")) {
				x = in.nextInt();
				out.println(find(x));
			} else {
				out.println(xorsum);
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