package class185;

// Atm的树，C++版
// 树上有n个点，给定n-1条边，每条边有边权
// 现在关心，从节点x出发到达其他点的距离中，第k小的距离
// 注意，节点x到自己的距离0，不参与距离评比
// 给定正数k，打印每个节点的第k小距离，一共n条打印
// 1 <= n <= 15000
// 1 <= k <= 5000
// 1 <= 边权 <= 10
// 测试链接 : https://www.luogu.com.cn/problem/P10604
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 20001;
//const int MAXH = 18;
//const int MAXT = 1000001;
//int n, k, sumw;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int weight[MAXN << 1];
//int cntg;
//
//int dep[MAXN];
//int dist[MAXN];
//int stjump[MAXN][MAXH];
//
//bool vis[MAXN];
//int siz[MAXN];
//int centfa[MAXN];
//
//int addTree[MAXN];
//int minusTree[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int sum[MAXT];
//int cntt;
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//void dfs(int u, int fa, int dis) {
//    dep[u] = dep[fa] + 1;
//    dist[u] = dis;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (v != fa) {
//            dfs(v, u, dis + w);
//        }
//    }
//}
//
//int getLca(int a, int b) {
//    if (dep[a] < dep[b]) {
//        swap(a, b);
//    }
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (dep[stjump[a][p]] >= dep[b]) {
//            a = stjump[a][p];
//        }
//    }
//    if (a == b) {
//        return a;
//    }
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (stjump[a][p] != stjump[b][p]) {
//            a = stjump[a][p];
//            b = stjump[b][p];
//        }
//    }
//    return stjump[a][0];
//}
//
//int getDist(int x, int y) {
//    return dist[x] + dist[y] - (dist[getLca(x, y)] << 1);
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            getSize(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//int getCentroid(int u, int fa) {
//    getSize(u, fa);
//    int half = siz[u] >> 1;
//    bool find = false;
//    while (!find) {
//        find = true;
//        for (int e = head[u]; e; e = nxt[e]) {
//            int v = to[e];
//            if (v != fa && !vis[v] && siz[v] > half) {
//                fa = u;
//                u = v;
//                find = false;
//                break;
//            }
//        }
//    }
//    return u;
//}
//
//void centroidTree(int u, int fa) {
//    centfa[u] = fa;
//    vis[u] = true;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            centroidTree(getCentroid(v, u), u);
//        }
//    }
//}
//
//int add(int jobi, int jobv, int l, int r, int i) {
//    if (i == 0) {
//        i = ++cntt;
//    }
//    if (l == r) {
//        sum[i] += jobv;
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            ls[i] = add(jobi, jobv, l, mid, ls[i]);
//        } else {
//            rs[i] = add(jobi, jobv, mid + 1, r, rs[i]);
//        }
//        sum[i] = sum[ls[i]] + sum[rs[i]];
//    }
//    return i;
//}
//
//int query(int jobl, int jobr, int l, int r, int i) {
//    if (i == 0) {
//        return 0;
//    }
//    if (jobl <= l && r <= jobr) {
//        return sum[i];
//    }
//    int mid = (l + r) >> 1;
//    int ans = 0;
//    if (jobl <= mid) {
//        ans += query(jobl, jobr, l, mid, ls[i]);
//    }
//    if (jobr > mid) {
//        ans += query(jobl, jobr, mid + 1, r, rs[i]);
//    }
//    return ans;
//}
//
//void add(int x, int v) {
//    addTree[x] = add(0, v, 0, sumw, addTree[x]);
//    for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
//        int dist = getDist(x, fa);
//        addTree[fa] = add(dist, v, 0, sumw, addTree[fa]);
//        minusTree[cur] = add(dist, v, 0, sumw, minusTree[cur]);
//    }
//}
//
//int query(int x, int limit) {
//    int ans = query(0, limit, 0, sumw, addTree[x]);
//    for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
//        int dist = getDist(x, fa);
//        if (limit - dist >= 0) {
//            ans += query(0, limit - dist, 0, sumw, addTree[fa]);
//            ans -= query(0, limit - dist, 0, sumw, minusTree[cur]);
//        }
//    }
//    return ans;
//}
//
//int compute(int x) {
//    int ans = 0;
//    int l = 0, r = sumw, mid;
//    while (l <= r) {
//        mid = (l + r) >> 1;
//        if (query(x, mid) >= k) {
//            ans = mid;
//            r = mid - 1;
//        } else {
//            l = mid + 1;
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    cin >> k;
//    k = k + 1;
//    for (int i = 1, u, v, w; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdge(u, v, w);
//        addEdge(v, u, w);
//        sumw += w;
//    }
//    dfs(1, 0, 0);
//    centroidTree(getCentroid(1, 0), 0);
//    for (int i = 1; i <= n; i++) {
//        add(i, 1);
//    }
//    for (int i = 1; i <= n; i++) {
//        cout << compute(i) << '\n';
//    }
//    return 0;
//}