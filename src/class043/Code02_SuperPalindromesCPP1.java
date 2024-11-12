package class043;

// 超级回文数(superpalindromesInRange1方法C++版本)
// 如果一个正整数自身是回文数，而且它也是一个回文数的平方，那么我们称这个数为超级回文数。
// 现在，给定两个正整数 L 和 R （以字符串形式表示），
// 返回包含在范围 [L, R] 中的超级回文数的数目。
// 1 <= len(L) <= 18
// 1 <= len(R) <= 18
// L 和 R 是表示 [1, 10^18) 范围的整数的字符串
// 测试链接 : https://leetcode.cn/problems/super-palindromes/
// 如下实现是课上讲的superpalindromesInRange1方法的C++版本，提交如下代码可以直接通过

//#include <string>
//#include <cmath>
//#include <limits>
//
//using namespace std;
//
//class Solution {
//public:
//    int superpalindromesInRange(string left, string right) {
//        long long l = stoll(left);
//        long long r = stoll(right);
//        long long limit = static_cast<long long>(sqrt(r));
//        long long seed = 1;
//        long long num = 0;
//        int ans = 0;
//        do {
//            num = evenEnlarge(seed);
//            if (num <= limit && safeSquare(num) && check(num * num, l, r)) {
//                ans++;
//            }
//            num = oddEnlarge(seed);
//            if (num <= limit && safeSquare(num) && check(num * num, l, r)) {
//                ans++;
//            }
//            seed++;
//        } while (num < limit);
//
//        return ans;
//    }
//
//private:
//    bool safeSquare(long long num) {
//        return num <= static_cast<long long>(sqrt(numeric_limits<long long>::max()));
//    }
//
//    long long evenEnlarge(long long seed) {
//        long long ans = seed;
//        while (seed != 0) {
//            ans = ans * 10 + seed % 10;
//            seed /= 10;
//        }
//        return ans;
//    }
//
//    long long oddEnlarge(long long seed) {
//        long long ans = seed;
//        seed /= 10;
//        while (seed != 0) {
//            ans = ans * 10 + seed % 10;
//            seed /= 10;
//        }
//        return ans;
//    }
//
//    bool check(long long ans, long long l, long long r) {
//        return ans >= l && ans <= r && isPalindrome(ans);
//    }
//
//    bool isPalindrome(long long num) {
//        long long offset = 1;
//        while (num / offset >= 10) {
//            offset *= 10;
//        }
//        while (num != 0) {
//            if (num / offset != num % 10) {
//                return false;
//            }
//            num = (num % offset) / 10;
//            offset /= 100;
//        }
//        return true;
//    }
//};
