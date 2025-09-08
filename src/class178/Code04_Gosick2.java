package class178;

// 区间查倍数，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5398
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query1 {
//    int l, r, id;
//};
//
//struct Query2 {
//    int pos, id, l, r, op;
//};
//
//const int MAXN = 500001;
//const int MAXF = 5000001;
//const int LIMIT = 80;
//int n, m, maxv;
//int arr[MAXN];
//int bi[MAXN];
//
//int head[MAXN];
//int nxt[MAXF];
//int fac[MAXF];
//int cntf;
//
//Query1 query1[MAXN];
//Query2 query2[MAXN << 1];
//int cntq;
//
//int fcnt[MAXN];
//int xcnt[MAXN];
//long long pre[MAXN];
//
//int cnt1[MAXN];
//int cnt2[MAXN];
//
//long long ans[MAXN];
//
//bool Cmp1(Query1 &a, Query1 &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    return a.r < b.r;
//}
//
//bool Cmp2(Query2 &a, Query2 &b) {
//    return a.pos < b.pos;
//}
//
//void addFactors(int num) {
//    if (head[num] == 0) {
//        for (int f = 1; f * f <= num; f++) {
//            if (num % f == 0) {
//                nxt[++cntf] = head[num];
//                fac[cntf] = f;
//                head[num] = cntf;
//            }
//        }
//    }
//}
//
//void addQuery(int pos, int id, int l, int r, int op) {
//    query2[++cntq].pos = pos;
//    query2[cntq].id = id;
//    query2[cntq].l = l;
//    query2[cntq].r = r;
//    query2[cntq].op = op;
//}
//
//void compute() {
//    for (int i = 1, x; i <= n; i++) {
//        x = arr[i];
//        for (int e = head[x], f, other; e > 0; e = nxt[e]) {
//            f = fac[e];
//            other = x / f;
//            fcnt[f]++;
//            pre[i] += xcnt[f];
//            if (other != f) {
//                fcnt[other]++;
//                pre[i] += xcnt[other];
//            }
//        }
//        pre[i] += fcnt[x] + pre[i - 1];
//        xcnt[x]++;
//    }
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= m; i++) {
//        int jobl = query1[i].l;
//        int jobr = query1[i].r;
//        int id = query1[i].id;
//        if (winr < jobr) {
//            addQuery(winl - 1, id, winr + 1, jobr, -1);
//            ans[id] += pre[jobr] - pre[winr];
//        }
//        if (winr > jobr) {
//            addQuery(winl - 1, id, jobr + 1, winr, 1);
//            ans[id] -= pre[winr] - pre[jobr];
//        }
//        winr = jobr;
//        if (winl > jobl) {
//            addQuery(winr, id, jobl, winl - 1, 1);
//            ans[id] -= pre[winl - 1] - pre[jobl - 1];
//        }
//        if (winl < jobl) {
//            addQuery(winr, id, winl, jobl - 1, -1);
//            ans[id] += pre[jobl - 1] - pre[winl - 1];
//        }
//        winl = jobl;
//    }
//    sort(query2 + 1, query2 + cntq + 1, Cmp2);
//    memset(fcnt, 0, sizeof(fcnt));
//    for (int pos = 0, qi = 1; pos <= n && qi <= cntq; pos++) {
//        if (pos >= 1) {
//            int num = arr[pos];
//            for (int e = head[num], f, other; e > 0; e = nxt[e]) {
//                f = fac[e];
//                other = num / f;
//                fcnt[f]++;
//                if (other != f) {
//                    fcnt[other]++;
//                }
//            }
//            if (num > LIMIT) {
//                for (int v = num; v <= maxv; v += num) {
//                    fcnt[v]++;
//                }
//            }
//        }
//        for (; qi <= cntq && query2[qi].pos == pos; qi++) {
//            int id = query2[qi].id, l = query2[qi].l, r = query2[qi].r, op = query2[qi].op;
//            for (int i = l; i <= r; i++) {
//                ans[id] += 1LL * op * fcnt[arr[i]];
//            }
//        }
//    }
//    for (int v = 1; v <= LIMIT; v++) {
//        cnt1[0] = 0;
//        cnt2[0] = 0;
//        for (int i = 1; i <= n; i++) {
//            cnt1[i] = cnt1[i - 1] + (arr[i] == v ? 1 : 0);
//            cnt2[i] = cnt2[i - 1] + (arr[i] % v == 0 ? 1 : 0);
//        }
//        for (int i = 1; i <= cntq; i++) {
//            int pos = query2[i].pos, id = query2[i].id, l = query2[i].l, r = query2[i].r, op = query2[i].op;
//            ans[id] += 1LL * op * cnt1[pos] * (cnt2[r] - cnt2[l - 1]);
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
//    sort(query1 + 1, query1 + m + 1, Cmp1);
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
//        cin >> query1[i].l >> query1[i].r;
//        query1[i].id = i;
//    }
//    prepare();
//    compute();
//    for (int i = 2; i <= m; i++) {
//        ans[query1[i].id] += ans[query1[i - 1].id];
//    }
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}