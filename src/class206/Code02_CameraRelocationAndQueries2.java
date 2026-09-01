package class206;

// 摄像头改位置和查询，C++版
// 三维空间中有n个摄像头，给定每个摄像头的初始位置，三维坐标(x, y, z)
// 接下来有m条操作，格式如下
// 操作 0 i x y z : 第i号摄像头位置变成(x, y, z)
// 操作 1 x y z r : 一个球体出现了，圆心在(x, y, z)，半径为r
//                  题目保证该球体的表面只会碰到一个摄像头
//                  打印这个摄像头的编号，注意在内部的摄像头不算数
// 本题要求强制在线，得到操作参数的规则，打开测试链接查看
// 1 <= n、m <= 65536
// 坐标值是double，绝对值不超过100，均为随机生成，精确到小数点后五位
// 测试链接 : https://www.luogu.com.cn/problem/P11716
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 200001;
//const double INF = 1e100;
//const double EPS = 1e-8;
//int n, m;
//
//double a, b;
//double lastAns = 0.1;
//
//double x[MAXN];
//double y[MAXN];
//double z[MAXN];
//int kdtToCamera[MAXN];
//int cameraToKdt[MAXN];
//
//int cntkdt;
//int root;
//int ls[MAXN];
//int rs[MAXN];
//bool alive[MAXN];
//int allSiz[MAXN];
//int aliveSiz[MAXN];
//double xmin[MAXN];
//double xmax[MAXN];
//double ymin[MAXN];
//double ymax[MAXN];
//double zmin[MAXN];
//double zmax[MAXN];
//
//double ALPHA = 0.7;
//int top;
//int arr[MAXN];
//int treeSiz;
//
//int init(double qx, double qy, double qz, int camera) {
//    cntkdt++;
//    x[cntkdt] = qx;
//    y[cntkdt] = qy;
//    z[cntkdt] = qz;
//    kdtToCamera[cntkdt] = camera;
//    ls[cntkdt] = rs[cntkdt] = 0;
//    alive[cntkdt] = true;
//    allSiz[cntkdt] = 1;
//    aliveSiz[cntkdt] = 1;
//    xmin[cntkdt] = xmax[cntkdt] = qx;
//    ymin[cntkdt] = ymax[cntkdt] = qy;
//    zmin[cntkdt] = zmax[cntkdt] = qz;
//    return cntkdt;
//}
//
//void maintain(int i) {
//    int l = ls[i];
//    int r = rs[i];
//    allSiz[i] = 1 + allSiz[l] + allSiz[r];
//    if (alive[i]) {
//        aliveSiz[i] = 1 + aliveSiz[l] + aliveSiz[r];
//        xmin[i] = xmax[i] = x[i];
//        ymin[i] = ymax[i] = y[i];
//        zmin[i] = zmax[i] = z[i];
//    } else {
//        aliveSiz[i] = aliveSiz[l] + aliveSiz[r];
//        xmin[i] = ymin[i] = zmin[i] = INF;
//        xmax[i] = ymax[i] = zmax[i] = -INF;
//    }
//    if (aliveSiz[l] != 0) {
//        xmin[i] = min(xmin[i], xmin[l]);
//        xmax[i] = max(xmax[i], xmax[l]);
//        ymin[i] = min(ymin[i], ymin[l]);
//        ymax[i] = max(ymax[i], ymax[l]);
//        zmin[i] = min(zmin[i], zmin[l]);
//        zmax[i] = max(zmax[i], zmax[l]);
//    }
//    if (aliveSiz[r] != 0) {
//        xmin[i] = min(xmin[i], xmin[r]);
//        xmax[i] = max(xmax[i], xmax[r]);
//        ymin[i] = min(ymin[i], ymin[r]);
//        ymax[i] = max(ymax[i], ymax[r]);
//        zmin[i] = min(zmin[i], zmin[r]);
//        zmax[i] = max(zmax[i], zmax[r]);
//    }
//}
//
//int compareNode(int i, int j, int dimension) {
//    double v1 = dimension == 0 ? x[i] : (dimension == 1 ? y[i] : z[i]);
//    double v2 = dimension == 0 ? x[j] : (dimension == 1 ? y[j] : z[j]);
//    return v1 != v2 ? (v1 < v2 ? -1 : 1) : (i - j);
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
//    ls[rt] = build(l, mid - 1, (dimension + 1) % 3);
//    rs[rt] = build(mid + 1, r, (dimension + 1) % 3);
//    maintain(rt);
//    return rt;
//}
//
//bool balance(int i) {
//    return ALPHA * allSiz[i] >= max(allSiz[ls[i]], allSiz[rs[i]]) && aliveSiz[i] >= ALPHA * allSiz[i];
//}
//
//void dfs(int i) {
//    if (i != 0) {
//        if (alive[i]) {
//            arr[++treeSiz] = i;
//        }
//        dfs(ls[i]);
//        dfs(rs[i]);
//    }
//}
//
//int rebuild(int i, int dimension) {
//    if (i == top) {
//        treeSiz = 0;
//        dfs(i);
//        return build(1, treeSiz, dimension);
//    }
//    if (compareNode(top, i, dimension) < 0) {
//        ls[i] = rebuild(ls[i], (dimension + 1) % 3);
//    } else {
//        rs[i] = rebuild(rs[i], (dimension + 1) % 3);
//    }
//    maintain(i);
//    return i;
//}
//
//void rebuild() {
//    if (top != 0) {
//        root = rebuild(root, 0);
//    }
//}
//
//int insert(int insertNode, int u, int dimension) {
//    if (u == 0) {
//        return insertNode;
//    }
//    if (compareNode(insertNode, u, dimension) < 0) {
//        ls[u] = insert(insertNode, ls[u], (dimension + 1) % 3);
//    } else {
//        rs[u] = insert(insertNode, rs[u], (dimension + 1) % 3);
//    }
//    maintain(u);
//    if (!balance(u)) {
//        top = u;
//    }
//    return u;
//}
//
//int add(double qx, double qy, double qz, int camera) {
//    top = 0;
//    int insertNode = init(qx, qy, qz, camera);
//    root = insert(insertNode, root, 0);
//    rebuild();
//    return insertNode;
//}
//
//void erase(int eraseNode, int u, int dimension) {
//    if (u == eraseNode) {
//        alive[u] = false;
//    } else {
//        if (compareNode(eraseNode, u, dimension) < 0) {
//            erase(eraseNode, ls[u], (dimension + 1) % 3);
//        } else {
//            erase(eraseNode, rs[u], (dimension + 1) % 3);
//        }
//    }
//    maintain(u);
//    if (!balance(u)) {
//        top = u;
//    }
//}
//
//void remove(int eraseNode) {
//    top = 0;
//    erase(eraseNode, root, 0);
//    rebuild();
//}
//
//double mindist(double qx, double qy, double qz, int i) {
//    double xd = qx < xmin[i] ? (xmin[i] - qx) : qx > xmax[i] ? (qx - xmax[i]) : 0;
//    double yd = qy < ymin[i] ? (ymin[i] - qy) : qy > ymax[i] ? (qy - ymax[i]) : 0;
//    double zd = qz < zmin[i] ? (zmin[i] - qz) : qz > zmax[i] ? (qz - zmax[i]) : 0;
//    return xd * xd + yd * yd + zd * zd;
//}
//
//double maxdist(double qx, double qy, double qz, int i) {
//    double xd2 = max((qx - xmin[i]) * (qx - xmin[i]), (qx - xmax[i]) * (qx - xmax[i]));
//    double yd2 = max((qy - ymin[i]) * (qy - ymin[i]), (qy - ymax[i]) * (qy - ymax[i]));
//    double zd2 = max((qz - zmin[i]) * (qz - zmin[i]), (qz - zmax[i]) * (qz - zmax[i]));
//    return xd2 + yd2 + zd2;
//}
//
//double dist(double qx, double qy, double qz, int i) {
//    double xd = qx - x[i];
//    double yd = qy - y[i];
//    double zd = qz - z[i];
//    return xd * xd + yd * yd + zd * zd;
//}
//
//int query(double qx, double qy, double qz, double low, double high, int i) {
//    if (i == 0 || aliveSiz[i] == 0) {
//        return 0;
//    }
//    if (mindist(qx, qy, qz, i) > high || maxdist(qx, qy, qz, i) < low) {
//        return 0;
//    }
//    if (alive[i]) {
//        double d = dist(qx, qy, qz, i);
//        if (low <= d && d <= high) {
//            return kdtToCamera[i];
//        }
//    }
//    int ans = query(qx, qy, qz, low, high, ls[i]);
//    if (ans != 0) {
//        return ans;
//    }
//    return query(qx, qy, qz, low, high, rs[i]);
//}
//
//int query(double qx, double qy, double qz, double qr) {
//    double low = max(0.0, qr - EPS);
//    double high = qr + EPS;
//    low = low * low;
//    high = high * high;
//    return query(qx, qy, qz, low, high, root);
//}
//
//double decode(double encrypt, double l, double r) {
//    l = lastAns * l + 1;
//    r = lastAns * r + 1;
//    for (int i = 0; i < 60; i++) {
//        double mid = (l + r) / 2;
//        double val = a * mid - b * sin(mid);
//        if (val < encrypt) {
//            l = mid;
//        } else {
//            r = mid;
//        }
//    }
//    double decrypt = (l + r) / 2;
//    decrypt = (decrypt - 1) / lastAns;
//    return decrypt;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m >> a >> b;
//    lastAns = 0.1;
//    xmin[0] = ymin[0] = zmin[0] = INF;
//    xmax[0] = ymax[0] = zmax[0] = -INF;
//    double qx, qy, qz, qr, qid;
//    int op, camera, kdtNode, curAns;
//    for (int i = 1; i <= n; i++) {
//        cin >> qx >> qy >> qz;
//        kdtNode = init(qx, qy, qz, i);
//        cameraToKdt[i] = kdtNode;
//        arr[i] = kdtNode;
//    }
//    root = build(1, n, 0);
//    for (int i = 1; i <= m; i++) {
//        cin >> op;
//        if (op == 0) {
//            cin >> qid >> qx >> qy >> qz;
//            qx = decode(qx, -100, 100);
//            qy = decode(qy, -100, 100);
//            qz = decode(qz, -100, 100);
//            camera = (int) floor(decode(qid, 1, n) + 0.5);
//            remove(cameraToKdt[camera]);
//            cameraToKdt[camera] = add(qx, qy, qz, camera);
//        } else {
//            cin >> qx >> qy >> qz >> qr;
//            qx = decode(qx, -100, 100);
//            qy = decode(qy, -100, 100);
//            qz = decode(qz, -100, 100);
//            qr = decode(qr, 0, 400);
//            curAns = query(qx, qy, qz, qr);
//            cout << curAns << "\n";
//            lastAns = curAns;
//        }
//    }
//    return 0;
//}