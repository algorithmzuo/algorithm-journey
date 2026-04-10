package class196;

// 互质，java版
// 一共n张卡片，每张卡片给定两个待选数字，选择其中一个作为卡片的值
// 确定每张卡片的值之后，如果任意两张卡片的值是互质的，那么算作成功
// 存在成功的选值方案打印"Yes"，不存在打印"No"
// 1 <= n <= 3 * 10^4
// 1 <= 待选数字 <= 2 * 10^6
// 测试链接 : https://www.luogu.com.cn/problem/AT_abc210_f
// 测试链接 : https://atcoder.jp/contests/abc210/tasks/abc210_f
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code07_Coprime1 {

	public static int MAXN = 1000001;
	public static int MAXV = 2000001;
	public static int MAXM = 3000001;
	public static int n, cntt, maxv;
	public static int[] ab = new int[MAXN];

	// 利用欧拉筛生成最小质因子表
	public static boolean[] vis = new boolean[MAXV];
	public static int[] prime = new int[MAXV];
	public static int[] minp = new int[MAXV];
	public static int cntp;

	// 质因子、拥有这个质因子的数字编号
	// 排序后得到每个质因子的数字编号列表
	public static int[][] arr = new int[MAXN][2];
	public static int cnta;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int sccCnt;

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static int other(int x) {
		return x <= n ? (x + n) : (x - n);
	}

	// 欧拉筛，讲解097
	// 利用欧拉筛的过程，生成最小质因子表
	public static void euler() {
		for (int i = 2; i <= maxv; i++) {
			if (!vis[i]) {
				prime[++cntp] = i;
				minp[i] = i;
			}
			for (int j = 1, p, v; j <= cntp; j++) {
				p = prime[j];
				v = i * p;
				if (v > maxv) {
					break;
				}
				vis[v] = true;
				minp[v] = p;
				if (i % p == 0) {
					break;
				}
			}
		}
	}

	// 质因子分解
	public static void decompose() {
		for (int i = 1; i <= n << 1; i++) {
			for (int v = ab[i], p = minp[v]; v > 1; p = minp[v]) {
				arr[++cnta][0] = p;
				arr[cnta][1] = i;
				while (v % p == 0) {
					v /= p;
				}
			}
		}
	}

	// 前后缀优化建图
	public static void link() {
		Arrays.sort(arr, 1, cnta + 1, (a, b) -> a[0] != b[0] ? (a[0] - b[0]) : (a[1] - b[1]));
		for (int l = 1, r = 1; l <= cnta; l = ++r) {
			while (r + 1 <= cnta && arr[l][0] == arr[r + 1][0]) {
				r++;
			}
			cntt++;
			addEdge(cntt, other(arr[l][1]));
			for (int i = l + 1; i <= r; i++) {
				cntt++;
				addEdge(cntt, other(arr[i][1]));
				addEdge(arr[i][1], cntt - 1);
				addEdge(cntt, cntt - 1);
			}
			cntt++;
			addEdge(cntt, other(arr[r][1]));
			for (int i = r - 1; i >= l; i--) {
				cntt++;
				addEdge(cntt, other(arr[i][1]));
				addEdge(arr[i][1], cntt - 1);
				addEdge(cntt, cntt - 1);
			}
		}
	}

	public static void tarjan(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan(v);
				low[u] = Math.min(low[u], low[v]);
			} else {
				if (belong[v] == 0) {
					low[u] = Math.min(low[u], dfn[v]);
				}
			}
		}
		if (dfn[u] == low[u]) {
			sccCnt++;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
			} while (pop != u);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		cntt = n << 1;
		for (int i = 1, a, b; i <= n; i++) {
			a = in.nextInt();
			b = in.nextInt();
			ab[i] = a;
			ab[i + n] = b;
			maxv = Math.max(maxv, Math.max(a, b));
		}
		euler();
		decompose();
		link();
		for (int i = 1; i <= cntt; i++) {
			if (dfn[i] == 0) {
				tarjan(i);
			}
		}
		boolean check = true;
		for (int i = 1; i <= n; i++) {
			if (belong[i] == belong[i + n]) {
				check = false;
				break;
			}
		}
		out.println(check ? "Yes" : "No");
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
