package class147;

// 不同的二叉搜索树
// 测试链接 : https://leetcode.cn/problems/unique-binary-search-trees/
public class Code07_UniqueTrees {

	// 数据量小用哪个公式都可以
	// 不用考虑溢出、取模等问题
	// 这里用公式4
	public static int numTrees(int n) {
		int[] catalan = new int[n + 1];
		catalan[0] = catalan[1] = 1;
		for (int i = 2; i <= n; i++) {
			for (int l = 0, r = i - 1; l < i; l++, r--) {
				catalan[i] += catalan[l] * catalan[r];
			}
		}
		return catalan[n];
	}

}
