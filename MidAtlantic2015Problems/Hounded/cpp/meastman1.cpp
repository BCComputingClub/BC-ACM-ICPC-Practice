#include <array>
#include <cassert>
#include <functional>
#include <iomanip>
#include <iostream>
#include <string>
#include <vector>

using namespace std;

const int max_grid_width = 50;
const int max_grid_height = 50;
int grid_width, grid_height;

struct GridCell;

struct GridPos {
    int row, col;
    GridPos(int row, int col) : row{row}, col{col} { }
    bool valid() const { return row >= 0 && row < grid_height && col >= 0 && col < grid_width; }
    GridCell& cell();
};
ostream& operator<<(ostream& os, const GridPos& pos) {
    return os << '{' << pos.row << ", " << pos.col << '}';
}

struct GridCell {
    char sym;
    int dist_kennel{-1};
    int dist_thief{-1};
    int dist_exit{-1};
    int magic{-1};

    GridPos pos() const;
    vector<GridCell*> neighbors() const {
        GridPos my_pos = pos();
        vector<GridCell*> result;
        for (auto pos : {
            GridPos{my_pos.row - 1, my_pos.col},
            GridPos{my_pos.row, my_pos.col - 1},
            GridPos{my_pos.row, my_pos.col + 1},
            GridPos{my_pos.row + 1, my_pos.col},
        }) {
            if (pos.valid()) result.push_back(&pos.cell());
        }
        return result;
    }
};

array<GridCell, max_grid_width * max_grid_height> grid;

// GridPos / GridCell methods that reference grid
GridCell& GridPos::cell() {
    return grid[row * max_grid_width + col];
}
GridPos GridCell::pos() const {
    assert(this >= grid.data());
    assert(this < grid.data() + grid.size());
    const int offset = this - grid.data();
    return {offset / max_grid_width, offset % max_grid_width};
}

// ---------------------------------------------------------------------------

void flood_fill(
        char sym,
        int GridCell::*member,
        function<bool(GridCell&, int)> should_visit=[](GridCell&, int) { return true; }) {
    vector<GridCell*> updated;
    for (int row = 0; row < grid_height; ++row) {
        for (int col = 0; col < grid_width; ++col) {
            GridCell& cell = GridPos{row, col}.cell();
            if (cell.sym == sym && should_visit(cell, 0)) {
                cell.*member = 0;
                updated.push_back(&cell);
            }
        }
    }

    for (int depth = 1; !updated.empty(); ++depth) {
        decltype(updated) to_visit;
        swap(updated, to_visit);
        for (auto pCell : to_visit) {
            for (auto pNeighbor : pCell->neighbors()) {
                if ((*pNeighbor).*member == -1 && pNeighbor->sym != 'X' && should_visit(*pNeighbor, depth)) {
                    (*pNeighbor).*member = depth;
                    updated.push_back(pNeighbor);
                }
            }
        }
    }

#if 0
    cout << "Flood fill for sym " << sym << ":\n";
    for (int row = 0; row < grid_height; ++row) {
        for (int col = 0; col < grid_width; ++col) {
            GridCell& cell = GridPos{row, col}.cell();
            if (cell.sym == sym || cell.sym == 'X') {
                cout << setw(3) << cell.sym;
            } else {
                cout << setw(3) << cell.*member;
            }
        }
        cout << '\n';
    }
    cout << '\n';
#endif
}

void handle_case() {
    flood_fill('K', &GridCell::dist_kennel);
    flood_fill('T', &GridCell::dist_thief);
    flood_fill('E', &GridCell::dist_exit, [](GridCell& cell, int) {
        return cell.dist_thief < cell.dist_kennel;
    });

    bool hit_exit = false;
    flood_fill('T', &GridCell::magic, [&hit_exit](GridCell& cell, int depth) {
        if (cell.dist_exit < 0) return false;

        const int thief_exit_time = depth + cell.dist_exit;
        const int dog_exit_time = cell.dist_kennel + (cell.dist_exit + 1) / 2;
        if (thief_exit_time >= dog_exit_time) return false;

        if (cell.sym == 'E') hit_exit = true;
        return true;
    });

    cout << (hit_exit ? "KEEP IT\n" : "DROP IT\n");
}

int main() {
    string line;
    while (cin >> grid_width >> grid_height && grid_width >= 3 && grid_height >= 3) {
        getline(cin, line);
        assert(grid_width <= max_grid_width);
        assert(grid_height <= max_grid_height);

        grid.fill({});
        for (int row = 0; row < grid_height; ++row) {
            getline(cin, line);
            assert(line.size() >= static_cast<size_t>(grid_width));
            for (int col = 0; col < grid_width; ++col) {
                GridPos{row, col}.cell().sym = line[col];
            }
        }

        handle_case();
    }
}
