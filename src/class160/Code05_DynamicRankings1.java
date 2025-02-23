package class160;

// 动态排名，java版
// 给定一个长度为n的数组arr，下标1~n，每条操作都是如下2种类型中的一种，一共进行m次操作
// 操作 Q x y z : 查询arr[x..y]中排第z名的数字
// 操作 C x y   : arr中x位置的数字改成y
// 1 <= n、m <= 10^5
// 数组中的值永远在[0, 10^9]范围内
// 测试链接 : https://www.luogu.com.cn/problem/P2617
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Code05_DynamicRankings1 {

	public static int MAXN = 100001;

	public static int MAXT = MAXN * 130;

	public static int n, m, s;

	public static int[] arr = new int[MAXN];

	public static int[][] ques = new int[MAXN][4];

	public static int[] sorted = new int[MAXN * 2];

	public static int[] root = new int[MAXN];

	public static int[] sum = new int[MAXT];

	public static int[] left = new int[MAXT];

	public static int[] right = new int[MAXT];

	public static int cntt = 0;

	public static int[] addTree = new int[MAXN];

	public static int[] minusTree = new int[MAXN];

	public static int cntadd;

	public static int cntminus;

	public static int kth(int num) {
		int left = 1, right = s, mid;
		while (left <= right) {
			mid = (left + right) / 2;
			if (sorted[mid] == num) {
				return mid;
			} else if (sorted[mid] < num) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return -1;
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static int innerAdd(int jobi, int jobv, int l, int r, int i) {
		if (i == 0) {
			i = ++cntt;
		}
		if (l == r) {
			sum[i] += jobv;
		} else {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				left[i] = innerAdd(jobi, jobv, l, mid, left[i]);
			} else {
				right[i] = innerAdd(jobi, jobv, mid + 1, r, right[i]);
			}
			sum[i] = sum[left[i]] + sum[right[i]];
		}
		return i;
	}

	public static int innerQuery(int jobk, int l, int r) {
		if (l == r) {
			return l;
		}
		int mid = (l + r) / 2;
		int leftsum = 0;
		for (int i = 1; i <= cntadd; i++) {
			leftsum += sum[left[addTree[i]]];
		}
		for (int i = 1; i <= cntminus; i++) {
			leftsum -= sum[left[minusTree[i]]];
		}
		if (jobk <= leftsum) {
			for (int i = 1; i <= cntadd; i++) {
				addTree[i] = left[addTree[i]];
			}
			for (int i = 1; i <= cntminus; i++) {
				minusTree[i] = left[minusTree[i]];
			}
			return innerQuery(jobk, l, mid);
		} else {
			for (int i = 1; i <= cntadd; i++) {
				addTree[i] = right[addTree[i]];
			}
			for (int i = 1; i <= cntminus; i++) {
				minusTree[i] = right[minusTree[i]];
			}
			return innerQuery(jobk - leftsum, mid + 1, r);
		}
	}

	public static void add(int i, int cnt) {
		for (int j = i; j <= n; j += lowbit(j)) {
			root[j] = innerAdd(arr[i], cnt, 1, s, root[j]);
		}
	}

	public static void update(int i, int v) {
		add(i, -1);
		arr[i] = kth(v);
		add(i, 1);
	}

	public static int number(int l, int r, int k) {
		cntadd = cntminus = 0;
		for (int i = r; i > 0; i -= lowbit(i)) {
			addTree[++cntadd] = root[i];
		}
		for (int i = l - 1; i > 0; i -= lowbit(i)) {
			minusTree[++cntminus] = root[i];
		}
		return sorted[innerQuery(k, 1, s)];
	}

	public static void prepare() {
		s = 0;
		for (int i = 1; i <= n; i++) {
			sorted[++s] = arr[i];
		}
		for (int i = 1; i <= m; i++) {
			if (ques[i][0] == 2) {
				sorted[++s] = ques[i][2];
			}
		}
		Arrays.sort(sorted, 1, s + 1);
		int len = 1;
		for (int i = 2; i <= s; i++) {
			if (sorted[len] != sorted[i]) {
				sorted[++len] = sorted[i];
			}
		}
		s = len;
		for (int i = 1; i <= n; i++) {
			arr[i] = kth(arr[i]);
			add(i, 1);
		}
	}

	public static void main(String[] args) {
		Kattio io = new Kattio();
		n = io.nextInt();
		m = io.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = io.nextInt();
		}
		for (int i = 1; i <= m; i++) {
			ques[i][0] = io.next().equals("Q") ? 1 : 2;
			ques[i][1] = io.nextInt();
			ques[i][2] = io.nextInt();
			if (ques[i][0] == 1) {
				ques[i][3] = io.nextInt();
			}
		}
		prepare();
		for (int i = 1, op, x, y, z; i <= m; i++) {
			op = ques[i][0];
			x = ques[i][1];
			y = ques[i][2];
			if (op == 1) {
				z = ques[i][3];
				io.println(number(x, y, z));
			} else {
				update(x, y);
			}
		}
		io.flush();
		io.close();
	}

	// 读写工具类
	public static class Kattio extends PrintWriter {
		private BufferedReader r;
		private StringTokenizer st;

		public Kattio() {
			this(System.in, System.out);
		}

		public Kattio(InputStream i, OutputStream o) {
			super(o);
			r = new BufferedReader(new InputStreamReader(i));
		}

		public Kattio(String intput, String output) throws IOException {
			super(output);
			r = new BufferedReader(new FileReader(intput));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreTokens())
					st = new StringTokenizer(r.readLine());
				return st.nextToken();
			} catch (Exception e) {
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}