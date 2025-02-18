package class160;

// 排队，C++版
// 给定一个长度为n的数组arr，下标1~n
// 如果有i < j，并且arr[i] > arr[j]，那么(i,j)就叫做一个逆序对
// 首先打印原始arr中有多少逆序对，然后进行如下类型的操作，一共发生m次
// 操作 a b : 交换arr中a位置的数和b位置的数，打印数组中逆序对的数量
// 1 <= n <= 2 * 10^4
// 1 <= m <= 2 * 10^3
// 1 <= arr[i] <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P1975
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 20001;
//const int MAXT = MAXN * 80;
//const int INF = 1000000001;
//int n, m, s;
//int arr[MAXN];
//int sorted[MAXN + 2];
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//int sum[MAXT];
//int cnt = 0;
//int ans = 0;
//
//int kth(int num) {
//    int l = 1, r = s, mid;
//    while (l <= r) {
//        mid = (l + r) / 2;
//        if (sorted[mid] == num) return mid;
//        else if (sorted[mid] < num) l = mid + 1;
//        else r = mid - 1;
//    }
//    return -1;
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//int innerAdd(int jobi, int jobv, int l, int r, int i) {
//    if (i == 0) i = ++cnt;
//    if (l == r) {
//        sum[i] += jobv;
//    } else {
//        int mid = (l + r) / 2;
//        if (jobi <= mid) {
//            ls[i] = innerAdd(jobi, jobv, l, mid, ls[i]);
//        } else {
//            rs[i] = innerAdd(jobi, jobv, mid + 1, r, rs[i]);
//        }
//        sum[i] = sum[ls[i]] + sum[rs[i]];
//    }
//    return i;
//}
//
//int innerQuery(int jobl, int jobr, int l, int r, int i) {
//    if (i == 0) return 0;
//    if (jobl <= l && r <= jobr) return sum[i];
//    int mid = (l + r) / 2;
//    int ans = 0;
//    if (jobl <= mid) {
//    	ans += innerQuery(jobl, jobr, l, mid, ls[i]);
//    }
//    if (jobr > mid) {
//    	ans += innerQuery(jobl, jobr, mid + 1, r, rs[i]);
//    }
//    return ans;
//}
//
//void insert(int i, int v) {
//    for (int j = i; j <= n; j += lowbit(j)) {
//        root[j] = innerAdd(arr[i], v, 1, s, root[j]);
//    }
//}
//
//int query(int al, int ar, int numl, int numr) {
//    int ans = 0;
//    for (int i = ar; i > 0; i -= lowbit(i)) {
//        ans += innerQuery(numl, numr, 1, s, root[i]);
//    }
//    for (int i = al - 1; i > 0; i -= lowbit(i)) {
//        ans -= innerQuery(numl, numr, 1, s, root[i]);
//    }
//    return ans;
//}
//
//void prepare() {
//    s = 0;
//    for (int i = 1; i <= n; i++) sorted[++s] = arr[i];
//    sorted[++s] = -INF;
//    sorted[++s] = INF;
//    sort(sorted + 1, sorted + s + 1);
//    int len = 1;
//    for (int i = 2; i <= s; i++) {
//        if (sorted[len] != sorted[i]) sorted[++len] = sorted[i];
//    }
//    s = len;
//    for (int i = 1; i <= n; i++) {
//        arr[i] = kth(arr[i]);
//        insert(i, 1);
//    }
//}
//
//void compute(int a, int b) {
//    ans -= query(a + 1, b - 1, 1, arr[a] - 1);
//    ans += query(a + 1, b - 1, arr[a] + 1, s);
//    ans -= query(a + 1, b - 1, arr[b] + 1, s);
//    ans += query(a + 1, b - 1, 1, arr[b] - 1);
//    if (arr[a] < arr[b]) ans++;
//    else if (arr[a] > arr[b]) ans--;
//    insert(a, -1);
//    insert(b, -1);
//    swap(arr[a], arr[b]);
//    insert(a, 1);
//    insert(b, 1);
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) cin >> arr[i];
//    prepare();
//    for (int i = 2; i <= n; i++) ans += query(1, i - 1, arr[i] + 1, s);
//    cout << ans << '\n';
//    cin >> m;
//    for (int i = 1, a, b; i <= m; i++) {
//        cin >> a >> b;
//        if (a > b) swap(a, b);
//        compute(a, b);
//        cout << ans << '\n';
//    }
//    return 0;
//}