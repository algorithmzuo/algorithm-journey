package class205;

// 平面最近点对，C++版
// 课上讲述K-D Tree的方法，java实现大量测试点超时，C++实现可以完全通过
// 本题正解是归并分治，计算几何专题时，会讲述正解，这个题会重新讲述
// 测试链接 : https://www.luogu.com.cn/problem/P7883
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//using ll = long long;
//
//const int MAXN = 400001;
//const ll INF = 1LL << 60;
//int n;
//
//ll arr[MAXN][2];
//
//int ls[MAXN];
//int rs[MAXN];
//
//ll xmin[MAXN];
//ll xmax[MAXN];
//ll ymin[MAXN];
//ll ymax[MAXN];
//
//ll ans;
//
//int first, last;
//
//void swap(int i, int j) {
//    ll tmp = arr[i][0]; arr[i][0] = arr[j][0]; arr[j][0] = tmp;
//    tmp = arr[i][1]; arr[i][1] = arr[j][1]; arr[j][1] = tmp;
//}
//
//void partition(int l, int r, ll pivot, int dimension) {
//    first = l;
//    last = r;
//    int i = l;
//    while (i <= last) {
//        if (arr[i][dimension] == pivot) {
//            i++;
//        } else if (arr[i][dimension] < pivot) {
//            swap(first++, i++);
//        } else {
//            swap(i, last--);
//        }
//    }
//}
//
//void randSelect(int l, int r, int i, int dimension) {
//    while (l <= r) {
//        ll pivot = arr[l + rand() % (r - l + 1)][dimension];
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
//    xmin[i] = min(arr[i][0], min(xmin[ls[i]], xmin[rs[i]]));
//    xmax[i] = max(arr[i][0], max(xmax[ls[i]], xmax[rs[i]]));
//    ymin[i] = min(arr[i][1], min(ymin[ls[i]], ymin[rs[i]]));
//    ymax[i] = max(arr[i][1], max(ymax[ls[i]], ymax[rs[i]]));
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
//        sum += arr[i][dimension];
//    }
//    avg = sum / siz;
//    sum = 0;
//    for (int i = l; i <= r; i++) {
//        dif = arr[i][dimension] - avg;
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
//ll guess(int i, int rt) {
//    if (rt == 0) {
//        return INF;
//    }
//    ll x = arr[i][0];
//    ll y = arr[i][1];
//    ll dx = x < xmin[rt] ? (xmin[rt] - x) : (x > xmax[rt] ? (x - xmax[rt]) : 0);
//    ll dy = y < ymin[rt] ? (ymin[rt] - y) : (y > ymax[rt] ? (y - ymax[rt]) : 0);
//    return dx * dx + dy * dy;
//}
//
//ll dist(int a, int b) {
//    ll dx = arr[a][0] - arr[b][0];
//    ll dy = arr[a][1] - arr[b][1];
//    return dx * dx + dy * dy;
//}
//
//void updateAns(int i, int l, int r) {
//    if (l > r) {
//        return;
//    }
//    int mid = (l + r) >> 1;
//    if (mid != i) {
//        ans = min(ans, dist(i, mid));
//    }
//    if (l < r) {
//        ll gl = guess(i, ls[mid]);
//        ll gr = guess(i, rs[mid]);
//        if (gl < gr) {
//            if (gl < ans) {
//                updateAns(i, l, mid - 1);
//            }
//            if (gr < ans) {
//                updateAns(i, mid + 1, r);
//            }
//        } else {
//            if (gr < ans) {
//                updateAns(i, mid + 1, r);
//            }
//            if (gl < ans) {
//                updateAns(i, l, mid - 1);
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i][0] >> arr[i][1];
//    }
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    // build1(1, n, 0);
//    build2(1, n);
//    ans = dist(1, 2);
//    for (int i = 1; i <= n; i++) {
//        updateAns(i, 1, n);
//        if (ans == 0) {
//            break;
//        }
//    }
//    cout << ans << "\n";
//    return 0;
//}