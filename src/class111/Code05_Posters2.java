package class111;

// 贴海报(数据加强版)
// 有一面墙，有固定高度，无限的宽度，有n张海报，所有海报的高度都和墙的高度相同
// 从第1张海报开始，一张一张往墙上贴，直到n张海报贴完
// 每张海报都给出张贴位置(xi, yi)，表示第i张海报从墙的左边界xi一直延伸到右边界yi
// 有可能发生后面的海报把前面的海报完全覆盖，导致看不到的情况
// 当所有海报贴完，返回能看到海报的数量，哪怕只漏出一点的海报都算
// 1 <= n <= 10^5，1 <= xi、yi <= 10^7
// 测试链接 : http://poj.org/problem?id=2528
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_Posters2 {

	public static int MAXN = 10001;

	public static int[] pl = new int[MAXN];

	public static int[] pr = new int[MAXN];

	public static int[] num = new int[MAXN << 2];

	// 线段树的某个范围上是否被设置成了统一的海报
	// 如果poster[i] != 0，poster[i]表示统一海报的编号
	// 如果poster[i] == 0，表示该范围上没有海报或者海报编号没统一
	public static int[] poster = new int[MAXN << 4];

	// 某种海报编号是否已经被统计过了
	// 只在最后一次查询，最后统计海报数量的阶段时候使用
	public static boolean[] visited = new boolean[MAXN];

	public static int collect(int m) {
		Arrays.sort(num, 1, m + 1);
		int size = 1;
		for (int i = 2; i <= m; i++) {
			if (num[size] != num[i]) {
				num[++size] = num[i];
			}
		}
		int cnt = size;
		for (int i = 2; i <= size; i++) {
			if (num[i - 1] + 1 < num[i]) {
				num[++cnt] = num[i - 1] + 1;
			}
		}
		Arrays.sort(num, 1, cnt + 1);
		return cnt;
	}

	public static int rank(int l, int r, int v) {
		int m;
		int ans = 0;
		while (l <= r) {
			m = (l + r) >> 1;
			if (num[m] >= v) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static void down(int i) {
		if (poster[i] != 0) {
			poster[i << 1] = poster[i];
			poster[i << 1 | 1] = poster[i];
			poster[i] = 0;
		}
	}

	public static void build(int l, int r, int i) {
		if (l < r) {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
		}
		poster[i] = 0;
	}

	public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			poster[i] = jobv;
		} else {
			down(i);
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				update(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (l == r) {
			if (poster[i] != 0 && !visited[poster[i]]) {
				visited[poster[i]] = true;
				return 1;
			} else {
				return 0;
			}
		} else {
			down(i);
			int mid = (l + r) >> 1;
			int ans = 0;
			if (jobl <= mid) {
				ans += query(jobl, jobr, l, mid, i << 1);
			}
			if (jobr > mid) {
				ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
			}
			return ans;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int cases = (int) in.nval;
		for (int t = 1; t <= cases; t++) {
			in.nextToken();
			int n = (int) in.nval;
			int m = 0;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				pl[i] = (int) in.nval;
				in.nextToken();
				pr[i] = (int) in.nval;
				num[++m] = pl[i];
				num[++m] = pr[i];
			}
			m = collect(m);
			build(1, m, 1);
			for (int i = 1, jobl, jobr; i <= n; i++) {
				jobl = rank(1, m, pl[i]);
				jobr = rank(1, m, pr[i]);
				update(jobl, jobr, i, 1, m, 1);
			}
			out.println(query(1, m, 1, m, 1));
			Arrays.fill(visited, 1, n + 1, false);
		}
		out.flush();
		out.close();
		br.close();
	}

}