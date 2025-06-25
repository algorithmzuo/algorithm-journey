package class173;

// 区间父变小，C++版
// 一棵大小为n树，节点1是树头，节点i的父节点 = arr[i]，2 <= i <= n
// 接下来有m条操作，每种操作是如下两种类型中的一种
// 操作 x y z : [x..y]范围上任何一点i，arr[i] = max(1, arr[i] - z)
// 操作 x y   : 查询点x和点y的最低公共祖先
// 2 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1491H
// 测试链接 : https://codeforces.com/problemset/problem/1491/H
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXB = 501;
//int n, m;
//int arr[MAXN];
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//
//int lazy[MAXB];
//int outer[MAXN];
//int cnt[MAXB];
//
//void innerUpdate(int b) {
//    for (int i = bl[b]; i <= br[b]; i++) {
//        arr[i] = max(1, arr[i] - lazy[b]);
//    }
//    lazy[b] = 0;
//    for (int i = bl[b]; i <= br[b]; i++) {
//        if (arr[i] < bl[b]) {
//            outer[i] = arr[i];
//        } else {
//            outer[i] = outer[arr[i]];
//        }
//    }
//}
//
//void update(int l, int r, int v) {
//    if (bi[l] == bi[r]) {
//        for (int i = l; i <= r; i++) {
//            arr[i] = max(1, arr[i] - v);
//        }
//        innerUpdate(bi[l]);
//    } else {
//        for (int i = l; i <= br[bi[l]]; i++) {
//            arr[i] = max(1, arr[i] - v);
//        }
//        innerUpdate(bi[l]);
//        for (int i = bl[bi[r]]; i <= r; i++) {
//            arr[i] = max(1, arr[i] - v);
//        }
//        innerUpdate(bi[r]);
//        for (int i = bi[l] + 1; i <= bi[r] - 1; i++) {
//            lazy[i] = min(n, lazy[i] + v);
//            if (++cnt[i] <= blen) {
//                innerUpdate(i);
//            }
//        }
//    }
//}
//
//int jumpFa(int i) {
//    return max(1, arr[i] - lazy[bi[i]]);
//}
//
//int jumpOut(int i) { 
//    return max(1, outer[i] - lazy[bi[i]]);
//}
//
//int lca(int x, int y) {
//    while (bi[x] != bi[y] || jumpOut(x) != jumpOut(y)) {
//        if (bi[x] == bi[y]) {
//            x = jumpOut(x);
//            y = jumpOut(y);
//        } else {
//            if (bi[x] < bi[y]) {
//                swap(x, y);
//            }
//            x = jumpOut(x);
//        }
//    }
//    while (x != y) {
//        if (x < y) {
//            swap(x, y);
//        }
//        x = jumpFa(x);
//    }
//    return x;
//}
//
//void prepare() {
//    blen = (int)sqrt(n);
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        bl[i] = (i - 1) * blen + 1;
//        br[i] = min(i * blen, n);
//        innerUpdate(i);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 2; i <= n; i++) {
//        cin >> arr[i];
//    }
//    prepare();
//    for (int i = 1, op, x, y, z; i <= m; i++) {
//        cin >> op >> x >> y;
//        if (op == 1) {
//            cin >> z;
//            update(x, y, z);
//        } else {
//            cout << lca(x, y) << '\n';
//        }
//    }
//    return 0;
//}