package class182;

// 领导集团问题，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4577
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_LeadersGroup1 {

	public static int MAXN = 200001;
	public static int MAXV = 1000000000;
	public static int MAXT = MAXN * 40;
	public static int n;
	public static int[] arr = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int cntg;

	public static int[] root = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] max = new int[MAXT];
	public static int[] addTag = new int[MAXT];
	public static int cntt;

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void up(int i) {
		max[i] = Math.max(max[ls[i]], max[rs[i]]);
	}

	public static void lazy(int i, int v) {
		// 如果区间信息不存在
		// 说明没有建立过dp信息
		// 那么不需要增加v
		if (i != 0) {
			max[i] += v;
			addTag[i] += v;
		}
	}

	public static void down(int i) {
		if (addTag[i] > 0) {
			lazy(ls[i], addTag[i]);
			lazy(rs[i], addTag[i]);
			addTag[i] = 0;
		}
	}

	public static int add(int jobi, int jobv, int l, int r, int i) {
		int rt = i;
		if (rt == 0) {
			rt = ++cntt;
		}
		if (l == r) {
			max[rt] = Math.max(max[rt], jobv);
		} else {
			down(rt);
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				ls[rt] = add(jobi, jobv, l, mid, ls[rt]);
			} else {
				rs[rt] = add(jobi, jobv, mid + 1, r, rs[rt]);
			}
			up(rt);
		}
		return rt;
	}

	public static int max1, max2;

	public static int merge(int l, int r, int t1, int t2) {
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
			max[t1] = Math.max(max[t1], max1) + Math.max(max[t2], max2);
		} else {
			down(t1);
			down(t2);
			int mid = (l + r) >> 1;
			int mx1 = max1;
			int mx2 = max2;
			max1 = Math.max(max1, max[rs[t1]]);
			max2 = Math.max(max2, max[rs[t2]]);
			ls[t1] = merge(l, mid, ls[t1], ls[t2]);
			max1 = mx1;
			max2 = mx2;
			rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
			up(t1);
		}
		return t1;
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (i == 0) {
			return 0;
		}
		if (jobl <= l && r <= jobr) {
			return max[i];
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

	public static void dp(int u) {
		int val = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			dp(v);
			val += query(arr[u], MAXV, 1, MAXV, root[v]);
			max1 = max2 = 0;
			root[u] = merge(1, MAXV, root[u], root[v]);
		}
		root[u] = add(arr[u], val, 1, MAXV, root[u]);
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
		dp(1);
		out.println(max[root[1]]);
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
