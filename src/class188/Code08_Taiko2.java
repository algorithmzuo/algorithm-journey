package class188;

// 太鼓达人，C++版
// 给定一个正数n，所有长度为n的二进制状态一共有(2^n)个
// 构造一个字符串，字符串可以循环使用，其中的连续子串包含所有二进制状态
// 求出字符串的最小长度值，并且给出字典序最小的方案
// 比如n=3，字符串最小长度值为8，字典序最小的方案为00010111
// 注意到 000、001、010、101、011、111、110、100 都已包含
// 注意到 最后两个二进制状态 是字符串循环使用构造出来的
// 1 <= n <= 11
// 本题可以推广到k进制，代码就是按照推广来实现的
// 测试链接 : https://www.luogu.com.cn/problem/P10950
// 测试链接 : https://loj.ac/p/10110
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 3001;
//int n, k, m;
//
//int cur[MAXN];
//int path[MAXN];
//int cntp;
//
//void prepare(int len, int num) {
//    n = len;
//    k = num;
//    m = 1;
//    for (int i = 1; i <= n - 1; i++) {
//        m *= k;
//    }
//    for (int i = 0; i < m; i++) {
//        cur[i] = 0;
//    }
//    cntp = 0;
//}
//
//void euler(int u, int e) {
//    while (cur[u] < k) {
//        int ne = cur[u]++;
//        euler((u * k + ne) % m, ne);
//    }
//    path[++cntp] = e;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int len;
//    cin >> len;
//    int num = 2;
//    prepare(len, num);
//    euler(0, -1);
//    cout << (m * k) << " ";
//    for (int i = 1; i <= n - 1; i++) {
//        cout << "0";
//    }
//    for (int i = cntp - 1; i >= n; i--) {
//        cout << path[i];
//    }
//    cout << "\n";
//    return 0;
//}