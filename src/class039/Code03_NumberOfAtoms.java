package class039;

import java.util.TreeMap;

// 含有嵌套的分子式求原子数量
// 测试链接 : https://leetcode.cn/problems/number-of-atoms/
// 51:50 ~ 结束
public class Code03_NumberOfAtoms {

    public static int start;

    public static String countOfAtoms(String str) {
        start = 0;
        TreeMap<String, Integer> map = fun(str.toCharArray(), 0);
        StringBuilder ans = new StringBuilder();
        for (String key : map.keySet()) {
            ans.append(key);
            int cnt = map.get(key);
            if (cnt > 1) {
                ans.append(cnt);
            }
        }
        return ans.toString();
    }

    // str[i....]开始计算，遇到字符串终止 或者 遇到 ) 停止
    // 返回 : 自己负责的这一段字符串的结果，有序表！
    // 返回之间，更新全局变量where，为了上游函数知道从哪继续！
    public static TreeMap<String, Integer> fun(char[] str, int i) {
        TreeMap<String, Integer> allAns = new TreeMap<>();
        TreeMap<String, Integer> subAns = null;
        StringBuilder letter = new StringBuilder();
        int times = 0;
        while (i < str.length && str[i] != ')') {
            boolean upperCase = Character.isUpperCase(str[i]);
            boolean lowerCase = Character.isLowerCase(str[i]);
            boolean numberCase = Character.isDigit(str[i]);
            if (upperCase || str[i] == '(') {
                fill(allAns, letter, subAns, times);
                letter.setLength(0);
                subAns = null;
                times = 0;
                if (upperCase) {
                    letter.append(str[i++]);
                } else {
                    subAns = fun(str, i + 1);
                    i = start + 1;
                }
            } else if (lowerCase) {
                letter.append(str[i++]);
            } else if (numberCase) {
                times = times * 10 + str[i++] - '0';
            }
        }
        fill(allAns, letter, subAns, times);
        start = i;
        return allAns;
    }

    public static void fill(TreeMap<String, Integer> allAns, StringBuilder letter,
                            TreeMap<String, Integer> subAns, int times) {
        if (!letter.isEmpty() || subAns != null) {
            times = times == 0 ? 1 : times;
            if (subAns == null) {
                String key = letter.toString();
                allAns.put(key, allAns.getOrDefault(key, 0) + times);
            } else if (letter.isEmpty()) {
                for (String key : subAns.keySet()) {
                    allAns.put(key, allAns.getOrDefault(key, 0) + subAns.get(key) * times);
                }
            }
        }
    }

    public static void main(String[] args) {
//        String str = "Mg(OH)2";
        String str = "K4(ON(SO3)2)2";
        System.out.println(countOfAtoms(str));
    }

}
