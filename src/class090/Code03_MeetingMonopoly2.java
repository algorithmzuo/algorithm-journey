package class090;

// 同学找到了在线测试，看似是题目3，其实不是
// 区别在于数据量，如下测试链接中的题目
// 会议的数量10^6，会议的开始、结束时间也是10^6
// 排序会超时，C++的同学可能不会，但是java的同学会
// 而且根据这个数据量，最优解一定不是排序！
// 会议开始、结束时间也是10^6规模，所以不排序也可以做，具体看如下代码和注释
// 并不是课上讲错了，而是这个数据状况太特殊
// 测试链接 : https://www.luogu.com.cn/problem/P1803
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_MeetingMonopoly2 {

	// 既是会议的规模，也是开始、结束时间的规模
	public static int MAXN = 1000001;

	// latest[60] == 40
	// 表示 : 结束时间是60的所有会议中，最晚开始的会议是40的时候开始
	// 比如会议[1, 60]、[30, 60]、[40, 60]
	// 这些会议都在60结束，但是最晚开始的会议是40开始
	// 如果latest[60] == -1
	// 表示没有任何会议在60结束
	public static int[] latest = new int[MAXN];

	public static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			for (int i = 0; i < MAXN; i++) {
				latest[i] = -1;
			}
			for (int i = 0, start, end; i < n; i++) {
				in.nextToken();
				start = (int) in.nval;
				in.nextToken();
				end = (int) in.nval;
				if (latest[end] == -1) {
					// 如果结束时间在end的会议之前没发现过
					// 现在发现了
					latest[end] = start;
				} else {
					// 如果结束时间在end的会议之前发现过
					// 记录最晚的开始时间
					latest[end] = Math.max(latest[end], start);
				}
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		int ans = 0;
		// 不排序
		// 根据时间遍历
		for (int cur = 0, end = 0; end < MAXN; end++) {
			// cur : cur之前不能再安排会议，因为安排会议的人来到了cur时刻
			// end是当前的结束时间
			// 所有以end结束的会议，最晚的开始时间是latest[end]
			// 如果cur <= latest[end]，那么说明可以安排当前以end结束的会议
			if (cur <= latest[end]) {
				ans++;
				cur = end; // 安排之后，目前安排会议的人来到end时刻
			}
		}
		return ans;
	}

}
