package class185;

// 幻想乡战略游戏，C++版
// 树上有n个点，每个点的初始点权是0，给定n-1条边，每条边有边权
// 如果点x是指挥点，它指挥点y的花费 = x到y的简单路径权值和 * y的点权
// 树上存在某个最佳的指挥点，指挥所有点的总花费最小，叫做最小指挥总花费
// 一共m条操作，格式 x v : 先把x的点权增加v，然后打印此时的最小指挥总花费
// 注意参数v有可能是负数，但题目保证任何时候，点权不会出现负数
// 1 <= n、m <= 10^5
// 1 <= 边权 <= 1000
// -1000 <= v <= +1000
// 测试链接 : https://www.luogu.com.cn/problem/P3345
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int weight[MAXN << 1];
//int cent[MAXN << 1];
//int cntg;
//
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//int dist[MAXN];
//
//bool vis[MAXN];
//int centfa[MAXN];
//
//long long sum[MAXN];
//long long addCost[MAXN];
//long long minusCost[MAXN];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//void dfs1(int u, int f, int dis) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    dist[u] = dis;
//    siz[u] = 1;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (v != f) {
//            dfs1(v, u, dis + w);
//        }
//    }
//    for (int ei = head[u], v; ei; ei = nxt[ei]) {
//        v = to[ei];
//        if (v != f) {
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
//    if (son[u] == 0) {
//        return;
//    }
//    dfs2(son[u], t);
//    for (int e = head[u], v; e; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//int getLca(int a, int b) {
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
//int getDist(int x, int y) {
//    return dist[x] + dist[y] - (dist[getLca(x, y)] << 1);
//}
//
//void getSize(int u, int f) {
//    siz[u] = 1;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != f && !vis[v]) {
//            getSize(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//int getCentroid(int u, int f) {
//    getSize(u, f);
//    int half = siz[u] >> 1;
//    bool find = false;
//    while (!find) {
//        find = true;
//        for (int e = head[u]; e; e = nxt[e]) {
//            int v = to[e];
//            if (v != f && !vis[v] && siz[v] > half) {
//                f = u;
//                u = v;
//                find = false;
//                break;
//            }
//        }
//    }
//    return u;
//}
//
//void centroidTree(int u, int f) {
//    centfa[u] = f;
//    vis[u] = true;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (!vis[v]) {
//            int nextCent = getCentroid(v, u);
//            cent[e] = nextCent;
//            centroidTree(nextCent, u);
//        }
//    }
//}
//
//void add(int x, int v) {
//    sum[x] += v;
//    for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
//        int dist = getDist(x, fa);
//        sum[fa] += v;
//        addCost[fa] += 1LL * v * dist;
//        minusCost[cur] += 1LL * v * dist;
//    }
//}
//
//long long query(int x) {
//    long long ans = addCost[x];
//    for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
//        int dist = getDist(x, fa);
//        ans += addCost[fa];
//        ans -= minusCost[cur];
//        ans += (sum[fa] - sum[cur]) * dist;
//    }
//    return ans;
//}
//
//long long compute(int u) {
//    long long ans = query(u);
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (query(v) < ans) {
//            return compute(cent[e]);
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, u, v, w; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdge(u, v, w);
//        addEdge(v, u, w);
//    }
//    dfs1(1, 0, 0);
//    dfs2(1, 1);
//    int root = getCentroid(1, 0);
//    centroidTree(root, 0);
//    for (int i = 1, x, v; i <= m; i++) {
//        cin >> x >> v;
//        add(x, v);
//        cout << compute(root) << '\n';
//    }
//    return 0;
//}