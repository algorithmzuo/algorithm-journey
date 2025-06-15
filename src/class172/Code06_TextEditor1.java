package class172;

// 文本编辑器，块状链表实现，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4008
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class Code06_TextEditor1 {

	public static int MAXN = 3000001;
	public static int BLEN = 4001;
	public static int BNUM = (MAXN / BLEN) << 1;

	public static char[][] blocks = new char[BNUM][BLEN];
	public static int[] nxt = new int[BNUM];
	public static int[] siz = new int[BNUM];
	public static int[] pool = new int[BNUM];
	public static int psiz = 0;

	public static char[] str = new char[MAXN];

	public static void prepare() {
		for (int i = 1; i < BNUM; i++) {
			pool[i] = i;
		}
		psiz = BNUM - 1;
		siz[0] = 0;
		nxt[0] = -1;
	}

	public static int assign() {
		return pool[psiz--];
	}

	public static void recycle(int i) {
		pool[++psiz] = i;
	}

	public static int bi, pi;

	public static void find(int pos) {
		int curb = 0;
		while (curb != -1 && pos > siz[curb]) {
			pos -= siz[curb];
			curb = nxt[curb];
		}
		bi = curb;
		pi = pos;
	}

	public static void flush(int curb, int nextb, int tailLen, char[] src, int srcPos) {
		nxt[nextb] = nxt[curb];
		nxt[curb] = nextb;
		siz[nextb] = tailLen;
		System.arraycopy(src, srcPos, blocks[nextb], 0, tailLen);
	}

	public static void merge(int curb, int nextb) {
		System.arraycopy(blocks[nextb], 0, blocks[curb], siz[curb], siz[nextb]);
		siz[curb] += siz[nextb];
		nxt[curb] = nxt[nextb];
		recycle(nextb);
	}

	public static void split(int curb, int pos) {
		if (curb == -1 || pos == siz[curb]) {
			return;
		}
		int nextb = assign();
		flush(curb, nextb, siz[curb] - pos, blocks[curb], pos);
		siz[curb] = pos;
	}

	public static void maintain() {
		for (int curb = 0, nextb; curb != -1; curb = nxt[curb]) {
			nextb = nxt[curb];
			while (nextb != -1 && siz[curb] + siz[nextb] <= BLEN) {
				merge(curb, nextb);
				nextb = nxt[curb];
			}
		}
	}

	public static void insert(int pos, int len) {
		find(pos);
		split(bi, pi);
		int curb = bi, newb, done = 0;
		while (done + BLEN <= len) {
			newb = assign();
			flush(curb, newb, BLEN, str, done);
			done += BLEN;
			curb = newb;
		}
		if (len > done) {
			newb = assign();
			flush(curb, newb, len - done, str, done);
		}
		maintain();
	}

	public static void erase(int pos, int len) {
		find(pos);
		split(bi, pi);
		int curb = bi, nextb = nxt[curb];
		while (nextb != -1 && len > siz[nextb]) {
			len -= siz[nextb];
			recycle(nextb);
			nextb = nxt[nextb];
		}
		if (nextb != -1) {
			split(nextb, len);
			recycle(nextb);
			nxt[curb] = nxt[nextb];
		} else {
			nxt[curb] = -1;
		}
		maintain();
	}

	public static void get(int pos, int len) {
		find(pos);
		int curb = bi;
		pos = pi;
		int got = (len < siz[curb] - pos) ? len : (siz[curb] - pos);
		System.arraycopy(blocks[curb], pos, str, 0, got);
		curb = nxt[curb];
		while (curb != -1 && got + siz[curb] <= len) {
			System.arraycopy(blocks[curb], 0, str, got, siz[curb]);
			got += siz[curb];
			curb = nxt[curb];
		}
		if (curb != -1 && got < len) {
			System.arraycopy(blocks[curb], 0, str, got, len - got);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
		int n = in.nextInt();
		int pos = 0;
		int len;
		String op;
		prepare();
		for (int i = 1; i <= n; i++) {
			op = in.nextString();
			if (op.equals("Prev")) {
				pos--;
			} else if (op.equals("Next")) {
				pos++;
			} else if (op.equals("Move")) {
				pos = in.nextInt();
			} else if (op.equals("Insert")) {
				len = in.nextInt();
				for (int j = 0; j < len; j++) {
					str[j] = in.nextChar();
				}
				insert(pos, len);
			} else if (op.equals("Delete")) {
				len = in.nextInt();
				erase(pos, len);
			} else {
				len = in.nextInt();
				get(pos, len);
				out.write(str, 0, len);
				out.write('\n');
			}
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		final private int BUFFER_SIZE = 1 << 16;
		private final InputStream in;
		private final byte[] buffer;
		private int ptr, len;

		FastReader() {
			in = System.in;
			buffer = new byte[BUFFER_SIZE];
			ptr = len = 0;
		}

		private boolean hasNextByte() throws IOException {
			if (ptr < len)
				return true;
			ptr = 0;
			len = in.read(buffer);
			return len > 0;
		}

		private byte readByte() throws IOException {
			if (!hasNextByte())
				return -1;
			return buffer[ptr++];
		}

		// 读取下一个ASCII码范围在[32,126]的字符
		char nextChar() throws IOException {
			byte c;
			do {
				c = readByte();
				if (c == -1)
					return 0;
			} while (c < 32 || c > 126);
			return (char) c;
		}

		String nextString() throws IOException {
			byte b = readByte();
			while (isWhitespace(b))
				b = readByte();
			StringBuilder sb = new StringBuilder(16);
			while (!isWhitespace(b) && b != -1) {
				sb.append((char) b);
				b = readByte();
			}
			return sb.toString();
		}

		int nextInt() throws IOException {
			int num = 0, sign = 1;
			byte b = readByte();
			while (isWhitespace(b))
				b = readByte();
			if (b == '-') {
				sign = -1;
				b = readByte();
			}
			while (!isWhitespace(b) && b != -1) {
				num = num * 10 + (b - '0');
				b = readByte();
			}
			return sign * num;
		}

		private boolean isWhitespace(byte b) {
			return b == ' ' || b == '\n' || b == '\r' || b == '\t';
		}
	}

}