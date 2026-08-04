package class204;

// Jamie and Tree，java版
// 一共n个节点、n-1条边，所有节点组成一棵树，点有点权，初始的根为1号节点
// 接下来有q条操作，操作类型有三种，具体格式如下
// 操作 1 x     : 整棵树的根修改为x
// 操作 2 x y v : 当前根的情况下，lca(x, y)的子树中所有点权增加v
// 操作 3 x     : 当前根的情况下，打印x的子树点权和
// 1 <= n、q <= 10^5
// -10^8 <= 点权、修改值 <= 10^8
// 测试链接 : https://www.luogu.com.cn/problem/CF916E
// 测试链接 : https://codeforces.com/problemset/problem/916/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_JamieAndTree1 {

	public static int MAXN = 100001;
	public static int n, q, root;

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// val[x]表示节点x当前的点权，已经包含作用到x自身的所有增量
	public static long[] val = new long[MAXN];

	// treeSize[x]表示以x为根的辅助Splay所汇总的完整原树节点数
	// 包括辅助Splay中的所有节点，以及这些节点挂着的所有虚子树
	public static int[] treeSize = new int[MAXN];

	// virSize[x]表示x的所有直接虚儿子对应的完整原树子树大小总和
	public static int[] virSize = new int[MAXN];

	// allVirSize[x]表示以x为根的辅助Splay中
	// 每个节点的virSize之和，也就是该辅助Splay挂着的所有虚子树大小总和
	public static int[] allVirSize = new int[MAXN];

	// splaySize[x]表示以x为根的辅助Splay节点数
	// 只统计辅助Splay中的实链节点，不统计这些节点挂着的虚子树
	public static int[] splaySize = new int[MAXN];

	// treeSum[x]表示以x为根的辅助Splay所汇总的完整点权和
	// 包括辅助Splay中所有节点自身的点权，以及这些节点挂着的所有虚子树点权和
	public static long[] treeSum = new long[MAXN];

	// virSum[x]表示x的所有直接虚儿子对应的完整原树子树权值和
	// 只算基础贡献，不包含virAdd[x]对这些虚子树产生的统一增量
	public static long[] virSum = new long[MAXN];

	// virAdd[x]表示x当前所有直接虚子树已经累计的统一增量
	// 这是节点x自身维护的持久状态，即使virTag[x]下传后也不能清零
	public static long[] virAdd = new long[MAXN];

	// virTag[x]表示以x为根的辅助Splay中
	// 尚未向左右儿子下传的虚子树统一增量标记，不是x自身虚子树已经累计的增量
	public static long[] virTag = new long[MAXN];

	// splayTag[x]表示以x为根的辅助Splay中
	// 尚未向左右儿子下传的实链节点的点权增量标记
	// val[x]已经得到该增量，左右儿子等待通过down继续获得
	public static long[] splayTag = new long[MAXN];

	public static void up(int x) {
		treeSize[x] = treeSize[ls[x]] + treeSize[rs[x]] + virSize[x] + 1;
		splaySize[x] = splaySize[ls[x]] + splaySize[rs[x]] + 1;
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

	// 给辅助splay中的所有节点自身增加v
	// 不修改这些节点挂着的虚子树
	public static void addSplay(int x, long v) {
		if (x != 0) {
			splayTag[x] += v;
			val[x] += v;
			treeSum[x] += v * splaySize[x];
		}
	}

	// 给辅助splay中所有节点挂着的虚子树增加v
	// 不修改辅助splay节点自身
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

	// y原来是x的实儿子，现在变成x的虚儿子
	// y处于实链期间已经受到过virAdd[x]的影响
	// 加入virSum[x]之前，先删除这部分影响，使其恢复为基础贡献
	public static void insertVirtual(int x, int y) {
		if (y != 0) {
			addSplay(y, -virAdd[x]);
			addVirtual(y, -virAdd[x]);
			virSize[x] += treeSize[y];
			virSum[x] += treeSum[y];
		}
	}

	// y原来是x的虚儿子，现在变成x的实儿子
	// 先从x的虚子树信息中删除y
	// 再把virAdd[x]真正作用到y的整棵原树上
	public static void deleteVirtual(int x, int y) {
		if (y != 0) {
			virSize[x] -= treeSize[y];
			virSum[x] -= treeSum[y];
			addSplay(y, virAdd[x]);
			addVirtual(y, virAdd[x]);
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
			deleteVirtual(x, y);
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

	public static void addLcaTree(int x, int y, long v) {
		// 先将当前根设为原树根，连续两次access求lca
		makeroot(root);
		access(x);
		int xylca = access(y);
		split(root, xylca);
		// 此时ls[x]是root到x的祖先链，rs[x]为空
		// x的所有后代都在x的虚子树中
		virAdd[xylca] += v;
		val[xylca] += v;
		treeSum[xylca] += (virSize[xylca] + 1L) * v;
	}

	public static long query(int x) {
		// ls[x]是祖先链，不能计入答案
		// 答案只包括x自身以及x的所有虚子树
		split(root, x);
		return val[x] + virSum[x] + virSize[x] * virAdd[x];
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		q = in.nextInt();
		root = 1;
		for (int i = 1; i <= n; i++) {
			val[i] = in.nextLong();
			treeSize[i] = 1;
			splaySize[i] = 1;
			treeSum[i] = val[i];
		}
		for (int i = 1; i < n; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			link(x, y);
		}
		for (int i = 1; i <= q; i++) {
			int op = in.nextInt();
			if (op == 1) {
				root = in.nextInt();
			} else if (op == 2) {
				int x = in.nextInt();
				int y = in.nextInt();
				long v = in.nextLong();
				addLcaTree(x, y, v);
			} else {
				int x = in.nextInt();
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
			return (int) nextLong();
		}

		long nextLong() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);

			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}

			long val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + c - '0';
				c = readByte();
			}
			return neg ? -val : val;
		}

	}

}