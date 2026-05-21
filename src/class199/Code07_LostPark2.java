package class199;

// 迷失游乐园，C++版
// 图中有n个点、m条无向边，每条边有边权，图是普通树或者基环树
// 起点在所有节点中，等概率随机选择
// 每次从当前节点还没访问过的相邻节点中，等概率随机选择一个走过去
// 路径不能经过重复节点，如果无法继续走下去，那么路径终止
// 沿途经过的边权累加和叫做路径长度，计算路径长度的期望
// 1 <= n、m <= 10^5
// 如果存在环，环的节点数量 <= 20
// 测试链接 : https://www.luogu.com.cn/problem/P2081
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//int n, m;
//
//int head[MAXN];
//int nxt[MAXN << 1];
//int to[MAXN << 1];
//int weight[MAXN << 1];
//int cntg;
//
//int dfn[MAXN];
//int cntd;
//
//int from[MAXN];
//bool cycle[MAXN];
//
//int son[MAXN];
//double down[MAXN];
//double up[MAXN];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//}
//
//void dfs1(int u, int fa) {
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (v != fa) {
//            son[u]++;
//            dfs1(v, u);
//            down[u] += down[v] + w;
//        }
//    }
//    if (son[u] > 0) {
//        down[u] /= son[u];
//    }
//}
//
//void dfs2(int u, int fa) {
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (v != fa) {
//            if (u == 1) {
//                if (son[u] == 1) {
//                    up[v] = w;
//                } else {
//                    up[v] = w + (up[u] + down[u] * son[u] - down[v] - w) / (son[u] - 1);
//                }
//            } else {
//                up[v] = w + (up[u] + down[u] * son[u] - down[v] - w) / son[u];
//            }
//            dfs2(v, u);
//        }
//    }
//}
//
//double dpOnTree() {
//    dfs1(1, 0);
//    dfs2(1, 0);
//    double ans = down[1];
//    for (int i = 2; i <= n; i++) {
//        ans += (down[i] * son[i] + up[i]) / (son[i] + 1);
//    }
//    return ans / n;
//}
//
//void flag(int u, int preEdge) {
//    dfn[u] = ++cntd;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (e != (preEdge ^ 1)) {
//            if (dfn[v] == 0) {
//                from[v] = u;
//                flag(v, e);
//            } else if (dfn[u] < dfn[v]) {
//                cycle[u] = true;
//                for (int i = v; i != u; i = from[i]) {
//                    cycle[i] = true;
//                }
//            }
//        }
//    }
//}
//
//void dfs3(int u, int fa) {
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (v != fa && !cycle[v]) {
//            son[u]++;
//            dfs3(v, u);
//            down[u] += w + down[v];
//        }
//    }
//    if (son[u] > 0) {
//        down[u] /= son[u];
//    }
//}
//
//void dfs4(int u, int fa) {
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (v != fa && !cycle[v]) {
//            if (cycle[u]) {
//                up[v] = w + (up[u] * 2 + down[u] * son[u] - down[v] - w) / (son[u] + 1);
//            } else {
//                up[v] = w + (up[u] + down[u] * son[u] - down[v] - w) / son[u];
//            }
//            dfs4(v, u);
//        }
//    }
//}
//
//double around(int u, int fa, int start) {
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (cycle[v] && v != fa) {
//            if (v == start) {
//                return down[u];
//            } else {
//                return (around(v, u, start) + w + down[u] * son[u]) / (son[u] + 1);
//            }
//        }
//    }
//    return 0;
//}
//
//void walk(int u) {
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        int w = weight[e];
//        if (cycle[v]) {
//            up[u] += around(v, u, u) + w;
//        }
//    }
//    up[u] /= 2;
//}
//
//double dpOnGraph() {
//    flag(1, 0);
//    for (int i = 1; i <= n; i++) {
//        if (cycle[i]) {
//            dfs3(i, 0);
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (cycle[i]) {
//            walk(i);
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (cycle[i]) {
//            dfs4(i, 0);
//        }
//    }
//    double ans = 0;
//    for (int i = 1; i <= n; i++) {
//        if (cycle[i]) {
//            ans += (down[i] * son[i] + up[i] * 2) / (son[i] + 2);
//        } else {
//            ans += (down[i] * son[i] + up[i]) / (son[i] + 1);
//        }
//    }
//    return ans / n;
//}
//
//int main() {
//    scanf("%d%d", &n, &m);
//    cntg = 1;
//    for (int i = 1, u, v, w; i <= m; i++) {
//        scanf("%d%d%d", &u, &v, &w);
//        addEdge(u, v, w);
//        addEdge(v, u, w);
//    }
//    double ans;
//    if (m == n - 1) {
//        ans = dpOnTree();
//    } else {
//        ans = dpOnGraph();
//    }
//    printf("%.5f\n", ans);
//    return 0;
//}