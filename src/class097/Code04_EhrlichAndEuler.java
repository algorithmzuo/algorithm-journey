package class097;

// 计数质数
// 给定整数n，返回小于非负整数n的质数的数量
// 测试链接 : https://leetcode.cn/problems/count-primes/
public class Code04_EhrlichAndEuler {

	public static int countPrimes(int n) {
		return ehrlich(n - 1);
	}

	// 埃氏筛统计0 ~ n范围内的质数个数
	// 时间复杂度O(n * log(logn))
	public static int ehrlich(int n) {
		// visit[i] = true，代表i是合数
		// visit[i] = false，代表i是质数
		// 初始时认为0~n所有数都是质数
		boolean[] visit = new boolean[n + 1];
		for (int i = 2; i * i <= n; i++) {
			if (!visit[i]) {
				for (int j = i * i; j <= n; j += i) {
					visit[j] = true;
				}
			}
		}
		int cnt = 0;
		for (int i = 2; i <= n; i++) {
			if (!visit[i]) {
				// 此时i就是质数，可以收集，也可以计数
				cnt++;
			}
		}
		return cnt;
	}

	// 欧拉筛统计0 ~ n范围内的质数个数
	// 时间复杂度O(n)
	public static int euler(int n) {
		// visit[i] = true，代表i是合数
		// visit[i] = false，代表i是质数
		// 初始时认为0~n所有数都是质数
		boolean[] visit = new boolean[n + 1];
		// prime收集所有的质数，收集的个数是cnt
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

	// 只是计数的话
	// 埃氏筛还能改进
	public static int ehrlich2(int n) {
		if (n <= 1) {
			return 0;
		}
		// visit[i] = true，代表i是合数
		// visit[i] = false，代表i是质数
		// 初始时认为0~n所有数都是质数
		boolean[] visit = new boolean[n + 1];
		// 先把所有的偶数去掉，但是算上2
		// 估计的质数数量，如果发现更多合数，那么cnt--
		int cnt = (n + 1) / 2;
		for (int i = 3; i * i <= n; i += 2) {
			if (!visit[i]) {
				for (int j = i * i; j <= n; j += 2 * i) {
					if (!visit[j]) {
						visit[j] = true;
						cnt--;
					}
				}
			}
		}
		return cnt;
	}

}
