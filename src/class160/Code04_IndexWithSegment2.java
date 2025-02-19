package class160;

// 树状数组套线段树，C++版
// 给定一个长度为n的数组arr，下标1~n
// 每条操作都是如下5种类型中的一种，一共进行m次操作
// 操作 1 x y z : 查询数字z在arr[x..y]中的排名
// 操作 2 x y z : 查询arr[x..y]中排第z名的数字
// 操作 3 x y   : arr中x位置的数字改成y
// 操作 4 x y z : 查询数字z在arr[x..y]中的前驱，不存在返回-2147483647
// 操作 5 x y z : 查询数字z在arr[x..y]中的后继，不存在返回+2147483647
// 1 <= n、m <= 5 * 10^4
// 数组中的值永远在[0, 10^8]范围内
// 测试链接 : https://www.luogu.com.cn/problem/P3380
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
//const int MAXT = MAXN * 160;
//const int INF = INT_MAX;
//int n, m, s;
//int arr[MAXN];
//int ques[MAXN][4];
//int sorted[MAXN * 2];
//int root[MAXN];
//int sum[MAXT];
//int ls[MAXT];
//int rs[MAXT];
//int cntt = 0;
//int addTree[MAXN];
//int minusTree[MAXN];
//int cntadd;
//int cntminus;
//
//int kth(int num) {
//    int left = 1, right = s, mid;
//    while (left <= right) {
//        mid = (left + right) / 2;
//        if (sorted[mid] == num) {
//            return mid;
//        } else if (sorted[mid] < num) {
//            left = mid + 1;
//        } else {
//            right = mid - 1;
//        }
//    }
//    return -1;
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//int innerAdd(int jobi, int jobv, int l, int r, int i) {
//    if (i == 0) {
//        i = ++cntt;
//    }
//    if (l == r) {
//        sum[i] += jobv;
//    } else {
//        int mid = (l + r) / 2;
//        if (jobi <= mid) {
//        	ls[i] = innerAdd(jobi, jobv, l, mid, ls[i]);
//        } else {
//        	rs[i] = innerAdd(jobi, jobv, mid + 1, r, rs[i]);
//        }
//        sum[i] = sum[ls[i]] + sum[rs[i]];
//    }
//    return i;
//}
//
//int innerQuery(int jobk, int l, int r) {
//    if (l == r) {
//        return l;
//    }
//    int mid = (l + r) / 2;
//    int leftsum = 0;
//    for (int i = 1; i <= cntadd; i++) {
//        leftsum += sum[ls[addTree[i]]];
//    }
//    for (int i = 1; i <= cntminus; i++) {
//        leftsum -= sum[ls[minusTree[i]]];
//    }
//    if (jobk <= leftsum) {
//        for (int i = 1; i <= cntadd; i++) {
//            addTree[i] = ls[addTree[i]];
//        }
//        for (int i = 1; i <= cntminus; i++) {
//            minusTree[i] = ls[minusTree[i]];
//        }
//        return innerQuery(jobk, l, mid);
//    } else {
//        for (int i = 1; i <= cntadd; i++) {
//            addTree[i] = rs[addTree[i]];
//        }
//        for (int i = 1; i <= cntminus; i++) {
//            minusTree[i] = rs[minusTree[i]];
//        }
//        return innerQuery(jobk - leftsum, mid + 1, r);
//    }
//}
//
//int innerSmall(int jobi, int l, int r) {
//    if (l == r) {
//        return 0;
//    }
//    int mid = (l + r) / 2;
//    if (jobi <= mid) {
//        for (int i = 1; i <= cntadd; i++) {
//            addTree[i] = ls[addTree[i]];
//        }
//        for (int i = 1; i <= cntminus; i++) {
//            minusTree[i] = ls[minusTree[i]];
//        }
//        return innerSmall(jobi, l, mid);
//    } else {
//        int leftsum = 0;
//        for (int i = 1; i <= cntadd; i++) {
//            leftsum += sum[ls[addTree[i]]];
//            addTree[i] = rs[addTree[i]];
//        }
//        for (int i = 1; i <= cntminus; i++) {
//            leftsum -= sum[ls[minusTree[i]]];
//            minusTree[i] = rs[minusTree[i]];
//        }
//        return leftsum + innerSmall(jobi, mid + 1, r);
//    }
//}
//
//void add(int i, int cnt) {
//    for (int j = i; j <= n; j += lowbit(j)) {
//        root[j] = innerAdd(arr[i], cnt, 1, s, root[j]);
//    }
//}
//
//void update(int i, int v) {
//    add(i, -1);
//    arr[i] = kth(v);
//    add(i, 1);
//}
//
//int small(int l, int r, int v) {
//    cntadd = cntminus = 0;
//    for (int i = r; i > 0; i -= lowbit(i)) {
//        addTree[++cntadd] = root[i];
//    }
//    for (int i = l - 1; i > 0; i -= lowbit(i)) {
//        minusTree[++cntminus] = root[i];
//    }
//    return innerSmall(v, 1, s);
//}
//
//int number(int l, int r, int k) {
//    cntadd = cntminus = 0;
//    for (int i = r; i > 0; i -= lowbit(i)) {
//        addTree[++cntadd] = root[i];
//    }
//    for (int i = l - 1; i > 0; i -= lowbit(i)) {
//        minusTree[++cntminus] = root[i];
//    }
//    return sorted[innerQuery(k, 1, s)];
//}
//
//int pre(int l, int r, int v) {
//    int rank = small(l, r, v) + 1;
//    if (rank == 1) {
//        return -INF;
//    }
//    return number(l, r, rank - 1);
//}
//
//int post(int l, int r, int v) {
//    if (v == s) {
//        return INF;
//    }
//    int rank = small(l, r, v + 1) + 1;
//    if (rank == r - l + 2) {
//        return INF;
//    }
//    return number(l, r, rank);
//}
//
//void prepare() {
//    s = 0;
//    for (int i = 1; i <= n; i++) {
//        sorted[++s] = arr[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        if (ques[i][0] == 3) {
//            sorted[++s] = ques[i][2];
//        } else if (ques[i][0] != 2) {
//            sorted[++s] = ques[i][3];
//        }
//    }
//    sort(sorted + 1, sorted + s + 1);
//    int len = 1;
//    for (int i = 2; i <= s; i++) {
//        if (sorted[len] != sorted[i]) {
//            sorted[++len] = sorted[i];
//        }
//    }
//    s = len;
//    for (int i = 1; i <= n; i++) {
//        arr[i] = kth(arr[i]);
//        add(i, 1);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    cin >> m;
//    for (int i = 1; i <= n; i++) {
//        cin >> arr[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> ques[i][0];
//        cin >> ques[i][1];
//        cin >> ques[i][2];
//        if (ques[i][0] != 3) {
//            cin >> ques[i][3];
//        }
//    }
//    prepare();
//    for (int i = 1, op, x, y, z; i <= m; i++) {
//        op = ques[i][0];
//        x = ques[i][1];
//        y = ques[i][2];
//        if (op == 3) {
//            update(x, y);
//        } else {
//            z = ques[i][3];
//            if (op == 1) {
//                cout << small(x, y, kth(z)) + 1 << "\n";
//            } else if (op == 2) {
//                cout << number(x, y, z) << "\n";
//            } else if (op == 4) {
//                cout << pre(x, y, kth(z)) << "\n";
//            } else {
//                cout << post(x, y, kth(z)) << "\n";
//            }
//        }
//    }
//    return 0;
//}