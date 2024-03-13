package class108;

// 树状数组范围增加、范围查询模版
// 测试链接 : https://www.luogu.com.cn/problem/P3372
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_IndexTreeIntervalAddIntervalQuery {

	public static int MAXN = 100001;

	// 维护原始数组的差分信息：Di
	public static long[] info1 = new long[MAXN];

	// 维护原始数组的差分加工信息：(i-1) * Di
	public static long[] info2 = new long[MAXN];

	public static int n, m;

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(long[] tree, int i, long v) {
		while (i <= n) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static long sum(long[] tree, int i) {
		long ans = 0;
		while (i > 0) {
			ans += tree[i];
			i -= lowbit(i);
		}
		return ans;
	}

	// 原始数组中[l..r]每个数值+v
	public static void add(int l, int r, long v) {
		add(info1, l, v);
		add(info1, r + 1, -v);
		add(info2, l, (l - 1) * v);
		add(info2, r + 1, -(r * v));
	}

	// 原始数组中[l..r]范围上的累加和
	public static long range(int l, int r) {
		return sum(info1, r) * r - sum(info2, r) - sum(info1, l - 1) * (l - 1) + sum(info2, l - 1);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			long cur;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				cur = (long) in.nval;
				add(i, i, cur);
			}
			long v;
			for (int i = 1, op, l, r; i <= m; i++) {
				in.nextToken();
				op = (int) in.nval;
				if (op == 1) {
					in.nextToken();
					l = (int) in.nval;
					in.nextToken();
					r = (int) in.nval;
					in.nextToken();
					v = (long) in.nval;
					add(l, r, v);
				} else {
					in.nextToken();
					l = (int) in.nval;
					in.nextToken();
					r = (int) in.nval;
					out.println(range(l, r));
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
