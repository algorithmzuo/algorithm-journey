package class172;

// 文本编辑器，块状链表实现，java版
// 一开始文本为空，光标在文本开头，也就是1位置，请实现如下6种操作
// Move k     : 将光标移动到第k个字符之后，操作保证光标不会到非法位置
// Insert n s : 在光标处插入长度为n的字符串s，光标位置不变
// Delete n   : 删除光标后的n个字符，光标位置不变，操作保证有足够字符
// Get n      : 输出光标后的n个字符，光标位置不变，操作保证有足够字符
// Prev       : 光标前移一个字符，操作保证光标不会到非法位置
// Next       : 光标后移一个字符，操作保证光标不会到非法位置
// Insert操作时，字符串s中ASCII码在[32,126]范围上的字符一定有n个，其他字符请过滤掉
// 测试链接 : https://www.luogu.com.cn/problem/P4008
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class Code06_TextEditor1 {

	// 整个文章能到达的最大长度
	public static int MAXN = 3000001;
	// 块内容量，近似等于 2 * 根号n，每块内容大小不会超过容量
	public static int BLEN = 3001;
	// 块的数量上限
	public static int BNUM = (MAXN / BLEN) << 1;

	// 写入内容的空间，编号为i的块，内容写入到blocks[i]
	public static char[][] blocks = new char[BNUM][BLEN];
	// 编号分配池，其实是一个栈，分配编号从栈顶弹出，回收编号从栈顶压入
	public static int[] pool = new int[BNUM];
	// 分配池的栈顶
	public static int top = 0;

	// nxt[i]表示编号为i的块，下一块的编号
	public static int[] nxt = new int[BNUM];
	// siz[i]表示编号为i的块，写入了多少长度的内容
	public static int[] siz = new int[BNUM];

	// 插入字符串时，先读入进str，然后写入到块
	// 获取字符串时，先从块里取出内容保留在str，然后打印str
	public static char[] str = new char[MAXN];

	// 准备好分配池，从栈顶到栈底，依次是1、2、... BNUM - 1
	// 准备好头块的配置
	public static void prepare() {
		for (int i = 1, id = BNUM - 1; i < BNUM; i++, id--) {
			pool[i] = id;
		}
		top = BNUM - 1;
		siz[0] = 0;
		nxt[0] = -1;
	}

	// 分配编号
	public static int assign() {
		return pool[top--];
	}

	// 回收编号
	public static void recycle(int id) {
		pool[++top] = id;
	}

	// 寻找整个文章中的pos位置
	// 找到所在块的编号 和 块内位置
	// 块编号设置给bi，块内位置设置给pi
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

	// 链表中让curb块和nextb块，连在一起，然后让nextb块从0位置开始，写入如下的内容
	// 从src[pos]开始，拿取长度为len的字符串
	public static void linkAndWrite(int curb, int nextb, char[] src, int pos, int len) {
		nxt[nextb] = nxt[curb];
		nxt[curb] = nextb;
		System.arraycopy(src, pos, blocks[nextb], 0, len);
		siz[nextb] = len;
	}

	// curb块里，在内容的后面，追加nextb块的内容，然后nextb块从链表中删掉
	public static void merge(int curb, int nextb) {
		System.arraycopy(blocks[nextb], 0, blocks[curb], siz[curb], siz[nextb]);
		siz[curb] += siz[nextb];
		nxt[curb] = nxt[nextb];
		recycle(nextb);
	}

	// curb块的pos位置往后的内容，写入到新分裂出的块里
	public static void split(int curb, int pos) {
		if (curb == -1 || pos == siz[curb]) {
			return;
		}
		int nextb = assign();
		linkAndWrite(curb, nextb, blocks[curb], pos, siz[curb] - pos);
		siz[curb] = pos;
	}

	// 从头到尾遍历所有的块，检查任意相邻两块，内容大小的累加和 <= 块内容量，就合并
	public static void maintain() {
		for (int curb = 0, nextb; curb != -1; curb = nxt[curb]) {
			nextb = nxt[curb];
			while (nextb != -1 && siz[curb] + siz[nextb] <= BLEN) {
				merge(curb, nextb);
				nextb = nxt[curb];
			}
		}
	}

	// 插入的字符串在str中，长度为len，从整个文章的pos位置插入
	public static void insert(int pos, int len) {
		find(pos);
		split(bi, pi);
		int curb = bi, newb, done = 0;
		while (done + BLEN <= len) {
			newb = assign();
			linkAndWrite(curb, newb, str, done, BLEN);
			done += BLEN;
			curb = newb;
		}
		if (len > done) {
			newb = assign();
			linkAndWrite(curb, newb, str, done, len - done);
		}
		maintain();
	}

	// 从整个文章的pos位置，往后len的长度，这些内容删掉
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

	// 从整个文章的pos位置，往后len的长度，这些内容找到，写入进str
	public static void get(int pos, int len) {
		find(pos);
		int curb = bi;
		pos = pi;
		int done = (len < siz[curb] - pos) ? len : (siz[curb] - pos);
		System.arraycopy(blocks[curb], pos, str, 0, done);
		curb = nxt[curb];
		while (curb != -1 && done + siz[curb] <= len) {
			System.arraycopy(blocks[curb], 0, str, done, siz[curb]);
			done += siz[curb];
			curb = nxt[curb];
		}
		if (curb != -1 && done < len) {
			System.arraycopy(blocks[curb], 0, str, done, len - done);
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