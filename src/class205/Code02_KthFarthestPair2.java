package class205;

// K远点对，C++版
// 课上讲述K-D Tree的方法，java实现和C++实现，都有一个测试点超时
// 本题正解是旋转卡壳，计算几何专题时，会讲述正解，这个题会重新讲述
// 测试链接 : https://www.luogu.com.cn/problem/P4357
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，不是正解，无法通过全部测试

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
//const int MAXN = 100001;
//const int MAXK = 201;
//const ll INF = 1LL << 60;
//int n, k;
//
//Node arr[MAXN];
//
//int ls[MAXN];
//int rs[MAXN];
//
//ll xmin[MAXN];
//ll xmax[MAXN];
//ll ymin[MAXN];
//ll ymax[MAXN];
//
//priority_queue<ll, vector<ll>, greater<ll> > heap;
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
//int build1(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    if (l == r) {
//        ls[mid] = 0;
//        rs[mid] = 0;
//    } else {
//        randSelect(l, r, mid, dimension);
//        ls[mid] = build1(l, mid - 1, dimension ^ 1);
//        rs[mid] = build1(mid + 1, r, dimension ^ 1);
//    }
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
//    if (l == r) {
//        ls[mid] = 0;
//        rs[mid] = 0;
//    } else {
//        int dimension = variance(l, r, 0) >= variance(l, r, 1) ? 0 : 1;
//        randSelect(l, r, mid, dimension);
//        ls[mid] = build2(l, mid - 1);
//        rs[mid] = build2(mid + 1, r);
//    }
//    maintain(mid);
//    return mid;
//}
//
//ll dist(int a, int b) {
//    ll dx = arr[a].x - arr[b].x;
//    ll dy = arr[a].y - arr[b].y;
//    return dx * dx + dy * dy;
//}
//
//ll guess(int i, int rt) {
//    if (rt == 0) {
//        return 0;
//    }
//    ll x = arr[i].x;
//    ll y = arr[i].y;
//    ll dx = max(abs(x - xmin[rt]), abs(x - xmax[rt]));
//    ll dy = max(abs(y - ymin[rt]), abs(y - ymax[rt]));
//    return dx * dx + dy * dy;
//}
//
//void updateAns(int i, int l, int r) {
//    if (l > r) {
//        return;
//    }
//    int mid = (l + r) >> 1;
//    if (mid != i) {
//        ll cur = dist(i, mid);
//        if ((int) heap.size() < k) {
//            heap.push(cur);
//        } else if (cur > heap.top()) {
//            heap.pop();
//            heap.push(cur);
//        }
//    }
//    if (l < r) {
//        ll gl = guess(i, ls[mid]);
//        ll gr = guess(i, rs[mid]);
//        if (gl > gr) {
//            if (gl > heap.top()) {
//                updateAns(i, l, mid - 1);
//            }
//            if (gr > heap.top()) {
//                updateAns(i, mid + 1, r);
//            }
//        } else {
//            if (gr > heap.top()) {
//                updateAns(i, mid + 1, r);
//            }
//            if (gl > heap.top()) {
//                updateAns(i, l, mid - 1);
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    srand((unsigned)time(nullptr));
//    cin >> n >> k;
//    k <<= 1;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i].x >> arr[i].y;
//    }
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    // build1(1, n, 0);
//    build2(1, n);
//    for (int i = 1; i <= n; i++) {
//        updateAns(i, 1, n);
//    }
//    cout << heap.top() << "\n";
//    return 0;
//}