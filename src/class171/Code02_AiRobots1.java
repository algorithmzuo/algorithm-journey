package class171;

// 机器人聊天对，java版
// 一共有n个机器人，每个机器人给定，位置x、视野y、智商q
// 第i个机器人可以看见的范围是[xi − yi, xi + yi]
// 如果两个机器人相互之间可以看见，并且智商差距不大于k，那么它们会开始聊天
// 打印有多少对机器人可以聊天
// 1 <= n <= 10^5
// 0 <= k <= 20
// 0 <= x、y、q <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/CF1045G
// 测试链接 : https://codeforces.com/problemset/problem/1045/G
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code02_AiRobots1 {

	public static int MAXN = 100001;
	public static int n, k, s;

	// 位置x、视野y、智商q、左边界l、右边界r
	public static int[][] arr = new int[MAXN][5];
	// 所有x坐标组成的数组
	public static int[] x = new int[MAXN];

	public static int[] tree = new int[MAXN];

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		while (i <= s) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static int sum(int i) {
		int ret = 0;
		while (i > 0) {
			ret += tree[i];
			i -= lowbit(i);
		}
		return ret;
	}

	public static int query(int l, int r) {
		return sum(r) - sum(l - 1);
	}

	public static long merge(int l, int m, int r) {
		int winl = l, winr = l - 1;
		long ans = 0;
		for (int i = m + 1; i <= r; i++) {
			while (winl <= m && arr[winl][2] < arr[i][2] - k) {
				add(arr[winl][0], -1);
				winl++;
			}
			while (winr + 1 <= m && arr[winr + 1][2] <= arr[i][2] + k) {
				winr++;
				add(arr[winr][0], 1);
			}
			ans += query(arr[i][3], arr[i][4]);
		}
		for (int i = winl; i <= winr; i++) {
			add(arr[i][0], -1);
		}
		Arrays.sort(arr, l, r + 1, (a, b) -> a[2] - b[2]);
		return ans;
	}

	public static long cdq(int l, int r) {
		if (l == r) {
			return 0;
		}
		int mid = (l + r) / 2;
		return cdq(l, mid) + cdq(mid + 1, r) + merge(l, mid, r);
	}

	public static int lower(int num) {
		int l = 1, r = s, m, ans = 1;
		while (l <= r) {
			m = (l + r) / 2;
			if (x[m] >= num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static int upper(int num) {
		int l = 1, r = s, m, ans = s + 1;
		while (l <= r) {
			m = (l + r) / 2;
			if (x[m] > num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			x[i] = arr[i][0];
		}
		Arrays.sort(x, 1, n + 1);
		s = 1;
		for (int i = 2; i <= n; i++) {
			if (x[s] != x[i]) {
				x[++s] = x[i];
			}
		}
		for (int i = 1; i <= n; i++) {
			arr[i][3] = lower(arr[i][0] - arr[i][1]);
			arr[i][4] = upper(arr[i][0] + arr[i][1]) - 1;
			arr[i][0] = lower(arr[i][0]);
		}
		Arrays.sort(arr, 1, n + 1, (a, b) -> b[1] - a[1]);
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		k = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i][0] = in.nextInt();
			arr[i][1] = in.nextInt();
			arr[i][2] = in.nextInt();
		}
		prepare();
		out.println(cdq(1, n));
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
