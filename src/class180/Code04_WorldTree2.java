package class180;

// 世界树，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P3233
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 300001;
//const int MAXP = 20;
//const int INF = 1000000001;
//int n, q, k;
//
//int headg[MAXN];
//int nextg[MAXN << 1];
//int tog[MAXN << 1];
//int cntg;
//
//int headv[MAXN];
//int nextv[MAXN];
//int tov[MAXN];
//int cntv;
//
//int dep[MAXN];
//int siz[MAXN];
//int dfn[MAXN];
//int stjump[MAXN][MAXP];
//int cntd;
//
//int order[MAXN];
//int arr[MAXN];
//bool isKey[MAXN];
//int tmp[MAXN << 1];
//
//int dp[MAXN];
//int pick[MAXN];
//int ans[MAXN];
//
//void addEdgeG(int u, int v) {
//    nextg[++cntg] = headg[u];
//    tog[cntg] = v;
//    headg[u] = cntg;
//}
//
//void addEdgeV(int u, int v) {
//    nextv[++cntv] = headv[u];
//    tov[cntv] = v;
//    headv[u] = cntv;
//}
//
//bool cmp(int x, int y) {
//    return dfn[x] < dfn[y];
//}
//
//void dfs(int u, int fa) {
//    dep[u] = dep[fa] + 1;
//    siz[u] = 1;
//    dfn[u] = ++cntd;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXP; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = headg[u]; e; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa) {
//            dfs(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//int getLca(int a, int b) {
//    if (dep[a] < dep[b]) {
//        swap(a, b);
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
//int buildVirtualTree() {
//    sort(arr + 1, arr + k + 1, cmp);
//    int len = 0;
//    for (int i = 1; i < k; i++) {
//    	tmp[++len] = arr[i];
//    	tmp[++len] = getLca(arr[i], arr[i + 1]);
//    }
//    tmp[++len] = arr[k];
//    tmp[++len] = 1;
//    sort(tmp + 1, tmp + len + 1, cmp);
//    int unique = 1;
//    for (int i = 2; i <= len; i++) {
//        if (tmp[unique] != tmp[i]) {
//        	tmp[++unique] = tmp[i];
//        }
//    }
//    cntv = 0;
//    for (int i = 1; i <= unique; i++) {
//        headv[tmp[i]] = 0;
//    }
//    for (int i = 1; i < unique; i++) {
//        addEdgeV(getLca(tmp[i], tmp[i + 1]), tmp[i + 1]);
//    }
//    return tmp[1];
//}
//
//void dp1(int u) {
//    dp[u] = INF;
//    for (int e = headv[u]; e; e = nextv[e]) {
//        int v = tov[e];
//        dp1(v);
//        int dis = dep[v] - dep[u];
//        if (dp[u] > dp[v] + dis) {
//            dp[u] = dp[v] + dis;
//            pick[u] = pick[v];
//        } else if (dp[u] == dp[v] + dis) {
//            pick[u] = min(pick[u], pick[v]);
//        }
//    }
//    if (isKey[u]) {
//        dp[u] = 0;
//        pick[u] = u;
//    }
//}
//
//void calc(int x, int y) {
//    int b = y;
//    for (int p = MAXP - 1; p >= 0; p--) {
//        int l = (dep[y] - dep[stjump[b][p]]) + dp[y];
//        int r = (dep[stjump[b][p]] - dep[x]) + dp[x];
//        if (dep[stjump[b][p]] > dep[x] && (l < r || (l == r && pick[y] < pick[x]))) {
//            b = stjump[b][p];
//        }
//    }
//    ans[pick[y]] += siz[b] - siz[y];
//    ans[pick[x]] -= siz[b];
//}
//
//void dp2(int u) {
//    for (int e = headv[u]; e; e = nextv[e]) {
//        int v = tov[e];
//        int dis = dep[v] - dep[u];
//        if (dp[v] > dp[u] + dis) {
//            dp[v] = dp[u] + dis;
//            pick[v] = pick[u];
//        } else if (dp[v] == dp[u] + dis) {
//            pick[v] = min(pick[v], pick[u]);
//        }
//        calc(u, v);
//        dp2(v);
//    }
//    ans[pick[u]] += siz[u];
//}
//
//void compute() {
//    for (int i = 1; i <= k; i++) {
//        arr[i] = order[i];
//    }
//    for (int i = 1; i <= k; i++) {
//        isKey[arr[i]] = true;
//        ans[arr[i]] = 0;
//    }
//    int tree = buildVirtualTree();
//    dp1(tree);
//    dp2(tree);
//    for (int i = 1; i <= k; i++) {
//        isKey[arr[i]] = false;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdgeG(u, v);
//        addEdgeG(v, u);
//    }
//    dfs(1, 0);
//    cin >> q;
//    for (int t = 1; t <= q; t++) {
//        cin >> k;
//        for (int i = 1; i <= k; i++) {
//            cin >> order[i];
//        }
//        compute();
//        for (int i = 1; i <= k; i++) {
//            cout << ans[order[i]] << ' ';
//        }
//        cout << '\n';
//    }
//    return 0;
//}