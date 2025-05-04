package class167;

// 八纵八横，C++版
// 一共有n个点，给定m条边，每条边的边权，用01字符串表达
// 初始的m条边永不删除，接下来有q条操作，每种操作是如下三种类型中的一种
// 操作 Add x y z  : 加入点x到点y的边，边权是z，z为01字符串，第k条添加操作，边的编号为k
// 操作 Cancel k   : 删除编号为k的边
// 操作 Change k z : 编号为k的边，边权修改成z，z为01字符串
// 从1号点出发，最后回到1号点，边随便走，沿途所有边的边权异或起来
// 打印只有初始m条边的情况下，异或最大值为多少，每一条操作结束后，都打印异或最大值为多少
// 1 <= n、m <= 500    0 <= q <= 1000    1 <= 边权字符串长度 <= 1000
// 测试链接 : https://www.luogu.com.cn/problem/P3733
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 501;
//const int MAXQ = 1001;
//const int MAXT = 10001;
//const int BIT  = 999;
//
//typedef bitset<BIT + 1> bs;
//
//int n, m, q;
//int x[MAXQ];
//int y[MAXQ];
//bs w[MAXQ];
//int edgeCnt = 0;
//int last[MAXQ];
//
//bs basis[BIT + 1];
//int inspos[BIT + 1];
//int basiz = 0;
//
//int father[MAXN];
//int siz[MAXN];
//bs eor[MAXN];
//int rollback[MAXT][2];
//int opsize = 0;
//
//int head[MAXQ << 2];
//int nxt[MAXT];
//int tox[MAXT];
//int toy[MAXT];
//bs tow[MAXT];
//int cnt = 0;
//
//bs ans[MAXQ];
//
//void insert(bs& num) {
//    for (int i = BIT; i >= 0; --i) {
//        if (num[i] == 1) {
//            if (basis[i][i] == 0) {
//                basis[i] = num;
//                inspos[basiz++] = i;
//                return;
//            }
//            num ^= basis[i];
//        }
//    }
//}
//
//bs maxEor() {
//    bs ret;
//    for (int i = BIT; i >= 0; i--) {
//        if (ret[i] == 0 && basis[i][i] == 1) {
//            ret ^= basis[i];
//        }
//    }
//    return ret;
//}
//
//void cancel(int oldsiz) {
//    while (basiz > oldsiz) {
//        basis[inspos[--basiz]].reset();
//    }
//}
//
//int find(int v) {
//    while (v != father[v]) {
//        v = father[v];
//    }
//    return v;
//}
//
//bs getEor(int v) {
//    bs ret;
//    while (v != father[v]) {
//        ret ^= eor[v];
//        v = father[v];
//    }
//    return ret;
//}
//
//bool Union(int u, int v, bs& w) {
//    int fu = find(u);
//    int fv = find(v);
//    bs weight = getEor(u) ^ getEor(v) ^ w;
//    if (fu == fv) {
//        insert(weight);
//        return false;
//    }
//    if (siz[fu] < siz[fv]) {
//        int tmp = fu;
//        fu = fv;
//        fv = tmp;
//    }
//    father[fv] = fu;
//    siz[fu] += siz[fv];
//    eor[fv] = weight;
//    rollback[++opsize][0] = fu;
//    rollback[opsize][1] = fv;
//    return true;
//}
//
//void undo() {
//    int fu = rollback[opsize][0];
//    int fv = rollback[opsize--][1];
//    father[fv] = fv;
//    eor[fv].reset();
//    siz[fu] -= siz[fv];
//}
//
//void addEdge(int i, int u, int v, bs& w) {
//    nxt[++cnt] = head[i];
//    tox[cnt] = u;
//    toy[cnt] = v;
//    tow[cnt] = w;
//    head[i] = cnt;
//}
//
//void add(int jobl, int jobr, int jobx, int joby, bs& jobw, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addEdge(i, jobx, joby, jobw);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            add(jobl, jobr, jobx, joby, jobw, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            add(jobl, jobr, jobx, joby, jobw, mid + 1, r, i << 1 | 1);
//        }
//    }
//}
//
//void dfs(int l, int r, int i) {
//    int oldsiz = basiz;
//    int unionCnt = 0;
//    for (int e = head[i]; e; e = nxt[e]) {
//        if (Union(tox[e], toy[e], tow[e])) {
//            ++unionCnt;
//        }
//    }
//    if (l == r) {
//        ans[l] = maxEor();
//    } else {
//        int mid = (l + r) >> 1;
//        dfs(l, mid, i << 1);
//        dfs(mid + 1, r, i << 1 | 1);
//    }
//    cancel(oldsiz);
//    for (int k = 1; k <= unionCnt; k++) {
//        undo();
//    }
//}
//
//void print(const bs& ret) {
//    bool flag = false;
//    for (int i = BIT; i >= 0; i--) {
//        if (ret[i] == 1) {
//            flag = true;
//        }
//        if (flag) {
//            cout << ret[i];
//        }
//    }
//    if (!flag) {
//        cout << '0';
//    }
//    cout << '\n';
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> q;
//    for (int i = 0; i <= BIT; ++i) {
//        basis[i].reset();
//    }
//    for (int i = 1; i <= n; ++i) {
//        father[i] = i;
//        siz[i] = 1;
//        eor[i].reset();
//    }
//    int u, v;
//    bs weight;
//    for (int i = 1; i <= m; ++i) {
//        cin >> u >> v >> weight;
//        Union(u, v, weight);
//    }
//    ans[0] = maxEor();
//    string op;
//    int k;
//    for (int i = 1; i <= q; i++) {
//        cin >> op;
//        if (op == "Add") {
//            ++edgeCnt;
//            cin >> x[edgeCnt] >> y[edgeCnt] >> w[edgeCnt];
//            last[edgeCnt] = i;
//        } else if (op == "Cancel") {
//            cin >> k;
//            add(last[k], i - 1, x[k], y[k], w[k], 1, q, 1);
//            last[k] = 0;
//        } else {
//            cin >> k;
//            add(last[k], i - 1, x[k], y[k], w[k], 1, q, 1);
//            cin >> w[k];
//            last[k] = i;
//        }
//    }
//    for (int i = 1; i <= edgeCnt; i++) {
//        if (last[i] != 0) {
//            add(last[i], q, x[i], y[i], w[i], 1, q, 1);
//        }
//    }
//    if (q > 0) {
//        dfs(1, q, 1);
//    }
//    for (int i = 0; i <= q; i++) {
//        print(ans[i]);
//    }
//    return 0;
//}