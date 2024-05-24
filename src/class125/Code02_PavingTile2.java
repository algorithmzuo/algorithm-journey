package class125;

// 空间压缩的版本
// 测试链接 : http://poj.org/problem?id=2411
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_PavingTile2 {

	public static int MAXN = 11;

	public static int n;

	public static int m;

	public static int maxs;

	public static long[][] dp = new long[MAXN + 1][1 << MAXN];

	public static long[] prepare = new long[1 << MAXN];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			maxs = 1 << m;
			if (n != 0 || m != 0) {
				out.println(compute());
			}
		}
		out.flush();
		out.close();
		br.close();
	}

	public static long compute() {
		Arrays.fill(prepare, 0, maxs, 0);
		prepare[0] = 1;
		for (int i = n - 1; i >= 0; i--) {
			for (int s = 0; s < maxs; s++) {
				dp[m][s] = prepare[s];
			}
			for (int j = m - 1; j >= 0; j--) {
				for (int s = 0; s < maxs; s++) {
					long ans;
					if (((s >> j) & 1) == 1) {
						ans = dp[j + 1][s ^ (1 << j)];
					} else {
						ans = dp[j + 1][s | (1 << j)];
						if (j + 1 < m && ((s >> (j + 1)) & 1) == 0) {
							ans += dp[j + 2][s];
						}
					}
					dp[j][s] = ans;
				}
			}
			for (int s = 0; s < maxs; s++) {
				prepare[s] = dp[0][s];
			}
		}
		return dp[0][0];
	}

}
