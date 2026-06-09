package class200;

// 仙人掌遍历和找环的过程，C++版

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 100001;
//const int MAXM = 200001;
//
//int head[MAXN];
//int nxt[MAXM];
//int to[MAXM];
//int weight[MAXM];
//int cntg;
//
//int dfn[MAXN];
//int low[MAXN];
//int cntd;
//int sta[MAXN];
//int top;
//
//int fromWeight[MAXN];
//
//void tarjan(int u, int preEdge) {
//    dfn[u] = low[u] = ++cntd;
//    sta[++top] = u;
//    for (int e = head[u]; e > 0; e = nxt[e]) {
//        if ((e ^ 1) == preEdge) { // 利用边的编号，标记回头路
//            continue;
//        }
//        int v = to[e];
//        int w = weight[e];
//        if (dfn[v] == 0) {
//            tarjan(v, e);
//            fromWeight[v] = w; // 设置v的from信息
//            if (low[v] < dfn[u]) { // 发现向上的环路
//                low[u] = min(low[u], low[v]);
//            } else if (low[v] > dfn[u]) { // 发现割边
//                top--;
//                // 实现有关割边的处理
//            } else { // 发现环
//                // 实现有关环的处理
//            }
//        } else {
//            if (dfn[v] < dfn[u]) { // 发现回边
//                fromWeight[v] = w; // 闭合边的信息给入环节点
//                low[u] = min(low[u], dfn[v]);
//            } else { // 发现弃边
//                // 一般什么也不做
//            }
//        }
//    }
//}