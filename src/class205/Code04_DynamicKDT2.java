package class205;

// 动态KDT模版题1，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4148
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int x;
//    int y;
//    int v;
//};
//
//const int MAXN = 200001;
//const int MAXP = 19;
//const int INF = 1 << 30;
//int n, cntn;
//
//Node arr[MAXN];
//
//int ls[MAXN];
//int rs[MAXN];
//int sum[MAXN];
//int xmin[MAXN];
//int xmax[MAXN];
//int ymin[MAXN];
//int ymax[MAXN];
//
//int root[MAXP];
//
//int first, last;
//
//void partition(int l, int r, int pivot, int dimension) {
//    first = l;
//    last = r;
//    int i = l;
//    while (i <= last) {
//        int cur = dimension == 0 ? arr[i].x : arr[i].y;
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
//        int pivot = dimension == 0 ? arr[idx].x : arr[idx].y;
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
//    sum[i] = arr[i].v + sum[ls[i]] + sum[rs[i]];
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
//void add(int x, int y, int v) {
//    cntn++;
//    arr[cntn].x = x;
//    arr[cntn].y = y;
//    arr[cntn].v = v;
//    int p = 0, siz = 1;
//    while (root[p] != 0) {
//        root[p] = 0;
//        p++;
//        siz <<= 1;
//    }
//    root[p] = build(cntn - siz + 1, cntn, 0);
//}
//
//int query(int x1, int y1, int x2, int y2, int i) {
//    if (i == 0) {
//        return 0;
//    }
//    if (xmax[i] < x1 || x2 < xmin[i] || ymax[i] < y1 || y2 < ymin[i]) {
//        return 0;
//    }
//    if (x1 <= xmin[i] && xmax[i] <= x2 && y1 <= ymin[i] && ymax[i] <= y2) {
//        return sum[i];
//    }
//    int ans = 0;
//    if (x1 <= arr[i].x && arr[i].x <= x2 && y1 <= arr[i].y && arr[i].y <= y2) {
//        ans += arr[i].v;
//    }
//    ans += query(x1, y1, x2, y2, ls[i]);
//    ans += query(x1, y1, x2, y2, rs[i]);
//    return ans;
//}
//
//int query(int x1, int y1, int x2, int y2) {
//    int ans = 0;
//    for (int p = 0; p < MAXP; p++) {
//        ans += query(x1, y1, x2, y2, root[p]);
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    int op, a, b, c, d, lastAns;
//    cin >> op;
//    lastAns = 0;
//    while (op != 3) {
//        cin >> a >> b >> c;
//        a ^= lastAns;
//        b ^= lastAns;
//        c ^= lastAns;
//        if (op == 1) {
//            add(a, b, c);
//        } else {
//            cin >> d;
//            d ^= lastAns;
//            lastAns = query(a, b, c, d);
//            cout << lastAns << "\n";
//        }
//        cin >> op;
//    }
//    return 0;
//}