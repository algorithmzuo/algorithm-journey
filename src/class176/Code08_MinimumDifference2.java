package class176;

// 最小化极差，C++版
// 测试链接 : https://www.luogu.com.cn/problem/CF1476G
// 测试链接 : https://codeforces.com/problemset/problem/1476/G
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query {
//    int l, r, k, t, id;
//};
//
//struct Update {
//    int pos, val;
//};
//
//const int MAXN = 100001;
//const int MAXB = 3001;
//int n, m;
//int arr[MAXN];
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//
//Query query[MAXN];
//Update update[MAXN];
//int cntq, cntu;
//
//int cnt1[MAXN];
//int cnt2[MAXN];
//int sum[MAXB];
//int cntFreq[MAXN];
//int freqVal[MAXN];
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
//    cnt2[cnt1[num]]--;
//    sum[bi[cnt1[num]]]--;
//    cnt1[num]--;
//    cnt2[cnt1[num]]++;
//    sum[bi[cnt1[num]]]++;
//}
//
//void add(int num) {
//    cnt2[cnt1[num]]--;
//    sum[bi[cnt1[num]]]--;
//    cnt1[num]++;
//    cnt2[cnt1[num]]++;
//    sum[bi[cnt1[num]]]++;
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
//int getAns(int k) {
//    int size = 0;
//    for (int b = 1; b <= bi[n]; b++) {
//        if (sum[b] != 0) {
//            for (int f = bl[b]; f <= br[b]; f++) {
//                if (cnt2[f] > 0) {
//                    cntFreq[++size] = cnt2[f];
//                    freqVal[size] = f;
//                }
//            }
//        }
//    }
//    int minDiff = INT_MAX;
//    int cntSum = 0;
//    for (int l = 1, r = 0; l <= size; l++) {
//        while (cntSum < k && r < size) {
//            r++;
//            cntSum += cntFreq[r];
//        }
//        if (cntSum >= k) {
//            minDiff = min(minDiff, freqVal[r] - freqVal[l]);
//        }
//        cntSum -= cntFreq[l];
//    }
//    return minDiff == INT_MAX ? -1 : minDiff;
//}
//
//void compute() {
//    int winl = 1, winr = 0, wint = 0;
//    for (int i = 1; i <= cntq; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int jobk = query[i].k;
//        int jobt = query[i].t;
//        int id   = query[i].id;
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
//        ans[id] = getAns(jobk);
//    }
//}
//
//void prepare() {
//    int blen = max(1, (int)pow(n, 2.0 / 3.0));
//    int bnum = (n + blen - 1) / blen;
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        bl[i] = (i - 1) * blen + 1;
//        br[i] = min(i * blen, n);
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
//    for (int i = 1, op, l, r, k, pos, val; i <= m; i++) {
//        cin >> op;
//        if (op == 1) {
//            cin >> l >> r >> k;
//            cntq++;
//            query[cntq].l = l;
//            query[cntq].r = r;
//            query[cntq].k = k;
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