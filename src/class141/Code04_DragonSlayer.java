package class141;

// 屠龙勇士
// 测试链接 : https://www.luogu.com.cn/problem/P4774
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.TreeMap;

public class Code04_DragonSlayer {

	public static int MAXN = 100001;

	public static long[] hp = new long[MAXN];

	public static long[] recovery = new long[MAXN];

	public static long[] reward = new long[MAXN];

	public static long[] attack = new long[MAXN];

	public static long[] choose = new long[MAXN];

	public static TreeMap<Long, Integer> sorted = new TreeMap<>();

	// 讲解139 - 扩展欧几里得算法
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

	// 讲解033 - 位运算实现乘法
	// a*b过程每一步都%mod，这么写是防止溢出，也叫龟速乘
	public static long multiply(long a, long b, long mod) {
		a = (a % mod + mod) % mod;
		b = (b % mod + mod) % mod;
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

	// 给每个怪物分配剑
	public static void allocate(int n, int m) {
		sorted.clear();
		for (int i = 1; i <= m; i++) {
			sorted.put(attack[i], sorted.getOrDefault(attack[i], 0) + 1);
		}
		for (int i = 1; i <= n; i++) {
			Long sword = sorted.floorKey(hp[i]);
			if (sword == null) {
				sword = sorted.firstKey();
			}
			choose[i] = sword;
			sorted.put(sword, sorted.get(sword) - 1);
			if (sorted.get(sword) == 0) {
				sorted.remove(sword);
			}
			sorted.put(reward[i], sorted.getOrDefault(reward[i], 0) + 1);
		}
	}

	public static long compute(int n, int m) {
		allocate(n, m);
		long ans = 0, all = 1, a, b, c;
		for (int i = 1; i <= n; i++) {
			if (recovery[i] == 1) {
				ans = Math.max(ans, hp[i] / choose[i] + (hp[i] % choose[i] == 0 ? 0 : 1));
			} else {
				a = multiply(choose[i], all, recovery[i]);
				b = recovery[i];
				c = (hp[i] - multiply(choose[i], ans, recovery[i]) + recovery[i]) % recovery[i];
				exgcd(a, b);
				if (c % d != 0) {
					return -1;
				}
				b /= d;
				x = multiply(x, c / d, b);
				ans = (ans + multiply(x, all, all * b)) % (all * b);
				all *= b;
			}
		}
		return (ans != 0 ? ans : all);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int cases = (int) in.nval;
		for (int t = 1; t <= cases; t++) {
			in.nextToken();
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				hp[i] = (long) in.nval;
			}
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				recovery[i] = (long) in.nval;
			}
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				reward[i] = (long) in.nval;
			}
			for (int i = 1; i <= m; i++) {
				in.nextToken();
				attack[i] = (long) in.nval;
			}
			out.println(compute(n, m));
		}
		out.flush();
		out.close();
		br.close();
	}

}
