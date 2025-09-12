package class178;

// 区间倍数二元组，C++版
// 给定一个长度为n的数组arr，下面给出倍数二元组的定义
// 如果arr[i]是arr[j]的倍数(>=1倍)，那么(i, j)就是一个倍数二元组
// 当i != j时，(i, j)和(j, i)认为是不同的二元组，不要漏算
// 当i == j时，(i, j)和(j, i)认为是相同的二元组，不要多算
// 比如[2, 4, 2, 6]，有10个倍数二元组
// 一共有m条查询，格式为 l r : 打印arr[l..r]范围上，有多少倍数二元组
// 1 <= n、m、arr[i] <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5398
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query {
//    int l, r, id;
//};
//
//const int MAXN = 500001;
//const int MAXF = 5000001;
//const int LIMIT = 100;
//int n, m, maxv;
//int arr[MAXN];
//int bi[MAXN];
//
//int headf[MAXN];
//int nextf[MAXF];
//int fac[MAXF];
//int cntf;
//
//Query query[MAXN];
//int headq[MAXN];
//int nextq[MAXN << 1];
//int qx[MAXN << 1];
//int ql[MAXN << 1];
//int qr[MAXN << 1];
//int qop[MAXN << 1];
//int qid[MAXN << 1];
//int cntq;
//
//int xcnt[MAXN];
//int vcnt[MAXN];
//long long pre[MAXN];
//
//long long ans[MAXN];
//
//bool QueryCmp(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    return a.r < b.r;
//}
//
//void addFactors(int num) {
//    if (headf[num] == 0) {
//        for (int f = 1; f * f <= num; f++) {
//            if (num % f == 0) {
//                nextf[++cntf] = headf[num];
//                fac[cntf] = f;
//                headf[num] = cntf;
//            }
//        }
//    }
//}
//
//void addOffline(int x, int l, int r, int op, int id) {
//    nextq[++cntq] = headq[x];
//    headq[x] = cntq;
//    qx[cntq] = x;
//    ql[cntq] = l;
//    qr[cntq] = r;
//    qop[cntq] = op;
//    qid[cntq] = id;
//}
//
//void compute() {
//    for (int i = 1; i <= n; i++) {
//        int num = arr[i];
//        pre[i] = pre[i - 1];
//        pre[i] += xcnt[num];
//        for (int e = headf[num], f, other; e > 0; e = nextf[e]) {
//            f = fac[e];
//            other = num / f;
//            xcnt[f]++;
//            pre[i] += vcnt[f];
//            if (other != f) {
//                xcnt[other]++;
//                pre[i] += vcnt[other];
//            }
//        }
//        vcnt[num]++;
//    }
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= m; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int id = query[i].id;
//        if (winr < jobr) {
//            addOffline(winl - 1, winr + 1, jobr, -1, id);
//            ans[id] += pre[jobr] - pre[winr];
//        }
//        if (winr > jobr) {
//            addOffline(winl - 1, jobr + 1, winr, 1, id);
//            ans[id] -= pre[winr] - pre[jobr];
//        }
//        winr = jobr;
//        if (winl > jobl) {
//            addOffline(winr, jobl, winl - 1, 1, id);
//            ans[id] -= pre[winl - 1] - pre[jobl - 1] + 2 * (winl - jobl);
//        }
//        if (winl < jobl) {
//            addOffline(winr, winl, jobl - 1, -1, id);
//            ans[id] += pre[jobl - 1] - pre[winl - 1] + 2 * (jobl - winl);
//        }
//        winl = jobl;
//    }
//    memset(xcnt, 0, sizeof(xcnt));
//    for (int x = 0; x <= n; x++) {
//        if (x >= 1) {
//            int num = arr[x];
//            for (int e = headf[num], f, other; e > 0; e = nextf[e]) {
//                f = fac[e];
//                other = num / f;
//                xcnt[f]++;
//                if (other != f) {
//                    xcnt[other]++;
//                }
//            }
//            if (num > LIMIT) {
//                for (int v = num; v <= maxv; v += num) {
//                    xcnt[v]++;
//                }
//            }
//        }
//        for (int q = headq[x]; q > 0; q = nextq[q]) {
//            int l = ql[q], r = qr[q], op = qop[q], id = qid[q];
//            for (int j = l; j <= r; j++) {
//                ans[id] += 1LL * op * xcnt[arr[j]];
//            }
//        }
//    }
//    for (int v = 1; v <= LIMIT; v++) {
//        vcnt[0] = xcnt[0] = 0;
//        for (int i = 1; i <= n; i++) {
//            vcnt[i] = vcnt[i - 1] + (arr[i] == v ? 1 : 0);
//            xcnt[i] = xcnt[i - 1] + (arr[i] % v == 0 ? 1 : 0);
//        }
//        for(int i = 1; i <= cntq; i++) {
//             int x = qx[i], l = ql[i], r = qr[i], op = qop[i], id = qid[i];
//             ans[id] += 1LL * op * vcnt[x] * (xcnt[r] - xcnt[l - 1]);
//        }
//    }
//}
//
//void prepare() {
//    int blen = (int)sqrt(n);
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//        maxv = max(maxv, arr[i]);
//        addFactors(arr[i]);
//    }
//    sort(query + 1, query + m + 1, QueryCmp);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> query[i].l >> query[i].r;
//        query[i].id = i;
//    }
//    prepare();
//    compute();
//    for (int i = 2; i <= m; i++) {
//        ans[query[i].id] += ans[query[i - 1].id];
//    }
//    for (int i = 1; i <= m; i++) {
//        ans[query[i].id] += query[i].r - query[i].l + 1;
//    }
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}