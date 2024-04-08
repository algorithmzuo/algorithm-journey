package class111;

// 范围上开平方并求累加和
// 给定一个长度为n的数组arr，实现以下两种类型的操作
// 操作 0 l r : 把arr[l..r]范围上的每个数开平方，结果向下取整
// 操作 1 l r : 查询arr[l..r]范围上所有数字的累加和
// 两种操作一共发生m次，数据中有可能l>r，遇到这种情况请交换l和r
// 1 <= n, m <= 10^5，1 <= arr[i] <= 10^12
// 测试链接 : https://www.luogu.com.cn/problem/P4145
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_SquareRoot {

	public static int MAXN = 100001;

	public static long[] arr = new long[MAXN];

	public static long[] sum = new long[MAXN << 2];

	public static long[] max = new long[MAXN << 2];

	public static void up(int i) {
		sum[i] = sum[i << 1] + sum[i << 1 | 1];
		max[i] = Math.max(max[i << 1], max[i << 1 | 1]);
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = arr[l];
			max[i] = arr[l];
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
	}

	// sqrt方法是最核心的
	// 注意和常规线段树不一样，这里没有懒更新，也就不需要有down方法
	// 只有根据范围最大值信息的剪枝
	// 时间复杂度的分析就是课上讲的势能分析
	// 不用纠结单次调用的复杂度
	// 哪怕调用再多次sqrt方法，总的时间复杂度也就是O(n * 6 * logn)
	public static void sqrt(int jobl, int jobr, int l, int r, int i) {
		if (l == r) {
			long sqrt = (long) Math.sqrt(max[i]);
			sum[i] = sqrt;
			max[i] = sqrt;
		} else {
			int mid = (l + r) >> 1;
			if (jobl <= mid && max[i << 1] > 1) {
				sqrt(jobl, jobr, l, mid, i << 1);
			}
			if (jobr > mid && max[i << 1 | 1] > 1) {
				sqrt(jobl, jobr, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	// 没有懒更新
	// 不需要调用down方法
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

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (long) in.nval;
		}
		build(1, n, 1);
		in.nextToken();
		int m = (int) in.nval;
		for (int i = 1, op, jobl, jobr, tmp; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			jobl = (int) in.nval;
			in.nextToken();
			jobr = (int) in.nval;
			if (jobl > jobr) {
				tmp = jobl;
				jobl = jobr;
				jobr = tmp;
			}
			if (op == 0) {
				sqrt(jobl, jobr, 1, n, 1);
			} else {
				out.println(query(jobl, jobr, 1, n, 1));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
