package class161;

// 软件包管理器，C++版
// 一共有n个软件，编号0~n-1，0号软件不依赖任何软件，其他每个软件都仅依赖一个软件
// 依赖关系由数组形式给出，题目保证不会出现循环依赖
// 一开始所有软件都是没有安装的，如果a依赖b，那么安装a需要安装b，同时卸载b需要卸载a
// 一共有m条操作，每种操作是如下2种类型中的一种
// 操作 install x    : 安装x，如果x已经安装打印0，否则打印有多少个软件的状态需要改变
// 操作 uninstall x  : 卸载x，如果x没有安装打印0，否则打印有多少个软件的状态需要改变
// 1 <= n、m <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/P2146
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//int arr[MAXN];
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg = 0;
//int fa[MAXN];
//int dep[MAXN];
//int siz[MAXN];
//int son[MAXN];
//int top[MAXN];
//int dfn[MAXN];
//int cntd = 0;
//int sum[MAXN << 2];
//bool update[MAXN << 2];
//int change[MAXN << 2];
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs1(int u, int f) {
//    fa[u] = f;
//    dep[u] = dep[f] + 1;
//    siz[u] = 1;
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) dfs1(v, u);
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
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
//    if (son[u] == 0) return;
//    dfs2(son[u], t);
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//void up(int i) {
//    sum[i] = sum[i << 1] + sum[i << 1 | 1];
//}
//
//void lazy(int i, int v, int n) {
//    sum[i] = v * n;
//    update[i] = true;
//    change[i] = v;
//}
//
//void down(int i, int ln, int rn) {
//    if (update[i]) {
//        lazy(i << 1, change[i], ln);
//        lazy(i << 1 | 1, change[i], rn);
//        update[i] = false;
//    }
//}
//
//void updateRange(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        lazy(i, jobv, r - l + 1);
//    } else {
//        int mid = (l + r) / 2;
//        down(i, mid - l + 1, r - mid);
//        if (jobl <= mid) updateRange(jobl, jobr, jobv, l, mid, i << 1);
//        if (jobr > mid) updateRange(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
//        up(i);
//    }
//}
//
//long long query(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) return sum[i];
//    int mid = (l + r) / 2;
//    down(i, mid - l + 1, r - mid);
//    long long ans = 0;
//    if (jobl <= mid) ans += query(jobl, jobr, l, mid, i << 1);
//    if (jobr > mid) ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
//    return ans;
//}
//
//void pathUpdate(int x, int y, int v) {
//    while (top[x] != top[y]) {
//        if (dep[top[x]] <= dep[top[y]]) {
//            updateRange(dfn[top[y]], dfn[y], v, 1, n, 1);
//            y = fa[top[y]];
//        } else {
//            updateRange(dfn[top[x]], dfn[x], v, 1, n, 1);
//            x = fa[top[x]];
//        }
//    }
//    updateRange(min(dfn[x], dfn[y]), max(dfn[x], dfn[y]), v, 1, n, 1);
//}
//
//int install(int x) {
//    int pre = sum[1];
//    pathUpdate(1, x, 1);
//    int post = sum[1];
//    return abs(post - pre);
//}
//
//int uninstall(int x) {
//    int pre = sum[1];
//    updateRange(dfn[x], dfn[x] + siz[x] - 1, 0, 1, n, 1);
//    int post = sum[1];
//    return abs(post - pre);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int u = 2, v; u <= n; u++) {
//        cin >> v;
//        v++;
//        addEdge(v, u);
//    }
//    dfs1(1, 0);
//    dfs2(1, 1);
//    cin >> m;
//    string op;
//    int x;
//    for (int i = 1; i <= m; i++) {
//        cin >> op >> x;
//        x++;
//        if (op == "install") {
//            cout << install(x) << '\n';
//        } else {
//            cout << uninstall(x) << '\n';
//        }
//    }
//    return 0;
//}