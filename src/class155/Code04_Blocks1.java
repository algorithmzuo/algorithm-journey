package class155;

// Y的积木，可持久化左偏树实现最优解，java版
// 测试链接 : https://www.luogu.com.cn/problem/P2409
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_Blocks1 {

	public static int MAXN = 101;
	public static int MAXM = 10001;
	public static int MAXK = 10001;
	public static int MAXT = 1000001;
	public static int INF = 10000001;

	public static int n, k;

	public static int[] start = new int[MAXN];
	public static int[] boundary = new int[MAXN];
	public static int[] arr = new int[MAXM];

	public static int[] idx = new int[MAXT];
	public static int[] jdx = new int[MAXT];
	public static int[] num = new int[MAXT];
	public static int[] left = new int[MAXT];
	public static int[] right = new int[MAXT];
	public static int[] dist = new int[MAXT];
	public static int cnt = 0;

	public static int[] base = new int[MAXT];

	public static int[] heap = new int[MAXK];
	public static int heapSize = 0;

	public static int[] ans = new int[MAXK];

	public static int init(int i, int j) {
		idx[++cnt] = i;
		jdx[cnt] = j;
		num[cnt] = j + 1 < boundary[i] ? (arr[j + 1] - arr[j]) : INF;
		left[cnt] = right[cnt] = dist[cnt] = 0;
		return cnt;
	}

	public static int clone(int i) {
		idx[++cnt] = idx[i];
		jdx[cnt] = jdx[i];
		num[cnt] = num[i];
		left[cnt] = left[i];
		right[cnt] = right[i];
		dist[cnt] = dist[i];
		return cnt;
	}

	public static int merge(int i, int j) {
		if (i == 0 || j == 0) {
			return i + j;
		}
		int tmp;
		if (num[i] > num[j]) {
			tmp = i;
			i = j;
			j = tmp;
		}
		int h = clone(i);
		right[h] = merge(right[h], j);
		if (dist[left[h]] < dist[right[h]]) {
			tmp = left[h];
			left[h] = right[h];
			right[h] = tmp;
		}
		dist[h] = dist[right[h]] + 1;
		return h;
	}

	public static int pop(int i) {
		if (left[i] == 0 && right[i] == 0) {
			return 0;
		}
		if (left[i] == 0 || right[i] == 0) {
			return clone(left[i] + right[i]);
		}
		return merge(left[i], right[i]);
	}

	public static boolean compare(int i, int j) {
		return base[heap[i]] + num[heap[i]] < base[heap[j]] + num[heap[j]];
	}

	public static void heapAdd(int i) {
		heap[++heapSize] = i;
		int cur = heapSize, up = cur / 2, tmp;
		while (cur > 1 && compare(cur, up)) {
			tmp = heap[up];
			heap[up] = heap[cur];
			heap[cur] = tmp;
			cur = up;
			up = cur / 2;
		}
	}

	public static int heapPop() {
		int ans = heap[1];
		heap[1] = heap[heapSize--];
		int cur = 1, l = cur * 2, r = l + 1, best, tmp;
		while (l <= heapSize) {
			best = (r <= heapSize && compare(r, l)) ? r : l;
			best = compare(best, cur) ? best : cur;
			if (best == cur) {
				break;
			}
			tmp = heap[best];
			heap[best] = heap[cur];
			heap[cur] = tmp;
			cur = best;
			l = cur * 2;
			r = l + 1;
		}
		return ans;
	}

	public static void compute() {
		int first = 0;
		for (int i = 1; i <= n; i++) {
			Arrays.sort(arr, start[i], boundary[i]);
			first += arr[start[i]];
		}
		dist[0] = -1;
		int head = 0;
		for (int i = 1; i <= n; i++) {
			head = merge(head, init(i, start[i]));
		}
		base[head] = first;
		ans[1] = first;
		heapAdd(head);
		for (int ansi = 2, h1, h2; ansi <= k; ansi++) {
			head = heapPop();
			ans[ansi] = base[head] + num[head];
			h1 = pop(head);
			if (h1 != 0) {
				base[h1] = base[head];
				heapAdd(h1);
			}
			if (jdx[head] + 1 < boundary[idx[head]]) {
				h2 = merge(h1, init(idx[head], jdx[head] + 1));
				base[h2] = ans[ansi];
				heapAdd(h2);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		k = (int) in.nval;
		for (int i = 1, m, ai = 0; i <= n; i++) {
			in.nextToken();
			m = (int) in.nval;
			start[i] = ai + 1;
			for (int j = 1; j <= m; j++) {
				in.nextToken();
				arr[++ai] = (int) in.nval;
			}
			boundary[i] = start[i] + m;
		}
		compute();
		for (int i = 1; i <= k; i++) {
			out.print(ans[i] + " ");
		}
		out.println();
		out.flush();
		out.close();
		br.close();
	}

}
