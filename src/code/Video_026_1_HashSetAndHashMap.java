package code;

import java.util.HashMap;
import java.util.HashSet;

public class Video_026_1_HashSetAndHashMap {

	public static void main(String[] args) {
		// String、Integer、Long、Double、Float等都有这个特征
		String str1 = new String("Hello");
		String str2 = new String("Hello");
		// false，因为不同的内存地址
		System.out.println(str1 == str2);
		// true，因为它们的值是相同的
		System.out.println(str1.equals(str2));

		HashSet<String> set = new HashSet<>();
		set.add(str1);
		System.out.println(set.contains("Hello"));
		System.out.println(set.contains(str2));
		set.add(str2);
		System.out.println(set.size());
		set.remove(str1);
		set.clear();
		System.out.println(set.isEmpty());

		System.out.println("===========");

		HashMap<String, String> map = new HashMap<>();
		map.put(str1, "World");
		System.out.println(map.containsKey("Hello"));
		System.out.println(map.containsKey(str2));
		System.out.println(map.get(str2));
		System.out.println(map.get("你好"));
		map.remove("Hello");
		System.out.println(map.size());
		map.clear();
		System.out.println(map.isEmpty());

		System.out.println("===========");
		
		HashMap<Student, String> map2 = new HashMap<>();
		Student s1 = new Student(17, "张三");
		Student s2 = new Student(17, "张三");
		map2.put(s1, "这是张三");
		System.out.println(map2.containsKey(s1));
		System.out.println(map2.containsKey(s2));
		map2.put(s2, "这另一个是张三");
		System.out.println(map2.size());
		System.out.println(map2.get(s1));
		System.out.println(map2.get(s2));

	}

	public static class Student {
		public int age;
		public String name;

		public Student(int a, String b) {
			age = a;
			name = b;
		}
	}

}
