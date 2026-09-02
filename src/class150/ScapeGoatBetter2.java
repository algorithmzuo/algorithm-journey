package class150;

// 替罪羊树更好的实现，C++版
// 这个文件课上没有讲
// 替罪羊树不进行词频压缩的版本
// 数据经过加强
// 测试链接 : https://www.luogu.com.cn/problem/P6136
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 2000001;
//
//int cntn;
//int root;
//
//int key[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//bool alive[MAXN];
//int aliveSize[MAXN];
//
//double ALPHA = 0.7;
//int top;
//int father;
//int side;
//
//int collect[MAXN];
//int collectSiz;
//
//int init(int num) {
//    key[++cntn] = num;
//    ls[cntn] = rs[cntn] = 0;
//    alive[cntn] = true;
//    aliveSize[cntn] = 1;
//    return cntn;
//}
//
//void up(int i) {
//    aliveSize[i] = (alive[i] ? 1 : 0) + aliveSize[ls[i]] + aliveSize[rs[i]];
//}
//
//void inorder(int i) {
//    if (i != 0 && aliveSize[i] != 0) {
//        inorder(ls[i]);
//        if (alive[i]) {
//            collect[++collectSiz] = i;
//        }
//        inorder(rs[i]);
//    }
//}
//
//int build(int l, int r) {
//    if (l > r) {
//        return 0;
//    }
//    int m = (l + r) / 2;
//    int h = collect[m];
//    ls[h] = build(l, m - 1);
//    rs[h] = build(m + 1, r);
//    up(h);
//    return h;
//}
//
//void rebuild() {
//    if (top != 0) {
//        collectSiz = 0;
//        inorder(top);
//        int newRoot = build(1, collectSiz);
//        if (father == 0) {
//            root = newRoot;
//        } else if (side == 1) {
//            ls[father] = newRoot;
//        } else {
//            rs[father] = newRoot;
//        }
//    }
//}
//
//bool balance(int i) {
//    return ALPHA * aliveSize[i] >= max(aliveSize[ls[i]], aliveSize[rs[i]]);
//}
//
//void add(int i, int f, int s, int num) {
//    if (i == 0 || aliveSize[i] == 0) {
//        int newNode = init(num);
//        if (f == 0) {
//            root = newNode;
//        } else if (s == 1) {
//            ls[f] = newNode;
//        } else {
//            rs[f] = newNode;
//        }
//    } else {
//        if (num <= key[i]) {
//            add(ls[i], i, 1, num);
//        } else {
//            add(rs[i], i, 2, num);
//        }
//        up(i);
//        if (!balance(i)) {
//            top = i;
//            father = f;
//            side = s;
//        }
//    }
//}
//
//void add(int num) {
//    top = father = side = 0;
//    add(root, 0, 0, num);
//    rebuild();
//}
//
//int small(int i, int num) {
//    if (i == 0 || aliveSize[i] == 0) {
//        return 0;
//    }
//    if (num <= key[i]) {
//        return small(ls[i], num);
//    } else {
//        return aliveSize[ls[i]] + (alive[i] ? 1 : 0) + small(rs[i], num);
//    }
//}
//
//int getRank(int num) {
//    return small(root, num) + 1;
//}
//
//int index(int i, int x) {
//    if (x <= aliveSize[ls[i]]) {
//        return index(ls[i], x);
//    } else {
//        int less = aliveSize[ls[i]] + (alive[i] ? 1 : 0);
//        if (less < x) {
//            return index(rs[i], x - less);
//        }
//    }
//    return key[i];
//}
//
//int index(int x) {
//    return index(root, x);
//}
//
//int pre(int num) {
//    int kth = getRank(num);
//    if (kth == 1) {
//        return INT_MIN;
//    } else {
//        return index(kth - 1);
//    }
//}
//
//int post(int num) {
//    int kth = getRank(num + 1);
//    if (kth == aliveSize[root] + 1) {
//        return INT_MAX;
//    } else {
//        return index(kth);
//    }
//}
//
//void remove(int i, int f, int s, int rank) {
//    int lsiz = aliveSize[ls[i]];
//    if (rank <= lsiz) {
//        remove(ls[i], i, 1, rank);
//    } else {
//        int cur = alive[i] ? 1 : 0;
//        if (alive[i] && rank == lsiz + cur) {
//            alive[i] = false;
//        } else {
//            remove(rs[i], i, 2, rank - lsiz - cur);
//        }
//    }
//    up(i);
//    if (!balance(i)) {
//        top = i;
//        father = f;
//        side = s;
//    }
//}
//
//void remove(int num) {
//    int rank1 = getRank(num);
//    int rank2 = getRank(num + 1);
//    if (rank1 != rank2) {
//        top = father = side = 0;
//        remove(root, 0, 0, rank1);
//        rebuild();
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    int n, m;
//    cin >> n >> m;
//    for (int i = 1, num; i <= n; i++) {
//        cin >> num;
//        add(num);
//    }
//    int lastAns = 0;
//    int ans = 0;
//    for (int i = 1, op, x; i <= m; i++) {
//        cin >> op >> x;
//        x ^= lastAns;
//        if (op == 1) {
//            add(x);
//        } else if (op == 2) {
//            remove(x);
//        } else if (op == 3) {
//            lastAns = getRank(x);
//            ans ^= lastAns;
//        } else if (op == 4) {
//            lastAns = index(x);
//            ans ^= lastAns;
//        } else if (op == 5) {
//            lastAns = pre(x);
//            ans ^= lastAns;
//        } else {
//            lastAns = post(x);
//            ans ^= lastAns;
//        }
//    }
//    cout << ans << "\n";
//    return 0;
//}