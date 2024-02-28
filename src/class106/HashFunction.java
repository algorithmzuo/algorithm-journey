package class106;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HashFunction {

	// 哈希函数实例
	public static class Hash {

		private MessageDigest md;

		// 打印支持哪些哈希算法
		public static void showAlgorithms() {
			for (String algorithm : Security.getAlgorithms("MessageDigest")) {
				System.out.println(algorithm);
			}
		}

		// 用具体算法名字构造实例
		public Hash(String algorithm) {
			try {
				md = MessageDigest.getInstance(algorithm);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		// 输入字符串返回哈希值
		public String hashValue(String input) {
			byte[] hashInBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
			BigInteger bigInt = new BigInteger(1, hashInBytes);
			String hashText = bigInt.toString(16);
			return hashText;
		}

	}

	public static List<String> generateStrings(char[] arr, int n) {
		char[] path = new char[n];
		List<String> ans = new ArrayList<>();
		f(arr, 0, n, path, ans);
		return ans;
	}

	public static void f(char[] arr, int i, int n, char[] path, List<String> ans) {
		if (i == n) {
			ans.add(String.valueOf(path));
		} else {
			for (char cha : arr) {
				path[i] = cha;
				f(arr, i + 1, n, path, ans);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("支持的哈希算法 : ");
		Hash.showAlgorithms();
		System.out.println();

		String algorithm = "MD5";
		Hash hash = new Hash(algorithm);
		String str1 = "zuochengyunzuochengyunzuochengyun1";
		String str2 = "zuochengyunzuochengyunzuochengyun2";
		String str3 = "zuochengyunzuochengyunzuochengyun3";
		String str4 = "zuochengyunzuochengyunZuochengyun1";
		String str5 = "zuochengyunzuoChengyunzuochengyun2";
		String str6 = "zuochengyunzuochengyunzuochengyUn3";
		String str7 = "zuochengyunzuochengyunzuochengyun1";
		System.out.println("7个字符串得到的哈希值 : ");
		System.out.println(hash.hashValue(str1));
		System.out.println(hash.hashValue(str2));
		System.out.println(hash.hashValue(str3));
		System.out.println(hash.hashValue(str4));
		System.out.println(hash.hashValue(str5));
		System.out.println(hash.hashValue(str6));
		System.out.println(hash.hashValue(str7));
		System.out.println();

		char[] arr = { 'a', 'b' };
		int n = 20;
		System.out.println("生成长度为n，字符来自arr，所有可能的字符串");
		List<String> strs = generateStrings(arr, n);
//		for (String str : strs) {
//			System.out.println(str);
//		}
		System.out.println("不同字符串的数量 : " + strs.size());
		HashSet<String> set = new HashSet<>();
		for (String str : strs) {
			set.add(hash.hashValue(str));
		}
//		for (String str : set) {
//			System.out.println(str);
//		}
		System.out.println("不同哈希值的数量 : " + strs.size());
		System.out.println();

		int m = 13;
		int[] cnts = new int[m];
		System.out.println("现在看看这些哈希值，% " + m + " 之后的余数分布情况");
		BigInteger mod = new BigInteger(String.valueOf(m));
		for (String hashCode : set) {
			BigInteger bigInt = new BigInteger(hashCode, 16);
			int ans = bigInt.mod(mod).intValue();
			cnts[ans]++;
		}
		for (int i = 0; i < m; i++) {
			System.out.println("余数 " + i + " 出现了 " + cnts[i] + " 次");
		}
	}

}