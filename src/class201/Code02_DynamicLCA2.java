package class201;

// 动态树LCA，C++版
// 一共n个点，初始时每个点各自是一棵单点有根树，一共m条操作，操作类型如下
// 操作 link x y : 让点x成为点y的儿子，保证x是所在树的根，并且x和y不连通
// 操作 cut x    : 删除点x和它父亲节点之间的直接边，保证x不是根
// 操作 lca x y  : 查询点x和点y的最低公共祖先，保证x和y在同一棵树中
// 1 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/SP8791
// 测试链接 : https://www.spoj.com/problems/DYNALCA/
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//
//bool isroot(int x) {
//    return ls[fa[x]] != x && rs[fa[x]] != x;
//}
//
//int lr(int x) {
//    return ls[fa[x]] == x ? 0 : 1;
//}
//
//void rotate(int x) {
//    int f = fa[x], g = fa[f];
//    if (lr(x) == 0) {
//        ls[f] = rs[x];
//        if (ls[f] != 0) {
//            fa[ls[f]] = f;
//        }
//        rs[x] = f;
//    } else {
//        rs[f] = ls[x];
//        if (rs[f] != 0) {
//            fa[rs[f]] = f;
//        }
//        ls[x] = f;
//    }
//    if (!isroot(f)) {
//        if (lr(f) == 0) {
//            ls[g] = x;
//        } else {
//            rs[g] = x;
//        }
//    }
//    fa[f] = x;
//    fa[x] = g;
//}
//
//void splay(int x) {
//    while (!isroot(x)) {
//        int f = fa[x];
//        if (!isroot(f)) {
//            if (lr(x) == lr(f)) {
//                rotate(f);
//            } else {
//                rotate(x);
//            }
//        }
//        rotate(x);
//    }
//}
//
//int access(int x) {
//    int ans = 0;
//    for (int y = 0; x != 0; y = x, x = fa[x]) {
//        splay(x);
//        rs[x] = y;
//        ans = x;
//    }
//    return ans;
//}
//
//void makeson(int x, int y) {
//    access(x);
//    splay(x);
//    fa[x] = y;
//}
//
//void cutfa(int x) {
//    access(x);
//    splay(x);
//    fa[ls[x]] = 0;
//    ls[x] = 0;
//}
//
//int lca(int x, int y) {
//    if (x == y) {
//        return x;
//    }
//    access(x);
//    return access(y);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, x, y; i <= m; i++) {
//        string op;
//        cin >> op;
//        if (op == "link") {
//            cin >> x >> y;
//            makeson(x, y);
//        } else if (op == "cut") {
//            cin >> x;
//            cutfa(x);
//        } else {
//            cin >> x >> y;
//            cout << lca(x, y) << "\n";
//        }
//    }
//    return 0;
//}