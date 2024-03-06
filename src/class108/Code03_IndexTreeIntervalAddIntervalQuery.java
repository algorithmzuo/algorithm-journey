package class108;

// 树状数组范围增加、范围查询模版
// 用于可差分的信息，本题以累加和举例
// 后面讲线段树也能实现这个功能，而且支持的信息种类更多
// 树状数组的优势在于：
// 线段树实现代码较多，树状数组实现代码较少
// 线段树使用空间较多，树状数组使用空间较少
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

	public static long[] tree1 = new long[MAXN];

	public static long[] tree2 = new long[MAXN];

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

	public static void add(int l, int r, long v) {
		add(tree1, l, v);
		add(tree1, r + 1, -v);
		add(tree2, l, v * (l - 1));
		add(tree2, r + 1, -v * r);
	}

	public static long range(int l, int r) {
		return sum(tree1, r) * r - sum(tree2, r) - sum(tree1, l - 1) * (l - 1) + sum(tree2, l - 1);
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
