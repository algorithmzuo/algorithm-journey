package class097;

import java.util.Arrays;

// 按公因数计算最大组件大小
// 给定一个由不同正整数的组成的非空数组 nums
// 如果 nums[i] 和 nums[j] 有一个大于1的公因子，那么这两个数之间有一条无向边
// 返回 nums中最大连通组件的大小。
// 测试链接 : https://leetcode.cn/problems/largest-component-size-by-common-factor/
public class Code04_LargestComponentSize {

	public static int MAXV = 100001;

	public static int MAXN = 100001;

	public static int[] factors = new int[MAXV];

	public static int[] father = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int n;

	public static void build() {
		for (int i = 0; i < n; i++) {
			father[i] = i;
			size[i] = 1;
		}
		Arrays.fill(factors, -1);
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static void union(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (fx != fy) {
			father[fx] = fy;
			size[fy] += size[fx];
		}
	}

	public static int maxSize() {
		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans = Math.max(ans, size[i]);
		}
		return ans;
	}

	public static int largestComponentSize(int[] arr) {
		n = arr.length;
		build();
		for (int i = 0, x; i < n; i++) {
			x = arr[i];
			for (int j = 2; j * j <= x; j++) {
				if (x % j == 0) {
					if (factors[j] == -1) {
						factors[j] = i;
					} else {
						union(factors[j], i);
					}
					while (x % j == 0) {
						x /= j;
					}
				}
			}
			if (factors[x] != -1) {
				union(factors[x], i);
			} else {
				if (x > 1) {
					factors[x] = i;
				}
			}
		}
		return maxSize();
	}

}
