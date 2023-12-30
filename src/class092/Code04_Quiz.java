package class092;

// 知识竞赛
// 最近部门要选两个员工去参加一个需要合作的知识竞赛，
// 每个员工均有一个推理能力值ai，以及一个阅读能力值bi
// 如果选择第i个人和第j个人去参加竞赛，
// 两人在推理方面的能力为X = (ai + aj)/2
// 两人在阅读方面的能力为Y = (bi + bj)/2
// 现在需要最大化他们表现较差一方面的能力
// 即让min(X,Y) 尽可能大，问这个值最大是多少
// 测试链接 : https://www.nowcoder.com/practice/2a9089ea7e5b474fa8f688eae76bc050
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

public class Code04_Quiz {

	public static int MAXN = 200001;

	public static int[][] nums = new int[MAXN][2];

	public static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			for (int i = 0; i < n; i++) {
				in.nextToken();
				nums[i][0] = (int) in.nval;
				in.nextToken();
				nums[i][1] = (int) in.nval;
			}
			int ans = compute();
			out.println((double) ans / 2);
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		Arrays.sort(nums, 0, n, (a, b) -> Math.abs(a[0] - a[1]) - Math.abs(b[0] - b[1]));
		int maxA = nums[0][0]; // 左边最大的推理能力
		int maxB = nums[0][1]; // 左边最大的阅读能力
		int ans = 0;
		for (int i = 1; i < n; i++) {
			if (nums[i][0] <= nums[i][1]) {
				ans = Math.max(ans, maxA + nums[i][0]);
			} else {
				ans = Math.max(ans, maxB + nums[i][1]);
			}
			maxA = Math.max(maxA, nums[i][0]);
			maxB = Math.max(maxB, nums[i][1]);
		}
		return ans;
	}

}
