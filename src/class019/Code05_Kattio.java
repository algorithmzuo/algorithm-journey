package class019;

// 本文件课上没有讲，介绍一下Kattio类的使用
// 某些题目的输入，使用StreamTokenizer就是无法正确读入
// 那么可以使用本文件提供的Kattio类
// 比如，就是需要依次读取一个一个的字符串进行处理
// 再比如，StreamTokenizer读取：不溢出、但是很大的long类型数字时，可能会读入错误
// 再比如，StreamTokenizer读取：科学计数法表达的double类型数字时，可能会读入错误
// 如果使用Kattio进行读取，就没有这些问题
// 可以直接运行本文件的main函数，根据提示输入给定的数字，能清晰的看到这一点
// 那么可不可以放弃StreamTokenizer，以后都用Kattio呢？
// 不行！因为StreamTokenizer的效率还是比Kattio好！
// 只有在StreamTokenizer无法正确读取的情况下，才考虑使用Kattio类
// 其他语言中一定有对等的概念，不是java的同学，请自行搞定

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.StringTokenizer;

public class Code05_Kattio {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		System.out.println("请输入 : 131237128371723187");
		System.out.println("并按回车");
		in.nextToken();
		long long1 = (long) in.nval;
		System.out.println("StreamTokenizer读取到的数字 : ");
		System.out.println(long1);
		System.out.println();
		System.out.println("请输入 : 5.6920E+0001");
		System.out.println("并按回车");
		in.nextToken();
		double double1 = in.nval;
		System.out.println("StreamTokenizer读取到的数字 : ");
		System.out.println(double1);

		System.out.println("============================");

		Kattio io = new Kattio();
		System.out.println("请输入 : 131237128371723187");
		System.out.println("并按回车");
		long long2 = io.nextLong();
		System.out.println("Kattio读取到的数字 : ");
		System.out.println(long2);
		System.out.println();
		System.out.println("请输入 : 5.6920E+0001");
		System.out.println("并按回车");
		double double2 = io.nextDouble();
		System.out.println("Kattio读取到的数字 : ");
		System.out.println(double2);
		io.close();
	}

	// 如何使用Kattio的简单示例
	// 可以找个一些具体题目试一试
	// 这里就是罗列了一下
	public static void show() {
		Kattio io = new Kattio(); // 自动接入输入输出流
		io.next(); // 读取下一个字符串，注意不是整行，是以空格或回车分割的字符串，一个一个读取
		io.nextInt(); // 读取下一个int
		io.nextDouble(); // 读取下一个double
		io.nextLong(); // 读取下一个long
		io.println("ans"); // 答案进入输出流
		io.flush(); // 答案刷给后台
		io.close(); // 关闭io
	}

	// Kattio类IO效率很好，但还是不如StreamTokenizer
	// 只有StreamTokenizer无法正确处理时，才考虑使用这个类
	// 参考链接 : https://oi-wiki.org/lang/java-pro/
	public static class Kattio extends PrintWriter {
		private BufferedReader r;
		private StringTokenizer st;

		public Kattio() {
			this(System.in, System.out);
		}

		public Kattio(InputStream i, OutputStream o) {
			super(o);
			r = new BufferedReader(new InputStreamReader(i));
		}

		public Kattio(String intput, String output) throws IOException {
			super(output);
			r = new BufferedReader(new FileReader(intput));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreTokens())
					st = new StringTokenizer(r.readLine());
				return st.nextToken();
			} catch (Exception e) {
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}
