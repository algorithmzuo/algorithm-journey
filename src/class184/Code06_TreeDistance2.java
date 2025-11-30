package class184;

// 支配点对距离，C++版
// 一共有n个节点，给定n-1条边，每条边给定边权，所有节点组成一棵树
// 节点i到节点j的简单路径，边权的累加和，定义为dist(i, j)
// 编号区间[x, y]，考虑所有点对(a, b)，要求 x <= a < b <= y
// 如果dist(a, b)是所有情况中最小的，则称(a, b)为区间[x, y]的支配点对
// 也可以说，区间[x, y]的支配点对距离为dist(a, b)
// 特别的，如果x == y，那么不存在满足要求的点对，此时支配点对距离为-1
// 一共有m次查询，格式 x y : 保证x <= y，打印[x, y]的支配点对距离
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
//struct Pair {
//    int a, b;
//    long long dist;
//};
//
//bool QueryCmp(Query q1, Query q2) {
//    return q1.y < q2.y;
//}
//
//bool PairCmp(Pair p1, Pair p2) {
//    return p1.b < p2.b;
//}
//
//const int MAXN = 200001;
//const int MAXM = 1000001;
//const int MAXP = 10000001;
//const long long INF = 1LL << 60;
//int n, m;
//Query queryArr[MAXM];
//
//Pair pairArr[MAXP];
//int cntp;
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
//
//int sta[MAXN];
//int top;
//
//long long minTree[MAXN << 2];
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
//void stackAdd(int cur) {
//    while (top > 0 && dist[sta[top]] >= dist[cur]) {
//        pairArr[++cntp] = { min(sta[top], cur), max(sta[top], cur), dist[sta[top]] + dist[cur] };
//        top--;
//    }
//    sta[++top] = cur;
//}
//
//void calc(int u) {
//    cnta = 0;
//    dfs(u, 0, 0);
//    sort(nodeArr + 1, nodeArr + cnta + 1);
//    top = 0;
//    for (int i = 1; i <= cnta; i++) {
//        stackAdd(nodeArr[i]);
//    }
//    top = 0;
//    for (int i = cnta; i >= 1; i--) {
//        stackAdd(nodeArr[i]);
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
//void up(int i) {
//    minTree[i] = min(minTree[i << 1], minTree[i << 1 | 1]);
//}
//
//void build(int l, int r, int i) {
//    if (l == r) {
//        minTree[i] = INF;
//    } else {
//        int mid = (l + r) >> 1;
//        build(l, mid, i << 1);
//        build(mid + 1, r, i << 1 | 1);
//        up(i);
//    }
//}
//
//void update(int jobi, long long jobv, int l, int r, int i) {
//    if (l == r) {
//        minTree[i] = min(minTree[i], jobv);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            update(jobi, jobv, l, mid, i << 1);
//        } else {
//            update(jobi, jobv, mid + 1, r, i << 1 | 1);
//        }
//        up(i);
//    }
//}
//
//long long query(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return minTree[i];
//    }
//    long long ans = INF;
//    int mid = (l + r) >> 1;
//    if (jobl <= mid) {
//        ans = min(ans, query(jobl, jobr, l, mid, i << 1));
//    }
//    if (jobr > mid) {
//        ans = min(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
//    }
//    return ans;
//}
//
//void compute() {
//    solve(getCentroid(1, 0));
//    sort(queryArr + 1, queryArr + m + 1, QueryCmp);
//    sort(pairArr + 1, pairArr + cntp + 1, PairCmp);
//    build(1, n, 1);
//    for (int i = 1, j = 1; i <= m; i++) {
//        for (; j <= cntp && pairArr[j].b <= queryArr[i].y; j++) {
//            update(pairArr[j].a, pairArr[j].dist, 1, n, 1);
//        }
//        if (queryArr[i].x == queryArr[i].y) {
//            ans[queryArr[i].id] = -1;
//        } else {
//            ans[queryArr[i].id] = query(queryArr[i].x, queryArr[i].y, 1, n, 1);
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