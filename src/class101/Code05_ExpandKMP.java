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
		for (int i = 2, k = 1, j, r; i < n; i++) {
			r = k + zxt[k];
			j = zxt[i - k];
			if (i + j < r) {
				zxt[i] = j;
			} else {
				j = Math.max(0, r - i);
				while (i + j < n && s[i + j] == s[j]) {
					// 一旦成功就让右边界更往右了，而右边界最多走到n
					// 所以不要在乎每次while的代价
					// 要关注所有while行为的总代价为O(n)
					// 这一点和Manacher算法时间复杂度的估计很像
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
		for (int i = 1, k = 0, j, r; i < n; i++) {
			r = k + ext[k];
			j = zxt[i - k];
			if (i + j < r) {
				ext[i] = j;
			} else {
				j = Math.max(0, r - i);
				while (i + j < n && j < m && a[i + j] == b[j]) {
					// 一旦成功就让右边界更往右了，而右边界最多走到n
					// 所以不要在乎每次while的代价
					// 要关注所有while行为的总代价为O(n)
					// 这一点和Manacher算法时间复杂度的估计很像
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
