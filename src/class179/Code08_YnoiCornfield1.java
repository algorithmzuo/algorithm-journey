package class179;

// 由乃的玉米田，java版
// 给定一个长度为n的数组arr，接下来有m条查询，查询格式如下
// 查询 1 l r x : 打印arr[l..r]范围上能否选出两个数，减的结果为x
// 查询 2 l r x : 打印arr[l..r]范围上能否选出两个数，加的结果为x
// 查询 3 l r x : 打印arr[l..r]范围上能否选出两个数，乘的结果为x
// 查询 4 l r x : 打印arr[l..r]范围上能否选出两个数，除的结果为x，并且没有余数
// 选出的这两个数可以是同一个位置的数，答案如果为是，打印 "yuno"，否则打印 "yumi"
// 1 <= 所有数据 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5355
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code08_YnoiCornfield1 {

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

		public boolean getStatus(int bit) {
			return ((status[bit >> 6] >> (bit & 63)) & 1L) != 0L;
		}

		// 检查 自己 & other右移k位 是否有1存在
		public boolean andOtherMoveRight(BitSet other, int move) {
			int ws = move >> 6;
			int bs = move & 63;
			for (int i = 0; i < len; i++) {
				int src = i + ws;
				if (src >= len) {
					break;
				}
				long shifted = other.status[src] >>> bs;
				if (bs != 0 && src + 1 < len) {
					shifted |= other.status[src + 1] << (64 - bs);
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

	// 普通查询，l、r、x、op、id
	public static int[][] query = new int[MAXN][5];
	public static int cntq;

	// 特别查询，x的问题列表 : l、r、id
	public static int[] headq = new int[MAXB];
	public static int[] nextq = new int[MAXN];
	public static int[] ql = new int[MAXN];
	public static int[] qr = new int[MAXN];
	public static int[] qid = new int[MAXN];
	public static int cnts;

	// 数字出现的词频
	public static int[] cnt = new int[MAXN];
	public static BitSet bitSet1 = new BitSet(MAXN);
	public static BitSet bitSet2 = new BitSet(MAXN);

	// 特别查询的dp过程
	public static int[] pre = new int[MAXN];
	public static int[] dp = new int[MAXN];

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
			if (bi[a[0]] != bi[b[0]]) {
				return bi[a[0]] - bi[b[0]];
			}
			if ((bi[a[0]] & 1) == 1) {
				return a[1] - b[1];
			} else {
				return b[1] - a[1];
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

	public static boolean calc(int op, int x) {
		if (op == 1) {
			return bitSet1.andOtherMoveRight(bitSet1, x);
		} else if (op == 2) {
			return bitSet1.andOtherMoveRight(bitSet2, MAXV - x);
		} else if (op == 3) {
			for (int f = 1; f * f <= x; f++) {
				if (x % f == 0 && bitSet1.getStatus(f) && bitSet1.getStatus(x / f)) {
					return true;
				}
			}
			return false;
		} else {
			if (x >= 1) {
				for (int i = 1; i * x <= MAXV; i++) {
					if (bitSet1.getStatus(i) && bitSet1.getStatus(i * x)) {
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
			int jobl = query[i][0];
			int jobr = query[i][1];
			int jobx = query[i][2];
			int op = query[i][3];
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
			ans[id] = calc(op, jobx);
		}
	}

	public static void special() {
		for (int x = 1; x < blen; x++) {
			if (headq[x] != 0) {
				Arrays.fill(pre, 0);
				Arrays.fill(dp, 0);
				for (int i = 1; i <= n; i++) {
					int v = arr[i];
					pre[v] = i;
					dp[i] = dp[i - 1];
					if (v * x <= MAXV) {
						dp[i] = Math.max(dp[i], pre[v * x]);
					}
					if (v % x == 0) {
						dp[i] = Math.max(dp[i], pre[v / x]);
					}
				}
				for (int q = headq[x]; q > 0; q = nextq[q]) {
					int l = ql[q];
					int r = qr[q];
					int id = qid[q];
					ans[id] = l <= dp[r];
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
				query[++cntq][0] = l;
				query[cntq][1] = r;
				query[cntq][2] = x;
				query[cntq][3] = op;
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
