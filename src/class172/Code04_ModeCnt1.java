package class172;

// 空间少求区间众数的次数，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5048
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是内存占用过大，无法通过测试用例
// 因为这道题只考虑C++能通过的空间标准，根本没考虑java的用户
// 想通过用C++实现，本节课Code04_ModeCnt2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code04_ModeCnt1 {

	public static int MAXN = 500001;
	public static int MAXB = 801;
	public static int n, m, s;
	public static int[] arr = new int[MAXN];
	public static int[] sortv = new int[MAXN];

	public static int blen, bnum;
	public static int[] bi = new int[MAXN];
	public static int[] bl = new int[MAXB];
	public static int[] br = new int[MAXB];

	// 值、下标
	public static int[][] bucket = new int[MAXN][2];
	// bucketIdx[i] = j，代表数组的i位置元素在bucket中的j位置
	public static int[] bucketIdx = new int[MAXN];

	// modeCnt[i][j]表示从i块到j块中众数的出现次数
	public static int[][] modeCnt = new int[MAXB][MAXB];
	// 数字词频统计
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
		for (int i = 1; i <= n; i++) {
			bucket[i][0] = arr[i];
			bucket[i][1] = i;
		}
		Arrays.sort(bucket, 1, n + 1, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
		for (int i = 1; i <= n; i++) {
			bucketIdx[bucket[i][1]] = i;
		}
		for (int i = 1; i <= bnum; i++) {
			for (int j = i; j <= bnum; j++) {
				int cnt = modeCnt[i][j - 1];
				for (int k = bl[j]; k <= br[j]; k++) {
					cnt = Math.max(cnt, ++numCnt[arr[k]]);
				}
				modeCnt[i][j] = cnt;
			}
			for (int j = 1; j <= n; j++) {
				numCnt[j] = 0;
			}
		}
	}

	public static int query(int l, int r) {
		int ans = 0;
		if (bi[l] == bi[r]) {
			for (int i = l; i <= r; i++) {
				ans = Math.max(ans, ++numCnt[arr[i]]);
			}
			for (int i = l; i <= r; i++) {
				numCnt[arr[i]] = 0;
			}
		} else {
			ans = modeCnt[bi[l] + 1][bi[r] - 1];
			for (int i = l, idx; i <= br[bi[l]]; i++) {
				idx = bucketIdx[i];
				while (idx + ans <= n && bucket[idx + ans][0] == arr[i] && bucket[idx + ans][1] <= r) {
					ans++;
				}
			}
			for (int i = bl[bi[r]], idx; i <= r; i++) {
				idx = bucketIdx[i];
				while (idx - ans >= 1 && bucket[idx - ans][0] == arr[i] && bucket[idx - ans][1] >= l) {
					ans++;
				}
			}
		}
		return ans;
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
		for (int i = 1, l, r, lastAns = 0; i <= m; i++) {
			l = in.nextInt();
			r = in.nextInt();
			l ^= lastAns;
			r ^= lastAns;
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
