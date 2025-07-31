package class175;

// 数组查询，C++版
// 测试链接 : https://www.luogu.com.cn/problem/CF797E
// 测试链接 : https://codeforces.com/problemset/problem/797/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXB = 401;
//int n, q, blen;
//int arr[MAXN];
//int dp[MAXN][MAXB];
//
//int query(int p, int k) {
//    if (k <= blen) {
//        return dp[p][k];
//    }
//    int ans = 0;
//    while (p <= n) {
//        ans++;
//        p += arr[p] + k;
//    }
//    return ans;
//}
//
//void prepare() {
//    blen = (int)sqrt(n);
//    for (int p = n; p >= 1; p--) {
//        for (int k = 1; k <= blen; k++) {
//            dp[p][k] = 1 + (p + arr[p] + k > n ? 0 : dp[p + arr[p] + k][k]);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    prepare();
//    cin >> q;
//    for (int i = 1, p, k; i <= q; i++) {
//        cin >> p >> k;
//        cout << query(p, k) << '\n';
//    }
//    return 0;
//}