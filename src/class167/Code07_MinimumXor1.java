package class167;

// 最小异或查询，java版
// 测试链接 : https://www.luogu.com.cn/problem/AT_abc308_g
// 测试链接 : https://atcoder.jp/contests/abc308/tasks/abc308_g
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_MinimumXor1 {

	public static int MAXN = 10000001;
	public static int BIT = 29;
	public static int INF = 1 << 30;

	public static int[] fa = new int[MAXN];
	public static int[][] tree = new int[MAXN][2];
	public static int[] pass = new int[MAXN];
	public static int cnt = 1;

	public static int[] eor = new int[MAXN];
	public static int[] num = new int[MAXN];

	public static int insert(int x, int changeCnt) {
		int cur = 1;
		pass[cur] += changeCnt;
		for (int b = BIT, path; b >= 0; b--) {
			path = (x >> b) & 1;
			if (tree[cur][path] == 0) {
				tree[cur][path] = ++cnt;
				fa[tree[cur][path]] = cur;
			}
			cur = tree[cur][path];
			pass[cur] += changeCnt;
		}
		return cur;
	}

	public static void compute(int x, int changeCnt) {
		int bottom = insert(x, changeCnt);
		eor[bottom] = pass[bottom] >= 2 ? 0 : INF;
		num[bottom] = pass[bottom] == 1 ? x : 0;
		for (int i = fa[bottom], l, r; i > 0; i = fa[i]) {
			l = tree[i][0];
			r = tree[i][1];
			if (pass[i] < 2) {
				eor[i] = INF;
			} else if ((l != 0) ^ (r != 0)) {
				eor[i] = l > 0 ? eor[l] : eor[r];
			} else if (Math.max(pass[l], pass[r]) == 1) {
				eor[i] = num[l] ^ num[r];
			} else {
				eor[i] = Math.min(eor[l], eor[r]);
			}
			if (pass[l] + pass[r] == 1) {
				num[i] = pass[l] == 1 ? num[l] : num[r];
			} else {
				num[i] = 0;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int q = in.nextInt();
		for (int i = 1, op, x; i <= q; i++) {
			op = in.nextInt();
			if (op == 3) {
				out.println(eor[1]);
			} else {
				x = in.nextInt();
				if (op == 1) {
					compute(x, 1);
				} else {
					compute(x, -1);
				}
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