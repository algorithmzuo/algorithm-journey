package class172;

// 蒲公英，java版
// 给定一个长度为n的数组arr，接下来有m条操作，每条操作格式如下
// 操作 l r : 打印arr[l..r]范围上的众数，如果有多个众数，打印值最小的
// 1 <= n <= 4 * 10^4
// 1 <= m <= 5 * 10^4
// 1 <= 数组中的值 <= 10^9
// 题目要求强制在线，具体规则可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P4168
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code03_Violet1 {

	public static int MAXN = 40001;
	public static int MAXB = 201;
	public static int n, m, s;
	public static int[] arr = new int[MAXN];

	// 数字做离散化
	public static int[] sortv = new int[MAXN];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];

	// freq[i][j]表示前i块中j出现的次数
	public static int[][] freq = new int[MAXB][MAXN];
	// mode[i][j]表示从i块到j块中的众数(最小)
	public static int[][] mode = new int[MAXB][MAXB];
	// 数字的词频统计
	public static int[] numCnt = new int[MAXN];

	public static int lower(int num) {
		int l = 1, r = s, m, ans = 0;
		while (l <= r) {
			m = (l + r) >> 1;
			if (sortv[m] >= num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static int getCnt(int l, int r, int v) {
		return freq[r][v] - freq[l - 1][v];
	}

	public static void prepare() {
		// 建块
		blen = (int) Math.sqrt(n);
		bnum = (n + blen - 1) / blen;
		for (int i = 1; i <= n; i++) {
			bi[i] = (i - 1) / blen + 1;
		}
		for (int i = 1; i <= bnum; i++) {
			bl[i] = (i - 1) * blen + 1;
			br[i] = Math.min(i * blen, n);
		}
		// 离散化
		for (int i = 1; i <= n; i++) {
			sortv[i] = arr[i];
		}
		Arrays.sort(sortv, 1, n + 1);
		s = 1;
		for (int i = 2; i <= n; i++) {
			if (sortv[s] != sortv[i]) {
				sortv[++s] = sortv[i];
			}
		}
		for (int i = 1; i <= n; i++) {
			arr[i] = lower(arr[i]);
		}
		// 填好freq
		for (int i = 1; i <= bnum; i++) {
			for (int j = bl[i]; j <= br[i]; j++) {
				freq[i][arr[j]]++;
			}
			for (int j = 1; j <= s; j++) {
				freq[i][j] += freq[i - 1][j];
			}
		}
		// 填好mode
		for (int i = 1; i <= bnum; i++) {
			for (int j = i; j <= bnum; j++) {
				int most = mode[i][j - 1];
				int mostCnt = getCnt(i, j, most);
				for (int k = bl[j]; k <= br[j]; k++) {
					int cur = arr[k];
					int curCnt = getCnt(i, j, cur);
					if (curCnt > mostCnt || (curCnt == mostCnt && cur < most)) {
						most = cur;
						mostCnt = curCnt;
					}
				}
				mode[i][j] = most;
			}
		}
	}

	public static int query(int l, int r) {
		int most = 0;
		if (bi[l] == bi[r]) {
			for (int i = l; i <= r; i++) {
				numCnt[arr[i]]++;
			}
			for (int i = l; i <= r; i++) {
				if (numCnt[arr[i]] > numCnt[most] || (numCnt[arr[i]] == numCnt[most] && arr[i] < most)) {
					most = arr[i];
				}
			}
			for (int i = l; i <= r; i++) {
				numCnt[arr[i]] = 0;
			}
		} else {
			for (int i = l; i <= br[bi[l]]; i++) {
				numCnt[arr[i]]++;
			}
			for (int i = bl[bi[r]]; i <= r; i++) {
				numCnt[arr[i]]++;
			}
			most = mode[bi[l] + 1][bi[r] - 1];
			int mostCnt = getCnt(bi[l] + 1, bi[r] - 1, most) + numCnt[most];
			for (int i = l; i <= br[bi[l]]; i++) {
				int cur = arr[i];
				int curCnt = getCnt(bi[l] + 1, bi[r] - 1, cur) + numCnt[cur];
				if (curCnt > mostCnt || (curCnt == mostCnt && cur < most)) {
					most = cur;
					mostCnt = curCnt;
				}
			}
			for (int i = bl[bi[r]]; i <= r; i++) {
				int cur = arr[i];
				int curCnt = getCnt(bi[l] + 1, bi[r] - 1, cur) + numCnt[cur];
				if (curCnt > mostCnt || (curCnt == mostCnt && cur < most)) {
					most = cur;
					mostCnt = curCnt;
				}
			}
			for (int i = l; i <= br[bi[l]]; i++) {
				numCnt[arr[i]] = 0;
			}
			for (int i = bl[bi[r]]; i <= r; i++) {
				numCnt[arr[i]] = 0;
			}
		}
		return sortv[most];
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		prepare();
		for (int i = 1, a, b, l, r, lastAns = 0; i <= m; i++) {
			a = (in.nextInt() + lastAns - 1) % n + 1;
			b = (in.nextInt() + lastAns - 1) % n + 1;
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
