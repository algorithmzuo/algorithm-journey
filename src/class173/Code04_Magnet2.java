package class173;

// 磁力块，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P10590
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int x, y, m, p;
//    long long range;
//    long long dist;
//};
//
//bool cmp1(Node &a, Node &b) {
//    return a.m < b.m;
//}
//
//bool cmp2(Node &a, Node &b) {
//    return a.dist < b.dist;
//}
//
//const int MAXN = 300001;
//const int MAXB = 601;
//int n;
//Node arr[MAXN];
//
//int blen, bnum;
//int bi[MAXN];
//int bl[MAXB];
//int br[MAXB];
//int maxm[MAXB];
//
//bool vis[MAXN];
//int que[MAXN];
//
//void prepare() {
//    blen = (int)sqrt(n);
//    bnum = (n + blen - 1) / blen;
//    for (int i = 1; i <= n; i++) {
//        bi[i] = (i - 1) / blen + 1;
//    }
//    for (int i = 1; i <= bnum; i++) {
//        bl[i] = (i - 1) * blen + 1;
//        br[i] = min(i * blen, n);
//    }
//    sort(arr + 1, arr + n + 1, cmp1);
//    for (int i = 1; i <= bnum; i++) {
//        maxm[i] = arr[br[i]].m;
//        sort(arr + bl[i], arr + br[i] + 1, cmp2);
//    }
//}
//
//int bfs() {
//    int ans = 0;
//    vis[0] = true;
//    int l = 1, r = 1;
//    que[r++] = 0;
//    while (l < r) {
//        int cur = que[l++];
//        for (int i = 1; i <= bnum; i++) {
//            if (maxm[i] > arr[cur].p) {
//                for (int j = bl[i]; j <= br[i]; j++) {
//                    if (arr[j].dist <= arr[cur].range && arr[j].m <= arr[cur].p && !vis[j]) {
//                        vis[j] = true;
//                        que[r++] = j;
//                        ans++;
//                    }
//                }
//                break;
//            }
//            while (bl[i] <= br[i] && arr[bl[i]].dist <= arr[cur].range) {
//                if (!vis[bl[i]]) {
//                    vis[bl[i]] = true;
//                    que[r++] = bl[i];
//                    ans++;
//                }
//                bl[i]++;
//            }
//        }
//    }
//    return ans;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int x, y, m, p, range;
//    cin >> x >> y >> p >> range >> n;
//    arr[0] = {x, y, 0, p, range, 0};
//    for (int i = 1; i <= n; i++) {
//        cin >> x >> y >> m >> p >> range;
//        arr[i] = {x, y, m, p, range, 0};
//    }
//    long long xd, yd;
//    for (int i = 0; i <= n; i++) {
//        arr[i].range = arr[i].range * arr[i].range;
//        xd = arr[0].x - arr[i].x;
//        yd = arr[0].y - arr[i].y;
//        arr[i].dist = xd * xd + yd * yd;
//    }
//    prepare();
//    cout << bfs() << '\n';
//    return 0;
//}