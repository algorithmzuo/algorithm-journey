package class115;

// 天际线问题(洛谷测试)
// 测试链接 : https://www.luogu.com.cn/problem/P1904
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

// 堆结构由自己实现
public class Code02_SkylineLuogu {

	public static int MAXN = 20001;

	public static int[][] arr = new int[MAXN][3];

	public static int[] xsort = new int[MAXN];

	public static int[] height = new int[MAXN];

	public static int[][] heap = new int[MAXN][2];

	public static int heapSize;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = 0;
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			arr[n][0] = (int) in.nval;
			in.nextToken();
			arr[n][2] = (int) in.nval;
			in.nextToken();
			arr[n][1] = (int) in.nval;
			n++;
		}
		int m = prepare(n);
		compute(n, m);
		out.print(xsort[1] + " " + height[1]);
		for (int i = 2, pre = height[1]; i <= m; i++) {
			if (pre != height[i]) {
				out.print(" " + xsort[i] + " " + height[i]);
			}
			pre = height[i];
		}
		out.println();
		out.flush();
		out.close();
		br.close();
	}

	public static void compute(int n, int m) {
		for (int i = 1, j = 0; i <= m; i++) {
			for (; j < n && arr[j][0] <= i; j++) {
				push(arr[j][2], arr[j][1]);
			}
			while (!isEmpty() && peekEnd() < i) {
				poll();
			}
			if (!isEmpty()) {
				height[i] = peekHeight();
			}
		}
	}

	public static int prepare(int n) {
		int size = 0;
		for (int i = 0; i < n; i++) {
			xsort[++size] = arr[i][0];
			xsort[++size] = arr[i][1] - 1;
			xsort[++size] = arr[i][1];
		}
		Arrays.sort(xsort, 1, size + 1);
		int m = 1;
		for (int i = 1; i <= size; i++) {
			if (xsort[m] != xsort[i]) {
				xsort[++m] = xsort[i];
			}
		}
		for (int i = 0; i < n; i++) {
			arr[i][0] = rank(m, arr[i][0]);
			arr[i][1] = rank(m, arr[i][1] - 1);
		}
		Arrays.sort(arr, 0, n, (a, b) -> a[0] - b[0]);
		Arrays.fill(height, 1, m + 1, 0);
		return m;
	}

	public static int rank(int n, int v) {
		int ans = 0;
		int l = 1, r = n, mid;
		while (l <= r) {
			mid = (l + r) >> 1;
			if (xsort[mid] >= v) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

	public static boolean isEmpty() {
		return heapSize == 0;
	}

	public static int peekHeight() {
		return heap[0][0];
	}

	public static int peekEnd() {
		return heap[0][1];
	}

	public static void push(int h, int e) {
		heap[heapSize][0] = h;
		heap[heapSize][1] = e;
		heapInsert(heapSize++);
	}

	public static void poll() {
		swap(0, --heapSize);
		heapify(0);
	}

	public static void heapInsert(int i) {
		while (heap[i][0] > heap[(i - 1) / 2][0]) {
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}

	public static void heapify(int i) {
		int l = i * 2 + 1;
		while (l < heapSize) {
			int best = l + 1 < heapSize && heap[l + 1][0] > heap[l][0] ? l + 1 : l;
			best = heap[best][0] > heap[i][0] ? best : i;
			if (best == i) {
				break;
			}
			swap(best, i);
			i = best;
			l = i * 2 + 1;
		}
	}

	public static void swap(int i, int j) {
		int[] tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
	}

}
