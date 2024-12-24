package class155;

// 棘手的操作，C++版
// 编号1~n个节点，每个节点独立且有自己的权值，实现如下7种操作，操作一共调用m次
// U x y  : x所在的集合和y所在的集合合并
// A1 x v : x节点的权值增加v
// A2 x v : x所在的集合所有节点的权值增加v
// A3 v   : 所有节点的权值增加v
// F1 x   : 打印x节点的权值
// F2 x   : 打印x所在的集合中，权值最大的节点的权值
// F3     : 打印所有节点中，权值最大的节点的权值
// 1 <= n、m <= 3 * 10^5，权值不会超过int类型的范围
// 测试链接 : https://www.luogu.com.cn/problem/P3273
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 300001;
//int n, m;
//int num[MAXN];
//int up[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int dist[MAXN];
//int fa[MAXN];
//int siz[MAXN]; 
//int add[MAXN];
//int sta[MAXN];
//multiset<int> heads;
//int addAll = 0;
//
//void minusHead(int h) {
//    if (h != 0) {
//        heads.erase(heads.find(num[h] + add[h]));
//    }
//}
//
//void addHead(int h) {
//    if (h != 0) {
//        heads.insert(num[h] + add[h]);
//    }
//}
//
//void prepare() {
//    dist[0] = -1;
//    heads.clear();
//    for (int i = 1; i <= n; i++) {
//        up[i] = ls[i] = rs[i] = dist[i] = 0;
//        fa[i] = i;
//        siz[i] = 1;
//        add[i] = 0;
//        addHead(i);
//    }
//    addAll = 0;
//}
//
//int find(int i) {
//    fa[i] = fa[i] == i ? i : find(fa[i]);
//    return fa[i];
//}
//
//int merge(int i, int j) {
//    if (i == 0 || j == 0) return i + j;
//    if (num[i] < num[j]) {
//        swap(i, j);
//    }
//    rs[i] = merge(rs[i], j);
//    up[rs[i]] = i;
//    if (dist[ls[i]] < dist[rs[i]]) {
//        swap(ls[i], rs[i]);
//    }
//    dist[i] = dist[rs[i]] + 1;
//    fa[ls[i]] = i;
//    fa[rs[i]] = i;
//    return i;
//}
//
//int remove(int i) {
//    int h = find(i);
//    fa[ls[i]] = ls[i];
//    fa[rs[i]] = rs[i];
//    int s = merge(ls[i], rs[i]);
//    int f = up[i];
//    fa[i] = s;
//    up[s] = f;
//    if (h != i) {
//        fa[s] = h;
//        if (ls[f] == i) {
//            ls[f] = s;
//        } else {
//            rs[f] = s;
//        }
//        for (int d = dist[s]; dist[f] > d + 1; f = up[f], d++) {
//            dist[f] = d + 1;
//            if (dist[ls[f]] < dist[rs[f]]) {
//                swap(ls[f], rs[f]);
//            }
//        }
//    }
//    up[i] = ls[i] = rs[i] = dist[i] = 0;
//    return fa[s];
//}
//
//void down(int i, int v) {
//    if (i != 0) {
//        add[i] = 0;
//        int size = 0;
//        sta[++size] = i;
//        while (size > 0) {
//            i = sta[size--];
//            num[i] += v;
//            if (rs[i] != 0) sta[++size] = rs[i];
//            if (ls[i] != 0) sta[++size] = ls[i];
//        }
//    }
//}
//
//void u(int i, int j) {
//    int l = find(i);
//    int r = find(j);
//    if (l == r) return;
//    int lsize = siz[l];
//    minusHead(l);
//    int rsize = siz[r];
//    minusHead(r);
//    int addTag;
//    if (lsize <= rsize) {
//        down(l, add[l] - add[r]);
//        addTag = add[r];
//    } else {
//        down(r, add[r] - add[l]);
//        addTag = add[l];
//    }
//    int h = merge(l, r);
//    siz[h] = lsize + rsize;
//    add[h] = addTag;
//    addHead(h);
//}
//
//void a1(int i, int v) {
//    int h = find(i);
//    minusHead(h);
//    int l = remove(i);
//    if (l != 0) {
//        siz[l] = siz[h] - 1;
//        add[l] = add[h];
//        addHead(l);
//    }
//    num[i] = num[i] + add[h] + v;
//    fa[i] = i;
//    siz[i] = 1;
//    add[i] = 0;
//    addHead(i);
//    u(l, i);
//}
//
//void a2(int i, int v) {
//    int h = find(i);
//    minusHead(h);
//    add[h] += v;
//    addHead(h);
//}
//
//void a3(int v) {
//    addAll += v;
//}
//
//int f1(int i) {
//    return num[i] + add[find(i)] + addAll;
//}
//
//int f2(int i) {
//    int h = find(i);
//    return num[h] + add[h] + addAll;
//}
//
//int f3() {
//    return (*heads.rbegin()) + addAll;
//}
//
//int main(){
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    for (int i = 1; i <= n; i++) cin >> num[i];
//    prepare();
//    cin >> m;
//    for (int i = 1; i <= m; i++) {
//        string op; 
//        cin >> op;
//        if (op == "F3") {
//            cout << f3() << "\n";
//        } else {
//            int x; cin >> x;
//            if (op == "U") {
//                int y; cin >> y;
//                u(x, y);
//            } else if (op == "A1") {
//                int y; cin >> y;
//                a1(x, y);
//            } else if (op == "A2") {
//                int y; cin >> y;
//                a2(x, y);
//            } else if (op == "A3") {
//                a3(x);
//            } else if (op == "F1") {
//                cout << f1(x) << "\n";
//            } else {
//                cout << f2(x) << "\n";
//            }
//        }
//    }
//    return 0;
//}