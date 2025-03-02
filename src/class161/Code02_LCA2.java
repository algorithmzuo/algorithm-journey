package class161;

// 重链剖分解决LCA查询，C++版
// 一共有n个节点，给定n-1条边，节点连成一棵树，给定头节点编号root
// 一共有m条查询，每条查询给定a和b，打印a和b的最低公共祖先
// 请用树链剖分的方式实现
// 1 <= n、m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3379
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 500001;
//int n, m, root;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cnt = 0;
//
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cnt] = head[u];
//    to[cnt] = v;
//    head[u] = cnt;
//}
//
//void dfs1(int u, int f) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            dfs1(v, u);
//        }
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            siz[u] += siz[v];
//            if (son[u] == 0 || siz[son[u]] < siz[v]) {
//                son[u] = v;
//            }
//        }
//    }
//}
//
//void dfs2(int u, int t) {
//    top[u] = t;
//    if(son[u] == 0) {
//        return;
//    }
//    dfs2(son[u], t);
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//int lca(int a, int b) {
//    while (top[a] != top[b]) {
//        if (dep[top[a]] <= dep[top[b]]) {
//            b = fa[top[b]];
//        } else {
//            a = fa[top[a]];
//        }
//    }
//    return dep[a] <= dep[b] ? a : b;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> root;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs1(root, 0);
//    dfs2(root, root);
//    for (int i = 1, a, b; i <= m; i++) {
//        cin >> a >> b;
//        cout << lca(a, b) << "\n";
//    }
//    return 0;
//}