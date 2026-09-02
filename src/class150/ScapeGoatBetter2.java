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
//int aliveSiz[MAXN];
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
//    aliveSiz[cntn] = 1;
//    return cntn;
//}
//
//void up(int i) {
//    aliveSiz[i] = (alive[i] ? 1 : 0) + aliveSiz[ls[i]] + aliveSiz[rs[i]];
//}
//
//void inorder(int i) {
//    if (i != 0 && aliveSiz[i] != 0) {
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
//// 通常带惰性删除的替罪羊树，会同时维护子树总节点数和存活节点数
//// 总节点数用于判断树形是否失衡，存活比例过低时重构并清除死亡节点
//// 本实现只用aliveSiz作为子树重量，插入和成功删除后都检查重量平衡
//// ALPHA * aliveSiz[i] >= max(aliveSiz[ls[i]], aliveSiz[rs[i]])
//// 如果出现失衡，就重构路径上最高的不平衡子树，只保留其中的存活节点
//// 即使节点删除得非常均匀，某些子树一直没有触发重构，也不会影响复杂度
//// 因为每次操作结束后，所有包含存活节点的子树都满足上述重量平衡条件
//// 沿一条有效路径向下，存活节点数每层至多变为上一层的ALPHA倍
//// 所以有效访问高度始终是O(log n)，其中n是当前存活节点数
//// aliveSiz[i]为0的子树直接视为空树，插入和查询都不会进入其内部
//// 已死但aliveSiz不为0的节点如果只有一个非空儿子，就一定会失衡
//// 所以这种仍在有效结构中的死亡节点必须有两个非空儿子，数量不会超过n-1
//// 因此重构访问的节点数量，仍然与重构范围内的存活节点数量同阶
//// 一棵大小为k的子树重构后，需要经过差不多k次修改才可能再次失衡
//// 所以查询最坏O(log n)，插入和删除均摊O(log n)
//bool balance(int i) {
//    return ALPHA * aliveSiz[i] >= max(aliveSiz[ls[i]], aliveSiz[rs[i]]);
//}
//
//int add(int i, int f, int s, int num) {
//    if (i == 0 || aliveSiz[i] == 0) {
//        return init(num);
//    }
//    if (num <= key[i]) {
//        ls[i] = add(ls[i], i, 1, num);
//    } else {
//        rs[i] = add(rs[i], i, 2, num);
//    }
//    up(i);
//    if (!balance(i)) {
//        top = i;
//        father = f;
//        side = s;
//    }
//    return i;
//}
//
//void add(int num) {
//    top = father = side = 0;
//    root = add(root, 0, 0, num);
//    rebuild();
//}
//
//int small(int i, int num) {
//    if (i == 0 || aliveSiz[i] == 0) {
//        return 0;
//    }
//    if (num <= key[i]) {
//        return small(ls[i], num);
//    } else {
//        return aliveSiz[ls[i]] + (alive[i] ? 1 : 0) + small(rs[i], num);
//    }
//}
//
//int getRank(int num) {
//    return small(root, num) + 1;
//}
//
//int index(int i, int x) {
//    if (x <= aliveSiz[ls[i]]) {
//        return index(ls[i], x);
//    } else {
//        int less = aliveSiz[ls[i]] + (alive[i] ? 1 : 0);
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
//    if (kth == aliveSiz[root] + 1) {
//        return INT_MAX;
//    } else {
//        return index(kth);
//    }
//}
//
//void remove(int i, int f, int s, int rank) {
//    int lsiz = aliveSiz[ls[i]];
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