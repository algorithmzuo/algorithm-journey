package class166;

// 给边涂色，C++版
// 一共有n个点，给定m条无向边，一开始每条边无颜色，一共有k种颜色
// 合法状态的定义为，仅保留染成k种颜色中的任何一种颜色的边，图都是一张二分图
// 一共有q条操作，每条操作格式如下
// 操作 e c : 第e条边，现在要涂成c颜色
//           如果执行此操作之后，整张图还是合法状态，那么执行并打印"YES"
//           如果执行此操作之后，整张图不再是合法状态，那么不执行并打印"NO"
// 1 <= n、m、q <= 5 * 10^5    1 <= k <= 50
// 测试链接 : https://www.luogu.com.cn/problem/CF576E
// 测试链接 : https://codeforces.com/problemset/problem/576/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 500001;
//const int MAXK = 51;
//const int MAXT = 10000001;
//int n, m, k, q;
//
//int u[MAXN];
//int v[MAXN];
//int e[MAXN];
//int c[MAXN];
//int post[MAXN];
//
//int father[MAXK][MAXN << 1];
//int siz[MAXK][MAXN << 1];
//int rollback[MAXN << 1][3];
//int opsize = 0;
//
//int head[MAXN << 2];
//int nxt[MAXT];
//int qid[MAXT];
//int cnt = 0;
//
//int lastColor[MAXN];
//
//bool ans[MAXN];
//
//void addEdge(int i, int qi) {
//    nxt[++cnt] = head[i];
//    qid[cnt] = qi;
//    head[i] = cnt;
//}
//
//int find(int color, int i) {
//    while (i != father[color][i]) {
//        i = father[color][i];
//    }
//    return i;
//}
//
//void Union(int color, int x, int y) {
//    int fx = find(color, x);
//    int fy = find(color, y);
//    if (siz[color][fx] < siz[color][fy]) {
//        int tmp = fx;
//        fx = fy;
//        fy = tmp;
//    }
//    father[color][fy] = fx;
//    siz[color][fx] += siz[color][fy];
//    rollback[++opsize][0] = color;
//    rollback[opsize][1] = fx;
//    rollback[opsize][2] = fy;
//}
//
//void undo() {
//    int color = rollback[opsize][0];
//    int fx = rollback[opsize][1];
//    int fy = rollback[opsize--][2];
//    father[color][fy] = fy;
//    siz[color][fx] -= siz[color][fy];
//}
//
//void add(int jobl, int jobr, int jobq, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addEdge(i, jobq);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            add(jobl, jobr, jobq, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            add(jobl, jobr, jobq, mid + 1, r, i << 1 | 1);
//        }
//    }
//}
//
//void dfs(int l, int r, int i) {
//    int unionCnt = 0;
//    int color, x, y, xn, yn, fx, fy, fxn, fyn;
//    for (int ei = head[i]; ei > 0; ei = nxt[ei]) {
//        color = c[qid[ei]];
//        x = u[e[qid[ei]]];
//        y = v[e[qid[ei]]];
//        xn = x + n;
//        yn = y + n;
//        fx = find(color, x);
//        fy = find(color, y);
//        fxn = find(color, xn);
//        fyn = find(color, yn);
//        if (fx != fyn) {
//            Union(color, fx, fyn);
//            unionCnt++;
//        }
//        if (fy != fxn) {
//            Union(color, fy, fxn);
//            unionCnt++;
//        }
//    }
//    if (l == r) {
//        if (find(c[l], u[e[l]]) == find(c[l], v[e[l]])) {
//            ans[l] = false;
//            c[l] = lastColor[e[l]];
//        } else {
//            ans[l] = true;
//            lastColor[e[l]] = c[l];
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
//    for (int color = 1; color <= k; color++) {
//        for (int i = 1; i <= n; i++) {
//            father[color][i] = i;
//            father[color][i + n] = i + n;
//            siz[color][i] = 1;
//            siz[color][i + n] = 1;
//        }
//    }
//    for (int i = 1; i <= m; i++) {
//        post[i] = q + 1;
//    }
//    for (int i = q; i >= 1; i--) {
//        if (i + 1 <= post[e[i]] - 1) {
//            add(i + 1, post[e[i]] - 1, i, 1, q, 1);
//        }
//        post[e[i]] = i;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> k >> q;
//    for (int i = 1; i <= m; i++) {
//        cin >> u[i] >> v[i];
//    }
//    for (int i = 1; i <= q; i++) {
//        cin >> e[i] >> c[i];
//    }
//    prepare();
//    dfs(1, q, 1);
//    for (int i = 1; i <= q; i++) {
//        if (ans[i]) {
//            cout << "YES" << "\n";
//        } else {
//            cout << "NO" << "\n";
//        }
//    }
//    return 0;
//}