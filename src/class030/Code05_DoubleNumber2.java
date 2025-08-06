package class030;

// 数组中有2种数出现了奇数次，其他的数都出现了偶数次
// 返回这2种出现了奇数次的数
// 测试链接 : https://leetcode.cn/problems/single-number-iii/
// 如下代码是C++版，直接提交可以通过，注意看代码中的注释

//class Solution {
//public:
//    vector<int> singleNumber(vector<int>& nums) {
//        int eor1 = 0;
//        for (int x : nums) {
//            eor1 ^= x;
//        }
//        // 为什么这么写？自己去查！语言问题自己搞定
//        unsigned int rightOne = (unsigned int)eor1 & (-(unsigned int)eor1);
//        int a = 0;
//        for (int x : nums) {
//            if (((unsigned int)x & rightOne) == 0) {
//                a ^= x;
//            }
//        }
//        return {a, eor1 ^ a};
//    }
//};