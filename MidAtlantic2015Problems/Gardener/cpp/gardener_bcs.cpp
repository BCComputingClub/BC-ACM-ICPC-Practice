// Sample solution for The Scheming Gardener
// Author: Bryce Sandlund

#include <iostream>
#include <algorithm>
#include <cmath>
#include <vector>

#define INF 1000000000000LL
#define EP 1e-10

using namespace std;

typedef long long ll;
typedef pair<ll, ll> ii;
typedef vector<ii> vii;
typedef vector<ll> vi;
typedef vector<vi> vvi;
typedef pair<double, ii> dii;
typedef vector<dii> vdii;
typedef vector<bool> vb;

ll P, E;

// returns index of global point from adj_list[cur]
ll get_index(vii &pts, vvi &adj_list, ll cur, ii global) {
    for (ll i = 0; i < adj_list[cur].size(); ++i) {
        ii other_pt = pts[adj_list[cur][i]];
        if (other_pt == global) {
            return i;
        }
    }
    return -1;
}

// gets index in adj_list of first point going counterclockwise from angle at cur
ll get_next(vii &pts, vvi &adj_list, ll cur, double angle) {
    ii cur_pt = pts[cur];
    vdii adj_pts;
    for (ll i = 0; i < adj_list[cur].size(); ++i) {
        ii other_pt = pts[adj_list[cur][i]];
        double angle = atan2(other_pt.second - cur_pt.second, other_pt.first - cur_pt.first);
        adj_pts.push_back(dii(angle, other_pt));
    }

    sort(adj_pts.begin(), adj_pts.end());
    vdii::iterator it = lower_bound(adj_pts.begin(), adj_pts.end(), dii(angle+EP, ii(INF, INF)));
    if (it == adj_pts.end()) {  // need to wrap around
        it = adj_pts.begin();
    }

    return get_index(pts, adj_list, cur, it->second);
}

// checks if pts[cur] needs to be removed and recursively removes points affected by its removal
void check_remove(vii &pts, vvi &adj_list, ll cur) {
    ii cur_pt = pts[cur];
    if (adj_list[cur].size() == 1) {   // size 1 implies it is now a useless string, remove it 
        ll other = adj_list[cur][0];
        adj_list[other].erase(adj_list[other].begin() + get_index(pts, adj_list, other, cur_pt));
        adj_list[cur].erase(adj_list[cur].begin());
        check_remove(pts, adj_list, other); // recursively remove
    }

    if (adj_list[cur].empty()) {
        pts[cur].first = INF;   // effectively removes without affecting indexing
        pts[cur].second = INF;
    }
}

// returns index of point in component with minimum x-value, lowest y if tied
ll dfs(vii &pts, vvi &adj_list, ll cur, vb &visited) {
    if (!visited[cur]) {
        visited[cur] = true;
        ll min_idx = cur;
        for (ll i = 0; i < adj_list[cur].size(); ++i) {
            ll other = dfs(pts, adj_list, adj_list[cur][i], visited);
            if (pts[other] < pts[min_idx]) {
                min_idx = other;
            }
        }
        return min_idx;
    }
    return cur;
}

// executes peel operation on connected component
void peel(vii &pts, vvi &adj_list, ll start) {
    ll prev = start;
    ll cur_relative = get_next(pts, adj_list, prev, -M_PI/2);

    vvi adj_list_copy = adj_list;    

    while (true) {
        ll cur = adj_list[prev][cur_relative];
        ii cur_pt = pts[cur];
        ii prev_pt = pts[prev];
        ll prev_relative = get_index(pts, adj_list, cur, prev_pt);

        adj_list_copy[prev][cur_relative] = -1; // mark line segements for removal
        adj_list_copy[cur][prev_relative] = -1;

        if (cur == start) // don't find next, we've already looped
            break;

        double angle = atan2(prev_pt.second - cur_pt.second, prev_pt.first - cur_pt.first);
        ll next = get_next(pts, adj_list, cur, angle);
        prev = cur;
        cur_relative = next;
    }

    for (ll i = 0; i < P; ++i) {
        for (ll j = 0; j < adj_list_copy[i].size();) {
            if (adj_list_copy[i][j] == -1) {
                adj_list_copy[i].erase(adj_list_copy[i].begin() + j); // remove line segments
            }
            else {
                ++j;
            }
        }
    }

    adj_list = adj_list_copy;
}

int main() {
    for (ll cs = 1; cin >> P && P; ++cs) {
        cin >> E;

        vii pts(P);
        for (ll i = 0; i < P; ++i) {
            cin >> pts[i].first >> pts[i].second;
        }

        vvi adj_list(P);
        for (ll i = 0; i < E; ++i) {
            ll u, v;
            cin >> u >> v;
            adj_list[u].push_back(v);
            adj_list[v].push_back(u);
        }

        ll loops = -1;
        while (true) {      // peels the outside layer off of regions in each iteration
            bool change = false;
            
            vb visited(P, false);
            for (ll i = 0; i < P; ++i) {
                if (!visited[i]) {
                    ll start = dfs(pts, adj_list, i, visited);
                    if (pts[start].first != INF) {
                        change = true;
                        peel(pts, adj_list, start);
                    }
                }
            }

            if (!change)
                break;
            
            for (ll i = 0; i < P; ++i) {
                check_remove(pts, adj_list, i); // clean up useless strings and "delete" isolated points
            }
            ++loops;
        }

        cout << loops << endl;
    }
    return 0;
}
