package class199;

// 简单路径数量，C++版
// 图中有n个点、n条无向边，图保证连通，没有自环和重边
// 路径的长度为边的数量，计算长度>=1，不同简单路径的数量
// 简单路径a、b、c和c、b、a，认为是同一条，不要重复计算
// 3 <= n <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1454E
// 测试链接 : https://codeforces.com/problemset/problem/1454/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//using namespace std;
//using ll = long long;
//
//const int MAXN = 200001;
//int t, n;
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
//ll siz[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs(int u, int preEdge) {
//    dfn[u] = ++cntd;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (e != (preEdge ^ 1)) {
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
//void dpOnTree(int u, int fa) {
//    siz[u] = 1;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !cycle[v]) {
//            dpOnTree(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//ll compute() {
//    dfs(1, 0);
//    ll ans = 0;
//    for (int i = 1; i <= n; i++) {
//        if (cycle[i]) {
//            dpOnTree(i, 0);
//            ans += siz[i] * (siz[i] - 1) + siz[i] * 2 * (n - siz[i]);
//        }
//    }
//    return ans / 2;
//}
//
//void prepare() {
//    cntg = 1;
//    cntd = 0;
//    for (int i = 1; i <= n; i++) {
//        head[i] = dfn[i] = 0;
//        cycle[i] = false;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> t;
//    for (int c = 1; c <= t; c++) {
//        cin >> n;
//        prepare();
//        for (int i = 1, u, v; i <= n; i++) {
//            cin >> u >> v;
//            addEdge(u, v);
//            addEdge(v, u);
//        }
//        ll ans = compute();
//        cout << ans << "\n";
//    }
//    return 0;
//}