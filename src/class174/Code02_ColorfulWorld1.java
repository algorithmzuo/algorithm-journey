package class174;

// 五彩斑斓的世界，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4117
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
// 想通过用C++实现，本节课Code02_ColorfulWorld2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code02_ColorfulWorld1 {

	public static int MAXN = 1000001;
	public static int n, m;
	public static int blen, bnum;
	public static int maxv, lazy;
	public static int[] arr = new int[MAXN];

	public static int[] op = new int[MAXN];
	public static int[] ql = new int[MAXN];
	public static int[] qr = new int[MAXN];
	public static int[] qx = new int[MAXN];

	public static int[] fa = new int[MAXN];
	public static int[] sum = new int[MAXN];
	public static int[] ans = new int[MAXN];

	public static int find(int x) {
		if (x != fa[x]) {
			fa[x] = find(fa[x]);
		}
		return fa[x];
	}

	public static void union(int x, int y) {
		fa[find(x)] = find(y);
	}

	public static void down(int l, int r) {
		for (int i = l; i <= r; i++) {
			arr[i] = find(arr[i]);
		}
	}

	public static void update(int qi, int l, int r) {
		int jobl = ql[qi], jobr = qr[qi], jobx = qx[qi];
		if (jobx > maxv - lazy || jobl > r || jobr < l) {
			return;
		}
		if (jobl <= l && r <= jobr) {
			if ((jobx << 1) <= maxv - lazy) {
				for (int v = lazy + 1; v <= lazy + jobx; v++) {
					sum[v + jobx] += sum[v];
					sum[v] = 0;
					union(v, v + jobx);
				}
				lazy += jobx;
			} else {
				for (int v = maxv; v > lazy + jobx; v--) {
					sum[v - jobx] += sum[v];
					sum[v] = 0;
					union(v, v - jobx);
				}
				for (int v = maxv; v >= 0; v--) {
					if (sum[v] != 0) {
						maxv = v;
						break;
					}
				}
			}
		} else {
			down(l, r);
			for (int i = Math.max(l, jobl); i <= Math.min(r, jobr); i++) {
				if (arr[i] - lazy > jobx) {
					sum[arr[i]]--;
					arr[i] -= jobx;
					sum[arr[i]]++;
				}
			}
			for (int v = maxv; v >= 0; v--) {
				if (sum[v] != 0) {
					maxv = v;
					break;
				}
			}
		}
	}

	public static void query(int qi, int l, int r) {
		int jobl = ql[qi], jobr = qr[qi], jobx = qx[qi];
		if (jobx == 0) {
			return;
		}
		if (jobx > maxv - lazy || jobl > r || jobr < l) {
			return;
		}
		if (jobl <= l && r <= jobr) {
			ans[qi] += sum[jobx + lazy];
		} else {
			down(l, r);
			for (int i = Math.max(l, jobl); i <= Math.min(r, jobr); i++) {
				if (arr[i] - lazy == jobx) {
					ans[qi]++;
				}
			}
		}
	}

	public static void compute(int l, int r) {
		Arrays.fill(sum, 0);
		maxv = lazy = 0;
		for (int i = l; i <= r; i++) {
			maxv = Math.max(maxv, arr[i]);
			sum[arr[i]]++;
		}
		for (int v = 0; v <= maxv; v++) {
			fa[v] = v;
		}
		for (int i = 1; i <= m; i++) {
			if (op[i] == 1) {
				update(i, l, r);
			} else {
				query(i, l, r);
			}
		}
	}

	public static void prepare() {
		blen = (int) Math.sqrt(n * 3.0);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			sum[i] = sum[i - 1] + (arr[i] == 0 ? 1 : 0);
		}
		for (int i = 1; i <= m; i++) {
			if (op[i] == 2 && qx[i] == 0) {
				ans[i] = sum[qr[i]] - sum[ql[i] - 1];
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1; i <= m; i++) {
			op[i] = in.nextInt();
			ql[i] = in.nextInt();
			qr[i] = in.nextInt();
			qx[i] = in.nextInt();
		}
		prepare();
		for (int i = 1, l, r; i <= bnum; i++) {
			l = (i - 1) * blen + 1;
			r = Math.min(i * blen, n);
			compute(l, r);
		}
		for (int i = 1; i <= m; i++) {
			if (op[i] == 2) {
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
