package class114;

// 线段树范围增加操作 + 区间最值操作
// 给定一个长度为n的数组arr，实现支持以下四种操作的结构
// 操作 0 l r x : 把arr[l..r]范围的每个数v，增加x
// 操作 1 l r x : 把arr[l..r]范围的每个数v，更新成min(v, x)
// 操作 2 l r   : 查询arr[l..r]范围上的最大值
// 操作 3 l r   : 查询arr[l..r]范围上的累加和
// 对数器验证

public class Code04_SegmentTreeAddSetminQueryMaxSum {

	public static int MAXN = 500001;

	public static long LOWEST = Long.MIN_VALUE;

	// 原始数组
	public static int[] arr = new int[MAXN];

	// 累加和信息(查询信息)
	public static long[] sum = new long[MAXN << 2];

	// 最大值信息(只是查询信息，不再是懒更新信息，懒更新功能被maxAdd数组替代了)
	public static long[] max = new long[MAXN << 2];

	// 最大值个数(查询信息)
	public static int[] cnt = new int[MAXN << 2];

	// 严格次大值(查询信息)
	public static long[] sem = new long[MAXN << 2];

	// 最大值的增加幅度(懒更新信息)
	public static long[] maxAdd = new long[MAXN << 2];

	// 除最大值以外其他数字的增加幅度(懒更新信息)
	public static long[] otherAdd = new long[MAXN << 2];

	public static void up(int i) {
		int l = i << 1;
		int r = i << 1 | 1;
		sum[i] = sum[l] + sum[r];
		max[i] = Math.max(max[l], max[r]);
		if (max[l] > max[r]) {
			cnt[i] = cnt[l];
			sem[i] = Math.max(sem[l], max[r]);
		} else if (max[l] < max[r]) {
			cnt[i] = cnt[r];
			sem[i] = Math.max(max[l], sem[r]);
		} else {
			cnt[i] = cnt[l] + cnt[r];
			sem[i] = Math.max(sem[l], sem[r]);
		}
	}

	public static void lazy(int i, int n, long maxAddv, long otherAddv) {
		sum[i] += maxAddv * cnt[i] + otherAddv * (n - cnt[i]);
		max[i] += maxAddv;
		sem[i] += sem[i] == LOWEST ? 0 : otherAddv;
		maxAdd[i] += maxAddv;
		otherAdd[i] += otherAddv;
	}

	public static void down(int i, int ln, int rn) {
		int l = i << 1;
		int r = i << 1 | 1;
		// 为什么拿全局最大值不写成 : tmp = max[i]
		// 因为父亲范围的最大值可能已经被懒更新任务修改过了
		// 现在希望拿的是懒更新之前的最大值
		// 子范围的max值没有修改过，所以写成 : tmp = Math.max(max[l], max[r])
		long tmp = Math.max(max[l], max[r]);
		if (max[l] == tmp) {
			lazy(l, ln, maxAdd[i], otherAdd[i]);
		} else {
			lazy(l, ln, otherAdd[i], otherAdd[i]);
		}
		if (max[r] == tmp) {
			lazy(r, rn, maxAdd[i], otherAdd[i]);
		} else {
			lazy(r, rn, otherAdd[i], otherAdd[i]);
		}
		maxAdd[i] = otherAdd[i] = 0;
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			sum[i] = max[i] = arr[l];
			sem[i] = LOWEST;
			cnt[i] = 1;
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
		maxAdd[i] = otherAdd[i] = 0;
	}

	public static void add(int jobl, int jobr, long jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			lazy(i, r - l + 1, jobv, jobv);
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

	public static void setMin(int jobl, int jobr, long jobv, int l, int r, int i) {
		if (jobv >= max[i]) {
			return;
		}
		if (jobl <= l && r <= jobr && sem[i] < jobv) {
			lazy(i, r - l + 1, jobv - max[i], 0);
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			if (jobl <= mid) {
				setMin(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				setMin(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static long querySum(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return sum[i];
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			long ans = 0;
			if (jobl <= mid) {
				ans += querySum(jobl, jobr, l, mid, i << 1);
			}
			if (jobr > mid) {
				ans += querySum(jobl, jobr, mid + 1, r, i << 1 | 1);
			}
			return ans;
		}
	}

	public static long queryMax(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return max[i];
		} else {
			int mid = (l + r) >> 1;
			down(i, mid - l + 1, r - mid);
			Long ans = Long.MIN_VALUE;
			if (jobl <= mid) {
				ans = Math.max(ans, queryMax(jobl, jobr, l, mid, i << 1));
			}
			if (jobr > mid) {
				ans = Math.max(ans, queryMax(jobl, jobr, mid + 1, r, i << 1 | 1));
			}
			return ans;
		}
	}

	public static void main(String[] args) {
		System.out.println("测试开始");
		int n = 2000;
		int v = 5000;
		int t = 1000000;
		randomArray(n, v);
		long[] check = new long[n + 1];
		for (int i = 1; i <= n; i++) {
			check[i] = arr[i];
		}
		build(1, n, 1);
		for (int i = 1, op, a, b, jobl, jobr, jobv; i <= t; i++) {
			op = (int) (Math.random() * 4);
			a = (int) (Math.random() * n) + 1;
			b = (int) (Math.random() * n) + 1;
			jobl = Math.min(a, b);
			jobr = Math.max(a, b);
			if (op == 0) {
				jobv = (int) (Math.random() * v * 2) - v;
				add(jobl, jobr, jobv, 1, n, 1);
				checkAdd(check, jobl, jobr, jobv);
			} else if (op == 1) {
				jobv = (int) (Math.random() * v * 2) - v;
				setMin(jobl, jobr, jobv, 1, n, 1);
				checkSetMin(check, jobl, jobr, jobv);
			} else if (op == 2) {
				long ans1 = queryMax(jobl, jobr, 1, n, 1);
				long ans2 = checkQueryMax(check, jobl, jobr);
				if (ans1 != ans2) {
					System.out.println("出错了!");
				}
			} else {
				long ans1 = querySum(jobl, jobr, 1, n, 1);
				long ans2 = checkQuerySum(check, jobl, jobr);
				if (ans1 != ans2) {
					System.out.println("出错了!");
				}
			}
		}
		System.out.println("测试结束");
	}

	// 为了验证
	public static void randomArray(int n, int v) {
		for (int i = 1; i <= n; i++) {
			arr[i] = (int) (Math.random() * v * 2) - v;
		}
	}

	// 为了验证
	public static void checkAdd(long[] check, int jobl, int jobr, long jobv) {
		for (int i = jobl; i <= jobr; i++) {
			check[i] += jobv;
		}
	}

	// 为了验证
	public static void checkSetMin(long[] check, int jobl, int jobr, long jobv) {
		for (int i = jobl; i <= jobr; i++) {
			check[i] = Math.min(check[i], jobv);
		}
	}

	// 为了验证
	public static long checkQueryMax(long[] check, int jobl, int jobr) {
		long ans = Long.MIN_VALUE;
		for (int i = jobl; i <= jobr; i++) {
			ans = Math.max(ans, check[i]);
		}
		return ans;
	}

	// 为了验证
	public static long checkQuerySum(long[] check, int jobl, int jobr) {
		long ans = 0;
		for (int i = jobl; i <= jobr; i++) {
			ans += check[i];
		}
		return ans;
	}

}
