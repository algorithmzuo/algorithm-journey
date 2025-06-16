package class172;

// 作诗，java版
// 给定一个长度为n的数组arr，接下来有m条操作，每条操作格式如下
// 操作 l r : 打印arr[l..r]范围上，有多少个数出现正偶数次
// 1 <= 所有数值 <= 10^5
// 题目要求强制在线，强制在线的方式，可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P4135
// 提交交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Poem1 {

	public static int MAXN = 100001;
	public static int MAXB = 401;
	public static int n, c, m;
	public static int[] arr = new int[MAXN];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];

	// freq[i][j]表示前i块中j出现的次数
	public static int[][] freq = new int[MAXB][MAXN];
	// even[i][j]表示从第i块到第j块出现正偶数次的数有几个
	public static int[][] even = new int[MAXB][MAXB];
	// 数字词频统计
	public static int[] numCnt = new int[MAXN];

	// 返回从l块到r块中，数字v的次数
	public static int getCnt(int l, int r, int v) {
		return freq[r][v] - freq[l - 1][v];
	}

	// 加1之前的词频是pre，返回加1之后，正偶数次的数的个数变化量
	public static int delta(int pre) {
		return pre != 0 ? ((pre & 1) == 0 ? -1 : 1) : 0;
	}

	public static void prepare() {
		blen = (int) Math.sqrt(n);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, n);
		}
		for (int i = 1; i <= bnum; i++) {
			for (int j = bl[i]; j <= br[i]; j++) {
				freq[i][arr[j]]++;
			}
			for (int j = 1; j <= c; j++) {
				freq[i][j] += freq[i - 1][j];
			}
		}
		for (int i = 1; i <= bnum; i++) {
			for (int j = i; j <= bnum; j++) {
				even[i][j] = even[i][j - 1];
				for (int k = bl[j]; k <= br[j]; k++) {
					even[i][j] += delta(numCnt[arr[k]]);
					numCnt[arr[k]]++;
				}
			}
			for (int j = 1; j <= c; j++) {
				numCnt[j] = 0;
			}
		}
	}

	public static int query(int l, int r) {
		int ans = 0;
		if (bi[l] == bi[r]) {
			for (int i = l; i <= r; i++) {
				ans += delta(numCnt[arr[i]]);
				numCnt[arr[i]]++;
			}
			for (int i = l; i <= r; i++) {
				numCnt[arr[i]] = 0;
			}
		} else {
			ans = even[bi[l] + 1][bi[r] - 1];
			for (int i = l; i <= br[bi[l]]; i++) {
				ans += delta(getCnt(bi[l] + 1, bi[r] - 1, arr[i]) + numCnt[arr[i]]);
				numCnt[arr[i]]++;
			}
			for (int i = bl[bi[r]]; i <= r; i++) {
				ans += delta(getCnt(bi[l] + 1, bi[r] - 1, arr[i]) + numCnt[arr[i]]);
				numCnt[arr[i]]++;
			}
			for (int i = l; i <= br[bi[l]]; i++) {
				numCnt[arr[i]] = 0;
			}
			for (int i = bl[bi[r]]; i <= r; i++) {
				numCnt[arr[i]] = 0;
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		c = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		prepare();
		for (int i = 1, a, b, l, r, lastAns = 0; i <= m; i++) {
			a = (in.nextInt() + lastAns) % n + 1;
			b = (in.nextInt() + lastAns) % n + 1;
			l = Math.min(a, b);
			r = Math.max(a, b);
			lastAns = query(l, r);
			out.println(lastAns);
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
