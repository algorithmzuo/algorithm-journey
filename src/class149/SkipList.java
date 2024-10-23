package class149;

// 跳表的实现(静态数组实现)
// 测试链接 : https://www.luogu.com.cn/problem/P3369
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class SkipList {

	public static int MAXL = 20;

	public static int MAXN = 100001;

	public static int cnt;

	public static int[] key = new int[MAXN];

	public static int[] count = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static int[][] next = new int[MAXN][MAXL + 1];

	public static int[][] len = new int[MAXN][MAXL + 1];

	public static void build() {
		cnt = 1;
		key[cnt] = Integer.MIN_VALUE;
		size[cnt] = MAXL;
	}

	public static void clear() {
		Arrays.fill(key, 1, cnt + 1, 0);
		Arrays.fill(count, 1, cnt + 1, 0);
		Arrays.fill(size, 1, cnt + 1, 0);
		for (int i = 1; i <= cnt; i++) {
			Arrays.fill(next[i], 0);
			Arrays.fill(len[i], 0);
		}
		cnt = 0;
	}

	public static int random() {
		int ans = 1;
		while (Math.random() < 0.5) {
			ans++;
		}
		return Math.min(ans, MAXL);
	}

	public static int find(int i, int h, int num) {
		while (next[i][h] != 0 && key[next[i][h]] < num) {
			i = next[i][h];
		}
		if (h == 1) {
			if (next[i][h] != 0 && key[next[i][h]] == num) {
				return next[i][h];
			} else {
				return 0;
			}
		}
		return find(i, h - 1, num);
	}

	public static void add(int num) {
		if (find(1, MAXL, num) != 0) {
			addCount(1, MAXL, num);
		} else {
			key[++cnt] = num;
			count[cnt] = 1;
			size[cnt] = random();
			addNode(1, MAXL, cnt);
		}
	}

	public static void addCount(int i, int h, int num) {
		while (next[i][h] != 0 && key[next[i][h]] < num) {
			i = next[i][h];
		}
		if (h == 1) {
			count[next[i][h]]++;
		} else {
			addCount(i, h - 1, num);
		}
		len[i][h]++;
	}

	public static int addNode(int i, int h, int j) {
		int rightCnt = 0;
		while (next[i][h] != 0 && key[next[i][h]] < key[j]) {
			rightCnt += len[i][h];
			i = next[i][h];
		}
		if (h == 1) {
			next[j][h] = next[i][h];
			next[i][h] = j;
			len[j][h] = count[next[j][h]];
			len[i][h] = count[next[i][h]];
			return rightCnt;
		} else {
			int downCnt = addNode(i, h - 1, j);
			if (h > size[j]) {
				len[i][h]++;
			} else {
				next[j][h] = next[i][h];
				next[i][h] = j;
				len[j][h] = len[i][h] + 1 - downCnt - count[j];
				len[i][h] = downCnt + count[j];
			}
			return rightCnt + downCnt;
		}
	}

	public static void remove(int num) {
		int j = find(1, MAXL, num);
		if (j != 0) {
			if (count[j] > 1) {
				removeCount(1, MAXL, num);
			} else {
				removeNode(1, MAXL, j);
			}
		}
	}

	public static void removeCount(int i, int h, int num) {
		while (next[i][h] != 0 && key[next[i][h]] < num) {
			i = next[i][h];
		}
		if (h == 1) {
			count[next[i][h]]--;
		} else {
			removeCount(i, h - 1, num);
		}
		len[i][h]--;
	}

	public static void removeNode(int i, int h, int j) {
		if (h < 1) {
			return;
		}
		while (next[i][h] != 0 && key[next[i][h]] < key[j]) {
			i = next[i][h];
		}
		if (h > size[j]) {
			len[i][h]--;
		} else {
			next[i][h] = next[j][h];
			len[i][h] += len[j][h] - 1;
		}
		removeNode(i, h - 1, j);
	}

	public static int rank(int num) {
		return rank(1, MAXL, num) + 1;
	}

	public static int rank(int i, int h, int num) {
		int rightCnt = 0;
		while (next[i][h] != 0 && key[next[i][h]] < num) {
			rightCnt += len[i][h];
			i = next[i][h];
		}
		if (h == 1) {
			return rightCnt;
		} else {
			return rightCnt + rank(i, h - 1, num);
		}
	}

	public static int index(int x) {
		return index(1, MAXL, x);
	}

	public static int index(int i, int h, int x) {
		int c = 0;
		while (next[i][h] != 0 && c + len[i][h] < x) {
			c += len[i][h];
			i = next[i][h];
		}
		if (h == 1) {
			return key[next[i][h]];
		} else {
			return index(i, h - 1, x - c);
		}
	}

	public static int pre(int num) {
		return pre(1, MAXL, num);
	}

	public static int pre(int i, int h, int num) {
		while (next[i][h] != 0 && key[next[i][h]] < num) {
			i = next[i][h];
		}
		if (h == 1) {
			return i == 1 ? Integer.MIN_VALUE : key[i];
		} else {
			return pre(i, h - 1, num);
		}
	}

	public static int post(int num) {
		return post(1, MAXL, num);
	}

	public static int post(int i, int h, int num) {
		while (next[i][h] != 0 && key[next[i][h]] < num) {
			i = next[i][h];
		}
		if (h == 1) {
			if (next[i][h] == 0) {
				return Integer.MAX_VALUE;
			}
			if (key[next[i][h]] > num) {
				return key[next[i][h]];
			}
			i = next[i][h];
			if (next[i][h] == 0) {
				return Integer.MAX_VALUE;
			} else {
				return key[next[i][h]];
			}
		} else {
			return post(i, h - 1, num);
		}
	}

	public static void main(String[] args) throws IOException {
		build();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		for (int i = 1, op, x; i <= n; i++) {
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			x = (int) in.nval;
			if (op == 1) {
				add(x);
			} else if (op == 2) {
				remove(x);
			} else if (op == 3) {
				out.println(rank(x));
			} else if (op == 4) {
				out.println(index(x));
			} else if (op == 5) {
				out.println(pre(x));
			} else {
				out.println(post(x));
			}
		}
		clear();
		out.flush();
		out.close();
		br.close();
	}

}
