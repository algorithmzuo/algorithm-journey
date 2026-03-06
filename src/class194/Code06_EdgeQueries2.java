package class194;

// 边的查询，C++版
// 给定一张无向图，一共n个点、m条边，所有点保证连通
// 一共q条查询，格式 x y，含义如下
// 所有从x到y的简单路径上出现的边中，统计有多少条边满足
// 如果移除该边，x和y仍然可以互相到达，打印这个数量
// 1 <= n、m、q <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1763F
// 测试链接 : https://codeforces.com/problemset/problem/1763/F
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//const int MAXM = 200001;
//const int MAXP = 20;
//int n, m, q, cntn;
//int a[MAXM];
//int b[MAXM];
//
//int head1[MAXN];
//int next1[MAXM << 1];
//int to1[MAXM << 1];
//int cnt1;
//
//int head2[MAXN << 1];
//int next2[MAXM << 2];
//int to2[MAXM << 2];
//int cnt2;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//
//int sta[MAXN];
//int top;
//
//int dep[MAXN << 1];
//int stjump[MAXN << 1][MAXP];
//int edgeCnt[MAXN << 1];
//
//void addEdge1(int u, int v) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v) {
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    head2[u] = cnt2;
//}
//
//void tarjan(int u) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        if (dfn[v] == 0) {
//            tarjan(v);
//            low[u] = min(low[u], low[v]);
//            if (low[v] >= dfn[u]) {
//                cntn++;
//                addEdge2(cntn, u);
//                addEdge2(u, cntn);
//                int pop;
//                do {
//                    pop = sta[top--];
//                    addEdge2(cntn, pop);
//                    addEdge2(pop, cntn);
//                } while (pop != v);
//            }
//        } else {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//}
//
//void dfs(int u, int fa) {
//    dep[u] = dep[fa] + 1;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXP; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa) {
//            dfs(v, u);
//        }
//    }
//}
//
//int getLca(int x, int y) {
//    if (dep[x] < dep[y]) {
//        swap(x, y);
//    }
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (dep[stjump[x][p]] >= dep[y]) {
//            x = stjump[x][p];
//        }
//    }
//    if (x == y) {
//        return x;
//    }
//    for (int p = MAXP - 1; p >= 0; p--) {
//        if (stjump[x][p] != stjump[y][p]) {
//            x = stjump[x][p];
//            y = stjump[y][p];
//        }
//    }
//    return stjump[x][0];
//}
//
//void dfsCnt(int u, int fa) {
//    edgeCnt[u] += edgeCnt[fa];
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa) {
//            dfsCnt(v, u);
//        }
//    }
//}
//
//void buildEdgeCnt() {
//    for (int i = 1; i <= m; i++) {
//        int fa = stjump[a[i]][0];
//        int fb = stjump[b[i]][0];
//        if (fa == fb || stjump[fa][0] == b[i]) {
//            edgeCnt[fa]++;
//        } else {
//            edgeCnt[fb]++;
//        }
//    }
//    for (int i = n + 1; i <= cntn; i++) {
//        if (edgeCnt[i] == 1) {
//            edgeCnt[i] = 0;
//        }
//    }
//    dfsCnt(1, 0);
//}
//
//int query(int x, int y) {
//    int xylca = getLca(x, y);
//    int lcafa = stjump[xylca][0];
//    return edgeCnt[x] + edgeCnt[y] - edgeCnt[xylca] - edgeCnt[lcafa];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    cntn = n;
//    for (int i = 1; i <= m; i++) {
//        cin >> a[i] >> b[i];
//        addEdge1(a[i], b[i]);
//        addEdge1(b[i], a[i]);
//    }
//    tarjan(1);
//    dfs(1, 0);
//    buildEdgeCnt();
//    cin >> q;
//    for (int i = 1, x, y; i <= q; i++) {
//        cin >> x >> y;
//        cout << query(x, y) << "\n";
//    }
//    return 0;
//}