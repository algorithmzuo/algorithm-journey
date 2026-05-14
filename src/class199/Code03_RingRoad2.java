package class199;

// 城市环路，并查集找环，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P1453
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n;
//double k;
//int arr[MAXN];
//
//int fa[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int dp[MAXN][2];
//int root1, root2;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//int find(int i) {
//    if (i != fa[i]) {
//        fa[i] = find(fa[i]);
//    }
//    return fa[i];
//}
//
//void Union(int x, int y) {
//    int fx = find(x);
//    int fy = find(y);
//    if (fx != fy) {
//        fa[fx] = fy;
//    }
//}
//
//void dpOnTree(int u, int fa) {
//    dp[u][0] = 0;
//    dp[u][1] = arr[u];
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            dpOnTree(v, u);
//            dp[u][0] += max(dp[v][0], dp[v][1]);
//            dp[u][1] += dp[v][0];
//        }
//    }
//}
//
//int main() {
//    scanf("%d", &n);
//    for (int i = 1; i <= n; i++) {
//        scanf("%d", &arr[i]);
//    }
//    for (int i = 1; i <= n; i++) {
//        fa[i] = i;
//    }
//    for (int i = 1, u, v; i <= n; i++) {
//        scanf("%d%d", &u, &v);
//        u++;
//        v++;
//        if (find(u) == find(v)) {
//            root1 = u;
//            root2 = v;
//        } else {
//            Union(u, v);
//            addEdge(u, v);
//            addEdge(v, u);
//        }
//    }
//    scanf("%lf", &k);
//    dpOnTree(root1, 0);
//    int ans = dp[root1][0];
//    dpOnTree(root2, 0);
//    ans = max(ans, dp[root2][0]);
//    printf("%.1f\n", k * ans);
//    return 0;
//}