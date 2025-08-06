package class175;

// 等差数列求和，C++版
// 一共有t组测试，每组测试遵循同样的设定
// 给定一个长度为n的数组arr，接下来有q条查询，查询格式如下
// 查询 s d k : arr[s]作为第1项、arr[s + 1d]作为第2项、arr[s + 2d]作为第3项...
//             每项的值 * 项的编号，一共k项都累加起来，打印累加和
// 1 <= n <= 10^5
// 1 <= q <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1921F
// 测试链接 : https://codeforces.com/problemset/problem/1921/F
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXB = 401;
//int t, n, q, blen;
//int arr[MAXN];
//long long f[MAXB][MAXN];
//long long g[MAXB][MAXN];
//
//long long query(int s, int d, int k) {
//    long long ans = 0;
//    if (d <= blen) {
//        ans = g[d][s];
//        if (s + d * k <= n) {
//            ans -= g[d][s + d * k] + f[d][s + d * k] * k;
//        }
//    } else {
//        for (int i = 1; i <= k; i++) {
//            ans += 1LL * arr[s + (i - 1) * d] * i;
//        }
//    }
//    return ans;
//}
//
//void prepare() {
//    blen = (int)sqrt(n);
//    for (int d = 1; d <= blen; d++) {
//        for (int s = n; s >= 1; s--) {
//            f[d][s] = arr[s] + (s + d > n ? 0 : f[d][s + d]);
//        }
//    }
//    for (int d = 1; d <= blen; d++) {
//        for (int s = n; s >= 1; s--) {
//            g[d][s] = f[d][s] + (s + d > n ? 0 : g[d][s + d]);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> t;
//    for (int c = 1; c <= t; c++) {
//        cin >> n >> q;
//        for (int i = 1; i <= n; i++) {
//            cin >> arr[i];
//        }
//        prepare();
//        for (int i = 1, s, d, k; i <= q; i++) {
//            cin >> s >> d >> k;
//            cout << query(s, d, k) << '\n';
//        }
//    }
//    return 0;
//}