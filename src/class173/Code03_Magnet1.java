package class173;

// 磁力块，java版
// 磁块有五个属性值，x、y、m、p、range，磁块在(x, y)位置、质量为m、磁力为p、吸引半径range
// 磁块A可以把磁块B吸到磁块A的位置，需要满足如下的条件
// A与B的距离不大于A的吸引半径，并且B的质量不大于A的磁力
// 你有一个初始磁块，给定初始磁块的4个属性值(不给质量，因为没用)，你永远在初始磁块的位置
// 接下来给定n个磁块各自的5个属性值，你可以用初始磁块，吸过来其中的磁块
// 吸过来的磁块可以被你随意使用，返回你最多能吸过来多少磁块
// 1 <= n <= 3 * 10^5    -10^9 <= x、y <= +10^9    1 <= m、p、range <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P10590
// 测试链接 : https://codeforces.com/problemset/problem/198/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
// 为了java的实现能通过，不把数据封装成一个磁块对象，然后去排序
// 手写了双指针快排优化常数时间，一般不需要这么做，正式比赛不卡常

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_Magnet1 {

	public static int MAXN = 300001;
	public static int MAXB = 601;
	public static int n;
	public static int[] x = new int[MAXN];
	public static int[] y = new int[MAXN];
	public static int[] m = new int[MAXN];
	public static int[] p = new int[MAXN];
	public static long[] range = new long[MAXN];
	public static long[] dist = new long[MAXN];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];
	public static int[] maxm = new int[MAXB];

	public static int[] que = new int[MAXN];
	public static boolean[] vis = new boolean[MAXN];

	// 下标为i的磁块和下标为j的磁块交换
	public static void swap(int i, int j) {
		int tmp1;
		tmp1 = x[i]; x[i] = x[j]; x[j] = tmp1;
		tmp1 = y[i]; y[i] = y[j]; y[j] = tmp1;
		tmp1 = m[i]; m[i] = m[j]; m[j] = tmp1;
		tmp1 = p[i]; p[i] = p[j]; p[j] = tmp1;
		long tmp2;
		tmp2 = range[i]; range[i] = range[j]; range[j] = tmp2;
		tmp2 = dist[i]; dist[i] = dist[j]; dist[j] = tmp2;
	}

	// 所有磁块根据m值排序，手写双指针快排
	public static void sortByM(int l, int r) {
		if (l >= r) {
			return;
		}
		int i = l, j = r;
		int pivot = m[(l + r) >>> 1];
		while (i <= j) {
			while (m[i] < pivot) {
				i++;
			}
			while (m[j] > pivot) {
				j--;
			}
			if (i <= j) {
				swap(i++, j--);
			}
		}
		sortByM(l, j);
		sortByM(i, r);
	}

	// 所有磁块根据dist值排序，手写双指针快排
	public static void sortByDist(int l, int r) {
		if (l >= r) {
			return;
		}
		int i = l, j = r;
		long pivot = dist[(l + r) >>> 1];
		while (i <= j) {
			while (dist[i] < pivot) {
				i++;
			}
			while (dist[j] > pivot) {
				j--;
			}
			if (i <= j) {
				swap(i++, j--);
			}
		}
		sortByDist(l, j);
		sortByDist(i, r);
	}

	public static int bfs() {
		int ans = 0;
		vis[0] = true;
		int l = 1, r = 1;
		que[r++] = 0;
		while (l < r) {
			int cur = que[l++];
			for (int b = 1; b <= bnum; b++) {
				if (maxm[b] <= p[cur]) {
					while (bl[b] <= br[b] && dist[bl[b]] <= range[cur]) {
						int i = bl[b];
						if (!vis[i]) {
							vis[i] = true;
							que[r++] = i;
							ans++;
						}
						bl[b]++; // 重要剪枝逻辑
					}
				} else {
					for (int j = bl[b]; j <= br[b]; j++) {
						if (dist[j] <= range[cur] && m[j] <= p[cur] && !vis[j]) {
							vis[j] = true;
							que[r++] = j;
							ans++;
						}
					}
					break;
				}
			}
		}
		return ans;
	}

	public static void prepare() {
		blen = (int) Math.sqrt(n);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, n);
		}
		sortByM(1, n);
		for (int i = 1; i <= bnum; i++) {
			maxm[i] = m[br[i]];
			sortByDist(bl[i], br[i]);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		x[0] = in.nextInt();
		y[0] = in.nextInt();
		p[0] = in.nextInt();
		range[0] = in.nextInt();
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			x[i] = in.nextInt();
			y[i] = in.nextInt();
			m[i] = in.nextInt();
			p[i] = in.nextInt();
			range[i] = in.nextInt();
		}
		for (int i = 0; i <= n; i++) {
			range[i] *= range[i];
			long dx = x[0] - x[i];
			long dy = y[0] - y[i];
			dist[i] = dx * dx + dy * dy;
		}
		prepare();
		out.println(bfs());
		out.flush();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buf = new byte[1 << 20];
		private int ptr = 0, len = 0;
		private final InputStream in;

		FastReader(InputStream in) {
			this.in = in;
		}

		private int readByte() throws IOException {
			if (ptr >= len) {
				len = in.read(buf);
				ptr = 0;
				if (len <= 0)
					return -1;
			}
			return buf[ptr++];
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
