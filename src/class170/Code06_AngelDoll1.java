package class170;

// 天使玩偶，java版
// 规定(x1, y1)和(x2, y2)之间的距离 = | x1 - x2 | + | y1 - y2 |
// 一开始先给定n个点的位置，接下来有m条操作，每种操作是如下两种类型中的一种
// 操作 1 x y : 在(x, y)位置添加一个点
// 操作 2 x y : 打印已经添加的所有点中，到(x, y)位置最短距离的点是多远
// 1 <= n、m <= 3 * 10^5
// 0 <= x、y <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P4169
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code06_AngelDoll1 {

	public static int MAXN = 300001;
	public static int MAXV = 1000002;
	public static int INF = 1000000001;
	public static int n, m, v;

	// op == 1代表添加事件，x、y、空缺
	// op == 1代表查询事件，x、y、查询编号q
	// tim永远保持原始时序，每次变换象限都拷贝给arr，然后执行cdq分治
	public static int[][] tim = new int[MAXN << 1][4];
	public static int[][] arr = new int[MAXN << 1][4];
	public static int cnte = 0;
	public static int cntq = 0;

	// 树状数组，下标是y的值，维护前缀范围上的最大值
	public static int[] tree = new int[MAXV];

	public static int[] ans = new int[MAXN];

	public static void clone(int[] a, int[] b) {
		a[0] = b[0];
		a[1] = b[1];
		a[2] = b[2];
		a[3] = b[3];
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	// i位置的值设置成num，类似累加和操作，这里变成取最大值操作即可
	public static void update(int i, int num) {
		while (i <= v) {
			tree[i] = Math.max(tree[i], num);
			i += lowbit(i);
		}
	}

	// 查询1~i范围上的最大值
	public static int query(int i) {
		int ret = -INF;
		while (i > 0) {
			ret = Math.max(ret, tree[i]);
			i -= lowbit(i);
		}
		return ret;
	}

	// 因为本题的特殊性，树状数组一定全部清空
	// 所以当初更新时，i位置碰过哪些位置，一律设置无效值即可
	public static void clear(int i) {
		while (i <= v) {
			tree[i] = -INF;
			i += lowbit(i);
		}
	}

	public static void merge(int l, int m, int r) {
		int p1, p2;
		for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
			while (p1 + 1 <= m && arr[p1 + 1][1] <= arr[p2][1]) {
				p1++;
				if (arr[p1][0] == 1) {
					update(arr[p1][2], arr[p1][1] + arr[p1][2]);
				}
			}
			if (arr[p2][0] == 2) {
				ans[arr[p2][3]] = Math.min(ans[arr[p2][3]], arr[p2][1] + arr[p2][2] - query(arr[p2][2]));
			}
		}
		for (int i = l; i <= p1; i++) {
			if (arr[i][0] == 1) {
				clear(arr[i][2]);
			}
		}
		Arrays.sort(arr, l, r + 1, (a, b) -> a[1] - b[1]);
	}

	public static void cdq(int l, int r) {
		if (l == r) {
			return;
		}
		int mid = (l + r) / 2;
		cdq(l, mid);
		cdq(mid + 1, r);
		merge(l, mid, r);
	}

	// 点变换到第一象限进行cdq分治
	public static void to1() {
		for (int i = 1; i <= cnte; i++) {
			clone(arr[i], tim[i]);
		}
		cdq(1, cnte);
	}

	// 点变换到第二象限进行cdq分治
	public static void to2() {
		for (int i = 1; i <= cnte; i++) {
			clone(arr[i], tim[i]);
			arr[i][1] = v - arr[i][1];
		}
		cdq(1, cnte);
	}

	// 点变换到第三象限进行cdq分治
	public static void to3() {
		for (int i = 1; i <= cnte; i++) {
			clone(arr[i], tim[i]);
			arr[i][1] = v - arr[i][1];
			arr[i][2] = v - arr[i][2];
		}
		cdq(1, cnte);
	}

	// 点变换到第四象限进行cdq分治
	public static void to4() {
		for (int i = 1; i <= cnte; i++) {
			clone(arr[i], tim[i]);
			arr[i][2] = v - arr[i][2];
		}
		cdq(1, cnte);
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		// 树状数组下标从1开始，所以x和y都要自增一下
		// x或y的最大值用v记录，变换象限时，防止 v - (x或y) 出现0
		// 所以最后v再自增一下
		for (int i = 1, x, y; i <= n; i++) {
			x = in.nextInt();
			y = in.nextInt();
			tim[++cnte][0] = 1;
			tim[cnte][1] = ++x;
			tim[cnte][2] = ++y;
			v = Math.max(v, Math.max(x, y));
		}
		for (int i = 1, op, x, y; i <= m; i++) {
			op = in.nextInt();
			x = in.nextInt();
			y = in.nextInt();
			tim[++cnte][0] = op;
			tim[cnte][1] = ++x;
			tim[cnte][2] = ++y;
			if (op == 2) {
				tim[cnte][3] = ++cntq;
			}
			v = Math.max(v, Math.max(x, y));
		}
		v++;
		// 初始化树状数组
		for (int i = 1; i <= v; i++) {
			tree[i] = -INF;
		}
		// 初始化答案数组
		for (int i = 1; i <= cntq; i++) {
			ans[i] = INF;
		}
		to1();
		to2();
		to3();
		to4();
		for (int i = 1; i <= cntq; i++) {
			out.println(ans[i]);
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
