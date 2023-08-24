package class045;

import java.util.HashSet;

// 数组中两个数的最大异或值
// 给你一个整数数组 nums ，返回 nums[i] XOR nums[j] 的最大运算结果，其中 0<=i<=j<=n
// 1 <= nums.length <= 2 * 10^5
// 0 <= nums[i] <= 2^31 - 1
// 测试链接 : https://leetcode.cn/problems/maximum-xor-of-two-numbers-in-an-array/
public class Code02_TwoNumbersMaximumXor {

	// 前缀树的做法
	// 好想
	public static int findMaximumXOR1(int[] nums) {
		build(nums);
		int ans = 0;
		for (int num : nums) {
			ans = Math.max(ans, maxXor(num));
		}
		clear();
		return ans;
	}

	// 准备这么多静态空间就够了，实验出来的
	// 如果测试数据升级了规模，就改大这个值
	public static int MAXN = 3000001;

	public static int[][] tree = new int[MAXN][2];

	// 前缀树目前使用了多少空间
	public static int cnt;

	// 数字只需要从哪一位开始考虑
	public static int high;

	public static void build(int[] nums) {
		cnt = 1;
		// 找个最大值
		int max = Integer.MIN_VALUE;
		for (int num : nums) {
			max = Math.max(num, max);
		}
		// 计算数组最大值的二进制状态，有多少个前缀的0
		// 可以忽略这些前置的0，从left位开始考虑
		high = 31 - Integer.numberOfLeadingZeros(max);
		for (int num : nums) {
			insert(num);
		}
	}

	public static void insert(int num) {
		int cur = 1;
		for (int i = high, path; i >= 0; i--) {
			path = (num >> i) & 1;
			if (tree[cur][path] == 0) {
				tree[cur][path] = ++cnt;
			}
			cur = tree[cur][path];
		}
	}

	public static int maxXor(int num) {
		// 最终异或的结果(尽量大)
		int ans = 0;
		// 前缀树目前来到的节点编号
		int cur = 1;
		for (int i = high, status, want; i >= 0; i--) {
			// status : num第i位的状态
			status = (num >> i) & 1;
			// want : num第i位希望遇到的状态
			want = status ^ 1;
			if (tree[cur][want] == 0) { // 询问前缀树，能不能达成
				// 不能达成
				want ^= 1;
			}
			// want变成真的往下走的路
			ans |= (status ^ want) << i;
			cur = tree[cur][want];
		}
		return ans;
	}

	public static void clear() {
		for (int i = 1; i <= cnt; i++) {
			tree[i][0] = tree[i][1] = 0;
		}
	}

	// 用哈希表的做法
	// 难想
	public int findMaximumXOR2(int[] nums) {
		int max = Integer.MIN_VALUE;
		for (int num : nums) {
			max = Math.max(num, max);
		}
		int ans = 0;
		HashSet<Integer> set = new HashSet<>();
		for (int i = 31 - Integer.numberOfLeadingZeros(max); i >= 0; i--) {
			// ans : 31....i+1 已经达成的目标
			int better = ans | (1 << i);
			set.clear();
			for (int num : nums) {
				// num : 31.....i 这些状态保留，剩下全成0
				num = (num >> i) << i;
				set.add(num);
				// num ^ 某状态 是否能 达成better目标，就在set中找 某状态 : better ^ num
				if (set.contains(better ^ num)) {
					ans = better;
					break;
				}
			}
		}
		return ans;
	}

}