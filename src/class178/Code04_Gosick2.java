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
//
//Query1 query1[MAXN];
//Query2 query2[MAXN << 1];
//int cntq;
//
//int head[MAXN];
//int nxt[MAXF];
//int fac[MAXF];
//int cntf;
//
//int bi[MAXN];
//int fcnt[MAXN];
//int xcnt[MAXN];
//int pre[MAXN];
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
//    if (bi[a.l] & 1) {
//        return a.r < b.r;
//    } else {
//        return b.r < a.r;
//    }
//}
//
//bool Cmp2(Query2 &a, Query2 &b) {
//    return a.pos < b.pos;
//}
//
//void addFactor(int v, int f) {
//    nxt[++cntf] = head[v];
//    fac[cntf] = f;
//    head[v] = cntf;
//}
//
//void buildFactors(int x) {
//    if (head[x] > 0) {
//        return;
//    }
//    for (int f = 1; 1LL * f * f <= x; f++) {
//        if (x % f == 0) {
//            addFactor(x, f);
//            int other = x / f;
//            if (f != other) {
//                addFactor(x, other);
//            }
//        }
//    }
//}
//
//void addQuery(int pos, int id, int l, int r, int op) {
//	query2[++cntq].pos = pos;
//	query2[cntq].id = id;
//	query2[cntq].l = l;
//	query2[cntq].r = r;
//	query2[cntq].op = op;
//}
//
//void prepare() {
//    int blen = max(n / (int)sqrt((double)m), 1);
//    for (int i = 1, num; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//        num = arr[i];
//        maxv = max(maxv, num);
//        buildFactors(num);
//        for (int e = head[num], f; e > 0; e = nxt[e]) {
//            f = fac[e];
//            fcnt[f]++;
//            pre[i] += xcnt[f];
//        }
//        pre[i] += pre[i - 1] + fcnt[num];
//        xcnt[num]++;
//    }
//    sort(query1 + 1, query1 + m + 1, Cmp1);
//}
//
//void compute() {
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= m; i++) {
//        int jobl = query1[i].l;
//        int jobr = query1[i].r;
//        int id = query1[i].id;
//        if (winr < jobr) {
//            ans[id] += pre[jobr] - pre[winr];
//            addQuery(winl - 1, id, winr + 1, jobr, -1);
//        }
//        if (winr > jobr) {
//            ans[id] -= pre[winr] - pre[jobr];
//            addQuery(winl - 1, id, jobr + 1, winr, 1);
//        }
//        winr = jobr;
//        if (winl > jobl) {
//            ans[id] -= pre[winl - 1] - pre[jobl - 1];
//            addQuery(winr, id, jobl, winl - 1, 1);
//        }
//        if (winl < jobl) {
//            ans[id] += pre[jobl - 1] - pre[winl - 1];
//            addQuery(winr, id, winl, jobl - 1, -1);
//        }
//        winl = jobl;
//    }
//    sort(query2 + 1, query2 + cntq + 1, Cmp2);
//    memset(fcnt, 0, sizeof(fcnt));
//    for (int i = 1, j = 1; i <= cntq; i++) {
//        int pos = query2[i].pos, id = query2[i].id, l = query2[i].l, r = query2[i].r, op = query2[i].op;
//        while (j <= pos) {
//            int x = arr[j++];
//            buildFactors(x);
//            for (int e = head[x], f; e > 0; e = nxt[e]) {
//                f = fac[e];
//                fcnt[f]++;
//            }
//            if (x > LIMIT) {
//                for (int k = x; k <= maxv; k += x) {
//                    fcnt[k]++;
//                }
//            }
//        }
//        for (int k = l; k <= r; k++) {
//            ans[id] += 1LL * op * fcnt[arr[k]];
//        }
//    }
//    for (int i = 1; i <= LIMIT; i++) {
//        cnt1[0] = 0;
//        cnt2[0] = 0;
//        for (int j = 1; j <= n; j++) {
//            cnt1[j] = cnt1[j - 1] + (arr[j] == i ? 1 : 0);
//            cnt2[j] = cnt2[j - 1] + (arr[j] % i == 0 ? 1 : 0);
//        }
//        for (int j = 1; j <= cntq; j++) {
//            int pos = query2[j].pos, id = query2[j].id, l = query2[j].l, r = query2[j].r, op = query2[j].op;
//            ans[id] += 1LL * (cnt2[r] - cnt2[l - 1]) * cnt1[pos] * op;
//        }
//    }
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