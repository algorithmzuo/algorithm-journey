package class117;

// 出现次数最多的数有几个
// 给定一个长度为n的数组arr，该数组一定是有序的
// 一共有m次查询，每次查询arr[l~r]上出现次数最多的数有几个
// 题目查看 : https://www.luogu.com.cn/problem/UVA11235
// 题目提交 : https://vjudge.net/problem/UVA-11235
// 洛谷上提交会发生远程连接错误，需要绑定一个UVA账号再提交
// 也可以注册一个vjudge账号来提交，不过如果UVA链接不上就需要等
// 其实用对数器验证即可，我肯定是提交通过测试了的
// 提交以下的code，提交时请把类名改成"Main"

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_FrequentValues2 {

	public static int MAXN = 100001;

	public static int LIMIT = 17;

	public static int[] arr = new int[MAXN];

	public static int[] log2 = new int[MAXN];

	public static int[] bucket = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[][] stmax = new int[MAXN][LIMIT];

	public static void build(int n) {
		// 题目给定的数值范围-100000 ~ +100000
		// 把arr[0]设置成不会到达的数字即可
		arr[0] = -23333333;
		int cnt = 0;
		for (int i = 1; i <= n; i++) {
			if (arr[i - 1] != arr[i]) {
				right[cnt] = i - 1;
				left[++cnt] = i;
			}
			bucket[i] = cnt;
		}
		right[cnt] = n;
		log2[0] = -1;
		for (int i = 1; i <= cnt; i++) {
			log2[i] = log2[i >> 1] + 1;
			stmax[i][0] = right[i] - left[i] + 1;
		}
		for (int p = 1; p <= log2[cnt]; p++) {
			for (int i = 1; i + (1 << p) - 1 <= cnt; i++) {
				stmax[i][p] = Math.max(stmax[i][p - 1], stmax[i + (1 << (p - 1))][p - 1]);
			}
		}
	}

	public static int query(int l, int r) {
		if (l > r) {
			int tmp = l;
			l = r;
			r = tmp;
		}
		int lbucket = bucket[l];
		int rbucket = bucket[r];
		if (lbucket == rbucket) {
			return r - l + 1;
		}
		int a = right[lbucket] - l + 1, b = r - left[rbucket] + 1, c = 0;
		if (lbucket + 1 < rbucket) {
			int from = lbucket + 1, to = rbucket - 1, p = log2[to - from + 1];
			c = Math.max(stmax[from][p], stmax[to - (1 << p) + 1][p]);
		}
		return Math.max(Math.max(a, b), c);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			if (n == 0) {
				break;
			}
			in.nextToken();
			int m = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				arr[i] = (int) in.nval;
			}
			build(n);
			for (int i = 1, l, r; i <= m; i++) {
				in.nextToken();
				l = (int) in.nval;
				in.nextToken();
				r = (int) in.nval;
				out.println(query(l, r));
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
