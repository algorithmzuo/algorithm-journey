package class205;

// 查询第k远的点，C++版
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
//
//const int MAXN = 100001;
//const ll INF = 1LL << 60;
//int n, m;
//ll qx, qy;
//int qk;
//
//PointNode arr[MAXN];
//
//int ls[MAXN];
//int rs[MAXN];
//ll xmin[MAXN];
//ll xmax[MAXN];
//ll ymin[MAXN];
//ll ymax[MAXN];
//
//priority_queue<HeapNode> heap;
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
//ll dist(ll x1, ll y1, ll x2, ll y2) {
//    ll dx = x1 - x2;
//    ll dy = y1 - y2;
//    return dx * dx + dy * dy;
//}
//
//ll guess(int rt) {
//    if (rt == 0) {
//        return 0;
//    }
//    ll dx = max(abs(qx - xmin[rt]), abs(qx - xmax[rt]));
//    ll dy = max(abs(qy - ymin[rt]), abs(qy - ymax[rt]));
//    return dx * dx + dy * dy;
//}
//
//void updateAns(int l, int r) {
//    if (l > r) {
//        return;
//    }
//    int mid = (l + r) >> 1;
//    ll d = dist(qx, qy, arr[mid].x, arr[mid].y);
//    if (d > heap.top().dist || (d == heap.top().dist && arr[mid].id < heap.top().id)) {
//        heap.pop();
//        heap.push({d, arr[mid].id});
//    }
//    if (l < r) {
//        ll gl = guess(ls[mid]);
//        ll gr = guess(rs[mid]);
//        if (gl > gr) {
//            if (gl >= heap.top().dist) {
//                updateAns(l, mid - 1);
//            }
//            if (gr >= heap.top().dist) {
//                updateAns(mid + 1, r);
//            }
//        } else {
//            if (gr >= heap.top().dist) {
//                updateAns(mid + 1, r);
//            }
//            if (gl >= heap.top().dist) {
//                updateAns(l, mid - 1);
//            }
//        }
//    }
//}
//
//int query() {
//    while (!heap.empty()) {
//        heap.pop();
//    }
//    for (int i = 1; i <= qk; i++) {
//        heap.push({-1, 0});
//    }
//    updateAns(1, n);
//    return heap.top().id;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    srand((unsigned)time(nullptr));
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i].x >> arr[i].y;
//        arr[i].id = i;
//    }
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    build(1, n, 0);
//    cin >> m;
//    for (int i = 1; i <= m; i++) {
//        cin >> qx >> qy >> qk;
//        cout << query() << "\n";
//    }
//    return 0;
//}