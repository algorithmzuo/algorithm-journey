package class146;

// 康托展开
// 数字从1到n，可以有很多排列，给出具体的一个排列，求该排列的名次，答案对 998244353 取模
// 1 <= n <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P5367
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_CantorExpansion {

	public static int MAXN = 1000001;

	public static int MOD = 998244353;

	public static int[] arr = new int[MAXN];

	// 阶乘余数表
	public static int[] fac = new int[MAXN];

	// 树状数组
	public static int[] tree = new int[MAXN];

	public static int n;

	public static int lowbit(int i) {
		return i & -i;
	}

	public static int sum(int i) {
		int ans = 0;
		while (i > 0) {
			ans = (ans + tree[i]) % MOD;
			i -= lowbit(i);
		}
		return ans;
	}

	public static void add(int i, int v) {
		while (i <= n) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static long compute() {
		fac[0] = 1;
		for (int i = 1; i < n; i++) {
			fac[i] = (int) ((long) fac[i - 1] * i % MOD);
		}
		for (int i = 1; i <= n; i++) {
			add(i, 1);
		}
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			ans = (ans + (long) sum(arr[i] - 1) * fac[n - i] % MOD) % MOD;
			add(arr[i], -1);
		}
		// 求的排名从0开始，但是题目要求从1开始，所以最后+1再返回
		ans = (ans + 1) % MOD;
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
