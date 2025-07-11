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
//const int POW2 = 9;
//const int BLEN = 1 << POW2;
//const int OFFSET = BLEN - 1;
//int n, m;
//
//int arr[MAXN];
//int pos[MAXN];
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
//int pre[MAXN];
//int suf[MAXN];
//int len[MAXN];
//long long ans[MAXN];
//
//inline void mergeAns(int i, int rpre, int rsuf, int rlen, int rans) {
//    ans[i] += rans + 1LL * suf[i] * rpre;
//    if (pre[i] == len[i]) {
//        pre[i] += rpre;
//    }
//    if (rsuf == rlen) {
//        suf[i] += rsuf;
//    } else {
//        suf[i] = rsuf;
//    }
//    len[i] += rlen;
//}
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
//    radixSort();
//    for (int i = l; i <= r; i++) {
//        lst[i] = i - 1;
//        nxt[i] = i + 1;
//    }
//    int rpre = 0, rsuf = 0, rlen = r - l + 1, rans = 0;
//    int k = 1;
//    for (int i = l, idx; i <= r; i++) {
//        idx = pos[i];
//        for(; k <= siz && v[arrq[k]] < arr[idx]; k++) {
//            mergeAns(arrq[k], rpre, rsuf, rlen, rans);
//        }
//        if (lst[idx] == l - 1) {
//            rpre += nxt[idx] - idx;
//        }
//        if (nxt[idx] == r + 1) {
//            rsuf += idx - lst[idx];
//        }
//        rans += 1LL * (idx - lst[idx]) * (nxt[idx] - idx);
//        lst[nxt[idx]] = lst[idx];
//        nxt[lst[idx]] = nxt[idx];
//    }
//    for(; k <= siz; k++) {
//        mergeAns(arrq[k], rpre, rsuf, rlen, rans);
//    }
//    siz = 0;
//}
//
//inline void update(int qi, int l, int r) {
//    int jobi = x[qi], jobv = v[qi];
//    if (l <= jobi && jobi <= r) {
//        calc(l, r);
//        arr[jobi] = jobv;
//        int find = 0;
//        for (int i = l; i <= r; i++) {
//            if (pos[i] == jobi) {
//                find = i;
//                break;
//            }
//        }
//        for (int i = find; i < r && arr[pos[i]] > arr[pos[i + 1]]; i++) {
//            swap(pos[i], pos[i + 1]);
//        }
//        for (int i = find; i > l && arr[pos[i - 1]] > arr[pos[i]]; i--) {
//            swap(pos[i - 1], pos[i]);
//        }
//    }
//}
//
//inline void query(int qi, int l, int r) {
//    int jobl = x[qi], jobr = y[qi], jobv = v[qi];
//    if (jobl <= l && r <= jobr) {
//        arrq[++siz] = qi;
//    } else {
//        for (int i = max(jobl, l); i <= min(jobr, r); i++) {
//            if (arr[i] <= jobv) {
//                mergeAns(qi, 1, 1, 1, 1);
//            } else {
//                mergeAns(qi, 0, 0, 1, 0);
//            }
//        }
//    }
//}
//
//void compute(int l, int r) {
//    for (int i = l; i <= r; i++) {
//        pos[i] = i;
//    }
//    sort(pos + l, pos + r + 1, [&](int a, int b) {return arr[a] < arr[b];});
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
//    int bnum = (n + BLEN - 1) / BLEN;
//    for (int i = 1, l, r; i <= bnum; i++) {
//        l = (i - 1) * BLEN + 1;
//        r = min(i * BLEN, n);
//        compute(l, r);
//    }
//    for (int i = 1; i <= m; i++) {
//        if (op[i] == 2) {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}