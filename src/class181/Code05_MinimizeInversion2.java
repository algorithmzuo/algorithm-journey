package class181;

// 最小化逆序对，C++版
// 给定数字n，表示一棵二叉树有n个叶节点，叶节点的权值都不同并且都是1~n范围的数字
// 树上的任何节点，要么是叶节点，要么一定有两个孩子，请从输入流中不断读取数字x来建树
// 如果 x != 0，表示当前来到叶节点并且权值为x，注意只有叶节点有权值
// 如果 x == 0，表示当前来到非叶节点，先递归建立左树，再递归建立右树
// 输入流保证根据规则可以建好这棵二叉树，你可以任选一些节点，交换这些节点的左右子树
// 目的是先序遍历整棵树之后，所有叶节点权值组成的序列中，逆序对数量尽可能小，打印这个最小值
// 2 <= n <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3521
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXT = 5000001;
//int n;
//
//int ls[MAXT];
//int rs[MAXT];
//int siz[MAXT];
//int cntt;
//
//long long ans;
//
//void up(int i) {
//    siz[i] = siz[ls[i]] + siz[rs[i]];
//}
//
//int build(int jobi, int l, int r) {
//    int rt = ++cntt;
//    if (l == r) {
//        siz[rt]++;
//    } else {
//        int mid = (l + r) >> 1;
//        if (jobi <= mid) {
//            ls[rt] = build(jobi, l, mid);
//        } else {
//            rs[rt] = build(jobi, mid + 1, r);
//        }
//        up(rt);
//    }
//    return rt;
//}
//
//long long no, yes;
//
//int merge(int l, int r, int t1, int t2) {
//    if (t1 == 0 || t2 == 0) {
//        return t1 + t2;
//    }
//    if (l == r) {
//        siz[t1] += siz[t2];
//    } else {
//        no += 1LL * siz[rs[t1]] * siz[ls[t2]];
//        yes += 1LL * siz[rs[t2]] * siz[ls[t1]];
//        int mid = (l + r) >> 1;
//        ls[t1] = merge(l, mid, ls[t1], ls[t2]);
//        rs[t1] = merge(mid + 1, r, rs[t1], rs[t2]);
//        up(t1);
//    }
//    return t1;
//}
//
//int dfs() {
//    int rt;
//    int val;
//    cin >> val;
//    if (val == 0) {
//        int left = dfs();
//        int right = dfs();
//        no = yes = 0;
//        rt = merge(1, n, left, right);
//        ans += min(no, yes);
//    } else {
//        rt = build(val, 1, n);
//    }
//    return rt;
//}
//
//int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(nullptr);
//    cin >> n;
//    dfs();
//    cout << ans << '\n';
//    return 0;
//}