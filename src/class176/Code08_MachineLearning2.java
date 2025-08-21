package class176;

// 机器学习，java版
// 给定一个长度为n的数组arr，一共有m条操作，操作格式如下
// 操作 1 l r     : arr[l..r]范围上，每种数字出现的次数，假设构成一个集合
//                  打印这个集合中，没出现过的最小正数
// 操作 2 pos val : 把arr[pos]的值设置成val
// 1 <= n、m <= 10^5
// 1 <= arr[i]、val <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/CF940F
// 测试链接 : https://codeforces.com/problemset/problem/940/F
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
//const int MAXN = 100001;
//int n, m;
//int arr[MAXN];
//int sorted[MAXN << 1];
//
//int bi[MAXN];
//Query query[MAXN];
//Update update[MAXN];
//int cntq, cntu;
//
//int cnt1[MAXN << 1];
//int cnt2[MAXN];
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
//int kth(int len, int num) {
//    int left = 1, right = len, mid, ret = 0;
//    while (left <= right) {
//        mid = (left + right) >> 1;
//        if (sorted[mid] <= num) {
//            ret = mid;
//            left = mid + 1;
//        } else {
//            right = mid - 1;
//        }
//    }
//    return ret;
//}
//
//void del(int num) {
//    cnt2[cnt1[num]]--;
//    cnt1[num]--;
//    cnt2[cnt1[num]]++;
//}
//
//void add(int num) {
//    cnt2[cnt1[num]]--;
//    cnt1[num]++;
//    cnt2[cnt1[num]]++;
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
//            moveTime(jobl, jobr,  wint--);
//        }
//        int ret = 1;
//        while (ret <= n && cnt2[ret] > 0) {
//            ret++;
//        }
//        ans[id] = ret;
//    }
//}
//
//void prepare() {
//    int len = 0;
//    for (int i = 1; i <= n; i++) {
//        sorted[++len] = arr[i];
//    }
//    for (int i = 1; i <= cntu; i++) {
//        sorted[++len] = update[i].val;
//    }
//    sort(sorted + 1, sorted + len + 1);
//    int tmp = 1;
//    for (int i = 2; i <= len; i++) {
//        if (sorted[tmp] != sorted[i]) {
//            sorted[++tmp] = sorted[i];
//        }
//    }
//    len = tmp;
//    for (int i = 1; i <= n; i++) {
//        arr[i] = kth(len, arr[i]);
//    }
//    for (int i = 1; i <= cntu; i++) {
//        update[i].val = kth(len, update[i].val);
//    }
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
//    for (int i = 1, op, l, r, pos, val; i <= m; i++) {
//        cin >> op;
//        if (op == 1) {
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