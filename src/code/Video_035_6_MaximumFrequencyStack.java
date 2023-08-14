package code;

import java.util.ArrayList;
import java.util.HashMap;

// 最大频率栈
public class Video_035_6_MaximumFrequencyStack {

	// 测试链接 : https://leetcode.cn/problems/maximum-frequency-stack/
	class FreqStack {

		// 出现的最大次数
		private int topTimes;
		// 每层节点
		private HashMap<Integer, ArrayList<Integer>> cntValues = new HashMap<>();
		// 每一个数出现了几次
		private HashMap<Integer, Integer> valueTopTime = new HashMap<>();

		public void push(int val) {
			// 当前数词频+1
			valueTopTime.put(val, valueTopTime.getOrDefault(val, 0) + 1);
			int curTopTimes = valueTopTime.get(val);
			if (!cntValues.containsKey(curTopTimes)) {
				cntValues.put(curTopTimes, new ArrayList<>());
			}
			ArrayList<Integer> curTimeValues = cntValues.get(curTopTimes);
			curTimeValues.add(val);
			topTimes = Math.max(topTimes, curTopTimes);
		}

		public int pop() {
			// 最大词频的那一层的链表(动态数组)
			ArrayList<Integer> topTimeValues = cntValues.get(topTimes);
			int ans = topTimeValues.remove(topTimeValues.size() - 1);
			if (topTimeValues.size() == 0) {
				cntValues.remove(topTimes--);
			}
			int times = valueTopTime.get(ans);
			if (times == 1) {
				valueTopTime.remove(ans);
			} else {
				valueTopTime.put(ans, times - 1);
			}
			return ans;
		}
	}

}
