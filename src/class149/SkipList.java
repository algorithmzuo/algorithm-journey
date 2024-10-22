package class149;

// 跳表的实现
// 测试链接 : https://www.luogu.com.cn/problem/P3369
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

// 待实现
public class SkipList {

	public static void add(int k) {

	}

	public static void remove(int k) {

	}

	public static int rank(int k) {
		return 0;
	}

	public static int index(int x) {
		return 0;
	}

	public static int pre(int x) {
		return 0;
	}

	public static int post(int x) {
		return 0;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		for (int i = 1, op, x; i <= n; i++) {
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			x = (int) in.nval;
			if (op == 1) {
				add(x);
			} else if (op == 2) {
				remove(x);
			} else if (op == 3) {
				out.println(rank(x));
			} else if (op == 4) {
				out.println(index(x));
			} else if (op == 5) {
				out.println(pre(x));
			} else {
				out.println(post(x));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
