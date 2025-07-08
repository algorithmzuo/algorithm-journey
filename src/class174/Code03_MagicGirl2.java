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
//const int BLEN = 512;
//const int OFFSET = 0x1ff;
//const int POWER = 9;
//int n, m;
//
//int arr[MAXN];
//Node sortv[MAXN];
//
//int op[MAXN];
//int x[MAXN];
//int y[MAXN];
//int z[MAXN];
//
//int head1[MAXB];
//int head2[MAXB];
//int toq[MAXN << 1];
//int nexte[MAXN << 1];
//int cntg;
//
//int sortq[MAXN];
//int cntq;
//
//int lst[MAXN];
//int nxt[MAXN];
//
//Answer tmp;
//Answer ans[MAXN];
//
//inline void addEdge(int *head, int u, int v) {
//    nexte[++cntg] = head[u];
//    toq[cntg] = v;
//    head[u] = cntg;
//}
//
//void calc(int l, int r) {
//    for (int md = 0; md < BLEN; ++md) {
//        for (int e = head1[md]; e; e = nexte[e]) {
//            addEdge(head2, z[toq[e]] >> POWER, toq[e]);
//        }
//        head1[md] = 0;
//    }
//    for (int tm = n / BLEN; tm >= 0; --tm) {
//        for (int e = head2[tm]; e; e = nexte[e]) {
//            sortq[++cntq] = toq[e];
//        }
//        head2[tm] = 0;
//    }
//    for (int left = 1, right = cntq; left < right; ++left, --right) {
//        swap(sortq[left], sortq[right]);
//    }
//    for (int i = l; i <= r; ++i) {
//        lst[i] = i - 1;
//        nxt[i] = i + 1;
//    }
//    tmp = { 0, 0, r - l + 1, 0 };
//    int k = 1;
//    for (int i = l, idx; i <= r; ++i) {
//        idx = sortv[i].i;
//        for(; k <= cntq && z[sortq[k]] < arr[idx]; ++k) {
//        	ans[sortq[k]].merge(tmp);
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
//    for(; k <= cntq; ++k) {
//    	ans[sortq[k]].merge(tmp);
//    }
//    cntg = cntq = 0;
//}
//
//inline void update(int qi, int l, int r) {
//    int jobi = x[qi], jobv = y[qi];
//    if (l <= jobi && jobi <= r) {
//        calc(l, r);
//        arr[jobi] = jobv;
//        int pos = 0;
//        for (int i = l; i <= r; ++i) {
//            if (sortv[i].i == jobi) {
//                sortv[i].v = jobv;
//                pos = i;
//                break;
//            }
//        }
//        for (int i = pos; i < r && sortv[i].v > sortv[i + 1].v; ++i) {
//            swap(sortv[i], sortv[i + 1]);
//        }
//        for (int i = pos; i > l && sortv[i - 1].v > sortv[i].v; --i) {
//            swap(sortv[i - 1], sortv[i]);
//        }
//    }
//}
//
//inline void query(int qi, int l, int r) {
//    int jobl = x[qi], jobr = y[qi], jobv = z[qi];
//    if (jobl <= l && r <= jobr) {
//        addEdge(head1, jobv & OFFSET, qi);
//    } else {
//    	for (int i = max(jobl, l); i <= min(jobr, r); ++i) {
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
//    for (int i = l; i <= r; ++i) {
//        sortv[i] = { arr[i], i };
//    }
//    sort(sortv + l, sortv + r + 1, nodeCmp);
//    for (int qi = 1; qi <= m; ++qi) {
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
//    for (int i = 1; i <= n; ++i) {
//        arr[i] = read();
//    }
//    for (int i = 1; i <= m; ++i) {
//        op[i] = read();
//        x[i] = read();
//        y[i] = read();
//        if (op[i] == 2) {
//            z[i] = read();
//        }
//    }
//    int BNUM = (n + BLEN - 1) / BLEN;
//    for (int i = 1, l, r; i <= BNUM; ++i) {
//        l = (i - 1) * BLEN + 1;
//        r = min(i * BLEN, n);
//        compute(l, r);
//    }
//    for (int i = 1; i <= m; ++i) {
//        if (op[i] == 2) {
//            printf("%lld\n", ans[i].res);
//        }
//    }
//    return 0;
//}