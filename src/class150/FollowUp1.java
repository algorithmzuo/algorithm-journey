package class150;

// 替罪羊树实现普通有序表，数据加强的测试，java版
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
import java.util.Arrays;

public class FollowUp1 {

	public static double ALPHA = 0.7;

	public static int MAXN = 2000001;

	public static int head = 0;

	public static int cnt = 0;

	public static int[] key = new int[MAXN];

	public static int[] count = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int[] diff = new int[MAXN];

	public static int[] collect = new int[MAXN];

	public static int ci;

	public static int top;

	public static int father;

	public static int side;

	public static int init(int num) {
		key[++cnt] = num;
		left[cnt] = right[cnt] = 0;
		count[cnt] = size[cnt] = diff[cnt] = 1;
		return cnt;
	}

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + count[i];
		diff[i] = diff[left[i]] + diff[right[i]] + (count[i] > 0 ? 1 : 0);
	}

	public static void inorder(int i) {
		if (i != 0) {
			inorder(left[i]);
			if (count[i] > 0) {
				collect[++ci] = i;
			}
			inorder(right[i]);
		}
	}

	public static int build(int l, int r) {
		if (l > r) {
			return 0;
		}
		int m = (l + r) / 2;
		int h = collect[m];
		left[h] = build(l, m - 1);
		right[h] = build(m + 1, r);
		up(h);
		return h;
	}

	public static void rebuild() {
		if (top != 0) {
			ci = 0;
			inorder(top);
			if (ci > 0) {
				if (father == 0) {
					head = build(1, ci);
				} else if (side == 1) {
					left[father] = build(1, ci);
				} else {
					right[father] = build(1, ci);
				}
			}
		}
	}

	public static boolean balance(int i) {
		return ALPHA * diff[i] >= Math.max(diff[left[i]], diff[right[i]]);
	}

	public static void add(int i, int f, int s, int num) {
		if (i == 0) {
			if (f == 0) {
				head = init(num);
			} else if (s == 1) {
				left[f] = init(num);
			} else {
				right[f] = init(num);
			}
		} else {
			if (key[i] == num) {
				count[i]++;
			} else if (key[i] > num) {
				add(left[i], i, 1, num);
			} else {
				add(right[i], i, 2, num);
			}
			up(i);
			if (!balance(i)) {
				top = i;
				father = f;
				side = s;
			}
		}
	}

	public static void add(int num) {
		top = father = side = 0;
		add(head, 0, 0, num);
		rebuild();
	}

	public static int small(int i, int num) {
		if (i == 0) {
			return 0;
		}
		if (key[i] >= num) {
			return small(left[i], num);
		} else {
			return size[left[i]] + count[i] + small(right[i], num);
		}
	}

	public static int rank(int num) {
		return small(head, num) + 1;
	}

	public static int index(int i, int x) {
		if (size[left[i]] >= x) {
			return index(left[i], x);
		} else if (size[left[i]] + count[i] < x) {
			return index(right[i], x - size[left[i]] - count[i]);
		}
		return key[i];
	}

	public static int index(int x) {
		return index(head, x);
	}

	public static int pre(int num) {
		int kth = rank(num);
		if (kth == 1) {
			return Integer.MIN_VALUE;
		} else {
			return index(kth - 1);
		}
	}

	public static int post(int num) {
		int kth = rank(num + 1);
		if (kth == size[head] + 1) {
			return Integer.MAX_VALUE;
		} else {
			return index(kth);
		}
	}

	public static void remove(int i, int f, int s, int num) {
		if (key[i] == num) {
			count[i]--;
		} else if (key[i] > num) {
			remove(left[i], i, 1, num);
		} else {
			remove(right[i], i, 2, num);
		}
		up(i);
		if (!balance(i)) {
			top = i;
			father = f;
			side = s;
		}
	}

	public static void remove(int num) {
		if (rank(num) != rank(num + 1)) {
			top = father = side = 0;
			remove(head, 0, 0, num);
			rebuild();
		}
	}

	public static void clear() {
		Arrays.fill(key, 1, cnt + 1, 0);
		Arrays.fill(count, 1, cnt + 1, 0);
		Arrays.fill(left, 1, cnt + 1, 0);
		Arrays.fill(right, 1, cnt + 1, 0);
		Arrays.fill(size, 1, cnt + 1, 0);
		Arrays.fill(diff, 1, cnt + 1, 0);
		cnt = 0;
		head = 0;
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
		clear();
		out.flush();
		out.close();
		br.close();
	}

}
