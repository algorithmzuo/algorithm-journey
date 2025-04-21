package class166;

// 边的涂色，C++版
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
//
//int pos[MAXN];
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
//    for (int ei = head[i]; ei > 0; ei = nxt[ei]) {
//        int color = c[qid[ei]];
//        int x = u[e[qid[ei]]], xn = x + n, fx = find(color, x), fxn = find(color, xn);
//        int y = v[e[qid[ei]]], yn = y + n, fy = find(color, y), fyn = find(color, yn);
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
//            c[l] = pos[e[l]];
//        } else {
//            ans[l] = true;
//            pos[e[l]] = c[l];
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
//        pos[i] = q + 1;
//    }
//    for (int i = q; i >= 1; i--) {
//        if (i + 1 <= pos[e[i]] - 1) {
//            add(i + 1, pos[e[i]] - 1, i, 1, q, 1);
//        }
//        pos[e[i]] = i;
//    }
//    for (int i = 1; i <= m; i++) {
//        pos[i] = 0;
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