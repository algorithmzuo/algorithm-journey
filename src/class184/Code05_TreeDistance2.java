package class184;

// 支配点对距离，C++版
// 一共有n个节点，给定n-1条边，每条边给定边权，所有节点组成一棵树
// 节点i到节点j的简单路径权值和，定义为dist(i, j)
// 在[x, y]范围上，选两个编号a、b，要求a < b，这样的选择可能有很多情况
// 如果dist(a, b)是所有情况中最小的，就说点对(a, b)支配了[x, y]范围
// 也可以说，[x, y]范围的支配点对距离是dist(a, b)
// 特别的，如果x == y，那么[x, y]范围的支配点对距离是-1
// 一共有m条查询，格式 x y : 输入保证x <= y，打印[x, y]范围的支配点对距离
// 1 <= n <= 2 * 10^5    1 <= m <= 10^6    1 <= 边权 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P9678
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query {
//    int x, y, id;
//};
//
//struct Key {
//    int a, b;
//    long long dist;
//};
//
//bool QueryCmp(Query q1, Query q2) {
//    return q1.x > q2.x;
//}
//
//bool KeyCmp(Key k1, Key k2) {
//    return k1.a > k2.a;
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
//    for (int i = 1, j = 1; i <= m; i++) {
//        for (; j <= cntk && keyArr[j].a >= queryArr[i].x; j++) {
//            add(keyArr[j].b, keyArr[j].dist);
//        }
//        if (queryArr[i].x == queryArr[i].y) {
//            ans[queryArr[i].id] = -1;
//        } else {
//            ans[queryArr[i].id] = query(queryArr[i].y);
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
//        cin >> queryArr[i].x >> queryArr[i].y;
//        queryArr[i].id = i;
//    }
//    compute();
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}