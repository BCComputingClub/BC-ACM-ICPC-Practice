// Sample solution for Avoiding an Arrrgument
// Author: Bryce Sandlund

#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

using namespace std;

typedef long long ll;
typedef vector<ll> vi;
typedef pair<ll, char> ic;
typedef vector<ic> vic;
    
ll M, N;

// just simulate worst-case scenario with given choice of first gem
ll simulate(vic &gems, char choice) {
    ll pirate_ct = 0;
    ll me = 0;
    for (ll i = 0; i < gems.size(); ++i) {
        if (gems[i].second == choice) {
            me = gems[i].first;
            break;
        }
    }

    for (ll i = 0; i < gems.size(); ++i) {
        if (gems[i].second != choice) {    
            if (pirate_ct == N) {
                me += gems[i].first;
                break;
            }
            ++pirate_ct;
        }
    }
    return me;
}

int main() {
    for (ll cs = 1; cin >> M >> N && M; ++cs) {
        vic gems;
        set<char> choices;
        for (ll i = 0; i < M; ++i) {
            char G;
            cin >> G;
            choices.insert(G);
            
            for (ll j = 0; j < N+1; ++j) {
                ll val;
                cin >> val;
                gems.push_back(ic(val, G));
            }
        }

        sort(gems.rbegin(), gems.rend());

        ll ans = -1;
        char gem;
        for (set<char>::iterator it = choices.begin(); it != choices.end(); ++it) {
            ll cur_ans = simulate(gems, *it);
            if (cur_ans > ans || (cur_ans == ans && *it < gem)) {
                ans = cur_ans;
                gem = *it;
            }
        }

        cout << gem << endl;
    }
    return 0;
}
