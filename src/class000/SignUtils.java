package class000;

import java.util.Map;
import java.util.TreeMap;

public class SignUtils {
    Map<String, String> keyValuePairs;
    public SignUtils() {
        keyValuePairs = new TreeMap<>(String::compareTo);
    }


    public void put(String key, String value) {
        keyValuePairs.put(key, value);
    }
    public String getSign() {
        StringBuilder str = new StringBuilder();
        for (String key: keyValuePairs.keySet()) {
            str.append(key).append("=").append(keyValuePairs.get(key)).append("&");
        }
        return str.deleteCharAt(str.length()-1).toString();
    }
    public static void main(String[] args) {
// Test
        SignUtils signUtils = new SignUtils();
        signUtils.put("a", "a");
        signUtils.put("aa", "a");
        signUtils.put("ab", "a");
        signUtils.put("b", "a");
        signUtils.put("bb", "a");
        signUtils.put("timestamp", "1670549739457");
        signUtils.put("amount", "100");
        signUtils.put("userName", "alden");
        signUtils.put("userPhone", "09844789231");
        signUtils.put("orderNo", "fpx08742139912");
        System.out.println(signUtils.getSign());

    }

}
