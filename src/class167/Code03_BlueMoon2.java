package class167;

// 贪玩蓝月，C++版
// 测试链接 : https://loj.ac/p/6515
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXM = 50001;
//const int MAXP = 501;
//const int MAXT = 1000001;
//const long long INF = 1000000000001LL;
//
//int m, p;
//
//int op[MAXM];
//int x[MAXM];
//int y[MAXM];
//
//int head[MAXM << 2];
//int nxt[MAXT];
//int tow[MAXT];
//int tov[MAXT];
//int cnt = 0;
//
//int used = 0;
//long long dp[MAXM][MAXP];
//long long ans[MAXM];
//
//void addEdge(int i, int w, int v) {
//    nxt[++cnt] = head[i];
//    tow[cnt] = w;
//    tov[cnt] = v;
//    head[i] = cnt;
//}
//
//void add(int jobl, int jobr, int jobw, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addEdge(i, jobw, jobv);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            add(jobl, jobr, jobw, jobv, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            add(jobl, jobr, jobw, jobv, mid + 1, r, i << 1 | 1);
//        }
//    }
//}
//
//void dfs(int l, int r, int i) {
//    int siz = 0;
//    for (int e = head[i], w, v; e > 0; e = nxt[e]) {
//        w = tow[e];
//        v = tov[e];
//        for (int j = 0; j < p; j++) {
//            dp[used + siz + 1][j] = dp[used + siz][j];
//        }
//        for (int j = 0; j < p; j++) {
//            if (dp[used + siz][j] != -INF) {
//                int nj = (j + w) % p;
//                dp[used + siz + 1][nj] = max(dp[used + siz + 1][nj], dp[used + siz][j] + v);
//            }
//        }
//        siz++;
//    }
//    used += siz;
//    if (l == r) {
//        if (op[l] == 5) {
//            long long ret = -INF;
//            for (int j = x[l]; j <= y[l]; j++) {
//                ret = max(ret, dp[used][j]);
//            }
//            ans[l] = (ret == -INF ? -1 : ret);
//        }
//    } else {
//        int mid = (l + r) >> 1;
//        dfs(l, mid, i << 1);
//        dfs(mid + 1, r, i << 1 | 1);
//    }
//    used -= siz;
//}
//
//void prepare() {
//    deque<array<int,3>> dq;
//    for (int i = 1; i <= m; i++) {
//        if (op[i] == 1) {
//            dq.push_front({x[i] % p, y[i], i});
//        } else if (op[i] == 2) {
//            dq.push_back({x[i] % p, y[i], i});
//        } else if (op[i] == 3) {
//            auto data = dq.front();
//            add(data[2], i - 1, data[0], data[1], 1, m, 1);
//            dq.pop_front();
//        } else if (op[i] == 4) {
//            auto data = dq.back();
//            add(data[2], i - 1, data[0], data[1], 1, m, 1);
//            dq.pop_back();
//        }
//    }
//    while (!dq.empty()) {
//        auto data = dq.front();
//        add(data[2], m, data[0], data[1], 1, m, 1);
//        dq.pop_front();
//    }
//    for (int i = 0; i < MAXM; i++) {
//        for (int j = 0; j < MAXP; j++) {
//            dp[i][j] = -INF;
//        }
//    }
//    dp[0][0] = 0;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int tmp;
//    cin >> tmp;
//    cin >> m >> p;
//    string t;
//    for (int i = 1; i <= m; i++) {
//        cin >> t;
//        if (t == "IF") {
//            op[i] = 1;
//            cin >> x[i] >> y[i];
//        } else if (t == "IG") {
//            op[i] = 2;
//            cin >> x[i] >> y[i];
//        } else if (t == "DF") {
//            op[i] = 3;
//        } else if (t == "DG") {
//            op[i] = 4;
//        } else {
//            op[i] = 5;
//            cin >> x[i] >> y[i];
//        }
//    }
//    prepare();
//    dfs(1, m, 1);
//    for (int i = 1; i <= m; i++) {
//        if (op[i] == 5) {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}