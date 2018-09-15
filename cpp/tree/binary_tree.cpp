#include <iostream>
#include <set>

#include "binary_tree.h"

using namespace std;


int main(int argc, char const *argv[])
{
    BinaryTree<int> tree{20,10,30};
    set<int> valueSet;

    tree.erase({10,30,20});

    if (!tree.empty()) {
        cout << endl << "ERROR!" << endl;
        cout << "Tree not empty, size: " << tree.size() << endl;
        exit(1);
    };

    for (int i{0}; i < 10000; ++i) {
        auto newVal = rand();
        if (i % 3 == 2) {
            auto it = valueSet.begin();
            auto finalPos = newVal % valueSet.size();
            for (; finalPos > 0; --finalPos) ++it;
            // cout << "Deleting " << *it << endl;
            tree.erase(*it);
            valueSet.erase(it);
        } else {
            // cout << "Inserting " << newVal << endl;
            tree.insert(newVal);
            valueSet.insert(newVal);
        }

        if (!equal(tree.begin(), tree.end(), valueSet.begin())) {
            cout << endl << "ERROR! Iteration #" << i << endl;
            
            cout << "Expected: ";
            for (int x : valueSet) cout << x << ", ";
            cout << endl;
            
            cout << "Actual: " << endl;
            int j{0};
            for (int x : tree) {
                cout << x << ", ";
                ++j;
                if (j > tree.size() + 10) break;
            }

            exit(1);
        }
    }

    tree.clear();

    if (!tree.empty()) {
        cout << endl << "ERROR!" << endl;
        cout << "Tree not empty, size: " << tree.size() << endl;
        exit(1);
    };
}

/*
TODO:
Replace rand() with better random func in tests
Add template specialization for key, value pairs
Add iterator pre/postfix -- (support reverse iteration)
*/