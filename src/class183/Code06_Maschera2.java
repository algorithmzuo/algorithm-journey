package class183;

// 所有合法路径的魔力和，C++版
// 一共有n个节点，给定n-1条边，每条边有边权，所有节点组成一棵树
// 给定两个整数l、r，对于任意两个不同节点u、v，考虑它们之间的简单路径
// 如果路径上边的数量在[l, r]范围内，则这条路径是合法路径
// 路径的魔力值 = 路径上所有边权的最大值，打印所有合法路径的魔力和
// 注意，u到v和v到u视为两条不同的路径，均要计入答案
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
//const int MAXN = 100001;
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
//Node cur[MAXN];
//int cntc;
//Node all[MAXN];
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
//    while (i <= r) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//int sum(int i) {
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
//    cur[++cntc] = { maxv, edge };
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa && !vis[v]) {
//            dfs(v, u, max(maxv, weight[e]), edge + 1);
//        }
//    }
//}
//
//long long calc(int u) {
//    long long ans = 0;
//    cnta = 0;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            cntc = 0;
//            dfs(v, u, weight[e], 1);
//            sort(cur + 1, cur + cntc + 1, NodeCmp);
//            for (int i = 1; i <= cntc; i++) {
//                ans -= 1LL * cur[i].maxv * (sum(r - cur[i].edge) - sum(l - cur[i].edge - 1));
//                add(cur[i].edge, 1);
//            }
//            for (int i = 1; i <= cntc; i++) {
//                add(cur[i].edge, -1);
//            }
//            for (int i = 1; i <= cntc; i++) {
//                all[++cnta] = cur[i];
//            }
//        }
//    }
//    sort(all + 1, all + cnta + 1, NodeCmp);
//    for (int i = 1; i <= cnta; i++) {
//        ans += 1LL * all[i].maxv * (sum(r - all[i].edge) - sum(l - all[i].edge - 1));
//        add(all[i].edge, 1);
//    }
//    for (int i = 1; i <= cnta; i++) {
//        add(all[i].edge, -1);
//    }
//    for (int i = 1; i <= cnta; i++) {
//        if (all[i].edge >= l) {
//            ans += all[i].maxv;
//        }
//    }
//    return ans;
//}
//
//long long solve(int u) {
//    vis[u] = true;
//    long long ans = calc(u);
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
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