package class153;

// Splay树的实现，不用词频压缩，java版
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

public class Code01_Splay1 {

	public static int MAXN = 100001;

	public static int head = 0;

	public static int cnt = 0;

	public static int[] key = new int[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + 1;
	}

	public static int lr(int i) {
		return right[father[i]] == i ? 1 : 0;
	}

	public static void rotate(int i) {
		int f = father[i], g = father[f], soni = lr(i), sonf = lr(f);
		if (soni == 1) {
			right[f] = left[i];
			if (right[f] != 0) {
				father[right[f]] = f;
			}
			left[i] = f;
		} else {
			left[f] = right[i];
			if (left[f] != 0) {
				father[left[f]] = f;
			}
			right[i] = f;
		}
		if (g != 0) {
			if (sonf == 1) {
				right[g] = i;
			} else {
				left[g] = i;
			}
		}
		father[f] = i;
		father[i] = g;
		up(f);
		up(i);
	}

	public static void splay(int i, int goal) {
		int f = father[i], g = father[f];
		while (f != goal) {
			if (g != goal) {
				if (lr(i) == lr(f)) {
					rotate(f);
				} else {
					rotate(i);
				}
			}
			rotate(i);
			f = father[i];
			g = father[f];
		}
		if (goal == 0) {
			head = i;
		}
	}

	// 整棵树上找到中序排名为rank的节点，返回节点编号
	// 这个方法不是题目要求的查询操作，作为内部方法使用
	// 为什么该方法不进行提根操作？
	// 因为remove方法使用该方法时，要求find不能提根！
	public static int find(int rank) {
		int i = head;
		while (i != 0) {
			if (size[left[i]] + 1 == rank) {
				return i;
			} else if (size[left[i]] >= rank) {
				i = left[i];
			} else {
				rank -= size[left[i]] + 1;
				i = right[i];
			}
		}
		return 0;
	}

	public static void add(int num) {
		key[++cnt] = num;
		size[cnt] = 1;
		if (head == 0) {
			head = cnt;
		} else {
			int f = 0, i = head, son = 0;
			while (i != 0) {
				f = i;
				if (key[i] <= num) {
					son = 1;
					i = right[i];
				} else {
					son = 0;
					i = left[i];
				}
			}
			if (son == 1) {
				right[f] = cnt;
			} else {
				left[f] = cnt;
			}
			father[cnt] = f;
			splay(cnt, 0);
		}
	}

	public static int rank(int num) {
		int i = head, last = head;
		int ans = 0;
		while (i != 0) {
			last = i;
			if (key[i] >= num) {
				i = left[i];
			} else {
				ans += size[left[i]] + 1;
				i = right[i];
			}
		}
		splay(last, 0);
		return ans + 1;
	}

	public static int index(int x) {
		int i = find(x);
		splay(i, 0);
		return key[i];
	}

	public static int pre(int num) {
		int i = head, last = head;
		int ans = Integer.MIN_VALUE;
		while (i != 0) {
			last = i;
			if (key[i] >= num) {
				i = left[i];
			} else {
				ans = Math.max(ans, key[i]);
				i = right[i];
			}
		}
		splay(last, 0);
		return ans;
	}

	public static int post(int num) {
		int i = head, last = head;
		int ans = Integer.MAX_VALUE;
		while (i != 0) {
			last = i;
			if (key[i] <= num) {
				i = right[i];
			} else {
				ans = Math.min(ans, key[i]);
				i = left[i];
			}
		}
		splay(last, 0);
		return ans;
	}

	public static void remove(int num) {
		int kth = rank(num);
		if (kth != rank(num + 1)) {
			int i = find(kth);
			splay(i, 0);
			if (left[i] == 0) {
				head = right[i];
			} else if (right[i] == 0) {
				head = left[i];
			} else {
				int j = find(kth + 1);
				splay(j, i);
				left[j] = left[i];
				father[left[j]] = j;
				up(j);
				head = j;
			}
			father[head] = 0;
		}
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
		out.flush();
		out.close();
		br.close();
	}

}