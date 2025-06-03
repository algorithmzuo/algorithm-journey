package class171;

// 拦截导弹，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P2487
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int i, h, v;
//};
//
//bool Cmp1(Node a, Node b) {
//    return a.h > b.h;
//}
//
//bool Cmp2(Node a, Node b) {
//    return a.h < b.h;
//}
//
//const int MAXN = 50001;
//int n, s;
//int h[MAXN];
//int v[MAXN];
//int sortv[MAXN];
//
//Node arr[MAXN];
//
//int treelen[MAXN];
//double treecnt[MAXN];
//
//int f1[MAXN];
//double g1[MAXN];
//
//int f2[MAXN];
//double g2[MAXN];
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int len, double cnt) {
//    while (i <= s) {
//        if (len > treelen[i]) {
//            treelen[i] = len;
//            treecnt[i] = cnt;
//        } else if (len == treelen[i]) {
//            treecnt[i] += cnt;
//        }
//        i += lowbit(i);
//    }
//}
//
//int querylen;
//double querycnt;
//
//void query(int i) {
//    querylen = 0;
//    querycnt = 0;
//    while (i > 0) {
//        if (treelen[i] > querylen) {
//            querylen = treelen[i];
//            querycnt = treecnt[i];
//        } else if (treelen[i] == querylen) {
//            querycnt += treecnt[i];
//        }
//        i -= lowbit(i);
//    }
//}
//
//void clear(int i) {
//    while (i <= s) {
//        treelen[i] = 0;
//        treecnt[i] = 0;
//        i += lowbit(i);
//    }
//}
//
//void merge1(int l, int m, int r) {
//    for (int i = l; i <= r; i++) {
//        arr[i].i = i;
//        arr[i].h = h[i];
//        arr[i].v = v[i];
//    }
//    sort(arr + l, arr + m + 1, Cmp1);
//    sort(arr + m + 1, arr + r + 1, Cmp1);
//    int p1, p2;
//    for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
//        while (p1 + 1 <= m && arr[p1 + 1].h >= arr[p2].h) {
//            p1++;
//            add(s - arr[p1].v + 1, f1[arr[p1].i], g1[arr[p1].i]);
//        }
//        query(s - arr[p2].v + 1);
//        if (querylen + 1 > f1[arr[p2].i]) {
//            f1[arr[p2].i] = querylen + 1;
//            g1[arr[p2].i] = querycnt;
//        } else if (querylen + 1 == f1[arr[p2].i]) {
//            g1[arr[p2].i] += querycnt;
//        }
//    }
//    for (int i = l; i <= p1; i++) {
//        clear(s - arr[i].v + 1);
//    }
//}
//
//void cdq1(int l, int r) {
//    if (l == r) {
//        return;
//    }
//    int m = (l + r) / 2;
//    cdq1(l, m);
//    merge1(l, m, r);
//    cdq1(m + 1, r);
//}
//
//void merge2(int l, int m, int r) {
//    for (int i = l; i <= r; i++) {
//        arr[i].i = i;
//        arr[i].h = h[i];
//        arr[i].v = v[i];
//    }
//    sort(arr + l, arr + m + 1, Cmp2);
//    sort(arr + m + 1, arr + r + 1, Cmp2);
//    int p1, p2;
//    for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
//        while (p1 + 1 <= m && arr[p1 + 1].h <= arr[p2].h) {
//            p1++;
//            add(arr[p1].v, f2[arr[p1].i], g2[arr[p1].i]);
//        }
//        query(arr[p2].v);
//        if (querylen + 1 > f2[arr[p2].i]) {
//            f2[arr[p2].i] = querylen + 1;
//            g2[arr[p2].i] = querycnt;
//        } else if (querylen + 1 == f2[arr[p2].i]) {
//            g2[arr[p2].i] += querycnt;
//        }
//    }
//    for (int i = l; i <= p1; i++) {
//        clear(arr[i].v);
//    }
//}
//
//void cdq2(int l, int r) {
//    if (l == r) {
//        return;
//    }
//    int m = (l + r) / 2;
//    cdq2(l, m);
//    merge2(l, m, r);
//    cdq2(m + 1, r);
//}
//
//int lower(int num) {
//    int l = 1, r = s, ans = 1;
//    while (l <= r) {
//        int mid = (l + r) / 2;
//        if (sortv[mid] >= num) {
//            ans = mid;
//            r = mid - 1;
//        } else {
//            l = mid + 1;
//        }
//    }
//    return ans;
//}
//
//void prepare() {
//    for (int i = 1; i <= n; i++) {
//        sortv[i] = v[i];
//    }
//    sort(sortv + 1, sortv + n + 1);
//    s = 1;
//    for (int i = 2; i <= n; i++) {
//        if (sortv[s] != sortv[i]) {
//            sortv[++s] = sortv[i];
//        }
//    }
//    for (int i = 1; i <= n; i++) {
//        v[i] = lower(v[i]);
//    }
//    for (int i = 1; i <= n; i++) {
//        f1[i] = f2[i] = 1;
//        g1[i] = g2[i] = 1.0;
//    }
//}
//
//void compute() {
//    cdq1(1, n);
//    for (int l = 1, r = n; l < r; l++, r--) {
//        swap(h[l], h[r]);
//        swap(v[l], v[r]);
//    }
//    cdq2(1, n);
//    for (int l = 1, r = n; l < r; l++, r--) {
//        swap(f2[l], f2[r]);
//        swap(g2[l], g2[r]);
//    }
//}
//
//int main() {
//    scanf("%d", &n);
//    for (int i = 1; i <= n; i++) {
//        scanf("%d%d", &h[i], &v[i]);
//    }
//    prepare();
//    compute();
//    int len = 0;
//    double cnt = 0.0;
//    for (int i = 1; i <= n; i++) {
//        len = max(len, f1[i]);
//    }
//    for (int i = 1; i <= n; i++) {
//        if (f1[i] == len) {
//            cnt += g1[i];
//        }
//    }
//    printf("%d\n", len);
//    for (int i = 1; i <= n; i++) {
//        if (f1[i] + f2[i] - 1 < len) {
//            printf("0 ");
//        } else {
//            printf("%.5f ", g1[i] * g2[i] / cnt);
//        }
//    }
//    printf("\n");
//    return 0;
//}