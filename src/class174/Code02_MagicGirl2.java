package class174;

// 魔法少女网站，C++版
// 给定一个长度为n的数组arr，一共有m条操作，每条操作类型如下
// 操作 1 x v   : arr[x]的值变成v
// 操作 2 x y v : arr[x..y]范围上，查询有多少连续子数组的最大值 <= v
// 1 <= n、m <= 3 * 10^5
// 1 <= arr[i] <= n
// 测试链接 : https://www.luogu.com.cn/problem/P6578
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 300002;
//const int MAXB = 601;
//const int POW = 9;
//const int OFFSET = (1 << POW) - 1;
//int n, m;
//
//int arr[MAXN];
//int op[MAXN];
//int x[MAXN];
//int y[MAXN];
//int v[MAXN];
//
//int pos[MAXN];
//int que[MAXN];
//int cntp;
//int cntq;
//
//int cntv[MAXB];
//int help[MAXN];
//
//int lst[MAXN];
//int nxt[MAXN];
//
//int pre[MAXN];
//int suf[MAXN];
//int len[MAXN];
//long long ans[MAXN];
//
//inline void radix(int* idx, int* val, int siz) {
//    fill(cntv, cntv + MAXB, 0);
//    for (int i = 1; i <= siz; i++) cntv[val[idx[i]] & OFFSET]++;
//    for (int i = 1; i < MAXB; i++) cntv[i] += cntv[i - 1];
//    for (int i = siz; i >= 1; i--) help[cntv[val[idx[i]] & OFFSET]--] = idx[i];
//    for (int i = 1; i <= siz; i++) idx[i] = help[i];
//    fill(cntv, cntv + MAXB, 0);
//    for (int i = 1; i <= siz; i++) cntv[val[idx[i]] >> POW]++;
//    for (int i = 1; i < MAXB; i++) cntv[i] += cntv[i - 1];
//    for (int i = siz; i >= 1; i--) help[cntv[val[idx[i]] >> POW]--] = idx[i];
//    for (int i = 1; i <= siz; i++) idx[i] = help[i];
//}
//
//inline void merge(int i, int curPre, int curSuf, int curLen, int curAns) {
//    ans[i] += 1L * suf[i] * curPre + curAns;
//    pre[i] = pre[i] + (pre[i] == len[i] ? curPre : 0);
//    suf[i] = curSuf + (curSuf == curLen ? suf[i] : 0);
//    len[i] += curLen;
//}
//
//void calc(int l, int r) {
//    for (int i = l; i <= r; i++) {
//        pos[++cntp] = i;
//        lst[i] = i - 1;
//        nxt[i] = i + 1;
//    }
//    radix(pos, arr, cntp);
//    radix(que, v, cntq);
//    int curPre = 0, curSuf = 0, curLen = r - l + 1, curAns = 0;
//    for (int i = 1, j = 1, idx; i <= cntq; i++) {
//        while (j <= cntp && arr[pos[j]] <= v[que[i]]) {
//            idx = pos[j];
//            if (lst[idx] == l - 1) {
//                curPre += nxt[idx] - idx;
//            }
//            if (nxt[idx] == r + 1) {
//                curSuf += idx - lst[idx];
//            }
//            curAns += 1L * (idx - lst[idx]) * (nxt[idx] - idx);
//            lst[nxt[idx]] = lst[idx];
//            nxt[lst[idx]] = nxt[idx];
//            j++;
//        }
//        merge(que[i], curPre, curSuf, curLen, curAns);
//    }
//    cntp = cntq = 0;
//}
//
//void compute(int l, int r) {
//    for (int qi = 1; qi <= m; qi++) {
//        if (op[qi] == 1) {
//            if (l <= x[qi] && x[qi] <= r) {
//                calc(l, r);
//                arr[x[qi]] = v[qi];
//            }
//        } else {
//            if (x[qi] <= l && r <= y[qi]) {
//                que[++cntq] = qi;
//            } else {
//                for (int i = max(x[qi], l); i <= min(y[qi], r); i++) {
//                    if (arr[i] <= v[qi]) {
//                        merge(qi, 1, 1, 1, 1);
//                    } else {
//                        merge(qi, 0, 0, 1, 0);
//                    }
//                }
//            }
//        }
//    }
//    calc(l, r);
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
//        cin >> op[i] >> x[i];
//        if (op[i] == 1) {
//            cin >> v[i];
//        } else {
//            cin >> y[i] >> v[i];
//        }
//    }
//    int blen = 1 << POW;
//    int bnum = (n + blen - 1) / blen;
//    for (int i = 1, l, r; i <= bnum; i++) {
//        l = (i - 1) * blen + 1;
//        r = min(i * blen, n);
//        compute(l, r);
//    }
//    for (int i = 1; i <= m; i++) {
//        if (op[i] == 2) {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}