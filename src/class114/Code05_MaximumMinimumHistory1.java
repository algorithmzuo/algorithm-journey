package class114;

// 区间最值和历史最值，java版
// 给定两个长度都为n的数组A和B，一开始两个数组完全一样
// 任何操作做完，都更新B数组，B[i] = max(B[i],A[i])
// 实现以下五种操作，一共会调用m次
// 操作 1 l r v : A[l..r]范围上每个数加上v
// 操作 2 l r v : A[l..r]范围上每个数A[i]变成min(A[i],v)
// 操作 3 l r   : 返回A[l..r]范围上的累加和
// 操作 4 l r   : 返回A[l..r]范围上的最大值
// 操作 5 l r   : 返回B[l..r]范围上的最大值
// 1 <= n、m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P6242
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_MaximumMinimumHistory1 {

	public static int MAXN = 500001;
	public static long LOWEST = Long.MIN_VALUE;
	public static int n, m;

	public static int[] arr = new int[MAXN];
	public static long[] sum = new long[MAXN << 2];
	public static long[] max = new long[MAXN << 2];
	public static int[] cnt = new int[MAXN << 2];
	public static long[] sem = new long[MAXN << 2];
	public static long[] maxAdd = new long[MAXN << 2];
	public static long[] otherAdd = new long[MAXN << 2];

	// 历史最大值
	public static long[] maxHistory = new long[MAXN << 2];

	// 最大值达到过的最大提升幅度(懒更新信息)
	public static long[] maxAddTop = new long[MAXN << 2];

	// 除最大值以外的其他数字，达到过的最大提升幅度(懒更新信息)
	public static long[] otherAddTop = new long[MAXN << 2];

	public static void up(int i) {
		int l = i << 1;
		int r = i << 1 | 1;
		maxHistory[i] = Math.max(maxHistory[l], maxHistory[r]);
		sum[i] = sum[l] + sum[r];
		max[i] = Math.max(max[l], max[r]);
		if (max[l] > max[r]) {
			cnt[i] = cnt[l];
			sem[i] = Math.max(sem[l], max[r]);
		} else if (max[l] < max[r]) {
			cnt[i] = cnt[r];
			sem[i] = Math.max(max[l], sem[r]);
		} else {
			cnt[i] = cnt[l] + cnt[r];
			sem[i] = Math.max(sem[l], sem[r]);
		}
	}

	// maxAddv   : 最大值增加多少
	// otherAddv : 其他数增加多少
	// maxUpv    : 最大值达到过的最大提升幅度
	// otherUpv  : 其他数达到过的最大提升幅度
	public static void lazy(int i, int n, long maxAddv, long otherAddv, long maxUpv, long otherUpv) {
		maxHistory[i] = Math.max(maxHistory[i], max[i] + maxUpv);
		maxAddTop[i] = Math.max(maxAddTop[i], maxAdd[i] + maxUpv);
		otherAddTop[i] = Math.max(otherAddTop[i], otherAdd[i] + otherUpv);
		sum[i] += maxAddv * cnt[i] + otherAddv * (n - cnt[i]);
		max[i] += maxAddv;
		sem[i] += sem[i] == LOWEST ? 0 : otherAddv;
		maxAdd[i] += maxAddv;
		otherAdd[i] += otherAddv;
	}

	public static void down(int i, int ln, int rn) {
		int l = i << 1;
		int r = i << 1 | 1;
		long tmp = Math.max(max[l], max[r]);
		if (max[l] == tmp) {
			lazy(l, ln, maxAdd[i], otherAdd[i], maxAddTop[i], otherAddTop[i]);
		} else {
			lazy(l, ln, otherAdd[i], otherAdd[i], otherAddTop[i], otherAddTop[i]);
		}
		if (max[r] == tmp) {
			lazy(r, rn, maxAdd[i], otherAdd[i], maxAddTop[i], otherAddTop[i]);
		} else {
			lazy(r, rn, otherAdd[i], otherAdd[i], otherAddTop[i], otherAddTop[i]);
		}
		maxAdd[i] = otherAdd[i] = maxAddTop[i] = otherAddTop[i] = 0;
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = max[i] = maxHistory[i] = arr[l];
			sem[i] = LOWEST;
			cnt[i] = 1;
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
		maxAdd[i] = otherAdd[i] = maxAddTop[i] = otherAddTop[i] = 0;
	}

	public static void add(int jobl, int jobr, long jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, r - l + 1, jobv, jobv, jobv, jobv);
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

	public static void setMin(int jobl, int jobr, long jobv, int l, int r, int i) {
		if (jobv >= max[i]) {
			return;
		}
		if (jobl <= l && r <= jobr && sem[i] < jobv) {
			lazy(i, r - l + 1, jobv - max[i], 0, jobv - max[i], 0);
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

	public static long queryMax(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return max[i];
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			Long ans = LOWEST;
			if (jobl <= mid) {
				ans = Math.max(ans, queryMax(jobl, jobr, l, mid, i << 1));
			}
			if (jobr > mid) {
				ans = Math.max(ans, queryMax(jobl, jobr, mid + 1, r, i << 1 | 1));
			}
			return ans;
		}
	}

	public static long queryHistoryMax(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return maxHistory[i];
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			Long ans = LOWEST;
			if (jobl <= mid) {
				ans = Math.max(ans, queryHistoryMax(jobl, jobr, l, mid, i << 1));
			}
			if (jobr > mid) {
				ans = Math.max(ans, queryHistoryMax(jobl, jobr, mid + 1, r, i << 1 | 1));
			}
			return ans;
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		build(1, n, 1);
		int op, jobl, jobr;
		long jobv;
		for (int i = 1; i <= m; i++) {
			op = in.nextInt();
			jobl = in.nextInt();
			jobr = in.nextInt();
			if (op == 1) {
				jobv = in.nextInt();
				add(jobl, jobr, jobv, 1, n, 1);
			} else if (op == 2) {
				jobv = in.nextInt();
				setMin(jobl, jobr, jobv, 1, n, 1);
			} else if (op == 3) {
				out.println(querySum(jobl, jobr, 1, n, 1));
			} else if (op == 4) {
				out.println(queryMax(jobl, jobr, 1, n, 1));
			} else {
				out.println(queryHistoryMax(jobl, jobr, 1, n, 1));
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
