package class155;

// 城池攻占，C++版
// 输入保证，如果城市a管辖城市b，必有a < b
// 测试链接 : https://www.luogu.com.cn/problem/P3261
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 300001;
//int n, m;
//long long defend[MAXN];
//int belong[MAXN];
//int op[MAXN];       
//long long gain[MAXN];
//int deep[MAXN];
//int top[MAXN];
//int sacrifice[MAXN];
//long long attack[MAXN];
//int born[MAXN];
//int die[MAXN];
//int ls[MAXN];
//int rs[MAXN];
//int dist[MAXN];
//long long mul[MAXN];
//long long add[MAXN];
//
//void prepare() {
//    dist[0] = -1;
//    for (int i = 1; i <= m; i++) {
//        ls[i] = rs[i] = dist[i] = 0;
//        mul[i] = 1;
//        add[i] = 0;
//    }
//    for (int i = 1; i <= n; i++) {
//        sacrifice[i] = top[i] = 0;
//    }
//}
//
//void down(int i) {
//    if (mul[i] != 1 || add[i] != 0) {
//        int l = ls[i];
//        int r = rs[i];
//        if (l != 0) {
//            attack[l] = attack[l] * mul[i] + add[i];
//            mul[l] *= mul[i];
//            add[l] = add[l] * mul[i] + add[i];
//        }
//        if (r != 0) {
//            attack[r] = attack[r] * mul[i] + add[i];
//            mul[r] *= mul[i];
//            add[r] = add[r] * mul[i] + add[i];
//        }
//        mul[i] = 1;
//        add[i] = 0;
//    }
//}
//
//int merge(int i, int j) {
//    if (i == 0 || j == 0) {
//        return i + j;
//    }
//    if (attack[i] > attack[j]) {
//        swap(i, j);
//    }
//    down(i);
//    rs[i] = merge(rs[i], j);
//    if (dist[ls[i]] < dist[rs[i]]) {
//        swap(ls[i], rs[i]);
//    }
//    dist[i] = dist[rs[i]] + 1;
//    return i;
//}
//
//int pop(int i) {
//    down(i);
//    int ans = merge(ls[i], rs[i]);
//    ls[i] = rs[i] = dist[i] = 0;
//    return ans;
//}
//
//void upgrade(int i, int t, long long v) {
//    if (t == 0) {
//        add[i] += v;
//        attack[i] += v;
//    } else {
//        mul[i] *= v;
//        add[i] = add[i] * v;
//        attack[i] = attack[i] * v;
//    }
//}
//
//void compute() {
//    deep[1] = 1;
//    for (int i = 2; i <= n; i++) {
//        deep[i] = deep[belong[i]] + 1;
//    }
//    for (int i = 1; i <= m; i++) {
//        if (top[born[i]] == 0) {
//            top[born[i]] = i;
//        } else {
//            top[born[i]] = merge(top[born[i]], i);
//        }
//    }
//    for (int i = n; i >= 1; i--) {
//        while (top[i] != 0 && attack[top[i]] < defend[i]) {
//            die[top[i]] = i;
//            sacrifice[i]++;
//            top[i] = pop(top[i]);
//        }
//        if (top[i] != 0) {
//            upgrade(top[i], op[i], gain[i]);
//            if (top[belong[i]] == 0) {
//                top[belong[i]] = top[i];
//            } else {
//                top[belong[i]] = merge(top[belong[i]], top[i]);
//            }
//        }
//    }
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n >> m;
//    prepare();
//    for (int i = 1; i <= n; i++) {
//        cin >> defend[i];
//    }
//    for (int i = 2; i <= n; i++) {
//        cin >> belong[i] >> op[i] >> gain[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> attack[i] >> born[i];
//    }
//    compute();
//    for (int i = 1; i <= n; i++) {
//        cout << sacrifice[i] << "\n";
//    }
//    for (int i = 1; i <= m; i++) {
//        cout << deep[born[i]] - deep[die[i]] << endl;
//    }
//    return 0;
//}