package class204;

// QTREE7，C++版
// 给定一棵n个节点的树，每个节点有黑白两种颜色，给定初始颜色和点权
// 接下来有q条操作，操作类型如下
// 操作 0 x   : 打印节点x所在的同色连通块中的最大点权
// 操作 1 x   : 翻转节点x的颜色
// 操作 2 x w : 节点x的点权修改为w
// 1 <= n、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/SP16580
// 测试链接 : https://www.spoj.com/problems/QTREE7/
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
//int fa[2][MAXN];
//int ls[2][MAXN];
//int rs[2][MAXN];
//
//int parent[MAXN];
//int color[MAXN];
//int val[MAXN];
//
//multiset<int> vset[2][MAXN];
//int maxv[2][MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//int getmax(int c, int x) {
//    if (vset[c][x].empty()) {
//        return -INF;
//    }
//    return *vset[c][x].rbegin();
//}
//
//void up(int c, int x) {
//    maxv[c][x] = max(val[x], max(getmax(c, x), max(maxv[c][ls[c][x]], maxv[c][rs[c][x]])));
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
//        if (rs[c][x] != 0) {
//            vset[c][x].insert(maxv[c][rs[c][x]]);
//        }
//        if (y != 0) {
//            vset[c][x].erase(vset[c][x].find(maxv[c][y]));
//        }
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
//    vset[c][f].insert(maxv[c][x]);
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
//        return maxv[c][top];
//    }
//    return maxv[c][rs[c][top]];
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
//void updateValue(int x, int w) {
//    access(0, x);
//    splay(0, x);
//    access(1, x);
//    splay(1, x);
//    val[x] = w;
//    up(0, x);
//    up(1, x);
//}
//
//void dfs(int u, int f) {
//    parent[u] = f;
//    for (int e = head[u]; e != 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != f) {
//            dfs(v, u);
//            link(color[v], v, u);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i < n; i++) {
//        int u, v;
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    for (int i = 1; i <= n; i++) {
//        cin >> color[i];
//    }
//    maxv[0][0] = maxv[1][0] = -INF;
//    for (int i = 1, w; i <= n; i++) {
//        cin >> w;
//        val[i] = maxv[0][i] = maxv[1][i] = w;
//    }
//    dfs(1, 0);
//    cin >> q;
//    for (int i = 1, op, x, w; i <= q; i++) {
//        cin >> op >> x;
//        if (op == 0) {
//            cout << query(x) << "\n";
//        } else if (op == 1) {
//            changeColor(x);
//        } else {
//            cin >> w;
//            updateValue(x, w);
//        }
//    }
//    return 0;
//}