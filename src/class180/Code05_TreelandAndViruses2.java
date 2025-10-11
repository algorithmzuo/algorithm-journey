package class180;

// 树上病毒传播，C++版
// 测试链接 : https://www.luogu.com.cn/problem/CF1320E
// 测试链接 : https://codeforces.com/problemset/problem/1320/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int id, dist, time, source, sourceOrder;
//    bool operator<(const Node &other) const {
//        if (time != other.time) {
//            return time > other.time;
//        }
//        return sourceOrder > other.sourceOrder;
//    }
//};
//
//const int MAXN = 200001;
//const int MAXP = 20;
//int n, q, k, m;
//
//int headg[MAXN];
//int nextg[MAXN << 1];
//int tog[MAXN << 1];
//int cntg;
//
//int headv[MAXN];
//int nextv[MAXN << 1];
//int tov[MAXN << 1];
//int cntv;
//
//int dep[MAXN];
//int dfn[MAXN];
//int stjump[MAXN][MAXP];
//int cntd;
//
//int start[MAXN];
//int speed[MAXN];
//int query[MAXN];
//int order[MAXN];
//
//int arr[MAXN << 1];
//int tmp[MAXN << 2];
//int len;
//
//priority_queue<Node> heap;
//bool vis[MAXN];
//int minTime[MAXN];
//int bestSource[MAXN];
//int ans[MAXN];
//
//bool cmp(int x, int y) {
//    return dfn[x] < dfn[y];
//}
//
//void addEdgeG(int u, int v) {
//    nextg[++cntg] = headg[u];
//    tog[cntg] = v;
//    headg[u] = cntg;
//}
//
//void addEdgeV(int u, int v) {
//    nextv[++cntv] = headv[u];
//    tov[cntv] = v;
//    headv[u] = cntv;
//}
//
//void dfs(int u, int fa) {
//    dep[u] = dep[fa] + 1;
//    dfn[u] = ++cntd;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXP; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = headg[u]; e; e = nextg[e]) {
//        if (tog[e] != fa) {
//            dfs(tog[e], u);
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
//int buildVirtualTree() {
//    int tot = 0;
//    for (int i = 1; i <= k; i++) {
//        arr[++tot] = start[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        arr[++tot] = query[i];
//    }
//    sort(arr + 1, arr + tot + 1, cmp);
//    len = 0;
//    for (int i = 1; i < tot; i++) {
//        tmp[++len] = arr[i];
//        tmp[++len] = getLca(arr[i], arr[i + 1]);
//    }
//    tmp[++len] = arr[tot];
//    sort(tmp + 1, tmp + len + 1, cmp);
//    int unique = 1;
//    for (int i = 2; i <= len; i++) {
//        if (tmp[unique] != tmp[i]) {
//            tmp[++unique] = tmp[i];
//        }
//    }
//    cntv = 0;
//    for (int i = 1; i <= unique; i++) {
//        headv[tmp[i]] = 0;
//    }
//    for (int i = 1; i < unique; i++) {
//        int lca = getLca(tmp[i], tmp[i + 1]);
//        addEdgeV(lca, tmp[i + 1]);
//        addEdgeV(tmp[i + 1], lca);
//    }
//    len = unique;
//    return tmp[1];
//}
//
//void dijkstra() {
//    for (int i = 1; i <= len; i++) {
//        int u = tmp[i];
//        minTime[u] = n + 1;
//        bestSource[u] = n + 1;
//        vis[u] = false;
//    }
//    for (int i = 1; i <= k; i++) {
//        int s = start[i];
//        minTime[s] = 0;
//        bestSource[s] = s;
//        heap.push(Node{s, 0, 0, s, order[s]});
//    }
//    while (!heap.empty()) {
//        Node cur = heap.top();
//        heap.pop();
//        int u = cur.id;
//        if (!vis[u]) {
//            vis[u] = true;
//            for (int e = headv[u]; e; e = nextv[e]) {
//                int v = tov[e];
//                int dist = cur.dist + abs(dep[u] - dep[v]);
//                int time = (dist + speed[cur.source] - 1) / speed[cur.source];
//                if (!vis[v] && (time < minTime[v] || (time == minTime[v] && cur.sourceOrder < order[bestSource[v]]))) {
//                    minTime[v] = time;
//                    bestSource[v] = cur.source;
//                    heap.push(Node{v, dist, time, cur.source, cur.sourceOrder});
//                }
//            }
//        }
//    }
//}
//
//void compute() {
//    for (int i = 1; i <= k; i++) {
//        order[start[i]] = i;
//    }
//    buildVirtualTree();
//    dijkstra();
//    for (int i = 1; i <= m; i++) {
//        ans[i] = order[bestSource[query[i]]];
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdgeG(u, v);
//        addEdgeG(v, u);
//    }
//    dfs(1, 0);
//    cin >> q;
//    for (int t = 1; t <= q; t++) {
//        cin >> k >> m;
//        for (int i = 1, s, v; i <= k; i++) {
//            cin >> s >> v;
//            start[i] = s;
//            speed[s] = v;
//        }
//        for (int i = 1; i <= m; i++) {
//            cin >> query[i];
//        }
//        compute();
//        for (int i = 1; i <= m; i++) {
//            cout << ans[i] << ' ';
//        }
//        cout << '\n';
//    }
//    return 0;
//}