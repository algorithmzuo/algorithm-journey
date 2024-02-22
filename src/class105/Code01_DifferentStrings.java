package class105;

// 统计有多少个不同的字符串
// 测试链接 : https://www.luogu.com.cn/problem/P3370
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code01_DifferentStrings {

	public static int MAXN = 10001;

	public static int base = 499;

	public static long[] nums = new long[MAXN];

	public static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = Integer.valueOf(in.readLine());
		for (int i = 0; i < n; i++) {
			nums[i] = value(in.readLine().toCharArray());
		}
		out.println(cnt());
		out.flush();
		out.close();
		in.close();
	}

	public static long value(char[] s) {
		long ans = v(s[0]);
		for (int i = 1; i < s.length; i++) {
			ans = ans * base + v(s[i]);
		}
		return ans;
	}

	// 数字 + 大写 + 小写
	// '0' -> 1
	// '1' -> 2
	// ...
	// '9' -> 10
	// 'A' -> 11
	// 'B' -> 12
	// ...
	// 'Z' -> 36
	// 'a' -> 37
	// ...
	// 'z' -> 62
	public static int v(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0' + 1;
		} else if (c >= 'A' && c <= 'z') {
			return c - 'A' + 11;
		} else {
			return c - 'a' + 37;
		}
	}

	public static int cnt() {
		Arrays.sort(nums, 0, n);
		int ans = 1;
		for (int i = 1; i < n; i++) {
			if (nums[i] != nums[i - 1]) {
				ans++;
			}
		}
		return ans;
	}

}
