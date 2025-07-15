package class174;

// 魔法少女网站，java版
// 给定一个长度为n的数组arr，一共有m条操作，每条操作类型如下
// 操作 1 x v   : arr[x]的值变成v
// 操作 2 x y v : arr[x..y]范围上，查询有多少连续子数组的最大值 <= v
// 1 <= n、m <= 3 * 10^5
// 1 <= arr[i] <= n
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

	public static int MAXN = 300002;
	public static int MAXB = 601;
	public static int POW = 9;
	public static int OFFSET = (1 << POW) - 1;
	public static int n, m;

	public static int[] arr = new int[MAXN];
	public static int[] op = new int[MAXN];
	public static int[] x = new int[MAXN];
	public static int[] y = new int[MAXN];
	public static int[] v = new int[MAXN];

	// pos[1..cntp]是当前序列块的下标
	// que[1..cntq]是整包清算的查询编号
	public static int[] pos = new int[MAXN];
	public static int[] que = new int[MAXN];
	public static int cntp;
	public static int cntq;

	// 基数排序
	public static int[] cntv = new int[MAXB];
	public static int[] help = new int[MAXN];

	// 双链表
	public static int[] last = new int[MAXN];
	public static int[] next = new int[MAXN];

	// 每条查询的答案信息
	public static int[] pre = new int[MAXN];
	public static int[] suf = new int[MAXN];
	public static int[] len = new int[MAXN];
	public static long[] ans = new long[MAXN];

	// 讲解028 - 基数排序，不会的话去看课
	// idx[1..siz]都是编号，编号根据val[编号]的值排序
	// val[编号]的高位 = val[编号] >> POW
	// val[编号]的低位 = val[编号] & OFFSET
	public static void radix(int[] idx, int[] val, int siz) {
		Arrays.fill(cntv, 0);
		for (int i = 1; i <= siz; i++) cntv[val[idx[i]] & OFFSET]++;
		for (int i = 1; i < MAXB; i++) cntv[i] += cntv[i - 1];
		for (int i = siz; i >= 1; i--) help[cntv[val[idx[i]] & OFFSET]--] = idx[i];
		for (int i = 1; i <= siz; i++) idx[i] = help[i];
		Arrays.fill(cntv, 0);
		for (int i = 1; i <= siz; i++) cntv[val[idx[i]] >> POW]++;
		for (int i = 1; i < MAXB; i++) cntv[i] += cntv[i - 1];
		for (int i = siz; i >= 1; i--) help[cntv[val[idx[i]] >> POW]--] = idx[i];
		for (int i = 1; i <= siz; i++) idx[i] = help[i];
	}

	// 之前的答案信息 pre[i]、suf[i]、len[i]、ans[i]
	// 当前的答案信息 curPre、curSuf、curLen、curAns
	// 之前的答案合并当前的答案
	public static void mergeAns(int i, int curPre, int curSuf, int curLen, int curAns) {
		ans[i] += 1L * suf[i] * curPre + curAns;
		pre[i] = pre[i] + (pre[i] == len[i] ? curPre : 0);
		suf[i] = curSuf + (curSuf == curLen ? suf[i] : 0);
		len[i] += curLen;
	}

	// 整包结算
	// que[1..cntq]是查询编号，每条查询整包[l..r]
	// 根据序列块的数字状况，更新每个查询的答案信息
	public static void calc(int l, int r) {
		for (int i = l; i <= r; i++) {
			pos[++cntp] = i;
			last[i] = i - 1;
			next[i] = i + 1;
		}
		radix(pos, arr, cntp);
		radix(que, v, cntq);
		int curPre = 0, curSuf = 0, curLen = r - l + 1, curAns = 0;
		for (int i = 1, j = 1, idx; i <= cntq; i++) {
			while (j <= cntp && arr[pos[j]] <= v[que[i]]) {
				idx = pos[j];
				if (last[idx] == l - 1) {
					curPre += next[idx] - idx;
				}
				if (next[idx] == r + 1) {
					curSuf += idx - last[idx];
				}
				curAns += 1L * (idx - last[idx]) * (next[idx] - idx);
				last[next[idx]] = last[idx];
				next[last[idx]] = next[idx];
				j++;
			}
			mergeAns(que[i], curPre, curSuf, curLen, curAns);
		}
		cntp = cntq = 0;
	}

	public static void compute(int l, int r) {
		for (int qi = 1; qi <= m; qi++) {
			if (op[qi] == 1) {
				if (l <= x[qi] && x[qi] <= r) {
					calc(l, r);
					arr[x[qi]] = v[qi];
				}
			} else {
				if (x[qi] <= l && r <= y[qi]) {
					que[++cntq] = qi;
				} else {
					for (int i = Math.max(x[qi], l); i <= Math.min(y[qi], r); i++) {
						if (arr[i] <= v[qi]) {
							mergeAns(qi, 1, 1, 1, 1);
						} else {
							mergeAns(qi, 0, 0, 1, 0);
						}
					}
				}
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
			if (op[i] == 1) {
				v[i] = in.nextInt();
			} else {
				y[i] = in.nextInt();
				v[i] = in.nextInt();
			}
		}
		int blen = 1 << POW;
		int bnum = (n + blen - 1) / blen;
		for (int i = 1, l, r; i <= bnum; i++) {
			l = (i - 1) * blen + 1;
			r = Math.min(i * blen, n);
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