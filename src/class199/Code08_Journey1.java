package class199;

// 旅行，java版
// 图中有n个点、m条无向边，节点编号1~n，图是普通树或者基环树
// 对于普通树，从1号节点开始，选择不同的遍历顺序，会形成不同的dfs序列
// 其中字典序最小的dfs序列，叫做普通树的答案
// 对于基环树，可以删掉环上的任何一条边，让基环树变成某棵普通树
// 众多普通树的答案中，字典序最小的结果，叫做基环树的答案
// 图如果是普通树，打印普通树的答案，图如果是基环树，打印基环树的答案
// 1 <= n <= 5 * 10^5
// 普通版测试 : https://www.luogu.com.cn/problem/P5022
// 加强版测试 : https://www.luogu.com.cn/problem/P5049
// 提交以下的code，提交时请把类名改成"Main"，可以通过普通版测试
// 加强版测试卡常数时间，java实现无法通过，索性递归函数也不改迭代了
// 想通过用C++实现，本节课Code08_Journey2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code08_Journey1 {

	public static int MAXN = 500001;
	public static int n, m;
	public static int[][] arr = new int[MAXN << 1][2];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int cntd;

	public static int[] from = new int[MAXN];
	public static boolean[] cycle = new boolean[MAXN];

	public static boolean turn;
	public static boolean[] vis = new boolean[MAXN];
	public static int[] ans = new int[MAXN];
	public static int cnta;

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void dfs(int u) {
		dfn[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				from[v] = u;
				dfs(v);
			} else if (dfn[u] < dfn[v]) {
				cycle[u] = true;
				for (int i = v; i != u; i = from[i]) {
					cycle[i] = true;
				}
			}
		}
	}

	public static void path(int u, int back) {
		vis[u] = true;
		ans[++cnta] = u;
		if (!cycle[u] || turn) {
			// u不在环上 或者 已经回过头了
			// 那么像在树上遍历即可
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (!vis[v]) {
					path(v, n + 1);
				}
			}
			return;
		}
		// u在环上 并且 还没回过头
		// 考察最后一个儿子是否符合回头需要
		int end = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				end = v;
			}
		}
		turn = cycle[end] && end > back;
		if (turn) {
			// 回头成功
			// 无视最后一个儿子
			// 因为会从环的另一侧到达
			// 其他儿子像在树上遍历即可
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (!vis[v] && v != end) {
					path(v, n + 1);
				}
			}
			return;
		}
		// 回头不成功
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				if (!cycle[v]) {
					// 当前儿子v不在环上
					// 像在树上遍历即可
					path(v, n + 1);
				} else {
					// 当前儿子v在环上
					// 设置好后续的back
					// 回头可能发生在后续的环上
					// 因为节点u最多两个环上的孩子
					// 所以如下for循环最多发生两次
					// 所以时间复杂度不会变高
					int next = back;
					for (int ne = nxt[e]; ne > 0; ne = nxt[ne]) {
						int nv = to[ne];
						if (!vis[nv]) {
							next = nv;
							break;
						}
					}
					path(v, next);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			arr[i][0] = u;
			arr[i][1] = v;
			arr[i + m][0] = v;
			arr[i + m][1] = u;
		}
		Arrays.sort(arr, 1, m * 2 + 1, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
		for (int i = 1; i <= m * 2; i++) {
			addEdge(arr[i][0], arr[i][1]);
		}
		dfs(1);
		path(1, n + 1);
		for (int i = 1; i <= n; i++) {
			out.print(ans[i] + " ");
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