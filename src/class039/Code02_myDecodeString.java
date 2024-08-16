package class039;

import java.util.ArrayList;
import java.util.List;

// 含有嵌套的字符串解码
// 测试链接 : https://leetcode.cn/problems/decode-string/
public class Code02_myDecodeString {

	public static String decodeString(String str) {
		return  f(str.toCharArray(),0,0).toString();
	}

	public static int where = 0;

	// s[i....]开始计算，遇到字符串终止 或者 遇到 ] 停止
	// 返回 : 自己负责的这一段字符串的结果
	// 返回之间，更新全局变量where，为了上游函数知道从哪继续！
	public static StringBuilder f(char[] c, int i, int cnt) {
		StringBuilder str = new StringBuilder();
		int cur = 0;
		while(i < c.length  && c[i] != ']' ){
			if(c[i] >= '0' && c[i] <='9'){
				cur = cur * 10 + c[i++] -'0';
			} else if ( c[i] =='['){
				str.append(f(c, i +1, cur));
				cur = 0;
				i = where + 1;
			} else{
				str.append(c[i++]);
			}
		}
		where = i;

		return replicateStr(str,cnt);
	}
	public static StringBuilder replicateStr(StringBuilder str, int cnt){
        str.append(String.valueOf(str).repeat(Math.max(0, cnt - 1)));
		return  str;
	}

	public static void main(String[] args) {
		//System.out.println(replicateStr(new StringBuilder("a"),0));
		System.out.println(decodeString("4[a2[bb]]c"));
	}

}
