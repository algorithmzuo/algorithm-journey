package class108;

// 树状数组单点增加、范围查询模版
// 测试链接 : https://www.luogu.com.cn/problem/P3374
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_IndexTreeSingleAddIntervalQuery {

	public static int MAXN = 500001;

	// 原始数组的信息，根据课上说的关系，维护在树状数组中
	// 注意下标一定从1开始，不从0开始
	public static int[] tree = new int[MAXN];

	public static int n, m;

	// 得到i最右侧的1的状态
	// 其他位都是0
	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		while (i <= n) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	// 返回1~i范围累加和
	public static int sum(int i) {
		int ans = 0;
		while (i > 0) {
			ans += tree[i];
			i -= lowbit(i);
		}
		return ans;
	}

	public static int range(int l, int r) {
		return sum(r) - sum(l - 1);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			for (int i = 1, v; i <= n; i++) {
				in.nextToken();
				v = (int) in.nval;
				add(i, v);
			}
			for (int i = 1, a, b, c; i <= m; i++) {
				in.nextToken();
				a = (int) in.nval;
				in.nextToken();
				b = (int) in.nval;
				in.nextToken();
				c = (int) in.nval;
				if (a == 1) {
					add(b, c);
				} else {
					out.println(range(b, c));
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
