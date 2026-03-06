package class194;

// 路径问题，C++版
// 给定一张无向图，一共n个点、m条边，所有点保证连通，无重边和自环
// 给定三个不同的点a、b、c，打印是否存在一条路径
// 从a到达b，然后从b到达c，要求途中不能出现重复的点
// 1 <= n、m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/AT_abc318_g
// 测试链接 : https://atcoder.jp/contests/abc318/tasks/abc318_g
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//const int MAXM = 200001;
//int n, m, a, b, c, cntn;
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
//int sta[MAXN];
//int top;
//
//int dep[MAXN << 1];
//int fa[MAXN << 1];
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
//void dfs(int u, int f) {
//    dep[u] = dep[f] + 1;
//    fa[u] = f;
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != f) {
//            dfs(v, u);
//        }
//    }
//}
//
//bool check() {
//    while (a != c) {
//        if (dep[a] < dep[c]) {
//            swap(a, c);
//        }
//        a = fa[a];
//        if (a > n) {
//            for (int e = head2[a]; e > 0; e = next2[e]) {
//                int v = to2[e];
//                if (v == b) {
//                    return true;
//                }
//            }
//        }
//    }
//    return false;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> a >> b >> c;
//    cntn = n;
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        addEdge1(u, v);
//        addEdge1(v, u);
//    }
//    tarjan(1);
//    dfs(1, 0);
//    cout << (check() ? "Yes" : "No") << "\n";
//    return 0;
//}