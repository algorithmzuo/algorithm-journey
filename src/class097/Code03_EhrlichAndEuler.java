package class097;

// 计数质数
// 给定整数n，返回小于非负整数n的质数的数量
// 测试链接 : https://leetcode.cn/problems/count-primes/
public class Code03_EhrlichAndEuler {

	public static int countPrimes(int n) {
		return ehrlich(n - 1);
	}

	// 埃氏筛统计0 ~ n范围内的质数个数
	// 时间复杂度O(n * log(logn))
	public static int ehrlich(int n) {
		boolean[] visit = new boolean[n + 1];
		for (int i = 2; i * i <= n; i++) {
			if (!visit[i]) {
				for (int j = i * i; j <= n; j += i) {
					visit[j] = true;
				}
			}
		}
		int ans = 0;
		for (int i = 2; i <= n; i++) {
			if (!visit[i]) {
				ans++;
			}
		}
		return ans;
	}

	// 欧拉筛统计0 ~ n范围内的质数个数
	// 时间复杂度O(n)
	public static int euler(int n) {
		boolean[] visit = new boolean[n + 1];
		int[] prime = new int[n / 2 + 1];
		int cnt = 0;
		for (int i = 2; i <= n; i++) {
			if (!visit[i]) {
				prime[cnt++] = i;
			}
			for (int j = 0; j < cnt; j++) {
				if (i * prime[j] > n) {
					break;
				}
				visit[i * prime[j]] = true;
				if (i % prime[j] == 0) {
					break;
				}
			}
		}
		return cnt;
	}

}
