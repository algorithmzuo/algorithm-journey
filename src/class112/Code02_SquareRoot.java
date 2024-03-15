package class112;

// 范围上开平方并求累加和
// 给定一个长度为n的数组arr，实现以下两种类型的操作
// 操作0 : arr[l..r]范围上的每个数开平方，结果向下取整
// 操作1 : 返回arr[l..r]范围上所有数字的累加和
// 两种操作一共发生m次，数据中有可能l>r，遇到这种情况请交换l和r
// 1 <= n, m <= 10^5
// 1 <= arr[i] <= 10^12
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

public class Code02_SquareRoot {

	public static int MAXN = 100001;

	public static long[] arr = new long[MAXN];

	public static long[] sum = new long[MAXN << 2];

	public static long[] max = new long[MAXN << 2];

	public static void build(int l, int r, int rt) {
		if (l == r) {
			sum[rt] = arr[l];
			max[rt] = arr[l];
		} else {
			int mid = (l + r) / 2;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
			up(rt);
		}
	}

	public static void up(int rt) {
		sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
		max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
	}

	// change方法是最核心的
	// 注意和常规线段树不一样，这里没有懒更新
	// 哪怕任务范围包括了实际范围依然下发任务
	// 效率当然比线段树差，但因为开方次数有限，所以依然能通过
	// 核心点就在于每个数字不超过10^12次方，所以开方不了几回也就结束了
	// 如果发现一段范围上的最大值>1，才需要执行开方任务，否则跳过
	// 这也是为什么不需要down函数的原因，因为没有懒更新，任务都是直接下发的
	public static void change(int jobl, int jobr, int l, int r, int rt) {
		if (l == r) {
			long sqrt = (long) Math.sqrt(max[rt]);
			sum[rt] = sqrt;
			max[rt] = sqrt;
		} else {
			int mid = (l + r) / 2;
			if (jobl <= mid && max[rt << 1] > 1) {
				change(jobl, jobr, l, mid, rt << 1);
			}
			if (jobr > mid && max[rt << 1 | 1] > 1) {
				change(jobl, jobr, mid + 1, r, rt << 1 | 1);
			}
			up(rt);
		}
	}

	// 依然不需要down方法
	// 因为之前的任务都是完全下发的
	// 根本不会有任务积攒
	// 所以不需要执行down方法
	public static long query(int jobl, int jobr, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			return sum[rt];
		}
		int mid = (l + r) / 2;
		long ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, rt << 1);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, rt << 1 | 1);
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
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
					change(jobl, jobr, 1, n, 1);
				} else {
					out.println(query(jobl, jobr, 1, n, 1));
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
