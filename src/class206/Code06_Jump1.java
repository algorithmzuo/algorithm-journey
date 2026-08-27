package class206;

// 弹跳，java版
// 一共有n个点，编号1~n，给定每个点的坐标(x, y)
// 一共有m个弹跳装置，给定每个弹跳装置的参数 p t l r d u
// 表示装置在p号点，花费t的时间，可以从p号点跳到[l, r] * [d, u]中的任意点
// 从1号点出发，可以重复经过点，也可以重复使用弹跳装置，题目保证可以到达每个点
// 打印从1号点到达2、3 .. n号点各自的最短用时
// 1 <= n <= 7 * 10^4
// 1 <= m <= 1.5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5471
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Code06_Jump1 {

	public static int MAXN = 200001;
	public static int INF = 1 << 30;
	public static int n, m, w, h;

	// x、y、i
	public static int[][] arr = new int[MAXN][3];
	// t、l、r、d、u
	public static int[][] jump = new int[MAXN][5];

	// kd树
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static int[] xmin = new int[MAXN];
	public static int[] xmax = new int[MAXN];
	public static int[] ymin = new int[MAXN];
	public static int[] ymax = new int[MAXN];
	// kd树的根
	public static int root;

	// 优化建图
	public static int[] headg = new int[MAXN];
	public static int[] nextg = new int[MAXN];
	public static int[] tog = new int[MAXN];
	public static int cntg;

	// 弹跳装置列表
	public static int[] headj = new int[MAXN];
	public static int[] nextj = new int[MAXN];
	public static int[] toj = new int[MAXN];
	public static int cntj;

	// dijkstra
	public static int[] dist = new int[MAXN];
	public static boolean[] vis = new boolean[MAXN];
	// dist、i
	public static PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

	public static void addEdge(int u, int v) {
		nextg[++cntg] = headg[u];
		tog[cntg] = v;
		headg[u] = cntg;
	}

	public static void addJump(int p, int j) {
		nextj[++cntj] = headj[p];
		toj[cntj] = j;
		headj[p] = cntj;
	}

	public static void swap(int i, int j) {
		int[] tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static int first, last;

	public static void partition(int l, int r, int pivot, int dimension) {
		first = l;
		last = r;
		int i = l;
		while (i <= last) {
			int cur = arr[i][dimension];
			if (cur == pivot) {
				i++;
			} else if (cur < pivot) {
				swap(first++, i++);
			} else {
				swap(i, last--);
			}
		}
	}

	public static void randSelect(int l, int r, int i, int dimension) {
		while (l <= r) {
			int pivot = arr[l + (int) (Math.random() * (r - l + 1))][dimension];
			partition(l, r, pivot, dimension);
			if (i < first) {
				r = first - 1;
			} else if (i > last) {
				l = last + 1;
			} else {
				break;
			}
		}
	}

	public static void maintain(int i) {
		xmin[i] = Math.min(arr[i][0], Math.min(xmin[ls[i]], xmin[rs[i]]));
		xmax[i] = Math.max(arr[i][0], Math.max(xmax[ls[i]], xmax[rs[i]]));
		ymin[i] = Math.min(arr[i][1], Math.min(ymin[ls[i]], ymin[rs[i]]));
		ymax[i] = Math.max(arr[i][1], Math.max(ymax[ls[i]], ymax[rs[i]]));
	}

	public static int build(int l, int r, int dimension) {
		if (l > r) {
			return 0;
		}
		int mid = (l + r) >> 1;
		randSelect(l, r, mid, dimension);
		ls[mid] = build(l, mid - 1, dimension ^ 1);
		rs[mid] = build(mid + 1, r, dimension ^ 1);
		maintain(mid);
		// n+mid是虚点，表示以mid为根的整棵子树
		// 虚点可以到达子树中的所有真实点
		addEdge(n + mid, arr[mid][2]);
		if (ls[mid] != 0) {
			addEdge(n + mid, n + ls[mid]);
		}
		if (rs[mid] != 0) {
			addEdge(n + mid, n + rs[mid]);
		}
		return mid;
	}

	public static void update(int d, int i) {
		if (!vis[i] && dist[i] > d) {
			dist[i] = d;
			heap.add(new int[] { d, i });
		}
	}

	public static void xToRectangle(int jl, int jr, int jd, int ju, int jdist, int i) {
		if (i == 0) {
			return;
		}
		// 这棵KDT子树已经存在优于jdist的方案
		// 虚点可以通过权值为0的边，到达子树所有真实点
		// 于是可以剪枝
		if (dist[n + i] <= jdist) {
			return;
		}
		if (xmax[i] < jl || jr < xmin[i] || ymax[i] < jd || ju < ymin[i]) {
			return;
		}
		if (jl <= xmin[i] && xmax[i] <= jr && jd <= ymin[i] && ymax[i] <= ju) {
			update(jdist, n + i);
			return;
		}
		if (jl <= arr[i][0] && arr[i][0] <= jr && jd <= arr[i][1] && arr[i][1] <= ju) {
			update(jdist, arr[i][2]);
		}
		xToRectangle(jl, jr, jd, ju, jdist, ls[i]);
		xToRectangle(jl, jr, jd, ju, jdist, rs[i]);
	}

	public static void dijkstra() {
		Arrays.fill(dist, 1, (n << 1) + 1, INF);
		dist[1] = 0;
		heap.add(new int[] { 0, 1 });
		while (!heap.isEmpty()) {
			int[] cur = heap.poll();
			int d = cur[0];
			int i = cur[1];
			if (!vis[i]) {
				vis[i] = true;
				for (int e = headg[i]; e > 0; e = nextg[e]) {
					update(d, tog[e]);
				}
				if (i <= n) {
					for (int e = headj[i]; e > 0; e = nextj[e]) {
						int j = toj[e];
						int jt = jump[j][0];
						int jl = jump[j][1];
						int jr = jump[j][2];
						int jd = jump[j][3];
						int ju = jump[j][4];
						xToRectangle(jl, jr, jd, ju, d + jt, root);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		w = in.nextInt();
		h = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i][0] = in.nextInt();
			arr[i][1] = in.nextInt();
			arr[i][2] = i;
		}
		for (int j = 1, p; j <= m; j++) {
			p = in.nextInt();
			jump[j][0] = in.nextInt();
			jump[j][1] = in.nextInt();
			jump[j][2] = in.nextInt();
			jump[j][3] = in.nextInt();
			jump[j][4] = in.nextInt();
			addJump(p, j);
		}
		xmin[0] = ymin[0] = INF;
		xmax[0] = ymax[0] = -INF;
		root = build(1, n, 0);
		dijkstra();
		for (int i = 2; i <= n; i++) {
			out.println(dist[i]);
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