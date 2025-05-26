package class170;

// 三维偏序，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3810
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code01_3DPartialOrder1 {

	public static int MAXN = 200001;
	public static int n, k;

	// 位置i、属性a、属性b、属性c
	public static int[][] arr = new int[MAXN][4];
	// 归并排序辅助数组
	public static int[][] help = new int[MAXN][4];
	// 树状数组
	public static int[] tree = new int[MAXN];
	// 每个位置统计f(i)
	public static int[] f = new int[MAXN];
	// 答案
	public static int[] ans = new int[MAXN];

	public static void clone(int[] a, int[] b) {
		a[0] = b[0];
		a[1] = b[1];
		a[2] = b[2];
		a[3] = b[3];
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		while (i <= k) {
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
		// 利用左右各自有序，进行不回退的统计
		int p1, p2;
		for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
			while (p1 + 1 <= m && arr[p1 + 1][2] <= arr[p2][2]) {
				p1++;
				add(arr[p1][3], 1);
			}
			f[arr[p2][0]] += query(arr[p2][3]);
		}
		// 清空树状数组
		for (int i = l; i <= p1; i++) {
			add(arr[i][3], -1);
		}
		p1 = l;
		p2 = m + 1;
		int i = l;
		while (p1 <= m && p2 <= r) {
			clone(help[i++], arr[p1][2] <= arr[p2][2] ? arr[p1++] : arr[p2++]);
		}
		while (p1 <= m) {
			clone(help[i++], arr[p1++]);
		}
		while (p2 <= r) {
			clone(help[i++], arr[p2++]);
		}
		for (i = l; i <= r; i++) {
			clone(arr[i], help[i]);
		}
	}

	public static void compute(int l, int r) {
		if (l == r) {
			return;
		}
		int mid = (l + r) / 2;
		compute(l, mid);
		compute(mid + 1, r);
		merge(l, mid, r);
	}

	public static void prepare() {
		// 根据a排序，a一样根据b排序，b一样根据c排序
		Arrays.sort(arr, 1, n + 1, (a, b) -> a[1] != b[1] ? a[1] - b[1] : a[2] != b[2] ? a[2] - b[2] : a[3] - b[3]);
		// 排序后，a、b、c都一样的同一组内，组前的下标，得不到同组后面的统计量
		// 所以先设置f的初始值，把这部分补偿给组前的下标
		for (int l = 1, r = 1; l <= n; l = ++r) {
			while (r + 1 <= n && arr[l][1] == arr[r + 1][1] && arr[l][2] == arr[r + 1][2]
					&& arr[l][3] == arr[r + 1][3]) {
				r++;
			}
			for (int i = l; i <= r; i++) {
				f[arr[i][0]] = r - i;
			}
		}
		// 清空答案数组
		Arrays.fill(ans, 0, n, 0);
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		k = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i][0] = i;
			arr[i][1] = in.nextInt();
			arr[i][2] = in.nextInt();
			arr[i][3] = in.nextInt();
		}
		prepare();
		compute(1, n);
		for (int i = 1; i <= n; i++) {
			ans[f[i]]++;
		}
		for (int i = 0; i < n; i++) {
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