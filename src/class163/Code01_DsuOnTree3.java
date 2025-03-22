package class163;

// 树上启发式合并模版题，C++版
// 一共有n个节点，编号1~n，给定n-1条边，所有节点连成一棵树，1号节点为树头
// 每个节点给定一种颜色值，一共有m条查询，每条查询给定参数x
// 每条查询打印x为头的子树上，一共有多少种不同的颜色
// 1 <= n、m、颜色值 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/U41492
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//int color[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cnt = 0;
//
//int fa[MAXN];
//int siz[MAXN];
//int son[MAXN];
//
//int colorCnt[MAXN];
//int ans[MAXN];
//int diffColors = 0;
//
//void addEdge(int u, int v) {
//    nxt[++cnt] = head[u];
//    to[cnt] = v;
//    head[u] = cnt;
//}
//
//void dfs1(int u, int f) {
//    fa[u] = f;
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
//void effect(int u) {
//    if (++colorCnt[color[u]] == 1) {
//        diffColors++;
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u]) {
//            effect(v);
//        }
//    }
//}
//
//void cancle(int u) {
//    colorCnt[color[u]] = 0;
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u]) {
//            cancle(v);
//        }
//    }
//}
//
//void dfs2(int u, int keep) {
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, 0);
//        }
//    }
//    if (son[u] != 0) {
//        dfs2(son[u], 1);
//    }
//    if (++colorCnt[color[u]] == 1) {
//        diffColors++;
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            effect(v);
//        }
//    }
//    ans[u] = diffColors;
//    if (keep == 0) {
//        diffColors = 0;
//        cancle(u);
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
//    for (int i = 1; i <= n; i++) {
//        cin >> color[i];
//    }
//    dfs1(1, 0);
//    dfs2(1, 0);
//    cin >> m;
//    for (int i = 1, cur; i <= m; i++) {
//        cin >> cur;
//        cout << ans[cur] << "\n";
//    }
//    return 0;
//}