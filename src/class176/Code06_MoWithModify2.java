package class176;

// 带修莫队入门题，C++版
// 给定一个长度为n的数组arr，一共有m条操作，操作格式如下
// 操作 Q l r     : 打印arr[l..r]范围上有几种不同的数
// 操作 R pos val : 把arr[pos]的值设置成val
// 1 <= n、m <= 2 * 10^5
// 1 <= arr[i]、val <= 10^6
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
//    int pos, val;
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
//int kind;
//
//int ans[MAXN];
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
//    int pos = update[tim].pos;
//    int val = update[tim].val;
//    if (jobl <= pos && pos <= jobr) {
//        del(arr[pos]);
//        add(val);
//    }
//    int tmp = arr[pos];
//    arr[pos] = val;
//    update[tim].val = tmp;
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
//void prepare() {
//    int blen = max(1, (int)pow(n, 2.0 / 3));
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    sort(query + 1, query + cntq + 1, QueryCmp);
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
//    int l, r, pos, val;
//    for (int i = 1; i <= m; i++) {
//        cin >> op;
//        if (op == 'Q') {
//            cin >> l >> r;
//            cntq++;
//            query[cntq].l = l;
//            query[cntq].r = r;
//            query[cntq].t = cntu;
//            query[cntq].id = cntq;
//        } else {
//            cin >> pos >> val;
//            cntu++;
//            update[cntu].pos = pos;
//            update[cntu].val = val;
//        }
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= cntq; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}