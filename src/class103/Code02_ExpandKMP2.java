package class103;

// 扩展KMP，又称Z算法(洛谷威力加强版)
// 给定两个字符串a、b，求出两个数组
// b与b每一个后缀串的最长公共前缀长度
// b与a每一个后缀串的最长公共前缀长度
// 计算出要求的两个数组后，输出这两个数组的权值即可
// 对于一个数组x，i位置的权值定义为 : (i * (x[i] + 1))
// 数组权值为所有位置权值的异或和
// 测试链接 : https://www.luogu.com.cn/problem/P5410
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// 扩展KMP和KMP算法过程差别挺大，反而和Manacher算法非常像
public class Code02_ExpandKMP2 {

	public static int MAXN = 20000001;

	public static int[] zxt = new int[MAXN];

	public static int[] ext = new int[MAXN];

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		char[] a = in.readLine().toCharArray();
		char[] b = in.readLine().toCharArray();
		znext(b, b.length);
		exkmp(a, b, a.length, b.length);
		out.println(eor(zxt, b.length));
		out.println(eor(ext, a.length));
		out.flush();
		out.close();
		in.close();
	}

	public static void znext(char[] s, int n) {
		zxt[0] = n;
		for (int i = 0, j = 1; j < n && s[i] == s[j]; i++, j++) {
			zxt[1]++;
		}
		for (int i = 2, t = 1, r = 1 + zxt[1]; i < n; i++) {
			zxt[i] = Math.max(0, Math.min(r - i, zxt[i - t]));
			while (i + zxt[i] < n && s[i + zxt[i]] == s[zxt[i]]) {
				zxt[i]++;
			}
			if (i + zxt[i] > r) {
				r = i + zxt[i];
				t = i;
			}
		}
	}

	public static void exkmp(char[] a, char[] b, int n, int m) {
		for (int i = 0; i < n && i < m && a[i] == b[i]; i++) {
			ext[0]++;
		}
		for (int i = 1, t = 0, r = ext[0]; i < n; i++) {
			ext[i] = Math.max(0, Math.min(r - i, zxt[i - t]));
			while (i + ext[i] < n && ext[i] < m && a[i + ext[i]] == b[ext[i]]) {
				ext[i]++;
			}
			if (i + ext[i] > r) {
				r = i + ext[i];
				t = i;
			}
		}
	}

	public static long eor(int[] arr, int n) {
		long ans = 0;
		for (int i = 0; i < n; i++) {
			ans ^= (long) (i + 1) * (arr[i] + 1);
		}
		return ans;
	}

}
