package class186;

// 最长道路tree，C++版
// 一共n个节点，每个点给定点权，给定n-1条边，所有节点组成一棵树
// 树上任何一条链的指标 = 链的节点数 * 链上节点的最小值
// 打印这个指标的最大值
// 1 <= n <= 5 * 10^4
// 测试链接 : https://hydro.ac/p/bzoj-P2870
// 测试链接 : https://darkbzoj.cc/problem/2870
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//struct Node {
//    int edge, minv;
//};
//
//bool NodeCmp(Node x, Node y) {
//    return x.minv < y.minv;
//}
//
//const int MAXN = 100001;
//int n, cntn;
//int arr[MAXN];
//
//int head1[MAXN];
//int next1[MAXN << 1];
//int to1[MAXN << 1];
//int weight1[MAXN << 1];
//int cnt1;
//
//int head2[MAXN];
//int next2[MAXN << 1];
//int to2[MAXN << 1];
//int weight2[MAXN << 1];
//int cnt2;
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
//void addEdge1(int u, int v, int w) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    weight1[cnt1] = w;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v, int w) {
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    weight2[cnt2] = w;
//    head2[u] = cnt2;
//}
//
//void rebuild(int u, int fa) {
//    int last = 0;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        int w = weight1[e];
//        if (v != fa) {
//            if (last == 0) {
//                last = u;
//                addEdge2(u, v, w);
//                addEdge2(v, u, w);
//            } else {
//                int add = ++cntn;
//                arr[add] = arr[u];
//                addEdge2(last, add, 0);
//                addEdge2(add, last, 0);
//                addEdge2(add, v, w);
//                addEdge2(v, add, w);
//                last = add;
//            }
//            rebuild(v, u);
//        }
//    }
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
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
//        for (int e = head2[u]; e > 0; e = next2[e]) {
//            int v = to2[e];
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
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa && !vis[e >> 1]) {
//            dfs(v, u, edge + weight2[e], min(minv, arr[v]), op);
//        }
//    }
//}
//
//ll calc(int edge) {
//    cntl = cntr = 0;
//    int v1 = to2[edge];
//    int v2 = to2[edge ^ 1];
//    dfs(v1, 0, 0, arr[v1], 0);
//    dfs(v2, 0, 0, arr[v2], 1);
//    sort(larr + 1, larr + cntl + 1, NodeCmp);
//    sort(rarr + 1, rarr + cntr + 1, NodeCmp);
//    ll ans = 0;
//    ll maxEdge = 0;
//    for (int i = cntr, j = cntl; i >= 1; i--) {
//        while (j >= 1 && larr[j].minv >= rarr[i].minv) {
//            maxEdge = max(maxEdge, 1LL * larr[j].edge);
//            j--;
//        }
//        if (j < cntl) {
//            ans = max(ans, 1LL * rarr[i].minv * (maxEdge + rarr[i].edge + weight2[edge] + 1));
//        }
//    }
//    maxEdge = 0;
//    for (int i = cntl, j = cntr; i >= 1; i--) {
//        while (j >= 1 && rarr[j].minv >= larr[i].minv) {
//            maxEdge = max(maxEdge, 1LL * rarr[j].edge);
//            j--;
//        }
//        if (j < cntr) {
//            ans = max(ans, 1LL * larr[i].minv * (maxEdge + larr[i].edge + weight2[edge] + 1));
//        }
//    }
//    return ans;
//}
//
//ll solve(int u) {
//    ll ans = 0;
//    int edge = getCentroidEdge(u, 0);
//    if (edge > 0) {
//        vis[edge >> 1] = true;
//        ans = calc(edge);
//        ans = max(ans, solve(to2[edge]));
//        ans = max(ans, solve(to2[edge ^ 1]));
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
//        addEdge1(u, v, 1);
//        addEdge1(v, u, 1);
//    }
//    cntn = n;
//    cnt2 = 1;
//    rebuild(1, 0);
//    ll ans = solve(1);
//    cout << ans << '\n';
//    return 0;
//}