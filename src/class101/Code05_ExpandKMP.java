package class101;

// 扩展KMP，又称Z函数
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

public class Code05_ExpandKMP {

	public static int MAXN = 20000001;

	public static int[] zxt = new int[MAXN];

	public static int[] ext = new int[MAXN];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		char[] a = br.readLine().toCharArray();
		char[] b = br.readLine().toCharArray();
		znext(b, b.length);
		exkmp(a, b, a.length, b.length);
		out.println(eor(zxt, b.length));
		out.println(eor(ext, a.length));
		out.flush();
		out.close();
		br.close();
	}

	public static void znext(char[] s, int n) {
		zxt[0] = n;
		for (int i = 0, j = 1; j < n && s[i] == s[j]; i++, j++) {
			zxt[1]++;
		}
		for (int i = 2, k = 1, p, l, j; i < n; i++) {
			p = k + zxt[k] - 1;
			l = zxt[i - k];
			if (i + l <= p) {
				zxt[i] = l;
			} else {
				j = Math.max(0, p - i + 1);
				while (i + j < n && s[i + j] == s[j]) {
					j++;
				}
				zxt[i] = j;
				k = i;
			}
		}
	}

	public static void exkmp(char[] a, char[] b, int n, int m) {
		for (int i = 0; i < n && i < m && a[i] == b[i]; i++) {
			ext[0]++;
		}
		for (int i = 1, k = 0, p, l, j; i < n; i++) {
			p = k + ext[k] - 1;
			l = zxt[i - k];
			if (i + l <= p) {
				ext[i] = l;
			} else {
				j = Math.max(0, p - i + 1);
				while (i + j < n && j < m && a[i + j] == b[j]) {
					j++;
				}
				ext[i] = j;
				k = i;
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
