package class061;

// 水井
// 本题就是题目3，题意是一样的，来自洛谷的测试
// 一共n个田地，给定长度为n的数组cost，cost[i]表示在i号田地打井的花费
// 给定n * n的矩阵mat，mat[i][j]表示i号田地和j号田地连通的花费
// 题目保证 mat[i][j] == mat[j][i]
// 如果某个田地打了井，那么认为该田地有水可用
// 或者某个田地和有水可用的田地连通，也认为该田地有水可用
// 打印让所有田地都有水可用，最小的花费
// 1 <= n <= 300
// 0 <= 花费 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P1550
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code03_Water_Luogu {

	public static int MAXN = 301;
	public static int MAXM = 100001;

	// 题目输入
	public static int n;
	public static int[] cost = new int[MAXN];
	public static int[][] mat = new int[MAXN][MAXN];

	// 端点u、端点v、边权w
	public static int[][] edges = new int[MAXM][3];

	// 并查集
	public static int[] father = new int[MAXN];

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static boolean union(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (fx != fy) {
			father[fx] = fy;
			return true;
		} else {
			return false;
		}
	}

	public static int compute() {
		for (int i = 0; i <= n; i++) {
			father[i] = i;
		}
		int cnt = 0;
		// 节点0是虚拟水源，连接0和i的边表示在i号田地打井，边权为cost[i]
		for (int i = 1; i <= n; i++) {
			edges[++cnt][0] = 0;
			edges[cnt][1] = i;
			edges[cnt][2] = cost[i];
		}
		// 根据给定的连通代价，去生成其他的边
		for (int i = 1; i <= n; i++) {
			for (int j = i + 1; j <= n; j++) {
				edges[++cnt][0] = i;
				edges[cnt][1] = j;
				edges[cnt][2] = mat[i][j];
			}
		}
		// 边按照权值排序
		Arrays.sort(edges, 1, cnt + 1, (a, b) -> a[2] - b[2]);
		int ans = 0;
		for (int i = 1; i <= cnt; i++) {
			if (union(edges[i][0], edges[i][1])) {
				ans += edges[i][2];
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			cost[i] = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				mat[i][j] = in.nextInt();
			}
		}
		int ans = compute();
		out.println(ans);
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
