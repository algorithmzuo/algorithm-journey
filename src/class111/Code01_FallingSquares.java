package class111;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 掉落的方块
// 有一个二维平面，x轴是最底的边界
// 给定二维整数数组poss，poss[i] = [ lefti, leni ]
// 表示第i个方块边长为leni，左侧边缘在x = lefti位置，所在高度非常高
// 所有方块都是正方形，依次从高处垂直掉落，也就是左边界顺着x = lefti往下
// 如果掉落的方块碰到已经掉落正方形的顶边或者x轴就停止掉落
// 如果方块掉落时仅仅是擦过已经掉落正方形的左侧边或右侧边，并不会停止掉落
// 一旦停止，它就会固定在那里，无法再移动
// 俄罗斯方块游戏和本题意思一样
// 返回一个整数数组ans ，其中ans[i]表示在第i块方块掉落后整体的最大高度
// 测试链接 : https://leetcode.cn/problems/falling-squares/
public class Code01_FallingSquares {

	public static int MAXN = 2001;

	public static int[] arr = new int[MAXN];

	public static int[] max = new int[MAXN << 2];

	public static int[] change = new int[MAXN << 2];

	public static boolean[] update = new boolean[MAXN << 2];

	public static List<Integer> fallingSquares(int[][] poss) {
		int n = sort(poss);
		build(1, n, 1);
		List<Integer> ans = new ArrayList<>();
		int max = 0, l, r, h;
		for (int[] pos : poss) {
			l = rank(n, pos[0]);
			r = rank(n, pos[0] + pos[1] - 1);
			h = query(l, r, 1, n, 1) + pos[1];
			max = Math.max(max, h);
			ans.add(max);
			update(l, r, h, 1, n, 1);
		}
		return ans;
	}

	public static int sort(int[][] poss) {
		int size = 1;
		for (int[] pos : poss) {
			arr[size++] = pos[0];
			arr[size++] = pos[0] + pos[1] - 1;
		}
		Arrays.sort(arr, 1, size);
		int n = 1;
		for (int i = 2; i < size; i++) {
			if (arr[n] != arr[i]) {
				arr[++n] = arr[i];
			}
		}
		return n;
	}

	public static int rank(int n, int v) {
		int ans = 0;
		int l = 1, r = n, m;
		while (l <= r) {
			m = (l + r) / 2;
			if (arr[m] >= v) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static void build(int l, int r, int rt) {
		if (l < r) {
			int mid = (l + r) >> 1;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
		}
		max[rt] = 0;
		change[rt] = 0;
		update[rt] = false;
	}

	public static void up(int rt) {
		max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
	}

	public static void down(int rt) {
		if (update[rt]) {
			update[rt << 1] = true;
			update[rt << 1 | 1] = true;
			change[rt << 1] = change[rt];
			change[rt << 1 | 1] = change[rt];
			max[rt << 1] = change[rt];
			max[rt << 1 | 1] = change[rt];
			update[rt] = false;
		}
	}

	public static void update(int jobl, int jobr, int jobv, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			update[rt] = true;
			change[rt] = jobv;
			max[rt] = jobv;
		} else {
			int mid = (l + r) >> 1;
			down(rt);
			if (jobl <= mid) {
				update(jobl, jobr, jobv, l, mid, rt << 1);
			}
			if (jobr > mid) {
				update(jobl, jobr, jobv, mid + 1, r, rt << 1 | 1);
			}
			up(rt);
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int rt) {
		if (jobl <= l && r <= jobr) {
			return max[rt];
		}
		int mid = (l + r) >> 1;
		down(rt);
		int ans = Integer.MIN_VALUE;
		if (jobl <= mid) {
			ans = Math.max(ans, query(jobl, jobr, l, mid, rt << 1));
		}
		if (jobr > mid) {
			ans = Math.max(ans, query(jobl, jobr, mid + 1, r, rt << 1 | 1));
		}
		return ans;
	}

}
