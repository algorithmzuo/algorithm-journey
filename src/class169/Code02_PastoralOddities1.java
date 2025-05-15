package class169;

// 度为奇最小瓶颈，java版
// 一共有n个点，初始没有边，依次加入m条无向边，每条边有边权
// 每次加入后，询问是否存在一个边集，满足每个点连接的边的数量都是奇数
// 如果存在，希望边集的最大边权，尽可能小，一共有m条打印
// 2 <= n <= 10^5
// 1 <= m <= 3 * 10^5
// 1 <= 边权 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/CF603E
// 测试链接 : https://codeforces.com/problemset/problem/603/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code02_PastoralOddities1 {

	public static int MAXN = 100001;
	public static int MAXM = 300002;
	public static int n, m;

	// 边的编号i、端点x、端点y、权值w
	public static int[][] edge = new int[MAXM][4];
	public static int[][] wsort = new int[MAXM][4];

	// 节点数为奇数的联通区数量
	public static int oddnum;
	// 可撤销并查集
	public static int[] father = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[][] rollback = new int[MAXN][2];
	public static int opsize = 0;

	public static int[] ans = new int[MAXM];

	public static int find(int i) {
		while (i != father[i]) {
			i = father[i];
		}
		return i;
	}

	public static boolean union(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (fx == fy) {
			return false;
		}
		if ((siz[fx] & 1) == 1 && (siz[fy] & 1) == 1) {
			oddnum -= 2;
		}
		if (siz[fx] < siz[fy]) {
			int tmp = fx;
			fx = fy;
			fy = tmp;
		}
		father[fy] = fx;
		siz[fx] += siz[fy];
		rollback[++opsize][0] = fx;
		rollback[opsize][1] = fy;
		return true;
	}

	public static void undo() {
		int fx = rollback[opsize][0];
		int fy = rollback[opsize--][1];
		father[fy] = fy;
		siz[fx] -= siz[fy];
		if ((siz[fx] & 1) == 1 && (siz[fy] & 1) == 1) {
			oddnum += 2;
		}
	}

	public static void compute(int ql, int qr, int vl, int vr) {
		if (ql > qr) {
			return;
		}
		if (vl == vr) {
			for (int i = ql; i <= qr; i++) {
				ans[i] = wsort[vl][3];
			}
		} else {
			int mid = (vl + vr) >> 1;
			int unionCnt1 = 0;
			for (int i = vl; i <= mid; i++) {
				if (wsort[i][0] < ql) {
					if (union(wsort[i][1], wsort[i][2])) {
						unionCnt1++;
					}
				}
			}
			int unionCnt2 = 0;
			int split = qr + 1;
			for (int i = ql; i <= qr; i++) {
				if (edge[i][3] <= wsort[mid][3]) {
					if (union(edge[i][1], edge[i][2])) {
						unionCnt2++;
					}
				}
				if (oddnum == 0) {
					split = i;
					break;
				}
			}
			for (int i = 1; i <= unionCnt2; i++) {
				undo();
			}
			compute(ql, split - 1, mid + 1, vr);
			for (int i = 1; i <= unionCnt1; i++) {
				undo();
			}
			int unionCnt3 = 0;
			for (int i = ql; i <= split - 1; i++) {
				if (edge[i][3] <= wsort[vl][3]) {
					if (union(edge[i][1], edge[i][2])) {
						unionCnt3++;
					}
				}
			}
			compute(split, qr, vl, mid);
			for (int i = 1; i <= unionCnt3; i++) {
				undo();
			}
		}
	}

	public static void prepare() {
		oddnum = n;
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			siz[i] = 1;
		}
		for (int i = 1; i <= m; i++) {
			wsort[i][0] = edge[i][0];
			wsort[i][1] = edge[i][1];
			wsort[i][2] = edge[i][2];
			wsort[i][3] = edge[i][3];
		}
		Arrays.sort(wsort, 1, m + 1, (a, b) -> a[3] - b[3]);
		wsort[m + 1][3] = -1;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			edge[i][0] = i;
			edge[i][1] = in.nextInt();
			edge[i][2] = in.nextInt();
			edge[i][3] = in.nextInt();
		}
		prepare();
		compute(1, m, 1, m + 1);
		for (int i = 1; i <= m; i++) {
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
