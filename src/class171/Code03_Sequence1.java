package class171;

// 序列，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4093
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code03_Sequence1 {

	public static int MAXN = 100001;
	public static int n, m;

	// 位置i、数值v、最小值minv、最大值maxv
	public static int[][] arr = new int[MAXN][4];
	public static int[] tree = new int[MAXN];
	public static int[] dp = new int[MAXN];

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void more(int i, int num) {
		while (i <= n) {
			tree[i] = Math.max(tree[i], num);
			i += lowbit(i);
		}
	}

	public static int query(int i) {
		int ret = 0;
		while (i > 0) {
			ret = Math.max(ret, tree[i]);
			i -= lowbit(i);
		}
		return ret;
	}

	public static void clear(int i) {
		while (i <= n) {
			tree[i] = 0;
			i += lowbit(i);
		}
	}

	public static void merge(int l, int m, int r) {
		Arrays.sort(arr, l, m + 1, (a, b) -> a[1] - b[1]);
		Arrays.sort(arr, m + 1, r + 1, (a, b) -> a[2] - b[2]);
		int p1, p2;
		for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
			while (p1 + 1 <= m && arr[p1 + 1][1] <= arr[p2][2]) {
				p1++;
				more(arr[p1][3], dp[arr[p1][0]]);
			}
			dp[arr[p2][0]] = Math.max(dp[arr[p2][0]], query(arr[p2][1]) + 1);
		}
		for (int i = l; i <= p1; i++) {
			clear(arr[i][3]);
		}
		Arrays.sort(arr, l, r + 1, (a, b) -> a[0] - b[0]);
	}

	public static void cdq(int l, int r) {
		if (l == r) {
			return;
		}
		int mid = (l + r) / 2;
		// 为什么不是经典顺序，左、右、再整合？
		// 因为右侧dp的计算依赖左侧dp的结果
		// 需要先处理左侧范围，得到部分状态转移的可能性
		// 然后再进行左右合并，把左边dp的影响传递到右边
		// 最后再处理右侧范围，才能彻底把右侧dp计算正确
		cdq(l, mid);
		merge(l, mid, r);
		cdq(mid + 1, r);
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i][0] = i;
			arr[i][1] = in.nextInt();
			arr[i][2] = arr[i][1];
			arr[i][3] = arr[i][1];
			dp[i] = 1;
		}
		for (int i = 1, idx, num; i <= m; i++) {
			idx = in.nextInt();
			num = in.nextInt();
			arr[idx][2] = Math.min(arr[idx][2], num);
			arr[idx][3] = Math.max(arr[idx][3], num);
		}
		cdq(1, n);
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			ans = Math.max(ans, dp[i]);
		}
		out.println(ans);
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
