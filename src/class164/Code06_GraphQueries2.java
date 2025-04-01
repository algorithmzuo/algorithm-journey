package class164;

// 删边和查询，C++版
// 图里有n个点，m条无向边，初始时点权都不同，图里可能有若干个连通的部分
// 一共有q条操作，每条操作是如下两种类型中的一种
// 操作 1 x : 点x所在的连通区域中，假设点y拥有最大的点权
//            打印y的点权，然后把y的点权修改为0
// 操作 2 x : 删掉第x条边
// 1 <= n <= 2 * 10^5    1 <= m <= 3 * 10^5    1 <= q <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1416D
// 测试链接 : https://codeforces.com/problemset/problem/1416/D
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Edge {
//    int u, v, w;
//};
//
//bool cmp(Edge x, Edge y) {
//    return x.w < y.w;
//}
//
//const int MAXN = 200001;
//const int MAXK = 400001;
//const int MAXM = 300001;
//const int MAXQ = 500001;
//const int MAXH = 20;
//int INF = 1000000001;
//int n, m, q;
//
//int node[MAXN];
//Edge edge[MAXM];
//int ques[MAXQ][2];
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
//int stjump[MAXK][MAXH];
//int leafsiz[MAXK];
//int leafstart[MAXK];
//int leafseg[MAXK];
//int cntd;
//
//int maxdfn[MAXN << 2];
//
//void prepare() {
//    for (int i = 1; i <= q; i++) {
//        if (ques[i][0] == 2) {
//            edge[ques[i][1]].w = -1;
//        }
//    }
//    int time = 0;
//    for (int i = 1; i <= m; i++) {
//        if (edge[i].w != -1) {
//            edge[i].w = ++time;
//        }
//    }
//    for (int i = q; i >= 1; i--) {
//        if (ques[i][0] == 2) {
//            edge[ques[i][1]].w = ++time;
//        }
//    }
//}
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
//    for (int i = 1; i <= m; i++) {
//        int fx = find(edge[i].u);
//        int fy = find(edge[i].v);
//        if (fx != fy) {
//            father[fx] = father[fy] = ++cntu;
//            father[cntu] = cntu;
//            nodeKey[cntu] = edge[i].w;
//            addEdge(cntu, fx);
//            addEdge(cntu, fy);
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
//        leafstart[u] = ++cntd;
//        leafseg[cntd] = u;
//    } else {
//        leafsiz[u] = 0;
//        leafstart[u] = INF;
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        leafsiz[u] += leafsiz[to[e]];
//        leafstart[u] = min(leafstart[u], leafstart[to[e]]);
//    }
//}
//
//int getAncestor(int u, int limit) {
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (stjump[u][p] > 0 && nodeKey[stjump[u][p]] <= limit) {
//            u = stjump[u][p];
//        }
//    }
//    return u;
//}
//
//void up(int i) {
//    int l = i << 1;
//    int r = i << 1 | 1;
//    if (node[leafseg[maxdfn[l]]] > node[leafseg[maxdfn[r]]]) {
//        maxdfn[i] = maxdfn[l];
//    } else {
//        maxdfn[i] = maxdfn[r];
//    }
//}
//
//void build(int l, int r, int i) {
//    if (l == r) {
//        maxdfn[i] = l;
//    } else {
//        int mid = (l + r) / 2;
//        build(l, mid, i << 1);
//        build(mid + 1, r, i << 1 | 1);
//        up(i);
//    }
//}
//
//void update(int jobi, int jobv, int l, int r, int i) {
//    if (l == r) {
//        node[leafseg[jobi]] = jobv;
//    } else {
//        int mid = (l + r) / 2;
//        if (jobi <= mid) {
//            update(jobi, jobv, l, mid, i << 1);
//        } else {
//            update(jobi, jobv, mid + 1, r, i << 1 | 1);
//        }
//        up(i);
//    }
//}
//
//int queryMaxDfn(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return maxdfn[i];
//    } else {
//        int mid = (l + r) / 2;
//        int ldfn = 0, rdfn = 0;
//        if (jobl <= mid) {
//        	ldfn = queryMaxDfn(jobl, jobr, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//        	rdfn = queryMaxDfn(jobl, jobr, mid + 1, r, i << 1 | 1);
//        }
//        if (node[leafseg[ldfn]] > node[leafseg[rdfn]]) {
//            return ldfn;
//        } else {
//            return rdfn;
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> q;
//    for (int i = 1; i <= n; i++) {
//        cin >> node[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> edge[i].u >> edge[i].v;
//        edge[i].w = 0;
//    }
//    for (int i = 1; i <= q; i++) {
//        cin >> ques[i][0] >> ques[i][1];
//    }
//    prepare();
//    kruskalRebuild();
//    for (int i = 1; i <= cntu; i++) {
//        if (i == father[i]) {
//            dfs(i, 0);
//        }
//    }
//    build(1, n, 1);
//    int limit = m, anc, ansDfn;
//    for (int i = 1; i <= q; i++) {
//        if (ques[i][0] == 1) {
//            anc = getAncestor(ques[i][1], limit);
//            ansDfn = queryMaxDfn(leafstart[anc], leafstart[anc] + leafsiz[anc] - 1, 1, n, 1);
//            cout << node[leafseg[ansDfn]] << "\n";
//            update(ansDfn, 0, 1, n, 1);
//        } else {
//            limit--;
//        }
//    }
//    return 0;
//}