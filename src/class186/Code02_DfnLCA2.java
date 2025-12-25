package class186;

// dfn序求LCA，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3379
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 500001;
//const int MAXH = 20;
//int n, m, root;
//
//int headg[MAXN];
//int nextg[MAXN << 1];
//int tog[MAXN << 1];
//int cntg;
//
//int dfn[MAXN];
//int lg2[MAXN];
//int rmq[MAXN][MAXH];
//int cntd;
//
//void addEdge(int u, int v) {
//    nextg[++cntg] = headg[u];
//    tog[cntg] = v;
//    headg[u] = cntg;
//}
//
//int getUp(int x, int y) {
//    return dfn[x] <= dfn[y] ? x : y;
//}
//
//void dfs(int u, int fa) {
//    dfn[u] = ++cntd;
//    rmq[dfn[u]][0] = fa;
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa) {
//            dfs(v, u);
//        }
//    }
//}
//
//void buildRmq() {
//    dfs(root, 0);
//    for (int i = 2; i <= n; i++) {
//        lg2[i] = lg2[i >> 1] + 1;
//    }
//    for (int pre = 0, cur = 1; cur <= lg2[n]; pre++, cur++) {
//        for (int i = 1; i + (1 << cur) - 1 <= n; i++) {
//            rmq[i][cur] = getUp(rmq[i][pre], rmq[i + (1 << pre)][pre]);
//        }
//    }
//}
//
//int getLCA(int x, int y) {
//    if (x == y) {
//        return x;
//    }
//    x = dfn[x];
//    y = dfn[y];
//    if (x > y) {
//        swap(x, y);
//    }
//    x++;
//    int k = lg2[y - x + 1];
//    return getUp(rmq[x][k], rmq[y - (1 << k) + 1][k]);
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
//    buildRmq();
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        cout << getLCA(u, v) << '\n';
//    }
//    return 0;
//}