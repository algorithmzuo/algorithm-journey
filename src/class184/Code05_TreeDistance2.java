package class184;

// 支配点对距离，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P9678
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query {
//    int l, r, id;
//};
//
//struct Key {
//    int l, r;
//    long long dist;
//};
//
//bool QueryCmp(Query a, Query b) {
//    return a.l > b.l;
//}
//
//bool KeyCmp(Key a, Key b) {
//    return a.l > b.l;
//}
//
//const int MAXN = 200001;
//const int MAXM = 1000001;
//const int MAXK = 10000001;
//const long long INF = 1LL << 60;
//int n, m;
//Query queryArr[MAXM];
//
//Key keyArr[MAXK];
//int cntk;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int weight[MAXN << 1];
//int cntg;
//
//bool vis[MAXN];
//int siz[MAXN];
//
//long long dist[MAXN];
//int nodeArr[MAXN];
//int cnta;
//int sta[MAXN];
//long long tree[MAXN];
//
//long long ans[MAXM];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
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
//void dfs(int u, int fa, long long dis) {
//    dist[u] = dis;
//    nodeArr[++cnta] = u;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (v != fa && !vis[v]) {
//            dfs(v, u, dis + w);
//        }
//    }
//}
//
//void calc(int u) {
//    cnta = 0;
//    dfs(u, 0, 0);
//    sort(nodeArr + 1, nodeArr + cnta + 1);
//    int top = 0;
//    for (int i = 1; i <= cnta; i++) {
//        int cur = nodeArr[i];
//        while (top > 0 && dist[sta[top]] >= dist[cur]) {
//            keyArr[++cntk] = { sta[top], cur, dist[sta[top]] + dist[cur] };
//            top--;
//        }
//        if (top > 0) {
//            keyArr[++cntk] = { sta[top], cur, dist[sta[top]] + dist[cur] };
//        }
//        sta[++top] = cur;
//    }
//}
//
//void solve(int u) {
//    vis[u] = true;
//    calc(u);
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            solve(getCentroid(v, u));
//        }
//    }
//}
//
//void buildTree() {
//    for (int i = 1; i <= n; i++) {
//        tree[i] = INF;
//    }
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, long long v) {
//    while (i <= n) {
//        tree[i] = min(tree[i], v);
//        i += lowbit(i);
//    }
//}
//
//long long query(int i) {
//    long long ret = INF;
//    while (i > 0) {
//        ret = min(ret, tree[i]);
//        i -= lowbit(i);
//    }
//    return ret;
//}
//
//void compute() {
//    solve(getCentroid(1, 0));
//    sort(queryArr + 1, queryArr + m + 1, QueryCmp);
//    sort(keyArr + 1, keyArr + cntk + 1, KeyCmp);
//    buildTree();
//    int idx = 1;
//    for (int i = 1; i <= m; i++) {
//        while (idx <= cntk && keyArr[idx].l >= queryArr[i].l) {
//            add(keyArr[idx].r, keyArr[idx].dist);
//            idx++;
//        }
//        if (queryArr[i].l == queryArr[i].r) {
//            ans[queryArr[i].id] = -1;
//        } else {
//            ans[queryArr[i].id] = query(queryArr[i].r);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1, u, v, w; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdge(u, v, w);
//        addEdge(v, u, w);
//    }
//    cin >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> queryArr[i].l >> queryArr[i].r;
//        queryArr[i].id = i;
//    }
//    compute();
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}