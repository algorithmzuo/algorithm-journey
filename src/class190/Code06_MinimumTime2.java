package class190;

// 最少传输时间，C++版
// 给定一张n个点，m条边的有向图，每条边有非负边权
// 如果两点属同一个强连通分量，认为无花费，否则花费为边权
// 打印从1号点到n号点的最小花费，如果不能到达打印-1
// 1 <= n <= 2 * 10^5
// 1 <= m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P2169
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int x, cost;
//    bool operator<(const Node &other) const {
//        return cost > other.cost;
//    }
//};
//
//const int MAXN = 200001;
//const int MAXM = 1000001;
//const int INF = 1000000001;
//int n, m;
//
//int a[MAXM];
//int b[MAXM];
//int val[MAXM];
//
//int head[MAXN];
//int nxt[MAXM];
//int to[MAXM];
//int weight[MAXM];
//int cntg;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//
//int sta[MAXN];
//int top;
//
//int belong[MAXN];
//int sccCnt;
//
//int dist[MAXN];
//bool vis[MAXN];
//priority_queue<Node> heap;
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//void tarjan(int u) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (dfn[v] == 0) {
//            tarjan(v);
//            low[u] = min(low[u], low[v]);
//        } else {
//            if (belong[v] == 0) {
//                low[u] = min(low[u], dfn[v]);
//            }
//        }
//    }
//    if (dfn[u] == low[u]) {
//        sccCnt++;
//        int pop;
//        do {
//            pop = sta[top--];
//            belong[pop] = sccCnt;
//        } while (pop != u);
//    }
//}
//
//void condense() {
//    cntg = 0;
//    for (int i = 1; i <= sccCnt; i++) {
//        head[i] = 0;
//    }
//    for (int i = 1; i <= m; i++) {
//        int scc1 = belong[a[i]];
//        int scc2 = belong[b[i]];
//        if (scc1 > 0 && scc2 > 0 && scc1 != scc2) {
//            addEdge(scc1, scc2, val[i]);
//        }
//    }
//}
//
//int dijkstra() {
//    for (int i = 1; i <= sccCnt; i++) {
//        dist[i] = INF;
//        vis[i] = false;
//    }
//    dist[belong[1]] = 0;
//    heap.push(Node{belong[1], 0});
//    while (!heap.empty()) {
//        Node cur = heap.top();
//        heap.pop();
//        int u = cur.x;
//        int d = cur.cost;
//        if (!vis[u]) {
//            vis[u] = true;
//            for (int e = head[u]; e > 0; e = nxt[e]) {
//                int v = to[e];
//                int w = weight[e];
//                if (!vis[v] && dist[v] > d + w) {
//                    dist[v] = d + w;
//                    heap.push(Node{v, dist[v]});
//                }
//            }
//        }
//    }
//    return dist[belong[n]];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> a[i] >> b[i] >> val[i];
//        addEdge(a[i], b[i], val[i]);
//    }
//    tarjan(1);
//    if (belong[n] == 0) {
//        cout << -1 << "\n";
//    } else {
//        condense();
//        int ans = dijkstra();
//        cout << ans << "\n";
//    }
//    return 0;
//}