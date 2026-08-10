package class204;

// LCT与动态图，java版
// 初始有n个孤立节点，接下来有q条操作，操作类型如下
// 操作 1 x y : 在节点x和节点y之间增加一条无向边
// 操作 2 x y : 打印节点x和节点y之间的割边数量，不连通打印-1
// 操作 3 x y : 打印节点x和节点y之间的割点数量，不连通打印-1
// 割点数量包括节点x和节点y本身
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 1 <= n <= 10^5
// 1 <= q <= 3 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5489
// 提交以下的code，提交时请把类名改成"Main"
// 本题卡常数时间，java的实现无法通过
// 想通过用C++实现，本节课Code08_DynamicGraph2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code08_DynamicGraph1 {

	public static int MAXN = 100001;
	public static int MAXV = 400001;
	public static int n, q;

	// 原图的连通性只增不减，使用并查集维护
	public static int[] father = new int[MAXN];

	// 0号LCT维护割边，1号LCT维护圆方树上的割点
	public static int[][] fa = new int[2][MAXV];
	public static int[][] ls = new int[2][MAXV];
	public static int[][] rs = new int[2][MAXV];
	public static boolean[][] rev = new boolean[2][MAXV];
	public static int[] sta = new int[MAXV];

	public static int[][] val = new int[2][MAXV];
	public static int[][] sum = new int[2][MAXV];

	// 只给割边LCT使用，zeroTag[x]表示以x为根的辅助splay中，所有边节点的贡献变成0
	public static boolean[] zeroTag = new boolean[MAXV];

	// 展开圆方树上的路径
	public static int[] road = new int[MAXV];
	public static int cntr;

	// 两棵LCT分配节点的计数，割边LCT增加边节点，割点LCT增加方点
	public static int cnte;
	public static int cntv;

	public static int find(int x) {
		if (father[x] != x) {
			father[x] = find(father[x]);
		}
		return father[x];
	}

	public static void up(int c, int x) {
		sum[c][x] = sum[c][ls[c][x]] + sum[c][rs[c][x]] + val[c][x];
	}

	public static boolean isroot(int c, int x) {
		return ls[c][fa[c][x]] != x && rs[c][fa[c][x]] != x;
	}

	public static int lr(int c, int x) {
		return ls[c][fa[c][x]] == x ? 0 : 1;
	}

	public static void reverse(int c, int x) {
		if (x != 0) {
			int tmp = ls[c][x];
			ls[c][x] = rs[c][x];
			rs[c][x] = tmp;
			rev[c][x] = !rev[c][x];
		}
	}

	// 割边LCT中，将整棵辅助splay的贡献全部变成0
	public static void setZero(int x) {
		if (x != 0) {
			val[0][x] = 0;
			sum[0][x] = 0;
			zeroTag[x] = true;
		}
	}

	public static void down(int c, int x) {
		if (rev[c][x]) {
			reverse(c, ls[c][x]);
			reverse(c, rs[c][x]);
			rev[c][x] = false;
		}
		if (c == 0 && zeroTag[x]) {
			setZero(ls[c][x]);
			setZero(rs[c][x]);
			zeroTag[x] = false;
		}
	}

	public static void rotate(int c, int x) {
		int f = fa[c][x], g = fa[c][f];
		if (lr(c, x) == 0) {
			ls[c][f] = rs[c][x];
			if (ls[c][f] != 0) {
				fa[c][ls[c][f]] = f;
			}
			rs[c][x] = f;
		} else {
			rs[c][f] = ls[c][x];
			if (rs[c][f] != 0) {
				fa[c][rs[c][f]] = f;
			}
			ls[c][x] = f;
		}
		if (!isroot(c, f)) {
			if (lr(c, f) == 0) {
				ls[c][g] = x;
			} else {
				rs[c][g] = x;
			}
		}
		fa[c][f] = x;
		fa[c][x] = g;
		up(c, f);
		up(c, x);
	}

	public static void splay(int c, int x) {
		int size = 0;
		sta[++size] = x;
		for (int y = x; !isroot(c, y); y = fa[c][y]) {
			sta[++size] = fa[c][y];
		}
		while (size != 0) {
			down(c, sta[size--]);
		}
		while (!isroot(c, x)) {
			int f = fa[c][x];
			if (!isroot(c, f)) {
				if (lr(c, x) == lr(c, f)) {
					rotate(c, f);
				} else {
					rotate(c, x);
				}
			}
			rotate(c, x);
		}
		up(c, x);
	}

	public static void access(int c, int x) {
		for (int y = 0; x != 0; y = x, x = fa[c][x]) {
			splay(c, x);
			rs[c][x] = y;
			up(c, x);
		}
	}

	public static void makeroot(int c, int x) {
		access(c, x);
		splay(c, x);
		reverse(c, x);
	}

	public static void split(int c, int x, int y) {
		makeroot(c, x);
		access(c, y);
		splay(c, y);
	}

	// 保证x和y当前不连通，所以化简了写法
	public static void link(int c, int x, int y) {
		makeroot(c, x);
		fa[c][x] = y;
	}

	// 保证x和y之间存在直接边，所以化简了写法
	public static void cut(int c, int x, int y) {
		split(c, x, y);
		fa[c][x] = 0;
		ls[c][y] = 0;
		up(c, y);
	}

	public static void dfsRoad(int x) {
		if (x != 0) {
			down(1, x);
			dfsRoad(ls[1][x]);
			road[++cntr] = x;
			dfsRoad(rs[1][x]);
		}
	}

	public static void addEdge(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (fx != fy) {
			father[fy] = fx;
			// 割边LCT采用边转点，新建的边当前一定是割边，贡献为1
			int edge = ++cnte;
			val[0][edge] = 1;
			sum[0][edge] = 1;
			link(0, x, edge);
			link(0, edge, y);
			// 动态圆方树中，两个连通块之间直接连接两个圆点
			link(1, x, y);
		} else {
			// x、y原本已经连通，新边形成环
			// 割边LCT中，x到y路径上的所有边都进入环，从此不再是割边
			split(0, x, y);
			setZero(y);
			// 暴露当前动态圆方树中x到y的路径
			split(1, x, y);
			if (sum[1][y] > 2) {
				// 按照x到y的顺序，取出路径上的所有圆点和方点
				cntr = 0;
				dfsRoad(y);
				// 删除原路径上的所有树边
				for (int i = 2; i <= cntr; i++) {
					cut(1, road[i - 1], road[i]);
				}
				// 新建方点，表示新形成的、更大的点双连通分量
				int square = ++cntv;
				// 将原路径上的所有圆点和旧方点连接到新方点
				for (int i = 1; i <= cntr; i++) {
					link(1, road[i], square);
				}
			}
		}
	}

	public static int queryCute(int x, int y) {
		if (find(x) != find(y)) {
			return -1;
		}
		split(0, x, y);
		return sum[0][y];
	}

	public static int queryCutv(int x, int y) {
		if (find(x) != find(y)) {
			return -1;
		}
		split(1, x, y);
		return sum[1][y];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		q = in.nextInt();
		cnte = n;
		cntv = n;
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			val[1][i] = 1;
			sum[1][i] = 1;
		}
		for (int i = 1, lastAns = 0, curAns, op, x, y; i <= q; i++) {
			op = in.nextInt();
			x = in.nextInt();
			y = in.nextInt();
			x ^= lastAns;
			y ^= lastAns;
			if (op == 1) {
				addEdge(x, y);
			} else {
				if (op == 2) {
					curAns = queryCute(x, y);
				} else {
					curAns = queryCutv(x, y);
				}
				out.println(curAns);
				if (curAns != -1) {
					lastAns = curAns;
				}
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