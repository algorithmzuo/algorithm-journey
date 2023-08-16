package class035;

import java.util.ArrayList;
import java.util.HashMap;

// 最大频率栈
public class Code06_MaximumFrequencyStack {

	// 测试链接 : https://leetcode.cn/problems/maximum-frequency-stack/
	class FreqStack {

		// 出现的最大次数
		private int topTimes;
		// 每层节点
		private HashMap<Integer, ArrayList<Integer>> cntValues = new HashMap<>();
		// 每一个数出现了几次
		private HashMap<Integer, Integer> valueTimes = new HashMap<>();

		public void push(int val) {
			valueTimes.put(val, valueTimes.getOrDefault(val, 0) + 1);
			int curTopTimes = valueTimes.get(val);
			if (!cntValues.containsKey(curTopTimes)) {
				cntValues.put(curTopTimes, new ArrayList<>());
			}
			ArrayList<Integer> curTimeValues = cntValues.get(curTopTimes);
			curTimeValues.add(val);
			topTimes = Math.max(topTimes, curTopTimes);
		}

		public int pop() {
			ArrayList<Integer> topTimeValues = cntValues.get(topTimes);
			int ans = topTimeValues.remove(topTimeValues.size() - 1);
			if (topTimeValues.size() == 0) {
				cntValues.remove(topTimes--);
			}
			int times = valueTimes.get(ans);
			if (times == 1) {
				valueTimes.remove(ans);
			} else {
				valueTimes.put(ans, times - 1);
			}
			return ans;
		}
	}

}
