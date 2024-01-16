package class097;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

// 读入long类型数字的注意点
public class Code02_InputLong {

	public static void main(String[] args) throws IOException {
		f1();
		f2();
	}

	public static void f1() throws IOException {
		System.out.println("f1函数测试读入");
		// 尝试读入 : 131237128371723187
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		// in.nval是double类型
		// double类型64位
		// long类型也是64位
		// 但是double类型的64位会分配若干位给小数
		// 而long类型的64位全用来做整数
		// 所以当读入数字在long的范围内
		// in.nval会先变成double类型，再转成long类型
		// 这里就可能有精度耗损
		long num = (long) in.nval;
		out.println(num);
		out.flush();
	}

	public static void f2() throws IOException {
		System.out.println("f2函数测试读入");
		// 尝试读入 : 131237128371723187
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		String str = br.readLine();
		long num = Long.valueOf(str);
		out.println(num);
		out.flush();
	}

}
