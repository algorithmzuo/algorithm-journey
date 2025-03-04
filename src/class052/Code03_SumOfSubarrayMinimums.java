package class052;

// 子数组的最小值之和
// 给定一个整数数组 arr，找到 min(b) 的总和，其中 b 的范围为 arr 的每个（连续）子数组。
// 由于答案可能很大，答案对 1000000007 取模
// 测试链接 : https://leetcode.cn/problems/sum-of-subarray-minimums/
public class Code03_SumOfSubarrayMinimums {

	public static int MOD = 1000000007;

	public static int MAXN = 30001;

	public static int[] stack = new int[MAXN];

	public static int r;

	public static int sumSubarrayMins(int[] arr) {
		long ans = 0;
		r = 0;
		// 注意课上讲的相等情况的修正
		for (int i = 0; i < arr.length; i++) {
            //todo 栈 从小到大 这里相等时弹出或者不弹出都可以
			while (r > 0 && arr[stack[r - 1]] >= arr[i]) {
                //todo 当前值是栈顶元素(作为最小值时)
				int cur = stack[--r];
                //todo 找到左侧小的下标为边界值(作为比当前值小的值)
				int left = r == 0 ? -1 : stack[r - 1];
                //todo cur-left为从cur+1到left的值做开头的数量，i-cur为从i+1到cur为结尾的数量，相乘为以cur为最小值时的数组数量。
				ans = (ans + (long) (cur - left) * (i - cur) * arr[cur]) % MOD;
			}
			stack[r++] = i;
		}
		while (r > 0) {
			int cur = stack[--r];
			int left = r == 0 ? -1 : stack[r - 1];
			ans = (ans + (long) (cur - left) * (arr.length - cur) * arr[cur]) % MOD;
		}
		return (int) ans;
	}

}
