package class157;

// 标记永久化，范围增加 + 查询累加和，java版
// 给定一个长度为n的数组arr，下标1~n，一共有m条操作，操作类型如下
// 1 x y k : 将区间[x, y]每个数加上k
// 2 x y   : 打印区间[x, y]的累加和
// 这就是普通线段树，请用标记永久化的方式实现
// 测试链接 : https://www.luogu.com.cn/problem/P3372
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_TagPermanentization1 {

	public static int MAXN = 100001;

	public static long[] arr = new long[MAXN];

	// 不是真实累加和，而是之前的任务中
	// 不考虑被上方范围截住的任务，只考虑来到当前范围 或者 往下走的任务
	// 累加和变成了什么
	public static long[] sum = new long[MAXN << 2];

	// 不再是懒更新信息，变成标记信息
	public static long[] addTag = new long[MAXN << 2];

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = arr[l];
		} else {
			int mid = (l + r) / 2;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			sum[i] = sum[i << 1] + sum[i << 1 | 1];
		}
		addTag[i] = 0;
	}

	public static void add(int jobl, int jobr, long jobv, int l, int r, int i) {
		int a = Math.max(jobl, l), b = Math.min(jobr, r);
		sum[i] += jobv * (b - a + 1);
		if (jobl <= l && r <= jobr) {
			addTag[i] += jobv;
		} else {
			int mid = (l + r) / 2;
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
		}
	}

	public static long query(int jobl, int jobr, long addHistory, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i] + addHistory * (r - l + 1);
		}
		int mid = (l + r) >> 1;
		long ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, addHistory + addTag[i], l, mid, i << 1);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, addHistory + addTag[i], mid + 1, r, i << 1 | 1);
		}
		return ans;
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
			arr[i] = (long) in.nval;
		}
		build(1, n, 1);
		int op, jobl, jobr;
		long jobv;
		for (int i = 1; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			if (op == 1) {
				in.nextToken();
				jobl = (int) in.nval;
				in.nextToken();
				jobr = (int) in.nval;
				in.nextToken();
				jobv = (long) in.nval;
				add(jobl, jobr, jobv, 1, n, 1);
			} else {
				in.nextToken();
				jobl = (int) in.nval;
				in.nextToken();
				jobr = (int) in.nval;
				out.println(query(jobl, jobr, 0, 1, n, 1));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}