package class143;

// 整数倍的最小数位和
// 测试链接 : https://www.luogu.com.cn/problem/AT_arc084_b
// 测试链接 : https://atcoder.jp/contests/abc077/tasks/arc084_b

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayDeque;

public class Code03_SmallMultiple {

	public static int MAXN = 100001;

	public static boolean[] visit = new boolean[MAXN];

	public static ArrayDeque<int[]> deque = new ArrayDeque<>();

	public static int k;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		k = (int) in.nval;
		out.println(bfs());
		out.flush();
		out.close();
		br.close();
	}

	public static int bfs() {
		deque.clear();
		deque.add(new int[] { 1, 1 });
		int[] cur;
		int mod, cost;
		while (!deque.isEmpty()) {
			cur = deque.pollFirst();
			mod = cur[0];
			cost = cur[1];
			if (!visit[mod]) {
				visit[mod] = true;
				if (mod == 0) {
					return cost;
				}
				deque.addFirst(new int[] { (mod * 10) % k, cost });
				deque.addLast(new int[] { (mod + 1) % k, cost + 1 });
			}
		}
		return -1;
	}

}
