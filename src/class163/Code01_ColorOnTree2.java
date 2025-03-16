package class163;

// 树上数颜色，C++版
// 测试链接 : https://www.luogu.com.cn/problem/U41492
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//int MAXN = 100001;
//int n, m;
//int arr[100001];
//
//int head[100001];
//int nxt[200002];
//int to[200002];
//int cnt = 0;
//
//int fa[100001];
//int siz[100001];
//int son[100001];
//
//int colorCnt[100001];
//int ans[100001];
//int total = 0;
//
//void addEdge(int u, int v) {
//    nxt[++cnt] = head[u];
//    to[cnt] = v;
//    head[u] = cnt;
//}
//
//void effect(int u) {
//    if (++colorCnt[arr[u]] == 1) {
//        total++;
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u]) {
//            effect(v);
//        }
//    }
//}
//
//void cancle(int u) {
//    if (--colorCnt[arr[u]] == 0) {
//        total--;
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u]) {
//            cancle(v);
//        }
//    }
//}
//
//void dfs1(int u, int f) {
//    fa[u] = f;
//    siz[u] = 1;
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            dfs1(v, u);
//        }
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
//void dfs2(int u, int keep) {
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, 0);
//        }
//    }
//    if (son[u] != 0) {
//        dfs2(son[u], 1);
//    }
//    if (++colorCnt[arr[u]] == 1) {
//        total++;
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            effect(v);
//        }
//    }
//    ans[u] = total;
//    if (keep == 0) {
//        cancle(u);
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
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    dfs1(1, 0);
//    dfs2(1, 1);
//    cin >> m;
//    for (int i = 1, cur; i <= m; i++) {
//        cin >> cur;
//        cout << ans[cur] << "\n";
//    }
//    return 0;
//}