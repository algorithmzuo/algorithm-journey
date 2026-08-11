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
//const int MAXN = 200001;
//const int INF = 1000000001;
//int n, q;
//
//int color[MAXN];
//int weight[MAXN];
//
//int head[MAXN];
//int nxt[MAXN];
//int to[MAXN];
//int cntg;
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//
//int parent[MAXN];
//int val[MAXN];
//multiset<int> vir[MAXN];
//int maxv[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//int getmax(int x) {
//    if (vir[x].empty()) {
//        return -INF;
//    }
//    return *vir[x].rbegin();
//}
//
//void up(int x) {
//    maxv[x] = max(val[x], max(getmax(x), max(maxv[ls[x]], maxv[rs[x]])));
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
//            vir[x].insert(maxv[rs[x]]);
//        }
//        if (y != 0) {
//            vir[x].erase(vir[x].find(maxv[y]));
//        }
//        rs[x] = y;
//        up(x);
//    }
//}
//
//int findroot(int x) {
//    access(x);
//    splay(x);
//    while (ls[x] != 0) {
//        x = ls[x];
//    }
//    splay(x);
//    return x;
//}
//
//void link(int x, int f) {
//    if (f == 0) {
//        return;
//    }
//    access(f);
//    splay(f);
//    splay(x);
//    fa[x] = f;
//    vir[f].insert(maxv[x]);
//    up(f);
//}
//
//void cut(int x, int f) {
//    access(x);
//    splay(x);
//    if (f != 0) {
//        int left = ls[x];
//        fa[left] = 0;
//        ls[x] = 0;
//        up(x);
//    }
//}
//
//int query(int x) {
//    int cur = val[x] != -INF ? x : x + n;
//    int top = findroot(cur);
//    return val[top] != -INF ? maxv[top] : maxv[rs[top]];
//}
//
//void reverseColor(int x) {
//    int pre = val[x] != -INF ? x : x + n;
//    int cur = pre <= n ? pre + n : pre - n;
//    cut(pre, parent[pre]);
//    val[cur] = val[pre];
//    val[pre] = -INF;
//    link(cur, parent[cur]);
//}
//
//void updateValue(int x, int w) {
//    int cur = val[x] != -INF ? x : x + n;
//    access(cur);
//    splay(cur);
//    val[cur] = w;
//    up(cur);
//}
//
//void dfs(int u, int f) {
//    if (f != 0) {
//        parent[u] = f;
//        parent[u + n] = f + n;
//    }
//    for (int e = head[u]; e != 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != f) {
//            dfs(v, u);
//            int cur = color[v] == 0 ? v : v + n;
//            link(cur, parent[cur]);
//        }
//    }
//}
//
//void prepare() {
//    maxv[0] = -INF;
//    for (int i = 1; i <= n; i++) {
//        if (color[i] == 0) {
//            val[i] = maxv[i] = weight[i];
//            val[i + n] = maxv[i + n] = -INF;
//        } else {
//            val[i] = maxv[i] = -INF;
//            val[i + n] = maxv[i + n] = weight[i];
//        }
//    }
//    dfs(1, 0);
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
//    for (int i = 1; i <= n; i++) {
//        cin >> color[i];
//    }
//    for (int i = 1; i <= n; i++) {
//        cin >> weight[i];
//    }
//    prepare();
//    cin >> q;
//    for (int i = 1, op, x, w; i <= q; i++) {
//        cin >> op >> x;
//        if (op == 0) {
//            cout << query(x) << "\n";
//        } else if (op == 1) {
//            reverseColor(x);
//        } else {
//            cin >> w;
//            updateValue(x, w);
//        }
//    }
//    return 0;
//}