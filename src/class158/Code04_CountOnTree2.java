package class158;

// 路径上的第k小，C++版
// 有n个节点，编号1~n，每个节点有权值，有n-1条边，所有节点组成一棵树
// 一共有m条查询，每条查询 u v k : 打印u号点到v号点的路径上，第k小的点权
// 题目有强制在线的要求，上一次打印的答案为lastAns，初始时lastAns = 0
// 每次给定的u、v、k，按照如下方式得到真实的u、v、k，查询完成后更新lastAns
// 真实u = 给定u ^ lastAns
// 真实v = 给定v
// 真实k = 给定k
// 1 <= n、m <= 10^5
// 1 <= arr[i] <= 2^32 - 1
// 测试链接 : https://www.luogu.com.cn/problem/P2633
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXH = 20;
//const int MAXT = MAXN * MAXH;
//int n, m, s;
//int arr[MAXN];
//int sorted[MAXN];
//
//int head[MAXN];
//int to[MAXN << 1];
//int nxt[MAXN << 1];
//int cntg = 0;
//
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int size[MAXT];
//int cntt = 0;
//
//int deep[MAXN];
//int stjump[MAXN][MAXH];
//
//int kth(int num) {
//    int left = 1, right = s, mid;
//    while (left <= right) {
//        mid = (left + right) / 2;
//        if (sorted[mid] == num) {
//            return mid;
//        } else if (sorted[mid] < num) {
//            left = mid + 1;
//        } else {
//            right = mid - 1;
//        }
//    }
//    return -1;
//}
//
//int build(int l, int r) {
//    int rt = ++cntt;
//    size[rt] = 0;
//    if (l < r) {
//        int mid = (l + r) / 2;
//        ls[rt] = build(l, mid);
//        rs[rt] = build(mid + 1, r);
//    }
//    return rt;
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        sorted[i] = arr[i];
//    }
//    sort(sorted + 1, sorted + n + 1);
//    s = 1;
//    for (int i = 2; i <= n; i++) {
//        if (sorted[s] != sorted[i]) {
//            sorted[++s] = sorted[i];
//        }
//    }
//    root[0] = build(1, s);
//}
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//int insert(int jobi, int l, int r, int i) {
//    int rt = ++cntt;
//    ls[rt] = ls[i];
//    rs[rt] = rs[i];
//    size[rt] = size[i] + 1;
//    if (l < r) {
//        int mid = (l + r) / 2;
//        if (jobi <= mid) {
//            ls[rt] = insert(jobi, l, mid, ls[rt]);
//        } else {
//            rs[rt] = insert(jobi, mid + 1, r, rs[rt]);
//        }
//    }
//    return rt;
//}
//
//int query(int jobk, int l, int r, int u, int v, int lca, int lcafa) {
//    if (l == r) {
//        return l;
//    }
//    int lsize = size[ls[u]] + size[ls[v]] - size[ls[lca]] - size[ls[lcafa]];
//    int mid = (l + r) / 2;
//    if (lsize >= jobk) {
//        return query(jobk, l, mid, ls[u], ls[v], ls[lca], ls[lcafa]);
//    } else {
//        return query(jobk - lsize, mid + 1, r, rs[u], rs[v], rs[lca], rs[lcafa]);
//    }
//}
//
//void dfs(int u, int f) {
//    root[u] = insert(kth(arr[u]), 1, s, root[f]);
//    deep[u] = deep[f] + 1;
//    stjump[u][0] = f;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
//        if (to[ei] != f) {
//            dfs(to[ei], u);
//        }
//    }
//}
//
//int lca(int a, int b) {
//    if (deep[a] < deep[b]) {
//        swap(a, b);
//    }
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (deep[stjump[a][p]] >= deep[b]) {
//            a = stjump[a][p];
//        }
//    }
//    if (a == b) {
//        return a;
//    }
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (stjump[a][p] != stjump[b][p]) {
//            a = stjump[a][p];
//            b = stjump[b][p];
//        }
//    }
//    return stjump[a][0];
//}
//
//int kth(int u, int v, int k) {
//    int lcaNode = lca(u, v);
//    int i = query(k, 1, s, root[u], root[v], root[lcaNode], root[stjump[lcaNode][0]]);
//    return sorted[i];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    prepare();
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs(1, 0);
//    for (int i = 1, u, v, k, lastAns = 0; i <= m; i++) {
//        cin >> u >> v >> k;
//        u ^= lastAns;
//        lastAns = kth(u, v, k);
//        cout << lastAns << '\n';
//    }
//    return 0;
//}