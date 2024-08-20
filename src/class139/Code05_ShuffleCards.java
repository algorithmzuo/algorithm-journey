package class139;

// 洗牌
// 一共有n张牌，n一定是偶数，牌的编号从1到n，洗牌规则如下
// 比如，n = 6，牌的编号为1 2 3 4 5 6
// 先分成左堆1 2 3，右堆4 5 6
// 然后按照右堆第i张在前，左堆第i张在后的方式，洗在一起
// 得到4 1 5 2 6 3，如果再洗一次，就得到2 4 6 1 3 5
// 想知道一共n张牌，洗m次的情况下，第l张牌在什么位置
// 1 <= n <= 10^10，n为偶数
// 0 <= m <= 10^10
// 测试链接 : https://www.luogu.com.cn/problem/P2054
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_ShuffleCards {

	// 扩展欧几里得算法
	public static long d, x, y, px, py;

	public static void exgcd(long a, long b) {
		if (b == 0) {
			d = a;
			x = 1;
			y = 0;
		} else {
			exgcd(b, a % b);
			px = x;
			py = y;
			x = py;
			y = px - py * (a / b);
		}
	}

	// 原理来自，讲解033，位运算实现乘法
	// a * b的过程自己实现，每一个中间过程都 % mod
	// 这么写目的是防止溢出
	public static long multiply(long a, long b, long mod) {
		long ans = 0;
		while (b != 0) {
			if ((b & 1) != 0) {
				ans = (ans + a) % mod;
			}
			a = (a + a) % mod;
			b >>= 1;
		}
		return ans;
	}

	// 原理来自，讲解098，乘法快速幂
	// 计算a的b次方，最终 % mod 的结果
	public static long power(long a, long b, long mod) {
		long ans = 1;
		while (b > 0) {
			if ((b & 1) == 1) {
				ans = multiply(ans, a, mod);
			}
			a = multiply(a, a, mod);
			b >>= 1;
		}
		return ans;
	}

	public static long compute(long n, long m, long l) {
		long mod = n + 1;
		exgcd(power(2, m, mod), mod);
		x = (x % mod + mod) % mod;
		return multiply(x, l, mod);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		long n = (long) in.nval;
		in.nextToken();
		long m = (long) in.nval;
		in.nextToken();
		long l = (long) in.nval;
		out.println(compute(n, m, l));
		out.flush();
		out.close();
		br.close();
	}

}
