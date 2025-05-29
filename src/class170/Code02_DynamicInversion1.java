package class170;

// 动态逆序对，java版
// 给定一个长度为n的排列，1~n所有数字都出现一次
// 如果，前面的数 > 后面的数，那么这两个数就组成一个逆序对
// 给定一个长度为m的数组，表示依次删除的数字
// 打印每次删除数字前，排列中一共有多少逆序对，一共m条打印
// 1 <= n <= 10^5
// 1 <= m <= 5 * 10^4
// 测试链接 : https://www.luogu.com.cn/problem/P3157
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code02_DynamicInversion1 {

	public static int MAXN = 100001;
	public static int MAXM = 50001;
	public static int n, m;

	public static int[] num = new int[MAXN];
	public static int[] pos = new int[MAXN];
	public static int[] del = new int[MAXM];

	// 位置i、数值v、效果d、问题q
	public static int[][] arr = new int[MAXN + MAXM][4];
	public static int cnt = 0;

	public static int[] tree = new int[MAXN];

	public static long[] ans = new long[MAXM];

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		while (i <= n) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static int query(int i) {
		int ret = 0;
		while (i > 0) {
			ret += tree[i];
			i -= lowbit(i);
		}
		return ret;
	}

	public static void merge(int l, int m, int r) {
		int p1, p2;
		for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
			while (p1 + 1 <= m && arr[p1 + 1][0] <= arr[p2][0]) {
				p1++;
				add(arr[p1][1], arr[p1][2]);
			}
			ans[arr[p2][3]] += arr[p2][2] * (query(n) - query(arr[p2][1]));
		}
		for (int i = l; i <= p1; i++) {
			add(arr[i][1], -arr[i][2]);
		}
		for (p1 = m + 1, p2 = r; p2 > m; p2--) {
			while (p1 - 1 >= l && arr[p1 - 1][0] >= arr[p2][0]) {
				p1--;
				add(arr[p1][1], arr[p1][2]);
			}
			ans[arr[p2][3]] += arr[p2][2] * query(arr[p2][1] - 1);
		}
		for (int i = m; i >= p1; i--) {
			add(arr[i][1], -arr[i][2]);
		}
		Arrays.sort(arr, l, r + 1, (a, b) -> a[0] - b[0]);
	}

	public static void cdq(int l, int r) {
		if (l == r) {
			return;
		}
		int mid = (l + r) / 2;
		cdq(l, mid);
		cdq(mid + 1, r);
		merge(l, mid, r);
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			arr[++cnt][0] = i;
			arr[cnt][1] = num[i];
			arr[cnt][2] = 1;
			arr[cnt][3] = 0;
		}
		for (int i = 1; i <= m; i++) {
			arr[++cnt][0] = pos[del[i]];
			arr[cnt][1] = del[i];
			arr[cnt][2] = -1;
			arr[cnt][3] = i;
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			num[i] = in.nextInt();
			pos[num[i]] = i;
		}
		for (int i = 1; i <= m; i++) {
			del[i] = in.nextInt();
		}
		prepare();
		cdq(1, cnt);
		for (int i = 1; i < m; i++) {
			ans[i] += ans[i - 1];
		}
		for (int i = 0; i < m; i++) {
			out.println(ans[i]);
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
