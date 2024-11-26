package class148;

// AVL实现普通有序表，数据加强的测试，java版
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

	public static int MAXN = 2000001;

	public static int cnt = 0;

	public static int head = 0;

	public static int[] key = new int[MAXN];

	public static int[] height = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] count = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + count[i];
		height[i] = Math.max(height[left[i]], height[right[i]]) + 1;
	}

	public static int leftRotate(int i) {
		int r = right[i];
		right[i] = left[r];
		left[r] = i;
		up(i);
		up(r);
		return r;
	}

	public static int rightRotate(int i) {
		int l = left[i];
		left[i] = right[l];
		right[l] = i;
		up(i);
		up(l);
		return l;
	}

	public static int maintain(int i) {
		int lh = height[left[i]];
		int rh = height[right[i]];
		if (lh - rh > 1) {
			if (height[left[left[i]]] >= height[right[left[i]]]) {
				i = rightRotate(i);
			} else {
				left[i] = leftRotate(left[i]);
				i = rightRotate(i);
			}
		} else if (rh - lh > 1) {
			if (height[right[right[i]]] >= height[left[right[i]]]) {
				i = leftRotate(i);
			} else {
				right[i] = rightRotate(right[i]);
				i = leftRotate(i);
			}
		}
		return i;
	}

	public static void add(int num) {
		head = add(head, num);
	}

	public static int add(int i, int num) {
		if (i == 0) {
			key[++cnt] = num;
			count[cnt] = size[cnt] = height[cnt] = 1;
			return cnt;
		}
		if (key[i] == num) {
			count[i]++;
		} else if (key[i] > num) {
			left[i] = add(left[i], num);
		} else {
			right[i] = add(right[i], num);
		}
		up(i);
		return maintain(i);
	}

	public static void remove(int num) {
		if (rank(num) != rank(num + 1)) {
			head = remove(head, num);
		}
	}

	public static int remove(int i, int num) {
		if (key[i] < num) {
			right[i] = remove(right[i], num);
		} else if (key[i] > num) {
			left[i] = remove(left[i], num);
		} else {
			if (count[i] > 1) {
				count[i]--;
			} else {
				if (left[i] == 0 && right[i] == 0) {
					return 0;
				} else if (left[i] != 0 && right[i] == 0) {
					i = left[i];
				} else if (left[i] == 0 && right[i] != 0) {
					i = right[i];
				} else {
					int mostLeft = right[i];
					while (left[mostLeft] != 0) {
						mostLeft = left[mostLeft];
					}
					right[i] = removeMostLeft(right[i], mostLeft);
					left[mostLeft] = left[i];
					right[mostLeft] = right[i];
					i = mostLeft;
				}
			}
		}
		up(i);
		return maintain(i);
	}

	public static int removeMostLeft(int i, int mostLeft) {
		if (i == mostLeft) {
			return right[i];
		} else {
			left[i] = removeMostLeft(left[i], mostLeft);
			up(i);
			return maintain(i);
		}
	}

	public static int rank(int num) {
		return small(head, num) + 1;
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

	public static int index(int x) {
		return index(head, x);
	}

	public static int index(int i, int x) {
		if (size[left[i]] >= x) {
			return index(left[i], x);
		} else if (size[left[i]] + count[i] < x) {
			return index(right[i], x - size[left[i]] - count[i]);
		}
		return key[i];
	}

	public static int pre(int num) {
		return pre(head, num);
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

	public static int post(int num) {
		return post(head, num);
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

	public static void clear() {
		Arrays.fill(key, 1, cnt + 1, 0);
		Arrays.fill(height, 1, cnt + 1, 0);
		Arrays.fill(left, 1, cnt + 1, 0);
		Arrays.fill(right, 1, cnt + 1, 0);
		Arrays.fill(count, 1, cnt + 1, 0);
		Arrays.fill(size, 1, cnt + 1, 0);
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
