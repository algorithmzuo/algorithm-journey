package class205;

// 查询第k远的点，C++版
// 一共n个点，编号1~n，每个点给定坐标(x, y)
// 一共m条查询，格式 qx qy qk，查询距离(qx, qy)第qk远的点，打印该点的编号
// 如果多个点到(qx, qy)的距离相同，那么编号较小的点认为距离更远
// 1 <= n <= 10^5
// 1 <= m <= 10^4
// 1 <= qk <= 20
// -10^9 <= 坐标值 <= +10^9
// 测试链接 : https://www.luogu.com.cn/problem/P2093
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//struct PointNode {
//    ll x;
//    ll y;
//    int id;
//};
//
//bool XCmp(PointNode a, PointNode b) {
//    return a.x < b.x;
//}
//
//bool YCmp(PointNode a, PointNode b) {
//    return a.y < b.y;
//}
//
//struct HeapNode {
//    ll dist;
//    int id;
//
//    bool operator <(const HeapNode &other) const {
//        if (dist != other.dist) {
//            return dist > other.dist;
//        }
//        return id < other.id;
//    }
//};
//
//const int MAXN = 100001;
//const ll INF = 1LL << 60;
//int n, m;
//
//int root;
//PointNode arr[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//ll xmin[MAXN];
//ll xmax[MAXN];
//ll ymin[MAXN];
//ll ymax[MAXN];
//
//priority_queue<HeapNode> heap;
//
//void maintain(int i) {
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
//ll dist(ll x1, ll y1, ll x2, ll y2) {
//    ll dx = x1 - x2;
//    ll dy = y1 - y2;
//    return dx * dx + dy * dy;
//}
//
//ll guess(int qx, int qy, int i) {
//    if (i == 0) {
//        return 0;
//    }
//    ll dx = max(abs(qx - xmin[i]), abs(qx - xmax[i]));
//    ll dy = max(abs(qy - ymin[i]), abs(qy - ymax[i]));
//    return dx * dx + dy * dy;
//}
//
//void updateAns(int qx, int qy, int i) {
//    if (i == 0) {
//        return;
//    }
//    ll d = dist(qx, qy, arr[i].x, arr[i].y);
//    if (d > heap.top().dist || (d == heap.top().dist && arr[i].id < heap.top().id)) {
//        heap.pop();
//        heap.push({d, arr[i].id});
//    }
//    ll gl = guess(qx, qy, ls[i]);
//    ll gr = guess(qx, qy, rs[i]);
//    if (gl > gr) {
//        if (gl >= heap.top().dist) {
//            updateAns(qx, qy, ls[i]);
//        }
//        if (gr >= heap.top().dist) {
//            updateAns(qx, qy, rs[i]);
//        }
//    } else {
//        if (gr >= heap.top().dist) {
//            updateAns(qx, qy, rs[i]);
//        }
//        if (gl >= heap.top().dist) {
//            updateAns(qx, qy, ls[i]);
//        }
//    }
//}
//
//int query(int qx, int qy, int qk) {
//    while (!heap.empty()) {
//        heap.pop();
//    }
//    for (int i = 1; i <= qk; i++) {
//        heap.push({-1, 0});
//    }
//    updateAns(qx, qy, root);
//    return heap.top().id;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i].x >> arr[i].y;
//        arr[i].id = i;
//    }
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    root = build(1, n, 0);
//    cin >> m;
//    for (int i = 1, qx, qy, qk; i <= m; i++) {
//        cin >> qx >> qy >> qk;
//        cout << query(qx, qy, qk) << "\n";
//    }
//    return 0;
//}