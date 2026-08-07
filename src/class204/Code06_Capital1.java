package class204;

// 首都，java版
// 初始有n个互不连通的城市，一共有m条操作，操作类型如下
// 操作 A x y : 在两个不同国家的城市x和y之间连边，两个国家合并
// 操作 Q x   : 打印城市x所在国家的首都
// 操作 Xor   : 打印当前所有国家首都编号的异或和
// 一个国家的道路构成树，首都是到其他城市距离总和最小的城市
// 如果有多个首都，选择编号最小的城市
// 1 <= n <= 10^5
// 1 <= m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4299
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_Capital1 {

	public static int MAXN = 100001;
	public static int n, m;

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// center[]表示并查集，每个连通块是集合，代表节点就是该连通块的重心
	public static int[] center = new int[MAXN];

	public static int[] vir = new int[MAXN];
	public static int[] sum = new int[MAXN];

	public static int xorsum;

	// 查询x所在连通块当前的重心
	public static int find(int x) {
		if (x != center[x]) {
			center[x] = find(center[x]);
		}
		return center[x];
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

	// 两个旧重心的路径已经split出来
	// x是辅助splay的根，也是实链最下方的节点
	// 新重心在这条路径上，在辅助splay中，向较重的一侧搜索
	public static int getCenter(int x) {
		int half = sum[x] >> 1;
		int lout = 0;
		int rout = 0;
		int ans = n + 1;
		while (x != 0) {
			// 搜索过程向左或者向右移动，所以先要处理翻转标记
			down(x);
			int lsiz = sum[ls[x]] + lout;
			int rsiz = sum[rs[x]] + rout;
			// 两侧大小都不超过总大小的一半，那么x就是重心
			// 偶数大小的树可能有两个重心，所以取编号更小的
			if (lsiz <= half && rsiz <= half) {
				ans = Math.min(ans, x);
			}
			if (lsiz < rsiz) {
				// 右侧更大，向右寻找重心
				// 左儿子、x自身、x的虚子树都进入左侧
				lout += sum[ls[x]] + vir[x] + 1;
				x = rs[x];
			} else {
				// 左侧更大，向左寻找重心
				// 右儿子、x自身、x的虚子树都进入右侧
				rout += sum[rs[x]] + vir[x] + 1;
				x = ls[x];
			}
		}
		// 找到了新重心，旋转上去，保证平衡性
		splay(ans);
		return ans;
	}

	public static void road(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		link(x, y);
		// 新重心一定在两个旧重心的路径上
		// 合并两个集合，新重心作为代表节点
		// 异或和去掉两个旧重心，加入新重心
		split(fx, fy);
		int cur = getCenter(fy);
		center[cur] = center[fx] = center[fy] = cur;
		xorsum ^= fx ^ fy ^ cur;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			sum[i] = 1;
			center[i] = i;
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