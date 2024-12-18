package class155;

// 可持久化左偏树 + k短路，C++版
// 测试链接 : https://www.luogu.com.cn/problem/P2483
// 如下实现是C++的版本，C++版本和java版本逻辑完全一样
// 提交如下代码，可以通过所有测试用例

//#include <bits/stdc++.h>
//
//using namespace std;
//
//const int MAXN = 50001;
//const int MAXM = 200001;
//const int MAXT = MAXN * 20;
//const int MAXH = 4200001;
//
//int n, m;
//double money;
//
//int headg[MAXN];
//int tog[MAXM];
//int nextg[MAXM];
//double weightg[MAXM];
//int cntg = 0;
//
//int headr[MAXN];
//int tor[MAXM];
//int nextr[MAXM];
//double weightr[MAXM];
//int cntr = 0;
//
//int rt[MAXN];
//double num[MAXT];
//int ls[MAXT];
//int rs[MAXT];
//int dist[MAXT];
//int fa[MAXT];
//int cntt = 0;
//
//int idx[MAXH];
//double cost[MAXH];
//int cntd = 0;
//int heap[MAXH];
//int cnth = 0;
//
//bool vis[MAXN];
//int path[MAXN];
//double dis[MAXN];
//
//void addEdgeG(int u, int v, double w){
//    cntg++;
//    nextg[cntg] = headg[u];
//    tog[cntg] = v;
//    weightg[cntg] = w;
//    headg[u] = cntg;
//}
//
//void addEdgeR(int u, int v, double w){
//    cntr++;
//    nextr[cntr] = headr[u];
//    tor[cntr] = v;
//    weightr[cntr] = w;
//    headr[u] = cntr;
//}
//
//int init(int f, double v){
//    cntt++;
//    num[cntt] = v;
//    ls[cntt] = rs[cntt] = dist[cntt] = 0;
//    fa[cntt] = f;
//    return cntt;
//}
//
//int clone(int i){
//    cntt++;
//    num[cntt] = num[i];
//    ls[cntt] = ls[i];
//    rs[cntt] = rs[i];
//    dist[cntt] = dist[i];
//    fa[cntt] = fa[i];
//    return cntt;
//}
//
//int merge(int i, int j){
//    if(i == 0 || j == 0){
//        return i + j;
//    }
//    if(num[i] > num[j]){
//        swap(i, j);
//    }
//    int k = clone(i);
//    rs[k] = merge(rs[k], j);
//    if(dist[ls[k]] < dist[rs[k]]){
//        swap(ls[k], rs[k]);
//    }
//    dist[k] = dist[rs[k]] + 1;
//    return k;
//}
//
//void heapAdd(int i, double v){
//    idx[++cntd] = i;
//    cost[cntd] = v;
//    heap[++cnth] = cntd;
//    int cur = cnth;
//    while(cur > 1){
//        int up = cur / 2;
//        if(cost[heap[up]] > cost[heap[cur]]){
//            swap(heap[up], heap[cur]);
//            cur = up;
//        }
//        else{
//            break;
//        }
//    }
//}
//
//int heapPop(){
//    int ans = heap[1];
//    heap[1] = heap[cnth--];
//    int cur = 1;
//    while(cur * 2 <= cnth){
//        int l = cur * 2;
//        int r = l + 1;
//        int best = l;
//        if(r <= cnth && cost[heap[r]] < cost[heap[l]]){
//            best = r;
//        }
//        if(cost[heap[best]] < cost[heap[cur]]){
//            swap(heap[best], heap[cur]);
//            cur = best;
//        }
//        else{
//            break;
//        }
//    }
//    return ans;
//}
//
//bool isEmpty(){
//    return cnth == 0;
//}
//
//void dijkstra(){
//    fill(dis, dis + MAXN, 1e18);
//    dis[n] = 0;
//    cntd = cnth = 0;
//    heapAdd(n, 0.0);
//    while(!isEmpty()){
//        int h = heapPop();
//        int u = idx[h];
//        double w = cost[h];
//        if(!vis[u]){
//            vis[u] = true;
//            for(int e = headr[u]; e != 0; e = nextr[e]){
//                int v = tor[e];
//                if(dis[v] > w + weightr[e]){
//                    dis[v] = w + weightr[e];
//                    path[v] = e;
//                    heapAdd(v, dis[v]);
//                }
//            }
//        }
//    }
//}
//
//void mergeRoad(){
//    cntd = cnth = 0;
//    for(int i = 1; i <= n; i++){
//        heapAdd(i, dis[i]);
//    }
//    dist[0] = -1;
//    while(!isEmpty()){
//        int h = heapPop();
//        int u = idx[h];
//        for(int e = headg[u]; e != 0; e = nextg[e]){
//            if(e != path[u]){
//                rt[u] = merge(rt[u], init(tog[e], weightg[e] + dis[tog[e]] - dis[u]));
//            }
//        }
//        if(path[u] != 0){
//            rt[u] = merge(rt[u], rt[tog[path[u]]]);
//        }
//    }
//}
//
//int expand(){
//    int ans = 0;
//    money -= dis[1];
//    if(money >= 0){
//        ans++;
//        cntd = cnth = 0;
//        if(rt[1] != 0){
//            heapAdd(rt[1], num[rt[1]]);
//        }
//        while(!isEmpty()){
//            int h = heapPop();
//            int u = idx[h];
//            double w = cost[h];
//            money -= w + dis[1];
//            if(money < 0){
//                break;
//            }
//            ans++;
//            if(ls[u] != 0){
//                heapAdd(ls[u], w - num[u] + num[ls[u]]);
//            }
//            if(rs[u] != 0){
//                heapAdd(rs[u], w - num[u] + num[rs[u]]);
//            }
//            if(fa[u] != 0 && rt[fa[u]] != 0){
//                heapAdd(rt[fa[u]], w + num[rt[fa[u]]]);
//            }
//        }
//    }
//    return ans;
//}
//
//int main(){
//    ios::sync_with_stdio(false);
//    cin.tie(NULL);
//    cin >> n >> m >> money;
//    int u, v;
//    double w;
//    for(int i = 1; i <= m; i++){
//        cin >> u >> v >> w;
//        if(u != n){
//            addEdgeG(u, v, w);
//            addEdgeR(v, u, w);
//        }
//    }
//    dijkstra();
//    mergeRoad();
//    int ans = expand();
//    cout << ans << endl;
//    return 0;
//}