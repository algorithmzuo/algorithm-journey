package class039;

// 含有嵌套的字符串解码
// 测试链接 : https://leetcode.cn/problems/decode-string/
// 40:40 ~ 51:47
public class Code02_DecodeString {

    public static int start;

    public static String decodeString(String str) {
        start = 0;
        return fun(str.toCharArray(), 0);
    }

    // str[i....]开始计算，遇到字符串终止 或者 遇到 ] 停止
    // 返回 : 自己负责的这一段字符串的结果
    // 返回之间，更新全局变量start，为了上游函数知道从哪继续！
    public static String fun(char[] str, int i) {
        StringBuilder path = new StringBuilder();
        int times = 0;
        while (i < str.length && str[i] != ']') {
            if (Character.isAlphabetic(str[i])) {
                path.append(str[i++]);
            } else if (Character.isDigit(str[i])) {
                times = times * 10 + str[i++] - '0';
            }
            else {
                String next = get(times, fun(str, i + 1));
                path.append(next);
                i = start + 1;
                times = 0;
            }
        }
        start = i;
        return path.toString();
    }

    public static String get(int times, String str) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++) {
            builder.append(str);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        String str = "abc4[b]2[a]";
//        String str = "abc";
//        String str = "3[abc]";
        String result = decodeString(str);
        System.out.println("result : " + result);
    }

}