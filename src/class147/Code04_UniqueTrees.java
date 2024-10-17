package class147;

// 不同结构的二叉树数量
// 一共有n个节点，认为节点之间无差别，返回能形成多少种不同结构的二叉树
// 1 <= n <= 19
// 测试链接 : https://leetcode.cn/problems/unique-binary-search-trees/
public class Code04_UniqueTrees {

	// 数据量小用哪个公式都可以
	// 不用考虑溢出、取模等问题
	// 同时注意到n的范围并不大，直接使用公式4
	public static int numTrees(int n) {
		int[] f = new int[n + 1];
		f[0] = f[1] = 1;
		for (int i = 2; i <= n; i++) {
			for (int l = 0, r = i - 1; l < i; l++, r--) {
				f[i] += f[l] * f[r];
			}
		}
		return f[n];
	}

}
