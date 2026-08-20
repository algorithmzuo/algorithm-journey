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
//    ll x;
//    ll y;
//    ll v;
//};
//
//const int MAXN = 50001;
//const ll INF = 1LL << 60;
//int n, m;
//
//Node arr[MAXN];
//
//int ls[MAXN];
//int rs[MAXN];
//
//ll sum[MAXN];
//ll xmin[MAXN];
//ll xmax[MAXN];
//ll ymin[MAXN];
//ll ymax[MAXN];
//
//int first, last;
//
//void partition(int l, int r, ll pivot, int dimension) {
//    first = l;
//    last = r;
//    int i = l;
//    while (i <= last) {
//        ll cur = dimension == 0 ? arr[i].x : arr[i].y;
//        if (cur == pivot) {
//            i++;
//        } else if (cur < pivot) {
//            swap(arr[first++], arr[i++]);
//        } else {
//            swap(arr[i], arr[last--]);
//        }
//    }
//}
//
//void randSelect(int l, int r, int i, int dimension) {
//    while (l <= r) {
//        int idx = l + rand() % (r - l + 1);
//        ll pivot = dimension == 0 ? arr[idx].x : arr[idx].y;
//        partition(l, r, pivot, dimension);
//        if (i < first) {
//            r = first - 1;
//        } else if (i > last) {
//            l = last + 1;
//        } else {
//            break;
//        }
//    }
//}
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
//    if (l == r) {
//        ls[mid] = 0;
//        rs[mid] = 0;
//    } else {
//        randSelect(l, r, mid, dimension);
//        ls[mid] = build(l, mid - 1, dimension ^ 1);
//        rs[mid] = build(mid + 1, r, dimension ^ 1);
//    }
//    maintain(mid);
//    return mid;
//}
//
//ll query(int a, int b, int c, int l, int r) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    ll ax1 = xmin[mid] * a;
//    ll ax2 = xmax[mid] * a;
//    ll by1 = ymin[mid] * b;
//    ll by2 = ymax[mid] * b;
//    ll minv = min(ax1, ax2) + min(by1, by2);
//    ll maxv = max(ax1, ax2) + max(by1, by2);
//    if (minv >= c) {
//        return 0;
//    } else if (maxv < c) {
//        return sum[mid];
//    } else {
//        ll ans = 0;
//        if (a * arr[mid].x + b * arr[mid].y < c) {
//            ans += arr[mid].v;
//        }
//        ans += query(a, b, c, l, mid - 1);
//        ans += query(a, b, c, mid + 1, r);
//        return ans;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    srand((unsigned)time(nullptr));
//    cin >> n >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i].x >> arr[i].y >> arr[i].v;
//    }
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    build(1, n, 0);
//    for (int i = 1, a, b, c; i <= m; i++) {
//        cin >> a >> b >> c;
//        cout << query(a, b, c, 1, n) << "\n";
//    }
//    return 0;
//}