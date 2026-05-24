package class199;

// 旅行，C++版
// 图中有n个点、m条无向边，节点编号1~n，图是普通树或者基环树
// 对于普通树，从1号节点开始，选择不同的遍历顺序，会形成不同的dfs序列
// 其中字典序最小的dfs序列，叫做普通树的答案
// 对于基环树，可以删掉环上的任何一条边，让基环树变成某棵普通树
// 众多普通树的答案中，字典序最小的结果，叫做基环树的答案
// 图如果是普通树，打印普通树的答案，图如果是基环树，打印基环树的答案
// 1 <= n <= 5 * 10^5
// 普通版测试 : https://www.luogu.com.cn/problem/P5022
// 加强版测试 : https://www.luogu.com.cn/problem/P5049
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Edge {
//    int u, v;
//};
//
//bool EdgeCmp(Edge a, Edge b) {
//    if (a.u != b.u) {
//        return a.u < b.u;
//    }
//    return a.v > b.v;
//}
//
//const int MAXN = 500001;
//int n, m;
//
//Edge arr[MAXN << 1];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int dfn[MAXN];
//int cntd;
//
//int from[MAXN];
//bool cycle[MAXN];
//
//bool cut;
//bool vis[MAXN];
//int ans[MAXN];
//int cnta;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs(int u) {
//    dfn[u] = ++cntd;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dfn[v] == 0) {
//            from[v] = u;
//            dfs(v);
//        } else if (dfn[u] < dfn[v]) {
//            cycle[u] = true;
//            for (int i = v; i != u; i = from[i]) {
//                cycle[i] = true;
//            }
//        }
//    }
//}
//
//void path(int u, int back) {
//    vis[u] = true;
//    ans[++cnta] = u;
//    if (!cycle[u] || cut) {
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            if (!vis[v]) {
//                path(v, back);
//            }
//        }
//        return;
//    }
//    int end = 0;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            end = v;
//        }
//    }
//    cut = cycle[end] && end > back;
//    if (cut) {
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            if (!vis[v] && v != end) {
//                path(v, back);
//            }
//        }
//        return;
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            if (!cycle[v]) {
//                path(v, back);
//            } else {
//                int next = back;
//                for (int ne = nxt[e]; ne > 0; ne = nxt[ne]) {
//                    int nv = to[ne];
//                    if (!vis[nv]) {
//                        next = nv;
//                        break;
//                    }
//                }
//                path(v, next);
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        arr[i] = {u, v};
//        arr[i + m] = {v, u};
//    }
//    sort(arr + 1, arr + m * 2 + 1, EdgeCmp);
//    for (int i = 1; i <= m * 2; i++) {
//        addEdge(arr[i].u, arr[i].v);
//    }
//    dfs(1);
//    path(1, n + 1);
//    for (int i = 1; i <= n; i++) {
//        cout << ans[i] << " ";
//    }
//    return 0;
//}