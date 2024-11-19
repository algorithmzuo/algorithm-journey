package class152;

// 可持久化有序表，FHQ-Treap实现，不用词频统计，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3835
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_FHQTreapPersistent1 {

	public static int MAXN = 500001;

	public static int cnt = 0;

	public static int[] head = new int[MAXN];

	public static int[] key = new int[MAXN * 50];

	public static int[] left = new int[MAXN * 50];

	public static int[] right = new int[MAXN * 50];

	public static int[] size = new int[MAXN * 50];

	public static double[] priority = new double[MAXN * 50];

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + 1;
	}

	public static int copy(int i) {
		key[++cnt] = key[i];
		left[cnt] = left[i];
		right[cnt] = right[i];
		size[cnt] = size[i];
		priority[cnt] = priority[i];
		return cnt;
	}

	public static void split(int l, int r, int i, int num) {
		if (i == 0) {
			right[l] = left[r] = 0;
		} else {
			i = copy(i);
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
			l = copy(l);
			right[l] = merge(right[l], r);
			up(l);
			return l;
		} else {
			r = copy(r);
			left[r] = merge(l, left[r]);
			up(r);
			return r;
		}
	}

	public static void add(int v, int i, int num) {
		split(0, 0, i, num);
		key[++cnt] = num;
		size[cnt] = 1;
		priority[cnt] = Math.random();
		head[v] = merge(merge(right[0], cnt), left[0]);
		left[0] = right[0] = 0;
	}

	public static void remove(int v, int i, int num) {
		split(0, 0, i, num);
		int lm = right[0];
		int r = left[0];
		left[0] = right[0] = 0;
		split(0, 0, lm, num - 1);
		int m = left[0];
		int l = right[0];
		left[0] = right[0] = 0;
		head[v] = merge(merge(l, merge(left[m], right[m])), r);
	}

	public static int small(int i, int num) {
		if (i == 0) {
			return 0;
		}
		if (key[i] >= num) {
			return small(left[i], num);
		} else {
			return size[left[i]] + 1 + small(right[i], num);
		}
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

	public static int pre(int i, int num) {
		if (i == 0) {
			return Integer.MIN_VALUE + 1;
		}
		if (key[i] >= num) {
			return pre(left[i], num);
		} else {
			return Math.max(key[i], pre(right[i], num));
		}
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

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		for (int i = 1, version, op, x; i <= n; i++) {
			in.nextToken();
			version = (int) in.nval;
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			x = (int) in.nval;
			head[i] = head[version];
			if (op == 1) {
				add(i, head[i], x);
			} else if (op == 2) {
				remove(i, head[i], x);
			} else if (op == 3) {
				out.println(small(head[i], x) + 1);
			} else if (op == 4) {
				out.println(index(head[i], x));
			} else if (op == 5) {
				out.println(pre(head[i], x));
			} else {
				out.println(post(head[i], x));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
