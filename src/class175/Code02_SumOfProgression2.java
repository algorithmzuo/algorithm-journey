package class175;

// 等差数列求和，C++版
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
//const int MAXS = MAXN + MAXB;
//int t, n, q, blen;
//int arr[MAXN];
//long long f[MAXS][MAXB];
//long long g[MAXS][MAXB];
//
//long long query(int s, int d, int k) {
//    if (d <= blen) {
//        return g[s][d] - g[s + d * k][d] - f[s + d * k][d] * k;
//    }
//    long long ans = 0;
//    for (int i = 1; i <= k; i++) {
//        ans += 1LL * i * arr[s + (i - 1) * d];
//    }
//    return ans;
//}
//
//void prepare() {
//    blen = (int)sqrt(n);
//    for (int s = 1; s <= n + blen; s++) {
//        for (int d = 1; d < MAXB; d++) {
//            f[s][d] = g[s][d] = 0;
//        }
//    }
//    for (int s = n; s >= 1; s--) {
//        for (int d = 1; d <= blen; d++) {
//            f[s][d] = f[s + d][d] + arr[s];
//        }
//    }
//    for (int s = n; s >= 1; s--) {
//        for (int d = 1; d <= blen; d++) {
//            g[s][d] = g[s + d][d] + f[s][d];
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
//        for (int i = 1; i <= n; ++i) {
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