package class186;

// 红黑蛛网，C++版
// 一共n个节点，给定n-1条边，所有节点组成一棵树
// 每条边有两种边权，w为重量，c为颜色，颜色只有红和黑
// 考虑点对(x, y)的简单路径，x和y必须不同，假设红边数量r、黑边数量b
// 如果 2 * min(r, b) >= max(r, b)，那么简单路径是合法的
// 合法简单路径的收益 = 路径上所有边的重量乘积
// 注意点对(x, y)和点对(y, x)的简单路径，只能算一次收益
// 打印所有合法简单路径的收益总乘积，结果对 1000000007 取余，不存在任何合法路径打印1
// 2 <= n <= 10^5    1 <= w <= 10^9 + 6
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
//const int MAXN = 200001;
//const int MOD = 1000000007;
//int n, cntn;
//
//int head1[MAXN];
//int next1[MAXN << 1];
//int to1[MAXN << 1];
//int weight1[MAXN << 1];
//int color1[MAXN << 1];
//int cnt1;
//
//int head2[MAXN];
//int next2[MAXN << 1];
//int to2[MAXN << 1];
//int weight2[MAXN << 1];
//int color2[MAXN << 1];
//int cnt2;
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
//void addEdge1(int u, int v, int w, int c) {
//    next1[++cnt1] = head1[u];
//    to1[cnt1] = v;
//    weight1[cnt1] = w;
//    color1[cnt1] = c;
//    head1[u] = cnt1;
//}
//
//void addEdge2(int u, int v, int w, int c) {
//    next2[++cnt2] = head2[u];
//    to2[cnt2] = v;
//    weight2[cnt2] = w;
//    color2[cnt2] = c;
//    head2[u] = cnt2;
//}
//
//void rebuild(int u, int fa) {
//    int last = 0;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        int w = weight1[e];
//        int c = color1[e];
//        if (v != fa) {
//            if (last == 0) {
//                last = u;
//                addEdge2(u, v, w, c);
//                addEdge2(v, u, w, c);
//            } else {
//                int add = ++cntn;
//                addEdge2(last, add, 1, -1);
//                addEdge2(add, last, 1, -1);
//                addEdge2(add, v, w, c);
//                addEdge2(v, add, w, c);
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
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa && !vis[e >> 1]) {
//            int nextRed = red + (color2[e] == 0 ? 1 : 0);
//            int nextBlack = black + (color2[e] == 1 ? 1 : 0);
//            dfs(v, u, nextRed, nextBlack, path * weight2[e] % MOD);
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
//    for (int e = head2[u]; e > 0; e = next2[e]) {
//        int v = to2[e];
//        if (v != fa && !vis[e >> 1]) {
//            int nextRed = red + (color2[e] == 0 ? 1 : 0);
//            int nextBlack = black + (color2[e] == 1 ? 1 : 0);
//            calcAns(v, u, nextRed, nextBlack, path * weight2[e] % MOD);
//        }
//    }
//}
//
//void calc(int edge) {
//    cnta = 0;
//    int v = to2[edge];
//    dfs(v, 0, 0, 0, 1);
//    sort(redArr + 1, redArr + cnta + 1, NodeCmp);
//    sort(blackArr + 1, blackArr + cnta + 1, NodeCmp);
//    for (int i = 2; i <= cnta; i++) {
//        redArr[i].path = redArr[i - 1].path * redArr[i].path % MOD;
//        blackArr[i].path = blackArr[i - 1].path * blackArr[i].path % MOD;
//    }
//    v = to2[edge ^ 1];
//    int red = (color2[edge] == 0 ? 1 : 0);
//    int black = (color2[edge] == 1 ? 1 : 0);
//    calcAns(v, 0, red, black, weight2[edge] % MOD);
//}
//
//void solve(int u) {
//    int edge = getCentroidEdge(u, 0);
//    if (edge > 0) {
//        vis[edge >> 1] = true;
//        calc(edge);
//        solve(to2[edge]);
//        solve(to2[edge ^ 1]);
//    }
//}
//
//void prepare(int u, int fa) {
//    siz[u] = 1;
//    for (int e = head1[u]; e > 0; e = next1[e]) {
//        int v = to1[e];
//        if (v != fa) {
//            prepare(v, u);
//            siz[u] += siz[v];
//            ans1 = ans1 * power(weight1[e], 1LL * siz[v] * (n - siz[v])) % MOD;
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
//        addEdge1(u, v, w, c);
//        addEdge1(v, u, w, c);
//    }
//    cntn = n;
//    cnt2 = 1;
//    ans1 = ans2 = 1;
//    prepare(1, 0);
//    rebuild(1, 0);
//    solve(1);
//    ll ans = ans1 * power(ans2, MOD - 2) % MOD;
//    cout << ans << '\n';
//    return 0;
//}