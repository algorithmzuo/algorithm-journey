package class167;

// 贪玩蓝月，C++版
// 每件装备都有特征值w和战斗力v，放装备的背包是一个双端队列，只有背包中的装备是可选的
// 给定数值p，接下来有m条操作，每种操作是如下五种类型中的一种
// 操作 IF x y : 背包前端加入一件特征值x、战斗力y的装备
// 操作 IG x y : 背包后端加入一件特征值x、战斗力y的装备
// 操作 DF     : 删除背包前端的装备
// 操作 DG     : 删除背包后端的装备
// 操作 QU x y : 选择装备的特征值累加和 % p，必须在[x, y]范围，打印最大战斗力，无方案打印-1
// 1 <= m <= 5 * 10^4    1 <= p <= 500
// 0 <= 每件装备特征值、每件装备战斗力 <= 10^9
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
//const int DEEP = 20;
//int m, p;
//
//int op[MAXM];
//int x[MAXM];
//int y[MAXM];
//
//deque<array<int,3>> knapsack;
//
//int head[MAXM << 2];
//int nxt[MAXT];
//int tow[MAXT];
//int tov[MAXT];
//int cnt = 0;
//
//long long pre[MAXP];
//long long dp[MAXP];
//long long backup[DEEP][MAXP];
//
//long long ans[MAXM];
//
//void clone(long long* a, long long* b) {
//    for (int i = 0; i <= p; i++) {
//        a[i] = b[i];
//    }
//}
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
//void dfs(int l, int r, int i, int dep) {
//    clone(backup[dep], dp);
//    for (int e = head[i], w, v; e > 0; e = nxt[e]) {
//        w = tow[e];
//        v = tov[e];
//        clone(pre, dp);
//        for (int j = 0; j < p; j++) {
//            if (pre[j] != -1) {
//                dp[(j + w) % p] = max(dp[(j + w) % p], pre[j] + v);
//            }
//        }
//    }
//    if (l == r) {
//        if (op[l] == 5) {
//            long long ret = -1;
//            for (int j = x[l]; j <= y[l]; j++) {
//                ret = max(ret, dp[j]);
//            }
//            ans[l] = ret;
//        }
//    } else {
//        int mid = (l + r) >> 1;
//        dfs(l, mid, i << 1, dep + 1);
//        dfs(mid + 1, r, i << 1 | 1, dep + 1);
//    }
//    clone(dp, backup[dep]);
//}
//
//void prepare() {
//    array<int,3> equip;
//    for (int i = 1; i <= m; i++) {
//        if (op[i] == 1) {
//            knapsack.push_front({x[i] % p, y[i], i});
//        } else if (op[i] == 2) {
//            knapsack.push_back({x[i] % p, y[i], i});
//        } else if (op[i] == 3) {
//            equip = knapsack.front();
//            add(equip[2], i - 1, equip[0], equip[1], 1, m, 1);
//            knapsack.pop_front();
//        } else if (op[i] == 4) {
//            equip = knapsack.back();
//            add(equip[2], i - 1, equip[0], equip[1], 1, m, 1);
//            knapsack.pop_back();
//        }
//    }
//    while (!knapsack.empty()) {
//        equip = knapsack.front();
//        add(equip[2], m, equip[0], equip[1], 1, m, 1);
//        knapsack.pop_front();
//    }
//    for (int i = 0; i < p; i++) {
//        dp[i] = -1;
//    }
//    dp[0] = 0;
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
//    dfs(1, m, 1, 1);
//    for (int i = 1; i <= m; i++) {
//        if (op[i] == 5) {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}