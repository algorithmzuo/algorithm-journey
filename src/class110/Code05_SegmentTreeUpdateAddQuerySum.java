package class110;

// 线段树同时支持范围重置、范围增加、范围查询
// 维护累加和
// 对数器验证
// 当你写线段树出错了，就需要用对数器的方式来排查
// 所以本题选择对数器验证，来展示一下怎么写测试
public class Code05_SegmentTreeUpdateAddQuerySum {

	public static int MAXN = 1000001;

	public static long[] arr = new long[MAXN];

	public static long[] sum = new long[MAXN << 2];

	public static long[] add = new long[MAXN << 2];

	public static long[] change = new long[MAXN << 2];

	public static boolean[] update = new boolean[MAXN << 2];

	public static void up(int i) {
		sum[i] = sum[i << 1] + sum[i << 1 | 1];
	}

	public static void down(int i, int ln, int rn) {
		if (update[i]) {
			updateLazy(i << 1, change[i], ln);
			updateLazy(i << 1 | 1, change[i], rn);
			update[i] = false;
		}
		if (add[i] != 0) {
			addLazy(i << 1, add[i], ln);
			addLazy(i << 1 | 1, add[i], rn);
			add[i] = 0;
		}
	}

	public static void updateLazy(int i, long v, int n) {
		sum[i] = v * n;
		add[i] = 0;
		change[i] = v;
		update[i] = true;
	}

	public static void addLazy(int i, long v, int n) {
		sum[i] += v * n;
		add[i] += v;
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = arr[l];
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
		add[i] = 0;
		change[i] = 0;
		update[i] = false;
	}

	public static void update(int jobl, int jobr, long jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			updateLazy(i, jobv, r - l + 1);
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				update(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static void add(int jobl, int jobr, long jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			addLazy(i, jobv, r - l + 1);
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static long query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		int mid = (l + r) >> 1;
		down(i, mid - l + 1, r - mid);
		long ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, i << 1);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
		}
		return ans;
	}

	public static void main(String[] args) {
		System.out.println("测试开始");
		int n = 1000;
		int v = 2000;
		int t = 5000000;
		// 生成随机值填入arr数组
		randomArray(n, v);
		// 建立线段树
		build(1, n, 1);
		// 生成验证的结构
		long[] check = new long[n + 1];
		for (int i = 1; i <= n; i++) {
			check[i] = arr[i];
		}
		for (int i = 1; i <= t; i++) {
			// 生成操作类型
			// op = 0 增加操作
			// op = 1 更新操作
			// op = 2 查询操作
			int op = (int) (Math.random() * 3);
			// 下标从1开始，不从0开始，生成两个随机下标
			int a = (int) (Math.random() * n) + 1;
			int b = (int) (Math.random() * n) + 1;
			// 确保jobl <= jobr
			int jobl = Math.min(a, b);
			int jobr = Math.max(a, b);
			if (op == 0) {
				// 增加操作
				// 线段树、验证结构同步增加
				int jobv = (int) (Math.random() * v * 2) - v;
				add(jobl, jobr, jobv, 1, n, 1);
				checkAdd(check, jobl, jobr, jobv);
			} else if (op == 1) {
				// 更新操作
				// 线段树、验证结构同步更新
				int jobv = (int) (Math.random() * v * 2) - v;
				update(jobl, jobr, jobv, 1, n, 1);
				checkUpdate(check, jobl, jobr, jobv);
			} else {
				// 查询操作
				// 线段树、验证结构同步查询
				// 比对答案
				long ans1 = query(jobl, jobr, 1, n, 1);
				long ans2 = checkQuery(check, jobl, jobr);
				if (ans1 != ans2) {
					System.out.println("出错了!");
				}
			}
		}
		System.out.println("测试结束");
	}

	// 生成随机值填入arr数组
	// 为了验证
	public static void randomArray(int n, int v) {
		for (int i = 1; i <= n; i++) {
			arr[i] = (long) (Math.random() * v);
		}
	}

	// 验证结构的增加
	// 暴力增加
	// 为了验证
	public static void checkAdd(long[] check, int jobl, int jobr, long jobv) {
		for (int i = jobl; i <= jobr; i++) {
			check[i] += jobv;
		}
	}

	// 验证结构的更新
	// 暴力更新
	// 为了验证
	public static void checkUpdate(long[] check, int jobl, int jobr, long jobv) {
		for (int i = jobl; i <= jobr; i++) {
			check[i] = jobv;
		}
	}

	// 验证结构的查询
	// 暴力查询
	// 为了验证
	public static long checkQuery(long[] check, int jobl, int jobr) {
		long ans = 0;
		for (int i = jobl; i <= jobr; i++) {
			ans += check[i];
		}
		return ans;
	}

}
