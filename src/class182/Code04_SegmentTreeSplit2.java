package class182;

// 线段树分裂，C++版
// 数字范围1~n，给定每种数字的个数，建立编号为1的可重集合，以后新集合的编号自增即可
// 接下来有m条操作，每条操作是如下五种类型中的一种
// 操作 0 x y z : x号集合中，取出[y, z]范围上的所有数字，生成新的集合
// 操作 1 x y   : x号集合与y号集合合并，以后y编号的集合不会使用了
// 操作 2 x y z : x号集合中，加入y个数字z
// 操作 3 x y z : x号集合中，查询[y, z]范围上的数字个数
// 操作 4 x y   : x号集合中，查询第y小的数字，不存在打印-1
// 1 <= 所有数据 <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5494
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//const int MAXT = MAXN * 10;
//int n, m;
//
//int root[MAXN];
//int ls[MAXT];
//int rs[MAXT];
//long long sum[MAXT];
//int version;
//
//int pool[MAXT];
//int top;
//
//void prepare() {
//    top = 0;
//    for (int i = 1; i < MAXT; i++) {
//        pool[++top] = i;
//    }
//}
//
//int newNode() {
//    return pool[top--];
//}
//
//void del(int i) {
//    pool[++top] = i;
//    ls[i] = 0;
//    rs[i] = 0;
//    sum[i] = 0;
//}
//
//void up(int i) {
//    sum[i] = (sum[ls[i]] + sum[rs[i]]);
//}
//
//int add(int jobi, int jobv, int l, int r, int i) {
//    int rt = i;
//    if (rt == 0) {
//        rt = newNode();
//    }
//    if (l == r) {
//        sum[rt] += jobv;
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            ls[rt] = add(jobi, jobv, l, mid, ls[rt]);
//        } else {
//            rs[rt] = add(jobi, jobv, mid + 1, r, rs[rt]);
//        }
//        up(rt);
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
//int merge(int l, int r, int t1, int t2) {
//    if (t1 == 0 || t2 == 0) {
//        return t1 + t2;
//    }
//    if (l == r) {
//        sum[t1] += sum[t2];
//    } else {
//        int mid = (l + r) >> 1;
//        ls[t1] = merge(l, mid, ls[t1], ls[t2]);
//        rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
//        up(t1);
//    }
//    del(t2);
//    return t1;
//}
//
//int tree1, tree2;
//
//void split(int jobl, int jobr, int l, int r, int t1) {
//    if (t1 == 0) {
//        tree1 = t1;
//        tree2 = 0;
//        return;
//    }
//    if (jobl <= l && r <= jobr) {
//        tree1 = 0;
//        tree2 = t1;
//        return;
//    }
//    int t2 = newNode();
//    int mid = (l + r) >> 1;
//    if (jobl <= mid) {
//        split(jobl, jobr, l, mid, ls[t1]);
//        ls[t1] = tree1;
//        ls[t2] = tree2;
//    }
//    if (jobr > mid) {
//        split(jobl, jobr, mid + 1, r, rs[t1]);
//        rs[t1] = tree1;
//        rs[t2] = tree2;
//    }
//    up(t1);
//    up(t2);
//    tree1 = t1;
//    tree2 = t2;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    prepare();
//    cin >> n >> m;
//    version = 1;
//    for (int i = 1, x; i <= n; i++) {
//        cin >> x;
//        root[version] = add(i, x, 1, n, root[1]);
//    }
//    for (int i = 1, op, x, y, z; i <= m; i++) {
//        cin >> op;
//        if (op == 0) {
//            cin >> x >> y >> z;
//            split(y, z, 1, n, root[x]);
//            root[x] = tree1;
//            root[++version] = tree2;
//        } else if (op == 1) {
//            cin >> x >> y;
//            root[x] = merge(1, n, root[x], root[y]);
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