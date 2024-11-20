package class152;

// 可持久化文艺平衡树(java版)
// 测试链接 : https://www.luogu.com.cn/problem/P5055
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_LiteraryTreePersistent1 {

	public static int MAXN = 200001;

	public static int MAXM = MAXN * 80;

	public static int cnt = 0;

	public static int[] head = new int[MAXN];

	public static int[] key = new int[MAXM];

	public static int[] left = new int[MAXM];

	public static int[] right = new int[MAXM];

	public static int[] size = new int[MAXM];

	public static boolean[] reverse = new boolean[MAXM];

	public static long[] sum = new long[MAXM];

	public static double[] priority = new double[MAXM];

	public static int copy(int i) {
		key[++cnt] = key[i];
		left[cnt] = left[i];
		right[cnt] = right[i];
		size[cnt] = size[i];
		reverse[cnt] = reverse[i];
		sum[cnt] = sum[i];
		priority[cnt] = priority[i];
		return cnt;
	}

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + 1;
		sum[i] = sum[left[i]] + sum[right[i]] + key[i];
	}

	public static void down(int i) {
		if (reverse[i]) {
			if (left[i] != 0) {
				left[i] = copy(left[i]);
				reverse[left[i]] = !reverse[left[i]];
			}
			if (right[i] != 0) {
				right[i] = copy(right[i]);
				reverse[right[i]] = !reverse[right[i]];
			}
			int tmp = left[i];
			left[i] = right[i];
			right[i] = tmp;
			reverse[i] = false;
		}
	}

	public static void split(int l, int r, int i, int rank) {
		if (i == 0) {
			right[l] = left[r] = 0;
		} else {
			down(i);
			i = copy(i);
			if (size[left[i]] + 1 <= rank) {
				right[l] = i;
				split(i, r, right[i], rank - size[left[i]] - 1);
			} else {
				left[r] = i;
				split(l, i, left[i], rank);
			}
			up(i);
		}
	}

	public static int merge(int l, int r) {
		if (l == 0 || r == 0) {
			return l + r;
		}
		if (priority[l] >= priority[r]) {
			down(l);
			l = copy(l);
			right[l] = merge(right[l], r);
			up(l);
			return l;
		} else {
			down(r);
			r = copy(r);
			left[r] = merge(l, left[r]);
			up(r);
			return r;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		long lastAns = 0;
		for (int i = 1, version, op, x = 0, y = 0, l, m, lm, r; i <= n; i++) {
			in.nextToken();
			version = (int) in.nval;
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			x = (int) ((long) in.nval ^ lastAns);
			if (op != 2) {
				in.nextToken();
				y = (int) ((long) in.nval ^ lastAns);
			}
			if (op == 1) {
				split(0, 0, head[version], x);
				l = right[0];
				r = left[0];
				left[0] = right[0] = 0;
				key[++cnt] = (int) y;
				size[cnt] = 1;
				sum[cnt] = y;
				priority[cnt] = Math.random();
				head[i] = merge(merge(l, cnt), r);
			} else if (op == 2) {
				split(0, 0, head[version], x);
				lm = right[0];
				r = left[0];
				split(0, 0, lm, x - 1);
				l = right[0];
				m = left[0];
				left[0] = right[0] = 0;
				head[i] = merge(l, r);
			} else if (op == 3) {
				split(0, 0, head[version], y);
				lm = right[0];
				r = left[0];
				split(0, 0, lm, x - 1);
				l = right[0];
				m = left[0];
				left[0] = right[0] = 0;
				reverse[m] = !reverse[m];
				head[i] = merge(merge(l, m), r);
			} else {
				split(0, 0, head[version], y);
				lm = right[0];
				r = left[0];
				split(0, 0, lm, x - 1);
				l = right[0];
				m = left[0];
				lastAns = sum[m];
				left[0] = right[0] = 0;
				out.println(lastAns);
				head[i] = merge(merge(l, m), r);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
