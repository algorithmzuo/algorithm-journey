package class111;

// 瓶子里的花朵
// 给定n个瓶子，编号从0~n-1，一开始所有瓶子都是空的
// 每个瓶子最多插入一朵花，实现以下两种类型的操作
// 操作1 : insert(from, flowers)
//         一共有flower朵花，从from位置开始依次插入花朵，已经有花的瓶子跳过
//         如果一直到最后的瓶子，花也没有用完，就丢弃剩下的花朵
//         返回这次操作插入的首个空瓶的位置 和 最后空瓶的位置
//         如果从from开始到最后位置，所有瓶子都有花，打印"Can not put any one."
// 操作2 : clear(left, right)
//         清理从left位置开始到right位置的瓶子里所有的花朵，这些瓶子重新变回空瓶
//         返回清理花朵的数量
// 测试链接 : http://acm.hdu.edu.cn/showproblem.php?pid=4614
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_VasesAndFlowers {

	public static int MAXN = 50001;

	public static int[] sum = new int[MAXN << 2];

	public static int[] change = new int[MAXN << 2];

	public static boolean[] update = new boolean[MAXN << 2];

	public static int n;

	public static void build(int l, int r, int rt) {
		if (l < r) {
			int mid = (l + r) / 2;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
		}
		sum[rt] = 0;
		update[rt] = false;
	}

	public static void up(int rt) {
		sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
	}

	public static void down(int rt, int ln, int rn) {
		if (update[rt]) {
			change[rt << 1] = change[rt];
			update[rt << 1] = true;
			sum[rt << 1] = change[rt] * ln;
			change[rt << 1 | 1] = change[rt];
			update[rt << 1 | 1] = true;
			sum[rt << 1 | 1] = change[rt] * rn;
			update[rt] = false;
		}
	}

	public static void update(int jobl, int jobr, int jobv, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			sum[rt] = jobv * (r - l + 1);
			change[rt] = jobv;
			update[rt] = true;
		} else {
			int mid = (l + r) >> 1;
			down(rt, mid - l + 1, r - mid);
			if (jobl <= mid) {
				update(jobl, jobr, jobv, l, mid, rt << 1);
			}
			if (jobr > mid) {
				update(jobl, jobr, jobv, mid + 1, r, rt << 1 | 1);
			}
			up(rt);
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			return sum[rt];
		}
		int mid = (l + r) >> 1;
		down(rt, mid - l + 1, r - mid);
		int ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, rt << 1);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, rt << 1 | 1);
		}
		return ans;
	}

	// 插入花的首个空瓶位置，设置给全局变量start
	// 插入花的最后空瓶位置，设置给全局变量end
	// 注意题目给的下标从0开始
	// 线段树下标从1开始
	public static int start, end;

	public static void insert(int from, int flowers) {
		from++;
		int zeros = n - from + 1 - query(from, n, 1, n, 1);
		if (zeros == 0) {
			start = 0;
			end = 0;
		} else {
			start = findZero(from, 1);
			end = findZero(from, Math.min(zeros, flowers));
			update(start, end, 1, 1, n, 1);
		}
		// 题目需要从0开始的下标
		start--;
		end--;
	}

	// s~n范围上
	// 返回第k个0的位置
	public static int findZero(int s, int k) {
		int l = s, r = n, mid;
		int ans = 0;
		while (l <= r) {
			mid = (l + r) / 2;
			if (mid - s + 1 - query(s, mid, 1, n, 1) >= k) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

	// 注意题目给的下标从0开始
	// 线段树下标从1开始
	public static int clear(int left, int right) {
		left++;
		right++;
		int ans = query(left, right, 1, n, 1);
		update(left, right, 0, 1, n, 1);
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int t = (int) in.nval;
		for (int i = 1; i <= t; i++) {
			in.nextToken();
			n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			build(1, n, 1);
			for (int j = 1; j <= m; j++) {
				in.nextToken();
				int op = (int) in.nval;
				if (op == 1) {
					in.nextToken(); int from = (int) in.nval;
					in.nextToken(); int flowers = (int) in.nval;
					insert(from, flowers);
					if (start == -1) {
						out.println("Can not put any one.");
					} else {
						out.println(start + " " + end);
					}
				} else {
					in.nextToken(); int left = (int) in.nval;
					in.nextToken(); int right = (int) in.nval;
					out.println(clear(left, right));
				}
			}
			out.println();
		}
		out.flush();
		out.close();
		br.close();
	}

}
