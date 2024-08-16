package class140;

// 测试链接 : https://www.luogu.com.cn/problem/P5656
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//#include <cstdio>
//
//using namespace std;
//
//long long d, x, y;
//
//void exgcd(long long a, long long b) {
//    if (b == 0) {
//        d = a;
//        x = 1;
//        y = 0;
//    } else {
//        exgcd(b, a % b);
//        long long tmp = x;
//        x = y;
//        y = tmp - a / b * y;
//    }
//}
//
//int main() {
//    int cases;
//    scanf("%d", &cases);
//    for (int t = 1; t <= cases; t++) {
//        long long a, b, c;
//        scanf("%lld %lld %lld", &a, &b, &c);
//        exgcd(a, b);
//        if (c % d == 0) {
//            x *= c / d;
//            y *= c / d;
//            long long xd = b / d;
//            long long yd = a / d;
//            if (x < 0) {
//                long long k = (xd - x) / xd;
//                x += xd * k;
//                y -= yd * k;
//            } else {
//                long long k = (x - 1) / xd;
//                x -= xd * k;
//                y += yd * k;
//            }
//            if (y > 0) {
//                printf("%lld ", ((y - 1) / yd + 1));
//                printf("%lld ", x);
//                printf("%lld ", ((y - 1) % yd + 1));
//                printf("%lld ", (x + (y - 1) / yd * xd));
//                printf("%lld\n", y);
//            } else {
//                printf("%lld ", x);
//                printf("%lld\n", y + yd * ((yd - y) / yd));
//            }
//        } else {
//            printf("-1\n");
//        }
//    }
//    return 0;
//}
