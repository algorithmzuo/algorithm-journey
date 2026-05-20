package class199;

// 旅行，C++版
// 图中有n个点、m条无向边，节点编号1~n，图是普通树或者基环树
// 要求得到一个遍历所有节点的dfs序列，并让序列字典序最小
// 如果图是基环树，可以删掉环上的任何一条边，让图变成普通树
// 然后在所有可能方案中，得到字典序最小的dfs序列
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
//    int u, v, id;
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
//int cnte;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int eid[MAXN << 1];
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
//void addEdge(int u, int v, int id) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    eid[cntg] = id;
//    head[u] = cntg;
//}
//
//void dfs(int u, int preEdge) {
//    dfn[u] = ++cntd;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (eid[e] != eid[preEdge]) {
//            if (dfn[v] == 0) {
//                from[v] = u;
//                dfs(v, e);
//            } else if (dfn[u] < dfn[v]) {
//                cycle[u] = true;
//                for (int i = v; i != u; i = from[i]) {
//                    cycle[i] = true;
//                }
//            }
//        }
//    }
//}
//
//void path(int u, int back) {
//    vis[u] = true;
//    ans[++cnta] = u;
//    int cutNode = 0;
//    if (!cut) {
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            if (!vis[v]) {
//                cutNode = cycle[v] && v > back ? v : 0;
//            }
//        }
//    }
//    for (int e = head[u], ne; e > 0; e = ne) {
//        ne = nxt[e];
//        int v = to[e];
//        if (!vis[v]) {
//            if (v == cutNode) {
//                cut = true;
//                return;
//            }
//            int next = 0;
//            for (; ne > 0; ne = nxt[ne]) {
//                int nv = to[ne];
//                if (!vis[nv]) {
//                    next = nv;
//                    break;
//                }
//            }
//            path(v, next > 0 && cycle[u] ? next : back);
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
//        arr[++cnte] = {u, v, i};
//        arr[++cnte] = {v, u, i};
//    }
//    sort(arr + 1, arr + cnte + 1, EdgeCmp);
//    for (int i = 1; i <= cnte; i++) {
//        addEdge(arr[i].u, arr[i].v, arr[i].id);
//    }
//    dfs(1, 0);
//    path(1, n + 1);
//    for (int i = 1; i <= n; i++) {
//        cout << ans[i] << " ";
//    }
//    return 0;
//}