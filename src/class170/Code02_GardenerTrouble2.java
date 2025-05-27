package class170;

// 园丁的烦恼，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P2163
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int op, x, y, v, q, num; // 类型、x、y、效果v、问题q、树木量num
//};
//
//bool NodeCmp(Node a, Node b) {
//    if (a.x != b.x) {
//        return a.x < b.x;
//    }
//    if (a.y != b.y) {
//        return a.y < b.y;
//    }
//    return a.op < b.op;
//}
//
//const int MAXN = 500001 * 5;
//int n, m, p = 0;
//Node arr[MAXN];
//Node tmp[MAXN];
//int ans[MAXN];
//
//void addTree(int x, int y) {
//    arr[++p].op = 1;
//    arr[p].x = x;
//    arr[p].y = y;
//}
//
//void addQuery(int x, int y, int v, int q) {
//    arr[++p].op = 2;
//    arr[p].x = x;
//    arr[p].y = y;
//    arr[p].v = v;
//    arr[p].q = q;
//    arr[p].num = 0;
//}
//
//void merge(int l, int m, int r) {
//    int p1, p2, tree = 0;
//    for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
//        while (p1 + 1 <= m && arr[p1 + 1].y <= arr[p2].y) {
//            p1++;
//            if (arr[p1].op == 1) {
//                tree++;
//            }
//        }
//        if (arr[p2].op == 2) {
//            arr[p2].num += tree;
//        }
//    }
//    p1 = l;
//    p2 = m + 1;
//    int i = l;
//    while (p1 <= m && p2 <= r) {
//        tmp[i++] = arr[p1].y <= arr[p2].y ? arr[p1++] : arr[p2++];
//    }
//    while (p1 <= m) {
//        tmp[i++] = arr[p1++];
//    }
//    while (p2 <= r) {
//        tmp[i++] = arr[p2++];
//    }
//    for (i = l; i <= r; i++) {
//        arr[i] = tmp[i];
//    }
//}
//
//void cdq(int l, int r) {
//    if (l == r) {
//        return;
//    }
//    int mid = (l + r) / 2;
//    cdq(l, mid);
//    cdq(mid + 1, r);
//    merge(l, mid, r);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, x, y; i <= n; i++) {
//        cin >> x >> y;
//        addTree(x, y);
//    }
//    for (int i = 1, a, b, c, d; i <= m; i++) {
//        cin >> a >> b >> c >> d;
//        addQuery(c, d, 1, i);
//        addQuery(a - 1, b - 1, 1, i);
//        addQuery(a - 1, d, -1, i);
//        addQuery(c, b - 1, -1, i);
//    }
//    sort(arr + 1, arr + p + 1, NodeCmp);
//    cdq(1, p);
//    for (int i = 1; i <= p; i++) {
//        if (arr[i].op == 2) {
//            ans[arr[i].q] += arr[i].v * arr[i].num;
//        }
//    }
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}