package class205;

// 简单题，替罪羊树的方式，java版
// 有一个n * n的平面区域，初始时没有点，有若干条操作，类型如下
// 操作 1 a b c   : 平面里增加一个点，坐标(a, b)，点权为c
// 操作 2 a b c d : 查询(a, b)为左下角、(c, d)为右上角的区域中，所有点的点权和
// 操作 3         : 终止，以后没有操作了
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 1 <= n <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4148
// 提交以下的code，提交时请把类名改成"Main"，本题卡空间，java实现无法通过
// 想通过用C++实现，本节课Code04_SimpleProblem2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_SimpleProblem1 {

	public static int MAXN = 200001;
	public static int INF = 1 << 30;
	public static int n, cntn;

	// KDT的根
	public static int root;
	public static int[][] arr = new int[MAXN][3];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	public static int[] siz = new int[MAXN];
	public static int[] sum = new int[MAXN];
	public static int[] xmin = new int[MAXN];
	public static int[] xmax = new int[MAXN];
	public static int[] ymin = new int[MAXN];
	public static int[] ymax = new int[MAXN];

	// 平衡因子
	public static double ALPHA = 0.7;
	// 不平衡时收集节点编号
	public static int[] collect = new int[MAXN];
	// 收集的节点数量
	public static int collectSiz;
	// 最顶部的不平衡点
	public static int top;
	// 最顶部的不平衡点的父亲
	public static int topFather;
	// 最顶部的不平衡点是其父亲的哪侧孩子
	public static int topSide;
	// 最顶部的不平衡点是按照什么维度划分的
	public static int topDimension;

	public static int init(int x, int y, int v) {
		cntn++;
		arr[cntn][0] = x;
		arr[cntn][1] = y;
		arr[cntn][2] = v;
		ls[cntn] = rs[cntn] = 0;
		siz[cntn] = 1;
		sum[cntn] = v;
		xmin[cntn] = xmax[cntn] = x;
		ymin[cntn] = ymax[cntn] = y;
		return cntn;
	}

	public static void maintain(int i) {
		siz[i] = 1 + siz[ls[i]] + siz[rs[i]];
		sum[i] = arr[i][2] + sum[ls[i]] + sum[rs[i]];
		xmin[i] = Math.min(arr[i][0], Math.min(xmin[ls[i]], xmin[rs[i]]));
		xmax[i] = Math.max(arr[i][0], Math.max(xmax[ls[i]], xmax[rs[i]]));
		ymin[i] = Math.min(arr[i][1], Math.min(ymin[ls[i]], ymin[rs[i]]));
		ymax[i] = Math.max(arr[i][1], Math.max(ymax[ls[i]], ymax[rs[i]]));
	}

	// collect数组保持的是节点编号，交换节点编号即可
	public static void swap(int i, int j) {
		int tmp = collect[i];
		collect[i] = collect[j];
		collect[j] = tmp;
	}

	public static int first, last;

	public static void partition(int l, int r, int pivot, int dimension) {
		first = l;
		last = r;
		int i = l;
		while (i <= last) {
			int cur = arr[collect[i]][dimension];
			if (cur == pivot) {
				i++;
			} else if (cur < pivot) {
				swap(first++, i++);
			} else {
				swap(i, last--);
			}
		}
	}

	public static void randSelect(int l, int r, int i, int dimension) {
		while (l <= r) {
			int idx = collect[l + (int) (Math.random() * (r - l + 1))];
			int pivot = arr[idx][dimension];
			partition(l, r, pivot, dimension);
			if (i < first) {
				r = first - 1;
			} else if (i > last) {
				l = last + 1;
			} else {
				break;
			}
		}
	}

	public static int build(int l, int r, int dimension) {
		if (l > r) {
			return 0;
		}
		int mid = (l + r) >> 1;
		randSelect(l, r, mid, dimension);
		int rt = collect[mid];
		ls[rt] = build(l, mid - 1, dimension ^ 1);
		rs[rt] = build(mid + 1, r, dimension ^ 1);
		maintain(rt);
		return rt;
	}

	public static boolean balance(int i) {
		return ALPHA * siz[i] >= Math.max(siz[ls[i]], siz[rs[i]]);
	}

	// 收集子树中的所有节点编号
	// 先序、中序、后序哪种遍历都可以
	// 因为重构时会重新选择中位点
	public static void dfs(int i) {
		if (i != 0) {
			collect[++collectSiz] = i;
			dfs(ls[i]);
			dfs(rs[i]);
		}
	}

	public static void rebuild() {
		if (top != 0) {
			collectSiz = 0;
			dfs(top);
			int rt = build(1, collectSiz, topDimension);
			if (topFather == 0) {
				root = rt;
			} else if (topSide == 1) {
				ls[topFather] = rt;
			} else {
				rs[topFather] = rt;
			}
		}
	}

	public static void add(int insertNode, int u, int fa, int side, int dimension) {
		if (u == 0) {
			if (fa == 0) {
				root = insertNode;
			} else if (side == 1) {
				ls[fa] = insertNode;
			} else {
				rs[fa] = insertNode;
			}
		} else {
			if (arr[insertNode][dimension] <= arr[u][dimension]) {
				add(insertNode, ls[u], u, 1, dimension ^ 1);
			} else {
				add(insertNode, rs[u], u, 2, dimension ^ 1);
			}
			maintain(u);
			// 递归返回时会不断覆盖
			// 最终记录最上方的不平衡节点
			if (!balance(u)) {
				top = u;
				topFather = fa;
				topSide = side;
				topDimension = dimension;
			}
		}
	}

	public static void add(int x, int y, int v) {
		top = topFather = topSide = topDimension = 0;
		int insertNode = init(x, y, v);
		add(insertNode, root, 0, 0, 0);
		rebuild();
	}

	public static int query(int x1, int y1, int x2, int y2, int i) {
		if (i == 0) {
			return 0;
		}
		if (xmax[i] < x1 || x2 < xmin[i] || ymax[i] < y1 || y2 < ymin[i]) {
			return 0;
		}
		if (x1 <= xmin[i] && xmax[i] <= x2 && y1 <= ymin[i] && ymax[i] <= y2) {
			return sum[i];
		}
		int ans = 0;
		if (x1 <= arr[i][0] && arr[i][0] <= x2 && y1 <= arr[i][1] && arr[i][1] <= y2) {
			ans += arr[i][2];
		}
		ans += query(x1, y1, x2, y2, ls[i]);
		ans += query(x1, y1, x2, y2, rs[i]);
		return ans;
	}

	public static int query(int x1, int y1, int x2, int y2) {
		return query(x1, y1, x2, y2, root);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		// 读入的n其实没用
		n = in.nextInt();
		xmin[0] = ymin[0] = INF;
		xmax[0] = ymax[0] = -INF;
		int op, a, b, c, d, lastAns;
		op = in.nextInt();
		lastAns = 0;
		while (op != 3) {
			a = in.nextInt();
			b = in.nextInt();
			c = in.nextInt();
			a ^= lastAns;
			b ^= lastAns;
			c ^= lastAns;
			if (op == 1) {
				add(a, b, c);
			} else {
				d = in.nextInt();
				d ^= lastAns;
				lastAns = query(a, b, c, d);
				out.println(lastAns);
			}
			op = in.nextInt();
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