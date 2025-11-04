package class182;

// 命运，C++版
// 一共有n个节点，给定n-1条边，所有节点组成一棵树，规定1号节点是树头
// 给定m个点对，每个点对(x, y)，x是y的祖先节点，路径由从上到下的边组成
// 树上的每条边都要涂上白色或者黑色，完全由你决定
// 但是请保证每个点对的路径中，至少有一条黑色的边存在
// 打印给树涂色的方法数，答案对 998244353 取模
// 1 <= n、m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P6773
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 500001;
//const int MAXT = MAXN * 40;
//const int MOD = 998244353;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//long long sum[MAXT];
//long long mulLazy[MAXT];
//int cntt;
//
//int dep[MAXN];
//int maxdep[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void up(int i) {
//    sum[i] = (sum[ls[i]] + sum[rs[i]]) % MOD;
//}
//
//void lazy(int i, long long v) {
//    if (i != 0) {
//        sum[i] = sum[i] * v % MOD;
//        mulLazy[i] = mulLazy[i] * v % MOD;
//    }
//}
//
//void down(int i) {
//    if (mulLazy[i] != 1) {
//        lazy(ls[i], mulLazy[i]);
//        lazy(rs[i], mulLazy[i]);
//        mulLazy[i] = 1;
//    }
//}
//
//int insert(int jobi, int l, int r, int i) {
//    int rt = i;
//    if (rt == 0) {
//        rt = ++cntt;
//        mulLazy[rt] = 1;
//    }
//    if (l == r) {
//        sum[rt] = 1;
//    } else {
//        down(rt);
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            ls[rt] = insert(jobi, l, mid, ls[rt]);
//        } else {
//            rs[rt] = insert(jobi, mid + 1, r, rs[rt]);
//        }
//        up(rt);
//    }
//    return rt;
//}
//
//long long query(int jobl, int jobr, int l, int r, int i) {
//    if (i == 0) {
//        return 0;
//    }
//    if (jobl <= l && r <= jobr) {
//        return sum[i] % MOD;
//    }
//    down(i);
//    int mid = (l + r) >> 1;
//    long long ans = 0;
//    if (jobl <= mid) {
//        ans = query(jobl, jobr, l, mid, ls[i]) % MOD;
//    }
//    if (jobr > mid) {
//        ans = (ans + query(jobl, jobr, mid + 1, r, rs[i])) % MOD;
//    }
//    return ans;
//}
//
//int Merge(int l, int r, int t1, int t2, long long sum1, long long sum2) {
//    if (t1 == 0 || t2 == 0) {
//        if (t1 != 0) {
//            lazy(t1, sum2);
//        }
//        if (t2 != 0) {
//            lazy(t2, sum1);
//        }
//        return t1 + t2;
//    }
//    if (l == r) {
//        sum[t1] = ((sum[t1] * (sum2 + sum[t2])) % MOD + (sum[t2] * sum1) % MOD) % MOD;
//    } else {
//        down(t1);
//        down(t2);
//        int mid = (l + r) >> 1;
//        long long tmp1 = sum[ls[t1]];
//        long long tmp2 = sum[ls[t2]];
//        ls[t1] = Merge(l, mid, ls[t1], ls[t2], sum1, sum2);
//        rs[t1] = Merge(mid + 1, r, rs[t1], rs[t2], sum1 + tmp1, sum2 + tmp2);
//        up(t1);
//    }
//    return t1;
//}
//
//void dfs(int u, int fa) {
//    dep[u] = dep[fa] + 1;
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            dfs(v, u);
//        }
//    }
//}
//
//void dp(int u, int fa) {
//    root[u] = insert(maxdep[u], 0, n, root[u]);
//    for (int e = head[u]; e; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa) {
//            dp(v, u);
//            root[u] = Merge(0, n, root[u], root[v], 0, query(0, dep[u], 0, n, root[v]));
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs(1, 0);
//    cin >> m;
//    for (int i = 1, x, y; i <= m; i++) {
//        cin >> x >> y;
//        if (dep[x] > maxdep[y]) {
//            maxdep[y] = dep[x];
//        }
//    }
//    dp(1, 0);
//    long long ans = query(0, 0, 0, n, root[1]) % MOD;
//    cout << ans << '\n';
//    return 0;
//}