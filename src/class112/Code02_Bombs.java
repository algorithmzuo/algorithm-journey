package class112;

// 贪婪大陆
// 一共有n个格子，编号1~n，开始时格子上没有地雷，实现两种操作，一共调用m次
// 操作 1 l r : 在l~r范围的格子上放置一种新型地雷，每次地雷都是新款
// 操作 2 l r : 查询l~r范围的格子上一共放置过多少款不同的地雷
// 操作1并不是范围上替换地雷，而是范围上新增新型地雷，注意如下的例子
// 执行 1 3 6，表示3~6范围上新加一种地雷，假设地雷类型是A
// 执行 1 3 4，表示3~4范围上又新加一种地雷，和上次的地雷不同，假设地雷类型是B
// 格子3有两种地雷(A、B)、格子4有两种地雷(A、B)、格子5有一种地雷(A)、格子6有一种地雷(A)
// 执行 2 4 5，返回2；执行 2 5 6，会返回1
// 测试链接 : https://www.luogu.com.cn/problem/P2184
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_Bombs {

	public static int MAXN = 100001;

	public static int[] bombStarts = new int[MAXN << 2];

	public static int[] bombEnds = new int[MAXN << 2];

	public static void up(int i) {
		bombStarts[i] = bombStarts[i << 1] + bombStarts[i << 1 | 1];
		bombEnds[i] = bombEnds[i << 1] + bombEnds[i << 1 | 1];
	}

	public static void build(int l, int r, int i) {
		if (l < r) {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
		}
		bombStarts[i] = 0;
		bombEnds[i] = 0;
	}

	// jobt==0表示在添加地雷范围的开头，jobi就是地雷范围的开头位置
	// jobt==1表示在添加地雷范围的结尾，jobi就是地雷范围的结尾位置
	public static void add(int jobt, int jobi, int l, int r, int i) {
		if (l == r) {
			if (jobt == 0) {
				bombStarts[i]++;
			} else {
				bombEnds[i]++;
			}
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				add(jobt, jobi, l, mid, i << 1);
			} else {
				add(jobt, jobi, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	// jobt==0表示在查询[jobl ~ jobr]范围上有多少地雷范围的开头
	// jobt==1表示在查询[jobl ~ jobr]范围上有多少地雷范围的结尾
	public static int query(int jobt, int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return jobt == 0 ? bombStarts[i] : bombEnds[i];
		} else {
			int mid = (l + r) >> 1;
			int ans = 0;
			if (jobl <= mid) {
				ans += query(jobt, jobl, jobr, l, mid, i << 1);
			}
			if (jobr > mid) {
				ans += query(jobt, jobl, jobr, mid + 1, r, i << 1 | 1);
			}
			return ans;
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
		build(1, n, 1);
		for (int i = 1, op, jobl, jobr; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			jobl = (int) in.nval;
			in.nextToken();
			jobr = (int) in.nval;
			if (op == 1) {
				add(0, jobl, 1, n, 1);
				add(1, jobr, 1, n, 1);
			} else {
				int s = query(0, 1, jobr, 1, n, 1);
				// 如果jobl==1，说明没有更左的区域
				// 如果jobl>1，说明有更左的区域
				int e = jobl == 1 ? 0 : query(1, 1, jobl - 1, 1, n, 1);
				out.println(s - e);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
