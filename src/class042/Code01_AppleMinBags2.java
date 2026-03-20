package class042;

// 感谢热心的同学，找到了题目1的在线测试
// 测试链接 : https://www.nowcoder.com/practice/73e0552b78474a9086781e47f4e01d73
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_AppleMinBags2 {

	// 最优解
	public static int bags2(int apple) {
		if ((apple & 1) != 0) {
			return -1;
		}
		if (apple < 18) {
			if (apple == 0) {
				return 0;
			}
			if (apple == 6 || apple == 8) {
				return 1;
			}
			if (apple == 12 || apple == 14 || apple == 16) {
				return 2;
			}
			return -1;
		}
		return (apple - 18) / 8 + 3;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = in.nextInt();
		out.println(bags2(n));
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
	}

}
