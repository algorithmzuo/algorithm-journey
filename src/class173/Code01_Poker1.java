package class173;

// 由乃打扑克，java版
// 给定一个长度为n的数组arr，接下来有m条操作，操作类型如下
// 操作 1 l r v : 查询arr[l..r]范围上，第v小的数
// 操作 2 l r v : arr[l..r]范围上每个数加v，v可能是负数
// 1 <= n、m <= 10^5
// -2 * 10^4 <= 数组中的值 <= +2 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P5356
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code01_Poker1 {

	public static int MAXN = 100001;
	public static int MAXB = 1001;
	public static int n, m;

	public static int[] arr = new int[MAXN];
	public static int[] sortv = new int[MAXN];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];
	public static int[] lazy = new int[MAXB];

	public static void innerAdd(int l, int r, int v) {
		for (int i = l; i <= r; i++) {
			arr[i] += v;
		}
		for (int i = bl[bi[l]]; i <= br[bi[l]]; i++) {
			sortv[i] = arr[i];
		}
		Arrays.sort(sortv, bl[bi[l]], br[bi[l]] + 1);
	}

	public static void add(int l, int r, int v) {
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

	public static int getMin(int l, int r) {
		int lb = bi[l], rb = bi[r], ans = 10000000;
		if (lb == rb) {
			for (int i = l; i <= r; i++) {
				ans = Math.min(ans, arr[i] + lazy[lb]);
			}
		} else {
			for (int i = l; i <= br[lb]; i++) {
				ans = Math.min(ans, arr[i] + lazy[lb]);
			}
			for (int i = bl[rb]; i <= r; i++) {
				ans = Math.min(ans, arr[i] + lazy[rb]);
			}
			for (int i = lb + 1; i <= rb - 1; i++) {
				ans = Math.min(ans, sortv[bl[i]] + lazy[i]);
			}
		}
		return ans;
	}

	public static int getMax(int l, int r) {
		int lb = bi[l], rb = bi[r], ans = -10000000;
		if (lb == rb) {
			for (int i = l; i <= r; i++) {
				ans = Math.max(ans, arr[i] + lazy[lb]);
			}
		} else {
			for (int i = l; i <= br[lb]; i++) {
				ans = Math.max(ans, arr[i] + lazy[lb]);
			}
			for (int i = bl[rb]; i <= r; i++) {
				ans = Math.max(ans, arr[i] + lazy[rb]);
			}
			for (int i = lb + 1; i <= rb - 1; i++) {
				ans = Math.max(ans, sortv[br[i]] + lazy[i]);
			}
		}
		return ans;
	}

	// 返回第i块内<= v的数字个数
	public static int blockCnt(int i, int v) {
		v -= lazy[i];
		int l = bl[i], r = br[i];
		if (sortv[l] > v) {
			return 0;
		}
		if (sortv[r] <= v) {
			return r - l + 1;
		}
		int m, find = l;
		while (l <= r) {
			m = (l + r) / 2;
			if (sortv[m] <= v) {
				find = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return find - bl[i] + 1;
	}

	// 返回arr[l..r]范围上<= v的数字个数
	public static int getCnt(int l, int r, int v) {
		int lb = bi[l], rb = bi[r], ans = 0;
		if (lb == rb) {
			for (int i = l; i <= r; i++) {
				if (arr[i] + lazy[lb] <= v) {
					ans++;
				}
			}
		} else {
			for (int i = l; i <= br[lb]; i++) {
				if (arr[i] + lazy[lb] <= v) {
					ans++;
				}
			}
			for (int i = bl[rb]; i <= r; i++) {
				if (arr[i] + lazy[rb] <= v) {
					ans++;
				}
			}
			for (int i = lb + 1; i <= rb - 1; i++) {
				ans += blockCnt(i, v);
			}
		}
		return ans;
	}

	public static int query(int l, int r, int k) {
		if (k < 1 || k > r - l + 1) {
			return -1;
		}
		int minv = getMin(l, r);
		int maxv = getMax(l, r);
		int midv;
		int ans = -1;
		while (minv <= maxv) {
			midv = minv + (maxv - minv) / 2;
			if (getCnt(l, r, midv) >= k) {
				ans = midv;
				maxv = midv - 1;
			} else {
				minv = midv + 1;
			}
		}
		return ans;
	}

	// 注意调整块长
	public static void prepare() {
		blen = (int) Math.sqrt(n / 2);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, n);
		}
		for (int i = 1; i <= n; i++) {
			sortv[i] = arr[i];
		}
		for (int i = 1; i <= bnum; i++) {
			Arrays.sort(sortv, bl[i], br[i] + 1);
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		prepare();
		for (int i = 1, op, l, r, v; i <= m; i++) {
			op = in.nextInt();
			l = in.nextInt();
			r = in.nextInt();
			v = in.nextInt();
			if (op == 1) {
				out.println(query(l, r, v));
			} else {
				add(l, r, v);
			}
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