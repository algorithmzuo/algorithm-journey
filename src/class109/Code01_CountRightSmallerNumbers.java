package class109;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 计算右侧小于当前元素的个数
// 给你一个整数数组nums，按要求返回一个新数组counts
// counts[i]的值是nums[i]右侧小于nums[i]的元素的数量
// 测试链接 : https://leetcode.cn/problems/count-of-smaller-numbers-after-self/
public class Code01_CountRightSmallerNumbers {

	public static int MAXV = 10001;

	public static int MAXN = 100001;

	public static int[] tree = new int[MAXV << 1];

	public static int[] cnt = new int[MAXN];

	public static int n, m;

	public static void build() {
		Arrays.fill(tree, 1, m + 1, 0);
		Arrays.fill(cnt, 0, n, 0);
	}

	public static void add(int i) {
		while (i <= m) {
			tree[i]++;
			i += i & (-i);
		}
	}

	public static void set(int i, int v) {
		int ans = 0;
		while (v > 0) {
			ans += tree[v];
			v -= v & (-v);
		}
		cnt[i] = ans;
	}

	public List<Integer> countSmaller(int[] nums) {
		n = nums.length;
		int min = nums[0];
		int max = nums[0];
		for (int i = 1; i < n; i++) {
			min = Math.min(min, nums[i]);
			max = Math.max(max, nums[i]);
		}
		m = max - min + 1;
		build();
		for (int i = n - 1, num; i >= 0; i--) {
			num = nums[i] - min + 1;
			add(num);
			set(i, num - 1);
		}
		List<Integer> ans = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			ans.add(cnt[i]);
		}
		return ans;
	}

}
