package class192;

// 越狱老虎桥，java版
// 给定一张无向图，一共n个点、m条边，保证所有点连通
// 每条边给定边权，表示破坏这条边需要花费的钱数
// 敌人可能在任意两点之间新增一条边，新增的这条边无法被破坏
// 敌人新增一条边之后，你的目标是只破坏一条边，就让图变成两个连通区
// 你不知道敌人会选择哪两个端点来新增这条边，于是以最差情况来准备钱
// 假设遭遇最差情况，打印完成目标至少的钱数，如果无法完成目标打印-1
// 1 <= n <= 5 * 10^5
// 1 <= m <= 10^6
// 1 <= 边权 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5234
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_PrisonBreak1 {

	public static int MAXN = 500001;
	public static int MAXM = 1000001;
	public static int n, m, maxv;
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];
	public static int[] c = new int[MAXM];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int[] weight = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int ebccCnt;

	public static int[] dist = new int[MAXN];
	public static int diameter;
	public static int edgeCnt;

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	public static void tarjan(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if ((e ^ 1) == preEdge) {
				continue;
			}
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan(v, e);
				low[u] = Math.min(low[u], low[v]);
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
		if (dfn[u] == low[u]) {
			ebccCnt++;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = ebccCnt;
			} while (pop != u);
		}
	}

	public static void condense() {
		cntg = 0;
		for (int i = 1; i <= ebccCnt; i++) {
			head[i] = 0;
		}
		for (int i = 1; i <= m; i++) {
			int ebcc1 = belong[a[i]];
			int ebcc2 = belong[b[i]];
			int w = c[i];
			if (ebcc1 != ebcc2) {
				addEdge(ebcc1, ebcc2, w);
				addEdge(ebcc2, ebcc1, w);
			}
		}
	}

	public static void dpOnTree(int u, int fa, int limit) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dpOnTree(v, u, limit);
				int w = weight[e] <= limit ? 1 : 0;
				edgeCnt += w;
				diameter = Math.max(diameter, dist[u] + dist[v] + w);
				dist[u] = Math.max(dist[u], dist[v] + w);
			}
		}
	}

	public static boolean check(int limit) {
		for (int i = 1; i <= ebccCnt; i++) {
			dist[i] = 0;
		}
		diameter = edgeCnt = 0;
		dpOnTree(1, 0, limit);
		return diameter < edgeCnt;
	}

	public static int compute() {
		int l = 1, r = maxv, mid, ans = -1;
		while (l <= r) {
			mid = (l + r) / 2;
			if (check(mid)) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		cntg = 1;
		n = in.nextInt();
		m = in.nextInt();
		maxv = 0;
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			c[i] = in.nextInt();
			addEdge(a[i], b[i], 0);
			addEdge(b[i], a[i], 0);
			maxv = Math.max(maxv, c[i]);
		}
		tarjan(1, 0);
		condense();
		out.println(compute());
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
