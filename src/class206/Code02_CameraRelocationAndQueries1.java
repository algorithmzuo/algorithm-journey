package class206;

// 摄像头改位置和查询，java版
// 三维空间中有n个摄像头，给定每个摄像头的初始位置，三维坐标(x, y, z)
// 接下来有m条操作，格式如下
// 操作 0 i x y z : 第i号摄像头位置变成(x, y, z)
// 操作 1 x y z r : 一个球体出现了，圆心在(x, y, z)，半径为r
//                  题目保证该球体的表面只会碰到一个摄像头
//                  打印这个摄像头的编号，注意在内部的摄像头不算数
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 1 <= n、m <= 65536
// 坐标值是double，绝对值不超过100，均为随机生成，精确到小数点后五位
// 测试链接 : https://www.luogu.com.cn/problem/P11716
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_CameraRelocationAndQueries1 {

	public static int MAXN = 200001;
	public static double INF = 1e100;
	public static double EPS = 1e-8;
	public static int n, m;

	// 加密参数
	public static double a, b;

	// 题目规定lastAns的初始值是0.1
	public static double lastAns = 0.1;

	// 三维坐标
	public static double[] x = new double[MAXN];
	public static double[] y = new double[MAXN];
	public static double[] z = new double[MAXN];

	// kdt节点编号对应的摄像头编号
	public static int[] kdtToCamera = new int[MAXN];

	// 摄像头编号对应的kdt节点编号
	public static int[] cameraToKdt = new int[MAXN];

	public static int cntkdt;
	public static int root;
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] alive = new boolean[MAXN];
	public static int[] allSiz = new int[MAXN];
	public static int[] aliveSiz = new int[MAXN];

	public static double[] xmin = new double[MAXN];
	public static double[] xmax = new double[MAXN];
	public static double[] ymin = new double[MAXN];
	public static double[] ymax = new double[MAXN];
	public static double[] zmin = new double[MAXN];
	public static double[] zmax = new double[MAXN];

	public static double ALPHA = 0.7;
	public static int top;
	public static int[] arr = new int[MAXN];
	public static int treeSiz;

	public static int init(double qx, double qy, double qz, int camera) {
		cntkdt++;
		x[cntkdt] = qx;
		y[cntkdt] = qy;
		z[cntkdt] = qz;
		kdtToCamera[cntkdt] = camera;
		ls[cntkdt] = rs[cntkdt] = 0;
		alive[cntkdt] = true;
		allSiz[cntkdt] = 1;
		aliveSiz[cntkdt] = 1;
		xmin[cntkdt] = xmax[cntkdt] = qx;
		ymin[cntkdt] = ymax[cntkdt] = qy;
		zmin[cntkdt] = zmax[cntkdt] = qz;
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
			zmin[i] = zmax[i] = z[i];
		} else {
			aliveSiz[i] = aliveSiz[l] + aliveSiz[r];
			xmin[i] = ymin[i] = zmin[i] = INF;
			xmax[i] = ymax[i] = zmax[i] = -INF;
		}
		if (aliveSiz[l] != 0) {
			xmin[i] = Math.min(xmin[i], xmin[l]);
			xmax[i] = Math.max(xmax[i], xmax[l]);
			ymin[i] = Math.min(ymin[i], ymin[l]);
			ymax[i] = Math.max(ymax[i], ymax[l]);
			zmin[i] = Math.min(zmin[i], zmin[l]);
			zmax[i] = Math.max(zmax[i], zmax[l]);
		}
		if (aliveSiz[r] != 0) {
			xmin[i] = Math.min(xmin[i], xmin[r]);
			xmax[i] = Math.max(xmax[i], xmax[r]);
			ymin[i] = Math.min(ymin[i], ymin[r]);
			ymax[i] = Math.max(ymax[i], ymax[r]);
			zmin[i] = Math.min(zmin[i], zmin[r]);
			zmax[i] = Math.max(zmax[i], zmax[r]);
		}
	}

	public static int compareNode(int i, int j, int dimension) {
		double v1 = dimension == 0 ? x[i] : (dimension == 1 ? y[i] : z[i]);
		double v2 = dimension == 0 ? x[j] : (dimension == 1 ? y[j] : z[j]);
		return v1 != v2 ? Double.compare(v1, v2) : (i - j);
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
		ls[rt] = build(l, mid - 1, (dimension + 1) % 3);
		rs[rt] = build(mid + 1, r, (dimension + 1) % 3);
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
			ls[i] = rebuild(ls[i], (dimension + 1) % 3);
		} else {
			rs[i] = rebuild(rs[i], (dimension + 1) % 3);
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
			ls[u] = insert(insertNode, ls[u], (dimension + 1) % 3);
		} else {
			rs[u] = insert(insertNode, rs[u], (dimension + 1) % 3);
		}
		maintain(u);
		if (!balance(u)) {
			top = u;
		}
		return u;
	}

	public static int add(double qx, double qy, double qz, int camera) {
		top = 0;
		int insertNode = init(qx, qy, qz, camera);
		root = insert(insertNode, root, 0);
		rebuild();
		return insertNode;
	}

	public static void erase(int eraseNode, int u, int dimension) {
		if (u == eraseNode) {
			alive[u] = false;
		} else {
			if (compareNode(eraseNode, u, dimension) < 0) {
				erase(eraseNode, ls[u], (dimension + 1) % 3);
			} else {
				erase(eraseNode, rs[u], (dimension + 1) % 3);
			}
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

	public static double mindist(double qx, double qy, double qz, int i) {
		double xd = qx < xmin[i] ? (xmin[i] - qx) : qx > xmax[i] ? (qx - xmax[i]) : 0;
		double yd = qy < ymin[i] ? (ymin[i] - qy) : qy > ymax[i] ? (qy - ymax[i]) : 0;
		double zd = qz < zmin[i] ? (zmin[i] - qz) : qz > zmax[i] ? (qz - zmax[i]) : 0;
		return xd * xd + yd * yd + zd * zd;
	}

	public static double maxdist(double qx, double qy, double qz, int i) {
		double xd2 = Math.max((qx - xmin[i]) * (qx - xmin[i]), (qx - xmax[i]) * (qx - xmax[i]));
		double yd2 = Math.max((qy - ymin[i]) * (qy - ymin[i]), (qy - ymax[i]) * (qy - ymax[i]));
		double zd2 = Math.max((qz - zmin[i]) * (qz - zmin[i]), (qz - zmax[i]) * (qz - zmax[i]));
		return xd2 + yd2 + zd2;
	}

	public static double dist(double qx, double qy, double qz, int i) {
		double xd = qx - x[i];
		double yd = qy - y[i];
		double zd = qz - z[i];
		return xd * xd + yd * yd + zd * zd;
	}

	public static int query(double qx, double qy, double qz, double low, double high, int i) {
		if (i == 0 || aliveSiz[i] == 0) {
			return 0;
		}
		if (mindist(qx, qy, qz, i) > high || maxdist(qx, qy, qz, i) < low) {
			return 0;
		}
		if (alive[i]) {
			double d = dist(qx, qy, qz, i);
			if (low <= d && d <= high) {
				return kdtToCamera[i];
			}
		}
		int ans = query(qx, qy, qz, low, high, ls[i]);
		if (ans != 0) {
			return ans;
		}
		return query(qx, qy, qz, low, high, rs[i]);
	}

	public static int query(double qx, double qy, double qz, double qr) {
		double low = Math.max(0, qr - EPS);
		double high = qr + EPS;
		low = low * low;
		high = high * high;
		return query(qx, qy, qz, low, high, root);
	}

	// 解密
	public static double decode(double encrypt, double l, double r) {
		l = lastAns * l + 1;
		r = lastAns * r + 1;
		for (int i = 0; i < 60; i++) {
			double mid = (l + r) / 2;
			double val = a * mid - b * Math.sin(mid);
			if (val < encrypt) {
				l = mid;
			} else {
				r = mid;
			}
		}
		double decrypt = (l + r) / 2;
		decrypt = (decrypt - 1) / lastAns;
		return decrypt;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		a = in.nextDouble();
		b = in.nextDouble();
		lastAns = 0.1;
		xmin[0] = ymin[0] = zmin[0] = INF;
		xmax[0] = ymax[0] = zmax[0] = -INF;
		double qx, qy, qz, qr, qid;
		int op, camera, kdtNode, curAns;
		for (int i = 1; i <= n; i++) {
			qx = in.nextDouble();
			qy = in.nextDouble();
			qz = in.nextDouble();
			kdtNode = init(qx, qy, qz, i);
			cameraToKdt[i] = kdtNode;
			arr[i] = kdtNode;
		}
		root = build(1, n, 0);
		for (int i = 1; i <= m; i++) {
			op = in.nextInt();
			if (op == 0) {
				qid = in.nextDouble();
				qx = in.nextDouble();
				qy = in.nextDouble();
				qz = in.nextDouble();
				qx = decode(qx, -100, 100);
				qy = decode(qy, -100, 100);
				qz = decode(qz, -100, 100);
				camera = (int) Math.round(decode(qid, 1, n));
				remove(cameraToKdt[camera]);
				cameraToKdt[camera] = add(qx, qy, qz, camera);
			} else {
				qx = in.nextDouble();
				qy = in.nextDouble();
				qz = in.nextDouble();
				qr = in.nextDouble();
				qx = decode(qx, -100, 100);
				qy = decode(qy, -100, 100);
				qz = decode(qz, -100, 100);
				qr = decode(qr, 0, 400);
				curAns = query(qx, qy, qz, qr);
				out.println(curAns);
				lastAns = curAns;
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

		double nextDouble() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			long intPart = 0;
			while (c > ' ' && c != -1 && c != '.') {
				intPart = intPart * 10 + (c - '0');
				c = readByte();
			}
			double val = (double) intPart;
			if (c == '.') {
				c = readByte();
				double base = 0.1;
				while (c > ' ' && c != -1) {
					val += (c - '0') * base;
					base *= 0.1;
					c = readByte();
				}
			}
			return neg ? -val : val;
		}

	}

}