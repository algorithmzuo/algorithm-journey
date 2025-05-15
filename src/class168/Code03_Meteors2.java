package class168;

// 陨石雨，C++版
// 一共有n个国家，给定n个数字，表示每个国家希望收集到的陨石数量
// 一共有m个区域，1号区顺时针到2号区...m号区顺时针到1号区，即环形相连
// 每个区域只属于某一个国家，给定m个数字，表示每个区域归属给哪个国家
// 接下来会依次发生k场陨石雨，陨石雨格式 l r num，含义如下
// 从l号区顺时针到r号区发生了陨石雨，每个区域都增加num个陨石
// 打印每个国家经历前几场陨石雨，可以达到收集要求，如果无法满足，打印"NIE"
// 1 <= n、m、k <= 3 * 10^5    1 <= 陨石数量 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3527
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 300001;
//
//int n, m, k;
//
//int qid[MAXN];
//int need[MAXN];
//
//int rainl[MAXN];
//int rainr[MAXN];
//int num[MAXN];
//int used = 0;
//
//int head[MAXN];
//int nxt[MAXN];
//int to[MAXN];
//int cnt = 0;
//
//long long tree[MAXN << 1];
//
//int lset[MAXN];
//int rset[MAXN];
//
//int ans[MAXN];
//
//void addEdge(int i, int v) {
//    nxt[++cnt] = head[i];
//    to[cnt] = v;
//    head[i] = cnt;
//}
//
//int lowbit(int i) {
//    return i & -i;
//}
//
//void add(int i, int v) {
//    int siz = m * 2;
//    while (i <= siz) {
//        tree[i] += v;
//        i += lowbit(i);
//    }
//}
//
//void add(int l, int r, int v) {
//    add(l, v);
//    add(r + 1, -v);
//}
//
//long long query(int i) {
//    long long ret = 0;
//    while (i > 0) {
//    	ret += tree[i];
//        i -= lowbit(i);
//    }
//    return ret;
//}
//
//void compute(int ql, int qr, int vl, int vr) {
//    if (ql > qr) {
//        return;
//    }
//    if (vl == vr) {
//        for (int i = ql; i <= qr; i++) {
//            ans[qid[i]] = vl;
//        }
//    } else {
//        int mid = (vl + vr) >> 1;
//        int lsiz = 0, rsiz = 0;
//        while (used < mid) {
//            used++;
//            add(rainl[used], rainr[used], num[used]);
//        }
//        while (used > mid) {
//            add(rainl[used], rainr[used], -num[used]);
//            used--;
//        }
//        for (int i = ql; i <= qr; i++) {
//            int id = qid[i];
//            long long satisfy = 0;
//            for (int e = head[id]; e > 0; e = nxt[e]) {
//                satisfy += query(to[e]) + query(to[e] + m);
//                if (satisfy >= need[id]) {
//                    break;
//                }
//            }
//            if (satisfy >= need[id]) {
//                lset[++lsiz] = id;
//            } else {
//                rset[++rsiz] = id;
//            }
//        }
//        for (int i = 1; i <= lsiz; i++) {
//            qid[ql + i - 1] = lset[i];
//        }
//        for (int i = 1; i <= rsiz; i++) {
//            qid[ql + lsiz + i - 1] = rset[i];
//        }
//        compute(ql, ql + lsiz - 1, vl, mid);
//        compute(ql + lsiz, qr, mid + 1, vr);
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    for (int i = 1, nation; i <= m; i++) {
//        cin >> nation;
//        addEdge(nation, i);
//    }
//    for (int i = 1; i <= n; i++) {
//        qid[i] = i;
//        cin >> need[i];
//    }
//    cin >> k;
//    for (int i = 1; i <= k; i++) {
//        cin >> rainl[i] >> rainr[i] >> num[i];
//        if (rainr[i] < rainl[i]) {
//            rainr[i] += m;
//        }
//    }
//    compute(1, n, 1, k + 1);
//    for (int i = 1; i <= n; i++) {
//        if (ans[i] == k + 1) {
//            cout << "NIE" << '\n';
//        } else {
//            cout << ans[i] << '\n';
//        }
//    }
//    return 0;
//}