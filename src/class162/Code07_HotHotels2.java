package class162;

// 火热旅馆，C++版
// 一共有n个节点，给定n-1条边，所有节点连成一棵树
// 三个点构成一个点组(a, b, c)，打乱顺序依然认为是同一个点组
// 求树上有多少点组，两两之间的距离是一样的
// 1 <= n <= 10^5
// 答案一定在long类型范围内
// 测试链接 : https://www.luogu.com.cn/problem/P5904
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int cntg;
//
//int fa[MAXN];
//int son[MAXN];
//int len[MAXN];
//int cntd;
//
//int fid[MAXN];
//int gid[MAXN];
//long long f[MAXN];
//long long g[MAXN << 1];
//long long ans;
//
//void addEdge(int u, int v) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    head[u] = cntg;
//}
//
//void dfs1(int u, int f) {
//    fa[u] = f;
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            dfs1(v, u);
//        }
//    }
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != f) {
//            if (son[u] == 0 || len[son[u]] < len[v]) {
//                son[u] = v;
//            }
//        }
//    }
//    len[u] = len[son[u]] + 1;
//}
//
//void dfs2(int u, int t) {
//    fid[u] = cntd++;
//    if (son[u] == 0) {
//        gid[u] = fid[t] * 2;
//        return;
//    }
//    dfs2(son[u], t);
//    gid[u] = gid[son[u]] + 1;
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != son[u] && v != fa[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//void setf(int u, int i, long long v) {
//    f[fid[u] + i] = v;
//}
//
//long long getf(int u, int i) {
//    return f[fid[u] + i];
//}
//
//void setg(int u, int i, long long v) {
//    g[gid[u] + i] = v;
//}
//
//long long getg(int u, int i) {
//    return g[gid[u] + i];
//}
//
//void dfs3(int u) {
//    if (son[u] == 0) {
//        setf(u, 0, 1);
//        return;
//    }
//    dfs3(son[u]);
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != son[u] && v != fa[u]) {
//            dfs3(v);
//        }
//    }
//    ans += getg(u, 0);
//    setf(u, 0, getf(u, 0) + 1);
//    for (int e = head[u], v; e > 0; e = nxt[e]) {
//        v = to[e];
//        if (v != son[u] && v != fa[u]) {
//            for (int i = 0; i <= len[v]; i++) {
//                if (i > 0) {
//                    ans += getg(u, i) * getf(v, i - 1);
//                }
//                if (i + 1 < len[v]) {
//                    ans += getf(u, i) * getg(v, i + 1);
//                }
//            }
//            for (int i = 0; i <= len[v]; i++) {
//                if (i > 0) {
//                    setg(u, i, getg(u, i) + getf(u, i) * getf(v, i - 1));
//                }
//            }
//            for (int i = 0; i <= len[v]; i++) {
//                if (i < len[v]) {
//                    setg(u, i, getg(u, i) + getg(v, i + 1));
//                }
//                if (i > 0) {
//                    setf(u, i, getf(u, i) + getf(v, i - 1));
//                }
//            }
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
//    dfs1(1, 0);
//    dfs2(1, 1);
//    dfs3(1);
//    cout << ans << "\n";
//    return 0;
//}