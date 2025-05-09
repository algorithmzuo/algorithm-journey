package class168;

// 混合果汁，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4602
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code02_MixedJuice1 {

	public static int MAXN = 100001;
	public static int n, m;
	// 果汁参数 : 美味度d、每升价格p、添加上限l
	public static int[][] juice = new int[MAXN][3];
	// 记录所有小朋友的编号
	public static int[] arr = new int[MAXN];
	// 小朋友的钱数
	public static long[] money = new long[MAXN];
	// 小朋友需要至少多少升果汁
	public static long[] least = new long[MAXN];

	// 线段树
	public static int maxp = 0;
	public static long[] suml = new long[MAXN << 2];
	public static long[] cost = new long[MAXN << 2];
	// 多少种果汁参数加入了线段树
	public static int used = 0;

	// 整体二分的过程需要
	public static int[] lset = new int[MAXN];
	public static int[] rset = new int[MAXN];

	// 每个小朋友的答案，是第几号果汁的美味度
	public static int[] ans = new int[MAXN];

	public static void up(int i) {
		suml[i] = suml[i << 1] + suml[i << 1 | 1];
		cost[i] = cost[i << 1] + cost[i << 1 | 1];
	}

	public static void add(int jobi, int jobv, int l, int r, int i) {
		if (l == r) {
			suml[i] += jobv;
			cost[i] = suml[i] * l;
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				add(jobi, jobv, l, mid, i << 1);
			} else {
				add(jobi, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static long query(long volume, int l, int r, int i) {
		if (l == r) {
			return volume * l;
		}
		int mid = (l + r) >> 1;
		if (suml[i << 1] >= volume) {
			return query(volume, l, mid, i << 1);
		} else {
			return cost[i << 1] + query(volume - suml[i << 1], mid + 1, r, i << 1 | 1);
		}
	}

	public static void compute(int al, int ar, int jl, int jr) {
		if (jl == jr) {
			for (int i = al; i <= ar; i++) {
				ans[arr[i]] = jl;
			}
		} else {
			int mid = (jl + jr) >> 1;
			while (used < mid) {
				used++;
				add(juice[used][1], juice[used][2], 1, maxp, 1);
			}
			while (used > mid) {
				add(juice[used][1], -juice[used][2], 1, maxp, 1);
				used--;
			}
			int lsiz = 0, rsiz = 0;
			for (int i = al, id; i <= ar; i++) {
				id = arr[i];
				if (suml[1] >= least[id] && query(least[id], 1, maxp, 1) <= money[id]) {
					lset[++lsiz] = id;
				} else {
					rset[++rsiz] = id;
				}
			}
			for (int i = 1; i <= lsiz; i++) {
				arr[al + i - 1] = lset[i];
			}
			for (int i = 1; i <= rsiz; i++) {
				arr[al + lsiz + i - 1] = rset[i];
			}
			compute(al, al + lsiz - 1, jl, mid);
			compute(al + lsiz, ar, mid + 1, jr);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			juice[i][0] = in.nextInt();
			juice[i][1] = in.nextInt();
			juice[i][2] = in.nextInt();
			maxp = Math.max(maxp, juice[i][1]);
		}
		for (int i = 1; i <= m; i++) {
			arr[i] = i;
			money[i] = in.nextLong();
			least[i] = in.nextLong();
		}
		Arrays.sort(juice, 1, n + 1, (a, b) -> b[0] - a[0]);
		compute(1, m, 1, n + 1);
		for (int i = 1; i <= m; i++) {
			if (ans[i] == n + 1) {
				out.println(-1);
			} else {
				out.println(juice[ans[i]][0]);
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

		long nextLong() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			long val = 0L;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}

	}

}
