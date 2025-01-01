package class156;

// 食物链真假判断
// 测试链接 : https://www.luogu.com.cn/problem/P2024
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_FoodChainTrueOrFalse {

	public static int MAXN = 50001;

	public static int n, k, ans;

	public static int[] father = new int[MAXN];

	public static int[] relation = new int[MAXN];

	public static void prepare() {
		ans = 0;
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			relation[i] = 0;
		}
	}

	public static int find(int i) {
		if (i != father[i]) {
			int tmp = father[i];
			father[i] = find(tmp);
			relation[i] = (relation[i] + relation[tmp]) % 3;
		}
		return father[i];
	}

	public static void union(int op, int l, int r) {
		int lf = find(l), rf = find(r), v = op == 1 ? 0 : 1;
		if (lf != rf) {
			father[lf] = rf;
			relation[lf] = (relation[r] - relation[l] + v + 3) % 3;
		}
	}

	public static boolean check(int op, int l, int r) {
		if (l > n || r > n || (op == 2 && l == r)) {
			return false;
		}
		if (find(l) == find(r)) {
			if (op == 1) {
				if (relation[l] != relation[r]) {
					return false;
				}
			} else {
				if ((relation[l] - relation[r] + 3) % 3 != 1) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		k = (int) in.nval;
		prepare();
		for (int i = 1, op, x, y; i <= k; i++) {
			in.nextToken();
			op = (int) in.nval;
			in.nextToken();
			x = (int) in.nval;
			in.nextToken();
			y = (int) in.nval;
			if (!check(op, x, y)) {
				ans++;
			} else {
				union(op, x, y);
			}
		}
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
