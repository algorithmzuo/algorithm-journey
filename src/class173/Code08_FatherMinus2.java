package class173;

// 区间父变小，C++版
// 一棵大小为n树，节点1是树头，给定fa[2..n]表示父亲节点编号
// 对于每个i > 1，都有fa[i] < i，下来有m条操作，操作类型如下
// 操作 x y z : [x..y]范围上任何一点i，fa[i] = max(1, fa[i] - z)
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
//int fa[MAXN];
//int out[MAXN];
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//int lazy[MAXB];
//int cnt[MAXB];
//
//void innerUpdate(int b) {
//    for (int i = bl[b]; i <= br[b]; i++) {
//        fa[i] = max(1, fa[i] - lazy[b]);
//    }
//    lazy[b] = 0;
//    for (int i = bl[b]; i <= br[b]; i++) {
//        if (fa[i] < bl[b]) {
//            out[i] = fa[i];
//        } else {
//            out[i] = out[fa[i]];
//        }
//    }
//}
//
//void update(int l, int r, int v) {
//    if (bi[l] == bi[r]) {
//        for (int i = l; i <= r; i++) {
//            fa[i] = max(1, fa[i] - v);
//        }
//        innerUpdate(bi[l]);
//    } else {
//        for (int i = l; i <= br[bi[l]]; i++) {
//            fa[i] = max(1, fa[i] - v);
//        }
//        innerUpdate(bi[l]);
//        for (int i = bl[bi[r]]; i <= r; i++) {
//            fa[i] = max(1, fa[i] - v);
//        }
//        innerUpdate(bi[r]);
//        for (int b = bi[l] + 1; b <= bi[r] - 1; b++) {
//            lazy[b] = min(n, lazy[b] + v);
//            if (++cnt[b] <= blen) {
//                innerUpdate(b);
//            }
//        }
//    }
//}
//
//int jumpFa(int i) {
//    return max(1, fa[i] - lazy[bi[i]]);
//}
//
//int jumpOut(int i) { 
//    return max(1, out[i] - lazy[bi[i]]);
//}
//
//int lca(int x, int y) {
//    while (bi[x] != bi[y] || jumpOut(x) != jumpOut(y)) {
//        if (bi[x] != bi[y]) {
//            if (bi[x] < bi[y]) {
//                swap(x, y);
//            }
//            x = jumpOut(x);
//        } else {
//            x = jumpOut(x);
//            y = jumpOut(y);
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
//        cin >> fa[i];
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