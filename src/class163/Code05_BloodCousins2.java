package class163;

// 表亲数量，C++版
// 测试链接 : https://www.luogu.com.cn/problem/CF208E
// 测试链接 : https://codeforces.com/problemset/problem/208/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXH = 20;
//int n, m;
//
//bool root[MAXN];
//int headg[MAXN];
//int nextg[MAXN << 1];
//int tog[MAXN << 1];
//int cntg;
//
//int headq[MAXN];
//int nextq[MAXN << 1];
//int idxq[MAXN << 1];
//int kthq[MAXN << 1];
//int cntq;
//
//int siz[MAXN];
//int dep[MAXN];
//int son[MAXN];
//int stjump[MAXN][MAXH];
//
//int nodeCnt[MAXN];
//int ans[MAXN];
//
//void addEdge(int u, int v) {
//    nextg[++cntg] = headg[u];
//    tog[cntg] = v;
//    headg[u] = cntg;
//}
//
//void addQuestion(int u, int idx, int kth) {
//    nextq[++cntq] = headq[u];
//    idxq[cntq] = idx;
//    kthq[cntq] = kth;
//    headq[u] = cntq;
//}
//
//int kAncestor(int u, int k) {
//    for (int p = MAXH - 1; p >= 0; p--) {
//        if (k >= (1 << p)) {
//            k -= (1 << p);
//            u = stjump[u][p];
//        }
//    }
//    return u;
//}
//
//void dfs1(int u, int fa) {
//    siz[u] = 1;
//    dep[u] = dep[fa] + 1;
//    stjump[u][0] = fa;
//    for (int p = 1; p < MAXH; p++) {
//        stjump[u][p] = stjump[ stjump[u][p - 1] ][p - 1];
//    }
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        dfs1(tog[e], u);
//    }
//    for (int e = headg[u], v; e > 0; e = nextg[e]) {
//        v = tog[e];
//        siz[u] += siz[v];
//        if (son[u] == 0 || siz[son[u]] < siz[v]) {
//            son[u] = v;
//        }
//    }
//}
//
//void effect(int u) {
//    nodeCnt[dep[u]]++;
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        effect(tog[e]);
//    }
//}
//
//void cancle(int u) {
//    nodeCnt[dep[u]]--;
//    for (int e = headg[u]; e > 0; e = nextg[e]) {
//        cancle(tog[e]);
//    }
//}
//
//void dfs2(int u, int keep) {
//    for (int e = headg[u], v; e > 0; e = nextg[e]) {
//        v = tog[e];
//        if (v != son[u]) {
//            dfs2(v, 0);
//        }
//    }
//    if (son[u] != 0) {
//        dfs2(son[u], 1);
//    }
//    nodeCnt[dep[u]]++;
//    for (int e = headg[u], v; e > 0; e = nextg[e]) {
//        v = tog[e];
//        if (v != son[u]) {
//            effect(v);
//        }
//    }
//    for (int i = headq[u]; i > 0; i = nextq[i]) {
//    	ans[idxq[i]] = nodeCnt[dep[u] + kthq[i]];
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
//    for (int i = 1, father; i <= n; i++) {
//        cin >> father;
//        if (father == 0) {
//        	root[i] = true;
//        } else {
//            addEdge(father, i);
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (root[i]) {
//            dfs1(i, 0);
//        }
//    }
//    cin >> m;
//    for (int i = 1, u, k, kfather; i <= m; i++) {
//        cin >> u >> k;
//        kfather = kAncestor(u, k);
//        if (kfather != 0) {
//            addQuestion(kfather, i, k);
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        if (root[i]) {
//            dfs2(i, 0);
//        }
//    }
//    for (int i = 1; i <= m; i++) {
//        if (ans[i] == 0) {
//            cout << "0 ";
//        } else {
//            cout << ans[i] - 1 << " ";
//        }
//    }
//    cout << "\n";
//    return 0;
//}