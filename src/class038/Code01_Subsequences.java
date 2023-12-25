package class038;

import test.Performance_test;

import java.util.Arrays;
import java.util.HashSet;

// 字符串的全部子序列
// 子序列本身是可以有重复的，只是这个题目要求去重
// 测试链接 : https://www.nowcoder.com/practice/92e6247998294f2c933906fdedbc6e6a
// 02:20 ~ 22:08
public class Code01_Subsequences {

    public static String[] generatePermutation1(String str) {
        char[] s = str.toCharArray();
        HashSet<String> set = new HashSet<>();
        func1(s, 0, new StringBuilder(), set);
        int m = set.size();
        String[] ans = new String[m];
        int i = 0;
        for (String cur : set) {
            ans[i++] = cur;
        }
        return ans;
    }

    // s[i...]，之前决定的路径path，set收集结果时去重
    public static void func1(char[] s, int i, StringBuilder path, HashSet<String> set) {
        if (i == s.length) {
            set.add(path.toString());
        } else {
            path.append(s[i]); // 加到路径中去
            func1(s, i + 1, path, set);
            path.deleteCharAt(path.length() - 1); // 从路径中移除
            func1(s, i + 1, path, set);
        }
    }

    public static String[] generatePermutation2(String str) {
        char[] s = str.toCharArray();
        HashSet<String> set = new HashSet<>();
        func2(s, 0, new char[s.length], 0, set);
        int m = set.size();
        String[] ans = new String[m];
        int i = 0;
        for (String cur : set) {
            ans[i++] = cur;
        }
        return ans;
    }

    public static void func2(char[] s, int i, char[] path, int size, HashSet<String> set) {
        if (i == s.length) {
            set.add(String.valueOf(path, 0, size));
        } else {
            path[size] = s[i];
            func2(s, i + 1, path, size + 1, set);
            func2(s, i + 1, path, size, set);
        }
    }

    public static String randomString(int len) {
        int l = (int) (Math.random() * len) + 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < l; i++) {
            char cur = (char) ((Math.random() * 26) + 97);
            sb.append(cur);
        }
        return sb.toString();
    }

    public static boolean isEqualStringArray(String[] s1, String[] s2) {
        int l1 = s1.length, l2 = s2.length;
        if (l1 != l2) return false;
        HashSet<String> set = new HashSet<>();
        for (String i : s1)
            set.add(i);
        for (String j : s2)
            if (!set.contains(j))
                return false;
        return true;
    }

    public static void main(String[] args) {

        String[] str1 = generatePermutation2("abb");
        System.out.println(Arrays.toString(str1));

        int len = 10, testTimes = 3333;
        String[] ans1, ans2;
        Performance_test Test = new Performance_test();
        Performance_test.info info = Test.initializeInfo(2);
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            String str = randomString(len);

            ans1 = generatePermutation1(str);
            ans2 = generatePermutation2(str);

            info.time[1] += Test.runTime(() -> generatePermutation1(str));
            info.time[2] += Test.runTime(() -> generatePermutation2(str));

            info.memory[1] += Test.memoryConsumption(() -> generatePermutation1(str));
            info.memory[2] += Test.memoryConsumption(() -> generatePermutation2(str));

            if (!isEqualStringArray(ans1, ans2)) {
                success = false;
                info.ac = i;
                System.out.println("Oops!");
                break;
            }
        }
        if (success) {
            Test.printInfo();
        } else {
            info.print();
        }
    }

}