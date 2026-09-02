package class206;

// 射击场，C++版
// 每个靶子是一个矩形区域，x轴的范围[l, r]，y轴的范围[d, u]，还有z轴的数值
// 空间里有n个靶子，接下来有m发子弹，每发子弹给定出发时的xy坐标，沿z轴前进
// 子弹会击中前进过程中遇到的第一个尚未消失的靶子，随后击中的靶子和这发子弹都消失
// 对于每一发子弹，打印它击中的靶子编号，如果没有击中打印0
// 1 <= n、m <= 10^5
// 0 <= 坐标值 <= 10^7
// 测试链接 : https://www.luogu.com.cn/problem/CF44G
// 测试链接 : https://codeforces.com/problemset/problem/44/G
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//struct Target {
//    int l, r, d, u, z, id;
//};
//
//bool ZCmp(Target x, Target y) {
//    return x.z < y.z;
//}
//
//const int MAXN = 100001;
//const int INF = 1 << 30;
//int n, m;
//
//Target target[MAXN];
//
//int x[MAXN];
//int y[MAXN];
//int cntkdt;
//int root;
//int ls[MAXN];
//int rs[MAXN];
//bool alive[MAXN];
//int aliveSiz[MAXN];
//int xmin[MAXN];
//int xmax[MAXN];
//int ymin[MAXN];
//int ymax[MAXN];
//int idmin[MAXN];
//
//double ALPHA = 0.7;
//int top;
//int topFather;
//int topSide;
//int topDimension;
//
//int arr[MAXN];
//int treeSiz;
//
//int shot;
//int ans[MAXN];
//
//int init(int qx, int qy) {
//    cntkdt++;
//    x[cntkdt] = qx;
//    y[cntkdt] = qy;
//    ls[cntkdt] = rs[cntkdt] = 0;
//    alive[cntkdt] = true;
//    aliveSiz[cntkdt] = 1;
//    xmin[cntkdt] = xmax[cntkdt] = qx;
//    ymin[cntkdt] = ymax[cntkdt] = qy;
//    idmin[cntkdt] = cntkdt;
//    return cntkdt;
//}
//
//void maintain(int i) {
//    int l = ls[i];
//    int r = rs[i];
//    if (alive[i]) {
//        aliveSiz[i] = 1 + aliveSiz[l] + aliveSiz[r];
//        xmin[i] = xmax[i] = x[i];
//        ymin[i] = ymax[i] = y[i];
//        idmin[i] = i;
//    } else {
//        aliveSiz[i] = aliveSiz[l] + aliveSiz[r];
//        xmin[i] = ymin[i] = INF;
//        xmax[i] = ymax[i] = -INF;
//        idmin[i] = INF;
//    }
//    if (aliveSiz[l] != 0) {
//        xmin[i] = min(xmin[i], xmin[l]);
//        xmax[i] = max(xmax[i], xmax[l]);
//        ymin[i] = min(ymin[i], ymin[l]);
//        ymax[i] = max(ymax[i], ymax[l]);
//        idmin[i] = min(idmin[i], idmin[l]);
//    }
//    if (aliveSiz[r] != 0) {
//        xmin[i] = min(xmin[i], xmin[r]);
//        xmax[i] = max(xmax[i], xmax[r]);
//        ymin[i] = min(ymin[i], ymin[r]);
//        ymax[i] = max(ymax[i], ymax[r]);
//        idmin[i] = min(idmin[i], idmin[r]);
//    }
//}
//
//int compareNode(int i, int j, int dimension) {
//    int a = dimension == 0 ? x[i] : y[i];
//    int b = dimension == 0 ? x[j] : y[j];
//    return a != b ? (a - b) : (i - j);
//}
//
//struct Cmp {
//    int dimension;
//
//    bool operator()(int a, int b) const {
//        return compareNode(a, b, dimension) < 0;
//    }
//};
//
//int build(int l, int r, int dimension) {
//    if (l > r) {
//        return 0;
//    }
//    int mid = (l + r) >> 1;
//    nth_element(arr + l, arr + mid, arr + r + 1, Cmp{dimension});
//    int rt = arr[mid];
//    ls[rt] = build(l, mid - 1, dimension ^ 1);
//    rs[rt] = build(mid + 1, r, dimension ^ 1);
//    maintain(rt);
//    return rt;
//}
//
//bool balance(int i) {
//    return ALPHA * aliveSiz[i] >= max(aliveSiz[ls[i]], aliveSiz[rs[i]]);
//}
//
//void dfs(int i) {
//    if (i != 0 && aliveSiz[i] != 0) {
//        if (alive[i]) {
//            arr[++treeSiz] = i;
//        }
//        dfs(ls[i]);
//        dfs(rs[i]);
//    }
//}
//
//void rebuild() {
//    if (top != 0) {
//        treeSiz = 0;
//        dfs(top);
//        int newRoot = build(1, treeSiz, topDimension);
//        if (topFather == 0) {
//            root = newRoot;
//        } else if (topSide == 1) {
//            ls[topFather] = newRoot;
//        } else {
//            rs[topFather] = newRoot;
//        }
//    }
//}
//
//int add(int insertNode, int u, int fa, int side, int dimension) {
//    if (u == 0 || aliveSiz[u] == 0) {
//        return insertNode;
//    }
//    if (compareNode(insertNode, u, dimension) < 0) {
//        ls[u] = add(insertNode, ls[u], u, 1, dimension ^ 1);
//    } else {
//        rs[u] = add(insertNode, rs[u], u, 2, dimension ^ 1);
//    }
//    maintain(u);
//    if (!balance(u)) {
//        top = u;
//        topFather = fa;
//        topSide = side;
//        topDimension = dimension;
//    }
//    return u;
//}
//
//void add(int qx, int qy) {
//    top = topFather = topSide = topDimension = 0;
//    int insertNode = init(qx, qy);
//    root = add(insertNode, root, 0, 0, 0);
//    rebuild();
//}
//
//void remove(int removeNode, int u, int fa, int side, int dimension) {
//    if (u == removeNode) {
//        alive[u] = false;
//    } else if (compareNode(removeNode, u, dimension) < 0) {
//        remove(removeNode, ls[u], u, 1, dimension ^ 1);
//    } else {
//        remove(removeNode, rs[u], u, 2, dimension ^ 1);
//    }
//    maintain(u);
//    if (!balance(u)) {
//        top = u;
//        topFather = fa;
//        topSide = side;
//        topDimension = dimension;
//    }
//}
//
//void remove(int eraseNode) {
//    top = topFather = topSide = topDimension = 0;
//    remove(eraseNode, root, 0, 0, 0);
//    rebuild();
//}
//
//void query(int ql, int qr, int qd, int qu, int i) {
//    if (i == 0 || aliveSiz[i] == 0 || idmin[i] >= shot) {
//        return;
//    }
//    if (xmax[i] < ql || qr < xmin[i] || ymax[i] < qd || qu < ymin[i]) {
//        return;
//    }
//    if (ql <= xmin[i] && xmax[i] <= qr && qd <= ymin[i] && ymax[i] <= qu) {
//        shot = min(shot, idmin[i]);
//        return;
//    }
//    if (alive[i] && ql <= x[i] && x[i] <= qr && qd <= y[i] && y[i] <= qu) {
//        shot = min(shot, i);
//    }
//    int l = ls[i];
//    int r = rs[i];
//    if (idmin[l] < idmin[r]) {
//        query(ql, qr, qd, qu, l);
//        query(ql, qr, qd, qu, r);
//    } else {
//        query(ql, qr, qd, qu, r);
//        query(ql, qr, qd, qu, l);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) {
//        cin >> target[i].l >> target[i].r >> target[i].d >> target[i].u >> target[i].z;
//        target[i].id = i;
//    }
//    xmin[0] = ymin[0] = INF;
//    xmax[0] = ymax[0] = -INF;
//    idmin[0] = INF;
//    cin >> m;
//    for (int i = 1, qx, qy; i <= m; i++) {
//        cin >> qx >> qy;
//        add(qx, qy);
//    }
//    stable_sort(target + 1, target + n + 1, ZCmp);
//    for (int k = 1; k <= n; k++) {
//        shot = INF;
//        query(target[k].l, target[k].r, target[k].d, target[k].u, root);
//        if (shot != INF) {
//            ans[shot] = target[k].id;
//            remove(shot);
//        }
//    }
//    for (int i = 1; i <= m; i++) {
//        cout << ans[i] << "\n";
//    }
//    return 0;
//}