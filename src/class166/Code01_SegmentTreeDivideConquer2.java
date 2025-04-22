package class166;

// 线段树分治模版题，C++版
// 一共有n个节点，一共有m条操作，每条操作是如下三种类型中的一种
// 操作 0 x y : 点x和点y之间一定没有边，现在增加一条边
// 操作 1 x y : 点x和点y之间一定存在边，现在删除这条边
// 操作 2 x y : 查询点x和点y是否联通
// 1 <= n <= 5000
// 1 <= m <= 500000
// 不强制在线，可以离线处理
// 测试链接 : https://loj.ac/p/121
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 5001;
//const int MAXM = 500001;
//const int MAXT = 5000001;
//
//int n, m;
//
//int op[MAXM];
//int u[MAXM];
//int v[MAXM];
//
//int last[MAXN][MAXN];
//
//int father[MAXN];
//int siz[MAXN];
//int rollback[MAXN][2];
//int opsize = 0;
//
//int head[MAXM << 2];
//int nxt[MAXT];
//int tox[MAXT];
//int toy[MAXT];
//int cnt = 0;
//
//bool ans[MAXM];
//
//void addEdge(int i, int x, int y) {
//    nxt[++cnt] = head[i];
//    tox[cnt] = x;
//    toy[cnt] = y;
//    head[i] = cnt;
//}
//
//int find(int i) {
//    while (i != father[i]) {
//        i = father[i];
//    }
//    return i;
//}
//
//void Union(int x, int y) {
//    int fx = find(x);
//    int fy = find(y);
//    if (siz[fx] < siz[fy]) {
//        int tmp = fx;
//        fx = fy;
//        fy = tmp;
//    }
//    father[fy] = fx;
//    siz[fx] += siz[fy];
//    rollback[++opsize][0] = fx;
//    rollback[opsize][1] = fy;
//}
//
//void undo() {
//    int fx = rollback[opsize][0];
//    int fy = rollback[opsize--][1];
//    father[fy] = fy;
//    siz[fx] -= siz[fy];
//}
//
//void add(int jobl, int jobr, int jobx, int joby, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addEdge(i, jobx, joby);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            add(jobl, jobr, jobx, joby, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            add(jobl, jobr, jobx, joby, mid + 1, r, i << 1 | 1);
//        }
//    }
//}
//
//void dfs(int l, int r, int i) {
//    int unionCnt = 0;
//    for (int ei = head[i], x, y, fx, fy; ei > 0; ei = nxt[ei]) {
//        x = tox[ei];
//        y = toy[ei];
//        fx = find(x);
//        fy = find(y);
//        if (fx != fy) {
//            Union(fx, fy);
//            unionCnt++;
//        }
//    }
//    if (l == r) {
//        if (op[l] == 2) {
//            ans[l] = find(u[l]) == find(v[l]);
//        }
//    } else {
//        int mid = (l + r) >> 1;
//        dfs(l, mid, i << 1);
//        dfs(mid + 1, r, i << 1 | 1);
//    }
//    for (int j = 1; j <= unionCnt; j++) {
//        undo();
//    }
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        father[i] = i;
//        siz[i] = 1;
//    }
//    for (int i = 1, t, x, y; i <= m; i++) {
//        t = op[i];
//        x = u[i];
//        y = v[i];
//        if (t == 0) {
//            last[x][y] = i;
//        } else if (t == 1) {
//            add(last[x][y], i - 1, x, y, 1, m, 1);
//            last[x][y] = 0;
//        }
//    }
//    for (int x = 1; x <= n; x++) {
//        for (int y = x + 1; y <= n; y++) {
//            if (last[x][y] != 0) {
//                add(last[x][y], m, x, y, 1, m, 1);
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, t, x, y; i <= m; i++) {
//        cin >> t >> x >> y;
//        op[i] = t;
//        u[i] = min(x, y);
//        v[i] = max(x, y);
//    }
//    prepare();
//    dfs(1, m, 1);
//    for (int i = 1; i <= m; i++) {
//        if (op[i] == 2) {
//            if (ans[i]) {
//                cout << "Y" << "\n";
//            } else {
//                cout << "N" << "\n";
//            }
//        }
//    }
//    return 0;
//}