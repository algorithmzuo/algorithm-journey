package class162;

// 攻略，C++版
// 一共有n个节点，给定n-1条边，所有节点连成一棵树，每个点给定点权
// 规定1号点是头，任何路径都必须从头开始，然后走到某个叶节点停止
// 路径上的点，点权的累加和，叫做这个路径的收益
// 给定数字k，你可以随意选出k条路径，所有路径经过的点，需要取并集，也就是去重
// 并集中的点，点权的累加和，叫做k条路径的收益
// 打印k条路径的收益最大值
// 1 <= n、k <= 2 * 10^5
// 所有点权都是int类型的正数
// 测试链接 : https://www.luogu.com.cn/problem/P10641
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//int n, k;
//int arr[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cnt = 0;
//
//int fa[MAXN];
//int son[MAXN];
//int top[MAXN];
//long long money[MAXN];
//
//long long sorted[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cnt] = head[u];
//    to[cnt] = v;
//    head[u] = cnt;
//}
//
//void dfs1(int u, int f) {
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            dfs1(v, u);
//        }
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            if (son[u] == 0 || money[son[u]] < money[v]) {
//                son[u] = v;
//            }
//        }
//    }
//    fa[u] = f;
//    money[u] = money[son[u]] + arr[u];
//}
//
//void dfs2(int u, int t) {
//    top[u] = t;
//    if (son[u] == 0) {
//        return;
//    }
//    dfs2(son[u], t);
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//long long compute() {
//    int len = 0;
//    for (int i = 1; i <= n; i++) {
//        if (top[i] == i) {
//        	sorted[++len] = money[i];
//        }
//    }
//    sort(sorted + 1, sorted + len + 1);
//    long long ans = 0;
//    for (int i = 1, j = len; i <= k; i++, j--) {
//        ans += sorted[j];
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> k;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1; i < n; i++) {
//        int u, v;
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs1(1, 0);
//    dfs2(1, 1);
//    cout << compute() << "\n";
//    return 0;
//}