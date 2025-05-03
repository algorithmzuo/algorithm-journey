package class167;

// 火星商店，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4585
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code07_MarsStore1 {

	public static int MAXN = 100001;
	public static int MAXT = 2000001;
	public static int BIT = 16;
	public static int n, m, t;

	public static int[] arr = new int[MAXN];
	public static int[] op = new int[MAXN];
	public static int[] s = new int[MAXN];
	public static int[] v = new int[MAXN];
	public static int[] sl = new int[MAXN];
	public static int[] sr = new int[MAXN];
	public static int[] x = new int[MAXN];
	public static int[] d = new int[MAXN];
	public static int[] tim = new int[MAXN];

	public static int[] root = new int[MAXN];
	public static int[][] tree = new int[MAXT][2];
	public static int[] pass = new int[MAXT];
	public static int cntt = 0;

	public static int[] headp = new int[MAXN << 2];
	public static int[] nextp = new int[MAXT];
	public static int[] pid = new int[MAXT];
	public static int cntp = 0;

	public static int[] headb = new int[MAXN << 2];
	public static int[] nextb = new int[MAXT];
	public static int[] bid = new int[MAXT];
	public static int cntb = 0;

	public static int[][] product = new int[MAXN][2];
	public static int[] ans = new int[MAXN];

	public static int insert(int num, int i) {
		int rt = ++cntt;
		tree[rt][0] = tree[i][0];
		tree[rt][1] = tree[i][1];
		pass[rt] = pass[i] + 1;
		for (int b = BIT, path, pre = rt, cur; b >= 0; b--, pre = cur) {
			path = (num >> b) & 1;
			i = tree[i][path];
			cur = ++cntt;
			tree[cur][0] = tree[i][0];
			tree[cur][1] = tree[i][1];
			pass[cur] = pass[i] + 1;
			tree[pre][path] = cur;
		}
		return rt;
	}

	public static int query(int num, int u, int v) {
		int ans = 0;
		for (int b = BIT, path, best; b >= 0; b--) {
			path = (num >> b) & 1;
			best = path ^ 1;
			if (pass[tree[v][best]] > pass[tree[u][best]]) {
				ans += 1 << b;
				u = tree[u][best];
				v = tree[v][best];
			} else {
				u = tree[u][path];
				v = tree[v][path];
			}
		}
		return ans;
	}

	public static void addInfoP(int i, int pi) {
		nextp[++cntp] = headp[i];
		pid[cntp] = pi;
		headp[i] = cntp;
	}

	public static void addInfoB(int i, int bi) {
		nextb[++cntb] = headb[i];
		bid[cntb] = bi;
		headb[i] = cntb;
	}

	public static void addProduct(int jobi, int pi, int l, int r, int i) {
		addInfoP(i, pi);
		if (l < r) {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				addProduct(jobi, pi, l, mid, i << 1);
			} else {
				addProduct(jobi, pi, mid + 1, r, i << 1 | 1);
			}
		}
	}

	public static void addBuy(int jobl, int jobr, int bi, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			addInfoB(i, bi);
		} else {
			int mid = (l + r) / 2;
			if (jobl <= mid) {
				addBuy(jobl, jobr, bi, l, mid, i << 1);
			}
			if (jobr > mid) {
				addBuy(jobl, jobr, bi, mid + 1, r, i << 1 | 1);
			}
		}
	}

	public static int lower(int size, int num) {
		int l = 1, r = size, ans = size + 1;
		while (l <= r) {
			int mid = (l + r) >> 1;
			if (product[mid][0] >= num) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

	public static int upper(int size, int num) {
		int l = 1, r = size, ans = 0;
		while (l <= r) {
			int mid = (l + r) >> 1;
			if (product[mid][0] <= num) {
				ans = mid;
				l = mid + 1;
			} else {
				r = mid - 1;
			}
		}
		return ans;
	}

	public static void dfs(int l, int r, int i) {
		int pcnt = 0;
		for (int e = headp[i]; e > 0; e = nextp[e]) {
			product[++pcnt][0] = s[pid[e]];
			product[pcnt][1] = v[pid[e]];
		}
		Arrays.sort(product, 1, pcnt + 1, (a, b) -> a[0] - b[0]);
		cntt = 0;
		for (int k = 1; k <= pcnt; k++) {
			root[k] = insert(product[k][1], root[k - 1]);
		}
		for (int e = headb[i], id, pre, post; e > 0; e = nextb[e]) {
			id = bid[e];
			pre = lower(pcnt, sl[id]) - 1;
			post = upper(pcnt, sr[id]);
			ans[id] = Math.max(ans[id], query(x[id], root[pre], root[post]));
		}
		if (l < r) {
			int mid = (l + r) >> 1;
			dfs(l, mid, i << 1);
			dfs(mid + 1, r, i << 1 | 1);
		}
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			root[i] = insert(arr[i], root[i - 1]);
		}
		for (int i = 1; i <= m; i++) {
			if (op[i] == 0) {
				addProduct(tim[i], i, 1, t, 1);
			} else {
				ans[i] = query(x[i], root[sl[i] - 1], root[sr[i]]);
				int start = Math.max(tim[i] - d[i] + 1, 1);
				if (start <= tim[i]) {
					addBuy(start, tim[i], i, 1, t, 1);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		t = 0;
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1; i <= m; i++) {
			op[i] = in.nextInt();
			if (op[i] == 0) {
				t++;
				s[i] = in.nextInt();
				v[i] = in.nextInt();
			} else {
				sl[i] = in.nextInt();
				sr[i] = in.nextInt();
				x[i] = in.nextInt();
				d[i] = in.nextInt();
			}
			tim[i] = t;
		}
		prepare();
		dfs(1, t, 1);
		for (int i = 1; i <= m; i++) {
			if (op[i] == 1) {
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
