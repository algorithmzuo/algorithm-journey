package class148;

import java.util.Arrays;

// 重建队列(做到最优时间复杂度)
// 一共n个人，每个人有(a, b)两个数据，数据a表示该人的身高
// 数据b表示该人的要求，站在自己左边的人中，正好有b个人的身高大于等于自己的身高
// 请你把n个人从左到右进行排列，要求每个人的要求都可以满足
// 返回其中一种排列即可，本题的数据保证一定存在这样的排列
// 题解中的绝大多数方法，时间复杂度O(n平方)，但是时间复杂度能做到O(n * log n)
// 测试链接 : https://leetcode.cn/problems/queue-reconstruction-by-height/
public class Code02_ReconstructionQueue {

	public static int[][] reconstructQueue(int[][] people) {
		Arrays.sort(people, (a, b) -> a[0] != b[0] ? (b[0] - a[0]) : (a[1] - b[1]));
		for (int[] p : people) {
			add(p[0], p[1]);
		}
		fill(people);
		clear();
		return people;
	}

	public static int MAXN = 2001;

	public static int cnt = 0;

	public static int head = 0;

	public static int[] key = new int[MAXN];

	public static int[] height = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];
	
	public static int[] value = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + 1;
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

	public static void add(int num, int index) {
		head = add(head, index, num, index);
	}

	public static int add(int i, int rank, int num, int index) {
		if (i == 0) {
			key[++cnt] = num;
			value[cnt] = index;
			size[cnt] = height[cnt] = 1;
			return cnt;
		}
		if (size[left[i]] >= rank) {
			left[i] = add(left[i], rank, num, index);
		} else {
			right[i] = add(right[i], rank - size[left[i]] - 1, num, index);
		}
		up(i);
		return maintain(i);
	}

	public static void fill(int[][] ans) {
		fi = 0;
		inOrder(ans, head);
	}

	public static int fi;

	public static void inOrder(int[][] ans, int i) {
		if (i == 0) {
			return;
		}
		inOrder(ans, left[i]);
		ans[fi][0] = key[i];
		ans[fi++][1] = value[i];
		inOrder(ans, right[i]);
	}

	public static void clear() {
		Arrays.fill(key, 1, cnt + 1, 0);
		Arrays.fill(height, 1, cnt + 1, 0);
		Arrays.fill(left, 1, cnt + 1, 0);
		Arrays.fill(right, 1, cnt + 1, 0);
		Arrays.fill(value, 1, cnt + 1, 0);
		Arrays.fill(size, 1, cnt + 1, 0);
		cnt = 0;
		head = 0;
	}

}
