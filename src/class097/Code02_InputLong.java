package class097;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

// 读入long类型数字的注意点
// 讲解019的扩展，没有看过讲解019的同学去看一下
public class Code02_InputLong {

	public static void main(String[] args) throws IOException {
		f1();
		f2();
	}

	public static void f1() throws IOException {
		System.out.println("f1函数测试读入");
		// 尝试读入 : 131237128371723187
		// in.nval读出的是double类型
		// double类型64位
		// long类型也是64位
		// double的64位会分配若干位去表达小数部分
		// long类型的64位全用来表达整数部分
		// 所以读入是long范围的数，如果用以下的写法
		// in.nval会先变成double类型，如果再转成long类型，就可能有精度损耗
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		long num = (long) in.nval;
		out.println(num);
		out.flush();
	}

	public static void f2() throws IOException {
		System.out.println("f2函数测试读入");
		// 尝试读入 : 131237128371723187
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		// 直接读出字符串
		String str = br.readLine();
		// 然后把字符串转成long
		// 不可能有精度损耗
		long num = Long.valueOf(str);
		out.println(num);
		out.flush();
	}

}
