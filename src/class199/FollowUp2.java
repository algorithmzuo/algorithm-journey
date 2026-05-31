package class199;

// 有向图的基环树找环，C++版
// 这个文件课上没讲，针对有向图的找环，理解起来非常容易
// 无向图的入环节点u，可以发现环的末尾节点v，因为边是无向的
// 于是用dfn[u] < dfn[v]来判断环
// 但是对有向图来说，假设出现的环如下
// 入环节点u -> a -> b -> 环的末尾节点v -> 入环节点u
// 不能放在节点u处判断，因为v都不一定是u的儿子节点，因为边是有向的，也许只有v到u的边
// 所以有向图的基环树找环，把判断时机，放在环的末尾节点处，具体说明如下
// 节点一共三种状态，状态0表示没有访问，状态1表示在递归路径上，状态2表示递归执行完了
// 依然记录每个点，在递归路径里的上一个节点，也就是课上讲的from信息
// 来到节点u时，判断儿子节点v，是否在递归路径上
// 是的话，从u开始通过from走回v，环路就搞定了

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//
//int head[MAXN];
//int nxt[MAXN];
//int to[MAXN];
//int cntg;
//
//int state[MAXN];
//int from[MAXN];
//bool cycle[MAXN];
//
//bool dfs(int u) {
//    state[u] = 1;
//    bool find = false;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        int v = to[e];
//        if (state[v] == 0) {
//            from[v] = u;
//            if (dfs(v)) {
//                find = true;
//            }
//        } else if (state[v] == 1) {
//            cycle[v] = true;
//            for (int i = u; i != v; i = from[i]) {
//                cycle[i] = true;
//            }
//            find = true;
//        }
//        if (find) {
//            break;
//        }
//    }
//    state[u] = 2;
//    return find;
//}