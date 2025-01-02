package class156;

import java.util.HashMap;
import java.util.List;

// 除法求值
// 测试链接 : https://leetcode.cn/problems/evaluate-division/
public class Code04_EvaluateDivision {

	public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
		prepare(equations);
		for (int i = 0; i < values.length; i++) {
			union(equations.get(i).get(0), equations.get(i).get(1), values[i]);
		}
		double[] ans = new double[queries.size()];
		for (int i = 0; i < queries.size(); i++) {
			ans[i] = query(queries.get(i).get(0), queries.get(i).get(1));
		}
		return ans;
	}

	public static HashMap<String, String> father = new HashMap<>();

	public static HashMap<String, Double> dist = new HashMap<>();

	public static void prepare(List<List<String>> equations) {
		father.clear();
		dist.clear();
		for (List<String> list : equations) {
			for (String key : list) {
				father.put(key, key);
				dist.put(key, 1.0);
			}
		}
	}

	public static String find(String x) {
		if (!father.containsKey(x)) {
			return null;
		}
		String tmp, fa = x;
		if (!x.equals(father.get(x))) {
			tmp = father.get(x);
			fa = find(tmp);
			dist.put(x, dist.get(x) * dist.get(tmp));
			father.put(x, fa);
		}
		return fa;
	}

	public static void union(String l, String r, double v) {
		String lf = find(l), rf = find(r);
		if (!lf.equals(rf)) {
			father.put(lf, rf);
			dist.put(lf, dist.get(r) / dist.get(l) * v);
		}
	}

	public static double query(String l, String r) {
		String lf = find(l), rf = find(r);
		if (lf == null || rf == null || !lf.equals(rf)) {
			return -1.0;
		}
		return dist.get(l) / dist.get(r);
	}

}
