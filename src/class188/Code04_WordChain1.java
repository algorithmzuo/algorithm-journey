package class188;

// 词链，java版
// 给定n个由小写字母组成的单词，想把所有单词串成一个词链
// 同时要求前一个单词的结尾字符 == 后一个单词的开头字符
// 比如词链，aloha.arachnid.dog.gopher.rat.tiger
// 需要使用每个输入的单词恰好一次，同一单词出现k次就要用k次
// 返回字典序最小的结果，注意 . 的字典序 < 小写字母的字典序
// 如果不存在合法词链，输出***
// 1 <= n <= 1000
// 测试链接 : https://www.luogu.com.cn/problem/P1127
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code04_WordChain1 {

	public static class EdgeCmp implements Comparator<Integer> {
		public int compare(Integer i, Integer j) {
			if (a[i] != a[j]) {
				return a[i] - a[j];
			}
			return str[i].compareTo(str[j]);
		}
	}

	public static int MAXN = 27;
	public static int MAXM = 1002;
	public static int n = 26, m;

	public static String[] str = new String[MAXM];
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];
	public static Integer[] eidArr = new Integer[MAXM];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static String[] weight = new String[MAXM];
	public static int cntg;

	public static int[] cur = new int[MAXN];
	public static int[] outDeg = new int[MAXN];
	public static int[] inDeg = new int[MAXN];

	public static String[] path = new String[MAXM];
	public static int cntp;

	public static void addEdge(int u, int v, String w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	public static int startNode(String str) {
		return str.charAt(0) - 'a' + 1;
	}

	public static int endNode(String str) {
		return str.charAt(str.length() - 1) - 'a' + 1;
	}

	public static void connect() {
		Arrays.sort(eidArr, 1, m + 1, new EdgeCmp());
		for (int l = 1, r = 1; l <= m; l = ++r) {
			while (r + 1 <= m && a[eidArr[l]] == a[eidArr[r + 1]]) {
				r++;
			}
			for (int i = r, u, v; i >= l; i--) {
				u = a[eidArr[i]];
				v = b[eidArr[i]];
				outDeg[u]++;
				inDeg[v]++;
				addEdge(u, v, str[eidArr[i]]);
			}
		}
		for (int i = 1; i <= n; i++) {
			cur[i] = head[i];
		}
	}

	public static int directedStart() {
		int start = -1, end = -1;
		for (int i = 1; i <= n; i++) {
			int v = outDeg[i] - inDeg[i];
			if (v < -1 || v > 1 || (v == 1 && start != -1) || (v == -1 && end != -1)) {
				return -1;
			}
			if (v == 1) {
				start = i;
			}
			if (v == -1) {
				end = i;
			}
		}
		if ((start == -1) ^ (end == -1)) {
			return -1;
		}
		if (start != -1) {
			return start;
		}
		for (int i = 1; i <= n; i++) {
			if (outDeg[i] > 0) {
				return i;
			}
		}
		return -1;
	}

	public static void euler(int u, String w) {
		for (int e = cur[u]; e > 0; e = cur[u]) {
			cur[u] = nxt[e];
			euler(to[e], weight[e]);
		}
		path[++cntp] = w;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			str[i] = in.nextString();
			a[i] = startNode(str[i]);
			b[i] = endNode(str[i]);
			eidArr[i] = i;
		}
		connect();
		int start = directedStart();
		if (start == -1) {
			out.println("***");
		} else {
			euler(start, "");
			if (cntp != m + 1) {
				out.println("***");
			} else {
				out.print(path[cntp - 1]);
				for (int i = cntp - 2; i >= 1; i--) {
					out.print("." + path[i]);
				}
				out.println();
			}
		}
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

		String nextString() throws IOException {
			int c;
			do {
				c = readByte();
				if (c == -1)
					return null;
			} while (c < 'a' || c > 'z');
			StringBuilder sb = new StringBuilder();
			while (c >= 'a' && c <= 'z') {
				sb.append((char) c);
				c = readByte();
				if (c == -1)
					break;
			}
			return sb.toString();
		}

	}

}
