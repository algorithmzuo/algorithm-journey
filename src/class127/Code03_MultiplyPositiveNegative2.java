package class127;

// 感谢热心的同学，找到了题目3的在线测试
// 测试链接 : https://codeforces.com/problemset/problem/1215/B
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_MultiplyPositiveNegative2 {

	public static int MAXN = 200001;

	public static int n;

	public static int[] arr = new int[MAXN];

	// 结果可能很大，所以用long类型
	public static long ans1, ans2;

	public static void compute() {
		int[] cnt = new int[2];
		cnt[0] = 1;
		cnt[1] = 0;
		ans1 = ans2 = 0;
		for (int i = 1, cur = 0; i <= n; i++) {
			cur ^= arr[i] > 0 ? 0 : 1;
			ans1 += cnt[cur];
			ans2 += cnt[cur ^ 1];
			cnt[cur]++;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		compute();
		out.println(ans2 + " " + ans1);
		out.flush();
		out.close();
		br.close();
	}

}
