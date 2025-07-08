package class174;

// 魔法少女网站，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P6578
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//char buf[1000000], *p1 = buf, *p2 = buf;
//
//inline char gc() {
//    if (p1 == p2) {
//        p2 = (p1 = buf) + fread(buf, 1, 1000000, stdin);
//        if (p1 == p2) {
//            return EOF;
//        }
//    }
//    return *p1++;
//}
//
//inline int read() {
//    int x = 0;
//    char c = gc();
//    while (!isdigit(c)) {
//        c = gc();
//    }
//    while (isdigit(c)) {
//        x = x * 10 + c - '0';
//        c = gc();
//    }
//    return x;
//}
//
//struct Node {
//    int v;
//    int i;
//};
//
//inline bool nodeCmp(const Node &a, const Node &b) {
//    return a.v < b.v;
//}
//
//struct Answer {
//    int pre, suf, len;
//    long long res;
//    inline void merge(const Answer &right) {
//        res += right.res + 1LL * suf * right.pre;
//        if (pre == len) {
//            pre += right.pre;
//        }
//        if (right.suf == right.len) {
//            suf += right.suf;
//        } else {
//            suf = right.suf;
//        }
//        len += right.len;
//    }
//};
//
//const int MAXN = 300005;
//const int MAXB = 601;
//const int POW2 = 9;
//const int BLEN = 1 << POW2;
//const int OFFSET = BLEN - 1;
//int n, m;
//
//int arr[MAXN];
//Node sortv[MAXN];
//
//int op[MAXN];
//int x[MAXN];
//int y[MAXN];
//int v[MAXN];
//
//int arrq[MAXN];
//int cntv[MAXB];
//int help[MAXN];
//int siz;
//
//int lst[MAXN];
//int nxt[MAXN];
//
//Answer tmp;
//Answer ans[MAXN];
//
//inline void radixSort() {
//    fill(cntv, cntv + MAXB, 0);
//    for (int i = 1; i <= siz; i++) cntv[v[arrq[i]] & OFFSET]++;
//    for (int i = 1; i < MAXB; i++) cntv[i] += cntv[i - 1];
//    for (int i = siz; i >= 1; i--) help[cntv[v[arrq[i]] & OFFSET]--] = arrq[i];
//    for (int i = 1; i <= siz; i++) arrq[i] = help[i];
//    fill(cntv, cntv + MAXB, 0);
//    for (int i = 1; i <= siz; i++) cntv[v[arrq[i]] >> POW2]++;
//    for (int i = 1; i < MAXB; i++) cntv[i] += cntv[i - 1];
//    for (int i = siz; i >= 1; i--) help[cntv[v[arrq[i]] >> POW2]--] = arrq[i];
//    for (int i = 1; i <= siz; i++) arrq[i] = help[i];
//}
//
//void calc(int l, int r) {
//	radixSort();
//    for (int i = l; i <= r; i++) {
//        lst[i] = i - 1;
//        nxt[i] = i + 1;
//    }
//    tmp = { 0, 0, r - l + 1, 0 };
//    int k = 1;
//    for (int i = l, idx; i <= r; i++) {
//        idx = sortv[i].i;
//        for(; k <= siz && v[arrq[k]] < arr[idx]; k++) {
//        	ans[arrq[k]].merge(tmp);
//        }
//        if (lst[idx] == l - 1) {
//            tmp.pre += nxt[idx] - idx;
//        }
//        if (nxt[idx] == r + 1) {
//            tmp.suf += idx - lst[idx];
//        }
//        tmp.res += 1LL * (idx - lst[idx]) * (nxt[idx] - idx);
//        lst[nxt[idx]] = lst[idx];
//        nxt[lst[idx]] = nxt[idx];
//    }
//    for(; k <= siz; k++) {
//    	ans[arrq[k]].merge(tmp);
//    }
//    siz = 0;
//}
//
//inline void update(int qi, int l, int r) {
//    int jobi = x[qi], jobv = y[qi];
//    if (l <= jobi && jobi <= r) {
//        calc(l, r);
//        arr[jobi] = jobv;
//        int pos = 0;
//        for (int i = l; i <= r; i++) {
//            if (sortv[i].i == jobi) {
//                sortv[i].v = jobv;
//                pos = i;
//                break;
//            }
//        }
//        for (int i = pos; i < r && sortv[i].v > sortv[i + 1].v; i++) {
//            swap(sortv[i], sortv[i + 1]);
//        }
//        for (int i = pos; i > l && sortv[i - 1].v > sortv[i].v; i--) {
//            swap(sortv[i - 1], sortv[i]);
//        }
//    }
//}
//
//inline void query(int qi, int l, int r) {
//    int jobl = x[qi], jobr = y[qi], jobv = v[qi];
//    if (jobl <= l && r <= jobr) {
//        arrq[++siz] = qi;
//    } else {
//    	for (int i = max(jobl, l); i <= min(jobr, r); i++) {
//            if (arr[i] <= jobv) {
//                tmp = { 1, 1, 1, 1 };
//            }else {
//            	tmp = { 0, 0, 1, 0 };
//            }
//    		ans[qi].merge(tmp);
//        }
//    }
//}
//
//void compute(int l, int r) {
//    for (int i = l; i <= r; i++) {
//        sortv[i] = { arr[i], i };
//    }
//    sort(sortv + l, sortv + r + 1, nodeCmp);
//    for (int qi = 1; qi <= m; qi++) {
//        if (op[qi] == 1) {
//            update(qi, l, r);
//        } else {
//            query(qi, l, r);
//        }
//    }
//    calc(l, r);
//}
//
//int main() {
//    n = read();
//    m = read();
//    for (int i = 1; i <= n; i++) {
//        arr[i] = read();
//    }
//    for (int i = 1; i <= m; i++) {
//        op[i] = read();
//        x[i] = read();
//        y[i] = read();
//        if (op[i] == 2) {
//        	v[i] = read();
//        }
//    }
//    int BNUM = (n + BLEN - 1) / BLEN;
//    for (int i = 1, l, r; i <= BNUM; i++) {
//        l = (i - 1) * BLEN + 1;
//        r = min(i * BLEN, n);
//        compute(l, r);
//    }
//    for (int i = 1; i <= m; i++) {
//        if (op[i] == 2) {
//            printf("%lld\n", ans[i].res);
//        }
//    }
//    return 0;
//}