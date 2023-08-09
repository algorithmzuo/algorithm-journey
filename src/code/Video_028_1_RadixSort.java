package code;

// 基数排序，acm练习风格
// 测试链接 : https://www.nowcoder.com/practice/96c0717e2ed849219748796956291a22
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

public class Video_028_1_RadixSort {

	// 可以设置进制，不一定10进制，随你设置
	public static int BASE = 10;

	// 题目没有说数据量，按道理是要说的
	// 根据实验，长度500以内够用了
	// 如果有一天牛客升级了数据量导致出错
	// 把这个值改大即可
	public static int MAXN = 501;

	public static int[] arr = new int[MAXN];

	public static int[] help = new int[MAXN];

	public static int[] cnts = new int[BASE];

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
			sort();
			out.print(arr[0]);
			for (int i = 1; i < n; i++) {
				out.print(" " + arr[i]);
			}
			out.println();
		}
		out.flush();
		out.close();
	}

	public static void sort() {
		int min = arr[0];
		for (int i = 1; i < n; i++) {
			min = Math.min(min, arr[i]);
		}
		int max = 0;
		for (int i = 0; i < n; i++) {
			arr[i] -= min;
			max = Math.max(max, arr[i]);
		}
		radixSort(bits(max));
		for (int i = 0; i < n; i++) {
			arr[i] += min;
		}
	}

	// 返回number在BASE进制下有几位
	public static int bits(int number) {
		int ans = 0;
		while (number > 0) {
			ans++;
			number /= BASE;
		}
		return ans;
	}

	// 基数排序核心代码
	// arr内要保证没有负数
	// m是arr中最大值在BASE进制下有几位
	public static void radixSort(int m) {
		// 理解的时候可以假设BASE = 10
		for (int offset = 1; m > 0; offset *= BASE, m--) {
			Arrays.fill(cnts, 0);
			for (int i = 0; i < n; i++) {
				cnts[(arr[i] / offset) % BASE]++;
			}
			for (int i = 1; i < BASE; i++) {
				cnts[i] = cnts[i] + cnts[i - 1];
			}
			for (int i = n - 1; i >= 0; i--) {
				help[--cnts[(arr[i] / offset) % BASE]] = arr[i];
			}
			for (int i = 0; i < n; i++) {
				arr[i] = help[i];
			}
		}
	}

}