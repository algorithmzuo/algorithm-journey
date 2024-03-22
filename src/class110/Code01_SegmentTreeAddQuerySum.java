package class110;

// 线段树支持范围增加、范围查询
// 维护累加和
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

public class Code01_SegmentTreeAddQuerySum {

	public static int MAXN = 100001;

	public static long[] arr = new long[MAXN];

	public static long[] sum = new long[MAXN << 2];

	public static long[] add = new long[MAXN << 2];

	// 累加和信息的汇总
	public static void up(int i) {
		// 父范围的累加和 = 左范围累加和 + 右范围累加和
		sum[i] = sum[i << 1] + sum[i << 1 | 1];
	}

	// 懒信息的下发
	public static void down(int i, int ln, int rn) {
		if (add[i] != 0) {
			// 发左
			lazy(i << 1, add[i], ln);
			// 发右
			lazy(i << 1 | 1, add[i], rn);
			// 父范围懒信息清空
			add[i] = 0;
		}
	}

	// 当前来到l~r范围，对应的信息下标是i，范围上数字的个数是n = r-l+1
	// 现在收到一个懒更新任务 : l~r范围上每个数字增加v
	// 这个懒更新任务有可能是任务范围把当前线段树范围全覆盖导致的
	// 也有可能是父范围的懒信息下发下来的
	// 总之把线段树当前范围的sum数组和add数组调整好
	// 就不再继续往下下发了，懒住了
	public static void lazy(int i, long v, int n) {
		sum[i] += v * n;
		add[i] += v;
	}

	// 建树
	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = arr[l];
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
		add[i] = 0;
	}

	// 范围修改
	// jobl ~ jobr范围上每个数字增加jobv
	public static void add(int jobl, int jobr, long jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, jobv, r - l + 1);
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	// 查询累加和
	public static long query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		int mid = (l + r) >> 1;
		down(i, mid - l + 1, r - mid);
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
		in.nextToken(); int n = (int) in.nval;
		in.nextToken(); int m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (long) in.nval;
		}
		build(1, n, 1);
		long jobv;
		for (int i = 1, op, jobl, jobr; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			if (op == 1) {
				in.nextToken(); jobl = (int) in.nval;
				in.nextToken(); jobr = (int) in.nval;
				in.nextToken(); jobv = (long) in.nval;
				add(jobl, jobr, jobv, 1, n, 1);
			} else {
				in.nextToken(); jobl = (int) in.nval;
				in.nextToken(); jobr = (int) in.nval;
				out.println(query(jobl, jobr, 1, n, 1));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
