package class092;

// 超级洗衣机
// 假设有n台超级洗衣机放在同一排上
// 开始的时候，每台洗衣机内可能有一定量的衣服，也可能是空的
// 在每一步操作中，你可以选择任意 m (1 <= m <= n) 台洗衣机
// 与此同时将每台洗衣机的一件衣服送到相邻的一台洗衣机
// 给定一个整数数组machines代表从左至右每台洗衣机中的衣物数量
// 请给出能让所有洗衣机中剩下的衣物的数量相等的最少的操作步数
// 如果不能使每台洗衣机中衣物的数量相等则返回-1
// 测试链接 : https://leetcode.cn/problems/super-washing-machines/
public class Code05_SuperWashingMachines {

	public static int findMinMoves(int[] arr) {
		int n = arr.length;
		int sum = 0;
		for (int i = 0; i < n; i++) {
			sum += arr[i];
		}
		if (sum % n != 0) {
			return -1;
		}
		int avg = sum / n;
		int ans = 0;
		for (int i = 0, leftSum = 0, left, right; i < n; i++) {
			left = leftSum - i * avg;
			right = (sum - leftSum - arr[i]) - (n - i - 1) * avg;
			if (left < 0 && right < 0) {
				ans = Math.max(ans, Math.abs(left) + Math.abs(right));
			} else {
				ans = Math.max(ans, Math.max(Math.abs(left), Math.abs(right)));
			}
			leftSum += arr[i];
		}
		return ans;
	}

}
