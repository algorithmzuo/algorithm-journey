package class141;

// 屠龙勇士
// 一共n只巨龙，每只巨龙都有初始血量hp[i]，每只巨龙都有恢复能力recovery[i]
// 每只巨龙都会在攻击结束后开始恢复，初始一共m把剑，每把剑攻击力init[i]
// 每只巨龙只有当血量恰好为0时，才能被杀死。面对某只具体的龙，只能用固定的剑来攻击，规定如下：
// 攻击力不高于当前巨龙的血量，并且攻击力最大的一把剑，如果没有这样的剑，就选择攻击力最低的一把剑
// 需要按1~n的顺序依次讨伐巨龙，i号巨龙被杀后，那把攻击的剑会消失，同时奖励攻击力reward[i]的剑
// 勇士制定的策略如下，不管面对什么巨龙，攻击过程只打击ans下，让当前巨龙的血量<=0
// 然后在当前巨龙恢复的过程中，如果血量恰好为0，那么当前巨龙被杀死，勇士继续讨伐下一只
// 你的任务是算出最小的ans，让勇士可以在该策略下杀死所有巨龙
// 如果在固定打击次数的策略下，就是无法杀死所有巨龙，返回-1
// 查看数据范围可以打开测试链接 : https://www.luogu.com.cn/problem/P4774
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

	public static long[] init = new long[MAXN];

	public static long[] attack = new long[MAXN];

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

	// 每只怪物根据血量找到攻击的剑
	// 哪只龙需要砍最多次，才能让其血量<=0，返回最多的次数
	public static long allocate(int n, int m) {
		sorted.clear();
		for (int i = 1; i <= m; i++) {
			sorted.put(init[i], sorted.getOrDefault(init[i], 0) + 1);
		}
		long max = 0;
		for (int i = 1; i <= n; i++) {
			Long sword = sorted.floorKey(hp[i]);
			if (sword == null) {
				sword = sorted.firstKey();
			}
			attack[i] = sword;
			sorted.put(sword, sorted.get(sword) - 1);
			if (sorted.get(sword) == 0) {
				sorted.remove(sword);
			}
			sorted.put(reward[i], sorted.getOrDefault(reward[i], 0) + 1);
			max = Math.max(max, (hp[i] + attack[i] - 1) / attack[i]);
			// 血量 = 血量 % 恢复力，变成余数形式
			hp[i] %= recovery[i];
		}
		return max;
	}

	// bi * ans ≡ ri(% mi)方程组求解 + 本题对解的特殊处理
	public static long compute(int n, int m) {
		// max变量很关键，最后的逻辑需要用到
		// 哪只龙需要砍最多次，才能让其血量<=0，这个最多的次数就是max
		long max = allocate(n, m);
		long tail = 0, lcm = 1, tmp, a, b, c, x0;
		// ans = lcm * x + tail
		for (int i = 1; i <= n; i++) {
			// ai * ans = ai * lcm * x + tail * ai  1号方程
			// ai * ans = ri * y + hi               2号方程
			// ai * lcm * x + ri * y = hi - tail * ai
			a = multiply(attack[i], lcm, recovery[i]);
			b = recovery[i];
			c = ((hp[i] - attack[i] * tail) % b + b) % b;
			exgcd(a, b);
			if (c % d != 0) {
				return -1;
			}
			x0 = multiply(x, c / d, b / d);
			tmp = lcm * (b / d);
			tail = (tail + multiply(x0, lcm, tmp)) % tmp;
			lcm = tmp;
		}
		// 通解 ans = ? * lcm + tail
		// 下面属于本题的特殊处理，注意max变量的含义
		// 上面的大思路是，对每只怪兽，根据如下的公式，整理出同余式
		// ans * a[i] = h[i] + 每只怪兽若干恢复次数 * r[i]
		// 同余式为，ans * a[i] ≡ h[i] (% r[i])
		// 注意！能建立起的同余式，需要默认"每只怪兽若干恢复次数"的范围是整数
		// 最终解出，ans = k * lcm + tail，tail是最小正数解
		// 但实际情况是，"每只怪兽若干恢复次数"毫无疑问是非负的，并不是整个整数域
		// 也就是说，需要确保把每只怪兽的血量砍到<=0，然后才能保证，每只怪兽若干恢复次数>=0
		// 也就是说，ans = k * lcm + tail，需要确保，ans >= max，注意max变量的含义！
		// 如果tail >= max，那么答案就是tail，此时k==0
		// 如果tail < max，想保证ans >= max，其实就是tail + k * lcm >= max
		// k = (max - tail) / lcm，向上取整，也就是k = (max - tail + lcm - 1) / lcm
		// 所以，ans = (max - tail + lcm - 1) / lcm * lcm + tail
		long ans;
		if (tail >= max) {
			ans = tail;
		} else {
			ans = (max - tail + lcm - 1) / lcm * lcm + tail;
		}
		return ans;
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
				init[i] = (long) in.nval;
			}
			out.println(compute(n, m));
		}
		out.flush();
		out.close();
		br.close();
	}

}
