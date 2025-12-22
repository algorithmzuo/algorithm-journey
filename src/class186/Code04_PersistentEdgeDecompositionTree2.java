package class186;

// 可持久化边分树，C++版
// 树上有n个节点，给定n-1条边，每条边有边权
// 给定长度为n的数组arr，代表点编号组成的一个排列
// 接下来有q条操作，每条操作是如下两种类型中的一种
// 操作 1 x y z : 打印arr[x..y]中每个节点到节点z的简单路径距离之和
// 操作 2 x     : 交换arr[x]和arr[x+1]的值，输入保证 1 <= x < n
// 1 <= n、q <= 2 * 10^5
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
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
//const int MAXN = 400001;
//const int MAXT = MAXN * 30;
//int n, q, cntn;
//
//int head1[MAXN];
//int next1[MAXN << 1];
//int to1[MAXN << 1];
//int weight1[MAXN << 1];
//int cnt1;
//
//int head2[MAXN];
//int next2[MAXN << 1];
//int to2[MAXN << 1];
//int weight2[MAXN << 1];
//int cnt2;
//
//bool vis[MAXN];
//int siz[MAXN];
//
//int up[MAXN];
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int lcnt[MAXT];
//int rcnt[MAXT];
//ll lsum[MAXT];
//ll rsum[MAXT];
//int cntt;
//
//int arr[MAXN];
//int tree[MAXN];
//
//void addEdge1(int u, int v, int w) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    weight1[cnt1] = w;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v, int w) {
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    weight2[cnt2] = w;
//    head2[u] = cnt2;
//}
//
//void rebuild(int u, int fa) {
//    int last = 0;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        int w = weight1[e];
//        if (v != fa) {
//            if (last == 0) {
//                last = u;
//                addEdge2(u, v, w);
//                addEdge2(v, u, w);
//            } else {
//                int add = ++cntn;
//                addEdge2(last, add, 0);
//                addEdge2(add, last, 0);
//                addEdge2(add, v, w);
//                addEdge2(v, add, w);
//                last = add;
//            }
//            rebuild(v, u);
//        }
//    }
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
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
//        for (int e = head2[u]; e > 0; e = next2[e]) {
//            int v = to2[e];
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
//        if (up[u] == 0) {
//            up[u] = ++cntt;
//            root[u] = cntt;
//        }
//        int cur = up[u];
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
//        up[u] = nxt;
//    }
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa && !vis[e >> 1]) {
//            dfs(v, u, dist + weight2[e], op);
//        }
//    }
//}
//
//void solve(int u) {
//    int edge = getCentroidEdge(u, 0);
//    if (edge > 0) {
//        vis[edge >> 1] = true;
//        int v1 = to2[edge];
//        int v2 = to2[edge ^ 1];
//        dfs(v1, 0, 0, 0);
//        dfs(v2, 0, weight2[edge], 1);
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
//        return query(ls[i], ls[t1], ls[t2]) + rsum[t2] - rsum[t1] + lsum[i] * (rcnt[t2] - rcnt[t1]);
//    } else {
//        return query(rs[i], rs[t1], rs[t2]) + lsum[t2] - lsum[t1] + rsum[i] * (lcnt[t2] - lcnt[t1]);
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
//        addEdge1(u, v, w);
//        addEdge1(v, u, w);
//    }
//    cntn = n;
//    cnt2 = 1;
//    rebuild(1, 0);
//    solve(1);
//    for (int i = 1; i <= n; i++) {
//        tree[i] = add(tree[i - 1], root[arr[i]]);
//    }
//    ll mask = (1LL << 30) - 1;
//    ll lastAns = 0;
//    ll a, b, c;
//    int op, x, y, z, tmp;
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
//            tmp = arr[x];
//            arr[x] = arr[x + 1];
//            arr[x + 1] = tmp;
//            tree[x] = add(tree[x - 1], root[arr[x]]);
//        }
//    }
//    return 0;
//}