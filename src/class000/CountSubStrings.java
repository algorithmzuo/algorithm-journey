package class000;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

// https://leetcode.cn/problems/palindromic-substrings/
public class CountSubStrings {
    static HashSet<String> set = new HashSet<>();
    public static int countSubstrings(String word) {
        HashMap<String, Integer> map = new HashMap<>();

        return f(word, map);
    }

    public static  int f(String str, HashMap<String, Integer> map){
        if(str.length() == 0){
            return 0;
        }
        if(map.containsKey(str)){
            return map.get(str);
        }
        int cnt =  f(str.substring(1),map);

        // substring [beginIndex, endIndex)
        for (int i = 1; i <= str.length(); i++) {
            String subString = str.substring(0,i);
            if(set.contains(subString)){
                cnt ++;
            } else if (isPanlidrome(subString)) {
                cnt++;
                set.add(subString);

            }
        }
        map.put(str, cnt);
        return cnt;

    }
    public static boolean isPanlidrome(String word){
        if(word.length() == 1){
            set.add(word);
            return true;
        }
        char[] c = word.toCharArray();
        for (int i = 0 , j = c.length -1 ; i <= j; i++, j--) {
            if(c[i] != c[j])
                return false;
        }
        set.add(word);
        return true;
    }

    public static void main(String[] args) {
        String str = "ayeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaah";
        System.out.println(countSubstrings(str));
    }

}