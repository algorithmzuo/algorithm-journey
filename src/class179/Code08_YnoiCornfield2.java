package class179;

// 由乃的玉米田，C++版
// 给定一个长度为n的数组arr，接下来有m条查询，查询格式如下
// 查询 1 l r x : 打印arr[l..r]范围上能否选出两个数，减的结果为x
// 查询 2 l r x : 打印arr[l..r]范围上能否选出两个数，加的结果为x
// 查询 3 l r x : 打印arr[l..r]范围上能否选出两个数，乘的结果为x
// 查询 4 l r x : 打印arr[l..r]范围上能否选出两个数，除的结果为x，并且没有余数
// 选出的这两个数可以是同一个位置的数，答案如果为是，打印 "yuno"，否则打印 "yumi"
// 1 <= 所有数据 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5355
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Query {
//    int l, r, x, op, id;
//};
//
//const int MAXN = 100001;
//const int MAXV = 100000;
//const int MAXB = 401;
//int n, m, blen;
//int arr[MAXN];
//int bi[MAXN];
//
//Query query[MAXN];
//int cntq = 0;
//
//int headq[MAXB];
//int nextq[MAXN];
//int ql[MAXN];
//int qr[MAXN];
//int qid[MAXN];
//int cnts = 0;
//
//bitset<MAXN> bitSet1;
//bitset<MAXN> bitSet2;
//
//int cnt[MAXN];
//int lastPos[MAXN];
//int maxLeft[MAXN];
//
//bool ans[MAXN];
//
//void addSpecial(int x, int l, int r, int id) {
//    nextq[++cnts] = headq[x];
//    headq[x] = cnts;
//    ql[cnts] = l;
//    qr[cnts] = r;
//    qid[cnts] = id;
//}
//
//bool QueryCmp(const Query &a, const Query &b) {
//    if (bi[a.l] != bi[b.l]) {
//        return bi[a.l] < bi[b.l];
//    }
//    if (bi[a.l] & 1) {
//        return a.r < b.r;
//    } else {
//        return a.r > b.r;
//    }
//}
//
//void add(int x) {
//    cnt[x]++;
//    if (cnt[x] == 1) {
//        bitSet1[x] = 1;
//        bitSet2[MAXV - x] = 1;
//    }
//}
//
//void del(int x) {
//    cnt[x]--;
//    if (cnt[x] == 0) {
//        bitSet1[x] = 0;
//        bitSet2[MAXV - x] = 0;
//    }
//}
//
//bool calc(int op, int x) {
//    if (op == 1) {
//        return (bitSet1 & (bitSet1 << x)).any();
//    } else if (op == 2) {
//        return (bitSet1 & (bitSet2 >> (MAXV - x))).any();
//    } else if (op == 3) {
//        for (int d = 1; d * d <= x; d++) {
//            if (x % d == 0) {
//                if (bitSet1[d] && bitSet1[x / d]) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    } else {
//        for (int i = 1; i * x <= MAXV; i++) {
//            if (bitSet1[i] && bitSet1[i * x]) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
//
//void compute() {
//    int winl = 1, winr = 0;
//    for (int i = 1; i <= cntq; i++) {
//        int jobl = query[i].l;
//        int jobr = query[i].r;
//        int jobx = query[i].x;
//        int op = query[i].op;
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
//        ans[id] = calc(op, jobx);
//    }
//}
//
//void special() {
//    for (int x = 1; x < blen; x++) {
//        if (headq[x] != 0) {
//            memset(lastPos, 0, sizeof(int) * (MAXV + 1));
//            memset(maxLeft, 0, sizeof(int) * (n + 1));
//            int last = 0;
//            for (int i = 1; i <= n; i++) {
//                int val = arr[i];
//                lastPos[val] = i;
//                if (1LL * val * x <= MAXV) {
//                    last = max(last, lastPos[val * x]);
//                }
//                if (val % x == 0) {
//                    last = max(last, lastPos[val / x]);
//                }
//                maxLeft[i] = last;
//            }
//            for (int q = headq[x]; q > 0; q = nextq[q]) {
//                int l = ql[q];
//                int r = qr[q];
//                int id = qid[q];
//                ans[id] = (l <= maxLeft[r]);
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    blen = (int)sqrt(n);
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1, op, l, r, x; i <= m; i++) {
//        cin >> op >> l >> r >> x;
//        if (op == 4 && x < blen) {
//            addSpecial(x, l, r, i);
//        } else {
//            query[++cntq] = {l, r, x, op, i};
//        }
//    }
//    sort(query + 1, query + cntq + 1, QueryCmp);
//    compute();
//    special();
//    for (int i = 1; i <= m; i++) {
//        cout << (ans[i] ? "yuno" : "yumi") << '\n';
//    }
//    return 0;
//}