package class150;

// 替罪羊树的实现(java版)
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

public class Code02_ScapeGoat1 {

	// 平衡因子
	public static double ALPHA = 0.7;

	// 空间的最大使用量
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

	// 节点总数
	public static int[] diff = new int[MAXN];

	// 中序收集节点的数组
	public static int[] collect = new int[MAXN];

	// 中序收集节点的计数
	public static int ci;

	// 最上方的不平衡节点
	public static int top;

	// top的父节点
	public static int father;

	// top是父节点的什么孩子，1代表左孩子，2代表右孩子
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
