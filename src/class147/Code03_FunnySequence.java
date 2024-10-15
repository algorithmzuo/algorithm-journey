package class147;

// 有趣的数列(重要！质因子计数法)
// 测试链接 : https://www.luogu.com.cn/problem/P3200
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_FunnySequence {

	public static int MAXN = 2000001;

	public static boolean[] visit = new boolean[MAXN];

	public static int[] prime = new int[MAXN];

	public static int cnt;

	// 如果minpf[i] == 0，代表i是质数
	// 如果minpf[i] != 0，代表i的最小质因子是minpf[i]
	public static int[] minpf = new int[MAXN];

	public static int[] counts = new int[MAXN];

	// 来自讲解097，欧拉筛，时间复杂度O(n)
	public static void euler(int n) {
		Arrays.fill(visit, 1, n, false);
		cnt = 0;
		for (int i = 2; i <= n; i++) {
			if (!visit[i]) {
				prime[++cnt] = i;
			}
			for (int j = 1; j <= cnt; j++) {
				if (i * prime[j] > n) {
					break;
				}
				visit[i * prime[j]] = true;
				// 讲解097重点解释了欧拉筛的过程，看完必懂
				// 此时可以收集(i * prime[j])这个数的最小质因子为prime[j]
				minpf[i * prime[j]] = prime[j];
				if (i % prime[j] == 0) {
					break;
				}
			}
		}
	}

	public static long power(long x, long p, int mod) {
		long ans = 1;
		while (p > 0) {
			if ((p & 1) == 1) {
				ans = (ans * x) % mod;
			}
			x = (x * x) % mod;
			p >>= 1;
		}
		return ans;
	}

	// 使用的是公式2 + 质因子计数法
	public static int compute(int n, int mod) {
		euler(2 * n);
		Arrays.fill(counts, 2, n + 1, -1);
		Arrays.fill(counts, n + 2, 2 * n + 1, 1);
		for (int i = 2 * n; i >= 2; i--) {
			if (minpf[i] != 0) {
				counts[minpf[i]] += counts[i];
				counts[i / minpf[i]] += counts[i];
				counts[i] = 0;
			}
		}
		long ans = 1;
		for (int i = 2; i <= 2 * n; i++) {
			if (counts[i] != 0) {
				ans = ans * power(i, counts[i], mod) % mod;
			}
		}
		return (int) ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int mod = (int) in.nval;
		out.println(compute(n, mod));
		out.flush();
		out.close();
		br.close();
	}

}
