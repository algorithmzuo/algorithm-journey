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
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code08_DynamicGraph1 {

	public static int MAXN = 100001;
	public static int MAXT = 400001;
	public static int n, q;

	// 并查集维护连通性
	public static int[] father = new int[MAXN];

	// 节点x，有割边和割点两个状态，x表示割边状态，x+n表示割点状态
	// 割边LCT和割点LCT彼此独立，共用一套数组
	public static int[] fa = new int[MAXT];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static boolean[] rev = new boolean[MAXT];
	public static int[] sta = new int[MAXT];

	// 两套LCT都需要产生的新节点，都用cntev进行编号分配
	// cntev初始是2 * n，然后根据++cntev产生新的编号
	public static int cntev;

	// 两套LCT都有单点贡献和汇总贡献，并且汇总函数up是一样的
	public static int[] val = new int[MAXT];
	public static int[] sum = new int[MAXT];

	// 只给割边LCT使用，zeroTag[x]表示以x为根的辅助splay中，所有边节点的贡献变成0
	public static boolean[] zeroTag = new boolean[MAXT];

	// 收集割点LCT中的圆方树路径
	public static int[] road = new int[MAXT];
	public static int roadLen;

	public static int find(int x) {
		if (father[x] != x) {
			father[x] = find(father[x]);
		}
		return father[x];
	}

	public static void up(int x) {
		sum[x] = sum[ls[x]] + sum[rs[x]] + val[x];
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

	// 只对割边LCT生效，整棵辅助splay的贡献变成0
	public static void setZero(int x) {
		if (x != 0) {
			val[x] = 0;
			sum[x] = 0;
			zeroTag[x] = true;
		}
	}

	public static void down(int x) {
		if (rev[x]) {
			reverse(ls[x]);
			reverse(rs[x]);
			rev[x] = false;
		}
		// zeroTag只对割边LCT生效
		if (zeroTag[x]) {
			setZero(ls[x]);
			setZero(rs[x]);
			zeroTag[x] = false;
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
		int size = 0;
		sta[++size] = x;
		for (int y = x; !isroot(y); y = fa[y]) {
			sta[++size] = fa[y];
		}
		while (size != 0) {
			down(sta[size--]);
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
		up(x);
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

	public static void split(int x, int y) {
		makeroot(x);
		access(y);
		splay(y);
	}

	// 保证x和y当前不连通，所以化简了写法
	public static void link(int x, int y) {
		makeroot(x);
		fa[x] = y;
	}

	// 保证x和y之间存在直接边，所以化简了写法
	public static void cut(int x, int y) {
		split(x, y);
		fa[x] = 0;
		ls[y] = 0;
		up(y);
	}

	// 中序遍历收集节点，相当于实链中，从上到下依次收集节点
	public static void inOrder(int x) {
		if (x != 0) {
			// 先处理翻转的懒更新，左右儿子确保更新正确
			down(x);
			inOrder(ls[x]);
			road[++roadLen] = x;
			inOrder(rs[x]);
		}
	}

	public static void addEdge(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (fx != fy) {
			father[fy] = fx;
			// 割边LCT中，新建的边变成LCT中的点
			// 当前新建的边一定是割边，贡献为1
			int edge = ++cntev;
			val[edge] = 1;
			sum[edge] = 1;
			link(x, edge);
			link(edge, y);
			// 割点LCT中，原节点x的状态节点是x+n
			// 两个连通块之间直接连接两个圆点
			link(x + n, y + n);
		} else {
			// 如果x和y已经连通，那么此时形成环
			// 割边LCT中，x到y路径上的所有边都进入环
			// 从此不再是割边，贡献都变成0
			split(x, y);
			setZero(y);
			// 切换到割点LCT中对应的两个状态节点
			x = x + n;
			y = y + n;
			// 暴露当前动态圆方树中x到y的路径
			split(x, y);
			if (sum[y] > 2) {
				// 辅助splay中，按照中序遍历收集节点，核心是中序
				// 等同于按照当前实链从x到y的顺序收集所有节点
				roadLen = 0;
				inOrder(y);
				// 删除原来路径中所有的树边
				for (int i = 2; i <= roadLen; i++) {
					cut(road[i - 1], road[i]);
				}
				// 新建方点，表示新形成的、更大的点双连通分量
				int square = ++cntev;
				// 将原路径上的所有圆点和旧方点，此时连接到新方点
				for (int i = 1; i <= roadLen; i++) {
					link(road[i], square);
				}
			}
		}
	}

	public static int queryCute(int x, int y) {
		if (find(x) != find(y)) {
			return -1;
		}
		split(x, y);
		return sum[y];
	}

	public static int queryCutv(int x, int y) {
		if (find(x) != find(y)) {
			return -1;
		}
		x = x + n;
		y = y + n;
		split(x, y);
		return sum[y];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		q = in.nextInt();
		cntev = n << 1;
		// 并查集初始化
		// 割边LCT中的原节点贡献为0，无需设置
		// 割点LCT中的原节点是圆点，设置贡献是1
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			val[i + n] = 1;
			sum[i + n] = 1;
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