package class169;

// 点的度都是奇数的最小瓶颈，java版
// 一共有n个点，初始没有边，依次加入m条无向边，每条边有边权
// 每次加入后，询问是否存在一个边集，满足每个点连接的边的数量都是奇数
// 如果存在，希望边集的最大边权，尽可能小，如果不存在打印-1
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

public class Code05_PastoralOddities1 {

	public static int MAXN = 100001;
	public static int MAXM = 300001;
	public static int n, m;

	// edge是按时序组织的边数组
	// wsort是按权值组织的边数组
	// 每条边有：端点x、端点y、权值w、时序tim、排名rak
	public static int[][] edge = new int[MAXM][5];
	public static int[][] wsort = new int[MAXM][5];

	// 可撤销并查集 + 维护节点数为奇数的连通区数量oddnum
	public static int oddnum;
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

	// 按时序组织的边在edge里，当前来到[el..er]范围
	// 按权值组织的边在wsort里，答案范围[vl..vr]
	// 调用递归的前提 : el之前，边权排名<vl的边，已经加到图里了
	// 利用整体二分得到所有边的答案
	public static void compute(int el, int er, int vl, int vr) {
		if (el > er) {
			return;
		}
		if (vl == vr) {
			for (int i = el; i <= er; i++) {
				ans[i] = vl;
			}
		} else {
			int mid = (vl + vr) >> 1;
			// 1) el之前，边权排名在[vl..mid]之间的边，加到图里，遍历wsort[vl..mid]来加速
			int unionCnt1 = 0;
			for (int i = vl; i <= mid; i++) {
				if (wsort[i][3] < el) {
					if (union(wsort[i][0], wsort[i][1])) {
						unionCnt1++;
					}
				}
			}
			// 2) 从el开始，边权排名<=mid的边，加到图里，找到第一个达标的边split
			int unionCnt2 = 0;
			int split = er + 1;
			for (int i = el; i <= er; i++) {
				if (edge[i][4] <= mid) {
					if (union(edge[i][0], edge[i][1])) {
						unionCnt2++;
					}
				}
				if (oddnum == 0) {
					split = i;
					break;
				}
			}
			// 3) 撤销2)的效果，el之前，边权排名<=mid的边，都在图中
			for (int i = 1; i <= unionCnt2; i++) {
				undo();
			}
			// 4) 执行compute(el, split - 1, mid + 1, vr)，此时满足子递归的前提
			compute(el, split - 1, mid + 1, vr);
			// 5) 撤销1)的效果，此时只剩下前提了，el之前，边权排名<vl的边，都在图中
			for (int i = 1; i <= unionCnt1; i++) {
				undo();
			}
			// 6) 从el开始到split之前，边权排名<vl的边，加到图里
			int unionCnt3 = 0;
			for (int i = el; i <= split - 1; i++) {
				if (edge[i][4] < vl) {
					if (union(edge[i][0], edge[i][1])) {
						unionCnt3++;
					}
				}
			}
			// 7) 执行compute(split, er, vl, mid)，此时满足子递归的前提
			compute(split, er, vl, mid);
			// 8) 撤销6)的效果，回到了前提
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
		Arrays.sort(wsort, 1, m + 1, (a, b) -> a[2] - b[2]);
		// edge数组和wsort数组里的每条边，设置排名信息
		for (int i = 1; i <= m; i++) {
			wsort[i][4] = i;
			edge[wsort[i][3]][4] = i;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			edge[i][0] = in.nextInt();
			edge[i][1] = in.nextInt();
			edge[i][2] = in.nextInt();
			edge[i][3] = i;
		}
		prepare();
		compute(1, m, 1, m + 1);
		for (int i = 1; i <= m; i++) {
			if (ans[i] == m + 1) {
				out.println(-1);
			} else {
				out.println(wsort[ans[i]][2]);
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
