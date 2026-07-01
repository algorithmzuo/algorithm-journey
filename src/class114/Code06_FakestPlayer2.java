package class114;

// 最假女选手，C++版
// 这道题课上没讲，也是吉司机线段树的经典模版题，而且比题目5简单
// 如果理解了题目5，完全可以看懂如下的代码
// 给定一个长度为n的数组arr，然后是m条操作，类型如下
// 操作 1 l r x : arr[l..r]范围内，每个数都加上x
// 操作 2 l r x : arr[l..r]范围内，小于x的数都变成x
// 操作 3 l r x : arr[l..r]范围内，大于x的数都变成x
// 操作 4 l r   : 查询arr[l..r]累加和
// 操作 5 l r   : 查询arr[l..r]最大值
// 操作 6 l r   : 查询arr[l..r]最小值
// 1 <= n、m <= 5 * 10^5    累加和需要long类型
// 测试链接 : https://loj.ac/p/6565
// 测试链接 : https://www.luogu.com.cn/problem/P10639
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 500001;
//const int INF = 1000000001;
//int n, m;
//
//int arr[MAXN];
//ll sum[MAXN << 2];
//
//int maxv[MAXN << 2];
//int cntMax[MAXN << 2];
//int sem[MAXN << 2];
//
//int minv[MAXN << 2];
//int cntMin[MAXN << 2];
//int sim[MAXN << 2];
//
//int addTag[MAXN << 2];
//
//void up(int i) {
//    int l = i << 1;
//    int r = i << 1 | 1;
//    sum[i] = sum[l] + sum[r];
//    if (maxv[l] > maxv[r]) {
//        maxv[i] = maxv[l];
//        cntMax[i] = cntMax[l];
//        sem[i] = max(sem[l], maxv[r]);
//    } else if (maxv[l] < maxv[r]) {
//        maxv[i] = maxv[r];
//        cntMax[i] = cntMax[r];
//        sem[i] = max(maxv[l], sem[r]);
//    } else {
//        maxv[i] = maxv[l];
//        cntMax[i] = cntMax[l] + cntMax[r];
//        sem[i] = max(sem[l], sem[r]);
//    }
//    if (minv[l] < minv[r]) {
//        minv[i] = minv[l];
//        cntMin[i] = cntMin[l];
//        sim[i] = min(sim[l], minv[r]);
//    } else if (minv[l] > minv[r]) {
//        minv[i] = minv[r];
//        cntMin[i] = cntMin[r];
//        sim[i] = min(minv[l], sim[r]);
//    } else {
//        minv[i] = minv[l];
//        cntMin[i] = cntMin[l] + cntMin[r];
//        sim[i] = min(sim[l], sim[r]);
//    }
//}
//
//void lazyAdd(int i, int n, int v) {
//    sum[i] += 1LL * v * n;
//    maxv[i] += v;
//    sem[i] += sem[i] == -INF ? 0 : v;
//    minv[i] += v;
//    sim[i] += sim[i] == INF ? 0 : v;
//    addTag[i] += v;
//}
//
//void lazySetMin(int i, int v) {
//    sum[i] += 1LL * (v - maxv[i]) * cntMax[i];
//    if (minv[i] == maxv[i]) {
//        minv[i] = v;
//    } else if (sim[i] == maxv[i]) {
//        sim[i] = v;
//    }
//    maxv[i] = v;
//}
//
//void lazySetMax(int i, int v) {
//    sum[i] += 1LL * (v - minv[i]) * cntMin[i];
//    if (maxv[i] == minv[i]) {
//        maxv[i] = v;
//    } else if (sem[i] == minv[i]) {
//        sem[i] = v;
//    }
//    minv[i] = v;
//}
//
//void down(int i, int ln, int rn) {
//    int l = i << 1;
//    int r = i << 1 | 1;
//    if (addTag[i] != 0) {
//        lazyAdd(l, ln, addTag[i]);
//        lazyAdd(r, rn, addTag[i]);
//        addTag[i] = 0;
//    }
//    if (maxv[l] > maxv[i]) {
//        lazySetMin(l, maxv[i]);
//    }
//    if (minv[l] < minv[i]) {
//        lazySetMax(l, minv[i]);
//    }
//    if (maxv[r] > maxv[i]) {
//        lazySetMin(r, maxv[i]);
//    }
//    if (minv[r] < minv[i]) {
//        lazySetMax(r, minv[i]);
//    }
//}
//
//void build(int l, int r, int i) {
//    if (l == r) {
//        sum[i] = maxv[i] = minv[i] = arr[l];
//        sem[i] = -INF;
//        sim[i] = INF;
//        cntMax[i] = cntMin[i] = 1;
//    } else {
//        int mid = (l + r) >> 1;
//        build(l, mid, i << 1);
//        build(mid + 1, r, i << 1 | 1);
//        up(i);
//    }
//    addTag[i] = 0;
//}
//
//void add(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        lazyAdd(i, r - l + 1, jobv);
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
//void setMax(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobv <= minv[i]) {
//        return;
//    }
//    if (jobl <= l && r <= jobr && jobv < sim[i]) {
//        lazySetMax(i, jobv);
//    } else {
//        int mid = (l + r) >> 1;
//        down(i, mid - l + 1, r - mid);
//        if (jobl <= mid) {
//            setMax(jobl, jobr, jobv, l, mid, i << 1);
//        }
//        if (jobr > mid) {
//            setMax(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
//        }
//        up(i);
//    }
//}
//
//void setMin(int jobl, int jobr, int jobv, int l, int r, int i) {
//    if (jobv >= maxv[i]) {
//        return;
//    }
//    if (jobl <= l && r <= jobr && sem[i] < jobv) {
//        lazySetMin(i, jobv);
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
//int queryMax(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return maxv[i];
//    } else {
//        int mid = (l + r) >> 1;
//        down(i, mid - l + 1, r - mid);
//        int ans = -INF;
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
//int queryMin(int jobl, int jobr, int l, int r, int i) {
//    if (jobl <= l && r <= jobr) {
//        return minv[i];
//    } else {
//        int mid = (l + r) >> 1;
//        down(i, mid - l + 1, r - mid);
//        int ans = INF;
//        if (jobl <= mid) {
//            ans = min(ans, queryMin(jobl, jobr, l, mid, i << 1));
//        }
//        if (jobr > mid) {
//            ans = min(ans, queryMin(jobl, jobr, mid + 1, r, i << 1 | 1));
//        }
//        return ans;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    build(1, n, 1);
//    cin >> m;
//    for (int i = 1, op, l, r, x; i <= m; i++) {
//        cin >> op;
//        cin >> l;
//        cin >> r;
//        if (op == 1) {
//            cin >> x;
//            add(l, r, x, 1, n, 1);
//        } else if (op == 2) {
//            cin >> x;
//            setMax(l, r, x, 1, n, 1);
//        } else if (op == 3) {
//            cin >> x;
//            setMin(l, r, x, 1, n, 1);
//        } else if (op == 4) {
//            cout << querySum(l, r, 1, n, 1) << "\n";
//        } else if (op == 5) {
//            cout << queryMax(l, r, 1, n, 1) << "\n";
//        } else {
//            cout << queryMin(l, r, 1, n, 1) << "\n";
//        }
//    }
//    return 0;
//}