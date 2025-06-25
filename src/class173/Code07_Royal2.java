package class173;

// 王室联邦，C++版
// 一共有n个城市，编号1~n，给定n-1条边，所有城市连成一棵树
// 给定数值b，希望你把树划分成若干个联通区，也叫省，划分要求如下
// 每个省至少要有b个城市，最多有3 * b个城市，每个省必须有一个省会
// 省会可在省内也可在省外，一个城市可以是多个省的省会
// 一个省里，任何城市到达省会的路径上，除了省会之外，其他的城市必须都在省内
// 根据要求完成一种有效划分即可，先打印你划分了多少个省，假设数量为k
// 然后打印n个数字，范围[1, k]，表示每个城市被划分给了哪个省
// 最后打印k个数字，表示每个省会的城市编号
// 1 <= n、b <= 10^3
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