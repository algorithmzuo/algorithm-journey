package class114;

// 区间最值和历史最值，C++版
// 给定两个长度都为n的数组A和B，一开始两个数组完全一样
// 任何操作做完，都更新B数组，B[i] = max(B[i],A[i])
// 实现以下五种操作，一共会调用m次
// 操作 1 l r v : A[l..r]范围上每个数加上v
// 操作 2 l r v : A[l..r]范围上每个数A[i]变成min(A[i],v)
// 操作 3 l r   : 返回A[l..r]范围上的累加和
// 操作 4 l r   : 返回A[l..r]范围上的最大值
// 操作 5 l r   : 返回B[l..r]范围上的最大值
// 1 <= n、m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P6242
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 500001;
//const ll LOWEST = LLONG_MIN;
//int n, m;
//
//int arr[MAXN];
//ll sum[MAXN << 2];
//ll maxv[MAXN << 2];
//int cnt[MAXN << 2];
//ll sem[MAXN << 2];
//ll maxAdd[MAXN << 2];
//ll otherAdd[MAXN << 2];
//ll maxHistory[MAXN << 2];
//ll maxAddTop[MAXN << 2];
//ll otherAddTop[MAXN << 2];
//
//void up(int i) {
//    int l = i << 1;
//    int r = i << 1 | 1;
//    maxHistory[i] = max(maxHistory[l], maxHistory[r]);
//    sum[i] = sum[l] + sum[r];
//    maxv[i] = max(maxv[l], maxv[r]);
//    if (maxv[l] > maxv[r]) {
//        cnt[i] = cnt[l];
//        sem[i] = max(sem[l], maxv[r]);
//    } else if (maxv[l] < maxv[r]) {
//        cnt[i] = cnt[r];
//        sem[i] = max(maxv[l], sem[r]);
//    } else {
//        cnt[i] = cnt[l] + cnt[r];
//        sem[i] = max(sem[l], sem[r]);
//    }
//}
//
//void lazy(int i, int n, ll maxAddv, ll otherAddv, ll maxUpv, ll otherUpv) {
//    maxHistory[i] = max(maxHistory[i], maxv[i] + maxUpv);
//    maxAddTop[i] = max(maxAddTop[i], maxAdd[i] + maxUpv);
//    otherAddTop[i] = max(otherAddTop[i], otherAdd[i] + otherUpv);
//    sum[i] += maxAddv * cnt[i] + otherAddv * (n - cnt[i]);
//    maxv[i] += maxAddv;
//    sem[i] += sem[i] == LOWEST ? 0 : otherAddv;
//    maxAdd[i] += maxAddv;
//    otherAdd[i] += otherAddv;
//}
//
//void down(int i, int ln, int rn) {
//    int l = i << 1;
//    int r = i << 1 | 1;
//    ll tmp = max(maxv[l], maxv[r]);
//    if (maxv[l] == tmp) {
//        lazy(l, ln, maxAdd[i], otherAdd[i], maxAddTop[i], otherAddTop[i]);
//    } else {
//        lazy(l, ln, otherAdd[i], otherAdd[i], otherAddTop[i], otherAddTop[i]);
//    }
//    if (maxv[r] == tmp) {
//        lazy(r, rn, maxAdd[i], otherAdd[i], maxAddTop[i], otherAddTop[i]);
//    } else {
//        lazy(r, rn, otherAdd[i], otherAdd[i], otherAddTop[i], otherAddTop[i]);
//    }
//    maxAdd[i] = otherAdd[i] = maxAddTop[i] = otherAddTop[i] = 0;
//}
//
//void build(int l, int r, int i) {
//    if (l == r) {
//        sum[i] = maxv[i] = maxHistory[i] = arr[l];
//        sem[i] = LOWEST;
//        cnt[i] = 1;
//    } else {
//        int mid = (l + r) >> 1;
//        build(l, mid, i << 1);
//        build(mid + 1, r, i << 1 | 1);
//        up(i);
//    }
//    maxAdd[i] = otherAdd[i] = maxAddTop[i] = otherAddTop[i] = 0;
//}
//
//void add(int jobl, int jobr, ll jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        lazy(i, r - l + 1, jobv, jobv, jobv, jobv);
//    } else {
//        int mid = (l + r) >> 1;
//        down(i, mid - l + 1, r - mid);
//        if (jobl <= mid) {
//            add(jobl, jobr, jobv, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
//        }
//        up(i);
//    }
//}
//
//void setMin(int jobl, int jobr, ll jobv, int l, int r, int i) {
//    if (jobv >= maxv[i]) {
//        return;
//    }
//    if (jobl <= l && r <= jobr && sem[i] < jobv) {
//        lazy(i, r - l + 1, jobv - maxv[i], 0, jobv - maxv[i], 0);
//    } else {
//        int mid = (l + r) >> 1;
//        down(i, mid - l + 1, r - mid);
//        if (jobl <= mid) {
//            setMin(jobl, jobr, jobv, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            setMin(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
//        }
//        up(i);
//    }
//}
//
//ll querySum(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return sum[i];
//    } else {
//        int mid = (l + r) >> 1;
//        down(i, mid - l + 1, r - mid);
//        ll ans = 0;
//        if (jobl <= mid) {
//            ans += querySum(jobl, jobr, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            ans += querySum(jobl, jobr, mid + 1, r, i << 1 | 1);
//        }
//        return ans;
//    }
//}
//
//ll queryMax(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return maxv[i];
//    } else {
//        int mid = (l + r) >> 1;
//        down(i, mid - l + 1, r - mid);
//        ll ans = LOWEST;
//        if (jobl <= mid) {
//            ans = max(ans, queryMax(jobl, jobr, l, mid, i << 1));
//        }
//        if (jobr > mid) {
//            ans = max(ans, queryMax(jobl, jobr, mid + 1, r, i << 1 | 1));
//        }
//        return ans;
//    }
//}
//
//ll queryHistoryMax(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return maxHistory[i];
//    } else {
//        int mid = (l + r) >> 1;
//        down(i, mid - l + 1, r - mid);
//        ll ans = LOWEST;
//        if (jobl <= mid) {
//            ans = max(ans, queryHistoryMax(jobl, jobr, l, mid, i << 1));
//        }
//        if (jobr > mid) {
//            ans = max(ans, queryHistoryMax(jobl, jobr, mid + 1, r, i << 1 | 1));
//        }
//        return ans;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    build(1, n, 1);
//    int op, jobl, jobr;
//    ll jobv;
//    for (int i = 1; i <= m; i++) {
//        cin >> op >> jobl >> jobr;
//        if (op == 1) {
//            cin >> jobv;
//            add(jobl, jobr, jobv, 1, n, 1);
//        } else if (op == 2) {
//            cin >> jobv;
//            setMin(jobl, jobr, jobv, 1, n, 1);
//        } else if (op == 3) {
//            cout << querySum(jobl, jobr, 1, n, 1) << "\n";
//        } else if (op == 4) {
//            cout << queryMax(jobl, jobr, 1, n, 1) << "\n";
//        } else {
//            cout << queryHistoryMax(jobl, jobr, 1, n, 1) << "\n";
//        }
//    }
//    return 0;
//}