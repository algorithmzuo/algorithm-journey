package class019;

// 展示acm风格的测试方式
// 测试链接 : https://www.nowcoder.com/exam/test/70070648/detail?pid=27976983
// 其中，7.A+B(7)，就是一个没有给定数据规模，只能按行读数据的例子
// 此时需要自己切分出数据来计算
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_ReadByLine {

	public static String line;

	public static String[] parts;

	public static int sum;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while ((line = in.readLine()) != null) {
			parts = line.split(" ");
			sum = 0;
			for (String num : parts) {
				sum += Integer.valueOf(num);
			}
			out.println(sum);
		}
		out.flush();
		in.close();
		out.close();
	}

}
