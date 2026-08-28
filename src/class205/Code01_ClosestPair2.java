package class205;

// 平面最近点对，C++版
// 课上讲述KDT的方法，本题正解是平面最近点对的分治算法，计算几何专题会讲述正解
// 一共n个点，每个点给定坐标(x, y)，输出距离最近的两个点的距离平方
// 2 <= n <= 4 * 10^5
// -10^7 <= x、y <= +10^7
// 测试链接 : https://www.luogu.com.cn/problem/P7883
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//struct Node {
//    ll x;
//    ll y;
//};
//
//bool XCmp(Node a, Node b) {
//    return a.x < b.x;
//}
//
//bool YCmp(Node a, Node b) {
//    return a.y < b.y;
//}
//
//const int MAXN = 400001;
//const ll INF = 1LL << 60;
//int n;
//
//int root;
//Node arr[MAXN];
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
//    xmin[i] = min(arr[i].x, min(xmin[ls[i]], xmin[rs[i]]));
//    xmax[i] = max(arr[i].x, max(xmax[ls[i]], xmax[rs[i]]));
//    ymin[i] = min(arr[i].y, min(ymin[ls[i]], ymin[rs[i]]));
//    ymax[i] = max(arr[i].y, max(ymax[ls[i]], ymax[rs[i]]));
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
//    ls[mid] = build1(l, mid - 1, dimension ^ 1);
//    rs[mid] = build1(mid + 1, r, dimension ^ 1);
//    maintain(mid);
//    return mid;
//}
//
//double variance(int l, int r, int dimension) {
//    double siz = r - l + 1, sum = 0, avg = 0, dif = 0;
//    for (int i = l; i <= r; i++) {
//        sum += dimension == 0 ? arr[i].x : arr[i].y;
//    }
//    avg = sum / siz;
//    sum = 0;
//    for (int i = l; i <= r; i++) {
//        dif = (dimension == 0 ? arr[i].x : arr[i].y) - avg;
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
//    ls[mid] = build2(l, mid - 1);
//    rs[mid] = build2(mid + 1, r);
//    maintain(mid);
//    return mid;
//}
//
//ll dist(int qi, int i) {
//    ll dx = arr[qi].x - arr[i].x;
//    ll dy = arr[qi].y - arr[i].y;
//    return dx * dx + dy * dy;
//}
//
//ll guess(int qi, int i) {
//    if (i == 0) {
//        return INF;
//    }
//    ll qx = arr[qi].x;
//    ll qy = arr[qi].y;
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
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i].x >> arr[i].y;
//    }
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    // root = build1(1, n, 0);
//    root = build2(1, n);
//    ans = dist(1, 2);
//    for (int i = 1; i <= n; i++) {
//        updateAns(i, root);
//        if (ans == 0) {
//            break;
//        }
//    }
//    cout << ans << "\n";
//    return 0;
//}