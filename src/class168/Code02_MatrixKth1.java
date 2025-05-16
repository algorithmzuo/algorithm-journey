package class168;

// 矩阵内第k小，第一种写法，java版
// 给定一个n * n的矩阵，接下来有q条查询，格式如下
// 查询 a b c d k : 左上角(a, b)，右下角(c, d)，打印该区域中第k小的数
// 1 <= n <= 500
// 1 <= q <= 6 * 10^4
// 0 <= 矩阵中的数字 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P1527
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code02_MatrixKth1 {

	public static int MAXN = 501;
	public static int MAXQ = 1000001;
	public static int n, q;

	// 矩阵中的每个数字，所在行x、所在列y、数值v
	public static int[][] xyv = new int[MAXN * MAXN][3];
	// 矩阵中一共有多少个数字，cntv就是矩阵的规模
	public static int cntv = 0;

	// 查询任务的编号
	public static int[] qid = new int[MAXQ];
	// 查询范围的左上角坐标
	public static int[] a = new int[MAXQ];
	public static int[] b = new int[MAXQ];
	// 查询范围的右下角坐标
	public static int[] c = new int[MAXQ];
	public static int[] d = new int[MAXQ];
	// 查询矩阵内第k小
	public static int[] k = new int[MAXQ];

	// 二维树状数组
	public static int[][] tree = new int[MAXN][MAXN];

	// 整体二分
	public static int[] lset = new int[MAXQ];
	public static int[] rset = new int[MAXQ];

	// 每条查询的答案
	public static int[] ans = new int[MAXQ];

	public static int lowbit(int i) {
		return i & -i;
	}

	// 二维空间中，(x,y)位置的词频加v
	public static void add(int x, int y, int v) {
		for (int i = x; i <= n; i += lowbit(i)) {
			for (int j = y; j <= n; j += lowbit(j)) {
				tree[i][j] += v;
			}
		}
	}

	// 二维空间中，左上角(1,1)到右下角(x,y)范围上的词频累加和
	public static int sum(int x, int y) {
		int ret = 0;
		for (int i = x; i > 0; i -= lowbit(i)) {
			for (int j = y; j > 0; j -= lowbit(j)) {
				ret += tree[i][j];
			}
		}
		return ret;
	}

	// 二维空间中，左上角(a,b)到右下角(c,d)范围上的词频累加和
	public static int query(int a, int b, int c, int d) {
		return sum(c, d) - sum(a - 1, d) - sum(c, b - 1) + sum(a - 1, b - 1);
	}

	public static void compute(int ql, int qr, int vl, int vr) {
		if (ql > qr) {
			return;
		}
		if (vl == vr) {
			for (int i = ql; i <= qr; i++) {
				ans[qid[i]] = xyv[vl][2];
			}
		} else {
			int mid = (vl + vr) >> 1;
			for (int i = vl; i <= mid; i++) {
				add(xyv[i][0], xyv[i][1], 1);
			}
			int lsiz = 0, rsiz = 0;
			for (int i = ql; i <= qr; i++) {
				int id = qid[i];
				int satisfy = query(a[id], b[id], c[id], d[id]);
				if (satisfy >= k[id]) {
					lset[++lsiz] = id;
				} else {
					k[id] -= satisfy;
					rset[++rsiz] = id;
				}
			}
			for (int i = 1; i <= lsiz; i++) {
				qid[ql + i - 1] = lset[i];
			}
			for (int i = 1; i <= rsiz; i++) {
				qid[ql + lsiz + i - 1] = rset[i];
			}
			for (int i = vl; i <= mid; i++) {
				add(xyv[i][0], xyv[i][1], -1);
			}
			compute(ql, ql + lsiz - 1, vl, mid);
			compute(ql + lsiz, qr, mid + 1, vr);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		q = in.nextInt();
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				xyv[++cntv][0] = i;
				xyv[cntv][1] = j;
				xyv[cntv][2] = in.nextInt();
			}
		}
		for (int i = 1; i <= q; i++) {
			qid[i] = i;
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			c[i] = in.nextInt();
			d[i] = in.nextInt();
			k[i] = in.nextInt();
		}
		Arrays.sort(xyv, 1, cntv + 1, (a, b) -> a[2] - b[2]);
		compute(1, q, 1, cntv);
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
