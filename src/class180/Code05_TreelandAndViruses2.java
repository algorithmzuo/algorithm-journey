package class180;

// 树上病毒传播，C++版
// 一共有n个城市，有n-1条无向边，所有城市组成一棵树，一共有q条查询，每条查询数据如下
// 首先给定k种病毒，每种病毒有初次感染的城市start[i]，还有传播速度speed[i]
// 然后给定m个关键城市，打印每个城市被第几号病毒感染，病毒传播的规则如下
// 病毒的传播按轮次进行，每一轮病毒1先传播，然后是病毒2 .. 直到病毒k，下一轮又从病毒1开始
// 如果第i种病毒已经感染了城市x，当自己传播时，想要感染城市y的条件如下
// 城市x到城市y的路径包含的边数<=speed[i]，城市x到城市y的路径上，除了x所有城市都未被感染
// 一旦城市被某种病毒感染就永久保持，不会再被其他病毒感染，传播一直持续，直到所有城市都被感染
// 1 <= n、q、所有查询病毒总数、所有查询关键城市总数 <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1320E
// 测试链接 : https://codeforces.com/problemset/problem/1320/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int id, dist, time, source, virus;
//    bool operator<(const Node &other) const {
//        if (time != other.time) {
//            return time > other.time;
//        }
//        return virus > other.virus;
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
//int virusId[MAXN];
//
//int arr[MAXN << 1];
//int tmp[MAXN << 2];
//int len;
//
//priority_queue<Node> heap;
//bool vis[MAXN];
//int minTime[MAXN];
//int sourceCity[MAXN];
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
//        sourceCity[u] = n + 1;
//        vis[u] = false;
//    }
//    for (int i = 1; i <= k; i++) {
//        int s = start[i];
//        minTime[s] = 0;
//        sourceCity[s] = s;
//        heap.push(Node{s, 0, 0, s, virusId[s]});
//    }
//    while (!heap.empty()) {
//        Node cur = heap.top();
//        heap.pop();
//        int u = cur.id;
//        int udist = cur.dist;
//        int usource = cur.source;
//        int uvirus = cur.virus;
//        if (!vis[u]) {
//            vis[u] = true;
//            for (int e = headv[u]; e; e = nextv[e]) {
//                int v = tov[e];
//                if (!vis[v]) {
//                    int vdist = udist + abs(dep[u] - dep[v]);
//                    int vtime = (vdist + speed[usource] - 1) / speed[usource];
//                    if (vtime < minTime[v] || (vtime == minTime[v] && uvirus < virusId[sourceCity[v]])) {
//                        minTime[v] = vtime;
//                        sourceCity[v] = usource;
//                        heap.push(Node{v, vdist, vtime, usource, uvirus});
//                    }
//                }
//            }
//        }
//    }
//}
//
//void compute() {
//    for (int i = 1; i <= k; i++) {
//        virusId[start[i]] = i;
//    }
//    buildVirtualTree();
//    dijkstra();
//    for (int i = 1; i <= m; i++) {
//        ans[i] = virusId[sourceCity[query[i]]];
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