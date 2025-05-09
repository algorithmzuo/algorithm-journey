package class168;

// 陨石雨，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3527
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但无法通过所有测试用例，内存使用过大
// 因为这道题只考虑C++能通过的空间极限，根本没考虑java的用户
// 想通过用C++实现，本节课Code01_Meteors2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_Meteors1 {

	public static int MAXN = 300001;
	public static int n, m, k;

	public static int[] arr = new int[MAXN];
	public static int[] need = new int[MAXN];

	public static int[] rainl = new int[MAXN];
	public static int[] rainr = new int[MAXN];
	public static int[] num = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int cnt = 0;

	public static long[] tree = new long[MAXN << 1];

	public static int[] lset = new int[MAXN];
	public static int[] rset = new int[MAXN];

	public static int[] ans = new int[MAXN];

	public static void addEdge(int i, int v) {
		next[++cnt] = head[i];
		to[cnt] = v;
		head[i] = cnt;
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		int siz = m * 2;
		while (i <= siz) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static void add(int l, int r, int v) {
		add(l, v);
		add(r + 1, -v);
	}

	public static long query(int i) {
		long ret = 0;
		while (i > 0) {
			ret += tree[i];
			i -= lowbit(i);
		}
		return ret;
	}

	public static void compute(int al, int ar, int tl, int tr) {
		if (tl == tr) {
			for (int i = al; i <= ar; i++) {
				ans[arr[i]] = tl;
			}
		} else {
			int mid = (tl + tr) >> 1;
			int lsiz = 0, rsiz = 0, nation;
			long satisfy;
			for (int i = tl; i <= mid; i++) {
				add(rainl[i], rainr[i], num[i]);
			}
			for (int i = al; i <= ar; i++) {
				nation = arr[i];
				satisfy = 0;
				for (int e = head[nation]; e > 0; e = next[e]) {
					satisfy += query(to[e]) + query(to[e] + m);
					if (satisfy >= need[nation]) {
						break;
					}
				}
				if (satisfy >= need[nation]) {
					lset[++lsiz] = nation;
				} else {
					need[nation] -= satisfy;
					rset[++rsiz] = nation;
				}
			}
			for (int i = tl; i <= mid; i++) {
				add(rainl[i], rainr[i], -num[i]);
			}
			for (int i = 1; i <= lsiz; i++) {
				arr[al + i - 1] = lset[i];
			}
			for (int i = 1; i <= rsiz; i++) {
				arr[al + lsiz + i - 1] = rset[i];
			}
			compute(al, al + lsiz - 1, tl, mid);
			compute(al + lsiz, ar, mid + 1, tr);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, nation; i <= m; i++) {
			nation = in.nextInt();
			addEdge(nation, i);
		}
		for (int i = 1; i <= n; i++) {
			arr[i] = i;
			need[i] = in.nextInt();
		}
		k = in.nextInt();
		for (int i = 1; i <= k; i++) {
			rainl[i] = in.nextInt();
			rainr[i] = in.nextInt();
			if (rainr[i] < rainl[i]) {
				rainr[i] += m;
			}
			num[i] = in.nextInt();
		}
		compute(1, n, 1, k + 1);
		for (int i = 1; i <= n; i++) {
			if (ans[i] == k + 1) {
				out.println("NIE");
			} else {
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
