package class155;

// 可持久化左偏树的实现，利用对数器验证正确性，java版

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Code03_PersistentLeftistTree1 {

	public static int MAXN = 10000;
	public static int MAXV = 100000;
	public static int MAXT = 2000001;

	public static int[] rt = new int[MAXN];
	public static int[] num = new int[MAXT];
	public static int[] left = new int[MAXT];
	public static int[] right = new int[MAXT];
	public static int[] dist = new int[MAXT];
	public static int[] size = new int[MAXT];
	public static int cnt = 0;

	public static int init(int v) {
		num[++cnt] = v;
		left[cnt] = right[cnt] = dist[cnt] = 0;
		return cnt;
	}

	public static int clone(int i) {
		num[++cnt] = num[i];
		left[cnt] = left[i];
		right[cnt] = right[i];
		dist[cnt] = dist[i];
		return cnt;
	}

	public static int merge(int i, int j) {
		if (i == 0 || j == 0) {
			return i + j;
		}
		int tmp;
		if (num[i] > num[j]) {
			tmp = i;
			i = j;
			j = tmp;
		}
		int h = clone(i);
		right[h] = merge(right[h], j);
		if (dist[left[h]] < dist[right[h]]) {
			tmp = left[h];
			left[h] = right[h];
			right[h] = tmp;
		}
		dist[h] = dist[right[h]] + 1;
		return h;
	}

	public static int pop(int i) {
		if (left[i] == 0 && right[i] == 0) {
			return 0;
		}
		if (left[i] == 0 || right[i] == 0) {
			return clone(left[i] + right[i]);
		}
		return merge(left[i], right[i]);
	}

	// 可持久化左偏树，x版本加入数字y，生成最新的i版本
	public static void treeAdd(int x, int y, int i) {
		rt[i] = merge(rt[x], init(y));
		size[rt[i]] = size[rt[x]] + 1;
	}

	// 可持久化左偏树，x版本与y版本合并，生成最新的i版本
	public static void treeMerge(int x, int y, int i) {
		if (rt[x] == 0 && rt[y] == 0) {
			rt[i] = 0;
		} else if (rt[x] == 0 || rt[y] == 0) {
			rt[i] = clone(rt[x] + rt[y]);
		} else {
			rt[i] = merge(rt[x], rt[y]);
		}
		size[rt[i]] = size[rt[x]] + size[rt[y]];
	}

	// 可持久化左偏树，x版本弹出顶部，生成最新的i版本
	public static void treePop(int x, int i) {
		if (size[rt[x]] == 0) {
			rt[i] = 0;
		} else {
			rt[i] = pop(rt[x]);
			size[rt[i]] = size[rt[x]] - 1;
		}
	}

	// 验证结构
	public static ArrayList<PriorityQueue<Integer>> verify = new ArrayList<>();

	// 验证结构，x版本加入数字y，生成最新版本
	public static void verifyAdd(int x, int y) {
		PriorityQueue<Integer> pre = verify.get(x);
		ArrayList<Integer> tmp = new ArrayList<>();
		while (!pre.isEmpty()) {
			tmp.add(pre.poll());
		}
		PriorityQueue<Integer> cur = new PriorityQueue<>();
		for (int number : tmp) {
			pre.add(number);
			cur.add(number);
		}
		cur.add(y);
		verify.add(cur);
	}

	// 验证结构，x版本与y版本合并，生成最新版本
	public static void verifyMerge(int x, int y) {
		PriorityQueue<Integer> h1 = verify.get(x);
		PriorityQueue<Integer> h2 = verify.get(y);
		ArrayList<Integer> tmp = new ArrayList<>();
		PriorityQueue<Integer> cur = new PriorityQueue<>();
		while (!h1.isEmpty()) {
			int number = h1.poll();
			tmp.add(number);
			cur.add(number);
		}
		for (int number : tmp) {
			h1.add(number);
		}
		tmp.clear();
		while (!h2.isEmpty()) {
			int number = h2.poll();
			tmp.add(number);
			cur.add(number);
		}
		for (int number : tmp) {
			h2.add(number);
		}
		verify.add(cur);
	}

	// 验证结构，x版本弹出顶部，生成最新版本
	public static void verifyPop(int x) {
		PriorityQueue<Integer> pre = verify.get(x);
		PriorityQueue<Integer> cur = new PriorityQueue<>();
		if (pre.size() == 0) {
			verify.add(cur);
		} else {
			int top = pre.poll();
			ArrayList<Integer> tmp = new ArrayList<>();
			while (!pre.isEmpty()) {
				tmp.add(pre.poll());
			}
			for (int number : tmp) {
				pre.add(number);
				cur.add(number);
			}
			pre.add(top);
			verify.add(cur);
		}
	}

	// 可持久化左偏树i版本的堆
	// 是否等于
	// 验证结构i版本的堆
	public static boolean check(int i) {
		int h1 = rt[i];
		PriorityQueue<Integer> h2 = verify.get(i);
		if (size[h1] != h2.size()) {
			return false;
		}
		boolean ans = true;
		ArrayList<Integer> tmp = new ArrayList<>();
		while (!h2.isEmpty()) {
			int o1 = num[h1];
			h1 = pop(h1);
			int o2 = h2.poll();
			tmp.add(o2);
			if (o1 != o2) {
				ans = false;
				break;
			}
		}
		for (int v : tmp) {
			h2.add(v);
		}
		return ans;
	}

	public static void main(String[] args) {
		System.out.println("测试开始");
		dist[0] = -1;
		rt[0] = size[0] = 0; // 可持久化左偏树生成0版本的堆
		verify.add(new PriorityQueue<>()); // 验证结构生成0版本的堆
		for (int i = 1, op, x, y; i < MAXN; i++) {
			// op == 1，x版本的堆里加入数字y，形成i号版本的堆
			// op == 2，x版本的堆和y版本的堆合并，形成i号版本的堆
			// op == 3，x版本的堆弹出堆顶，形成i号版本的堆
			op = i == 1 ? 1 : ((int) (Math.random() * 3) + 1);
			x = (int) (Math.random() * i);
			if (op == 1) {
				y = (int) (Math.random() * MAXV);
				treeAdd(x, y, i);
				verifyAdd(x, y);
			} else if (op == 2) {
				y = x;
				do {
					y = (int) (Math.random() * i);
				} while (y == x);
				// 保证x != y
				treeMerge(x, y, i);
				verifyMerge(x, y);
			} else {
				treePop(x, i);
				verifyPop(x);
			}
			// 检查最新版本的堆是否一样
			if (!check(i)) {
				System.out.println("出错了！");
			}
		}
		// 最后验证是否所有版本的堆都一样
		for (int i = 1; i < MAXN; i++) {
			if (!check(i)) {
				System.out.println("出错了！");
			}
		}
		System.out.println("测试结束");
	}

}
