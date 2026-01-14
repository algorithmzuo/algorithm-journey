package class188;

// 邮递员，java版
// 一共n个点，m条有向边，不会有重边和自环
// 邮递员一定要从1号点开始，最后回到1号点，每条边都要走1次
// 邮递员依次走过的点的编号，就可以形成一个路径
// 给定t个序列，每个序列先给定长度k，然后是k个节点的编号
// 希望每个序列都是路径的连续子段，如果不存在这样的路径，打印"NIE"
// 如果存在这样的路径，先打印"TAK"，然后打印依次走过的节点编号
// 2 <= n <= 5 * 10^4    1 <= m <= 2 * 10^5
// 0 <= t <= 10^4        所有序列的节点总量 <= 2 * 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P3443
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class Code10_Postman1 {

	static class PairMap {
		public HashMap<Long, Integer> map = new HashMap<>();

		public long key(int a, int b) {
			return (((long) a) << 32) | (b & 0xffffffffL);
		}

		public boolean contains(int a, int b) {
			return map.containsKey(key(a, b));
		}

		public boolean isEmpty() {
			return map.isEmpty();
		}

		public void put(int a, int b, int v) {
			map.put(key(a, b), v);
		}

		public int get(int a, int b) {
			return map.get(key(a, b));
		}

		public void remove(int a, int b) {
			map.remove(key(a, b));
		}

		public void clear() {
			map.clear();
		}
	}

	public static int MAXN = 50005;
	public static int MAXM = 200005;
	public static int MAXC = 2000005;
	public static int n, m, t;
	public static int[] u = new int[MAXM];
	public static int[] v = new int[MAXM];
	public static int[] chain = new int[MAXC];

	public static int[] headg = new int[MAXN];
	public static int[] nextg = new int[MAXM];
	public static int[] tog = new int[MAXM];
	public static int cntg;
	public static int edgeCnt;

	public static PairMap pairEdge = new PairMap();
	public static PairMap chainHead = new PairMap();
	public static int[] lastChain = new int[MAXM];

	public static int[] curv = new int[MAXN];
	public static int[] inDeg = new int[MAXN];
	public static int[] outDeg = new int[MAXN];

	public static boolean[] start = new boolean[MAXM];
	public static int[] etoe = new int[MAXM];
	public static boolean[] vis = new boolean[MAXM];

	public static int[] sta = new int[MAXM];
	public static int top;

	public static int[] path = new int[MAXM];
	public static int cntp;

	public static int[] ans = new int[MAXM];
	public static int cnta;

	public static void addEdge(int x, int y) {
		nextg[++cntg] = headg[x];
		tog[cntg] = y;
		headg[x] = cntg;
	}

	public static boolean linkEdge() {
		for (int i = 1; i <= m; i++) {
			pairEdge.put(u[i], v[i], i);
			start[i] = true;
		}
		int siz = chain[1], l = 2, r = l + siz - 1;
		int a, b, ledge, redge;
		while (siz > 0) {
			ledge = 0;
			for (int i = l; i < r; i++) {
				a = chain[i];
				b = chain[i + 1];
				if (!pairEdge.contains(a, b)) {
					return false;
				}
				redge = pairEdge.get(a, b);
				if (ledge != 0) {
					if (etoe[ledge] != 0 && etoe[ledge] != redge) {
						return false;
					}
					etoe[ledge] = redge;
					start[redge] = false;
				}
				ledge = redge;
			}
			siz = chain[r + 1];
			l = r + 2;
			r = l + siz - 1;
		}
		return true;
	}

	public static int getChainEnd(int edge) {
		while (etoe[edge] != 0) {
			if (vis[edge] == true) {
				return -1;
			}
			vis[edge] = true;
			edge = etoe[edge];
		}
		return edge;
	}

	public static boolean compress() {
		for (int i = 1; i <= m; i++) {
			if (start[i]) {
				int x = u[i];
				int y = v[i];
				if (etoe[i] != 0) {
					int end = getChainEnd(i);
					if (end == -1) {
						return false;
					}
					y = v[end];
					if (chainHead.contains(x, y)) {
						lastChain[i] = chainHead.get(x, y);
					}
					chainHead.put(x, y, i);
				}
				addEdge(x, y);
				outDeg[x]++;
				inDeg[y]++;
				edgeCnt++;
			}
		}
		for (int i = 1; i <= n; i++) {
			if (inDeg[i] != outDeg[i]) {
				return false;
			}
		}
		for (int i = 1; i <= n; i++) {
			curv[i] = headg[i];
		}
		return true;
	}

	public static void euler1(int u) {
		for (int e = curv[u]; e > 0; e = curv[u]) {
			curv[u] = nextg[e];
			euler1(tog[e]);
		}
		path[++cntp] = u;
	}

	public static void euler2(int start) {
		sta[++top] = start;
		while (top > 0) {
			int u = sta[top--];
			int e = curv[u];
			if (e > 0) {
				curv[u] = nextg[e];
				sta[++top] = u;
				sta[++top] = tog[e];
			} else {
				path[++cntp] = u;
			}
		}
	}

	public static boolean decompress() {
		for (int i = cntp; i >= 1; i--) {
			int y = path[i];
			if (cnta == 0) {
				ans[++cnta] = y;
			} else {
				int x = ans[cnta];
				if (!chainHead.contains(x, y)) {
					ans[++cnta] = y;
				} else {
					int cur = chainHead.get(x, y);
					if (lastChain[cur] != 0) {
						chainHead.put(x, y, lastChain[cur]);
					} else {
						chainHead.remove(x, y);
					}
					while (cur > 0) {
						ans[++cnta] = v[cur];
						cur = etoe[cur];
					}
				}
			}
		}
		if (!chainHead.isEmpty() || cnta != m + 1) {
			return false;
		}
		return true;
	}

	public static boolean compute() {
		if (!linkEdge()) {
			return false;
		}
		if (!compress()) {
			return false;
		}
		// euler1(1);
		euler2(1);
		if (cntp != edgeCnt + 1) {
			return false;
		}
		return decompress();
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			u[i] = in.nextInt();
			v[i] = in.nextInt();
		}
		t = in.nextInt();
		for (int i = 1, siz, idx = 0; i <= t; i++) {
			siz = in.nextInt();
			chain[++idx] = siz;
			for (int j = 1; j <= siz; j++) {
				chain[++idx] = in.nextInt();
			}
		}
		boolean check = compute();
		if (!check) {
			out.println("NIE");
		} else {
			out.println("TAK");
			for (int i = 1; i <= cnta; i++) {
				out.println(ans[i]);
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