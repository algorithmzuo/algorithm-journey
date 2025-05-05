package class167;

// 打印所有合法数，java版
// 一个长度为n的序列，一开始所有值都是0
// 一共有q条操作，每条操作为 l r k : 序列[l..r]范围上，每个数字加k
// 你可以随意选择操作来执行，但是每条操作只能执行一次
// 如果你能让序列中的最大值正好为v，那么v就算一个合法数
// 打印1~n范围内有多少合法数，并且从小到大打印所有的合法数
// 1 <= k <= n、q <= 10^4
// 测试链接 : https://www.luogu.com.cn/problem/CF981E
// 测试链接 : https://codeforces.com/problemset/problem/981/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_AdditionOnSegments1 {

	public static int MAXN = 10001;
	public static int MAXT = 500001;
	public static int BIT = 10000;
	public static int DEEP = 20;
	public static int INT_BIT = 32;
	public static int LEN = BIT / INT_BIT + 1;
	public static int n, q;

	public static int[] head = new int[MAXN << 2];
	public static int[] next = new int[MAXT];
	public static int[] to = new int[MAXT];
	public static int cnt = 0;

	public static int[] tmp = new int[LEN];
	public static int[] dp = new int[LEN];
	public static int[][] backup = new int[DEEP][LEN];
	public static int[] ans = new int[LEN];

	// 清空
	public static void clear(int[] bitset) {
		for (int i = 0; i < LEN; i++) {
			bitset[i] = 0;
		}
	}

	// 位图set1 拷贝 位图set2
	public static void clone(int[] set1, int[] set2) {
		for (int i = 0; i < LEN; i++) {
			set1[i] = set2[i];
		}
	}

	// 返回位图第i位的状态
	public static int getBit(int[] bitset, int i) {
		return (bitset[i / INT_BIT] >> (i % INT_BIT)) & 1;
	}

	// 第i位的状态设置成v
	public static void setBit(int[] bitset, int i, int v) {
		if (v == 0) {
			bitset[i / INT_BIT] &= ~(1 << (i % INT_BIT));
		} else {
			bitset[i / INT_BIT] |= 1 << (i % INT_BIT);
		}
	}

	// 位图set1 或 位图set2
	public static void bitOr(int[] set1, int[] set2) {
		for (int i = 0; i < LEN; i++) {
			set1[i] |= set2[i];
		}
	}

	// 位图ret 变成 位图bitset左移move位的状态
	// 不使用一位一位拷贝的方式，因为太慢了
	// 采用整块左移的方式，这样比较快
	public static void bitLeft(int[] ret, int[] bitset, int move) {
		clear(ret);
		if (move > BIT) {
			return;
		}
		if (move <= 0) {
			clone(ret, bitset);
			return;
		}
		int shift = move / INT_BIT;
		int offset = move % INT_BIT;
		if (offset == 0) {
			for (int i = LEN - 1, j = i - shift; j >= 0; i--, j--) {
				ret[i] = bitset[j];
			}
		} else {
			int carry = INT_BIT - offset, high, low;
			for (int i = LEN - 1; i > shift; i--) {
				high = bitset[i - shift] << offset;
				low = bitset[i - shift - 1] >>> carry;
				ret[i] = high | low;
			}
			ret[shift] = bitset[0] << offset;
		}
		// 最高位BIT到最低位0，一共BIT+1个有效位
		// 其他更高位信息需要清空，rest就是有多少无效的更高位
		int rest = LEN * INT_BIT - (BIT + 1);
		if (rest > 0) {
			ret[LEN - 1] &= (1 << (INT_BIT - rest)) - 1;
		}
	}

	public static void addEdge(int i, int v) {
		next[++cnt] = head[i];
		to[cnt] = v;
		head[i] = cnt;
	}

	public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			addEdge(i, jobv);
		} else {
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
		}
	}

	public static void dfs(int l, int r, int i, int dep) {
		clone(backup[dep], dp);
		for (int e = head[i]; e > 0; e = next[e]) {
			bitLeft(tmp, dp, to[e]);
			bitOr(dp, tmp);
		}
		if (l == r) {
			bitOr(ans, dp);
		} else {
			int mid = (l + r) >> 1;
			dfs(l, mid, i << 1, dep + 1);
			dfs(mid + 1, r, i << 1 | 1, dep + 1);
		}
		clone(dp, backup[dep]);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		q = in.nextInt();
		for (int i = 1, l, r, k; i <= q; i++) {
			l = in.nextInt();
			r = in.nextInt();
			k = in.nextInt();
			add(l, r, k, 1, n, 1);
		}
		setBit(dp, 0, 1);
		dfs(1, n, 1, 1);
		int ansCnt = 0;
		for (int i = 1; i <= n; i++) {
			if (getBit(ans, i) == 1) {
				ansCnt++;
			}
		}
		out.println(ansCnt);
		for (int i = 1; i <= n; i++) {
			if (getBit(ans, i) == 1) {
				out.print(i + " ");
			}
		}
		out.println();
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
