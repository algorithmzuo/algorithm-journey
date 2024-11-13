package class151;

// Treap树的实现(java版)
// 实现一种结构，支持如下操作，要求单次调用的时间复杂度O(log n)
// 1，增加x，重复加入算多个词频
// 2，删除x，如果有多个，只删掉一个
// 3，查询x的排名，x的排名为，比x小的数的个数+1
// 4，查询数据中排名为x的数
// 5，查询x的前驱，x的前驱为，小于x的数中最大的数，不存在返回整数最小值
// 6，查询x的后继，x的后继为，大于x的数中最小的数，不存在返回整数最大值
// 所有操作的次数 <= 10^5
// -10^7 <= x <= +10^7
// 测试链接 : https://www.luogu.com.cn/problem/P3369
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_Treap1 {

	public static int MAXN = 100001;

	// 整棵树的头节点编号
	public static int head = 0;

	// 空间使用计数
	public static int cnt = 0;

	// 节点的key值
	public static int[] key = new int[MAXN];

	// 节点key的计数
	public static int[] count = new int[MAXN];

	// 左孩子
	public static int[] left = new int[MAXN];

	// 右孩子
	public static int[] right = new int[MAXN];

	// 数字总数
	public static int[] size = new int[MAXN];

	// 节点优先级
	public static double[] priority = new double[MAXN];

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + count[i];
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

	public static int add(int i, int num) {
		if (i == 0) {
			key[++cnt] = num;
			count[cnt] = size[cnt] = 1;
			priority[cnt] = Math.random();
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
		if (left[i] != 0 && priority[left[i]] > priority[i]) {
			return rightRotate(i);
		}
		if (right[i] != 0 && priority[right[i]] > priority[i]) {
			return leftRotate(i);
		}
		return i;
	}

	public static void add(int num) {
		head = add(head, num);
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
					if (priority[left[i]] >= priority[right[i]]) {
						i = rightRotate(i);
						right[i] = remove(right[i], num);
					} else {
						i = leftRotate(i);
						left[i] = remove(left[i], num);
					}
				}
			}
		}
		up(i);
		return i;
	}

	public static void remove(int num) {
		if (rank(num) != rank(num + 1)) {
			head = remove(head, num);
		}
	}

	public static void clear() {
		Arrays.fill(key, 1, cnt + 1, 0);
		Arrays.fill(count, 1, cnt + 1, 0);
		Arrays.fill(left, 1, cnt + 1, 0);
		Arrays.fill(right, 1, cnt + 1, 0);
		Arrays.fill(size, 1, cnt + 1, 0);
		Arrays.fill(priority, 1, cnt + 1, 0);
		cnt = 0;
		head = 0;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		for (int i = 1, op, x; i <= n; i++) {
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			x = (int) in.nval;
			if (op == 1) {
				add(x);
			} else if (op == 2) {
				remove(x);
			} else if (op == 3) {
				out.println(rank(x));
			} else if (op == 4) {
				out.println(index(x));
			} else if (op == 5) {
				out.println(pre(x));
			} else {
				out.println(post(x));
			}
		}
		clear();
		out.flush();
		out.close();
		br.close();
	}

}
