package class101;

// 扩展KMP
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

	public static char[] a;

	public static char[] b;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		a = br.readLine().toCharArray();
		b = br.readLine().toCharArray();
		compute();
		out.println();
		out.flush();
		out.close();
		br.close();
	}

	public static void compute() {

	}

}
