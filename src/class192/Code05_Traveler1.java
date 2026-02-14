package class192;

// 旅行者，java版
// 测试链接 : https://www.luogu.com.cn/problem/P7924
// 提交以下的code，提交时请把类名改成"Main"
// 逻辑一定是正确的，但是本题卡常，并且很多递归函数需要改成迭代版，索性算了
// 想通过用C++实现，本节课Code05_Traveler2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Traveler1 {

	public static int MAXN = 500001;
	public static int MAXM = 2000001;
	public static int MAXP = 20;
	public static int n, m, q;
	public static int[] arr = new int[MAXN];
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int[] val = new int[MAXN];
	public static int ebccCnt;

	public static int[] lg2 = new int[MAXN];
	public static int[][] rmq = new int[MAXN][MAXP];

	public static int[] pass = new int[MAXN];
	public static int ans;

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
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
				val[ebccCnt] += arr[pop];
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
			if (ebcc1 != ebcc2) {
				addEdge(ebcc1, ebcc2);
				addEdge(ebcc2, ebcc1);
			}
		}
	}

	public static int getUp(int x, int y) {
		return dfn[x] < dfn[y] ? x : y;
	}

	public static void dfs(int u, int fa) {
		dfn[u] = ++cntd;
		rmq[dfn[u]][0] = fa;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dfs(v, u);
			}
		}
	}

	public static void buildRmq() {
		cntd = 0;
		dfs(1, 0);
		for (int i = 2; i <= ebccCnt; i++) {
			lg2[i] = lg2[i >> 1] + 1;
		}
		for (int pre = 0, cur = 1; cur <= lg2[ebccCnt]; pre++, cur++) {
			for (int i = 1; i + (1 << cur) - 1 <= ebccCnt; i++) {
				rmq[i][cur] = getUp(rmq[i][pre], rmq[i + (1 << pre)][pre]);
			}
		}
	}

	public static int getFather(int x) {
		return rmq[dfn[x]][0];
	}

	public static int getLCA(int x, int y) {
		if (x == y) {
			return x;
		}
		x = dfn[x];
		y = dfn[y];
		if (x > y) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		x++;
		int k = lg2[y - x + 1];
		return getUp(rmq[x][k], rmq[y - (1 << k) + 1][k]);
	}

	public static void getAns(int u, int fa) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				getAns(v, u);
				pass[u] += pass[v];
			}
		}
		if (pass[u] > 0) {
			ans += val[u];
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		cntg = 1;
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			addEdge(a[i], b[i]);
			addEdge(b[i], a[i]);
		}
		tarjan(1, 0);
		condense();
		buildRmq();
		q = in.nextInt();
		for (int i = 1, x, y, xylca, lcafa; i <= q; i++) {
			x = in.nextInt();
			y = in.nextInt();
			x = belong[x];
			y = belong[y];
			xylca = getLCA(x, y);
			lcafa = getFather(xylca);
			pass[x]++;
			pass[y]++;
			pass[xylca]--;
			pass[lcafa]--;
		}
		getAns(1, 0);
		out.println(ans);
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
