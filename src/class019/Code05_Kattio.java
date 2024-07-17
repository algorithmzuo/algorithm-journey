package class019;

// 本文件课上没有讲，介绍一下Kattio类的使用
// 如果题目输入就是无法一个一个的读出数字，使用StreamTokenizer就是怎么都无法正确读入
// 那么可以使用本文件提供的Kattio类
// 比如就是需要读入整行字符串进行处理的时候
// 再比如，StreamTokenizer读取很大的数字时会有精度问题
// 读取不溢出、但是很大的double、long类型的数字时，可能会读不到正确输入
// 你可以尝试用StreamTokenizer读取这个数字：131237128371723187
// 会发现读取的结果不对
// 但是用Kattio.nextLong()进行读取，就没有这个问题
// 可以直接运行本文件的main函数，能清晰的看到这一点
// 那么可不可以放弃StreamTokenizer，以后都用Kattio呢？
// 不行！因为StreamTokenizer的效率还是比Kattio好的
// 只有在StreamTokenizer无法正确读取的情况下，才考虑使用Kattio类

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
		System.out.println("请输入 : 131237128371723187");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		in.nextToken();
		long read1 = (long) in.nval;
		System.out.println("StreamTokenizer读取到的数字 : ");
		System.out.println(read1);

		System.out.println("============================");

		System.out.println("请输入 : 131237128371723187");
		Kattio io = new Kattio();
		long read2 = io.nextLong();
		System.out.println("Kattio读取到的数字 : ");
		System.out.println(read2);
		io.close();
	}

	// 如何使用Kattio的简单示例
	// 可以找个一些具体题目试一试
	// 这里就是罗列了一下
	public static void show() {
		Kattio io = new Kattio(); // 自动接入输入输出流
		io.next(); // 读取字符串
		io.nextInt(); // 读取int
		io.nextDouble(); // 读取double
		io.nextLong(); // 读取long
		io.println("ans"); // 打印答案
		io.flush(); // 答案刷给后台
		io.close(); // 关闭io
	}

	// 这个类IO效率很好，但是不如StreamTokenizer
	// 只有StreamTokenizer无法正确处理时，才考虑这个类
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
