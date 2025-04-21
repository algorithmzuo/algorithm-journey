package class166;

// 大融合，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4219
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code05_GreatIntegration1 {

	public static int MAXN = 100001;
	public static int MAXT = 3000001;
	public static int n, q;

	public static int[] op = new int[MAXN];
	public static int[] u = new int[MAXN];
	public static int[] v = new int[MAXN];

	public static int[][] event = new int[MAXN][3];

	public static int[] father = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[][] rollback = new int[MAXN][2];
	public static int opsize = 0;

	public static int[] head = new int[MAXN << 2];
	public static int[] next = new int[MAXT];
	public static int[] tox = new int[MAXT];
	public static int[] toy = new int[MAXT];
	public static int cnt = 0;

	public static long[] ans = new long[MAXN];

	public static void addEdge(int i, int x, int y) {
		next[++cnt] = head[i];
		tox[cnt] = x;
		toy[cnt] = y;
		head[i] = cnt;
	}

	public static int find(int i) {
		while (i != father[i]) {
			i = father[i];
		}
		return i;
	}

	public static void union(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (siz[fx] < siz[fy]) {
			int tmp = fx;
			fx = fy;
			fy = tmp;
		}
		father[fy] = fx;
		siz[fx] += siz[fy];
		rollback[++opsize][0] = fx;
		rollback[opsize][1] = fy;
	}

	public static void undo() {
		int fx = rollback[opsize][0];
		int fy = rollback[opsize--][1];
		father[fy] = fy;
		siz[fx] -= siz[fy];
	}

	public static void add(int jobl, int jobr, int jobx, int joby, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			addEdge(i, jobx, joby);
		} else {
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				add(jobl, jobr, jobx, joby, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobx, joby, mid + 1, r, i << 1 | 1);
			}
		}
	}

	public static void dfs(int l, int r, int i) {
		int unionCnt = 0;
		for (int ei = head[i], fx, fy; ei > 0; ei = next[ei]) {
			fx = find(tox[ei]);
			fy = find(toy[ei]);
			if (fx != fy) {
				union(fx, fy);
				unionCnt++;
			}
		}
		if (l == r) {
			if (op[l] == 2) {
				ans[l] = (long) siz[find(u[l])] * siz[find(v[l])];
			}
		} else {
			int mid = (l + r) >> 1;
			dfs(l, mid, i << 1);
			dfs(mid + 1, r, i << 1 | 1);
		}
		for (int k = 1; k <= unionCnt; k++) {
			undo();
		}
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			siz[i] = 1;
		}
		for (int i = 1; i <= q; i++) {
			event[i][0] = u[i];
			event[i][1] = v[i];
			event[i][2] = i;
		}
		Arrays.sort(event, 1, q + 1, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] != b[1] ? a[1] - b[1] : a[2] - b[2]);
		for (int l = 1, r = 1; l <= q; l = ++r) {
			int x = event[l][0], y = event[l][1], t = event[l][2];
			while (r + 1 <= q && event[r + 1][0] == x && event[r + 1][1] == y) {
				r++;
			}
			for (int i = l + 1; i <= r; i++) {
				add(t, event[i][2] - 1, x, y, 1, q, 1);
				t = event[i][2] + 1;
			}
			if (t <= q) {
				add(t, q, x, y, 1, q, 1);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		q = in.nextInt();
		char t;
		int x, y;
		for (int i = 1; i <= q; i++) {
			t = in.nextChar();
			x = in.nextInt();
			y = in.nextInt();
			op[i] = t == 'A' ? 1 : 2;
			u[i] = Math.min(x, y);
			v[i] = Math.max(x, y);
		}
		prepare();
		dfs(1, q, 1);
		for (int i = 1; i <= q; i++) {
			if (op[i] == 2) {
				out.println(ans[i]);
			}
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		final private int BUFFER_SIZE = 1 << 16;
		private final InputStream in;
		private final byte[] buffer;
		private int ptr, len;

		public FastReader() {
			in = System.in;
			buffer = new byte[BUFFER_SIZE];
			ptr = len = 0;
		}

		private boolean hasNextByte() throws IOException {
			if (ptr < len)
				return true;
			ptr = 0;
			len = in.read(buffer);
			return len > 0;
		}

		private byte readByte() throws IOException {
			if (!hasNextByte())
				return -1;
			return buffer[ptr++];
		}

		public char nextChar() throws IOException {
			byte c;
			do {
				c = readByte();
				if (c == -1)
					return 0;
			} while (c <= ' ');
			char ans = 0;
			while (c > ' ') {
				ans = (char) c;
				c = readByte();
			}
			return ans;
		}

		public int nextInt() throws IOException {
			int num = 0;
			byte b = readByte();
			while (isWhitespace(b))
				b = readByte();
			boolean minus = false;
			if (b == '-') {
				minus = true;
				b = readByte();
			}
			while (!isWhitespace(b) && b != -1) {
				num = num * 10 + (b - '0');
				b = readByte();
			}
			return minus ? -num : num;
		}

		private boolean isWhitespace(byte b) {
			return b == ' ' || b == '\n' || b == '\r' || b == '\t';
		}
	}

}
