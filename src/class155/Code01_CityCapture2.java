package class155;

// 城池攻占，C++版
// 一共有n个城市，1号城市是城市树的头，每个城市都有防御值、上级城市编号、奖励类型、奖励值
// 如果奖励类型为0，任何骑士攻克这个城市后，攻击力会加上奖励值
// 如果奖励类型为1，任何骑士攻克这个城市后，攻击力会乘以奖励值
// 任何城市的上级编号 < 这座城市的编号，1号城市没有上级城市编号、奖励类型、奖励值
// 一共有m个骑士，每个骑士都有攻击力、第一次攻击的城市
// 如果骑士攻击力 >= 城市防御值，当前城市会被攻占，骑士获得奖励，继续攻击上级城市
// 如果骑士攻击力  < 城市防御值，那么骑士会在该城市牺牲，没有后续动作了
// 所有骑士都是独立的，不会影响其他骑士攻击这座城池的结果
// 打印每个城市牺牲的骑士数量，打印每个骑士攻占的城市数量
// 1 <= n、m <= 3 * 10^5，攻击值的增加也不会超过long类型范围
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
//int type[MAXN];       
//long long gain[MAXN];
//long long attack[MAXN];
//int first[MAXN];
//int deep[MAXN];
//int top[MAXN];
//int sacrifice[MAXN];
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
//void upgrade(int i, int t, long long v) {
//    if (t == 0) {
//        attack[i] += v;
//        add[i] += v;
//    } else {
//        attack[i] *= v;
//        mul[i] *= v;
//        add[i] *= v;
//    }
//}
//
//void down(int i) {
//    if (mul[i] != 1 || add[i] != 0) {
//        int l = ls[i];
//        int r = rs[i];
//        if (l != 0) {
//            attack[l] = attack[l] * mul[i] + add[i];
//            mul[l] = mul[l] * mul[i];
//            add[l] = add[l] * mul[i] + add[i];
//        }
//        if (r != 0) {
//            attack[r] = attack[r] * mul[i] + add[i];
//            mul[r] = mul[r] * mul[i];
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
//void compute() {
//    deep[1] = 1;
//    for (int i = 2; i <= n; i++) {
//        deep[i] = deep[belong[i]] + 1;
//    }
//    for (int i = 1; i <= m; i++) {
//        if (top[first[i]] == 0) {
//            top[first[i]] = i;
//        } else {
//            top[first[i]] = merge(top[first[i]], i);
//        }
//    }
//    for (int i = n; i >= 1; i--) {
//        while (top[i] != 0 && attack[top[i]] < defend[i]) {
//            die[top[i]] = i;
//            sacrifice[i]++;
//            top[i] = pop(top[i]);
//        }
//        if (top[i] != 0) {
//            upgrade(top[i], type[i], gain[i]);
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
//        cin >> belong[i] >> type[i] >> gain[i];
//    }
//    for (int i = 1; i <= m; i++) {
//        cin >> attack[i] >> first[i];
//    }
//    compute();
//    for (int i = 1; i <= n; i++) {
//        cout << sacrifice[i] << "\n";
//    }
//    for (int i = 1; i <= m; i++) {
//        cout << deep[first[i]] - deep[die[i]] << endl;
//    }
//    return 0;
//}