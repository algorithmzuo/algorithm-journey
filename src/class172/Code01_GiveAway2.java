package class172;

// 区间内>=的个数，C++版
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
//int blen, bnum;
//int arr[MAXN];
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//int sortv[MAXN];
//
//void build() {
//    blen = (int)sqrt(n);
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1, l = 1, r = blen; i <= bnum; i++, l += blen, r += blen) {
//        r = min(r, n);
//        for (int j = l; j <= r; j++) {
//            bi[j] = i;
//        }
//        bl[i] = l;
//        br[i] = r;
//    }
//    for (int i = 1; i <= n; i++) {
//        sortv[i] = arr[i];
//    }
//    for (int i = 1; i <= bnum; i++) {
//        sort(sortv + bl[i], sortv + br[i] + 1);
//    }
//}
//
//int num(int bid, int v) {
//    int l = bl[bid], r = br[bid], m, ans = 0;
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
//            ans += num(i, v);
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