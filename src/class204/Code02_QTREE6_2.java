package class204;

// QTREE6，C++版
// 给定一棵n个节点的树，每个节点有黑白两种颜色，初始所有节点都是黑色
// 接下来有q条操作，操作类型如下
// 操作 0 x : 打印节点x所在的同色连通块大小
// 操作 1 x : 翻转节点x的颜色
// 1 <= n、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/SP16549
// 测试链接 : https://www.spoj.com/problems/QTREE6/
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, q;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int fa[2][MAXN];
//int ls[2][MAXN];
//int rs[2][MAXN];
//
//int parent[MAXN];
//int color[MAXN];
//int vir[2][MAXN];
//int sum[2][MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void up(int c, int x) {
//    sum[c][x] = sum[c][ls[c][x]] + sum[c][rs[c][x]] + vir[c][x] + (color[x] == c ? 1 : 0);
//}
//
//bool isroot(int c, int x) {
//    return ls[c][fa[c][x]] != x && rs[c][fa[c][x]] != x;
//}
//
//int lr(int c, int x) {
//    return ls[c][fa[c][x]] == x ? 0 : 1;
//}
//
//void rotate(int c, int x) {
//    int f = fa[c][x], g = fa[c][f];
//    if (lr(c, x) == 0) {
//        ls[c][f] = rs[c][x];
//        if (ls[c][f] != 0) {
//            fa[c][ls[c][f]] = f;
//        }
//        rs[c][x] = f;
//    } else {
//        rs[c][f] = ls[c][x];
//        if (rs[c][f] != 0) {
//            fa[c][rs[c][f]] = f;
//        }
//        ls[c][x] = f;
//    }
//    if (!isroot(c, f)) {
//        if (lr(c, f) == 0) {
//            ls[c][g] = x;
//        } else {
//            rs[c][g] = x;
//        }
//    }
//    fa[c][f] = x;
//    fa[c][x] = g;
//    up(c, f);
//    up(c, x);
//}
//
//void splay(int c, int x) {
//    while (!isroot(c, x)) {
//        int f = fa[c][x];
//        if (!isroot(c, f)) {
//            if (lr(c, x) == lr(c, f)) {
//                rotate(c, f);
//            } else {
//                rotate(c, x);
//            }
//        }
//        rotate(c, x);
//    }
//    up(c, x);
//}
//
//void access(int c, int x) {
//    for (int y = 0; x != 0; y = x, x = fa[c][x]) {
//        splay(c, x);
//        vir[c][x] += sum[c][rs[c][x]];
//        vir[c][x] -= sum[c][y];
//        rs[c][x] = y;
//        up(c, x);
//    }
//}
//
//int findroot(int c, int x) {
//    access(c, x);
//    splay(c, x);
//    while (ls[c][x] != 0) {
//        x = ls[c][x];
//    }
//    splay(c, x);
//    return x;
//}
//
//void link(int c, int x, int f) {
//    if (f == 0) {
//        return;
//    }
//    access(c, f);
//    splay(c, f);
//    splay(c, x);
//    fa[c][x] = f;
//    vir[c][f] += sum[c][x];
//    up(c, f);
//}
//
//void cut(int c, int x, int f) {
//    access(c, x);
//    splay(c, x);
//    if (f != 0) {
//        int left = ls[c][x];
//        fa[c][left] = 0;
//        ls[c][x] = 0;
//        up(c, x);
//    }
//}
//
//int query(int x) {
//    int c = color[x];
//    int top = findroot(c, x);
//    if (color[top] == c) {
//        return sum[c][top];
//    } else {
//        return sum[c][rs[c][top]];
//    }
//}
//
//void changeColor(int x) {
//    int pre = color[x];
//    int cur = pre ^ 1;
//    int f = parent[x];
//    cut(pre, x, f);
//    color[x] = cur;
//    link(cur, x, f);
//}
//
//void dfs(int u, int f) {
//    parent[u] = f;
//    sum[0][u] = 1;
//    for (int e = head[u]; e != 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != f) {
//            dfs(v, u);
//            link(0, v, u);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
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
//            cout << query(x) << "\n";
//        } else {
//            changeColor(x);
//        }
//    }
//    return 0;
//}