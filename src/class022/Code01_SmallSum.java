package class022;

// 小和问题
// 测试链接 : https://www.nowcoder.com/practice/edfe05a1d45c4ea89101d936cac32469
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_SmallSum {

	public static int MAXN = 100001;

	public static int[] arr = new int[MAXN];

	public static int[] help = new int[MAXN];

	public static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			for (int i = 0; i < n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
			}
			out.println(smallSum(0, n - 1));
		}
		out.flush();
		out.close();
	}

	// 结果比较大，用int会溢出的，所以返回long类型
	// 特别注意溢出这个点，笔试常见坑
	// 返回arr[l...r]范围上，小和的累加和，同时请把arr[l..r]变有序
	// 时间复杂度O(n * logn)
	public static long smallSum(int l, int r) {
		if (l == r) {
			return 0;
		}
		int m = (l + r) / 2;
		return smallSum(l, m) + smallSum(m + 1, r) + merge(l, m, r);
	}

	// 返回跨左右产生的小和累加和，左侧有序、右侧有序，让左右两侧整体有序
	// arr[l...m] arr[m+1...r]
	public static long merge(int l, int m, int r) {
		// 统计部分
		long ans = 0;
		for (int j = m + 1, i = l, sum = 0; j <= r; j++) {
			while (i <= m && arr[i] <= arr[j]) {
				sum += arr[i++];
			}
			ans += sum;
		}
		// 正常merge
		int i = l;
		int a = l;
		int b = m + 1;
		while (a <= m && b <= r) {
			help[i++] = arr[a] <= arr[b] ? arr[a++] : arr[b++];
		}
		while (a <= m) {
			help[i++] = arr[a++];
		}
		while (b <= r) {
			help[i++] = arr[b++];
		}
		for (i = l; i <= r; i++) {
			arr[i] = help[i];
		}
		return ans;
	}

}