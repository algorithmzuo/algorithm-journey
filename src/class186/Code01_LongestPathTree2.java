package class186;

// 最长道路tree，C++版
// 测试链接 : https://hydro.ac/p/bzoj-P2870
// 测试链接 : https://darkbzoj.cc/problem/2870
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int edge, minv;
//};
//
//bool NodeCmp(Node x, Node y) {
//    return x.minv < y.minv;
//}
//
//const int MAXN = 200001;
//const int MAXS = 1000001;
//int n;
//int arr[MAXN];
//
//int headg[MAXN];
//int nextg[MAXN << 1];
//int tog[MAXN << 1];
//int weightg[MAXN << 1];
//int cntg;
//
//int sonCnt[MAXN];
//int heads[MAXN];
//int nexts[MAXS];
//int sons[MAXS];
//int cnts;
//
//bool vis[MAXN];
//int siz[MAXN];
//
//Node larr[MAXN];
//int cntl;
//
//Node rarr[MAXN];
//int cntr;
//
//void addEdge(int u, int v, int w) {
//    nextg[++cntg] = headg[u];
//    tog[cntg] = v;
//    weightg[cntg] = w;
//    headg[u] = cntg;
//}
//
//void addSon(int u, int v) {
//    sonCnt[u]++;
//    nexts[++cnts] = heads[u];
//    sons[cnts] = v;
//    heads[u] = cnts;
//}
//
//void buildSon(int u, int fa) {
//    for (int e = headg[u]; e; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa) {
//            addSon(u, v);
//            buildSon(v, u);
//        }
//    }
//}
//
//void rebuildTree() {
//    buildSon(1, 0);
//    cntg = 1;
//    for (int u = 1; u <= n; u++) {
//        headg[u] = 0;
//    }
//    for (int u = 1; u <= n; u++) {
//        if (sonCnt[u] <= 2) {
//            for (int e = heads[u]; e; e = nexts[e]) {
//                int son = sons[e];
//                addEdge(u, son, 1);
//                addEdge(son, u, 1);
//            }
//        } else {
//            int node1 = ++n;
//            int node2 = ++n;
//            arr[node1] = arr[u];
//            arr[node2] = arr[u];
//            addEdge(u, node1, 0);
//            addEdge(node1, u, 0);
//            addEdge(u, node2, 0);
//            addEdge(node2, u, 0);
//            bool add1 = true;
//            for (int e = heads[u]; e; e = nexts[e]) {
//                int son = sons[e];
//                if (add1) {
//                    addSon(node1, son);
//                } else {
//                    addSon(node2, son);
//                }
//                add1 = !add1;
//            }
//        }
//    }
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = headg[u]; e; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa && !vis[e >> 1]) {
//            getSize(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//int getCentroidEdge(int u, int fa) {
//    getSize(u, fa);
//    int total = siz[u];
//    int edge = 0;
//    int best = total;
//    while (u > 0) {
//        int nextu = 0, nextfa = 0;
//        for (int e = headg[u]; e; e = nextg[e]) {
//            int v = tog[e];
//            if (v != fa && !vis[e >> 1]) {
//                int cur = max(total - siz[v], siz[v]);
//                if (cur < best) {
//                    edge = e;
//                    best = cur;
//                    nextfa = u;
//                    nextu = v;
//                }
//            }
//        }
//        fa = nextfa;
//        u = nextu;
//    }
//    return edge;
//}
//
//void dfs(int u, int fa, int edge, int minv, int op) {
//    if (op == 0) {
//        larr[++cntl] = { edge, minv };
//    } else {
//        rarr[++cntr] = { edge, minv };
//    }
//    for (int e = headg[u]; e; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa && !vis[e >> 1]) {
//            dfs(v, u, edge + weightg[e], min(minv, arr[v]), op);
//        }
//    }
//}
//
//long long calc(int edge) {
//    cntl = cntr = 0;
//    int v = tog[edge];
//    dfs(v, 0, 0, arr[v], 0);
//    v = tog[edge ^ 1];
//    dfs(v, 0, 0, arr[v], 1);
//    sort(larr + 1, larr + cntl + 1, NodeCmp);
//    sort(rarr + 1, rarr + cntr + 1, NodeCmp);
//    long long ans = 0;
//    long long maxEdge = 0;
//    for (int i = cntr, j = cntl; i >= 1; i--) {
//        while (j >= 1 && larr[j].minv >= rarr[i].minv) {
//            maxEdge = max(maxEdge, 1LL * larr[j].edge);
//            j--;
//        }
//        if (j < cntl) {
//            ans = max(ans, 1LL * rarr[i].minv * (maxEdge + rarr[i].edge + weightg[edge] + 1));
//        }
//    }
//    maxEdge = 0;
//    for (int i = cntl, j = cntr; i >= 1; i--) {
//        while (j >= 1 && rarr[j].minv >= larr[i].minv) {
//            maxEdge = max(maxEdge, 1LL * rarr[j].edge);
//            j--;
//        }
//        if (j < cntr) {
//            ans = max(ans, 1LL * larr[i].minv * (maxEdge + larr[i].edge + weightg[edge] + 1));
//        }
//    }
//    return ans;
//}
//
//long long solve(int u) {
//    long long ans = 0;
//    int edge = getCentroidEdge(u, 0);
//    if (edge > 0) {
//        vis[edge >> 1] = true;
//        ans = calc(edge);
//        ans = max(ans, solve(tog[edge]));
//        ans = max(ans, solve(tog[edge ^ 1]));
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v, 1);
//        addEdge(v, u, 1);
//    }
//    rebuildTree();
//    long long ans = solve(1);
//    cout << ans << '\n';
//    return 0;
//}