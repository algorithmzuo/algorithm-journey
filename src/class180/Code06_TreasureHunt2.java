package class180;

// 寻宝游戏，C++版
// 一共有n个节点，节点有两种类型，刷宝的点 和 不刷宝的点
// 一共有n-1条无向边，每条边有边权，所有节点组成一棵树
// 开始时所有节点都是不刷宝的点，接下来有m条操作，格式如下
// 操作 x : x号点的类型翻转，刷宝的点 变成 不刷宝的点，不刷宝的点 变成 刷宝的点
// 一次操作后，每个刷宝的点都会产生宝物，你可以瞬移到任何点作为出发点，瞬移是无代价的
// 你需要走路拿到所有的宝物，最后回到出发点，打印最小的行走总路程，一共有m条打印
// 1 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3320
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXP = 20;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int weight[MAXN << 1];
//int cntg;
//
//int dep[MAXN];
//long long dist[MAXN];
//int dfn[MAXN];
//int seg[MAXN];
//int stjump[MAXN][MAXP];
//int cntd;
//
//int arr[MAXN];
//bool treasure[MAXN];
//std::set<int> st;
//std::set<int>::iterator it;
//
//long long ans[MAXN];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//void dfs(int u, int fa, int w) {
//    dep[u] = dep[fa] + 1;
//    dfn[u] = ++cntd;
//    seg[cntd] = u;
//    dist[u] = dist[fa] + w;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXP; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            dfs(v, u, weight[e]);
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
//long long getDist(int x, int y) {
//    return dist[x] + dist[y] - 2LL * dist[getLca(x, y)];
//}
//
//void compute() {
//    long long curAns = 0;
//    for (int i = 1; i <= m; i++) {
//        int nodeId = arr[i];
//        int dfnId = dfn[nodeId];
//        if (!treasure[nodeId]) {
//            treasure[nodeId] = true;
//            st.insert(dfnId);
//        } else {
//            treasure[nodeId] = false;
//            st.erase(dfnId);
//        }
//        if (st.size() <= 1) {
//            curAns = 0;
//        } else {
//            int low = seg[(it = st.lower_bound(dfnId)) == st.begin() ? *--st.end() : *--it];
//            int high = seg[(it = st.upper_bound(dfnId)) == st.end() ? *st.begin() : *it];
//            long long delta = getDist(nodeId, low) + getDist(nodeId, high) - getDist(low, high);
//            if (treasure[nodeId]) {
//                curAns += delta;
//            } else {
//                curAns -= delta;
//            }
//        }
//        ans[i] = curAns;
//    }
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
//    dfs(1, 0, 0);
//    for (int i = 1; i <= m; i++) {
//        cin >> arr[i];
//    }
//    compute();
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}