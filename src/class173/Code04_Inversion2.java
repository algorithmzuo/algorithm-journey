package class173;

// 区间逆序对，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5046
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例
// 这道题比较卡常，C++实现也需要优化常数，比如快读
// 正式比赛不卡常

//#include <bits/stdc++.h>
//
//using namespace std;
//
//char buf[1000000], *p1 = buf, *p2 = buf;
//
//inline char getChar() {
//    return p1 == p2 && (p2 = (p1 = buf) + fread(buf, 1, 1000000, stdin), p1 == p2) ? EOF : *p1++;
//}
//
//inline int read() {
//    int s = 0;
//    char c = getChar();
//    while (!isdigit(c)) {
//        c = getChar();
//    }
//    while (isdigit(c)) {
//        s = s * 10 + c - '0';
//        c = getChar();
//    }
//    return s;
//}
//
//struct Node {
//    int v, i;
//};
//
//bool NodeCmp(Node &a, Node &b) {
//    return a.v != b.v ? a.v < b.v : a.i < b.i;
//}
//
//const int MAXN = 100001;
//const int MAXB = 701;
//int n, m;
//int arr[MAXN];
//Node sortv[MAXN];
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//
//int tree[MAXN];
//int pre[MAXN];
//int suf[MAXN];
//int cnt[MAXB][MAXN];
//long long dp[MAXB][MAXB];
//
//inline int lowbit(int i) {
//    return i & -i;
//}
//
//inline void add(int i, int v) {
//    while (i <= n) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//inline int sum(int i) {
//    int ret = 0;
//    while (i > 0) {
//        ret += tree[i];
//        i -= lowbit(i);
//    }
//    return ret;
//}
//
//inline int calc(int x, int xl, int xr, int y, int yl, int yr) {
//    int ans = 0;
//    for (int p1 = bl[x], p2 = bl[y] - 1, tmp = 0; p1 <= br[x]; p1++) {
//        if (xl <= sortv[p1].i && sortv[p1].i <= xr) {
//            while (p2 + 1 <= br[y] && sortv[p1].v > sortv[p2 + 1].v) {
//                p2++;
//                if (yl <= sortv[p2].i && sortv[p2].i <= yr) {
//                    tmp++;
//                }
//            }
//            ans += tmp;
//        }
//    }
//    return ans;
//}
//
//long long query(int l, int r) {
//    long long ans = 0;
//    int lb = bi[l], rb = bi[r];
//    if (lb == rb) {
//        if (l == bl[lb]) {
//            ans = pre[r];
//        } else {
//            ans = pre[r] - pre[l - 1] - calc(lb, 1, l - 1, lb, l, r);
//        }
//    } else {
//        for (int i = l; i <= br[lb]; i++) {
//            ans += cnt[rb - 1][arr[i]] - cnt[lb][arr[i]];
//        }
//        for (int i = bl[rb]; i <= r; i++) {
//            ans += br[rb - 1] - bl[lb + 1] + 1 - cnt[rb - 1][arr[i]] + cnt[lb][arr[i]];
//        }
//        ans += dp[lb + 1][rb - 1] + suf[l] + pre[r] + calc(lb, l, br[lb], rb, bl[rb], r);
//    }
//    return ans;
//}
//
//void prepare() {
//    blen = (int)(sqrt(n) / 2);
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1; i <= n; i++) bi[i] = (i - 1) / blen + 1;
//    for (int i = 1; i <= bnum; i++) {
//        bl[i] = (i - 1) * blen + 1;
//        br[i] = min(i * blen, n);
//    }
//    for (int i = 1; i <= n; i++) {
//        sortv[i].v = arr[i];
//        sortv[i].i = i;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        sort(sortv + bl[i], sortv + br[i] + 1, NodeCmp);
//    }
//    for (int i = 1; i <= bnum; i++) {
//        for (int j = bl[i]; j <= br[i]; j++) {
//            cnt[i][arr[j]]++;
//            if (j != bl[i]) {
//                pre[j] = pre[j - 1] + sum(n) - sum(arr[j]);
//            }
//            add(arr[j], 1);
//        }
//        for (int j = bl[i]; j <= br[i]; j++) {
//            add(arr[j], -1);
//        }
//        for (int j = br[i]; j >= bl[i]; j--) {
//            if (j != br[i]) {
//                suf[j] = suf[j + 1] + sum(arr[j]);
//            }
//            add(arr[j], 1);
//        }
//        for (int j = bl[i]; j <= br[i]; j++) {
//            add(arr[j], -1);
//        }
//        int tmp = 0;
//        for (int j = 1; j <= n; j++) {
//            tmp += cnt[i][j];
//            cnt[i][j] = tmp + cnt[i - 1][j];
//        }
//        dp[i][i] = pre[br[i]];
//    }
//    for (int len = 2; len <= bnum; len++) {
//        for (int l = 1, r = l + len - 1; r <= bnum; l++, r++) {
//            dp[l][r] = dp[l + 1][r] + dp[l][r - 1] - dp[l + 1][r - 1] + calc(l, bl[l], br[l], r, bl[r], br[r]);
//        }
//    }
//}
//
//int main() {
//    n = read();
//    m = read();
//    for (int i = 1; i <= n; i++) {
//        arr[i] = read();
//    }
//    prepare();
//    long long lastAns = 0;
//    for (int i = 1, l, r; i <= m; i++) {
//        l = read() ^ lastAns;
//        r = read() ^ lastAns;
//        lastAns = query(l, r);
//        printf("%lld\n", lastAns);
//    }
//    return 0;
//}