package class000;

public class StringReverseUtils {
    public String reverse(String s) {
        return reverse(s.toCharArray(),0, s.length()-1);
    }
    public String reverse(String s, int k) {
        return  reverse(s.toCharArray(),0, k);
    }

    public static String reverse(char[] c, int start, int end){
        for (; start <=end ; start++, end--) {
            char tmp = c[start];
            c[start] = c[end];
            c[end] = tmp;
        }
        return String.valueOf(c);
    }
    public static void main(String[] args) {
// Test
        StringReverseUtils utils = new StringReverseUtils();
        System.out.println(utils.reverse("abcde"));
        System.out.println(utils.reverse("abcde", 3));
        System.out.println(utils.reverse("abcdefghijk", 4));
    }
}
