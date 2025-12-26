package class164;

// 加边直到连通，C++版
// 图里有n个点，m条无向边，点的编号1~n，边的编号1~m，所有点都连通
// 一共有q条查询，每条查询格式如下
// 查询 l r : 至少要加完编号前多少的边，才能使得[l, r]中的所有点连通
// 1 <= n <= 10^5
// 1 <= m、q <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1706E
// 测试链接 : https://codeforces.com/problemset/problem/1706/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Edge {
//    int u, v, w;
//};
//
//bool cmp(Edge x, Edge y) {
//    return x.w < y.w;
//}
//
//const int MAXN = 100001;
//const int MAXK = 200001;
//const int MAXM = 200001;
//const int MAXP = 20;
//int t, n, m, q;
//Edge edge[MAXM];
//
//int father[MAXK];
//
//int head[MAXK];
//int nxt[MAXK];
//int to[MAXK];
//int cntg;
//int nodeKey[MAXK];
//int cntu;
//
//int dep[MAXK];
//int dfn[MAXK];
//int seg[MAXK];
//int stjump[MAXK][MAXP];
//int cntd;
//
//int lg2[MAXN];
//int stmax[MAXN][MAXP];
//int stmin[MAXN][MAXP];
//
//void clear() {
//    cntg = 0;
//    cntd = 0;
//    for (int i = 1; i <= n * 2; i++) {
//        head[i] = 0;
//    }
//}
//
//int find(int i) {
//    if (i != father[i]) {
//        father[i] = find(father[i]);
//    }
//    return father[i];
//}
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void kruskalRebuild() {
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//    }
//    sort(edge + 1, edge + m + 1, cmp);
//    cntu = n;
//    for (int i = 1, fx, fy; i <= m; i++) {
//        fx = find(edge[i].u);
//        fy = find(edge[i].v);
//        if (fx != fy) {
//            father[fx] = father[fy] = ++cntu;
//            father[cntu] = cntu;
//            nodeKey[cntu] = edge[i].w;
//            addEdge(cntu, fx);
//            addEdge(cntu, fy);
//        }
//    }
//}
//
//void dfs(int u, int fa) {
//    dep[u] = dep[fa] + 1;
//    dfn[u] = ++cntd;
//    seg[cntd] = u;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXP; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        dfs(to[e], u);
//    }
//}
//
//void buildst() {
//	lg2[0] = -1;
//    for (int i = 1; i <= n; i++) {
//    	lg2[i] = lg2[i >> 1] + 1;
//        stmax[i][0] = dfn[i];
//        stmin[i][0] = dfn[i];
//    }
//    for (int p = 1; p <= lg2[n]; p++) {
//        for (int i = 1; i + (1 << p) - 1 <= n; i++) {
//            stmax[i][p] = max(stmax[i][p - 1], stmax[i + (1 << (p - 1))][p - 1]);
//            stmin[i][p] = min(stmin[i][p - 1], stmin[i + (1 << (p - 1))][p - 1]);
//        }
//    }
//}
//
//int dfnmin(int l, int r) {
//    int p = lg2[r - l + 1];
//    int ans = min(stmin[l][p], stmin[r - (1 << p) + 1][p]);
//    return ans;
//}
//
//int dfnmax(int l, int r) {
//    int p = lg2[r - l + 1];
//    int ans = max(stmax[l][p], stmax[r - (1 << p) + 1][p]);
//    return ans;
//}
//
//int lca(int a, int b) {
//    if (dep[a] < dep[b]) {
//        int tmp = a;
//        a = b;
//        b = tmp;
//    }
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (dep[stjump[a][p]] >= dep[b]) {
//            a = stjump[a][p];
//        }
//    }
//    if (a == b) {
//        return a;
//    }
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (stjump[a][p] != stjump[b][p]) {
//            a = stjump[a][p];
//            b = stjump[b][p];
//        }
//    }
//    return stjump[a][0];
//}
//
//int query(int l, int r) {
//    int x = seg[dfnmin(l, r)];
//    int y = seg[dfnmax(l, r)];
//    return nodeKey[lca(x, y)];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> t;
//    for (int test = 1; test <= t; test++) {
//        cin >> n >> m >> q;
//        for (int i = 1; i <= m; i++) {
//            cin >> edge[i].u >> edge[i].v;
//            edge[i].w = i;
//        }
//        clear();
//        kruskalRebuild();
//        dfs(cntu, 0);
//        buildst();
//        for (int i = 1, l, r; i <= q; i++) {
//            cin >> l >> r;
//            if (l == r) {
//                cout << 0 << " ";
//            } else {
//                cout << query(l, r) << " ";
//            }
//        }
//        cout << "\n";
//    }
//    return 0;
//}