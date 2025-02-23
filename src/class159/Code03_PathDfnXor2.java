package class159;

// 路径和子树的异或，C++版
// 一共有n个节点，n-1条边，组成一棵树，1号节点为树头，每个节点给定点权
// 一共有m条查询，每条查询是如下两种类型中的一种
// 1 x y   : 以x为头的子树中任选一个值，希望异或y之后的值最大，打印最大值
// 2 x y z : 节点x到节点y的路径中任选一个值，希望异或z之后的值最大，打印最大值
// 2 <= n、m <= 10^5
// 1 <= 点权、z < 2^30
// 测试链接 : https://www.luogu.com.cn/problem/P4592
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXT = MAXN * 62;
//const int MAXH = 16;
//const int BIT = 29;
//int n, m;
//int arr[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg = 0;
//
//int deep[MAXN];
//int size[MAXN];
//int stjump[MAXN][MAXH];
//int dfn[MAXN];
//int cntd = 0;
//
//int root1[MAXN];
//int root2[MAXN];
//int tree[MAXT][2];
//int pass[MAXT];
//int cntt = 0;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//int insert(int num, int i) {
//    int rt = ++cntt;
//    tree[rt][0] = tree[i][0];
//    tree[rt][1] = tree[i][1];
//    pass[rt] = pass[i] + 1;
//    for (int b = BIT, path, pre = rt, cur; b >= 0; b--, pre = cur) {
//        path = (num >> b) & 1;
//        i = tree[i][path];
//        cur = ++cntt;
//        tree[cur][0] = tree[i][0];
//        tree[cur][1] = tree[i][1];
//        pass[cur] = pass[i] + 1;
//        tree[pre][path] = cur;
//    }
//    return rt;
//}
//
//int query(int num, int u, int v) {
//    int ans = 0;
//    for (int b = BIT, path, best; b >= 0; b--) {
//        path = (num >> b) & 1;
//        best = path ^ 1;
//        if (pass[tree[v][best]] > pass[tree[u][best]]) {
//            ans += (1 << b);
//            u = tree[u][best];
//            v = tree[v][best];
//        } else {
//            u = tree[u][path];
//            v = tree[v][path];
//        }
//    }
//    return ans;
//}
//
//void dfs1(int u, int fa) {
//    deep[u] = deep[fa] + 1;
//    size[u] = 1;
//    stjump[u][0] = fa;
//    dfn[u] = ++cntd;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int ei = head[u], v; ei > 0; ei = nxt[ei]) {
//        v = to[ei];
//        if (v != fa) {
//            dfs1(v, u);
//            size[u] += size[v];
//        }
//    }
//}
//
//void dfs2(int u, int fa) {
//    root1[dfn[u]] = insert(arr[u], root1[dfn[u] - 1]);
//    root2[u] = insert(arr[u], root2[fa]);
//    for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
//        if (to[ei] != fa) {
//            dfs2(to[ei], u);
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
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs1(1, 0);
//    dfs2(1, 0);
//    for (int i = 1, op, x, y, z; i <= m; i++) {
//        cin >> op >> x >> y;
//        if (op == 1) {
//            cout << query(y, root1[dfn[x] - 1], root1[dfn[x] + size[x] - 1]) << '\n';
//        } else {
//            cin >> z;
//            int lcafa = stjump[lca(x, y)][0];
//            int ans = max(query(z, root2[lcafa], root2[x]), query(z, root2[lcafa], root2[y]));
//            cout << ans << '\n';
//        }
//    }
//    return 0;
//}