package class179;

// 由乃的玉米田，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5355
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code08_CornField1 {

	static class BitSet {
		int len;
		long[] status;

		public BitSet(int siz) {
			len = (siz + 63) >> 6;
			status = new long[len];
		}

		public void setOne(int bit) {
			status[bit >> 6] |= 1L << (bit & 63);
		}

		public void setZero(int bit) {
			status[bit >> 6] &= ~(1L << (bit & 63));
		}

		public boolean status(int bit) {
			return ((status[bit >> 6] >> (bit & 63)) & 1L) != 0L;
		}

		public boolean andOther(BitSet other) {
			for (int i = 0; i < len; i++) {
				if ((status[i] & other.status[i]) != 0L) {
					return true;
				}
			}
			return false;
		}

		// 检查 自己 & 自己左移k位 是否有非零位
		public boolean andSelfMoveLeft(int k) {
			int ws = k >> 6;
			int bs = k & 63;
			for (int i = len - 1; i >= 0; i--) {
				int src = i - ws;
				if (src < 0) {
					break;
				}
				long shifted = (status[src] << bs);
				if (bs != 0 && src - 1 >= 0) {
					shifted |= (status[src - 1] >>> (64 - bs));
				}
				if ((status[i] & shifted) != 0L) {
					return true;
				}
			}
			return false;
		}

		// 检查 自己 & other右移k位 是否有非零位
		public boolean andOtherMoveRight(BitSet other, int k) {
			int ws = k >> 6;
			int bs = k & 63;
			for (int i = 0; i < len; i++) {
				int src = i + ws;
				if (src >= len) {
					break;
				}
				long shifted = (other.status[src] >>> bs);
				if (bs != 0 && src + 1 < len) {
					shifted |= (other.status[src + 1] << (64 - bs));
				}
				if ((status[i] & shifted) != 0L) {
					return true;
				}
			}
			return false;
		}

	}

	public static int MAXN = 100001;
	public static int MAXV = 100000;
	public static int MAXB = 401;
	public static int n, m, blen;
	public static int[] arr = new int[MAXN];
	public static int[] bi = new int[MAXN];

	// 普通查询，op、l、r、x、id
	public static int[][] query = new int[MAXN][5];
	public static int cntq;

	// 特别查询，l、r、id
	public static int[] headq = new int[MAXB];
	public static int[] nextq = new int[MAXN];
	public static int[] ql = new int[MAXN];
	public static int[] qr = new int[MAXN];
	public static int[] qid = new int[MAXN];
	public static int cnts;

	public static BitSet bitSet1 = new BitSet(MAXN);
	public static BitSet bitSet2 = new BitSet(MAXN);

	public static int[] cnt = new int[MAXN];
	public static int[] pre = new int[MAXN];
	public static int[] maxLeft = new int[MAXN];

	public static boolean[] ans = new boolean[MAXN];

	public static void addSpecial(int x, int l, int r, int id) {
		nextq[++cnts] = headq[x];
		headq[x] = cnts;
		ql[cnts] = l;
		qr[cnts] = r;
		qid[cnts] = id;
	}

	public static class QueryCmp implements Comparator<int[]> {
		@Override
		public int compare(int[] a, int[] b) {
			if (bi[a[1]] != bi[b[1]]) {
				return bi[a[1]] - bi[b[1]];
			}
			if ((bi[a[1]] & 1) == 1) {
				return a[2] - b[2];
			} else {
				return b[2] - a[2];
			}
		}
	}

	public static void add(int x) {
		cnt[x]++;
		if (cnt[x] == 1) {
			bitSet1.setOne(x);
			bitSet2.setOne(MAXV - x);
		}
	}

	public static void del(int x) {
		cnt[x]--;
		if (cnt[x] == 0) {
			bitSet1.setZero(x);
			bitSet2.setZero(MAXV - x);
		}
	}

	public static boolean getAns(int op, int x) {
		if (op == 1) {
			return bitSet1.andSelfMoveLeft(x);
		} else if (op == 2) {
			return bitSet1.andOtherMoveRight(bitSet2, MAXV - x);
		} else if (op == 3) {
			for (int d = 1; d * d <= x; d++) {
				if (x % d == 0) {
					if (bitSet1.status(d) && bitSet1.status(x / d)) {
						return true;
					}
				}
			}
			return false;
		} else {
			if (x >= 1) {
				for (int i = 1; i * x <= MAXV; i++) {
					if (bitSet1.status(i) && bitSet1.status(i * x)) {
						return true;
					}
				}
			}
			return false;
		}
	}

	public static void compute() {
		int winl = 1, winr = 0;
		for (int i = 1; i <= cntq; i++) {
			int op = query[i][0];
			int jobl = query[i][1];
			int jobr = query[i][2];
			int jobx = query[i][3];
			int id = query[i][4];
			while (winl > jobl) {
				add(arr[--winl]);
			}
			while (winr < jobr) {
				add(arr[++winr]);
			}
			while (winl < jobl) {
				del(arr[winl++]);
			}
			while (winr > jobr) {
				del(arr[winr--]);
			}
			ans[id] = getAns(op, jobx);
		}
	}

	public static void special() {
		for (int x = 1; x < blen; x++) {
			if (headq[x] != 0) {
				Arrays.fill(pre, 0);
				Arrays.fill(maxLeft, 0);
				int last = 0;
				for (int i = 1; i <= n; i++) {
					int val = arr[i];
					pre[val] = i;
					if (val * x <= MAXV) {
						last = Math.max(last, pre[val * x]);
					}
					if (val % x == 0) {
						last = Math.max(last, pre[val / x]);
					}
					maxLeft[i] = last;
				}
				for (int q = headq[x]; q > 0; q = nextq[q]) {
					int l = ql[q];
					int r = qr[q];
					int id = qid[q];
					ans[id] = l <= maxLeft[r];
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		blen = (int) Math.sqrt(n);
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1, op, l, r, x; i <= m; i++) {
			op = in.nextInt();
			l = in.nextInt();
			r = in.nextInt();
			x = in.nextInt();
			if (op == 4 && x < blen) {
				addSpecial(x, l, r, i);
			} else {
				query[++cntq][0] = op;
				query[cntq][1] = l;
				query[cntq][2] = r;
				query[cntq][3] = x;
				query[cntq][4] = i;
			}
		}
		Arrays.sort(query, 1, cntq + 1, new QueryCmp());
		compute();
		special();
		for (int i = 1; i <= m; i++) {
			if (ans[i]) {
				out.println("yuno");
			} else {
				out.println("yumi");
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
