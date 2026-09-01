package class206;

// 射击场，java版
// 每个靶子是一个矩形区域，x轴的范围[l, r]，y轴的范围[d, u]，还有z轴的数值
// 空间里有n个靶子，接下来有m发子弹，每发子弹给定出发时的xy坐标，沿z轴前进
// 子弹会击中前进过程中遇到的第一个尚未消失的靶子，随后击中的靶子和这发子弹都消失
// 对于每一发子弹，打印它击中的靶子编号，如果没有击中打印0
// 1 <= n、m <= 10^5
// 0 <= 坐标值 <= 10^7
// 测试链接 : https://www.luogu.com.cn/problem/CF44G
// 测试链接 : https://codeforces.com/problemset/problem/44/G
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code01_ShootingGallery1 {

	public static int MAXN = 100001;
	public static int INF = 1 << 30;
	public static int n, m;

	// l、r、d、u、z、id
	public static int[][] target = new int[MAXN][6];

	public static int[] x = new int[MAXN];
	public static int[] y = new int[MAXN];

	public static int cntkdt;
	public static int root;
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];

	// 单个节点是否存活、所有节点数量、存活节点数量
	public static boolean[] alive = new boolean[MAXN];
	public static int[] allSiz = new int[MAXN];
	public static int[] aliveSiz = new int[MAXN];

	public static int[] xmin = new int[MAXN];
	public static int[] xmax = new int[MAXN];
	public static int[] ymin = new int[MAXN];
	public static int[] ymax = new int[MAXN];
	// 区域内存活节点的最小编号
	public static int[] idmin = new int[MAXN];

	public static double ALPHA = 0.7;
	public static int top;
	public static int[] arr = new int[MAXN];
	public static int treeSiz;

	public static int shot;
	public static int[] ans = new int[MAXN];

	public static int init(int qx, int qy) {
		cntkdt++;
		x[cntkdt] = qx;
		y[cntkdt] = qy;
		ls[cntkdt] = rs[cntkdt] = 0;
		alive[cntkdt] = true;
		allSiz[cntkdt] = 1;
		aliveSiz[cntkdt] = 1;
		xmin[cntkdt] = xmax[cntkdt] = qx;
		ymin[cntkdt] = ymax[cntkdt] = qy;
		idmin[cntkdt] = cntkdt;
		return cntkdt;
	}

	public static void maintain(int i) {
		int l = ls[i];
		int r = rs[i];
		allSiz[i] = 1 + allSiz[l] + allSiz[r];
		if (alive[i]) {
			aliveSiz[i] = 1 + aliveSiz[l] + aliveSiz[r];
			xmin[i] = xmax[i] = x[i];
			ymin[i] = ymax[i] = y[i];
			idmin[i] = i;
		} else {
			aliveSiz[i] = aliveSiz[l] + aliveSiz[r];
			xmin[i] = ymin[i] = INF;
			xmax[i] = ymax[i] = -INF;
			idmin[i] = INF;
		}
		if (aliveSiz[l] != 0) {
			xmin[i] = Math.min(xmin[i], xmin[l]);
			xmax[i] = Math.max(xmax[i], xmax[l]);
			ymin[i] = Math.min(ymin[i], ymin[l]);
			ymax[i] = Math.max(ymax[i], ymax[l]);
			idmin[i] = Math.min(idmin[i], idmin[l]);
		}
		if (aliveSiz[r] != 0) {
			xmin[i] = Math.min(xmin[i], xmin[r]);
			xmax[i] = Math.max(xmax[i], xmax[r]);
			ymin[i] = Math.min(ymin[i], ymin[r]);
			ymax[i] = Math.max(ymax[i], ymax[r]);
			idmin[i] = Math.min(idmin[i], idmin[r]);
		}
	}

	public static int compareNode(int i, int j, int dimension) {
		int a = dimension == 0 ? x[i] : y[i];
		int b = dimension == 0 ? x[j] : y[j];
		return a != b ? (a - b) : (i - j);
	}

	public static void swap(int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static int first, last;

	public static void partition(int l, int r, int pidx, int dimension) {
		first = l;
		last = r;
		int i = l;
		while (i <= last) {
			int cmp = compareNode(arr[i], pidx, dimension);
			if (cmp == 0) {
				i++;
			} else if (cmp < 0) {
				swap(first++, i++);
			} else {
				swap(i, last--);
			}
		}
	}

	public static void randSelect(int l, int r, int k, int dimension) {
		while (l <= r) {
			int pidx = arr[l + (int) (Math.random() * (r - l + 1))];
			partition(l, r, pidx, dimension);
			if (k < first) {
				r = first - 1;
			} else if (k > last) {
				l = last + 1;
			} else {
				break;
			}
		}
	}

	public static int build(int l, int r, int dimension) {
		if (l > r) {
			return 0;
		}
		int mid = (l + r) >> 1;
		randSelect(l, r, mid, dimension);
		int rt = arr[mid];
		ls[rt] = build(l, mid - 1, dimension ^ 1);
		rs[rt] = build(mid + 1, r, dimension ^ 1);
		maintain(rt);
		return rt;
	}

	public static boolean balance(int i) {
		return ALPHA * allSiz[i] >= Math.max(allSiz[ls[i]], allSiz[rs[i]]) && aliveSiz[i] >= ALPHA * allSiz[i];
	}

	public static void dfs(int i) {
		if (i != 0) {
			if (alive[i]) {
				arr[++treeSiz] = i;
			}
			dfs(ls[i]);
			dfs(rs[i]);
		}
	}

	public static int rebuild(int i, int dimension) {
		if (i == top) {
			treeSiz = 0;
			dfs(i);
			return build(1, treeSiz, dimension);
		}
		if (compareNode(top, i, dimension) < 0) {
			ls[i] = rebuild(ls[i], dimension ^ 1);
		} else {
			rs[i] = rebuild(rs[i], dimension ^ 1);
		}
		maintain(i);
		return i;
	}

	public static void rebuild() {
		if (top != 0) {
			root = rebuild(root, 0);
		}
	}

	public static int insert(int insertNode, int u, int dimension) {
		if (u == 0) {
			return insertNode;
		}
		if (compareNode(insertNode, u, dimension) < 0) {
			ls[u] = insert(insertNode, ls[u], dimension ^ 1);
		} else {
			rs[u] = insert(insertNode, rs[u], dimension ^ 1);
		}
		maintain(u);
		if (!balance(u)) {
			top = u;
		}
		return u;
	}

	public static void add(int qx, int qy) {
		top = 0;
		int insertNode = init(qx, qy);
		root = insert(insertNode, root, 0);
		rebuild();
	}

	public static void erase(int eraseNode, int u, int dimension) {
		if (u == eraseNode) {
			alive[u] = false;
		} else if (compareNode(eraseNode, u, dimension) < 0) {
			erase(eraseNode, ls[u], dimension ^ 1);
		} else {
			erase(eraseNode, rs[u], dimension ^ 1);
		}
		maintain(u);
		if (!balance(u)) {
			top = u;
		}
	}

	public static void remove(int eraseNode) {
		top = 0;
		erase(eraseNode, root, 0);
		rebuild();
	}

	public static void query(int ql, int qr, int qd, int qu, int i) {
		if (i == 0 || aliveSiz[i] == 0 || idmin[i] >= shot) {
			return;
		}
		if (xmax[i] < ql || qr < xmin[i] || ymax[i] < qd || qu < ymin[i]) {
			return;
		}
		if (ql <= xmin[i] && xmax[i] <= qr && qd <= ymin[i] && ymax[i] <= qu) {
			shot = Math.min(shot, idmin[i]);
			return;
		}
		if (alive[i] && ql <= x[i] && x[i] <= qr && qd <= y[i] && y[i] <= qu) {
			shot = Math.min(shot, i);
		}
		int l = ls[i];
		int r = rs[i];
		if (idmin[l] < idmin[r]) {
			query(ql, qr, qd, qu, l);
			query(ql, qr, qd, qu, r);
		} else {
			query(ql, qr, qd, qu, r);
			query(ql, qr, qd, qu, l);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			target[i][0] = in.nextInt();
			target[i][1] = in.nextInt();
			target[i][2] = in.nextInt();
			target[i][3] = in.nextInt();
			target[i][4] = in.nextInt();
			target[i][5] = i;
		}
		xmin[0] = ymin[0] = INF;
		xmax[0] = ymax[0] = -INF;
		idmin[0] = INF;
		m = in.nextInt();
		for (int i = 1, qx, qy; i <= m; i++) {
			qx = in.nextInt();
			qy = in.nextInt();
			add(qx, qy);
		}
		Arrays.sort(target, 1, n + 1, (a, b) -> a[4] - b[4]);
		for (int k = 1; k <= n; k++) {
			shot = INF;
			query(target[k][0], target[k][1], target[k][2], target[k][3], root);
			if (shot != INF) {
				ans[shot] = target[k][5];
				remove(shot);
			}
		}
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