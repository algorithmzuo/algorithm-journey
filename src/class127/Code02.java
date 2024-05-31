package class127;

// 青蛙上学至少的跳跃能力
// 青蛙住在一条河边, 每天到河对岸的上学
// 河里的石头排成了一条直线，青蛙每次跳跃必须落在一块石头或者岸上
// 给定一个长度为n的数组arr，表示每块儿石头的高度数值
// 每次青蛙从一块石头起跳，这块石头的高度就会下降1
// 当石头的高度下降到0时，青蛙不能再跳到这块石头上
// 跳跃后使石头高度下降到0是允许的
// 青蛙一共需要去学校上x天课, 所以它需要往返x次
// 青蛙具有跳跃能力y, 它可以跳跃不超过y的距离
// 请问青蛙的跳跃能力至少是多少，才能用这些石头往返x次
// 1 <= n <= 10^5
// 1 <= arr[i] <= 10^4
// 1 <= x <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P8775
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02 {

	public static int MAXN = 100001;

	public static long[] help = new long[MAXN];

	public static int n, x;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		x = (int) in.nval;
		for (int i = 1; i < n; i++) {
			in.nextToken();
			help[i] = help[i - 1] + (int) in.nval;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		int ans = 0;
		for (int l = 1, r = 1; l < n; l++) {
			while (r < n && help[r] - help[l - 1] < 2L * x) {
				r++;
			}
			ans = Math.max(ans, r - l + 1);
		}
		return ans;
	}

}