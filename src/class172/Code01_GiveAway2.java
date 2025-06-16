package class172;

// Give Away，C++版
// 测试链接 : https://www.luogu.com.cn/problem/SP18185
// 测试链接 : https://www.spoj.com/problems/GIVEAWAY
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 500001;
//const int MAXB = 1001;
//int n, m;
//int arr[MAXN];
//int sortv[MAXN];
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//
//void build() {
//    blen = (int)sqrt(n);
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        bl[i] = (i - 1) * blen + 1;
//        br[i] = min(i * blen, n);
//    }
//    for (int i = 1; i <= n; i++) {
//        sortv[i] = arr[i];
//    }
//    for (int i = 1; i <= bnum; i++) {
//        sort(sortv + bl[i], sortv + br[i] + 1);
//    }
//}
//
//int getCnt(int i, int v) {
//    int l = bl[i], r = br[i], m, ans = 0;
//    while (l <= r) {
//        m = (l + r) >> 1;
//        if (sortv[m] >= v) {
//            ans += r - m + 1;
//            r = m - 1;
//        } else {
//            l = m + 1;
//        }
//    }
//    return ans;
//}
//
//int query(int l, int r, int v) {
//    int ans = 0;
//    if (bi[l] == bi[r]) {
//        for (int i = l; i <= r; i++) {
//            if (arr[i] >= v) {
//                ans++;
//            }
//        }
//    } else {
//        for (int i = l; i <= br[bi[l]]; i++) {
//            if (arr[i] >= v) {
//                ans++;
//            }
//        }
//        for (int i = bl[bi[r]]; i <= r; i++) {
//            if (arr[i] >= v) {
//                ans++;
//            }
//        }
//        for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
//            ans += getCnt(i, v);
//        }
//    }
//    return ans;
//}
//
//void update(int i, int v) {
//    int l = bl[bi[i]];
//    int r = br[bi[i]];
//    arr[i] = v;
//    for (int j = l; j <= r; j++) {
//        sortv[j] = arr[j];
//    }
//    sort(sortv + l, sortv + r + 1);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    build();
//    cin >> m;
//    int op, a, b, c;
//    for (int i = 1; i <= m; i++) {
//        cin >> op >> a >> b;
//        if (op == 0) {
//            cin >> c;
//            cout << query(a, b, c) << '\n';
//        } else {
//            update(a, b);
//        }
//    }
//    return 0;
//}