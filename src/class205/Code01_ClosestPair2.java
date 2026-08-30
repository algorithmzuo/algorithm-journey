package class205;

// 平面最近点对，C++版
// 课上讲述K-D树的方法，但这不是正解，刻意设计测试是可以卡住的
// 本题复杂度正确的正解，是平面最近点对的分治算法，计算几何专题会讲述
// 一共n个点，每个点给定坐标(x, y)，输出最近两个点的距离，保留四位小数
// 2 <= n <= 2 * 10^5
// 0 <= x、y <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P1429
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 200001;
//const ll INF = 1LL << 60;
//int n;
//
//ll x[MAXN];
//ll y[MAXN];
//int arr[MAXN];
//
//int root;
//int ls[MAXN];
//int rs[MAXN];
//ll xmin[MAXN];
//ll xmax[MAXN];
//ll ymin[MAXN];
//ll ymax[MAXN];
//
//ll ans;
//
//void maintain(int i) {
//    xmin[i] = min(x[i], min(xmin[ls[i]], xmin[rs[i]]));
//    xmax[i] = max(x[i], max(xmax[ls[i]], xmax[rs[i]]));
//    ymin[i] = min(y[i], min(ymin[ls[i]], ymin[rs[i]]));
//    ymax[i] = max(y[i], max(ymax[ls[i]], ymax[rs[i]]));
//}
//
//bool XCmp(int a, int b) {
//    return x[a] < x[b];
//}
//
//bool YCmp(int a, int b) {
//    return y[a] < y[b];
//}
//
//int build1(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    if (dimension == 0) {
//        nth_element(arr + l, arr + mid, arr + r + 1, XCmp);
//    } else {
//        nth_element(arr + l, arr + mid, arr + r + 1, YCmp);
//    }
//    int rt = arr[mid];
//    ls[rt] = build1(l, mid - 1, dimension ^ 1);
//    rs[rt] = build1(mid + 1, r, dimension ^ 1);
//    maintain(rt);
//    return rt;
//}
//
//double variance(int l, int r, int dimension) {
//    double siz = r - l + 1, sum = 0, avg = 0, dif = 0;
//    for (int i = l; i <= r; i++) {
//        sum += dimension == 0 ? x[arr[i]] : y[arr[i]];
//    }
//    avg = sum / siz;
//    sum = 0;
//    for (int i = l; i <= r; i++) {
//        dif = (dimension == 0 ? x[arr[i]] : y[arr[i]]) - avg;
//        sum += dif * dif;
//    }
//    return sum / siz;
//}
//
//int build2(int l, int r) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    int dimension = variance(l, r, 0) >= variance(l, r, 1) ? 0 : 1;
//    if (dimension == 0) {
//        nth_element(arr + l, arr + mid, arr + r + 1, XCmp);
//    } else {
//        nth_element(arr + l, arr + mid, arr + r + 1, YCmp);
//    }
//    int rt = arr[mid];
//    ls[rt] = build2(l, mid - 1);
//    rs[rt] = build2(mid + 1, r);
//    maintain(rt);
//    return rt;
//}
//
//ll dist(int qi, int i) {
//    ll dx = x[qi] - x[i];
//    ll dy = y[qi] - y[i];
//    return dx * dx + dy * dy;
//}
//
//ll guess(int qi, int i) {
//    if (i == 0) {
//        return INF;
//    }
//    ll qx = x[qi];
//    ll qy = y[qi];
//    ll dx = qx < xmin[i] ? (xmin[i] - qx) : (qx > xmax[i] ? (qx - xmax[i]) : 0);
//    ll dy = qy < ymin[i] ? (ymin[i] - qy) : (qy > ymax[i] ? (qy - ymax[i]) : 0);
//    return dx * dx + dy * dy;
//}
//
//void updateAns(int qi, int i) {
//    if (i == 0) {
//        return;
//    }
//    if (qi != i) {
//        ans = min(ans, dist(qi, i));
//    }
//    ll gl = guess(qi, ls[i]);
//    ll gr = guess(qi, rs[i]);
//    if (gl < gr) {
//        if (gl < ans) {
//            updateAns(qi, ls[i]);
//        }
//        if (gr < ans) {
//            updateAns(qi, rs[i]);
//        }
//    } else {
//        if (gr < ans) {
//            updateAns(qi, rs[i]);
//        }
//        if (gl < ans) {
//            updateAns(qi, ls[i]);
//        }
//    }
//}
//
//int main() {
//    scanf("%d", &n);
//    for (int i = 1; i <= n; i++) {
//        scanf("%lld%lld", &x[i], &y[i]);
//        arr[i] = i;
//    }
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    root = build1(1, n, 0);
//    // root = build2(1, n);
//    ans = dist(1, 2);
//    for (int i = 1; i <= n; i++) {
//        updateAns(i, root);
//        if (ans == 0) {
//            break;
//        }
//    }
//    printf("%.4f\n", sqrt((double) ans));
//    return 0;
//}