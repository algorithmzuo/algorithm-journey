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
		int cur = 0;
		while (cur != -1 && pos > siz[cur]) {
			pos -= siz[cur];
			cur = nxt[cur];
		}
		bi = cur;
		pi = pos;
	}

	public static void flush(int cur, int next, int tailLen, char[] src, int srcPos) {
		nxt[next] = nxt[cur];
		nxt[cur] = next;
		siz[next] = tailLen;
		System.arraycopy(src, srcPos, blocks[next], 0, tailLen);
	}

	public static void merge(int cur, int next) {
		System.arraycopy(blocks[next], 0, blocks[cur], siz[cur], siz[next]);
		siz[cur] += siz[next];
		nxt[cur] = nxt[next];
		recycle(next);
	}

	public static void split(int cur, int pos) {
		if (cur == -1 || pos == siz[cur]) {
			return;
		}
		int next = assign();
		flush(cur, next, siz[cur] - pos, blocks[cur], pos);
		siz[cur] = pos;
	}

	public static void maintain() {
		for (int cur = 0, next; cur != -1; cur = nxt[cur]) {
			next = nxt[cur];
			while (next != -1 && siz[cur] + siz[next] <= BLEN) {
				merge(cur, next);
				next = nxt[cur];
			}
		}
	}

	public static void insert(int pos, int len) {
		find(pos);
		split(bi, pi);
		int cur = bi, newb, done = 0;
		while (done + BLEN <= len) {
			newb = assign();
			flush(cur, newb, BLEN, str, done);
			done += BLEN;
			cur = newb;
		}
		if (len > done) {
			newb = assign();
			flush(cur, newb, len - done, str, done);
		}
		maintain();
	}

	public static void erase(int pos, int len) {
		find(pos);
		split(bi, pi);
		int cur = bi, next = nxt[cur];
		while (next != -1 && len > siz[next]) {
			len -= siz[next];
			recycle(next);
			next = nxt[next];
		}
		if (next != -1) {
			split(next, len);
			recycle(next);
			nxt[cur] = nxt[next];
		} else {
			nxt[cur] = -1;
		}
		maintain();
	}

	public static void get(int pos, int len) {
		find(pos);
		int cur = bi;
		pos = pi;
		int got = (len < siz[cur] - pos) ? len : (siz[cur] - pos);
		System.arraycopy(blocks[cur], pos, str, 0, got);
		cur = nxt[cur];
		while (cur != -1 && got + siz[cur] <= len) {
			System.arraycopy(blocks[cur], 0, str, got, siz[cur]);
			got += siz[cur];
			cur = nxt[cur];
		}
		if (cur != -1 && got < len) {
			System.arraycopy(blocks[cur], 0, str, got, len - got);
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