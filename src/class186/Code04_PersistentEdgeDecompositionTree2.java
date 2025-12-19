package class186;

// 可持久化边分树，C++版
// 测试链接 : https://www.luogu.com.cn/problem/CF757G
// 测试链接 : https://codeforces.com/problemset/problem/757/G
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 500001;
//const int MAXM = MAXN * 30;
//int n, q, cntn;
//int arr[MAXN];
//
//int headg[MAXN];
//int nextg[MAXN << 1];
//int tog[MAXN << 1];
//int weightg[MAXN << 1];
//int cntg;
//
//int sonCnt[MAXN];
//int heads[MAXN];
//int nexts[MAXM];
//int sons[MAXM];
//int weights[MAXM];
//int cnts;
//
//bool vis[MAXN];
//int siz[MAXN];
//
//int root[MAXN];
//int ls[MAXM];
//int rs[MAXM];
//int lcnt[MAXM];
//int rcnt[MAXM];
//ll lsum[MAXM];
//ll rsum[MAXM];
//int cntt;
//
//int latest[MAXN];
//
//int tree[MAXN];
//
//void addEdge(int u, int v, int w) {
//    nextg[++cntg] = headg[u];
//    tog[cntg] = v;
//    weightg[cntg] = w;
//    headg[u] = cntg;
//}
//
//void addSon(int u, int v, int w) {
//    sonCnt[u]++;
//    nexts[++cnts] = heads[u];
//    sons[cnts] = v;
//    weights[cnts] = w;
//    heads[u] = cnts;
//}
//
//void buildSon(int u, int fa) {
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa) {
//            addSon(u, v, weightg[e]);
//            buildSon(v, u);
//        }
//    }
//}
//
//void rebuildTree() {
//    buildSon(1, 0);
//    cntn = n;
//    cntg = 1;
//    for (int i = 1; i <= cntn; i++) {
//        headg[i] = 0;
//    }
//    for (int u = 1; u <= cntn; u++) {
//        if (sonCnt[u] <= 2) {
//            for (int e = heads[u]; e > 0; e = nexts[e]) {
//                int v = sons[e];
//                int w = weights[e];
//                addEdge(u, v, w);
//                addEdge(v, u, w);
//            }
//        } else {
//            int node1 = ++cntn;
//            int node2 = ++cntn;
//            addEdge(u, node1, 0);
//            addEdge(node1, u, 0);
//            addEdge(u, node2, 0);
//            addEdge(node2, u, 0);
//            bool add1 = true;
//            for (int e = heads[u]; e > 0; e = nexts[e]) {
//                int v = sons[e];
//                int w = weights[e];
//                if (add1) {
//                    addSon(node1, v, w);
//                } else {
//                    addSon(node2, v, w);
//                }
//                add1 = !add1;
//            }
//        }
//    }
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa && !vis[e >> 1]) {
//            getSize(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//int getCentroidEdge(int u, int fa) {
//    getSize(u, fa);
//    int total = siz[u];
//    int edge = 0;
//    int best = total;
//    while (u > 0) {
//        int nextu = 0, nextfa = 0;
//        for (int e = headg[u]; e > 0; e = nextg[e]) {
//            int v = tog[e];
//            if (v != fa && !vis[e >> 1]) {
//                int cur = max(total - siz[v], siz[v]);
//                if (cur < best) {
//                    edge = e;
//                    best = cur;
//                    nextfa = u;
//                    nextu = v;
//                }
//            }
//        }
//        fa = nextfa;
//        u = nextu;
//    }
//    return edge;
//}
//
//void dfs(int u, int fa, ll dist, int op) {
//    if (u <= n) {
//        if (latest[u] == 0) {
//            latest[u] = ++cntt;
//            root[u] = cntt;
//        }
//        int cur = latest[u];
//        int nxt = ++cntt;
//        if (op == 0) {
//            ls[cur] = nxt;
//            lsum[cur] = dist;
//            lcnt[cur] = 1;
//        } else {
//            rs[cur] = nxt;
//            rsum[cur] = dist;
//            rcnt[cur] = 1;
//        }
//        latest[u] = nxt;
//    }
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa && !vis[e >> 1]) {
//            dfs(v, u, dist + weightg[e], op);
//        }
//    }
//}
//
//void solve(int u) {
//    int edge = getCentroidEdge(u, 0);
//    if (edge > 0) {
//        vis[edge >> 1] = true;
//        int v1 = tog[edge];
//        int v2 = tog[edge ^ 1];
//        dfs(v1, 0, 0, 0);
//        dfs(v2, 0, weightg[edge], 1);
//        solve(v1);
//        solve(v2);
//    }
//}
//
//int add(int pre, int addt) {
//    if (pre == 0 || addt == 0) {
//        return pre + addt;
//    }
//    int rt = ++cntt;
//    ls[rt] = ls[pre];
//    rs[rt] = rs[pre];
//    lcnt[rt] = lcnt[pre] + lcnt[addt];
//    rcnt[rt] = rcnt[pre] + rcnt[addt];
//    lsum[rt] = lsum[pre] + lsum[addt];
//    rsum[rt] = rsum[pre] + rsum[addt];
//    if (lcnt[addt] > 0) {
//        ls[rt] = add(ls[rt], ls[addt]);
//    }
//    if (rcnt[addt] > 0) {
//        rs[rt] = add(rs[rt], rs[addt]);
//    }
//    return rt;
//}
//
//ll query(int i, int t1, int t2) {
//    if (lcnt[i] == 0 && rcnt[i] == 0) {
//        return 0;
//    } else if (lcnt[i] > 0) {
//        return query(ls[i], ls[t1], ls[t2]) + (rsum[t2] - rsum[t1]) + lsum[i] * (rcnt[t2] - rcnt[t1]);
//    } else {
//        return query(rs[i], rs[t1], rs[t2]) + (lsum[t2] - lsum[t1]) + rsum[i] * (lcnt[t2] - lcnt[t1]);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> q;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1, u, v, w; i < n; i++) {
//        cin >> u >> v >> w;
//        addEdge(u, v, w);
//        addEdge(v, u, w);
//    }
//    rebuildTree();
//    solve(1);
//    for (int i = 1; i <= n; i++) {
//        tree[i] = add(tree[i - 1], root[arr[i]]);
//    }
//    ll mask = (1LL << 30) - 1;
//    ll lastAns = 0;
//    ll a, b, c;
//    int op, x, y, z;
//    for (int i = 1; i <= q; i++) {
//        cin >> op;
//        if (op == 1) {
//            cin >> a >> b >> c;
//            a ^= lastAns;
//            b ^= lastAns;
//            c ^= lastAns;
//            x = (int)a;
//            y = (int)b;
//            z = (int)c;
//            lastAns = query(root[z], tree[x - 1], tree[y]);
//            cout << lastAns << '\n';
//            lastAns &= mask;
//        } else {
//            cin >> a;
//            a ^= lastAns;
//            x = (int)a;
//            swap(arr[x], arr[x + 1]);
//            tree[x] = add(tree[x - 1], root[arr[x]]);
//        }
//    }
//    return 0;
//}