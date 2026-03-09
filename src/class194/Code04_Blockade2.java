package class194;

// 城市阻断，C++版
// 给定一张无向图，一共n个点、m条边，所有点保证连通
// 对每个点i，打印如下的答案
// 节点i相连的边去掉以后，无向图有多少点对(x, y)，x和y不连通
// 注意(x, y)和(y, x)是不同点对
// 删掉和i相连的边，但是节点i本身不删掉，要参与点对数量的计算
// 1 <= n <= 10^5
// 1 <= m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3469
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 100001;
//const int MAXM = 500001;
//int n, m, cntn;
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
//int siz[MAXN << 1];
//ll ans[MAXN << 1];
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
//void dpOnTree(int u, int fa) {
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa) {
//            dpOnTree(v, u);
//            ans[u] += 2LL * siz[u] * siz[v];
//            siz[u] += siz[v];
//        }
//    }
//    siz[u] += u <= n ? 1 : 0;
//    ans[u] += 2LL * (siz[u] - 1) * (n - siz[u]);
//    ans[u] += 2LL * (n - 1);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    cntn = n;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        addEdge1(u, v);
//        addEdge1(v, u);
//    }
//    tarjan(1);
//    dpOnTree(1, 0);
//    for (int i = 1; i <= n; i++) {
//        cout << ans[i] << "\n";
//    }
//    return 0;
//}