package class176;

// 数颜色，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P1903
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query {
//    int l, r, t, id;
//};
//
//struct Update {
//    int i, v;
//};
//
//const int MAXN = 200001;
//const int MAXV = 1000001;
//int n, m;
//int arr[MAXN];
//int bi[MAXN];
//
//Query query[MAXN];
//Update update[MAXN];
//int cntq, cntu;
//
//int cnt[MAXV];
//int ans[MAXN];
//int kind;
//
//bool QueryCmp(Query &a, Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    if (bi[a.r] != bi[b.r]) {
//        return bi[a.r] < bi[b.r];
//    }
//    return a.t < b.t;
//}
//
//void del(int num) {
//    if (--cnt[num] == 0) {
//        kind--;
//    }
//}
//
//void add(int num) {
//    if (++cnt[num] == 1) {
//        kind++;
//    }
//}
//
//void moveTime(int jobl, int jobr, int tim) {
//    int pos = update[tim].i;
//    int val = update[tim].v;
//    if (jobl <= pos && pos <= jobr) {
//        del(arr[pos]);
//        add(val);
//    }
//    int tmp = arr[pos];
//    arr[pos] = val;
//    update[tim].v = tmp;
//}
//
//void prepare() {
//    int blen = max(1, (int)pow(n, 2.0 / 3));
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    sort(query + 1, query + cntq + 1, QueryCmp);
//}
//
//void compute() {
//    int winl = 1, winr = 0, wint = 0;
//    for (int i = 1; i <= cntq; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int jobt = query[i].t;
//        int id = query[i].id;
//        while (winl > jobl) {
//            add(arr[--winl]);
//        }
//        while (winr < jobr) {
//            add(arr[++winr]);
//        }
//        while (winl < jobl) {
//            del(arr[winl++]);
//        }
//        while (winr > jobr) {
//            del(arr[winr--]);
//        }
//        while (wint < jobt) {
//            moveTime(jobl, jobr, ++wint);
//        }
//        while (wint > jobt) {
//            moveTime(jobl, jobr, wint--);
//        }
//        ans[id] = kind;
//     }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    char op;
//    int x, y;
//    for (int i = 1; i <= m; i++) {
//        cin >> op >> x >> y;
//        if (op == 'Q') {
//            cntq++;
//            query[cntq].l = x;
//            query[cntq].r = y;
//            query[cntq].t = cntu;
//            query[cntq].id = cntq;
//        } else {
//            cntu++;
//            update[cntu].i = x;
//            update[cntu].v = y;
//        }
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= cntq; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}