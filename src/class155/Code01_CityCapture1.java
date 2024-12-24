package class155;

// 城池攻占，java版
// 一共有n个城市，1号城市是城市树的头，每个城市都有防御值、上级城市编号、奖励类型、奖励值
// 如果奖励类型为0，任何骑士攻克这个城市后，攻击力会加上奖励值
// 如果奖励类型为1，任何骑士攻克这个城市后，攻击力会乘以奖励值
// 任何城市的上级编号 < 这座城市的编号，1号城市没有上级城市编号、奖励类型、奖励值
// 一共有m个骑士，每个骑士都有攻击力、第一次攻击的城市
// 如果骑士攻击力 >= 城市防御值，当前城市会被攻占，骑士获得奖励，继续攻击上级城市
// 如果骑士攻击力  < 城市防御值，那么骑士会在该城市牺牲，没有后续动作了
// 所有骑士都是独立的，不会影响其他骑士攻击这座城池的结果
// 打印每个城市牺牲的骑士数量，打印每个骑士攻占的城市数量
// 1 <= n、m <= 3 * 10^5，攻击值的增加也不会超过long类型范围
// 测试链接 : https://www.luogu.com.cn/problem/P3261
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.PrintWriter;

public class Code01_CityCapture1 {

	public static int MAXN = 300001;

	public static int n, m;

	// 城市防御值
	public static long[] defend = new long[MAXN];

	// 上级城市编号
	public static int[] belong = new int[MAXN];

	// 奖励类型
	public static int[] type = new int[MAXN];

	// 奖励值
	public static long[] gain = new long[MAXN];

	// 骑士攻击力
	public static long[] attack = new long[MAXN];

	// 骑士第一次攻击的城市
	public static int[] first = new int[MAXN];

	// 城市在城市树中的深度
	public static int[] deep = new int[MAXN];

	// 城市拥有的骑士列表，用小根堆左偏树组织，最弱的骑士是头
	public static int[] top = new int[MAXN];

	// 每个城市牺牲人数统计
	public static int[] sacrifice = new int[MAXN];

	// 每个骑士死在了什么城市
	public static int[] die = new int[MAXN];

	// 左偏树需要
	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] dist = new int[MAXN];

	// 懒更新信息，攻击力应该乘多少
	public static long[] mul = new long[MAXN];

	// 懒更新信息，攻击力应该加多少
	public static long[] add = new long[MAXN];

	public static void prepare() {
		dist[0] = -1;
		for (int i = 1; i <= m; i++) {
			left[i] = right[i] = dist[i] = 0;
			mul[i] = 1;
			add[i] = 0;
		}
		for (int i = 1; i <= n; i++) {
			sacrifice[i] = top[i] = 0;
		}
	}

	public static void upgrade(int i, int t, long v) {
		if (t == 0) {
			attack[i] += v;
			add[i] += v;
		} else {
			attack[i] *= v;
			mul[i] *= v;
			add[i] *= v;
		}
	}

	public static void down(int i) {
		if (mul[i] != 1 || add[i] != 0) {
			int l = left[i];
			int r = right[i];
			if (l != 0) {
				attack[l] = attack[l] * mul[i] + add[i];
				mul[l] = mul[l] * mul[i];
				add[l] = add[l] * mul[i] + add[i];
			}
			if (r != 0) {
				attack[r] = attack[r] * mul[i] + add[i];
				mul[r] = mul[r] * mul[i];
				add[r] = add[r] * mul[i] + add[i];
			}
			mul[i] = 1;
			add[i] = 0;
		}
	}

	public static int merge(int i, int j) {
		if (i == 0 || j == 0) {
			return i + j;
		}
		int tmp;
		if (attack[i] > attack[j]) {
			tmp = i;
			i = j;
			j = tmp;
		}
		down(i);
		right[i] = merge(right[i], j);
		if (dist[left[i]] < dist[right[i]]) {
			tmp = left[i];
			left[i] = right[i];
			right[i] = tmp;
		}
		dist[i] = dist[right[i]] + 1;
		return i;
	}

	public static int pop(int i) {
		down(i);
		int ans = merge(left[i], right[i]);
		left[i] = right[i] = dist[i] = 0;
		return ans;
	}

	public static void compute() {
		deep[1] = 1;
		for (int i = 2; i <= n; i++) {
			deep[i] = deep[belong[i]] + 1;
		}
		for (int i = 1; i <= m; i++) {
			if (top[first[i]] == 0) {
				top[first[i]] = i;
			} else {
				top[first[i]] = merge(top[first[i]], i);
			}
		}
		for (int i = n; i >= 1; i--) {
			while (top[i] != 0 && attack[top[i]] < defend[i]) {
				die[top[i]] = i;
				sacrifice[i]++;
				top[i] = pop(top[i]);
			}
			if (top[i] != 0) {
				upgrade(top[i], type[i], gain[i]);
				if (top[belong[i]] == 0) {
					top[belong[i]] = top[i];
				} else {
					top[belong[i]] = merge(top[belong[i]], top[i]);
				}
			}
		}
	}

	public static void main(String[] args) {
		ReaderWriter io = new ReaderWriter();
		n = io.nextInt();
		m = io.nextInt();
		prepare();
		for (int i = 1; i <= n; i++) {
			defend[i] = io.nextLong();
		}
		for (int i = 2; i <= n; i++) {
			belong[i] = io.nextInt();
			type[i] = io.nextInt();
			gain[i] = io.nextLong();
		}
		for (int i = 1; i <= m; i++) {
			attack[i] = io.nextLong();
			first[i] = io.nextInt();
		}
		compute();
		for (int i = 1; i <= n; i++) {
			io.println(sacrifice[i]);
		}
		for (int i = 1; i <= m; i++) {
			io.println(deep[first[i]] - deep[die[i]]);
		}
		io.flush();
		io.close();
	}

	// 读写工具类
	public static class ReaderWriter extends PrintWriter {
		byte[] buf = new byte[1 << 16];
		int bId = 0, bSize = 0;
		boolean eof = false;

		public ReaderWriter() {
			super(System.out);
		}

		private byte getByte() {
			if (bId >= bSize) {
				try {
					bSize = System.in.read(buf);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (bSize == -1)
					eof = true;
				bId = 0;
			}
			return buf[bId++];
		}

		byte c;

		public boolean hasNext() {
			if (eof)
				return false;
			while ((c = getByte()) <= 32 && !eof)
				;
			if (eof)
				return false;
			bId--;
			return true;
		}

		public String next() {
			if (!hasNext())
				return null;
			byte c = getByte();
			while (c <= 32)
				c = getByte();
			StringBuilder sb = new StringBuilder();
			while (c > 32) {
				sb.append((char) c);
				c = getByte();
			}
			return sb.toString();
		}

		public int nextInt() {
			if (!hasNext())
				return Integer.MIN_VALUE;
			int sign = 1;
			byte c = getByte();
			while (c <= 32)
				c = getByte();
			if (c == '-') {
				sign = -1;
				c = getByte();
			}
			int val = 0;
			while (c >= '0' && c <= '9') {
				val = val * 10 + (c - '0');
				c = getByte();
			}
			bId--;
			return val * sign;
		}

		public long nextLong() {
			if (!hasNext())
				return Long.MIN_VALUE;
			int sign = 1;
			byte c = getByte();
			while (c <= 32)
				c = getByte();
			if (c == '-') {
				sign = -1;
				c = getByte();
			}
			long val = 0;
			while (c >= '0' && c <= '9') {
				val = val * 10 + (c - '0');
				c = getByte();
			}
			bId--;
			return val * sign;
		}

		public double nextDouble() {
			if (!hasNext())
				return Double.NaN;
			int sign = 1;
			byte c = getByte();
			while (c <= 32)
				c = getByte();
			if (c == '-') {
				sign = -1;
				c = getByte();
			}
			double val = 0;
			while (c >= '0' && c <= '9') {
				val = val * 10 + (c - '0');
				c = getByte();
			}
			if (c == '.') {
				double mul = 1;
				c = getByte();
				while (c >= '0' && c <= '9') {
					mul *= 0.1;
					val += (c - '0') * mul;
					c = getByte();
				}
			}
			bId--;
			return val * sign;
		}
	}

}
