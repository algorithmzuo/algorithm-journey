package class170;

// 老C的任务，C++版
// 有n个基站，每个基站给定x、y、v，表示基站在(x, y)位置，功率为v
// 接下来有m条查询，每条查询格式如下
// 查询 a b c d : 打印左上角(a, b)、右下角(c, d)的区域里基站的功率和
// 1 <= n、m <= 10^5
// 其余数值都在int类型的范围
// 测试链接 : https://www.luogu.com.cn/problem/P3755
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
//    if (a.x != b.x) {
//        return a.x < b.x;
//    }
//    return a.op < b.op;
//}
//
//const int MAXN = 500001;
//int n, m;
//Node arr[MAXN];
//int cnt = 0;
//Node tmp[MAXN];
//long long ans[MAXN];
//
//void addStation(int x, int y, int v) {
//    arr[++cnt].op = 1;
//    arr[cnt].x = x;
//    arr[cnt].y = y;
//    arr[cnt].v = v;
//}
//
//void addQuery(int x, int y, int v, int q) {
//    arr[++cnt].op = 2;
//    arr[cnt].x = x;
//    arr[cnt].y = y;
//    arr[cnt].v = v;
//    arr[cnt].q = q;
//}
//
//void merge(int l, int m, int r) {
//    int p1, p2;
//    long long sum = 0;
//    for (p1 = l - 1, p2 = m + 1; p2 <= r; p2++) {
//        while (p1 + 1 <= m && arr[p1 + 1].y <= arr[p2].y) {
//            p1++;
//            if (arr[p1].op == 1) {
//                sum += arr[p1].v;
//            }
//        }
//        if (arr[p2].op == 2) {
//            ans[arr[p2].q] += sum * arr[p2].v;
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
//    for (int i = 1, x, y, v; i <= n; i++) {
//        cin >> x >> y >> v;
//        addStation(x, y, v);
//    }
//    for (int i = 1, a, b, c, d; i <= m; i++) {
//        cin >> a >> b >> c >> d;
//        addQuery(c, d, 1, i);
//        addQuery(a - 1, b - 1, 1, i);
//        addQuery(a - 1, d, -1, i);
//        addQuery(c, b - 1, -1, i);
//    }
//    sort(arr + 1, arr + cnt + 1, NodeCmp);
//    cdq(1, cnt);
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << '\n';
//    }
//    return 0;
//}