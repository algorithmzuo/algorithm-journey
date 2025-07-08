package class174;

// 魔法少女网站，java版
// 测试链接 : https://www.luogu.com.cn/problem/P6578
// 提交以下的code，提交时请把类名改成"Main"，本题卡常，java实现无法通过
// 想通过用C++实现，本节课Code03_MagicGirl2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code03_MagicGirl1 {

	static class Node {
		int v, i;

		Node(int v_, int i_) {
			v = v_;
			i = i_;
		}
	}

	static class Answer {
		int pre, suf, len;
		long res;

		Answer(int p, int s, int l, long r) {
			pre = p;
			suf = s;
			len = l;
			res = r;
		}

		void merge(Answer right) {
			res += right.res + 1L * suf * right.pre;
			if (pre == len)
				pre += right.pre;
			if (right.suf == right.len) {
				suf += right.suf;
			} else {
				suf = right.suf;
			}
			len += right.len;
		}

	}

	public static int MAXN = 300005;
	public static int MAXB = 601;
	public static int BLEN = 512;
	public static int OFFSET = 0x1ff;
	public static int POWER = 9;
	public static int n, m;

	public static int[] arr = new int[MAXN];
	public static Node[] sortv = new Node[MAXN];

	public static int[] op = new int[MAXN];
	public static int[] x = new int[MAXN];
	public static int[] y = new int[MAXN];
	public static int[] z = new int[MAXN];

	public static int[] head1 = new int[MAXB];
	public static int[] head2 = new int[MAXB];
	public static int[] toq = new int[MAXN << 1];
	public static int[] nexte = new int[MAXN << 1];
	public static int cntg;

	public static int[] sortq = new int[MAXN];
	public static int cntq;

	public static int[] lst = new int[MAXN];
	public static int[] nxt = new int[MAXN];

	public static Answer tmp = new Answer(0, 0, 0, 0);
	public static Answer[] ans = new Answer[MAXN];

	public static void swap(Node[] a, int i, int j) {
		Node t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	public static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	public static void addEdge(int[] head, int u, int v) {
		nexte[++cntg] = head[u];
		toq[cntg] = v;
		head[u] = cntg;
	}

	public static void calc(int l, int r) {
		for (int md = 0; md < BLEN; ++md) {
			for (int e = head1[md]; e != 0; e = nexte[e]) {
				addEdge(head2, z[toq[e]] >> POWER, toq[e]);
			}
			head1[md] = 0;
		}
		for (int tm = n / BLEN; tm >= 0; --tm) {
			for (int e = head2[tm]; e != 0; e = nexte[e]) {
				sortq[++cntq] = toq[e];
			}
			head2[tm] = 0;
		}
		for (int left = 1, right = cntq; left < right; ++left, --right) {
			swap(sortq, left, right);
		}
		for (int i = l; i <= r; ++i) {
			lst[i] = i - 1;
			nxt[i] = i + 1;
		}
		tmp.pre = tmp.suf = 0;
		tmp.len = r - l + 1;
		tmp.res = 0;
		int k = 1;
		for (int i = l; i <= r; ++i) {
			int idx = sortv[i].i;
			while (k <= cntq && z[sortq[k]] < arr[idx]) {
				ans[sortq[k++]].merge(tmp);
			}
			if (lst[idx] == l - 1) {
				tmp.pre += nxt[idx] - idx;
			}
			if (nxt[idx] == r + 1) {
				tmp.suf += idx - lst[idx];
			}
			tmp.res += 1L * (idx - lst[idx]) * (nxt[idx] - idx);
			lst[nxt[idx]] = lst[idx];
			nxt[lst[idx]] = nxt[idx];
		}
		while (k <= cntq) {
			ans[sortq[k++]].merge(tmp);
		}
		cntg = cntq = 0;
	}

	public static void update(int qi, int l, int r) {
		int jobi = x[qi], jobv = y[qi];
		if (l <= jobi && jobi <= r) {
			calc(l, r);
			arr[jobi] = jobv;
			int pos = 0;
			for (int i = l; i <= r; ++i) {
				if (sortv[i].i == jobi) {
					sortv[i].v = jobv;
					pos = i;
					break;
				}
			}
			for (int i = pos; i < r && sortv[i].v > sortv[i + 1].v; ++i) {
				swap(sortv, i, i + 1);
			}
			for (int i = pos; i > l && sortv[i - 1].v > sortv[i].v; --i) {
				swap(sortv, i - 1, i);
			}
		}
	}

	public static void query(int qi, int l, int r) {
		int jobl = x[qi], jobr = y[qi], jobv = z[qi];
		if (jobl <= l && r <= jobr) {
			addEdge(head1, jobv & OFFSET, qi);
		} else {
			for (int i = Math.max(jobl, l); i <= Math.min(jobr, r); ++i) {
				if (arr[i] <= jobv) {
					tmp.pre = tmp.suf = tmp.len = 1;
					tmp.res = 1;
				} else {
					tmp.pre = tmp.suf = 0;
					tmp.len = 1;
					tmp.res = 0;
				}
				ans[qi].merge(tmp);
			}
		}
	}

	public static void compute(int l, int r) {
		for (int i = l; i <= r; ++i) {
			sortv[i] = new Node(arr[i], i);
		}
		Arrays.sort(sortv, l, r + 1, (a, b) -> a.v - b.v);
		for (int qi = 1; qi <= m; ++qi) {
			if (op[qi] == 1) {
				update(qi, l, r);
			} else {
				query(qi, l, r);
			}
		}
		calc(l, r);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; ++i) {
			arr[i] = in.nextInt();
		}
		for (int i = 1; i <= m; ++i) {
			op[i] = in.nextInt();
			x[i] = in.nextInt();
			y[i] = in.nextInt();
			if (op[i] == 2) {
				z[i] = in.nextInt();
			}
		}
		for (int i = 1; i <= m; ++i) {
			ans[i] = new Answer(0, 0, 0, 0);
		}
		int BNUM = (n + BLEN - 1) / BLEN;
		for (int b = 1, l, r; b <= BNUM; ++b) {
			l = (b - 1) * BLEN + 1;
			r = Math.min(b * BLEN, n);
			compute(l, r);
		}
		for (int i = 1; i <= m; ++i) {
			if (op[i] == 2) {
				out.println(ans[i].res);
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