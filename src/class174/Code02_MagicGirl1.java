package class174;

// 魔法少女网站，java版
// 测试链接 : https://www.luogu.com.cn/problem/P6578
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
// 想通过用C++实现，本节课Code02_MagicGirl2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code02_MagicGirl1 {

	public static int MAXN = 300005;
	public static int MAXB = 601;
	public static int POW2 = 9;
	public static int BLEN = 1 << POW2;
	public static int OFFSET = BLEN - 1;
	public static int n, m;

	public static int[] arr = new int[MAXN];
	public static int[] pos = new int[MAXN];

	public static int[] op = new int[MAXN];
	public static int[] x = new int[MAXN];
	public static int[] y = new int[MAXN];
	public static int[] v = new int[MAXN];

	public static int[] arrq = new int[MAXN];
	public static int[] cntv = new int[MAXB];
	public static int[] help = new int[MAXN];
	public static int siz;

	public static int[] last = new int[MAXN];
	public static int[] next = new int[MAXN];

	public static int[] pre = new int[MAXN];
	public static int[] suf = new int[MAXN];
	public static int[] len = new int[MAXN];
	public static long[] ans = new long[MAXN];

	public static void mergeAns(int i, int rpre, int rsuf, int rlen, int rans) {
		ans[i] += rans + 1L * suf[i] * rpre;
		if (pre[i] == len[i]) {
			pre[i] += rpre;
		}
		if (rsuf == rlen) {
			suf[i] += rsuf;
		} else {
			suf[i] = rsuf;
		}
		len[i] += rlen;
	}

	// 根据arr[pos[i]]的值，对pos[l..r]进行双指针快排
	public static void quickSort(int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = arr[pos[(l + r) >> 1]], tmp;
		while (i <= j) {
			while (arr[pos[i]] < pivot) i++;
			while (arr[pos[j]] > pivot) j--;
			if (i <= j) {
				tmp = pos[i]; pos[i] = pos[j]; pos[j] = tmp;
				i++; j--;
			}
		}
		quickSort(l, j);
		quickSort(i, r);
	}

	// 根据查询任务的v值，对查询任务的编号进行基数排序
	public static void radixSort() {
		Arrays.fill(cntv, 0);
		for (int i = 1; i <= siz; i++) cntv[v[arrq[i]] & OFFSET]++;
		for (int i = 1; i < MAXB; i++) cntv[i] += cntv[i - 1];
		for (int i = siz; i >= 1; i--) help[cntv[v[arrq[i]] & OFFSET]--] = arrq[i];
		for (int i = 1; i <= siz; i++) arrq[i] = help[i];
		Arrays.fill(cntv, 0);
		for (int i = 1; i <= siz; i++) cntv[v[arrq[i]] >> POW2]++;
		for (int i = 1; i < MAXB; i++) cntv[i] += cntv[i - 1];
		for (int i = siz; i >= 1; i--) help[cntv[v[arrq[i]] >> POW2]--] = arrq[i];
		for (int i = 1; i <= siz; i++) arrq[i] = help[i];
	}

	public static void calc(int l, int r) {
		radixSort();
		for (int i = l; i <= r; i++) {
			last[i] = i - 1;
			next[i] = i + 1;
		}
		int rpre = 0, rsuf = 0, rlen = r - l + 1, rans = 0;
		int k = 1;
		for (int i = l, idx; i <= r; i++) {
			idx = pos[i];
			for (; k <= siz && v[arrq[k]] < arr[idx]; k++) {
				mergeAns(arrq[k], rpre, rsuf, rlen, rans);
			}
			if (last[idx] == l - 1) {
				rpre += next[idx] - idx;
			}
			if (next[idx] == r + 1) {
				rsuf += idx - last[idx];
			}
			rans += 1L * (idx - last[idx]) * (next[idx] - idx);
			last[next[idx]] = last[idx];
			next[last[idx]] = next[idx];
		}
		for (; k <= siz; k++) {
			mergeAns(arrq[k], rpre, rsuf, rlen, rans);
		}
		siz = 0;
	}

	public static void update(int qi, int l, int r) {
		int jobi = x[qi], jobv = y[qi];
		if (l <= jobi && jobi <= r) {
			calc(l, r);
			arr[jobi] = jobv;
			int find = 0;
			for (int i = l; i <= r; i++) {
				if (pos[i] == jobi) {
					find = i;
					break;
				}
			}
			int tmp;
			for (int i = find; i < r && arr[pos[i]] > arr[pos[i + 1]]; i++) {
				tmp = pos[i]; pos[i] = pos[i + 1]; pos[i + 1] = tmp;
			}
			for (int i = find; i > l && arr[pos[i - 1]] > arr[pos[i]]; i--) {
				tmp = pos[i - 1]; pos[i - 1] = pos[i]; pos[i] = tmp;
			}
		}
	}

	public static void query(int qi, int l, int r) {
		int jobl = x[qi], jobr = y[qi], jobv = v[qi];
		if (jobl <= l && r <= jobr) {
			arrq[++siz] = qi;
		} else {
			for (int i = Math.max(jobl, l); i <= Math.min(jobr, r); i++) {
				if (arr[i] <= jobv) {
					mergeAns(qi, 1, 1, 1, 1);
				} else {
					mergeAns(qi, 0, 0, 1, 0);
				}
			}
		}
	}

	public static void compute(int l, int r) {
		for (int i = l; i <= r; i++) {
			pos[i] = i;
		}
		quickSort(l, r);
		for (int qi = 1; qi <= m; qi++) {
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
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1; i <= m; i++) {
			op[i] = in.nextInt();
			x[i] = in.nextInt();
			y[i] = in.nextInt();
			if (op[i] == 2) {
				v[i] = in.nextInt();
			}
		}
		int bnum = (n + BLEN - 1) / BLEN;
		for (int i = 1, l, r; i <= bnum; i++) {
			l = (i - 1) * BLEN + 1;
			r = Math.min(i * BLEN, n);
			compute(l, r);
		}
		for (int i = 1; i <= m; i++) {
			if (op[i] == 2) {
				out.println(ans[i]);
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