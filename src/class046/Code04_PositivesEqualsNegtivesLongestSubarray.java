package class046;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.HashMap;

// 返回无序数组中正数和负数个数相等的最长子数组长度
// 给定一个无序数组arr，其中元素可正、可负、可0
// 求arr所有子数组中正数与负数个数相等的最长子数组的长度
// 测试链接 : https://www.nowcoder.com/practice/545544c060804eceaed0bb84fcd992fb
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过
public class Code04_PositivesEqualsNegtivesLongestSubarray {

	public static int MAXN = 100001;

	public static int[] arr = new int[MAXN];

	public static int n;

	public static HashMap<Integer, Integer> map = new HashMap<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			for (int i = 0, num; i < n; i++) {
				in.nextToken();
				num = (int) in.nval;
				arr[i] = num != 0 ? (num > 0 ? 1 : -1) : 0;
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		map.clear();
		map.put(0, -1);
		int ans = 0;
		for (int i = 0, sum = 0; i < n; i++) {
			sum += arr[i];
			if (map.containsKey(sum)) {
				ans = Math.max(ans, i - map.get(sum));
			} else {
				map.put(sum, i);
			}
		}
		return ans;
	}

}
