package class161;

// 网络，C++版
// 一共有n个服务器，n-1条边，所有服务器连成一棵树
// 某两个服务器之间的路径上，可能接受一条请求，路径上的所有服务器都需要保存该请求的重要度
// 一共有m条操作，每条操作是如下3种类型中的一种，操作依次发生，第i条操作发生的时间为i
// 操作 0 a b v : a号服务器到b号服务器的路径上，增加了一个重要度为v的请求
// 操作 1 t     : 当初时间为t的操作，一定是增加请求的操作，现在这个请求结束了
// 操作 2 x     : 当前时间下，和x号服务器无关的所有请求中，打印最大的重要度
//                如果当前时间下，没有任何请求、或者所有请求都和x号服务器有关，打印-1
// 2 <= n <= 10^5
// 1 <= m <= 2 * 10^5
// 重要度 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3250
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXM = 200001;
//int n, m;
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
//int tree[MAXN];
//int events[MAXM][4];
//int lset[MAXM][4];
//int rset[MAXM][4];
//int sorted[MAXM];
//int s = 0;
//int ans[MAXM];
//int cntans = 0;
//
//int kth(int num) {
//    int left = 1, right = s, mid;
//    while (left <= right) {
//        mid = (left + right) / 2;
//        if (sorted[mid] == num) {
//            return mid;
//        } else if (sorted[mid] < num) {
//            left = mid + 1;
//        } else {
//            right = mid - 1;
//        }
//    }
//    return -1;
//}
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
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != f) {
//            dfs1(v, u);
//        }
//    }
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
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
//    if (son[u] == 0) {
//        return;
//    }
//    dfs2(son[u], t);
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (v != fa[u] && v != son[u]) {
//            dfs2(v, v);
//        }
//    }
//}
//
//void add(int i, int v) {
//    for (; i <= n; i += i & -i) {
//        tree[i] += v;
//    }
//}
//
//int query(int i) {
//    int sum = 0;
//    for (; i > 0; i -= i & -i) {
//        sum += tree[i];
//    }
//    return sum;
//}
//
//void linkAdd(int x, int y, int v) {
//    add(x, v);
//    add(y + 1, -v);
//}
//
//void pathAdd(int x, int y, int v) {
//    while (top[x] != top[y]) {
//        if (dep[top[x]] <= dep[top[y]]) {
//            linkAdd(dfn[top[y]], dfn[y], v);
//            y = fa[top[y]];
//        } else {
//            linkAdd(dfn[top[x]], dfn[x], v);
//            x = fa[top[x]];
//        }
//    }
//    linkAdd(min(dfn[x], dfn[y]), max(dfn[x], dfn[y]), v);
//}
//
//void clone(int *event1, int *event2) {
//    event1[0] = event2[0];
//    event1[1] = event2[1];
//    event1[2] = event2[2];
//    event1[3] = event2[3];
//}
//
//void prepare() {
//    dfs1(1, 0);
//    dfs2(1, 1);
//    sorted[0] = -1;
//    for (int i = 1; i <= m; i++) {
//        if (events[i][0] == 0) {
//        	sorted[++s] = events[i][3];
//        }
//    }
//    sort(sorted + 1, sorted + s + 1);
//    int len = 1;
//    for (int i = 2; i <= s; i++) {
//        if (sorted[len] != sorted[i]) {
//        	sorted[++len] = sorted[i];
//        }
//    }
//    s = len;
//    for (int i = 1; i <= m; i++) {
//        if (events[i][0] == 0) {
//        	events[i][3] = kth(events[i][3]);
//        } else if (events[i][0] == 1) {
//            clone(events[i], events[events[i][1]]);
//            events[i][0] = -1;
//        } else {
//        	events[i][0] = ++cntans;
//        }
//    }
//}
//
//void compute(int evtl, int evtr, int impl, int impr) {
//    if (impl == impr) {
//        for (int i = evtl; i <= evtr; i++) {
//            if (events[i][0] > 0) {
//                ans[events[i][0]] = impl;
//            }
//        }
//    } else {
//        int impm = (impl + impr) / 2;
//        int lsize = 0, rsize = 0, influence = 0;
//        for (int i = evtl; i <= evtr; i++) {
//            if (events[i][0] == 0) {
//                if (events[i][3] > impm) {
//                    pathAdd(events[i][1], events[i][2], 1);
//                    clone(rset[++rsize], events[i]);
//                    influence++;
//                } else {
//                    clone(lset[++lsize], events[i]);
//                }
//            } else if (events[i][0] == -1) {
//                if (events[i][3] > impm) {
//                    pathAdd(events[i][1], events[i][2], -1);
//                    clone(rset[++rsize], events[i]);
//                    influence--;
//                } else {
//                    clone(lset[++lsize], events[i]);
//                }
//            } else {
//                int sum = query(dfn[events[i][1]]);
//                if (sum != influence) {
//                    clone(rset[++rsize], events[i]);
//                } else {
//                    clone(lset[++lsize], events[i]);
//                }
//            }
//        }
//        for (int i = 1; i <= rsize; i++) {
//            if (rset[i][0] == 0 && rset[i][3] > impm) {
//                pathAdd(rset[i][1], rset[i][2], -1);
//            }
//            if (rset[i][0] == -1 && rset[i][3] > impm) {
//                pathAdd(rset[i][1], rset[i][2], 1);
//            }
//        }
//        for (int i = 1; i <= lsize; i++) {
//            clone(events[evtl + i - 1], lset[i]);
//        }
//        for (int i = 1; i <= rsize; i++) {
//            clone(events[evtl + lsize + i - 1], rset[i]);
//        }
//        compute(evtl, evtl + lsize - 1, impl, impm);
//        compute(evtl + lsize, evtr, impm + 1, impr);
//    }
//}
//
//int main() {
//    scanf("%d%d", &n, &m);
//    for(int i = 1; i < n; i++) {
//        int u, v;
//        scanf("%d%d", &u, &v);
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    for(int i = 1; i <= m; i++) {
//        scanf("%d", &events[i][0]);
//        scanf("%d", &events[i][1]);
//        if(events[i][0] == 0) {
//            scanf("%d%d", &events[i][2], &events[i][3]);
//        }
//    }
//    prepare();
//    compute(1, m, 0, s);
//    for(int i = 1; i <= cntans; i++) {
//        printf("%d\n", sorted[ans[i]]);
//    }
//    return 0;
//}