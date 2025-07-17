package class164;

// youyou的军训，C++版
// 图里有n个点，m条无向边，每条边给定不同的边权，图里可能有若干个连通的部分
// 一开始limit = 0，接下来有q条操作，每条操作都是如下的三种类型中的一种
// 操作 1 x   : 所有修改操作生效，然后limit变成x，图中那些边权小于limit的边断开
// 操作 2 x   : 查询点x所在连通区域大小
// 操作 3 x y : 第x条边的边权修改为y，但不是立刻生效，而是下次limit改变时生效
// 题目保证边权不管怎么修改，所有边权都不相等，并且每条边的边权排名不发生变化
// 1 <= n、m、q <= 4 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P9638
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Edge {
//    int u, v, w, i;
//};
//
//bool cmp(Edge x, Edge y) {
//    return x.w > y.w;
//}
//
//const int MAXK = 800001;
//const int MAXM = 400001;
//const int MAXH = 20;
//int n, m, q;
//Edge edge[MAXM];
//int edgeToTree[MAXM];
//
//int father[MAXK];
//
//int head[MAXK];
//int nxt[MAXK];
//int to[MAXK];
//int cntg;
//int nodeKey[MAXK];
//int cntu;
//
//int leafsiz[MAXK];
//int stjump[MAXK][MAXH];
//
//int pendEdge[MAXM];
//int pendVal[MAXM];
//int cntp;
//
//int find(int i) {
//    if (i != father[i]) {
//        father[i] = find(father[i]);
//    }
//    return father[i];
//}
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void kruskalRebuild() {
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//    }
//    sort(edge + 1, edge + m + 1, cmp);
//    cntu = n;
//    for (int i = 1, fx, fy; i <= m; i++) {
//        fx = find(edge[i].u);
//        fy = find(edge[i].v);
//        if (fx != fy) {
//            father[fx] = father[fy] = ++cntu;
//            father[cntu] = cntu;
//            nodeKey[cntu] = edge[i].w;
//            addEdge(cntu, fx);
//            addEdge(cntu, fy);
//            edgeToTree[edge[i].i] = cntu;
//        }
//    }
//}
//
//void dfs(int u, int fa) {
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        dfs(to[e], u);
//    }
//    if (u <= n) {
//        leafsiz[u] = 1;
//    } else {
//        leafsiz[u] = 0;
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        leafsiz[u] += leafsiz[to[e]];
//    }
//}
//
//int query(int u, int limit) {
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (stjump[u][p] > 0 && nodeKey[stjump[u][p]] >= limit) {
//            u = stjump[u][p];
//        }
//    }
//    return leafsiz[u];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> q;
//    for (int i = 1; i <= m; i++) {
//        cin >> edge[i].u >> edge[i].v >> edge[i].w;
//        edge[i].i = i;
//    }
//    kruskalRebuild();
//    for (int i = 1; i <= cntu; i++) {
//        if (i == father[i]) {
//            dfs(i, 0);
//        }
//    }
//    int op, x, y, limit = 0;
//    for (int i = 1; i <= q; i++) {
//        cin >> op;
//        if (op == 1) {
//            for (int k = 1; k <= cntp; k++) {
//                nodeKey[edgeToTree[pendEdge[k]]] = pendVal[k];
//            }
//            cntp = 0;
//            cin >> limit;
//        } else if (op == 2) {
//            cin >> x;
//            cout << query(x, limit) << "\n";
//        } else {
//            cin >> x >> y;
//            if (edgeToTree[x] != 0) {
//                pendEdge[++cntp] = x;
//                pendVal[cntp] = y;
//            }
//        }
//    }
//    return 0;
//}