package class110;

// 线段树支持范围重置、范围查询
// 维护最大值
// 对数器验证
// 当你写线段树出错了，就需要用对数器的方式来排查
// 所以本题选择对数器验证，来展示一下怎么写测试
public class Code04_SegmentTreeUpdateQueryMax {

	public static int MAXN = 100001;

	public static long[] arr = new long[MAXN];

	public static long[] max = new long[MAXN << 2];

	public static long[] change = new long[MAXN << 2];

	public static boolean[] update = new boolean[MAXN << 2];

	public static void up(int i) {
		max[i] = Math.max(max[i << 1], max[i << 1 | 1]);
	}

	public static void down(int i) {
		if (update[i]) {
			lazy(i << 1, change[i]);
			lazy(i << 1 | 1, change[i]);
			update[i] = false;
		}
	}

	public static void lazy(int i, long v) {
		max[i] = v;
		change[i] = v;
		update[i] = true;
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			max[i] = arr[l];
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
		change[i] = 0;
		update[i] = false;
	}

	public static void update(int jobl, int jobr, long jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, jobv);
		} else {
			down(i);
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				update(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static long query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return max[i];
		}
		down(i);
		int mid = (l + r) >> 1;
		long ans = Long.MIN_VALUE;
		if (jobl <= mid) {
			ans = Math.max(ans, query(jobl, jobr, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.max(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
		}
		return ans;
	}

	// 对数器逻辑
	// 展示了线段树的建立和使用
	// 使用验证结构来检查线段树是否正常工作
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
			// op = 0 更新操作
			// op = 1 查询操作
			int op = (int) (Math.random() * 2);
			// 下标从1开始，不从0开始，生成两个随机下标
			int a = (int) (Math.random() * n) + 1;
			int b = (int) (Math.random() * n) + 1;
			// 确保jobl <= jobr
			int jobl = Math.min(a, b);
			int jobr = Math.max(a, b);
			if (op == 0) {
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
		long ans = Long.MIN_VALUE;
		for (int i = jobl; i <= jobr; i++) {
			ans = Math.max(ans, check[i]);
		}
		return ans;
	}

}
