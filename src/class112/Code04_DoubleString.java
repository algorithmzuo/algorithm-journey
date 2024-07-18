package class112;

// 读取科学计数法表达的double数字
// 如果输入的字符串代表double数字的科学计数法形式
// 用StreamTokenizer读取会出错
// 用StringTokenizer读取正确但比较费内存
// 参考链接 : https://oi-wiki.org/lang/java-pro/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.StringTokenizer;

public class Code04_DoubleString {

	/*
输入如下
5
10.203E+0000
5.6920E+0001
30.888E-0001
400.20E+0002
0.2373E-0002
	 */
	public static void main(String[] args) throws IOException {
		// test1();
		test2();
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

	// StreamTokenizer无法正确读取
	public static void test1() throws IOException {
		System.out.println("测试StreamTokenizer");
		System.out.println("输入 : ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		double num;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			num = (double) in.nval;
			out.println(num);
		}
		out.flush();
		out.close();
		br.close();
	}

	// StringTokenizer可以正确读取
	public static void test2() throws IOException {
		System.out.println("测试StringTokenizer");
		System.out.println("输入 : ");
		Kattio io = new Kattio();
		int n = io.nextInt();
		double num;
		for (int i = 1; i <= n; i++) {
			num = io.nextDouble();
			io.println(num);
		}
		io.flush();
		io.close();
	}

}
