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
//const int MAXN = 200001;
//int n, q;
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
//int vir[MAXN];
//int sum[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void up(int x) {
//    sum[x] = sum[ls[x]] + sum[rs[x]] + vir[x] + val[x];
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
//        vir[x] += sum[rs[x]];
//        vir[x] -= sum[y];
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
//    vir[f] += sum[x];
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
//    int cur = val[x] == 1 ? x : x + n;
//    int top = findroot(cur);
//    return val[top] == 1 ? sum[top] : sum[rs[top]];
//}
//
//void changeColor(int x) {
//    int pre = val[x] == 1 ? x : x + n;
//    int cur = pre <= n ? pre + n : pre - n;
//    cut(pre, parent[pre]);
//    val[pre] = 0;
//    val[cur] = 1;
//    link(cur, parent[cur]);
//}
//
//void dfs(int u, int f) {
//    if (f != 0) {
//        parent[u] = f;
//        parent[u + n] = f + n;
//    }
//    val[u] = sum[u] = 1;
//    for (int e = head[u]; e != 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != f) {
//            dfs(v, u);
//            link(v, u);
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