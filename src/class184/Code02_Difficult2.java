package class184;

// 树的难题，C++版
// 一共有n个节点，给定n-1条边，每条边给定颜色，所有节点组成一棵树
// 颜色一共有m种，val[i]表示第i种颜色的权值，可能为负数
// 树上的一条简单路径，依次经过的边收集其颜色，可以组成一个颜色序列
// 颜色序列划分成若干个连续同色段，比如AABAACC，有4个连续同色段
// 每个连续同色段只算一次颜色权值，颜色权值的累加和作为路径的权
// 请计算边数在[limitl, limitr]范围的所有路径中，最大的权是多少
// 1 <= n、m <= 2 * 10^5
// 题目保证一定存在边数在[limitl, limitr]的路径
// 测试链接 : https://www.luogu.com.cn/problem/P3714
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Edge {
//    int node, color;
//};
//
//bool EdgeCmp(Edge a, Edge b) {
//    return a.color < b.color;
//}
//
//const int MAXN = 200001;
//const long long INF = 1LL << 60;
//int n, m, limitl, limitr;
//int val[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int color[MAXN << 1];
//int cntg;
//
//long long preTree[MAXN << 2];
//long long curTree[MAXN << 2];
//
//bool vis[MAXN];
//int siz[MAXN];
//
//Edge edgeArr[MAXN];
//int cnte;
//
//int edgeCnt[MAXN];
//long long pathSum[MAXN];
//
//int subtreeNode[MAXN];
//int cnts;
//int colorNode[MAXN];
//int cntc;
//
//void addEdge(int u, int v, int c) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    color[cntg] = c;
//    head[u] = cntg;
//}
//
//void build(long long *tree, int l, int r, int i) {
//    tree[i] = -INF;
//    if (l < r) {
//        int mid = (l + r) >> 1;
//        build(tree, l, mid, i << 1);
//        build(tree, mid + 1, r, i << 1 | 1);
//    }
//}
//
//void clear(long long *tree, int l, int r, int i) {
//    if (tree[i] == -INF) {
//        return;
//    }
//    tree[i] = -INF;
//    if (l < r) {
//        int mid = (l + r) >> 1;
//        clear(tree, l, mid, i << 1);
//        clear(tree, mid + 1, r, i << 1 | 1);
//    }
//}
//
//void update(long long *tree, int jobi, long long jobv, int l, int r, int i) {
//    if (l == r) {
//        tree[i] = max(tree[i], jobv);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            update(tree, jobi, jobv, l, mid, i << 1);
//        } else {
//            update(tree, jobi, jobv, mid + 1, r, i << 1 | 1);
//        }
//        tree[i] = max(tree[i << 1], tree[i << 1 | 1]);
//    }
//}
//
//long long query(long long *tree, int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return tree[i];
//    }
//    int mid = (l + r) >> 1;
//    long long ans = -INF;
//    if (jobl <= mid) {
//        ans = max(ans, query(tree, jobl, jobr, l, mid, i << 1));
//    }
//    if (jobr > mid) {
//        ans = max(ans, query(tree, jobl, jobr, mid + 1, r, i << 1 | 1));
//    }
//    return ans;
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
//void dfs(int u, int fa, int preColor, int edge, long long sum) {
//    if (edge > limitr) {
//        return;
//    }
//    edgeCnt[u] = edge;
//    pathSum[u] = sum;
//    subtreeNode[++cnts] = u;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        int c = color[e];
//        if (v != fa && !vis[v]) {
//            dfs(v, u, c, edge + 1, sum + (preColor == c ? 0 : val[c]));
//        }
//    }
//}
//
//long long calc(int u) {
//    cnte = 0;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        int c = color[e];
//        if (!vis[v]) {
//            edgeArr[++cnte] = {v, c};
//        }
//    }
//    sort(edgeArr + 1, edgeArr + cnte + 1, EdgeCmp);
//    update(preTree, 0, 0, 0, n, 1);
//    long long ans = -INF;
//    cntc = 0;
//    for (int k = 1; k <= cnte; k++) {
//        int v = edgeArr[k].node;
//        int c = edgeArr[k].color;
//        if (k > 1 && edgeArr[k - 1].color != c) {
//            clear(curTree, 0, n, 1);
//            for (int i = 1; i <= cntc; i++) {
//                int node = colorNode[i];
//                update(preTree, edgeCnt[node], pathSum[node], 0, n, 1);
//            }
//            cntc = 0;
//        }
//        cnts = 0;
//        dfs(v, u, c, 1, val[c]);
//        for (int i = 1; i <= cnts; i++) {
//            int node = subtreeNode[i];
//            int l = max(0, limitl - edgeCnt[node]);
//            int r = limitr - edgeCnt[node];
//            ans = max(ans, query(preTree, l, r, 0, n, 1) + pathSum[node]);
//            ans = max(ans, query(curTree, l, r, 0, n, 1) + pathSum[node] - val[c]);
//        }
//        for (int i = 1; i <= cnts; i++) {
//            int node = subtreeNode[i];
//            colorNode[++cntc] = node;
//            update(curTree, edgeCnt[node], pathSum[node], 0, n, 1);
//        }
//    }
//    clear(preTree, 0, n, 1);
//    clear(curTree, 0, n, 1);
//    return ans;
//}
//
//long long solve(int u) {
//    vis[u] = true;
//    long long ans = calc(u);
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            ans = max(ans, solve(getCentroid(v, u)));
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> limitl >> limitr;
//    for (int i = 1; i <= m; i++) {
//        cin >> val[i];
//    }
//    for (int i = 1, u, v, c; i < n; i++) {
//        cin >> u >> v >> c;
//        addEdge(u, v, c);
//        addEdge(v, u, c);
//    }
//    build(preTree, 0, n, 1);
//    build(curTree, 0, n, 1);
//    cout << solve(getCentroid(1, 0)) << '\n';
//    return 0;
//}