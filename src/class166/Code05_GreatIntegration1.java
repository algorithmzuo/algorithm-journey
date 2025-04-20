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

	public static int[][] event = new int[MAXN][3];
	public static int[][] sorted = new int[MAXN][3];

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
			if (event[l][0] == 2) {
				ans[l] = (long) siz[find(event[l][1])] * siz[find(event[l][2])];
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
			sorted[i][0] = i;
			sorted[i][1] = event[i][1];
			sorted[i][2] = event[i][2];
		}
		Arrays.sort(sorted, 1, q + 1, (a, b) -> a[1] != b[1] ? a[1] - b[1] : a[2] != b[2] ? a[2] - b[2] : a[0] - b[0]);
		for (int l = 1, r = 1; l <= q; l = ++r) {
			int t = sorted[l][0];
			int x = sorted[l][1];
			int y = sorted[l][2];
			while (r + 1 <= q && sorted[r + 1][1] == x && sorted[r + 1][2] == y) {
				r++;
			}
			for (int i = l + 1; i <= r; i++) {
				add(t, sorted[i][0] - 1, x, y, 1, q, 1);
				t = sorted[i][0] + 1;
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
		char op;
		int u, v;
		for (int i = 1; i <= q; i++) {
			op = in.nextChar();
			u = in.nextInt();
			v = in.nextInt();
			event[i][0] = op == 'A' ? 1 : 2;
			event[i][1] = Math.min(u, v);
			event[i][2] = Math.max(u, v);
		}
		prepare();
		dfs(1, q, 1);
		for (int i = 1; i <= q; i++) {
			if (event[i][0] == 2) {
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
