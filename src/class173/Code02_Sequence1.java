package class173;

// 序列，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3863
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code02_Sequence1 {

	public static int MAXN = 100001;
	public static int MAXB = 501;
	public static int n, m;
	public static int[] arr = new int[MAXN];

	// op == 1，代表修改事件，位置x、时间t、修改效果v、空缺
	// op == 2，代表查询事件，位置x、时间t、查询标准v、问题编号q
	public static int[][] event = new int[MAXN << 2][5];
	public static int cnte = 0;
	public static int cntq = 0;

	public static long[] tim = new long[MAXN];
	public static long[] sort = new long[MAXN];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];
	public static long[] lazy = new long[MAXB];

	public static int[] ans = new int[MAXN];

	public static void addChange(int x, int t, int v) {
		event[++cnte][0] = 1;
		event[cnte][1] = x;
		event[cnte][2] = t;
		event[cnte][3] = v;
	}

	public static void addQuery(int x, int t, int v) {
		event[++cnte][0] = 2;
		event[cnte][1] = x;
		event[cnte][2] = t;
		event[cnte][3] = v;
		event[cnte][4] = ++cntq;
	}

	public static void innerAdd(int l, int r, long v) {
		for (int i = l; i <= r; i++) {
			tim[i] += v;
		}
		for (int i = bl[bi[l]]; i <= br[bi[l]]; i++) {
			sort[i] = tim[i];
		}
		Arrays.sort(sort, bl[bi[l]], br[bi[l]] + 1);
	}

	public static void add(int l, int r, long v) {
		if (l > r) {
			return;
		}
		if (bi[l] == bi[r]) {
			innerAdd(l, r, v);
		} else {
			innerAdd(l, br[bi[l]], v);
			innerAdd(bl[bi[r]], r, v);
			for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
				lazy[i] += v;
			}
		}
	}

	public static int innerQuery(int l, int r, long v) {
		v -= lazy[bi[l]];
		int ans = 0;
		for (int i = l; i <= r; i++) {
			if (tim[i] >= v) {
				ans++;
			}
		}
		return ans;
	}

	public static int getCnt(int i, long v) {
		v -= lazy[i];
		int l = bl[i], r = br[i], m, pos = br[i] + 1;
		while (l <= r) {
			m = (l + r) >> 1;
			if (sort[m] >= v) {
				pos = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return br[i] - pos + 1;
	}

	public static int query(int l, int r, long v) {
		if (l > r) {
			return 0;
		}
		int ans = 0;
		if (bi[l] == bi[r]) {
			ans += innerQuery(l, r, v);
		} else {
			ans += innerQuery(l, br[bi[l]], v);
			ans += innerQuery(bl[bi[r]], r, v);
			for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
				ans += getCnt(i, v);
			}
		}
		return ans;
	}

	public static void prepare() {
		blen = (int) Math.sqrt(m);
		bnum = (m + blen - 1) / blen;
		for (int i = 1; i <= m; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, m);
		}
		Arrays.sort(event, 1, cnte + 1, (a, b) -> a[1] != b[1] ? a[1] - b[1] : a[2] - b[2]);
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		m++; // 时间轴重新定义，1是初始时刻、2、3 ... m+1
		for (int t = 2, op, l, r, v, x; t <= m; t++) {
			op = in.nextInt();
			if (op == 1) {
				l = in.nextInt();
				r = in.nextInt();
				v = in.nextInt();
				addChange(l, t, v);
				addChange(r + 1, t, -v);
			} else {
				x = in.nextInt();
				v = in.nextInt();
				addQuery(x, t, v);
			}
		}
		prepare();
		for (int i = 1, op, x, t, v, q; i <= cnte; i++) {
			op = event[i][0];
			x = event[i][1];
			t = event[i][2];
			v = event[i][3];
			q = event[i][4];
			if (op == 1) {
				add(t, m, v);
			} else {
				ans[q] = query(1, t - 1, v - arr[x]);
			}
		}
		for (int i = 1; i <= cntq; i++) {
			out.println(ans[i]);
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 20];
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
