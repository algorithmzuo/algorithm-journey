package class186;

// 欧拉序求LCA，C++版
// 一共有n个节点，给定n-1条边，所有节点组成一棵树，给定树头root
// 一共有m条查询，格式 u v : 打印u和v的最低公共祖先
// 1 <= n、m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3379
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 500001;
//const int MAXP = 20;
//int n, m, root;
//
//int headg[MAXN];
//int nextg[MAXN << 1];
//int tog[MAXN << 1];
//int cntg;
//
//int first[MAXN];
//int lg2[MAXN << 1];
//int rmq[MAXN << 1][MAXP];
//int cntEuler;
//
//void addEdge(int u, int v) {
//    nextg[++cntg] = headg[u];
//    tog[cntg] = v;
//    headg[u] = cntg;
//}
//
//int getUp(int x, int y) {
//    return first[x] < first[y] ? x : y;
//}
//
//void dfs(int u, int fa) {
//    first[u] = ++cntEuler;
//    rmq[first[u]][0] = u;
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa) {
//            dfs(v, u);
//            rmq[++cntEuler][0] = u;
//        }
//    }
//}
//
//void buildRmq() {
//    dfs(root, 0);
//    for (int i = 2; i <= cntEuler; i++) {
//        lg2[i] = lg2[i >> 1] + 1;
//    }
//    for (int pre = 0, cur = 1; cur <= lg2[cntEuler]; pre++, cur++) {
//        for (int i = 1; i + (1 << cur) - 1 <= cntEuler; i++) {
//            rmq[i][cur] = getUp(rmq[i][pre], rmq[i + (1 << pre)][pre]);
//        }
//    }
//}
//
//int getLCA(int x, int y) {
//    x = first[x];
//    y = first[y];
//    if (x > y) {
//        swap(x, y);
//    }
//    int k = lg2[y - x + 1];
//    return getUp(rmq[x][k], rmq[y - (1 << k) + 1][k]);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> root;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    buildRmq();
//    for (int i = 1, u, v; i <= m; i++) {
//        cin >> u >> v;
//        cout << getLCA(u, v) << '\n';
//    }
//    return 0;
//}