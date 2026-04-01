package class195;

// 美丽的树，树剖优化建图，C++版
// 一共n个节点，给定n-1条无向边，所有节点组成一棵树，1号节点是根
// 你需要给每个节点赋值，但是不能破坏如下的m条关系，关系的格式如下
// 关系 1 a b c : 节点a到节点b的路径上，值最小的节点必须是节点c，输入保证c一定在路径上
// 关系 2 a b c : 节点a到节点b的路径上，值最大的节点必须是节点c，输入保证c一定在路径上
// 如果存在赋值方案，并且这些值是1到n的一个排列，打印一种方案即可，否则打印-1
// 2 <= n、m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1904F
// 测试链接 : https://codeforces.com/problemset/problem/1904/F
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//const int MAXT = MAXN * 10;
//const int MAXE = MAXN * 50;
//int n, m;
//
//int head1[MAXN];
//int next1[MAXN << 1];
//int to1[MAXN << 1];
//int cnt1;
//
//int indegree[MAXT];
//int head2[MAXT];
//int next2[MAXE];
//int to2[MAXE];
//int cnt2;
//
//int ls[MAXT];
//int rs[MAXT];
//int rootOut, rootIn;
//int cntt;
//
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//int dfn[MAXN];
//int seg[MAXN];
//int cntd;
//
//int que[MAXT];
//int ans[MAXN];
//
//void addEdge1(int u, int v) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v) {
//    indegree[v]++;
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    head2[u] = cnt2;
//}
//
//int buildOut(int l, int r) {
//    int rt;
//    if (l == r) {
//        rt = l;
//    } else {
//        rt = ++cntt;
//        int mid = (l + r) >> 1;
//        ls[rt] = buildOut(l, mid);
//        rs[rt] = buildOut(mid + 1, r);
//        addEdge2(ls[rt], rt);
//        addEdge2(rs[rt], rt);
//    }
//    return rt;
//}
//
//int buildIn(int l, int r) {
//    int rt;
//    if (l == r) {
//        rt = l;
//    } else {
//        rt = ++cntt;
//        int mid = (l + r) >> 1;
//        ls[rt] = buildIn(l, mid);
//        rs[rt] = buildIn(mid + 1, r);
//        addEdge2(rt, ls[rt]);
//        addEdge2(rt, rs[rt]);
//    }
//    return rt;
//}
//
//void xToRange(int jobx, int jobl, int jobr, int l, int r, int i) {
//    if (jobl > jobr) {
//        return;
//    }
//    if (jobl <= l && r <= jobr) {
//        addEdge2(jobx, i);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            xToRange(jobx, jobl, jobr, l, mid, ls[i]);
//        }
//        if (jobr > mid) {
//            xToRange(jobx, jobl, jobr, mid + 1, r, rs[i]);
//        }
//    }
//}
//
//void rangeToX(int jobl, int jobr, int jobx, int l, int r, int i) {
//    if (jobl > jobr) {
//        return;
//    }
//    if (jobl <= l && r <= jobr) {
//        addEdge2(i, jobx);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            rangeToX(jobl, jobr, jobx, l, mid, ls[i]);
//        }
//        if (jobr > mid) {
//            rangeToX(jobl, jobr, jobx, mid + 1, r, rs[i]);
//        }
//    }
//}
//
//void dfs1(int u, int f) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    for (int e = head1[u], v; e > 0; e = next1[e]) {
//        v = to1[e];
//        if (v != f) {
//            dfs1(v, u);
//            siz[u] += siz[v];
//            if (son[u] == 0 || siz[son[u]] < siz[v]) {
//                son[u] = v;
//            }
//        }
//    }
//}
//
//void dfs2(int u, int t) {
//    top[u] = t;
//    dfn[u] = ++cntd;
//    seg[cntd] = u;
//    if (son[u] == 0) {
//        return;
//    }
//    dfs2(son[u], t);
//    for (int e = head1[u], v; e > 0; e = next1[e]) {
//        v = to1[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//void pathSet(int op, int x, int y, int z) {
//    if (op == 1) {
//        if (x <= z && z <= y) {
//            xToRange(z, x, z - 1, 1, n, rootIn);
//            xToRange(z, z + 1, y, 1, n, rootIn);
//        } else {
//            xToRange(z, x, y, 1, n, rootIn);
//        }
//    } else {
//        if (x <= z && z <= y) {
//            rangeToX(x, z - 1, z, 1, n, rootOut);
//            rangeToX(z + 1, y, z, 1, n, rootOut);
//        } else {
//            rangeToX(x, y, z, 1, n, rootOut);
//        }
//    }
//}
//
//void link(int op, int a, int b, int c) {
//    while (top[a] != top[b]) {
//        if (dep[top[a]] < dep[top[b]]) {
//            swap(a, b);
//        }
//        pathSet(op, dfn[top[a]], dfn[a], dfn[c]);
//        a = fa[top[a]];
//    }
//    pathSet(op, min(dfn[a], dfn[b]), max(dfn[a], dfn[b]), dfn[c]);
//}
//
//bool topo() {
//    int qi = 1, qsiz = 0;
//    for (int i = 1; i <= cntt; i++) {
//        if (indegree[i] == 0) {
//            que[++qsiz] = i;
//        }
//    }
//    int val = 0;
//    while (qi <= qsiz) {
//        int u = que[qi++];
//        if (u <= n) {
//            ans[seg[u]] = ++val;
//        }
//        for (int e = head2[u]; e > 0; e = next2[e]) {
//            int v = to2[e];
//            if (--indegree[v] == 0) {
//                que[++qsiz] = v;
//            }
//        }
//    }
//    return qsiz == cntt;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    cntt = n;
//    rootOut = buildOut(1, n);
//    rootIn = buildIn(1, n);
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge1(u, v);
//        addEdge1(v, u);
//    }
//    dfs1(1, 0);
//    dfs2(1, 1);
//    for (int i = 1, op, a, b, c; i <= m; i++) {
//        cin >> op >> a >> b >> c;
//        link(op, a, b, c);
//    }
//    bool check = topo();
//    if (check) {
//        for (int i = 1; i <= n; i++) {
//            cout << ans[i] << " ";
//        }
//    } else {
//        cout << -1 << "\n";
//    }
//    return 0;
//}