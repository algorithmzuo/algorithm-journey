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
//using ui = unsigned int;
//
//const int MAXN = 10001;
//const int MAXT = 500001;
//const int BIT = 10000;
//const int INTBIT = 32;
//const int LEN = BIT / INTBIT + 1;
//int n, q;
//
//ui help[LEN];
//ui path[LEN];
//ui ans[LEN];
//
//int head[MAXN << 2];
//int nxt[MAXT];
//int to[MAXT];
//int cnt = 0;
//
//void clear(ui* bitset) {
//    for (int i = 0; i < LEN; i++) {
//        bitset[i] = 0;
//    }
//}
//
//void clone(ui* set1, ui* set2) {
//    for (int i = 0; i < LEN; i++) {
//        set1[i] = set2[i];
//    }
//}
//
//int getBit(ui* bitset, int i) {
//    return (bitset[i / INTBIT] >> (i % INTBIT)) & 1;
//}
//
//void setBit(ui* bitset, int i, int v) {
//    if (v == 0) {
//        bitset[i / INTBIT] &= ~(1u << (i % INTBIT));
//    } else {
//        bitset[i / INTBIT] |= 1u << (i % INTBIT);
//    }
//}
//
//void bitOr(ui* set1, ui* set2) {
//    for (int i = 0; i < LEN; i++) {
//        set1[i] |= set2[i];
//    }
//}
//
//void bitLeft(ui* ret, ui* bitset, int left) {
//    clear(ret);
//    if (left >= BIT) {
//        return;
//    }
//    if (left <= 0) {
//        clone(ret, bitset);
//        return;
//    }
//    int shift = left / INTBIT;
//    int offset = left % INTBIT;
//    if (offset == 0) {
//        for (int i = LEN - 1; i >= shift; i--) {
//            ret[i] = bitset[i - shift];
//        }
//    } else {
//        int carry = INTBIT - offset;
//        for (int i = LEN - 1; i > shift; i--) {
//            ui high = bitset[i - shift] << offset;
//            ui low = bitset[i - shift - 1] >> carry;
//            ret[i] = high | low;
//        }
//        ret[shift] = bitset[0] << offset;
//    }
//    int extra = LEN * INTBIT - BIT - 1;
//    if (extra > 0) {
//        ui mask = ~0u >> extra;
//        ret[LEN - 1] &= mask;
//    }
//}
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
//void dfs(int l, int r, int i) {
//    ui backup[LEN];
//    clone(backup, path);
//    for (int e = head[i]; e > 0; e = nxt[e]) {
//        bitLeft(help, path, to[e]);
//        bitOr(path, help);
//    }
//    if (l == r) {
//        bitOr(ans, path);
//    } else {
//        int mid = (l + r) >> 1;
//        dfs(l, mid, i << 1);
//        dfs(mid + 1, r, i << 1 | 1);
//    }
//    clone(path, backup);
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
//    setBit(path, 0, 1);
//    dfs(1, n, 1);
//    int ansCnt = 0;
//    for (int i = 1; i <= n; i++) {
//        if (getBit(ans, i) == 1) {
//            ansCnt++;
//        }
//    }
//    cout << ansCnt << '\n';
//    for (int i = 1; i <= n; i++) {
//        if (getBit(ans, i) == 1) {
//            cout << i << ' ';
//        }
//    }
//    cout << '\n';
//    return 0;
//}