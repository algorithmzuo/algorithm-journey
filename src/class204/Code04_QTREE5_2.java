package class204;

// QTREE5，C++版
// 给定一棵n个节点的树，每个节点有黑白两种颜色，初始所有节点都是黑色
// 接下来有q条操作，操作类型如下
// 操作 0 x : 翻转节点x的颜色
// 操作 1 x : 打印节点x到最近白色节点的距离，不存在白色节点打印-1
// 1 <= n、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/SP2939
// 测试链接 : https://www.spoj.com/problems/QTREE5/
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int INF = 1000000001;
//int n, q;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//
//int color[MAXN];
//int siz[MAXN];
//int lm[MAXN];
//int rm[MAXN];
//
//multiset<int> vir[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//int getmin(int x) {
//    if (vir[x].empty()) {
//        return INF;
//    }
//    return *vir[x].begin();
//}
//
//void up(int x) {
//    siz[x] = siz[ls[x]] + siz[rs[x]] + 1;
//    int cur = color[x] == 1 ? 0 : INF;
//    lm[x] = min(lm[ls[x]], siz[ls[x]] + min(cur, min(getmin(x), lm[rs[x]] + 1)));
//    rm[x] = min(rm[rs[x]], siz[rs[x]] + min(cur, min(getmin(x), rm[ls[x]] + 1)));
//}
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
//    up(f);
//    up(x);
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
//    up(x);
//}
//
//void access(int x) {
//    for (int y = 0; x != 0; y = x, x = fa[x]) {
//        splay(x);
//        if (rs[x] != 0) {
//            vir[x].insert(lm[rs[x]] + 1);
//        }
//        if (y != 0) {
//            vir[x].erase(vir[x].find(lm[y] + 1));
//        }
//        rs[x] = y;
//        up(x);
//    }
//}
//
//void changeColor(int x) {
//    access(x);
//    splay(x);
//    color[x] ^= 1;
//    up(x);
//}
//
//int query(int x) {
//    access(x);
//    splay(x);
//    return rm[x] == INF ? -1 : rm[x];
//}
//
//void dfs(int u, int f) {
//    fa[u] = f;
//    siz[u] = 1;
//    lm[u] = rm[u] = INF;
//    for (int e = head[u]; e != 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != f) {
//            dfs(v, u);
//            vir[u].insert(lm[v] + 1);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    lm[0] = rm[0] = INF;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs(1, 0);
//    cin >> q;
//    for (int i = 1, op, x; i <= q; i++) {
//        cin >> op >> x;
//        if (op == 0) {
//            changeColor(x);
//        } else {
//            cout << query(x) << "\n";
//        }
//    }
//    return 0;
//}