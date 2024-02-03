package class104;

// 最长双回文串的长度
// 输入字符串s，求s的最长双回文子串t的长度
// 双回文子串t，就是可以把t分成x、y两个回文串的字符串
// 比如"aabb"，可以分成"aa"、"bb"
// 测试链接 : https://www.luogu.com.cn/problem/P4555
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_LongestDoublePalindrome {

	public static char[] s;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		s = in.readLine().toCharArray();
		out.println(compute());
		out.flush();
		out.close();
		in.close();
	}

	// 待完成
	public static int compute() {
		return 1;
	}

}
