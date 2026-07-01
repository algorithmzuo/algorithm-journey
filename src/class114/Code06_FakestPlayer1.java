package class114;

// 最假女选手，java版
// 这道题课上没讲，也是吉司机线段树的经典模版题
// 这道题不涉及历史最值，所以比题目5简单
// 如果理解了题目5，完全可以看懂如下的代码，重点的地方我加了注释
// 给定一个长度为n的数组arr，然后是m条操作，类型如下
// 操作 1 l r x : arr[l..r]范围内，每个数都加上x
// 操作 2 l r x : arr[l..r]范围内，小于x的数都变成x
// 操作 3 l r x : arr[l..r]范围内，大于x的数都变成x
// 操作 4 l r   : 查询arr[l..r]累加和
// 操作 5 l r   : 查询arr[l..r]最大值
// 操作 6 l r   : 查询arr[l..r]最小值
// 1 <= n、m <= 5 * 10^5    累加和需要long类型
// 测试链接 : https://loj.ac/p/6565
// 测试链接 : https://www.luogu.com.cn/problem/P10639
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_FakestPlayer1 {

	public static int MAXN = 500001;
	public static int INF = 1000000001;
	public static int n, m;

	public static int[] arr = new int[MAXN];
	public static long[] sum = new long[MAXN << 2];

	public static int[] max = new int[MAXN << 2];
	public static int[] cntMax = new int[MAXN << 2];
	public static int[] sem = new int[MAXN << 2];

	// 区间最小值
	public static int[] min = new int[MAXN << 2];
	// 区间最小值的个数
	public static int[] cntMin = new int[MAXN << 2];
	// 区间严格次小值
	public static int[] sim = new int[MAXN << 2];

	// 整个区间统一增加的懒信息
	public static int[] addTag = new int[MAXN << 2];

	public static void up(int i) {
		int l = i << 1;
		int r = i << 1 | 1;
		sum[i] = sum[l] + sum[r];
		if (max[l] > max[r]) {
			max[i] = max[l];
			cntMax[i] = cntMax[l];
			sem[i] = Math.max(sem[l], max[r]);
		} else if (max[l] < max[r]) {
			max[i] = max[r];
			cntMax[i] = cntMax[r];
			sem[i] = Math.max(max[l], sem[r]);
		} else {
			max[i] = max[l];
			cntMax[i] = cntMax[l] + cntMax[r];
			sem[i] = Math.max(sem[l], sem[r]);
		}
		if (min[l] < min[r]) {
			min[i] = min[l];
			cntMin[i] = cntMin[l];
			sim[i] = Math.min(sim[l], min[r]);
		} else if (min[l] > min[r]) {
			min[i] = min[r];
			cntMin[i] = cntMin[r];
			sim[i] = Math.min(min[l], sim[r]);
		} else {
			min[i] = min[l];
			cntMin[i] = cntMin[l] + cntMin[r];
			sim[i] = Math.min(sim[l], sim[r]);
		}
	}

	public static void lazyAdd(int i, int n, int v) {
		sum[i] += 1L * v * n;
		max[i] += v;
		sem[i] += sem[i] == -INF ? 0 : v;
		min[i] += v;
		sim[i] += sim[i] == INF ? 0 : v;
		addTag[i] += v;
	}

	// 只把当前区间中的最大值压到v，要求sem[i] < v < max[i]
	public static void lazySetMin(int i, int v) {
		sum[i] += 1L * (v - max[i]) * cntMax[i];
		if (min[i] == max[i]) {
			min[i] = v;
		} else if (sim[i] == max[i]) {
			sim[i] = v;
		}
		max[i] = v;
	}

	// 只把当前区间中的最小值抬到v，要求min[i] < v < sim[i]
	public static void lazySetMax(int i, int v) {
		sum[i] += 1L * (v - min[i]) * cntMin[i];
		if (max[i] == min[i]) {
			max[i] = v;
		} else if (sem[i] == min[i]) {
			sem[i] = v;
		}
		min[i] = v;
	}

	public static void down(int i, int ln, int rn) {
		int l = i << 1;
		int r = i << 1 | 1;
		if (addTag[i] != 0) {
			lazyAdd(l, ln, addTag[i]);
			lazyAdd(r, rn, addTag[i]);
			addTag[i] = 0;
		}
		if (max[l] > max[i]) {
			lazySetMin(l, max[i]);
		}
		if (min[l] < min[i]) {
			lazySetMax(l, min[i]);
		}
		if (max[r] > max[i]) {
			lazySetMin(r, max[i]);
		}
		if (min[r] < min[i]) {
			lazySetMax(r, min[i]);
		}
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = max[i] = min[i] = arr[l];
			sem[i] = -INF;
			sim[i] = INF;
			cntMax[i] = cntMin[i] = 1;
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
		addTag[i] = 0;
	}

	public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazyAdd(i, r - l + 1, jobv);
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static void setMax(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobv <= min[i]) {
			return;
		}
		if (jobl <= l && r <= jobr && jobv < sim[i]) {
			lazySetMax(i, jobv);
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				setMax(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				setMax(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static void setMin(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobv >= max[i]) {
			return;
		}
		if (jobl <= l && r <= jobr && sem[i] < jobv) {
			lazySetMin(i, jobv);
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				setMin(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				setMin(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static long querySum(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			long ans = 0;
			if (jobl <= mid) {
				ans += querySum(jobl, jobr, l, mid, i << 1);
			}
			if (jobr > mid) {
				ans += querySum(jobl, jobr, mid + 1, r, i << 1 | 1);
			}
			return ans;
		}
	}

	public static int queryMax(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return max[i];
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			int ans = -INF;
			if (jobl <= mid) {
				ans = Math.max(ans, queryMax(jobl, jobr, l, mid, i << 1));
			}
			if (jobr > mid) {
				ans = Math.max(ans, queryMax(jobl, jobr, mid + 1, r, i << 1 | 1));
			}
			return ans;
		}
	}

	public static int queryMin(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return min[i];
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			int ans = INF;
			if (jobl <= mid) {
				ans = Math.min(ans, queryMin(jobl, jobr, l, mid, i << 1));
			}
			if (jobr > mid) {
				ans = Math.min(ans, queryMin(jobl, jobr, mid + 1, r, i << 1 | 1));
			}
			return ans;
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		build(1, n, 1);
		m = in.nextInt();
		for (int i = 1, op, l, r, x; i <= m; i++) {
			op = in.nextInt();
			l = in.nextInt();
			r = in.nextInt();
			if (op == 1) {
				x = in.nextInt();
				add(l, r, x, 1, n, 1);
			} else if (op == 2) {
				x = in.nextInt();
				setMax(l, r, x, 1, n, 1);
			} else if (op == 3) {
				x = in.nextInt();
				setMin(l, r, x, 1, n, 1);
			} else if (op == 4) {
				out.println(querySum(l, r, 1, n, 1));
			} else if (op == 5) {
				out.println(queryMax(l, r, 1, n, 1));
			} else {
				out.println(queryMin(l, r, 1, n, 1));
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