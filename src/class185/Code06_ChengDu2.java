package class185;

// 成都七中，C++版
// 树上有n个点，每个点给定颜色，给定n-1条边
// 一共m条查询，查询之间不会相互影响，格式如下
// 查询 l r x : 只保留编号在[l, r]的节点，打印点x所在连通块的颜色数量
// 1 <= 所有数据 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5311
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int l, r, color;
//};
//
//struct Ques {
//    int l, r, qid;
//};
//
//bool NodeCmp(Node x, Node y) {
//    return x.r < y.r;
//}
//
//bool QuesCmp(Ques x, Ques y) {
//    return x.r < y.r;
//}
//
//const int MAXN = 100001;
//int n, m;
//int color[MAXN];
//
//int headg[MAXN];
//int nxtg[MAXN << 1];
//int tog[MAXN << 1];
//int cntg;
//
//int headq[MAXN];
//int nxtq[MAXN];
//int ql[MAXN];
//int qr[MAXN];
//int qid[MAXN];
//int cntq;
//
//bool vis[MAXN];
//int siz[MAXN];
//
//Node nodeArr[MAXN];
//int cntNode;
//
//Ques quesArr[MAXN];
//int cntQues;
//
//int pos[MAXN];
//int tree[MAXN];
//int ans[MAXN];
//
//void addEdge(int u, int v) {
//    nxtg[++cntg] = headg[u];
//    tog[cntg] = v;
//    headg[u] = cntg;
//}
//
//void addQuery(int u, int l, int r, int id) {
//    nxtq[++cntq] = headq[u];
//    ql[cntq] = l;
//    qr[cntq] = r;
//    qid[cntq] = id;
//    headq[u] = cntq;
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int v) {
//    if (i <= 0) {
//        return;
//    }
//    while (i <= n) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//int sum(int i) {
//    int ret = 0;
//    while (i > 0) {
//        ret += tree[i];
//        i -= lowbit(i);
//    }
//    return ret;
//}
//
//int query(int l, int r) {
//    return sum(r) - sum(l - 1);
//}
//
//void getSize(int u, int fa) {
//    siz[u] = 1;
//    for (int e = headg[u]; e > 0; e = nxtg[e]) {
//        int v = tog[e];
//        if (v != fa && !vis[v]) {
//            getSize(v, u);
//            siz[u] += siz[v];
//        }
//    }
//}
//
//int getCentroid(int u, int fa) {
//    getSize(u, fa);
//    int half = siz[u] >> 1;
//    bool find = false;
//    while (!find) {
//        find = true;
//        for (int e = headg[u]; e > 0; e = nxtg[e]) {
//            int v = tog[e];
//            if (v != fa && !vis[v] && siz[v] > half) {
//                fa = u;
//                u = v;
//                find = false;
//                break;
//            }
//        }
//    }
//    return u;
//}
//
//void dfs(int u, int fa, int nl, int nr) {
//    nodeArr[++cntNode] = { nl, nr, color[u] };
//    for (int e = headq[u]; e > 0; e = nxtq[e]) {
//        if (ql[e] <= nl && nr <= qr[e]) {
//            quesArr[++cntQues] = { ql[e], qr[e], qid[e] };
//        }
//    }
//    for (int e = headg[u]; e > 0; e = nxtg[e]) {
//        int v = tog[e];
//        if (v != fa && !vis[v]) {
//            dfs(v, u, min(v, nl), max(v, nr));
//        }
//    }
//}
//
//void calc(int u) {
//    cntNode = 0;
//    cntQues = 0;
//    dfs(u, 0, u, u);
//    sort(nodeArr + 1, nodeArr + cntNode + 1, NodeCmp);
//    sort(quesArr + 1, quesArr + cntQues + 1, QuesCmp);
//    for (int i = 1, j = 1; i <= cntQues; i++) {
//        while (j <= cntNode && nodeArr[j].r <= quesArr[i].r) {
//            if (nodeArr[j].l > pos[nodeArr[j].color]) {
//                add(pos[nodeArr[j].color], -1);
//                pos[nodeArr[j].color] = nodeArr[j].l;
//                add(pos[nodeArr[j].color], 1);
//            }
//            j++;
//        }
//        ans[quesArr[i].qid] = max(ans[quesArr[i].qid], query(quesArr[i].l, n));
//    }
//    for (int i = 1; i <= cntNode; i++) {
//        add(pos[nodeArr[i].color], -1);
//        pos[nodeArr[i].color] = 0;
//    }
//}
//
//void solve(int u) {
//    vis[u] = true;
//    calc(u);
//    for (int e = headg[u]; e > 0; e = nxtg[e]) {
//        int v = tog[e];
//        if (!vis[v]) {
//            solve(getCentroid(v, u));
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> color[i];
//    }
//    for (int i = 1, u, v; i < n; i++) {
//        cin >> u >> v;
//        addEdge(u, v);
//        addEdge(v, u);
//    }
//    for (int i = 1, l, r, x; i <= m; i++) {
//        cin >> l >> r >> x;
//        addQuery(x, l, r, i);
//    }
//    solve(getCentroid(1, 0));
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}