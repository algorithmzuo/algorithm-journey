package class095;

// 斐波那契博弈(Fibonacci Game + Zeckendorf定理)
// 一共有n枚石子，两位玩家定了如下规则进行游戏：
// 先手后手轮流取石子，先手在第一轮可以取走任意的石子
// 接下来的每一轮当前的玩家最少要取走一个石子，最多取走上一次取的数量的2倍
// 当然，玩家取走的数量必须不大于目前场上剩余的石子数量，双方都以最优策略取石子
// 你也看出来了，根据规律先手一定会获胜，但是先手想知道
// 第一轮自己取走至少几颗石子就可以保证获胜了
// 测试链接 : https://www.luogu.com.cn/problem/P6487
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_FibonacciGame {

	public static long MAXN = 1000000000000000L;

	public static int MAXM = 101;

	public static long[] f = new long[MAXM];

	public static int size;

	public static void build() {
		f[0] = 1;
		f[1] = 2;
		size = 1;
		while (f[size] <= MAXN) {
			f[size + 1] = f[size] + f[size - 1];
			size++;
		}
	}

	public static void main(String[] args) throws IOException {
		build();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			out.println(compute((long) in.nval));
		}
		out.flush();
		out.close();
		br.close();
	}

	public static long compute(long n) {
		long ans = -1, find;
		while (n != 1 && n != 2) {
			find = bs(n);
			if (n == find) {
				ans = find;
				break;
			} else {
				n -= find;
			}
		}
		if (ans != -1) {
			return ans;
		} else {
			return n;
		}
	}

	public static long bs(long n) {
		int l = 0;
		int r = size;
		int m;
		long ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (f[m] <= n) {
				ans = f[m];
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

}
