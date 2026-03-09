package class194;

// 铁人两项，C++版
// 给定一张无向图，一共n个点、m条边，图中可能存在多个连通区
// 有效的点对三元组(a, b, c)，首先要求a、b、c是不同的点
// 其次要求存在一条从a出发，经过b，最终到达c的路径，沿途无重复节点
// 打印有效的点对三元组的数量
// 1 <= n <= 10^5
// 1 <= m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4630
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 100001;
//const int MAXM = 200001;
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
//int val[MAXN << 1];
//int siz[MAXN << 1];
//int nodeCnt;
//ll ans;
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
//    nodeCnt++;
//    val[u] = -1;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        if (dfn[v] == 0) {
//            tarjan(v);
//            low[u] = min(low[u], low[v]);
//            if (low[v] >= dfn[u]) {
//                cntn++;
//                addEdge2(cntn, u);
//                addEdge2(u, cntn);
//                val[cntn]++;
//                int pop;
//                do {
//                    pop = sta[top--];
//                    addEdge2(cntn, pop);
//                    addEdge2(pop, cntn);
//                    val[cntn]++;
//                } while (pop != v);
//            }
//        } else {
//            low[u] = min(low[u], dfn[v]);
//        }
//    }
//}
//
//void dpOnTree(int u, int fa) {
//    siz[u] = u <= n ? 1 : 0;
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa) {
//            dpOnTree(v, u);
//            ans += 2LL * siz[u] * siz[v] * (ll)val[u];
//            siz[u] += siz[v];
//        }
//    }
//    ans += 2LL * siz[u] * (nodeCnt - siz[u]) * (ll)val[u];
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
//    for (int i = 1; i <= n; i++) {
//        if (dfn[i] == 0) {
//            nodeCnt = 0;
//            tarjan(i);
//            dpOnTree(i, 0);
//        }
//    }
//    cout << ans << "\n";
//    return 0;
//}