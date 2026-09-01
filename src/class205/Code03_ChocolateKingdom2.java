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
//const int MAXN = 50001;
//const ll INF = 1LL << 60;
//int n, m;
//
//ll x[MAXN];
//ll y[MAXN];
//ll v[MAXN];
//int arr[MAXN];
//
//int root;
//int ls[MAXN];
//int rs[MAXN];
//ll sum[MAXN];
//ll xmin[MAXN];
//ll xmax[MAXN];
//ll ymin[MAXN];
//ll ymax[MAXN];
//
//int compareNode(int i, int j, int dimension) {
//    ll a = dimension == 0 ? x[i] : y[i];
//    ll b = dimension == 0 ? x[j] : y[j];
//    return a != b ? (a < b ? -1 : 1) : (i - j);
//}
//
//struct Cmp {
//    int dimension;
//
//    bool operator()(int a, int b) const {
//        return compareNode(a, b, dimension) < 0;
//    }
//};
//
//void maintain(int i) {
//    sum[i] = v[i] + sum[ls[i]] + sum[rs[i]];
//    xmin[i] = min(x[i], min(xmin[ls[i]], xmin[rs[i]]));
//    xmax[i] = max(x[i], max(xmax[ls[i]], xmax[rs[i]]));
//    ymin[i] = min(y[i], min(ymin[ls[i]], ymin[rs[i]]));
//    ymax[i] = max(y[i], max(ymax[ls[i]], ymax[rs[i]]));
//}
//
//int build(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    nth_element(arr + l, arr + mid, arr + r + 1, Cmp{dimension});
//    int rt = arr[mid];
//    ls[rt] = build(l, mid - 1, dimension ^ 1);
//    rs[rt] = build(mid + 1, r, dimension ^ 1);
//    maintain(rt);
//    return rt;
//}
//
//ll query(ll a, ll b, ll c, int i) {
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
//        if (a * x[i] + b * y[i] < c) {
//            ans += v[i];
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
//        cin >> x[i] >> y[i] >> v[i];
//        arr[i] = i;
//    }
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    root = build(1, n, 0);
//    ll a, b, c;
//    for (int i = 1; i <= m; i++) {
//        cin >> a >> b >> c;
//        cout << query(a, b, c, root) << "\n";
//    }
//    return 0;
//}