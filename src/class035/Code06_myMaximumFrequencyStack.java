package class035;

import java.util.*;

// 最大频率栈
public class Code06_myMaximumFrequencyStack {

	// 测试链接 : https://leetcode.cn/problems/maximum-frequency-stack/
	static class FreqStack {
		// max Freq
		int maxCnt;


		// create a linkedlist to take notes of the frequency, the index indicates its frequency
		HashMap<Integer, List<Integer>> freqMap ;

		Map<Integer, Integer> valFreqMap;


		public FreqStack(){
			maxCnt = 0;
			freqMap = new HashMap<>();
			valFreqMap = new HashMap<>();
		}
		// every push or pull will need to deal with 3 things - maxCnt, freq, valfreq
		public void push(int val) {
			int freq = 1;
			List<Integer> freqList = new ArrayList<>();
			if(valFreqMap.containsKey(val)) {
				// the value has already exist
				freq = valFreqMap.get(val) + 1;
				if(freqMap.containsKey(freq)){
					freqList = freqMap.get(freq);
				}
			}
			if(freqMap.containsKey(freq)){
				freqList = freqMap.get(freq);
			}
			freqList.add(val);
			freqMap.put(freq, freqList);
			valFreqMap.put(val, freq);
			maxCnt =  Math.max(maxCnt, freq);
		}

		public int pop() {
			List<Integer> maxFreqList = freqMap.get(maxCnt);
			int val = maxFreqList.get(maxFreqList.size() - 1);
			maxFreqList.remove(maxFreqList.size() -1 );


			if(maxCnt -1 == 0 ){
				valFreqMap.remove(val);
			}else{
				valFreqMap.put(val, maxCnt -1);
			}

			if(maxFreqList.size() == 0){
				freqMap.remove(maxCnt);
				maxCnt--;
			}

			return val;
		}
	}

	public static void main(String[] args) {
		FreqStack freqStack = new FreqStack();
		freqStack.push(5);
		freqStack.push(7);
		freqStack.push(5);
		freqStack.push(7);
		freqStack.push(4);
		freqStack.push(5);
		System.out.println(freqStack.pop());
		System.out.println(freqStack.pop());
		System.out.println(freqStack.pop());
		System.out.println(freqStack.pop());

	}

}
