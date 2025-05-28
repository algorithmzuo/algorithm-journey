package class170;

// 摩基亚，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P4390
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Node {
//    int op, x, y, v, q;
//};
//
//bool NodeCmp(Node a, Node b) {
//    return a.x < b.x;
//}
//
//const int MAXN = 200001;
//const int MAXV = 2000002;
//const int INF = 1000000001;
//int w;
//
//Node arr[MAXN];
//int cnte = 0;
//int cntq = 0;
//
//int tree[MAXV];
//
//int ans[MAXN];
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int v) {
//    while (i <= w) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//int query(int i) {
//    int ret = 0;
//    while (i > 0) {
//        ret += tree[i];
//        i -= lowbit(i);
//    }
//    return ret;
//}
//
//void addPeople(int x, int y, int v) {
//    arr[++cnte].op = 1;
//    arr[cnte].x = x;
//    arr[cnte].y = y;
//    arr[cnte].v = v;
//}
//
//void addQuery(int x, int y, int v, int q) {
//    arr[++cnte].op = 2;
//    arr[cnte].x = x;
//    arr[cnte].y = y;
//    arr[cnte].v = v;
//    arr[cnte].q = q;
//}
//
//void merge(int l, int m, int r) {
//    int p1, p2;
//    for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
//        while (p1 + 1 <= m && arr[p1 + 1].x <= arr[p2].x) {
//            p1++;
//            if (arr[p1].op == 1) {
//                add(arr[p1].y, arr[p1].v);
//            }
//        }
//        if (arr[p2].op == 2) {
//            ans[arr[p2].q] += arr[p2].v * query(arr[p2].y);
//        }
//    }
//    for (int i = l; i <= p1; i++) {
//        if (arr[i].op == 1) {
//            add(arr[i].y, -arr[i].v);
//        }
//    }
//    sort(arr + l, arr + r + 1, NodeCmp);
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
//    int tmp;
//    cin >> tmp >> w;
//    w++;
//    int op, x, y, v, a, b, c, d;
//    cin >> op;
//    while (op != 3) {
//        if (op == 1) {
//            cin >> x >> y >> v;
//            x++; y++;
//            addPeople(x, y, v);
//        } else {
//            cin >> a >> b >> c >> d;
//            a++; b++; c++; d++;
//            addQuery(c, d, 1, ++cntq);
//            addQuery(a - 1, b - 1, 1, cntq);
//            addQuery(a - 1, d, -1, cntq);
//            addQuery(c, b - 1, -1, cntq);
//        }
//        cin >> op;
//    }
//    cdq(1, cnte);
//    for (int i = 1; i <= cntq; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}