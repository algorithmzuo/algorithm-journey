package code;

import java.util.HashSet;

// 数组中两个数的最大异或值
// leetcode的测试数据规定 : 数组中都是非负数
// 测试链接 : https://leetcode.cn/problems/maximum-xor-of-two-numbers-in-an-array/
public class Video_038_5_FindMaximumXor {

	public static int MAXN = 3000001;

	public static int[][] tree = new int[MAXN][2];

	// 前缀树目前使用了多少空间
	public static int cnt;

	// 数字只需要从哪一位开始考虑
	public static int left;

	public static void build(int[] nums) {
		cnt = 1;
		int max = Integer.MIN_VALUE;
		for (int num : nums) {
			max = Math.max(num, max);
		}
		// 计算数组最大值的二进制状态，有多少个前缀的0
		// 可以忽略这些前置的0，从left位开始考虑
		left = 31 - Integer.numberOfLeadingZeros(max);
	}

	public static void insert(int num) {
		int cur = 1;
		for (int i = left, path; i >= 0; i--) {
			path = (num >> i) & 1;
			if (tree[cur][path] == 0) {
				tree[cur][path] = ++cnt;
			}
			cur = tree[cur][path];
		}
	}

	public static int maxXor(int num) {
		int ans = 0;
		int cur = 1;
		for (int i = left, status, want; i >= 0; i--) {
			status = (num >> i) & 1;
			// 虽然测试数据一定不包含负数
			// 但是这一句是按照数组可能有正、负、0这种情况来设计的
			// 也就是数组即便有正、负、0，也能算对正确的结果
			want = i == 31 ? status : (status ^ 1);
			if (tree[cur][want] == 0) {
				want ^= 1;
			}
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

	// 测试数据规定nums一定不包含负数
	// 返回两个数的最大异或值
	// 但是这个方法是随便nums有没有负数，都能返回正确的结果
	public static int findMaximumXOR1(int[] nums) {
		build(nums);
		int ans = 0;
		for (int num : nums) {
			insert(num);
			ans = Math.max(ans, maxXor(num));
		}
		clear();
		return ans;
	}

	// 用哈希表的做法，运行时间很快
	// 但这种方法其实有着很大的问题
	// 具体请看讲解019-算法笔试中处理输入和输出，讲了为什么不推荐笔试、比赛时使用动态空间
	// 笔试、比赛时，使用动态空间的办法可能会被判定空间占用过大，导致不能通过
	public int findMaximumXOR2(int[] nums) {
		int max = Integer.MIN_VALUE;
		for (int num : nums) {
			max = Math.max(num, max);
		}
		int ans = 0;
		HashSet<Integer> set = new HashSet<>();
		for (int i = 31 - Integer.numberOfLeadingZeros(max); i >= 0; i--) {
			int better = i == 31 ? 0 : (ans | (1 << i));
			set.clear();
			for (int num : nums) {
				num = (num >> i) << i;
				set.add(num);
				if (set.contains(better ^ num)) {
					ans = better;
					break;
				}
			}
		}
		return ans;
	}

}