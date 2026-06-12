package class200;

// 仙人掌最短路，C++版
// 给定n个点、m条边的仙人掌图，每条边有边权，没有自环，没有重边
// 一共q条查询，每条查询格式为 x y，查询点x和点y之间的最短路距离
// 1 <= n、q <= 10^4
// 1 <= m <= 2 * 10^4
// 1 <= 边权 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5236
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
//int n, m, q, cntn;
//
//int head1[MAXN];
//int next1[MAXN];
//int to1[MAXN];
//int weight1[MAXN];
//int cnt1;
//
//int head2[MAXN];
//int next2[MAXN];
//int to2[MAXN];
//int weight2[MAXN];
//int cnt2;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//int sta[MAXN];
//int stasiz;
//
//int fromWeight[MAXN];
//int cycleLen[MAXN];
//int cycleSum[MAXN];
//
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int len[MAXN];
//int son[MAXN];
//int top[MAXN];
//
//void addEdge1(int u, int v, int w) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    weight1[cnt1] = w;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v, int w) {
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    weight2[cnt2] = w;
//    head2[u] = cnt2;
//}
//
//void cycleLink(int u, int v) {
//    cntn++;
//    cycleSum[cntn] = fromWeight[u];
//    addEdge2(u, cntn, 0);
//    int tmp = stasiz;
//    int pop;
//    do {
//        pop = sta[tmp--];
//        cycleLen[pop] = cycleSum[cntn];
//        cycleSum[cntn] += fromWeight[pop];
//    } while (pop != v);
//    do {
//        pop = sta[stasiz--];
//        addEdge2(cntn, pop, min(cycleLen[pop], cycleSum[cntn] - cycleLen[pop]));
//    } while (pop != v);
//}
//
//void tarjan(int u, int preEdge) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++stasiz] = u;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        if ((e ^ 1) == preEdge) {
//            continue;
//        }
//        int v = to1[e];
//        int w = weight1[e];
//        if (dfn[v] == 0) {
//            tarjan(v, e);
//            fromWeight[v] = w;
//            if (low[v] < dfn[u]) {
//                low[u] = min(low[u], low[v]);
//            } else if (low[v] > dfn[u]) {
//                stasiz--;
//                addEdge2(u, v, w);
//            } else {
//                cycleLink(u, v);
//            }
//        } else if (dfn[v] < dfn[u]) {
//            fromWeight[v] = w;
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//}
//
//void dfs1(int u, int f, int l) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    len[u] = l;
//    for (int e = head2[u], v; e > 0; e = next2[e]) {
//        v = to2[e];
//        if (v != f) {
//            dfs1(v, u, len[u] + weight2[e]);
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
//    if (son[u] != 0) {
//        dfs2(son[u], t);
//    }
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
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
//int find(int x, int square) {
//    int ans = 0;
//    while (top[x] != top[square]) {
//        ans = top[x];
//        x = fa[top[x]];
//    }
//    return x == square ? ans : son[square];
//}
//
//int query(int x, int y) {
//    int ans = 0;
//    int xylca = lca(x, y);
//    if (xylca <= n) {
//        ans = len[x] + len[y] - (len[xylca] << 1);
//    } else {
//        int fx = find(x, xylca);
//        int fy = find(y, xylca);
//        ans = len[x] + len[y] - len[fx] - len[fy];
//        int small = min(cycleLen[fx], cycleLen[fy]);
//        int big = max(cycleLen[fx], cycleLen[fy]);
//        ans += min(big - small, cycleSum[xylca] - (big - small));
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> q;
//    cntn = n;
//    cnt1 = 1;
//    for (int i = 1, u, v, w; i <= m; i++) {
//        cin >> u >> v >> w;
//        addEdge1(u, v, w);
//        addEdge1(v, u, w);
//    }
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            tarjan(i, 0);
//        }
//    }
//    dfs1(1, 0, 0);
//    dfs2(1, 1);
//    for (int i = 1, x, y; i <= q; i++) {
//        cin >> x >> y;
//        cout << query(x, y) << "\n";
//    }
//    return 0;
//}