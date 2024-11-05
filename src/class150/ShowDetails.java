package class150;

// 不同的平衡因子对替罪羊树产生的影响
// 一旦， max(左树节点数，右树节点数) / 整树节点数 >= 平衡因子，就会发生重构
// 平衡因子不能小于0.5，不能大于1.0，因为无意义
// 当平衡因子等于0.5时树最平衡，即树高最小，此时查询效率高，但是重构的节点最多
// 当平衡因子等于1.0时树为线状，即树高最大，此时查询效率低，但是重构几乎不发生
// 为了保证查询效率高，同时重构的节点也不多，需要设置合适的平衡因子，一般为0.7
// 当平衡因子为0.7左右时，树高几乎是O(log n)，这保证了查询效率
// 同时重构触发时机合适，重构的节点不会特别多，均摊下来，单次代价认为是O(log n)

import java.util.Arrays;

public class ShowDetails {

	public static int MAXN = 100001;

	public static double ALPHA = 0.7;

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

	public static void inorder(int i) {
		if (i != 0) {
			inorder(left[i]);
			if (count[i] > 0) {
				collect[++ci] = i;
			}
			inorder(right[i]);
		}
	}

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + count[i];
		diff[i] = diff[left[i]] + diff[right[i]] + (count[i] > 0 ? 1 : 0);
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

	public static int rebuild(int i) {
		ci = 0;
		inorder(i);
		if (ci > 0) {
			return build(1, ci);
		} else {
			return 0;
		}
	}

	// 一旦判断不平衡，接下来必然重构，所以统计总共有多少节点会重构
	public static boolean balance(int i) {
		if (ALPHA * diff[i] > Math.max(diff[left[i]], diff[right[i]])) {
			return true;
		} else {
			cost += diff[i];
			return false;
		}
	}

	public static void add(int num) {
		head = add(head, num);
	}

	public static int add(int i, int num) {
		if (i == 0) {
			i = ++cnt;
			key[i] = num;
			left[i] = right[i] = 0;
			count[i] = size[i] = diff[i] = 1;
		} else {
			if (key[i] == num) {
				count[i]++;
			} else if (key[i] > num) {
				left[i] = add(left[i], num);
			} else {
				right[i] = add(right[i], num);
			}
		}
		up(i);
		return balance(i) ? i : rebuild(i);
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

	public static void remove(int num) {
		if (rank(num) != rank(num + 1)) {
			head = remove(head, num);
		}
	}

	public static int remove(int i, int num) {
		if (key[i] == num) {
			count[i]--;
		} else if (key[i] > num) {
			left[i] = remove(left[i], num);
		} else {
			right[i] = remove(right[i], num);
		}
		up(i);
		return balance(i) ? i : rebuild(i);
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

	public static int deep(int i) {
		if (i == 0) {
			return 0;
		}
		return Math.max(deep(left[i]), deep(right[i])) + 1;
	}

	public static int max, cost;

	public static void main(String[] args) {
		System.out.println("测试开始");
		ALPHA = 0.7; // 设置不同的平衡因子
		max = 10000; // 设置插入范围
		cost = 0; // 清空重构节点计数
		for (int num = 1; num <= max; num++) {
			add(num);
		}
		System.out.println("依次插入从 1 到 " + max + " 的所有数字");
		System.out.println("平衡因子 : " + ALPHA);
		System.out.println("重构节点总数 : " + cost);
		System.out.println("树高 : " + deep(head));
		System.out.println("测试结束");
	}

}
