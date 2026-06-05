package class200;

// 最短路最大值，C++版
// 给定n个点、m条边的仙人掌图，每条边有边权，没有自环，可能有重边
// 一共q条查询，格式 k a1 a2 .. ak 含义如下
// 给定的k个点中，任意选两个点，可以选择相同点
// 打印它们之间最短路的最大值是多少
// 1 <= n、查询中k的总和 <= 3 * 10^5
// 测试链接 : https://uoj.ac/problem/87
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 600001;
//int n, m, q, k, cntn;
//
//int head1[MAXN];
//int next1[MAXN << 1];
//int to1[MAXN << 1];
//int weight1[MAXN << 1];
//int cnt1;
//
//int head2[MAXN];
//int next2[MAXN];
//int to2[MAXN];
//ll weight2[MAXN];
//int cnt2;
//
//int head3[MAXN];
//int next3[MAXN];
//int to3[MAXN];
//int cnt3;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//int sta[MAXN];
//int stasiz;
//
//ll fromWeight[MAXN];
//ll cycleDist[MAXN];
//ll cycleSum[MAXN];
//
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//ll dist[MAXN];
//int son[MAXN];
//int top[MAXN];
//
//int arr[MAXN];
//int tmp[MAXN];
//
//ll dp[MAXN];
//ll ans;
//
//int idx[MAXN];
//ll pos[MAXN];
//ll val[MAXN];
//int que[MAXN];
//
//void addEdge1(int u, int v, int w) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    weight1[cnt1] = w;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v, ll w) {
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    weight2[cnt2] = w;
//    head2[u] = cnt2;
//}
//
//void addEdge3(int u, int v) {
//    next3[++cnt3] = head3[u];
//    to3[cnt3] = v;
//    head3[u] = cnt3;
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
//        cycleDist[pop] = cycleSum[cntn];
//        cycleSum[cntn] += fromWeight[pop];
//    } while (pop != v);
//    do {
//        pop = sta[stasiz--];
//        addEdge2(cntn, pop, min(cycleDist[pop], cycleSum[cntn] - cycleDist[pop]));
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
//        ll w = weight1[e];
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
//void dfs1(int u, int f, ll dis) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    dist[u] = dis;
//    for (int e = head2[u], v; e > 0; e = next2[e]) {
//        v = to2[e];
//        if (v != f) {
//            dfs1(v, u, dist[u] + weight2[e]);
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
//    dfn[u] = ++cntd;
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
//int find(int cur, int lca) {
//    int ans = 0;
//    while (top[cur] != top[lca]) {
//        ans = top[cur];
//        cur = fa[top[cur]];
//    }
//    return cur == lca ? ans : son[lca];
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
//int buildVirtualTree() {
//    sort(arr + 1, arr + k + 1, [](int a, int b) {
//        return dfn[a] < dfn[b];
//    });
//    int len = 0;
//    for (int i = 1; i < k; i++) {
//        tmp[++len] = arr[i];
//        tmp[++len] = lca(arr[i], arr[i + 1]);
//    }
//    tmp[++len] = arr[k];
//    sort(tmp + 1, tmp + len + 1, [](int a, int b) {
//        return dfn[a] < dfn[b];
//    });
//    int unique = 1;
//    for (int i = 2; i <= len; i++) {
//        if (tmp[unique] != tmp[i]) {
//            tmp[++unique] = tmp[i];
//        }
//    }
//    cnt3 = 0;
//    for (int i = 1; i <= unique; i++) {
//        head3[tmp[i]] = 0;
//    }
//    for (int i = 1; i < unique; i++) {
//        addEdge3(lca(tmp[i], tmp[i + 1]), tmp[i + 1]);
//    }
//    return tmp[1];
//}
//
//void update(int u, int siz) {
//    sort(idx + 1, idx + siz + 1, [](int a, int b) {
//        return cycleDist[a] < cycleDist[b];
//    });
//    for (int i = 1; i <= siz; i++) {
//        pos[i] = cycleDist[idx[i]];
//        pos[i + siz] = pos[i] + cycleSum[u];
//        val[i] = dp[idx[i]];
//        val[i + siz] = val[i];
//    }
//    int l = 1;
//    int r = 0;
//    for (int i = 1; i <= siz * 2; i++) {
//        while (l <= r && (pos[i] - pos[que[l]]) * 2 > cycleSum[u]) {
//            l++;
//        }
//        if (l <= r) {
//            ans = max(ans, val[que[l]] - pos[que[l]] + val[i] + pos[i]);
//        }
//        while (l <= r && val[que[r]] - pos[que[r]] <= val[i] - pos[i]) {
//            r--;
//        }
//        que[++r] = i;
//    }
//}
//
//void dpOnTree(int u) {
//    dp[u] = 0;
//    for (int e = head3[u]; e > 0; e = next3[e]) {
//        int v = to3[e];
//        dpOnTree(v);
//    }
//    int siz = 0;
//    for (int e = head3[u]; e > 0; e = next3[e]) {
//        int v = to3[e];
//        if (u <= n) {
//            ans = max(ans, dp[u] + dp[v] + dist[v] - dist[u]);
//        } else {
//            int f = find(v, u);
//            dp[f] = dp[v] + dist[v] - dist[f];
//            idx[++siz] = f;
//        }
//        dp[u] = max(dp[u], dp[v] + dist[v] - dist[u]);
//    }
//    if (siz >= 2) {
//        update(u, siz);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    cntn = n;
//    cnt1 = 1;
//    for (int i = 1, u, v, w; i <= m; i++) {
//        cin >> u >> v >> w;
//        addEdge1(u, v, w);
//        addEdge1(v, u, w);
//    }
//    tarjan(1, 0);
//    cntd = 0;
//    dfs1(1, 0, 0);
//    dfs2(1, 1);
//    cin >> q;
//    for (int i = 1; i <= q; i++) {
//        cin >> k;
//        for (int j = 1; j <= k; j++) {
//            cin >> arr[j];
//        }
//        ans = 0;
//        int tree = buildVirtualTree();
//        dpOnTree(tree);
//        cout << ans << "\n";
//    }
//    return 0;
//}