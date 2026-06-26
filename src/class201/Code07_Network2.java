package class201;

// 网络，C++版
// 无向图中有n个点，点有点权，有m条边，边有颜色，颜色总数量为C，颜色的数值0 ~ C-1
// 无向图保证没有重边和自环，并且始终满足如下两个性质
// 性质1，对于任意节点连出去的边中，相同颜色的边不超过两条
// 性质2，图中不存在同色的环，指相同颜色的边构成的环
// 一共有q条操作，类型如下
// 操作 0 x y   : 点x的点权变成y
// 操作 2 c x y : 颜色c的边构成的图中，打印x到y路径上的点权最大值，不连通打印-1
// 操作 1 x y c : 点x到点y的直接边，颜色改成c，下面是具体说明
// 若修改后不满足性质1，不修改边的颜色，打印 "Error 1.”
// 若修改后不满足性质2，不修改边的颜色，打印 "Error 2."
// 若同时不满足性质1和性质2，优先打印 "Error 1."
// 若不存在直接边，不修改边的颜色，打印 "No such edge."
// 若上述情况都没出现，那么修改边的颜色，打印 "Success."
// 1 <= n <= 10^4
// C <= 10
// 1 <= m、q <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P2173
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m, C, q;
//
//int fa[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int sta[MAXN];
//bool rev[MAXN];
//
//int arr[MAXN];
//int maxv[MAXN];
//
//int nodeDegree[MAXN];
//map<pair<int, int>, int> edgeColor;
//
//void up(int x) {
//    maxv[x] = max(max(maxv[ls[x]], maxv[rs[x]]), arr[x]);
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
//void reverse(int x) {
//    if (x != 0) {
//        swap(ls[x], rs[x]);
//        rev[x] = !rev[x];
//    }
//}
//
//void down(int x) {
//    if (rev[x]) {
//        reverse(ls[x]);
//        reverse(rs[x]);
//        rev[x] = false;
//    }
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
//    int siz = 0;
//    sta[++siz] = x;
//    for (int y = x; !isroot(y); y = fa[y]) {
//        sta[++siz] = fa[y];
//    }
//    while (siz != 0) {
//        down(sta[siz--]);
//    }
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
//void access(int x) {
//    for (int y = 0; x != 0; y = x, x = fa[x]) {
//        splay(x);
//        rs[x] = y;
//        up(x);
//    }
//}
//
//void makeroot(int x) {
//    access(x);
//    splay(x);
//    reverse(x);
//}
//
//int findroot(int x) {
//    access(x);
//    splay(x);
//    down(x);
//    while (ls[x] != 0) {
//        x = ls[x];
//        down(x);
//    }
//    splay(x);
//    return x;
//}
//
//void split(int x, int y) {
//    makeroot(x);
//    access(y);
//    splay(y);
//}
//
//void link(int x, int y) {
//    makeroot(x);
//    if (findroot(y) != x) {
//        fa[x] = y;
//    }
//}
//
//void cut(int x, int y) {
//    makeroot(x);
//    if (findroot(y) == x && fa[y] == x && ls[y] == 0 && rs[x] == y) {
//        fa[y] = rs[x] = 0;
//        up(x);
//    }
//}
//
//int node(int c, int x) {
//    return c * n + x;
//}
//
//pair<int, int> edge(int x, int y) {
//    int a = min(x, y);
//    int b = max(x, y);
//    return {a, b};
//}
//
//void connect(int c, int x, int y) {
//    int u = node(c, x);
//    int v = node(c, y);
//    nodeDegree[u]++;
//    nodeDegree[v]++;
//    link(u, v);
//    edgeColor[edge(x, y)] = c;
//}
//
//void disconnect(int c, int x, int y) {
//    int u = node(c, x);
//    int v = node(c, y);
//    nodeDegree[u]--;
//    nodeDegree[v]--;
//    cut(u, v);
//    edgeColor.erase(edge(x, y));
//}
//
//void updateNode(int x, int v) {
//    for (int c = 0; c < C; c++) {
//        int cur = node(c, x);
//        splay(cur);
//        arr[cur] = v;
//        up(cur);
//    }
//}
//
//int updateEdge(int x, int y, int c) {
//    auto it = edgeColor.find(edge(x, y));
//    if (it == edgeColor.end()) {
//        return 3;
//    }
//    int p = it->second;
//    if (p == c) {
//        return 4;
//    }
//    if (nodeDegree[node(c, x)] >= 2 || nodeDegree[node(c, y)] >= 2) {
//        return 1;
//    }
//    if (findroot(node(c, x)) == findroot(node(c, y))) {
//        return 2;
//    }
//    disconnect(p, x, y);
//    connect(c, x, y);
//    return 4;
//}
//
//int query(int c, int x, int y) {
//    int u = node(c, x);
//    int v = node(c, y);
//    if (findroot(u) != findroot(v)) {
//        return -1;
//    }
//    split(u, v);
//    return maxv[v];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> C >> q;
//    for (int i = 1, v; i <= n; i++) {
//        cin >> v;
//        for (int c = 0; c < C; c++) {
//            int cur = node(c, i);
//            arr[cur] = v;
//            maxv[cur] = v;
//        }
//    }
//    for (int i = 1, x, y, c; i <= m; i++) {
//        cin >> x >> y >> c;
//        connect(c, x, y);
//    }
//    for (int i = 1, op, x, y, c; i <= q; i++) {
//        cin >> op;
//        if (op == 0) {
//            cin >> x >> y;
//            updateNode(x, y);
//        } else if (op == 1) {
//            cin >> x >> y >> c;
//            int ans = updateEdge(x, y, c);
//            if (ans == 1) {
//                cout << "Error 1." << "\n";
//            }
//            if (ans == 2) {
//                cout << "Error 2." << "\n";
//            }
//            if (ans == 3) {
//                cout << "No such edge." << "\n";
//            }
//            if (ans == 4) {
//                cout << "Success." << "\n";
//            }
//        } else {
//            cin >> c >> x >> y;
//            cout << query(c, x, y) << "\n";
//        }
//    }
//    return 0;
//}