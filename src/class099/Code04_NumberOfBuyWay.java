package class099;

// 多次查询购买方法
// 一共有4种硬币，面值分别为v0、v1、v2、v3，这个永远是确定的
// 每次去购物的细节由一个数组arr来表示，每次购物都是一次查询
// arr[0] = 携带v0面值的硬币数量
// arr[1] = 携带v1面值的硬币数量
// arr[2] = 携带v2面值的硬币数量
// arr[3] = 携带v3面值的硬币数量
// arr[4] = 本次购物一定要花多少钱
// 返回每次有多少种花钱的方法
// 1 <= v0、v1、v2、v3、arr[i] <= 10^5
// 查询数量 <= 1000
// 测试链接 : https://www.luogu.com.cn/problem/P1450
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_NumberOfBuyWay {

	public static int LIMIT = 100000;

	public static long[] dp = new long[LIMIT + 1];

	public static int[] v = new int[4];

	public static int[] arr = new int[4];

	public static int n, s;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			v[0] = (int) in.nval;
			in.nextToken(); v[1] = (int) in.nval;
			in.nextToken(); v[2] = (int) in.nval;
			in.nextToken(); v[3] = (int) in.nval;
			in.nextToken(); n = (int) in.nval;
			build();
			for (int i = 0; i < n; i++) {
				in.nextToken(); arr[0] = (int) in.nval;
				in.nextToken(); arr[1] = (int) in.nval;
				in.nextToken(); arr[2] = (int) in.nval;
				in.nextToken(); arr[3] = (int) in.nval;
				in.nextToken(); s = (int) in.nval;
				out.println(query());
			}
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void build() {
		dp[0] = 1;
		for (int i = 0; i <= 3; i++) {
			for (int j = v[i]; j <= LIMIT; j++) {
				dp[j] += dp[j - v[i]];
			}
		}
	}

	public static long query() {
		long minus = 0;
		for (int status = 1; status <= 15; status++) {
			long t = s;
			int sign = -1;
			for (int j = 0; j <= 3; j++) {
				if (((status >> j) & 1) == 1) {
					t -= v[j] * (arr[j] + 1);
					sign = -sign;
				}
			}
			if (t >= 0) {
				minus += dp[(int) t] * sign;
			}
		}
		return dp[s] - minus;
	}

}
