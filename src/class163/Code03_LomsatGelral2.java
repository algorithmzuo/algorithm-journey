package class163;

// 主导颜色累加和，C++版
// 测试链接 : https://www.luogu.com.cn/problem/CF600E
// 测试链接 : https://codeforces.com/problemset/problem/600/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n;
//int color[MAXN];
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cnt = 0;
//
//int fa[MAXN];
//int siz[MAXN];
//int son[MAXN];
//
//int colorCnt[MAXN];
//int maxCnt[MAXN];
//long long ans[MAXN];
//
//void addEdge(int u, int v) {
//    nxt[++cnt] = head[u];
//    to[cnt] = v;
//    head[u] = cnt;
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
//void effect(int u, int h) {
//    colorCnt[color[u]]++;
//    if (colorCnt[color[u]] == maxCnt[h]) {
//        ans[h] += color[u];
//    } else if (colorCnt[color[u]] > maxCnt[h]) {
//        maxCnt[h] = colorCnt[color[u]];
//        ans[h] = color[u];
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u]) {
//            effect(v, h);
//        }
//    }
//}
//
//void cancle(int u) {
//    colorCnt[color[u]] = 0;
//    maxCnt[u] = 0;
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u]) {
//            cancle(v);
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
//    maxCnt[u] = maxCnt[son[u]];
//    ans[u] = ans[son[u]];
//    colorCnt[color[u]]++;
//    if (colorCnt[color[u]] == maxCnt[u]) {
//        ans[u] += color[u];
//    } else if (colorCnt[color[u]] > maxCnt[u]) {
//        maxCnt[u] = colorCnt[color[u]];
//        ans[u] = color[u];
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            effect(v, u);
//        }
//    }
//    if (keep == 0) {
//        cancle(u);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> color[i];
//    }
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    dfs1(1, 0);
//    dfs2(1, 1);
//    for (int i = 1; i <= n; i++) {
//        cout << ans[i] << " ";
//    }
//    cout << "\n";
//    return 0;
//}