package class173;

// 王室联邦，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P2325
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 1001;
//int n, b;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int root[MAXN];
//int belong[MAXN];
//int sta[MAXN];
//int siz;
//int cntb;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs(int u, int f) {
//    int tmp = siz;
//    for (int e = head[u], v; e; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            dfs(v, u);
//            if (siz - tmp >= b) {
//                root[++cntb] = u;
//                while (siz > tmp) {
//                    belong[sta[siz--]] = cntb;
//                }
//            }
//        }
//    }
//    sta[++siz] = u;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> b;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs(1, 0);
//    if (cntb == 0) {
//        root[++cntb] = 1;
//    }
//    while (siz > 0) {
//        belong[sta[siz--]] = cntb;
//    }
//    cout << cntb << '\n';
//    for (int i = 1; i <= n; i++) {
//        cout << belong[i] << ' ';
//    }
//    cout << '\n';
//    for (int i = 1; i <= cntb; i++) {
//        cout << root[i] << ' ';
//    }
//    cout << '\n';
//    return 0;
//}