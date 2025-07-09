package class174;

// 最小公倍数，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3247
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code04_Lcm1 {

	static class Param {
		int u, v, a, b, qid;

		public Param(int u_, int v_, int a_, int b_) {
			u = u_;
			v = v_;
			a = a_;
			b = b_;
		}
	}

	static class CmpA implements Comparator<Param> {
		@Override
		public int compare(Param o1, Param o2) {
			return o1.a - o2.a;
		}
	}

	static class CmpB implements Comparator<Param> {
		@Override
		public int compare(Param o1, Param o2) {
			return o1.b - o2.b;
		}
	}

	public static CmpA cmpa = new CmpA();
	public static CmpB cmpb = new CmpB();

	public static int MAXN = 50001;
	public static int MAXM = 100001;
	public static int MAXQ = 50001;
	public static int n, m, q;
	public static int blen, bnum;

	public static Param[] edge = new Param[MAXM];
	public static Param[] ques = new Param[MAXQ];

	public static int[] arr = new int[MAXQ];
	public static int cntq = 0;

	public static int[] fa = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] maxa = new int[MAXN];
	public static int[] maxb = new int[MAXN];
	public static int[][] rollback = new int[MAXN][5];
	public static int opsize = 0;

	public static boolean[] ans = new boolean[MAXQ];

	public static void build() {
		for (int i = 1; i <= n; i++) {
			fa[i] = i;
			siz[i] = 1;
			maxa[i] = -1;
			maxb[i] = -1;
		}
	}

	public static int find(int x) {
		while (x != fa[x]) {
			x = fa[x];
		}
		return x;
	}

	public static void union(int x, int y, int a, int b) {
		int fx = find(x);
		int fy = find(y);
		if (siz[fx] < siz[fy]) {
			int tmp = fx;
			fx = fy;
			fy = tmp;
		}
		rollback[++opsize][0] = fx;
		rollback[opsize][1] = fy;
		rollback[opsize][2] = siz[fx];
		rollback[opsize][3] = maxa[fx];
		rollback[opsize][4] = maxb[fx];
		if (fx != fy) {
			fa[fy] = fx;
			siz[fx] += siz[fy];
		}
		maxa[fx] = Math.max(Math.max(maxa[fx], maxa[fy]), a);
		maxb[fx] = Math.max(Math.max(maxb[fx], maxb[fy]), b);
	}

	public static void undo() {
		for (int fx, fy; opsize > 0; opsize--) {
			fx = rollback[opsize][0];
			fy = rollback[opsize][1];
			fa[fy] = fy;
			siz[fx] = rollback[opsize][2];
			maxa[fx] = rollback[opsize][3];
			maxb[fx] = rollback[opsize][4];
		}
	}

	public static boolean query(int x, int y, int a, int b) {
		int fx = find(x);
		int fy = find(y);
		return fx == fy && maxa[fx] == a && maxb[fx] == b;
	}

	public static void compute(int l, int r) {
		build();
		cntq = 0;
		for (int i = 1; i <= q; i++) {
			// 保证每条询问只落入其中的一块
			if (edge[l].a <= ques[i].a && (r + 1 > m || ques[i].a < edge[r + 1].a)) {
				arr[++cntq] = i;
			}
		}
		if (cntq > 0) {
			Arrays.sort(edge, 1, l, cmpb);
			int pos = 1;
			for (int i = 1; i <= cntq; i++) {
				for (; pos < l && edge[pos].b <= ques[arr[i]].b; pos++) {
					union(edge[pos].u, edge[pos].v, edge[pos].a, edge[pos].b);
				}
				opsize = 0;
				for (int j = l; j <= r; j++) {
					if (edge[j].a <= ques[arr[i]].a && edge[j].b <= ques[arr[i]].b) {
						union(edge[j].u, edge[j].v, edge[j].a, edge[j].b);
					}
				}
				ans[ques[arr[i]].qid] = query(ques[arr[i]].u, ques[arr[i]].v, ques[arr[i]].a, ques[arr[i]].b);
				undo();
			}
		}
	}

	public static int log2(int n) {
		int ans = 0;
		while ((1 << ans) <= (n >> 1)) {
			ans++;
		}
		return ans;
	}

	public static void prepare() {
		blen = Math.max(1, (int) Math.sqrt(m * log2(n)));
		bnum = (m + blen - 1) / blen;
		Arrays.sort(edge, 1, m + 1, cmpa);
		Arrays.sort(ques, 1, q + 1, cmpb);
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v, a, b; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			a = in.nextInt();
			b = in.nextInt();
			edge[i] = new Param(u, v, a, b);
		}
		q = in.nextInt();
		for (int i = 1, u, v, a, b; i <= q; i++) {
			u = in.nextInt();
			v = in.nextInt();
			a = in.nextInt();
			b = in.nextInt();
			ques[i] = new Param(u, v, a, b);
			ques[i].qid = i;
		}
		prepare();
		for (int i = 1, l, r; i <= bnum; i++) {
			l = (i - 1) * blen + 1;
			r = Math.min(i * blen, m);
			compute(l, r);
		}
		for (int i = 1; i <= q; i++) {
			if (ans[i]) {
				out.println("Yes");
			} else {
				out.println("No");
			}
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
