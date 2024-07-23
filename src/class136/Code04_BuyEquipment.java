package class136;

// 装备购买
// 测试链接 : https://www.luogu.com.cn/problem/P3265
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_BuyEquipment {

	public static int MAXN = 502;

	public static int MAXM = 502;

	public static double sml = 1e-5;

	public static double[][] mat = new double[MAXN][MAXM];

	// 记录的是编号不是状态
	public static int[] basis = new int[MAXN];

	public static int n, m;

	public static int cnt, ans;

	// 普通消元
	public static void compute() {
		cnt = ans = 0;
		Arrays.sort(mat, 1, n + 1, (a, b) -> a[m + 1] <= b[m + 1] ? -1 : 1);
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				if (Math.abs(mat[i][j]) >= sml) {
					if (basis[j] == 0) {
						basis[j] = i;
						cnt++;
						ans += (int) mat[i][m + 1];
						break;
					}
					double rate = mat[i][j] / mat[basis[j]][j];
					for (int k = j; k <= m; k++) {
						mat[i][k] -= rate * mat[basis[j]][k];
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				in.nextToken();
				mat[i][j] = (double) in.nval;
			}
		}
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			mat[i][m + 1] = (double) in.nval;
		}
		compute();
		out.println(cnt + " " + ans);
		out.flush();
		out.close();
		br.close();
	}

}