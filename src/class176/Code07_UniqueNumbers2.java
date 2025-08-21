package class176;

// 统计出现1次的数，C++版
// 给定一个长度为n的数组arr，下标0~n-1，一共有m条操作，格式如下
// 操作 1 pos val : 把arr[pos]的值设置成val
// 操作 2 l r     : 查询arr[l..r]范围上，有多少种数出现了1次
// 0 <= n、m、arr[i] <= 2 * 10^5 
// 测试链接 : https://www.luogu.com.cn/problem/SP30906
// 测试链接 : https://www.spoj.com/problems/ADAUNIQ/
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
//int n, m;
//int arr[MAXN];
//int bi[MAXN];
//
//Query query[MAXN];
//Update update[MAXN];
//int cntq, cntu;
//
//int cnt[MAXN];
//int curAns;
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
//    if (cnt[num] == 1) {
//        curAns--;
//    }
//    if (cnt[num] == 2) {
//        curAns++;
//    }
//    cnt[num]--;
//}
//
//void add(int num) {
//    if (cnt[num] == 0) {
//        curAns++;
//    }
//    if (cnt[num] == 1) {
//        curAns--;
//    }
//    cnt[num]++;
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
//        ans[id] = curAns;
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
//    for (int i = 1, op, l, r, pos, val; i <= m; i++) {
//        cin >> op;
//        if (op == 1) {
//            cin >> pos >> val;
//            cntu++;
//            update[cntu].pos = pos + 1;
//            update[cntu].val = val;
//        } else {
//            cin >> l >> r;
//            cntq++;
//            query[cntq].l = l + 1;
//            query[cntq].r = r + 1;
//            query[cntq].t = cntu;
//            query[cntq].id = cntq;
//        }
//    }
//    prepare();
//    compute();
//    for (int i = 1; i <= cntq; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}