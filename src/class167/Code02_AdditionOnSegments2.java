package class167;

// 能达到的最大值，C++版
// 测试链接 : https://www.luogu.com.cn/problem/CF981E
// 测试链接 : https://codeforces.com/problemset/problem/981/E
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 10001;
//const int MAXT = 500001;
//const int BIT = 10000;
//const int DEEP = 20;
//
//typedef bitset<BIT + 1> bs;
//
//int n, q;
//int head[MAXN << 2];
//int nxt[MAXT];
//int to[MAXT];
//int cnt = 0;
//
//bs path;
//bs backup[DEEP];
//bs ans;
//
//void addEdge(int i, int v) {
//    nxt[++cnt] = head[i];
//    to[cnt] = v;
//    head[i] = cnt;
//}
//
//void add(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        addEdge(i, jobv);
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobl <= mid) {
//            add(jobl, jobr, jobv, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
//        }
//    }
//}
//
//void dfs(int l, int r, int i, int dep) {
//    backup[dep] = path;
//    for (int e = head[i]; e > 0; e = nxt[e]) {
//        path |= path << to[e];
//    }
//    if (l == r) {
//        ans |= path;
//    } else {
//        int mid = (l + r) >> 1;
//        dfs(l, mid, i << 1, dep + 1);
//        dfs(mid + 1, r, i << 1 | 1, dep + 1);
//    }
//    path = backup[dep];
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> q;
//    for (int i = 1, l, r, k; i <= q; i++) {
//        cin >> l >> r >> k;
//        add(l, r, k, 1, n, 1);
//    }
//    path[0] = 1;
//    dfs(1, n, 1, 1);
//    int ansCnt = 0;
//    for (int i = 1; i <= n; i++) {
//        if (ans[i] == 1) {
//            ansCnt++;
//        }
//    }
//    cout << ansCnt << '\n';
//    for (int i = 1; i <= n; i++) {
//        if (ans[i] == 1) {
//            cout << i << ' ';
//        }
//    }
//    cout << '\n';
//    return 0;
//}