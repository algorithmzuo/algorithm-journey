package class162;

// 哪个距离的点最多，C++版
// 一共有n个节点，给定n-1条边，所有节点连成一棵树，规定1号节点是头
// 规定任何点到自己的距离为0
// 定义d(u, x)，以u为头的子树中，到u的距离为x的节点数
// 对于每个点u，想知道哪个尽量小的x，能取得最大的d(u, x)值
// 打印每个点的答案
// 1 <= n <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/CF1009F
// 测试链接 : https://codeforces.com/problemset/problem/1009/F
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 1000001;
//int n;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg = 0;
//
//int len[MAXN];
//int son[MAXN];
//int dfn[MAXN];
//int cntd = 0;
//
//int dp[MAXN];
//int ansx[MAXN];
//
//void setVal(int u, int i, int v) {
//    dp[dfn[u] + i] = v;
//}
//
//int getVal(int u, int i) {
//    return dp[dfn[u] + i];
//}
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs1(int u, int fa) {
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa) {
//            dfs1(v, u);
//        }
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa) {
//            if (son[u] == 0 || len[son[u]] < len[v]) {
//                son[u] = v;
//            }
//        }
//    }
//    len[u] = len[son[u]] + 1;
//}
//
//void dfs2(int u, int fa) {
//    dfn[u] = ++cntd;
//    setVal(u, 0, 1);
//    if (son[u] == 0) {
//        ansx[u] = 0;
//        return;
//    }
//    dfs2(son[u], u);
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa && v != son[u]) {
//            dfs2(v, u);
//        }
//    }
//    ansx[u] = ansx[son[u]] + 1;
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa && v != son[u]) {
//            for (int i = 1; i <= len[v]; i++) {
//                setVal(u, i, getVal(u, i) + getVal(v, i - 1));
//                if (getVal(u, i) > getVal(u, ansx[u]) || (getVal(u, i) == getVal(u, ansx[u]) && i < ansx[u])) {
//                    ansx[u] = i;
//                }
//            }
//        }
//    }
//    if (getVal(u, ansx[u]) == 1) {
//        ansx[u] = 0;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs1(1, 0);
//    dfs2(1, 0);
//    for (int i = 1; i <= n; i++) {
//        cout << ansx[i] << "\n";
//    }
//    return 0;
//}