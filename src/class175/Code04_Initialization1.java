package class175;

// 初始化，java版
// 给定一个长度为n的数组arr，接下来有m条操作，操作格式如下
// 操作 1 x y z : 从arr[y]开始，下标每次+x，所有相应位置的数都+z，题目保证 y <= x
// 操作 2 x y   : 打印arr[x..y]的累加和，答案对1000000007取余
// 1 <= n、m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5309
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
// 想通过用C++实现，本节课Code04_Initialization2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_Initialization1 {

	public static int MAXN = 200001;
	public static int MAXB = 501;
	public static int MOD = 1000000007;
	public static int n, m;

	public static long[][] pre = new long[MAXB][MAXB];
	public static long[][] suf = new long[MAXB][MAXB];
	public static long[] arr = new long[MAXN];
	public static long[] sum = new long[MAXB];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];

	public static void add(int x, int y, int z) {
		if (x <= blen) {
			for (int i = y; i <= x; i++) {
				pre[x][i] += z;
			}
			for (int i = y; i >= 1; i--) {
				suf[x][i] += z;
			}
		} else {
			for (int i = y; i <= n; i += x) {
				arr[i] += z;
				sum[bi[i]] += z;
			}
		}
	}

	public static long querySum(int l, int r) {
		int lb = bi[l], rb = bi[r];
		long ans = 0;
		if (lb == rb) {
			for (int i = l; i <= r; i++) {
				ans += arr[i];
			}
		} else {
			for (int i = l; i <= br[lb]; i++) {
				ans += arr[i];
			}
			for (int i = bl[rb]; i <= r; i++) {
				ans += arr[i];
			}
			for (int b = lb + 1; b <= rb - 1; b++) {
				ans += sum[b];
			}
		}
		return ans;
	}

	public static long query(int l, int r) {
		long ans = querySum(l, r);
		for (int x = 1, lb, rb, num; x <= blen; x++) {
			lb = (l - 1) / x + 1;
			rb = (r - 1) / x + 1;
			num = rb - lb - 1;
			if (lb == rb) {
				ans = ans + pre[x][(r - 1) % x + 1] - pre[x][(l - 1) % x];
			} else {
				ans = ans + suf[x][(l - 1) % x + 1] + pre[x][x] * num + pre[x][(r - 1) % x + 1];
			}
		}
		return ans % MOD;
	}

	public static void prepare() {
		blen = (int) Math.sqrt(n);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int b = 1; b <= bnum; b++) {
			bl[b] = (b - 1) * blen + 1;
			br[b] = Math.min(b * blen, n);
			for (int i = bl[b]; i <= br[b]; i++) {
				sum[b] += arr[i];
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
		prepare();
		for (int i = 1, op, x, y, z; i <= m; i++) {
			op = in.nextInt();
			x = in.nextInt();
			y = in.nextInt();
			if (op == 1) {
				z = in.nextInt();
				add(x, y, z);
			} else {
				out.println(query(x, y));
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
