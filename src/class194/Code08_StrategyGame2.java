package class194;

// 战略游戏，C++版
// 给定一张无向图，一共n个点、m条边，所有点保证连通，图中可能有重边
// 一共有q条查询，每条查询格式 s K1 K2 .. Ks，含义如下
// 当前查询涉及s个点，给出s个点的编号，你只能删掉一个另外的点
// 删除该点后，只要这s个点中存在任意两点不连通，就算成功
// 对每条查询，打印你能选择的点的个数
// 2 <= n <= 10^5    1 <= m <= 2 * 10^5    1 <= q <= 10^5
// 所有查询中s的总和 <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4606
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXM = 200001;
//const int MAXP = 20;
//int t, n, m, q, s, cntn;
//
//int head1[MAXN];
//int next1[MAXM << 1];
//int to1[MAXM << 1];
//int cnt1;
//
//int head2[MAXN << 1];
//int next2[MAXM << 2];
//int to2[MAXM << 2];
//int cnt2;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//
//int sta[MAXN];
//int top;
//
//int nid[MAXN << 1];
//int dist[MAXN << 1];
//int dep[MAXN << 1];
//int stjump[MAXN << 1][MAXP];
//int cnti;
//
//int arr[MAXN];
//
//void addEdge1(int u, int v) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v) {
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    head2[u] = cnt2;
//}
//
//void tarjan(int u) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        if (dfn[v] == 0) {
//            tarjan(v);
//            low[u] = min(low[u], low[v]);
//            if (low[v] >= dfn[u]) {
//                cntn++;
//                addEdge2(cntn, u);
//                addEdge2(u, cntn);
//                int pop;
//                do {
//                    pop = sta[top--];
//                    addEdge2(cntn, pop);
//                    addEdge2(pop, cntn);
//                } while (pop != v);
//            }
//        } else {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//}
//
//void dfs(int u, int fa) {
//    nid[u] = ++cnti;
//    dist[u] = dist[fa] + (u <= n ? 1 : 0);
//    dep[u] = dep[fa] + 1;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXP; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa) {
//            dfs(v, u);
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
//int getDist(int x, int y) {
//    return dist[x] + dist[y] - 2 * dist[getLca(x, y)];
//}
//
//int compute() {
//    sort(arr + 1, arr + s + 1, [](int x, int y) {return nid[x] < nid[y];});
//    int ans = 0;
//    for (int i = 1; i < s; i++) {
//        ans += getDist(arr[i], arr[i + 1]);
//    }
//    ans += getDist(arr[1], arr[s]);
//    ans /= 2;
//    ans += getLca(arr[1], arr[s]) <= n ? 1 : 0;
//    ans -= s;
//    return ans;
//}
//
//void prepare() {
//    cnt1 = cnt2 = cntd = top = cnti = 0;
//    for (int i = 1; i <= n; i++) {
//        head1[i] = head2[i] = dfn[i] = 0;
//        head2[i + n] = 0;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> t;
//    for (int c = 1; c <= t; c++) {
//        cin >> n >> m;
//        cntn = n;
//        prepare();
//        for (int i = 1, u, v; i <= m; i++) {
//            cin >> u >> v;
//            addEdge1(u, v);
//            addEdge1(v, u);
//        }
//        tarjan(1);
//        dfs(1, 0);
//        cin >> q;
//        for (int i = 1; i <= q; i++) {
//            cin >> s;
//            for (int j = 1; j <= s; j++) {
//                cin >> arr[j];
//            }
//            cout << compute() << "\n";
//        }
//    }
//    return 0;
//}