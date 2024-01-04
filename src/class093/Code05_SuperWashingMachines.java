package class093;

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
		int avg = sum / n; // 每台洗衣机最终要求的衣服数量一定是平均值
		int leftSum = 0; // 左侧累加和
		int leftNeed = 0; // 左边还需要多少件衣服
		int rightNeed = 0;// 右边还需要多少件衣服
		int bottleNeck = 0;// 每一步的瓶颈
		int ans = 0;
		for (int i = 0; i < n; leftSum += arr[i], i++) {
			leftNeed = i * avg - leftSum;
			rightNeed = (n - i - 1) * avg - (sum - leftSum - arr[i]);
			if (leftNeed > 0 && rightNeed > 0) {
				bottleNeck = leftNeed + rightNeed;
			} else {
				bottleNeck = Math.max(Math.abs(leftNeed), Math.abs(rightNeed));
			}
			ans = Math.max(ans, bottleNeck);
		}
		return ans;
	}

}
