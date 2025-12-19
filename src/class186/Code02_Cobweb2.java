package class186;

// 红黑蛛网，C++版
// 测试链接 : https://www.luogu.com.cn/problem/CF833D
// 测试链接 : https://codeforces.com/problemset/problem/833/D
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//struct Node {
//    int key;
//    ll path;
//};
//
//bool NodeCmp(Node x, Node y) {
//    return x.key < y.key;
//}
//
//const int MAXN = 400001;
//const int MAXM = 4000001;
//const int MOD = 1000000007;
//int n, cntn;
//
//int headg[MAXN];
//int nextg[MAXN << 1];
//int tog[MAXN << 1];
//int weightg[MAXN << 1];
//int colorg[MAXN << 1];
//int cntg;
//
//int sonCnt[MAXN];
//int heads[MAXN];
//int nexts[MAXM];
//int sons[MAXM];
//int weights[MAXM];
//int colors[MAXM];
//int cnts;
//
//bool vis[MAXN];
//int siz[MAXN];
//
//Node redArr[MAXN];
//Node blackArr[MAXN];
//int cnta;
//
//ll ans1, ans2;
//
//ll power(ll x, ll p) {
//    ll ans = 1;
//    x %= MOD;
//    while (p != 0) {
//        if (p & 1) {
//            ans = ans * x % MOD;
//        }
//        p >>= 1;
//        x = x * x % MOD;
//    }
//    return ans;
//}
//
//void addEdge(int u, int v, int w, int c) {
//    nextg[++cntg] = headg[u];
//    tog[cntg] = v;
//    weightg[cntg] = w;
//    colorg[cntg] = c;
//    headg[u] = cntg;
//}
//
//void addSon(int u, int v, int w, int c) {
//    sonCnt[u]++;
//    nexts[++cnts] = heads[u];
//    sons[cnts] = v;
//    weights[cnts] = w;
//    colors[cnts] = c;
//    heads[u] = cnts;
//}
//
//void buildSon(int u, int fa) {
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa) {
//            addSon(u, v, weightg[e], colorg[e]);
//            buildSon(v, u);
//        }
//    }
//}
//
//void rebuildTree() {
//    buildSon(1, 0);
//    cntn = n;
//    cntg = 1;
//    for (int u = 1; u <= cntn; u++) {
//        headg[u] = 0;
//    }
//    for (int u = 1; u <= cntn; u++) {
//        if (sonCnt[u] <= 2) {
//            for (int e = heads[u]; e > 0; e = nexts[e]) {
//                int v = sons[e];
//                int w = weights[e];
//                int c = colors[e];
//                addEdge(u, v, w, c);
//                addEdge(v, u, w, c);
//            }
//        } else {
//            int node1 = ++cntn;
//            int node2 = ++cntn;
//            addEdge(u, node1, 1, -1);
//            addEdge(node1, u, 1, -1);
//            addEdge(u, node2, 1, -1);
//            addEdge(node2, u, 1, -1);
//            bool add1 = true;
//            for (int e = heads[u]; e > 0; e = nexts[e]) {
//                int v = sons[e];
//                int w = weights[e];
//                int c = colors[e];
//                if (add1) {
//                    addSon(node1, v, w, c);
//                } else {
//                    addSon(node2, v, w, c);
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
//int lessThan(Node arr[], int len, int num) {
//    int l = 1, r = len, mid, ans = 0;
//    while (l <= r) {
//        mid = (l + r) >> 1;
//        if (arr[mid].key < num) {
//            ans = mid;
//            l = mid + 1;
//        } else {
//            r = mid - 1;
//        }
//    }
//    return ans;
//}
//
//void dfs(int u, int fa, int red, int black, ll path) {
//    if (u <= n) {
//        redArr[++cnta] = { 2 * red - black, path };
//        blackArr[cnta] = { 2 * black - red, path };
//    }
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa && !vis[e >> 1]) {
//            int nextRed = red + (colorg[e] == 0 ? 1 : 0);
//            int nextBlack = black + (colorg[e] == 1 ? 1 : 0);
//            dfs(v, u, nextRed, nextBlack, path * weightg[e] % MOD);
//        }
//    }
//}
//
//void calcAns(int u, int fa, int red, int black, ll path) {
//    if (u <= n) {
//        int r = lessThan(redArr, cnta, black - 2 * red);
//        int b = lessThan(blackArr, cnta, red - 2 * black);
//        if (r > 0) {
//            ans2 = ans2 * power(path, r) % MOD * redArr[r].path % MOD;
//        }
//        if (b > 0) {
//            ans2 = ans2 * power(path, b) % MOD * blackArr[b].path % MOD;
//        }
//    }
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa && !vis[e >> 1]) {
//            int nextRed = red + (colorg[e] == 0 ? 1 : 0);
//            int nextBlack = black + (colorg[e] == 1 ? 1 : 0);
//            calcAns(v, u, nextRed, nextBlack, path * weightg[e] % MOD);
//        }
//    }
//}
//
//void calc(int edge) {
//    cnta = 0;
//    int v = tog[edge];
//    dfs(v, 0, 0, 0, 1);
//    sort(redArr + 1, redArr + cnta + 1, NodeCmp);
//    sort(blackArr + 1, blackArr + cnta + 1, NodeCmp);
//    for (int i = 2; i <= cnta; i++) {
//        redArr[i].path = redArr[i - 1].path * redArr[i].path % MOD;
//        blackArr[i].path = blackArr[i - 1].path * blackArr[i].path % MOD;
//    }
//    v = tog[edge ^ 1];
//    int red = (colorg[edge] == 0 ? 1 : 0);
//    int black = (colorg[edge] == 1 ? 1 : 0);
//    calcAns(v, 0, red, black, weightg[edge] % MOD);
//}
//
//void solve(int u) {
//    int edge = getCentroidEdge(u, 0);
//    if (edge > 0) {
//        vis[edge >> 1] = true;
//        calc(edge);
//        solve(tog[edge]);
//        solve(tog[edge ^ 1]);
//    }
//}
//
//void prepare(int u, int fa) {
//    siz[u] = 1;
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        int v = tog[e];
//        if (v != fa) {
//            prepare(v, u);
//            siz[u] += siz[v];
//            ans1 = ans1 * power(weightg[e], 1LL * siz[v] * (n - siz[v])) % MOD;
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1, u, v, w, c; i < n; i++) {
//        cin >> u >> v >> w >> c;
//        addEdge(u, v, w, c);
//        addEdge(v, u, w, c);
//    }
//    ans1 = ans2 = 1;
//    prepare(1, 0);
//    rebuildTree();
//    solve(1);
//    ll ans = ans1 * power(ans2, MOD - 2) % MOD;
//    cout << ans << '\n';
//    return 0;
//}