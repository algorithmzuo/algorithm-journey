package class182;

// 领导集团问题，java版
// 一共有n个节点，给定每个点的点权，所有节点组成一棵树
// 已知1号节点是整棵树的头，其他节点的父亲节点都会给出
// 如果你在树上选择了u、v两个节点，并且u是v的祖先节点的话
// 那么需要保证 u的点权 <= v的点权，除此之外就没有别的限制了
// 打印你最多能在树上选择几个点
// 1 <= n <= 2 * 10^5
// 1 <= 点权 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P4577
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code01_LeadersGroup1 {

	public static int MAXN = 200001;
	public static int MAXT = MAXN * 20;
	public static int n;
	public static int[] arr = new int[MAXN];
	public static int[] sorted = new int[MAXN];
	public static int cntv;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int cntg;

	public static int[] root = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] maxv = new int[MAXT];
	public static int[] addLazy = new int[MAXT];
	public static int cntt;

	public static int kth(int num) {
		int left = 1, right = cntv, mid, ret = 0;
		while (left <= right) {
			mid = (left + right) >> 1;
			if (sorted[mid] <= num) {
				ret = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return ret;
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void up(int i) {
		maxv[i] = Math.max(maxv[ls[i]], maxv[rs[i]]);
	}

	public static void lazy(int i, int v) {
		if (i != 0) {
			maxv[i] += v;
			addLazy[i] += v;
		}
	}

	public static void down(int i) {
		if (addLazy[i] > 0) {
			lazy(ls[i], addLazy[i]);
			lazy(rs[i], addLazy[i]);
			addLazy[i] = 0;
		}
	}

	// jobi来了个新值jobv，如果比之前获得的值更大就更新，否则不更新
	public static int update(int jobi, int jobv, int l, int r, int i) {
		int rt = i;
		if (rt == 0) {
			rt = ++cntt;
		}
		if (l == r) {
			maxv[rt] = Math.max(maxv[rt], jobv);
		} else {
			down(rt);
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				ls[rt] = update(jobi, jobv, l, mid, ls[rt]);
			} else {
				rs[rt] = update(jobi, jobv, mid + 1, r, rs[rt]);
			}
			up(rt);
		}
		return rt;
	}

	// 查询[jobl..jobr]范围上的最大值
	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (i == 0) {
			return 0;
		}
		if (jobl <= l && r <= jobr) {
			return maxv[i];
		}
		down(i);
		int mid = (l + r) >> 1;
		int ans = 0;
		if (jobl <= mid) {
			ans = Math.max(ans, query(jobl, jobr, l, mid, ls[i]));
		}
		if (jobr > mid) {
			ans = Math.max(ans, query(jobl, jobr, mid + 1, r, rs[i]));
		}
		return ans;
	}

	// 线段树合并
	// max1代表dp[u][r+1...]的最大值，max2代表dp[v][r+1...]的最大值
	public static int merge(int l, int r, int t1, int t2, int max1, int max2) {
		if (t1 == 0 || t2 == 0) {
			if (t1 != 0) {
				lazy(t1, max2);
			}
			if (t2 != 0) {
				lazy(t2, max1);
			}
			return t1 + t2;
		}
		if (l == r) {
			maxv[t1] += Math.max(maxv[t2], max2);
		} else {
			down(t1);
			down(t2);
			int mid = (l + r) >> 1;
			ls[t1] = merge(l, mid, ls[t1], ls[t2], Math.max(max1, maxv[rs[t1]]), Math.max(max2, maxv[rs[t2]]));
			rs[t1] = merge(mid + 1, r, rs[t1], rs[t2], max1, max2);
			up(t1);
		}
		return t1;
	}

	public static void dp(int u) {
		int sum = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			dp(v);
			sum += query(arr[u], cntv, 1, cntv, root[v]);
			// 不选u的情况，每棵子树合并一遍
			root[u] = merge(1, cntv, root[u], root[v], 0, 0);
		}
		// 选u的情况，最后sum需要加1
		root[u] = update(arr[u], sum + 1, 1, cntv, root[u]);
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			sorted[++cntv] = arr[i];
		}
		Arrays.sort(sorted, 1, cntv + 1);
		int len = 1;
		for (int i = 2; i <= cntv; i++) {
			if (sorted[len] != sorted[i]) {
				sorted[++len] = sorted[i];
			}
		}
		cntv = len;
		for (int i = 1; i <= n; i++) {
			arr[i] = kth(arr[i]);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 2, fa; i <= n; i++) {
			fa = in.nextInt();
			addEdge(fa, i);
		}
		prepare();
		dp(1);
		out.println(query(1, cntv, 1, cntv, root[1]));
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
