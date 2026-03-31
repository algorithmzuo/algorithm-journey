package class195;

// 沙漠，C++版
// 一共n个数字，所有数字都在 1 ~ 10^9 的范围，这是范围说明
// 接下来给定s条设置说明，格式 x v ，表示第x个数的值确定是v
// 接下来给定m条关系说明，格式 l r k x1 x2 ... xk 含义如下
// 第l到第r个数字，其中有k个数字，分别是第x1、第x2 .. 第xk个数字
// 这k个数字中的每一个，都比剩下的(r - l + 1 - k)个数字要大，严格大于
// 根据上面的说明，找到没有矛盾的，给每个数字赋值的方案，任何一个方案即可
// 如果存在方案打印"TAK"，然后打印每个数字，不存在方案打印"NIE"
// 1 <= n、s <= 10^5
// 1 <= m <= 2 * 10^5
// 所有k的累加和 <= 3 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3588
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXT = MAXN * 5;
//const int MAXE = MAXN * 20;
//const int LIMIT = 1000000000;
//int n, s, m;
//
//int head[MAXT];
//int nxt[MAXE];
//int to[MAXE];
//int weight[MAXE];
//int cntg;
//
//int ls[MAXT];
//int rs[MAXT];
//int root;
//int cntt;
//
//int setv[MAXT];
//int indegree[MAXT];
//int dist[MAXT];
//int que[MAXT];
//
//void addEdge(int u, int v, int w) {
//    nxt[++cntg] = head[u];
//    to[cntg] = v;
//    weight[cntg] = w;
//    head[u] = cntg;
//    indegree[v]++;
//}
//
//int build(int l, int r) {
//    int rt;
//    if (l == r) {
//        rt = l;
//    } else {
//        rt = ++cntt;
//        int mid = (l + r) >> 1;
//        ls[rt] = build(l, mid);
//        rs[rt] = build(mid + 1, r);
//        addEdge(rt, ls[rt], 0);
//        addEdge(rt, rs[rt], 0);
//    }
//    return rt;
//}
//
//void xToRange(int jobx, int jobl, int jobr, int jobw, int l, int r, int i) {
//    if (jobl > jobr) {
//        return;
//    }
//    if (jobl <= l && r <= jobr) {
//        addEdge(jobx, i, jobw);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            xToRange(jobx, jobl, jobr, jobw, l, mid, ls[i]);
//        }
//        if (jobr > mid) {
//            xToRange(jobx, jobl, jobr, jobw, mid + 1, r, rs[i]);
//        }
//    }
//}
//
//bool topo() {
//    int qi = 1, qsiz = 0;
//    for (int i = 1; i <= cntt; i++) {
//        if (indegree[i] == 0) {
//            que[++qsiz] = i;
//        }
//        dist[i] = setv[i] == 0 ? LIMIT : setv[i];
//    }
//    while (qi <= qsiz) {
//        int u = que[qi++];
//        if (dist[u] < 1) {
//            return false;
//        }
//        for (int e = head[u]; e > 0; e = nxt[e]) {
//            int v = to[e];
//            int w = weight[e];
//            if (dist[v] > dist[u] + w) {
//                dist[v] = dist[u] + w;
//                if ((setv[v] != 0 && dist[v] < setv[v]) || dist[v] < 1) {
//                    return false;
//                }
//            }
//            if (--indegree[v] == 0) {
//                que[++qsiz] = v;
//            }
//        }
//    }
//    return qsiz == cntt;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> s >> m;
//    cntt = n;
//    root = build(1, n);
//    for (int i = 1; i <= s; i++) {
//        int x, v;
//        cin >> x >> v;
//        setv[x] = v;
//    }
//    for (int i = 1; i <= m; i++) {
//        int l, r, k;
//        cin >> l >> r >> k;
//        int vnode = ++cntt;
//        for (int j = 1; j <= k; j++) {
//            int x;
//            cin >> x;
//            addEdge(x, vnode, 0);
//            xToRange(vnode, l, x - 1, -1, 1, n, root);
//            l = x + 1;
//        }
//        xToRange(vnode, l, r, -1, 1, n, root);
//    }
//    bool check = topo();
//    if (check) {
//        cout << "TAK" << "\n";
//        for (int i = 1; i <= n; i++) {
//            cout << dist[i] << " ";
//        }
//        cout << "\n";
//    } else {
//        cout << "NIE" << "\n";
//    }
//    return 0;
//}