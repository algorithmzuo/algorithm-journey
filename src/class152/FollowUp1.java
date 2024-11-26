package class152;

// FHQ-Treap实现普通有序表，不用词频压缩，数据加强的测试，java版
// 这个文件课上没有讲，测试数据加强了，而且有强制在线的要求
// 基本功能要求都是不变的，可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P6136
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class FollowUp1 {

	public static int MAXN = 2000001;

	public static int head = 0;

	public static int cnt = 0;

	public static int[] key = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static double[] priority = new double[MAXN];

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + 1;
	}

	public static void split(int l, int r, int i, int num) {
		if (i == 0) {
			right[l] = left[r] = 0;
		} else {
			if (key[i] <= num) {
				right[l] = i;
				split(i, r, right[i], num);
			} else {
				left[r] = i;
				split(l, i, left[i], num);
			}
			up(i);
		}
	}

	public static int merge(int l, int r) {
		if (l == 0 || r == 0) {
			return l + r;
		}
		if (priority[l] >= priority[r]) {
			right[l] = merge(right[l], r);
			up(l);
			return l;
		} else {
			left[r] = merge(l, left[r]);
			up(r);
			return r;
		}
	}

	public static void add(int num) {
		split(0, 0, head, num);
		key[++cnt] = num;
		size[cnt] = 1;
		priority[cnt] = Math.random();
		head = merge(merge(right[0], cnt), left[0]);
	}

	public static void remove(int num) {
		split(0, 0, head, num);
		int lm = right[0];
		int r = left[0];
		split(0, 0, lm, num - 1);
		int l = right[0];
		int m = left[0];
		head = merge(merge(l, merge(left[m], right[m])), r);
	}

	public static int rank(int num) {
		split(0, 0, head, num - 1);
		int ans = size[right[0]] + 1;
		head = merge(right[0], left[0]);
		return ans;
	}

	public static int index(int i, int x) {
		if (size[left[i]] >= x) {
			return index(left[i], x);
		} else if (size[left[i]] + 1 < x) {
			return index(right[i], x - size[left[i]] - 1);
		} else {
			return key[i];
		}
	}

	public static int index(int x) {
		return index(head, x);
	}

	public static int pre(int i, int num) {
		if (i == 0) {
			return Integer.MIN_VALUE;
		}
		if (key[i] >= num) {
			return pre(left[i], num);
		} else {
			return Math.max(key[i], pre(right[i], num));
		}
	}

	public static int pre(int num) {
		return pre(head, num);
	}

	public static int post(int i, int num) {
		if (i == 0) {
			return Integer.MAX_VALUE;
		}
		if (key[i] <= num) {
			return post(right[i], num);
		} else {
			return Math.min(key[i], post(left[i], num));
		}
	}

	public static int post(int num) {
		return post(head, num);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		for (int i = 1, num; i <= n; i++) {
			in.nextToken();
			num = (int) in.nval;
			add(num);
		}
		int lastAns = 0;
		int ans = 0;
		for (int i = 1, op, x; i <= m; i++) {
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			x = (int) in.nval ^ lastAns;
			if (op == 1) {
				add(x);
			} else if (op == 2) {
				remove(x);
			} else if (op == 3) {
				lastAns = rank(x);
				ans ^= lastAns;
			} else if (op == 4) {
				lastAns = index(x);
				ans ^= lastAns;
			} else if (op == 5) {
				lastAns = pre(x);
				ans ^= lastAns;
			} else {
				lastAns = post(x);
				ans ^= lastAns;
			}
		}
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
