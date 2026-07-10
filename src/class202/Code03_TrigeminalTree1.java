package class202;

// 三叉神经树，java版
// 节点有两种类型，细胞节点编号范围[1, n]，信号节点编号范围[n+1, 3*n+1]
// 每个信号节点没有儿子节点，每个细胞节点固定有三个儿子节点
// 给定每个细胞节点的三个儿子节点编号，每个儿子可能是细胞节点，或者信号节点
// 每个信号节点拥有自身的值，是0或者1，初始值会给定，该值向上传递给父节点
// 每个细胞节点没有自身的值，根据三个儿子的信号，决定向上传递的值
// 三个儿子节点中，1多就向上传递1，0多就向上传递0
// 一共q条操作，每次给定一个信号节点的编号，表示它的值发生了翻转
// 题目保证1号节点是整棵树的根，每次操作执行完，都打印1号节点的输出
// 1 <= n、q <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4332
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_TrigeminalTree1 {

	public static int MAXN = 500001;
	public static int MAXT = MAXN * 3;
	public static int n, q;

	// 原树的父节点
	public static int[] parent = new int[MAXT];

	// 辅助splay
	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	// 不是左右孩子翻转，而是本题的传导状态翻转
	public static boolean[] tag = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// cell[i]，细胞节点i有几个1，范围0~3
	public static int[] cell = new int[MAXN];
	// sign[i]，信号节点i的值，范围0~1
	public static int[] sign = new int[MAXT];

	// x所在的实链中，从下往上第一个cell != 1的节点，当外部输入从0变1时
	// 从下往上一路连续的cell == 1的节点都改变输出，最终停在end1[x]
	public static int[] end1 = new int[MAXN];

	// x所在的实链中，从下往上第一个cell != 2的节点，当外部输入从1变0时
	// 从下往上一路连续的cell == 2的节点都改变输出，最终停在end2[x]
	public static int[] end2 = new int[MAXN];

	// 拓扑排序
	public static int[] que = new int[MAXT];
	public static int[] degree = new int[MAXN];

	public static int ans;

	// 维护实链的end1和end2
	public static void up(int x) {
		end1[x] = end1[rs[x]];
		if (end1[x] == 0 && cell[x] != 1) {
			end1[x] = x;
		}
		if (end1[x] == 0) {
			end1[x] = end1[ls[x]];
		}
		end2[x] = end2[rs[x]];
		if (end2[x] == 0 && cell[x] != 2) {
			end2[x] = x;
		}
		if (end2[x] == 0) {
			end2[x] = end2[ls[x]];
		}
	}

	// 传导状态翻转，只作用在连续传导段上
	// 要么连续一段，全是cell == 1，变成cell == 2
	// 要么连续一段，全是cell == 2，变成cell == 1
	// 只涉及这两种场景，不会有其他的cell值
	public static void effect(int x) {
		if (x != 0) {
			cell[x] ^= 3;
			int tmp = end1[x];
			end1[x] = end2[x];
			end2[x] = tmp;
			tag[x] = !tag[x];
		}
	}

	public static boolean isroot(int x) {
		return ls[fa[x]] != x && rs[fa[x]] != x;
	}

	public static int lr(int x) {
		return ls[fa[x]] == x ? 0 : 1;
	}

	public static void down(int x) {
		if (tag[x]) {
			effect(ls[x]);
			effect(rs[x]);
			tag[x] = false;
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

	public static void change(int x) {
		sign[x] ^= 1;
		int delta = sign[x] == 1 ? 1 : -1;
		x = parent[x];
		access(x);
		splay(x);
		int stop = delta == 1 ? end1[x] : end2[x];
		if (stop != 0) {
			splay(stop);
			effect(rs[stop]);
			cell[stop] += delta;
			up(stop);
		} else {
			effect(x);
			ans ^= 1;
		}
	}

	public static int get01(int x) {
		if (x <= n) {
			return cell[x] <= 1 ? 0 : 1;
		} else {
			return sign[x];
		}
	}

	public static void topo() {
		for (int i = 1; i <= n; i++) {
			degree[i] = 3;
		}
		int l = 1, r = 0;
		for (int i = n + 1; i <= 3 * n + 1; i++) {
			que[++r] = i;
		}
		while (l <= r) {
			int x = que[l++];
			if (x <= n) {
				up(x);
			}
			int p = parent[x];
			if (p != 0) {
				cell[p] += get01(x);
				if (--degree[p] == 0) {
					que[++r] = p;
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1, x; i <= n; i++) {
			for (int j = 1; j <= 3; j++) {
				x = in.nextInt();
				parent[x] = i;
				if (x <= n) {
					fa[x] = i;
				}
			}
		}
		for (int i = n + 1; i <= 3 * n + 1; i++) {
			sign[i] = in.nextInt();
		}
		topo();
		ans = get01(1);
		q = in.nextInt();
		for (int i = 1, x; i <= q; i++) {
			x = in.nextInt();
			change(x);
			out.println(ans);
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
