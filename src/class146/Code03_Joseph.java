package class146;

// 约瑟夫环问题
// 测试链接 : https://www.luogu.com.cn/problem/P8671
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_Joseph {

	public static int compute(int n, int k) {
		int ans = 1;
		for (int c = 2; c <= n; c++) {
			ans = (ans + k - 1) % c + 1;
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int k = (int) in.nval;
		out.println(compute(n, k));
		out.close();
		br.close();
	}

}
