package class164;

// 边的最大编号的最小值，C++版
// 图里有n个点，m条无向边，边的编号1~m，没有边权，所有点都连通
// 一共有q条查询，查询的格式如下
// 查询 x y z : 从两个点x和y出发，希望经过的点数量等于z
//              每个点可以重复经过，但是重复经过只计算一次
//              经过边的最大编号，最小是多少
// 3 <= n、m、q <= 10^5
// 3 <= z <= n
// 测试链接 : https://www.luogu.com.cn/problem/AT_agc002_d
// 测试链接 : https://atcoder.jp/contests/agc002/tasks/agc002_d
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
//const int MAXK = 200001;
//const int MAXM = 100001;
//const int MAXH = 20;
//int n, m, q;
//Edge edge[MAXM];
//int father[MAXK];
//
//int head[MAXK];
//int nxt[MAXK];
//int to[MAXK];
//int cntg = 0;
//int nodeKey[MAXK];
//int cntu;
//
//int leafsiz[MAXK];
//int stjump[MAXK][MAXH];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//int find(int i) {
//    if (i != father[i]) {
//        father[i] = find(father[i]);
//    }
//    return father[i];
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
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        dfs(to[e], u);
//    }
//    if (u <= n) {
//        leafsiz[u] = 1;
//    } else {
//        leafsiz[u] = 0;
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        leafsiz[u] += leafsiz[to[e]];
//    }
//}
//
//bool check(int x, int y, int z, int limit) {
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (stjump[x][p] > 0 && nodeKey[stjump[x][p]] <= limit) {
//            x = stjump[x][p];
//        }
//    }
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (stjump[y][p] > 0 && nodeKey[stjump[y][p]] <= limit) {
//            y = stjump[y][p];
//        }
//    }
//    if (x == y) {
//        return leafsiz[x] >= z;
//    } else {
//        return leafsiz[x] + leafsiz[y] >= z;
//    }
//}
//
//int query(int x, int y, int z) {
//    int l = 1, r = m, ans = 0;
//    while (l <= r) {
//        int mid = (l + r) / 2;
//        if (check(x, y, z, mid)) {
//            ans = mid;
//            r = mid - 1;
//        } else {
//            l = mid + 1;
//        }
//    }
//    return ans;
//}
//
//int main(){
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> edge[i].u >> edge[i].v;
//        edge[i].w = i;
//    }
//    kruskalRebuild();
//    dfs(cntu, 0);
//    cin >> q;
//    for (int i = 1; i <= q; i++) {
//        int x, y, z;
//        cin >> x >> y >> z;
//        cout << query(x, y, z) << "\n";
//    }
//    return 0;
//}