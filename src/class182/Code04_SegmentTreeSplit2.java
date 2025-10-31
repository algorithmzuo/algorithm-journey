package class182;

// 线段树分裂，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P5494
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//const int MAXT = MAXN * 40;
//int n, m;
//
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//long long sum[MAXT];
//int cntRoot;
//
//int pool[MAXT];
//int top;
//
//void prepare() {
//	top = 0;
//    for (int i = 1; i < MAXT; i++) {
//    	pool[++top] = i;
//    }
//}
//
//int newNode() {
//    return pool[top--];
//}
//
//void del(int i) {
//	pool[++top] = i;
//    ls[i] = 0;
//    rs[i] = 0;
//    sum[i] = 0;
//}
//
//int add(int jobi, int jobv, int l, int r, int i) {
//    int rt = i;
//    if (rt == 0) {
//        rt = newNode();
//    }
//    sum[rt] += jobv;
//    if (l < r) {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            ls[rt] = add(jobi, jobv, l, mid, ls[rt]);
//        } else {
//            rs[rt] = add(jobi, jobv, mid + 1, r, rs[rt]);
//        }
//    }
//    return rt;
//}
//
//long long query(int jobl, int jobr, int l, int r, int i) {
//    if (i == 0) {
//        return 0;
//    }
//    if (jobl <= l && r <= jobr) {
//        return sum[i];
//    }
//    int mid = (l + r) >> 1;
//    long long ans = 0;
//    if (jobl <= mid) {
//        ans += query(jobl, jobr, l, mid, ls[i]);
//    }
//    if (jobr > mid) {
//        ans += query(jobl, jobr, mid + 1, r, rs[i]);
//    }
//    return ans;
//}
//
//int kth(long long jobk, int l, int r, int i) {
//    if (i == 0) {
//        return -1;
//    }
//    if (l == r) {
//        return l;
//    }
//    int mid = (l + r) >> 1;
//    if (sum[ls[i]] >= jobk) {
//        return kth(jobk, l, mid, ls[i]);
//    } else {
//        return kth(jobk - sum[ls[i]], mid + 1, r, rs[i]);
//    }
//}
//
//int merge(int t1, int t2) {
//    if (t1 == 0 || t2 == 0) {
//        return t1 + t2;
//    }
//    sum[t1] += sum[t2];
//    ls[t1] = merge(ls[t1], ls[t2]);
//    rs[t1] = merge(rs[t1], rs[t2]);
//    del(t2);
//    return t1;
//}
//
//int split(int x, long long k) {
//    if (x == 0) return 0;
//    int y = newNode();
//    long long lsum = sum[ls[x]];
//    if (k > lsum) {
//        rs[y] = split(rs[x], k - lsum);
//    } else {
//    	swap(rs[x], rs[y]);
//    }
//    if (k < lsum) {
//        ls[y] = split(ls[x], k);
//    }
//    sum[y] = sum[x] - k;
//    sum[x] = k;
//    return y;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    prepare();
//    cntRoot = 1;
//    for (int i = 1, x; i <= n; i++) {
//        cin >> x;
//        root[cntRoot] = add(i, x, 1, n, root[1]);
//    }
//    for (int i = 1, op, x, y, z; i <= m; i++) {
//        cin >> op;
//        if (op == 0) {
//            cin >> x >> y >> z;
//            long long k1 = query(1, z, 1, n, root[x]);
//            long long k2 = query(y, z, 1, n, root[x]);
//            root[++cntRoot] = split(root[x], k1 - k2);
//            int tmp = split(root[cntRoot], k2);
//            root[x] = merge(root[x], tmp);
//        } else if (op == 1) {
//            cin >> x >> y;
//            root[x] = merge(root[x], root[y]);
//        } else if (op == 2) {
//            cin >> x >> y >> z;
//            root[x] = add(z, y, 1, n, root[x]);
//        } else if (op == 3) {
//            cin >> x >> y >> z;
//            cout << query(y, z, 1, n, root[x]) << '\n';
//        } else {
//            cin >> x >> y;
//            cout << kth(y, 1, n, root[x]) << '\n';
//        }
//    }
//    return 0;
//}