package class091;

// 组团买票找到了在线测试
// 逻辑和课上讲的一样，但是测试中设定的ki为负数
// 实现做了一些小优化，具体可以看注释
// 测试链接 : https://www.luogu.com.cn/problem/P12331
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.util.PriorityQueue;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_GroupBuyTickets2 {

	public static class Game {
		public long ki;
		public long bi;
		public int people;

		public Game(long k, long b) {
			ki = k;
			bi = b;
		}

		public long earn() {
			return cost(people + 1) - cost(people);
		}

		public long cost(long p) {
			long price = ki * p + bi;
			if (price < 0) {
				price = 0;
			}
			return p * price;
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = in.nextInt();
		int m = in.nextInt();
		PriorityQueue<Game> heap = new PriorityQueue<>((a, b) -> Long.compare(b.earn(), a.earn()));
		for (int i = 0; i < m; i++) {
			Game cur = new Game(in.nextLong(), in.nextLong());
			// 初始增费<=0的项目直接忽略
			if (cur.earn() > 0) {
				heap.add(cur);
			}
		}
		long ans = 0;
		for (int i = 0; i < n && !heap.isEmpty(); i++) {
			Game cur = heap.poll();
			long money = cur.earn();
			if (money <= 0) {
				// 没有正向增费，那么可以结束了
				break;
			}
			ans += money;
			cur.people++;
			if (cur.earn() > 0) {
				heap.add(cur);
			}
		}
		out.println(ans);
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 16];
		private int ptr = 0, len = 0;
		private final InputStream in;

		FastReader(InputStream in) {
			this.in = in;
		}

		private int readByte() throws IOException {
			if (ptr >= len) {
				len = in.read(buffer);
				ptr = 0;
				if (len <= 0)
					return -1;
			}
			return buffer[ptr++];
		}

		int nextInt() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			int val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}

		long nextLong() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			long val = 0L;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}

	}

}
