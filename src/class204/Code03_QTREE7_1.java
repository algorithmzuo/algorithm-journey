package class204;

// QTREE7，java版
// 给定一棵n个节点的树，每个节点有黑白两种颜色，给定初始颜色和点权
// 接下来有q条操作，操作类型如下
// 操作 0 x   : 打印节点x所在的同色连通块中的最大点权
// 操作 1 x   : 翻转节点x的颜色
// 操作 2 x w : 节点x的点权修改为w
// 1 <= n、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/SP16580
// 测试链接 : https://www.spoj.com/problems/QTREE7/
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.TreeMap;

public class Code03_QTREE7_1 {

	public static int MAXN = 200001;
	public static int INF = 1000000001;
	public static int n, q;

	// 节点的初始颜色、初始点权
	public static int[] color = new int[MAXN];
	public static int[] weight = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int cntg;

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	public static int[] parent = new int[MAXN];

	// 节点x有两个状态
	// val[x] != -INF，说明节点x为黑，此时黑色状态的权值就是点权
	// val[x + n] != -INF，说明节点x为白，此时白色状态的权值就是点权
	// 一定有一个状态是节点点权，另一个状态是-INF
	public static int[] val = new int[MAXN];

	// 保存状态x每个直接虚儿子的完整子树最大值，以及出现次数
	public static HashMap<Integer, TreeMap<Integer, Integer>> vir = new HashMap<>();

	// maxv[x]表示以状态节点x为根的辅助splay汇总的最大点权
	// 包括x自身、x的虚子树、splay中的左右儿子
	public static int[] maxv = new int[MAXN];

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void insert(int x, int v) {
		if (!vir.containsKey(x)) {
			vir.put(x, new TreeMap<>());
		}
		TreeMap<Integer, Integer> map = vir.get(x);
		map.put(v, map.getOrDefault(v, 0) + 1);
	}

	public static void remove(int x, int v) {
		TreeMap<Integer, Integer> map = vir.get(x);
		int cnt = map.get(v);
		if (cnt == 1) {
			map.remove(v);
		} else {
			map.put(v, cnt - 1);
		}
	}

	public static int getmax(int x) {
		if (!vir.containsKey(x) || vir.get(x).isEmpty()) {
			return -INF;
		}
		return vir.get(x).lastKey();
	}

	public static void up(int x) {
		maxv[x] = Math.max(val[x], Math.max(getmax(x), Math.max(maxv[ls[x]], maxv[rs[x]])));
	}

	public static boolean isroot(int x) {
		return ls[fa[x]] != x && rs[fa[x]] != x;
	}

	public static int lr(int x) {
		return ls[fa[x]] == x ? 0 : 1;
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

	// 注意修正虚子树贡献
	public static void access(int x) {
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			if (rs[x] != 0) {
				insert(x, maxv[rs[x]]);
			}
			if (y != 0) {
				remove(x, maxv[y]);
			}
			rs[x] = y;
			up(x);
		}
	}

	public static int findroot(int x) {
		access(x);
		splay(x);
		while (ls[x] != 0) {
			x = ls[x];
		}
		splay(x);
		return x;
	}

	// 连接固定父边(x, f)，x是子，f是父，连接后x作为f的虚儿子
	public static void link(int x, int f) {
		if (f == 0) {
			return;
		}
		access(f);
		splay(f);
		splay(x);
		fa[x] = f;
		insert(f, maxv[x]);
		up(f);
	}

	// 删除固定父边(x, f)，x是子，f是父
	public static void cut(int x, int f) {
		access(x);
		splay(x);
		if (f != 0) {
			int left = ls[x];
			fa[left] = 0;
			ls[x] = 0;
			up(x);
		}
	}

	public static int query(int x) {
		x = val[x] != -INF ? x : x + n;
		int y = findroot(x);
		return val[y] != -INF ? maxv[y] : maxv[rs[y]];
	}

	public static void reverseColor(int x) {
		int pre = val[x] != -INF ? x : x + n;
		int cur = pre <= n ? pre + n : pre - n;
		cut(pre, parent[pre]);
		val[cur] = val[pre];
		val[pre] = -INF;
		link(cur, parent[cur]);
	}

	public static void updateValue(int x, int w) {
		int cur = val[x] != -INF ? x : x + n;
		access(cur);
		splay(cur);
		val[cur] = w;
		up(cur);
	}

	public static void dfs(int u, int f) {
		if (f != 0) {
			parent[u] = f;
			parent[u + n] = f + n;
		}
		for (int e = head[u]; e != 0; e = nxt[e]) {
			int v = to[e];
			if (v != f) {
				dfs(v, u);
				int cur = color[v] == 0 ? v : v + n;
				link(cur, parent[cur]);
			}
		}
	}

	public static void prepare() {
		maxv[0] = -INF;
		// 根据初始颜色，设置黑白状态的贡献和最大值
		for (int i = 1; i <= n; i++) {
			if (color[i] == 0) {
				val[i] = maxv[i] = weight[i];
				val[i + n] = maxv[i + n] = -INF;
			} else {
				val[i] = maxv[i] = -INF;
				val[i + n] = maxv[i + n] = weight[i];
			}
		}
		dfs(1, 0);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1; i <= n; i++) {
			color[i] = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			weight[i] = in.nextInt();
		}
		prepare();
		q = in.nextInt();
		for (int i = 1, op, x, w; i <= q; i++) {
			op = in.nextInt();
			x = in.nextInt();
			if (op == 0) {
				out.println(query(x));
			} else if (op == 1) {
				reverseColor(x);
			} else {
				w = in.nextInt();
				updateValue(x, w);
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