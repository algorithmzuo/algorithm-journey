package class163;

// 森林，C++版
// 一共有n个节点，编号1~n，初始时给定m条边，所有节点可能组成森林结构
// 每个节点都给定非负的点权，一共有t条操作，每条操作是如下两种类型中的一种
// 操作 Q x y k : 点x到点y路径上所有的权值中，打印第k小的权值是多少
//                题目保证x和y联通，并且路径上至少有k个点
// 操作 L x y   : 点x和点y之间连接一条边
//                题目保证操作后，所有节点仍然是森林
// 题目要求强制在线，请不要使用离线算法
// 1 <= n、m、t <= 8 * 10^4
// 点权 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3302
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 80001;
//const int MAXT = MAXN * 110;
//const int MAXH = 20;
//int testcase;
//int n, m, t;
//
//int arr[MAXN];
//int sorted[MAXN];
//int diff;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg = 0;
//
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int siz[MAXT];
//int cntt = 0;
//
//int dep[MAXN];
//int treeHead[MAXN];
//int headSiz[MAXN];
//int stjump[MAXN][MAXH];
//
//int kth(int num) {
//    int left = 1, right = diff, mid;
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
//    siz[rt] = siz[i] + 1;
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
//int query(int jobk, int l, int r, int u, int v, int lca_, int lcafa) {
//    if (l == r) {
//        return l;
//    }
//    int lsize = siz[ls[u]] + siz[ls[v]] - siz[ls[lca_]] - siz[ls[lcafa]];
//    int mid = (l + r) / 2;
//    if (lsize >= jobk) {
//        return query(jobk, l, mid, ls[u], ls[v], ls[lca_], ls[lcafa]);
//    } else {
//        return query(jobk - lsize, mid + 1, r, rs[u], rs[v], rs[lca_], rs[lcafa]);
//    }
//}
//
//void dfs(int u, int fa, int treeh) {
//    root[u] = insert(arr[u], 1, diff, root[fa]);
//    dep[u] = dep[fa] + 1;
//    treeHead[u] = treeh;
//    headSiz[treeh]++;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[ stjump[u][p - 1] ][p - 1];
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        if (to[e] != fa) {
//            dfs(to[e], u, treeh);
//        }
//    }
//}
//
//int lca(int a, int b) {
//    if (dep[a] < dep[b]) {
//        int tmp = a;
//        a = b;
//        b = tmp;
//    }
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (dep[stjump[a][p]] >= dep[b]) {
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
//int queryKth(int x, int y, int k) {
//    int xylca = lca(x, y);
//    int lcafa = stjump[xylca][0];
//    int i = query(k, 1, diff, root[x], root[y], root[xylca], root[lcafa]);
//    return sorted[i];
//}
//
//void connect(int x, int y) {
//    addEdge(x, y);
//    addEdge(y, x);
//    int fx = treeHead[x];
//    int fy = treeHead[y];
//    if (headSiz[fx] >= headSiz[fy]) {
//        dfs(y, x, fx);
//    } else {
//        dfs(x, y, fy);
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        sorted[i] = arr[i];
//    }
//    sort(sorted + 1, sorted + n + 1);
//    diff = 1;
//    for (int i = 2; i <= n; i++) {
//        if (sorted[diff] != sorted[i]) {
//            sorted[++diff] = sorted[i];
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        arr[i] = kth(arr[i]);
//    }
//    for (int i = 1; i <= n; i++) {
//        if (treeHead[i] == 0) {
//            dfs(i, 0, i);
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> testcase >> n >> m >> t;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        int u, v;
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    prepare();
//    char op;
//    int x, y, k, lastAns = 0;
//    for (int i = 1; i <= t; i++) {
//        cin >> op;
//        cin >> x;
//        cin >> y;
//        x ^= lastAns;
//        y ^= lastAns;
//        if (op == 'Q') {
//            cin >> k;
//            k ^= lastAns;
//            lastAns = queryKth(x, y, k);
//            cout << lastAns << "\n";
//        } else {
//            connect(x, y);
//        }
//    }
//    return 0;
//}