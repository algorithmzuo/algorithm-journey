package class127;

// 相乘为正或负的子数组数量
// 给定一个长度为n的数组arr，其中所有值都不是0
// 返回有多少个子数组相乘的结果是正
// 返回有多少个子数组相乘的结果是负
// 1 <= n <= 10^6
// -10^9 <= arr[i] <= +10^9，arr[i]一定不是0
// 来自真实大厂笔试，对数器验证
public class Code03_MultiplyPositiveNegative1 {

	// 正式方法
	public static int[] num(int[] arr) {
		// 0 : 正状态
		// 1 : 负状态
		// ^ : 乘法运算
		int[] cnt = new int[2];
		cnt[0] = 1;
		cnt[1] = 0;
		int ans1 = 0;
		int ans2 = 0;
		for (int i = 0, cur = 0; i < arr.length; i++) {
			cur ^= arr[i] > 0 ? 0 : 1;
			ans1 += cnt[cur];
			ans2 += cnt[cur ^ 1];
			cnt[cur]++;
		}
		return new int[] { ans1, ans2 };
	}

	// 暴力方法
	// 为了验证
	public static int[] right(int[] arr) {
		int n = arr.length;
		int ans1 = 0;
		int ans2 = 0;
		for (int i = 0; i < n; i++) {
			long cur = 1;
			for (int j = i; j < n; j++) {
				cur = cur * arr[j];
				if (cur > 0) {
					ans1++;
				} else {
					ans2++;
				}
			}
		}
		return new int[] { ans1, ans2 };
	}

	// 为了验证
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			do {
				ans[i] = (int) (Math.random() * v * 2) - v;
			} while (ans[i] == 0);
		}
		return ans;
	}

	public static void main(String[] args) {
		// 正式方法无所谓，怎么都正确
		// 但是对数器方法是暴力乘起来，所以n和v不要太大，防止溢出
		int n = 20;
		int v = 10;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int size = (int) (Math.random() * n);
			int[] arr = randomArray(size, v);
			int[] ans1 = num(arr);
			int[] ans2 = right(arr);
			if (ans1[0] != ans2[0] || ans1[1] != ans2[1]) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
