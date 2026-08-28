package class205;

// 巧克力王国，C++版
// 一共n个点，每个点有坐标(x, y)，还有点权v
// 一共m条查询，格式 a b c，含义如下
// 满足 a * x + b * y < c 的所有点，打印点权累加和
// 1 <= n、m <= 5 * 10^4
// -10^9 <= a、b、x、y <= +10^9
// 测试链接 : https://www.luogu.com.cn/problem/P4475
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//struct Node {
//    ll x, y, v;
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
//const int MAXN = 50001;
//const ll INF = 1LL << 60;
//int n, m;
//
//int root;
//Node arr[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//
//ll sum[MAXN];
//ll xmin[MAXN];
//ll xmax[MAXN];
//ll ymin[MAXN];
//ll ymax[MAXN];
//
//void maintain(int i) {
//    sum[i] = sum[ls[i]] + sum[rs[i]] + arr[i].v;
//    xmin[i] = min(arr[i].x, min(xmin[ls[i]], xmin[rs[i]]));
//    xmax[i] = max(arr[i].x, max(xmax[ls[i]], xmax[rs[i]]));
//    ymin[i] = min(arr[i].y, min(ymin[ls[i]], ymin[rs[i]]));
//    ymax[i] = max(arr[i].y, max(ymax[ls[i]], ymax[rs[i]]));
//}
//
//int build(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    if (dimension == 0) {
//        nth_element(arr + l, arr + mid, arr + r + 1, XCmp);
//    } else {
//        nth_element(arr + l, arr + mid, arr + r + 1, YCmp);
//    }
//    ls[mid] = build(l, mid - 1, dimension ^ 1);
//    rs[mid] = build(mid + 1, r, dimension ^ 1);
//    maintain(mid);
//    return mid;
//}
//
//ll query(int a, int b, int c, int i) {
//    if (i == 0) {
//        return 0;
//    }
//    ll ax1 = xmin[i] * a;
//    ll ax2 = xmax[i] * a;
//    ll by1 = ymin[i] * b;
//    ll by2 = ymax[i] * b;
//    ll minv = min(ax1, ax2) + min(by1, by2);
//    ll maxv = max(ax1, ax2) + max(by1, by2);
//    if (minv >= c) {
//        return 0;
//    } else if (maxv < c) {
//        return sum[i];
//    } else {
//        ll ans = 0;
//        if (a * arr[i].x + b * arr[i].y < c) {
//            ans += arr[i].v;
//        }
//        ans += query(a, b, c, ls[i]);
//        ans += query(a, b, c, rs[i]);
//        return ans;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i].x >> arr[i].y >> arr[i].v;
//    }
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    root = build(1, n, 0);
//    for (int i = 1, a, b, c; i <= m; i++) {
//        cin >> a >> b >> c;
//        cout << query(a, b, c, root) << "\n";
//    }
//    return 0;
//}