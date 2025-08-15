package class176;

// 普通莫队模版题，C++版
// 测试链接 : https://www.luogu.com.cn/problem/SP3267
// 测试链接 : https://www.spoj.com/problems/DQUERY/
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
//const int MAXN = 30001;
//const int MAXV = 1000001;
//const int MAXQ = 200001;
//int n, q;
//int arr[MAXN];
//Query query[MAXQ];
//
//int bi[MAXN];
//int cnt[MAXV];
//int kind = 0;
//int ans[MAXQ];
//
//bool QueryCmp1(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    return a.r < b.r;
//}
//
//bool QueryCmp2(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    if ((bi[a.l] & 1) == 1) {
//        return a.r < b.r;
//    }
//    return a.r > b.r;
//}
//
//void del(int idx) {
//    if (--cnt[arr[idx]] == 0) {
//        kind--;
//    }
//}
//
//void add(int idx) {
//    if (++cnt[arr[idx]] == 1) {
//        kind++;
//    }
//}
//
//void prepare() {
//    int blen = (int)sqrt(n);
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    sort(query + 1, query + q + 1, QueryCmp2);
//}
//
//void compute() {
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= q; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        while (winl > jobl) {
//            add(--winl);
//        }
//        while (winr < jobr) {
//            add(++winr);
//        }
//        while (winl < jobl) {
//            del(winl++);
//        }
//        while (winr > jobr) {
//            del(winr--);
//        }
//        ans[query[i].id] = kind;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    cin >> q;
//    for (int i = 1; i <= q; i++) {
//        cin >> query[i].l;
//        cin >> query[i].r;
//        query[i].id = i;
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= q; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}