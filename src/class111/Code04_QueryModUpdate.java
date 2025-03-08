package class111;

// 包含取模操作的线段树
// 给定一个长度为n的数组arr，实现如下三种操作，一共调用m次
// 操作 1 l r : 查询arr[l..r]的累加和
// 操作 2 l r x : 把arr[l..r]上每个数字对x取模
// 操作 3 k x : 把arr[k]上的数字设置为x
// 1 <= n, m <= 10^5，操作1得到的结果，有可能超过int范围
// 测试链接 : https://www.luogu.com.cn/problem/CF438D
// 测试链接 : https://codeforces.com/problemset/problem/438/D
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_QueryModUpdate {

	public static int MAXN = 100001;

	public static int[] arr = new int[MAXN];

	public static long[] sum = new long[MAXN << 2];

	public static int[] max = new int[MAXN << 2];

	public static void up(int i) {
		sum[i] = sum[i << 1] + sum[i << 1 | 1];
		max[i] = Math.max(max[i << 1], max[i << 1 | 1]);
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = max[i] = arr[l];
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
	}

	public static long query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		int mid = (l + r) >> 1;
		long ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, i << 1);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
		}
		return ans;
	}

	public static void mod(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobv > max[i]) {
			return;
		}
		if (l == r) {
			sum[i] %= jobv;
			max[i] %= jobv;
		} else {
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				mod(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				mod(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static void update(int jobi, int jobv, int l, int r, int i) {
		if (l == r) {
			sum[i] = max[i] = jobv;
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				update(jobi, jobv, l, mid, i << 1);
			} else {
				update(jobi, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		build(1, n, 1);
		for (int i = 1, op; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			if (op == 1) {
				in.nextToken();
				int jobl = (int) in.nval;
				in.nextToken();
				int jobr = (int) in.nval;
				out.println(query(jobl, jobr, 1, n, 1));
			} else if (op == 2) {
				in.nextToken();
				int jobl = (int) in.nval;
				in.nextToken();
				int jobr = (int) in.nval;
				in.nextToken();
				int jobv = (int) in.nval;
				mod(jobl, jobr, jobv, 1, n, 1);
			} else {
				in.nextToken();
				int jobi = (int) in.nval;
				in.nextToken();
				int jobv = (int) in.nval;
				update(jobi, jobv, 1, n, 1);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}