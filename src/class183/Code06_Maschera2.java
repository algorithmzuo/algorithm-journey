package class183;

// 所有合法路径的魔力和，C++版
// 一共有n个节点，给定n-1条边，每条边有边权，所有节点组成一棵树
// 给定两个整数l、r，对于点对(x, y)，考虑两点之间的简单路径
// 如果路径的边数在[l, r]范围内，则该路径视为合法
// 一条路径的魔力值 = 该路径上所有边权的最大值
// 计算所有合法路径的魔力值之和
// 本题规定(x, x)不是点对，(x, y)和(y, x)认为是不同的点对
// 1 <= n、边权 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5351
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int maxv, edge;
//};
//
//bool NodeCmp(Node a, Node b) {
//    return a.maxv < b.maxv;
//}
//
//const int MAXN = 100002;
//int n, l, r;
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
//Node arr[MAXN];
//int cnta;
//
//int tree[MAXN];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int v) {
//    i++;
//    while (i <= r + 1) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//int sum(int i) {
//    i++;
//    int ret = 0;
//    while (i > 0) {
//        ret += tree[i];
//        i -= lowbit(i);
//    }
//    return ret;
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
//void dfs(int u, int fa, int maxv, int edge) {
//    if (edge > r) {
//        return;
//    }
//    arr[++cnta] = { maxv, edge };
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            dfs(v, u, max(maxv, weight[e]), edge + 1);
//        }
//    }
//}
//
//long long calc(int u, int maxv, int edge) {
//    cnta = 0;
//    dfs(u, 0, maxv, edge);
//    sort(arr + 1, arr + cnta + 1, NodeCmp);
//    long long ans = 0;
//    for (int i = 1; i <= cnta; i++) {
//        ans += 1LL * arr[i].maxv * (sum(r - arr[i].edge) - sum(l - arr[i].edge - 1));
//        add(arr[i].edge, 1);
//    }
//    for (int i = 1; i <= cnta; i++) {
//        add(arr[i].edge, -1);
//    }
//    return ans;
//}
//
//long long solve(int u) {
//    vis[u] = true;
//    long long ans = calc(u, 0, 0);
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            ans -= calc(v, weight[e], 1);
//            ans += solve(getCentroid(v, u));
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> l >> r;
//    for (int i = 1, u, v, w; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdge(u, v, w);
//        addEdge(v, u, w);
//    }
//    long long ans = solve(getCentroid(1, 0));
//    cout << (ans << 1) << '\n';
//    return 0;
//}