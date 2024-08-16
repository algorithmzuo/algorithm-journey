package class039;

import java.util.TreeMap;

// 含有嵌套的分子式求原子数量
// 测试链接 : https://leetcode.cn/problems/number-of-atoms/
public class Code03_myNumberOfAtoms {
	public static int where;
	public static String countOfAtoms(String str) {
		StringBuilder str1 = new StringBuilder();
		TreeMap<String, Integer> ans = f(str.toCharArray(), 0);
		for (String key: ans.keySet()) {
			str1.append(key).append(ans.get(key) >1 ? ans.get(key):"");
		}
		return str1.toString();
	}

	public static TreeMap<String, Integer> f(char[] c, int index){
		TreeMap<String, Integer> ans = new TreeMap<>();
		TreeMap<String, Integer> pre = new TreeMap<>();
		StringBuilder str = new StringBuilder();
		int cnt = 0;
		while(index < c.length && c[index]!=')'){
			if(c[index] >= '0' && c[index] <='9'){
				cnt = cnt * 10 + c[index++] - '0';
			} else if (c[index]>='a' && c[index] <='z'){
				str.append(c[index++]);
			} else{
				// 遇到大写或者( 1- 收集
				collect(String.valueOf(str), pre , cnt, ans);
				// 请记录
				str.setLength(0);
				pre.clear();
				cnt = 0;
				//重新开始收集
				if(c[index] >='A' && c[index] <='Z'){
					str.append(c[index++]);
				}else{
					pre = f(c, index + 1);
					index = where + 1;
				}

			}
		}
		where = index;
		collect(String.valueOf(str), pre ,cnt, ans);
		return ans;
	}

	public static void collect(String str, TreeMap<String, Integer> pre, int cnt, TreeMap<String, Integer> ans){
		cnt = cnt == 0 ? 1 : cnt;
		if(!str.isEmpty()){
			ans.put(str, ans.getOrDefault(str,0) + cnt);
		} else if(!pre.isEmpty()){
			for(String key : pre.keySet()){
				ans.put(key,ans.getOrDefault(key,0) +pre.get(key) * cnt );
			}
		}

	}

	public static void main(String[] args) {
//		StringBuilder str = new StringBuilder("Mg(OH)3");
////		System.out.println(countOfAtoms(str));
//		str.setLength(0);
//
//		String str1= "(OH)2(O(OH)2H)Mg";
//		System.out.println(countOfAtoms(str1));

		Integer i1 = 40;
		Integer i2 = new Integer(40);
		System.out.println(i1==i2);
	}
	// s[i....]开始计算，遇到字符串终止 或者 遇到 ) 停止
	// 返回 : 自己负责的这一段字符串的结果，有序表！
	// 返回之间，更新全局变量where，为了上游函数知道从哪继续！



}
