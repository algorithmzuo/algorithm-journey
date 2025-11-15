package class183;

// 消息传递，C++版
// 一共有n个节点，给定n-1条边，所有节点组成一棵树
// 如果x号节点收到一个消息，那么消息会从x开始扩散，速度为每天越过一条边
// 接下来有m条查询，每条查询都是相互独立的，格式如下
// 查询 x k : 第0天的时候，x号节点得到一条信息，打印第k天时，新收到该消息的人数
// 1 <= n、m <= 10^5
// 0 <= k < n
// 测试链接 : https://www.luogu.com.cn/problem/P6626
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int tim, qid;
//};
//
//const int MAXN = 100001;
//int t, n, m;
//
//int headg[MAXN];
//int nextg[MAXN << 1];
//int tog[MAXN << 1];
//int cntg;
//
//int headq[MAXN];
//int nextq[MAXN];
//int tim[MAXN];
//int qid[MAXN];
//int cntq;
//
//bool vis[MAXN];
//int siz[MAXN];
//
//int depCnt[MAXN];
//int maxDep;
//
//Node arr[MAXN];
//int cnta;
//
//int ans[MAXN];
//
//void addEdge(int u, int v) {
//    nextg[++cntg] = headg[u];
//    tog[cntg] = v;
//    headg[u] = cntg;
//}
//
//void addQuery(int u, int t, int id) {
//    nextq[++cntq] = headq[u];
//    tim[cntq] = t;
//    qid[cntq] = id;
//    headq[u] = cntq;
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = headg[u]; e; e = nextg[e]) {
//        int v = tog[e];
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
//        for (int e = headg[u]; e; e = nextg[e]) {
//            int v = tog[e];
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
//void dfs(int u, int fa, int dep) {
//    depCnt[dep]++;
//    maxDep = max(maxDep, dep);
//    for (int e = headq[u]; e; e = nextq[e]) {
//        if (tim[e] + 1 >= dep) {
//            arr[++cnta] = { tim[e] - dep + 2, qid[e] };
//        }
//    }
//    for (int e = headg[u]; e; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa && !vis[v]) {
//            dfs(v, u, dep + 1);
//        }
//    }
//}
//
//void calc(int u) {
//    cnta = 0;
//    maxDep = 0;
//    dfs(u, 0, 1);
//    for (int i = 1; i <= cnta; i++) {
//        ans[arr[i].qid] += depCnt[arr[i].tim];
//    }
//    for (int d = 1; d <= maxDep; d++) {
//        depCnt[d] = 0;
//    }
//    for (int e = headg[u]; e; e = nextg[e]) {
//        int v = tog[e];
//        if (!vis[v]) {
//            cnta = 0;
//            maxDep = 0;
//            dfs(v, u, 2);
//            for (int i = 1; i <= cnta; i++) {
//                ans[arr[i].qid] -= depCnt[arr[i].tim];
//            }
//            for (int d = 1; d <= maxDep; d++) {
//                depCnt[d] = 0;
//            }
//        }
//    }
//}
//
//void solve(int u) {
//    vis[u] = true;
//    calc(u);
//    for (int e = headg[u]; e; e = nextg[e]) {
//        int v = tog[e];
//        if (!vis[v]) {
//            solve(getCentroid(v, u));
//        }
//    }
//}
//
//void prepare() {
//    cntg = 0;
//    cntq = 0;
//    for (int i = 1; i <= n; i++) {
//        headg[i] = 0;
//        headq[i] = 0;
//        vis[i] = false;
//    }
//    for (int i = 1; i <= m; i++) {
//        ans[i] = 0;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> t;
//    for (int c = 1; c <= t; c++) {
//        cin >> n >> m;
//        prepare();
//        for (int i = 1, u, v; i < n; i++) {
//            cin >> u >> v;
//            addEdge(u, v);
//            addEdge(v, u);
//        }
//        for (int i = 1, x, k; i <= m; i++) {
//            cin >> x >> k;
//            addQuery(x, k, i);
//        }
//        solve(getCentroid(1, 0));
//        for (int i = 1; i <= m; i++) {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}