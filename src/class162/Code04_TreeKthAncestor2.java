package class162;

// 树上k级祖先，C++版
// 一共有n个节点，编号1~n，给定一个长度为n的数组arr，表示依赖关系
// 如果arr[i] = j，表示i号节点的父节点是j，如果arr[i] == 0，表示i号节点是树头
// 一共有m条查询，每条查询 x k : 打印x往上走k步的祖先节点编号
// 题目要求预处理的时间复杂度O(n * log n)，处理每条查询的时间复杂度O(1)
// 题目要求强制在线，必须按顺序处理每条查询，如何得到每条查询的入参，请打开测试链接查看
// 1 <= n <= 5 * 10^5
// 1 <= m <= 5 * 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P5903
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//#define ui unsigned int
//
//using namespace std;
//
//const int MAXN = 500001;
//const int MAXH = 20;
//int n, m;
//ui s;
//int root;
//
//int head[MAXN];
//int nxt[MAXN];
//int to[MAXN];
//int cntg = 0;
//
//int stjump[MAXN][MAXH];
//int dep[MAXN];
//int len[MAXN];
//int son[MAXN];
//int top[MAXN];
//int dfn[MAXN];
//int cntd = 0;
//
//int high[MAXN];
//int up[MAXN];
//int down[MAXN];
//
//ui get(ui x) {
//    x ^= x << 13;
//    x ^= x >> 17;
//    x ^= x << 5;
//    s = x;
//    return x;
//}
//
//void setUp(int u, int i, int v) {
//    up[dfn[u] + i] = v;
//}
//
//int getUp(int u, int i) {
//    return up[dfn[u] + i];
//}
//
//void setDown(int u, int i, int v) {
//    down[dfn[u] + i] = v;
//}
//
//int getDown(int u, int i) {
//    return down[dfn[u] + i];
//}
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs1(int u, int f) {
//    stjump[u][0] = f;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    dep[u] = dep[f] + 1;
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            dfs1(v, u);
//        }
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            if (son[u] == 0 || len[son[u]] < len[v]) {
//                son[u] = v;
//            }
//        }
//    }
//    len[u] = len[son[u]] + 1;
//}
//
//void dfs2(int u, int t) {
//    top[u] = t;
//    dfn[u] = ++cntd;
//    if (son[u] == 0) return;
//    dfs2(son[u], t);
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != stjump[u][0] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//void prepare() {
//    dfs1(root, 0);
//    dfs2(root, root);
//    high[0] = -1;
//    for (int i = 1; i <= n; i++) {
//        high[i] = high[i / 2] + 1;
//    }
//    for (int u = 1; u <= n; u++) {
//        if (top[u] == u) {
//            for (int i = 0, a = u, b = u; i < len[u]; i++, a = stjump[a][0], b = son[b]) {
//                setUp(u, i, a);
//                setDown(u, i, b);
//            }
//        }
//    }
//}
//
//int query(int x, int k) {
//    if (k == 0) {
//        return x;
//    }
//    if (k == (1 << high[k])) {
//        return stjump[x][high[k]];
//    }
//    x = stjump[x][high[k]];
//    k -= (1 << high[k]);
//    k -= dep[x] - dep[top[x]];
//    x = top[x];
//    return (k >= 0) ? getUp(x, k) : getDown(x, -k);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> s;
//    for (int i = 1, father; i <= n; i++) {
//        cin >> father;
//        if (father == 0) {
//            root = i;
//        } else {
//            addEdge(father, i);
//        }
//    }
//    prepare();
//    long long ans = 0;
//    for (int i = 1, x, k, lastAns = 0; i <= m; i++) {
//        x = (get(s) ^ lastAns) % n + 1;
//        k = (get(s) ^ lastAns) % dep[x];
//        lastAns = query(x, k);
//        ans ^= (long long) i * lastAns;
//    }
//    cout << ans << '\n';
//    return 0;
//}