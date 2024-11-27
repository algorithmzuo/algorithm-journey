package class152;

// 可持久化文艺平衡树，FHQ-Treap实现，C++版
// 一开始序列为空，实现如下操作，操作一共发生n次
// v 1 x y : 基于v版本的序列，在第x个数后插入y，生成新版本的序列
// v 2 x   : 基于v版本的序列，删除第x个数，生成新版本的序列
// v 3 x y : 基于v版本的序列，范围[x,y]所有数字翻转，生成新版本的序列
// v 4 x y : 基于v版本的序列，查询范围[x,y]所有数字的和，生成新版本的序列状况=v版本状况
// 不管什么操作，都基于某个v版本，操作完成后得到新版本的序列，但v版本不会变化
// 每种操作给定的参数都是有效的，插入数字的范围[-10^6, +10^6]
// 1 <= n <= 2 * 10^5
// 本题目要求强制在线，具体规则可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P5055
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <iostream>
//#include <vector>
//#include <cstdlib>
//#include <ctime>
//using namespace std;
//
//const int MAXN = 200001;
//const int MAXM = MAXN * 100;
//
//int cnt = 0;
//int head[MAXN];
//int key[MAXM];
//int ls[MAXM];
//int rs[MAXM];
//int size[MAXM];
//bool rev[MAXM];
//long long sum[MAXM];
//double priority[MAXM];
//
//int copy(int i) {
//    key[++cnt] = key[i];
//    ls[cnt] = ls[i];
//    rs[cnt] = rs[i];
//    size[cnt] = size[i];
//    rev[cnt] = rev[i];
//    sum[cnt] = sum[i];
//    priority[cnt] = priority[i];
//    return cnt;
//}
//
//void up(int i) {
//    size[i] = size[ls[i]] + size[rs[i]] + 1;
//    sum[i] = sum[ls[i]] + sum[rs[i]] + key[i];
//}
//
//void down(int i) {
//    if (rev[i]) {
//        if (ls[i] != 0) {
//            ls[i] = copy(ls[i]);
//            rev[ls[i]] ^= 1;
//        }
//        if (rs[i] != 0) {
//            rs[i] = copy(rs[i]);
//            rev[rs[i]] ^= 1;
//        }
//        swap(ls[i], rs[i]);
//        rev[i] = false;
//    }
//}
//
//void split(int l, int r, int i, int rank) {
//    if (i == 0) {
//        rs[l] = ls[r] = 0;
//    } else {
//        i = copy(i);
//        down(i);
//        if (size[ls[i]] + 1 <= rank) {
//            rs[l] = i;
//            split(i, r, rs[i], rank - size[ls[i]] - 1);
//        } else {
//            ls[r] = i;
//            split(l, i, ls[i], rank);
//        }
//        up(i);
//    }
//}
//
//int merge(int l, int r) {
//    if (l == 0 || r == 0) {
//        return l + r;
//    }
//    if (priority[l] >= priority[r]) {
//        l = copy(l);
//        down(l);
//        rs[l] = merge(rs[l], r);
//        up(l);
//        return l;
//    } else {
//        r = copy(r);
//        down(r);
//        ls[r] = merge(l, ls[r]);
//        up(r);
//        return r;
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    srand(time(0));
//    int n;
//    cin >> n;
//    long long lastAns = 0;
//    for (int i = 1; i <= n; i++) {
//        int version, op;
//        long long x, y = 0;
//        cin >> version >> op >> x;
//        x ^= lastAns;
//        if (op != 2) {
//            cin >> y;
//            y ^= lastAns;
//        }
//        int l, m, lm, r;
//        if (op == 1) {
//            split(0, 0, head[version], x);
//            l = rs[0];
//            r = ls[0];
//            ls[0] = rs[0] = 0;
//            key[++cnt] = y;
//            size[cnt] = 1;
//            sum[cnt] = y;
//            priority[cnt] = (double)rand() / RAND_MAX;
//            head[i] = merge(merge(l, cnt), r);
//        } else if (op == 2) {
//            split(0, 0, head[version], x);
//            lm = rs[0];
//            r = ls[0];
//            split(0, 0, lm, x - 1);
//            l = rs[0];
//            m = ls[0];
//            ls[0] = rs[0] = 0;
//            head[i] = merge(l, r);
//        } else if (op == 3) {
//            split(0, 0, head[version], y);
//            lm = rs[0];
//            r = ls[0];
//            split(0, 0, lm, x - 1);
//            l = rs[0];
//            m = ls[0];
//            ls[0] = rs[0] = 0;
//            rev[m] ^= 1;
//            head[i] = merge(merge(l, m), r);
//        } else {
//            split(0, 0, head[version], y);
//            lm = rs[0];
//            r = ls[0];
//            split(0, 0, lm, x - 1);
//            l = rs[0];
//            m = ls[0];
//            ls[0] = rs[0] = 0;
//            lastAns = sum[m];
//            cout << lastAns << endl;
//            head[i] = merge(merge(l, m), r);
//        }
//    }
//    return 0;
//}