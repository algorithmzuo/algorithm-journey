package class171;

// 拦截导弹，java版
// 测试链接 : https://www.luogu.com.cn/problem/P2487
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_Interceptor1 {

	public static int MAXN = 50001;

	public static int n, s;

	// 位置i、高度h、速度v
	public static int[][] arr = new int[MAXN][3];
	public static int[] sortv = new int[MAXN];

	public static int[] max1 = new int[MAXN];
	public static double[] cnt1 = new double[MAXN];

	public static int[] max2 = new int[MAXN];
	public static double[] cnt2 = new double[MAXN];

	public static int[] treemax = new int[MAXN];
	public static double[] treecnt = new double[MAXN];

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int max, double cnt) {
		while (i <= s) {
			if (max > treemax[i]) {
				treemax[i] = max;
				treecnt[i] = cnt;
			} else if (max == treemax[i]) {
				treecnt[i] += cnt;
			}
			i += lowbit(i);
		}
	}

	public static int querymax;

	public static double querycnt;

	public static void query(int i) {
		querymax = 0;
		querycnt = 0;
		while (i > 0) {
			if (treemax[i] > querymax) {
				querymax = treemax[i];
				querycnt = treecnt[i];
			} else if (treemax[i] == querymax) {
				querycnt += treecnt[i];
			}
			i -= lowbit(i);
		}
	}

	public static void clear(int i) {
		while (i <= s) {
			treemax[i] = 0;
			treecnt[i] = 0;
			i += lowbit(i);
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for(int i = 1; i <= n; i++) {
			arr[i][0] = i;
			arr[i][1] = in.nextInt();
			arr[i][2] = in.nextInt();
			sortv[i] = arr[i][2];
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
