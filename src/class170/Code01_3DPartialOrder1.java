package class170;

// 三维偏序，java版
// 一共有n个对象，属性值范围[1, k]，每个对象有a属性、b属性、c属性
// f(i)表示，aj <= ai 且 bj <= bi 且 cj <= ci 且 j != i 的j的数量
// ans(d)表示，f(i) == d 的i的数量
// 打印所有的ans[d]，d的范围[0, n)
// 1 <= n <= 10^5
// 1 <= k <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3810
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code01_3DPartialOrder1 {

	public static int MAXN = 100001;
	public static int MAXK = 200001;
	public static int n, k;

	// 对象的编号i、属性a、属性b、属性c
	public static int[][] arr = new int[MAXN][4];

	// 树状数组，根据属性c的值增加词频，查询 <= 某个数的词频累加和
	public static int[] tree = new int[MAXK];

	// 每个对象的答案
	public static int[] f = new int[MAXN];

	// 题目要求的ans[d]
	public static int[] ans = new int[MAXN];

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
		// 利用左、右各自b属性有序
		// 不回退的找，当前右组对象包括了几个左组的对象
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
		// 直接根据b属性排序，无需写经典的归并过程，课上重点解释了原因
		Arrays.sort(arr, l, r + 1, (a, b) -> a[2] - b[2]);
	}

	// 大顺序已经按a属性排序，cdq分治里按b属性重新排序
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
		// 根据a排序，a一样根据b排序，b一样根据c排序
		// 排序后a、b、c一样的同组内，组前的下标得不到同组后面的统计量
		// 所以把这部分的贡献，提前补偿给组前的下标，然后再跑CDQ分治
		Arrays.sort(arr, 1, n + 1, (a, b) -> a[1] != b[1] ? a[1] - b[1] : a[2] != b[2] ? a[2] - b[2] : a[3] - b[3]);
		for (int l = 1, r = 1; l <= n; l = ++r) {
			while (r + 1 <= n && arr[l][1] == arr[r + 1][1] && arr[l][2] == arr[r + 1][2]
					&& arr[l][3] == arr[r + 1][3]) {
				r++;
			}
			for (int i = l; i <= r; i++) {
				f[arr[i][0]] = r - i;
			}
		}
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
		cdq(1, n);
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