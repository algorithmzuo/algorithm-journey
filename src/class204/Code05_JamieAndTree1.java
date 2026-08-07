package class204;

// 杰米与树，java版
// 一共n个节点、n-1条边，所有节点组成一棵树，点有点权，初始的根为1号节点
// 接下来有q条操作，操作类型如下
// 操作 1 x     : 整棵树的根修改为x
// 操作 2 x y v : 当前根的情况下，lca(x, y)的子树中所有点权增加v
// 操作 3 x     : 当前根的情况下，打印x的子树点权累加和
// 1 <= n、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF916E
// 测试链接 : https://codeforces.com/problemset/problem/916/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_JamieAndTree1 {

	public static int MAXN = 100001;
	public static int n, q, root;

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// val[x]表示节点x当前的点权，已经包含作用到x自身的所有增量
	public static long[] val = new long[MAXN];

	// splaySize[x]表示以x为根的辅助splay中，汇总实链的节点总量
	// 只算实链的节点，不统计这些节点下方的虚子树
	public static int[] splaySize = new int[MAXN];

	// treeSize[x]表示以x为根的辅助splay中，汇总原树的节点总量
	// 包括辅助splay中，实链所有节点，以及这些节点下方的所有虚子树
	public static int[] treeSize = new int[MAXN];

	// treeSum[x]表示以x为根的辅助splay中，汇总原树的节点点权累加和
	// 包括辅助splay中，实链所有节点点权，以及这些节点下方的所有虚子树点权
	public static long[] treeSum = new long[MAXN];

	// virSize[x]表示x的所有直接虚儿子所代表的完整原树子树，节点总量
	public static int[] virSize = new int[MAXN];

	// virSum[x]表示x的所有直接虚儿子所代表的完整原树子树，基础点权累加和
	// 不包含virAdd[x]统一作用在这些虚子树上的增量
	// x的所有虚子树当前真实点权和 = virSum[x] + virSize[x] * virAdd[x]
	public static long[] virSum = new long[MAXN];

	// allVirSize[x]表示以x为根的辅助splay中，每个节点的virSize之和
	// 也就是，不统计辅助splay中的实链节点本身，只统计它们挂着的全部虚子树节点总量
	// 也就是，allVirSize[x] = treeSize[x] - splaySize[x]
	// 单独用一个数组维护而已，其实可以通过treeSize和splaySize加工得到
	public static int[] allVirSize = new int[MAXN];

	// virAdd[x]表示x的所有直接虚子树已经累计获得的统一增量
	// 这是节点x维护的持久状态，即使virTag[x]下传后也不能清零
	public static long[] virAdd = new long[MAXN];

	// virTag[x]表示作用于以x为根的辅助splay中，所有节点虚子树的统一增量
	// 该增量已经统计进x的信息，但还没有向x的左右辅助splay儿子下传
	public static long[] virTag = new long[MAXN];

	// splayTag[x]表示作用于以x为根的辅助splay中，所有实链节点自身点权的统一增量
	// 该增量已经统计进x的信息，但还没有向x的左右辅助splay儿子下传
	public static long[] splayTag = new long[MAXN];

	public static void up(int x) {
		splaySize[x] = splaySize[ls[x]] + splaySize[rs[x]] + 1;
		treeSize[x] = treeSize[ls[x]] + treeSize[rs[x]] + virSize[x] + 1;
		allVirSize[x] = allVirSize[ls[x]] + allVirSize[rs[x]] + virSize[x];
		treeSum[x] = treeSum[ls[x]] + treeSum[rs[x]] + val[x] + virSum[x] + virSize[x] * virAdd[x];
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

	// 给辅助splay中的所有节点自身增加v，不修改这些节点的虚子树
	public static void addSplay(int x, long v) {
		if (x != 0) {
			splayTag[x] += v;
			val[x] += v;
			treeSum[x] += v * splaySize[x];
		}
	}

	// 给辅助splay中所有节点的虚子树增加v，不修改辅助splay节点自身
	public static void addVirtual(int x, long v) {
		if (x != 0) {
			virAdd[x] += v;
			virTag[x] += v;
			treeSum[x] += v * allVirSize[x];
		}
	}

	public static void down(int x) {
		if (rev[x]) {
			reverse(ls[x]);
			reverse(rs[x]);
			rev[x] = false;
		}
		if (splayTag[x] != 0) {
			addSplay(ls[x], splayTag[x]);
			addSplay(rs[x], splayTag[x]);
			splayTag[x] = 0;
		}
		if (virTag[x] != 0) {
			addVirtual(ls[x], virTag[x]);
			addVirtual(rs[x], virTag[x]);
			virTag[x] = 0;
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
		up(x);
	}

	// 原来f的实儿子是x，现在x变成虚儿子
	public static void insertVirtual(int f, int x) {
		if (x != 0) {
			addSplay(x, -virAdd[f]);
			addVirtual(x, -virAdd[f]);
			virSize[f] += treeSize[x];
			virSum[f] += treeSum[x];
		}
	}

	// 原来f的虚儿子是x，现在x变成实儿子
	public static void removeVirtual(int f, int x) {
		if (x != 0) {
			virSize[f] -= treeSize[x];
			virSum[f] -= treeSum[x];
			addSplay(x, virAdd[f]);
			addVirtual(x, virAdd[f]);
		}
	}

	// 返回最后一次接上的点，用于得到lca
	public static int access(int x) {
		int ans = 0;
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			// 原右儿子由实儿子变成虚儿子
			insertVirtual(x, rs[x]);
			// 新右儿子由虚儿子变成实儿子
			removeVirtual(x, y);
			rs[x] = y;
			up(x);
			ans = x;
		}
		return ans;
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
			insertVirtual(y, x);
			up(y);
		}
	}

	public static void addLcaTree(int x, int y, int v) {
		// 先将当前根设为原树根
		makeroot(root);
		// 连续两次access求lca
		access(x);
		int xylca = access(y);
		split(root, xylca);
		// 此时root到lca的祖先链都在ls[lca]上，rs[lca]为空
		// lca子树的所有节点都在lca的虚子树上
		virAdd[xylca] += v;
		val[xylca] += v;
		treeSum[xylca] += (virSize[xylca] + 1L) * v;
	}

	public static long query(int x) {
		split(root, x);
		// ls[x]是祖先链，不计入答案
		// 答案包括x自身，以及x的所有虚子树，还有增量
		return val[x] + virSum[x] + virSize[x] * virAdd[x];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		q = in.nextInt();
		root = 1;
		for (int i = 1; i <= n; i++) {
			val[i] = in.nextInt();
			splaySize[i] = 1;
			treeSize[i] = 1;
			treeSum[i] = val[i];
		}
		for (int i = 1, x, y; i < n; i++) {
			x = in.nextInt();
			y = in.nextInt();
			link(x, y);
		}
		for (int i = 1, op, x, y, v; i <= q; i++) {
			op = in.nextInt();
			if (op == 1) {
				root = in.nextInt();
			} else if (op == 2) {
				x = in.nextInt();
				y = in.nextInt();
				v = in.nextInt();
				addLcaTree(x, y, v);
			} else {
				x = in.nextInt();
				out.println(query(x));
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