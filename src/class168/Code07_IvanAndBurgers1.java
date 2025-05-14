package class168;

// 范围最大异或和，java版
// 给定一个长度为n的数组arr，下标1~n，接下来有q条查询，格式如下
// 查询 l r : arr[l..r]中选若干个数，打印最大的异或和
// 1 <= n、q <= 5 * 10^5
// 0 <= arr[i] <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/CF1100F
// 测试链接 : https://codeforces.com/problemset/problem/1100/F
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_IvanAndBurgers1 {

	public static int MAXN = 500001;
	public static int BIT = 21;
	public static int n, q;

	public static int[] arr = new int[MAXN];
	public static int[] qid = new int[MAXN];
	public static int[] l = new int[MAXN];
	public static int[] r = new int[MAXN];

	public static int[][] baset = new int[MAXN][BIT + 1];
	public static int[] tmp = new int[BIT + 1];

	public static int[] lset = new int[MAXN];
	public static int[] rset = new int[MAXN];
	public static int[] ans = new int[MAXN];

	public static void insert(int[] basis, int num) {
		for (int i = BIT; i >= 0; i--) {
			if (num >> i == 1) {
				if (basis[i] == 0) {
					basis[i] = num;
					return;
				}
				num ^= basis[i];
			}
		}
	}

	public static void clear(int[] basis) {
		for (int i = 0; i <= BIT; i++) {
			basis[i] = 0;
		}
	}

	public static int maxEor(int[] basis) {
		int ret = 0;
		for (int i = BIT; i >= 0; i--) {
			ret = Math.max(ret, ret ^ basis[i]);
		}
		return ret;
	}

	public static void clone(int[] b1, int[] b2) {
		for (int i = 0; i <= BIT; i++) {
			b1[i] = b2[i];
		}
	}

	public static void merge(int[] b1, int[] b2) {
		clone(tmp, b1);
		for (int i = 0; i <= BIT; i++) {
			insert(tmp, b2[i]);
		}
	}

	public static void compute(int ql, int qr, int vl, int vr) {
		if (ql > qr) {
			return;
		}
		if (vl == vr) {
			for (int i = ql; i <= qr; i++) {
				ans[qid[i]] = arr[vl];
			}
		} else {
			int mid = (vl + vr) >> 1;
			clear(baset[mid]);
			insert(baset[mid], arr[mid]);
			for (int i = mid - 1; i >= vl; i--) {
				clone(baset[i], baset[i + 1]);
				insert(baset[i], arr[i]);
			}
			for (int i = mid + 1; i <= vr; i++) {
				clone(baset[i], baset[i - 1]);
				insert(baset[i], arr[i]);
			}
			int lsiz = 0, rsiz = 0, id;
			for (int i = ql; i <= qr; i++) {
				id = qid[i];
				if (r[id] < mid) {
					lset[++lsiz] = id;
				} else if (l[id] > mid) {
					rset[++rsiz] = id;
				} else {
					merge(baset[l[id]], baset[r[id]]);
					ans[id] = maxEor(tmp);
				}
			}
			for (int i = 1; i <= lsiz; i++) {
				qid[ql + i - 1] = lset[i];
			}
			for (int i = 1; i <= rsiz; i++) {
				qid[ql + lsiz + i - 1] = rset[i];
			}
			compute(ql, ql + lsiz - 1, vl, mid);
			compute(ql + lsiz, ql + lsiz + rsiz - 1, mid + 1, vr);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		q = in.nextInt();
		for (int i = 1; i <= q; i++) {
			qid[i] = i;
			l[i] = in.nextInt();
			r[i] = in.nextInt();
		}
		compute(1, q, 1, n);
		for (int i = 1; i <= q; i++) {
			out.println(ans[i]);
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
