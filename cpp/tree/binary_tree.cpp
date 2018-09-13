#include <iostream>
#include <memory>
#include <set>

using namespace std;

template <typename T>
class BinaryTree {
    struct TreeNode {
        unique_ptr<TreeNode> left{nullptr}, right{nullptr};
        const T val;

        TreeNode(const T v) : val(v) {}
    };
    unique_ptr<TreeNode> root{nullptr};

private:
    void printInOrder(const TreeNode *const node) const {
        if (!node) return;
        printInOrder(node->left.get());
        cout << node->val << ", ";
        printInOrder(node->right.get());
    }

    TreeNode *const find(const T val, TreeNode *const node) const {
        if (!node)            { return nullptr; }
        if (node->val == val) { return node; }
        if (node->val > val)  { return find(val, node->left.get()); }
        return find(val, node->right.get());
    }

    void insert(const T val, TreeNode *const node) {
        if (node->val > val) {
            if (!node->left)  node->left = unique_ptr<TreeNode>(new TreeNode(val));
            else              insert(val, node->left.get());
        } else if (node->val < val) {
            if (!node->right) node->right = unique_ptr<TreeNode>(new TreeNode(val));
            else              insert(val, node->right.get());
        }
    }

    TreeNode *const remove(const T val, TreeNode *const node) {
        if (!node) { return nullptr; }
        if (node->val == val) {
            // Zero children: return null
            if (!node->left && !node->right) return nullptr;

            // One child: return the non-null child
            if (!node->left)  return node->right.release();
            if (!node->right) return node->left.release();

            // Two children: Hibbard deletion
            // First, find successor node
            TreeNode* prev{node};
            TreeNode* replacement{node->right.get()};
            while (replacement->left) {
                prev = replacement;
                replacement = replacement->left.get();
            }

            // If successor == node.right, only replace left subtree
            if (prev == node) {
                replacement = node->right.release();
                replacement->left = unique_ptr<TreeNode>(node->left.release());
                return replacement;
            }

            // Else, replace both subtrees and repair prev--x-->repl->right to prev->right
            replacement = prev->left.release();
            prev->left = unique_ptr<TreeNode>(replacement->right.release());
            replacement->left = unique_ptr<TreeNode>(node->left.release());
            replacement->right = unique_ptr<TreeNode>(node->right.release());
            return replacement;
        }
        if (node->val > val) {
            auto retNode{remove(val, node->left.get())};
            if (retNode != node->left.get()) {
                node->left = unique_ptr<TreeNode>(retNode);
            }
        } else {
            auto retNode{remove(val, node->right.get())};
            if (retNode != node->right.get()) {
                node->right = unique_ptr<TreeNode>(retNode);
            }
        }
        return node;
    }

public:
    void print() const {
        printInOrder(root.get());
        cout << endl;
    }

    bool contains(const T val) {
        return find(val, root.get());
    }

    void insert(const T val) {
        if (!root) root = unique_ptr<TreeNode>(new TreeNode(val));
        else       insert(val, root.get());
    }
    
    void remove(const T val) {
        TreeNode *const newRoot = remove(val, root.get());
        if (newRoot != root.get()) {
            root = unique_ptr<TreeNode>(newRoot);
        }
    };
};


int main(int argc, char const *argv[])
{
    BinaryTree<int> tree;
    set<int> valueSet;

    for (int i{0}; i < 100000; ++i) {
        auto newVal = rand();
        if (i % 3 == 2) {
            auto it = valueSet.begin();
            auto finalPos = newVal % valueSet.size();
            for (; finalPos > 0; --finalPos) ++it;
            tree.remove(*it);
            valueSet.erase(it);
        } else {
            tree.insert(newVal);
            valueSet.insert(newVal);
        }
        if (i % 1000 == 0) {
            for (int x : valueSet) {
                try {
                    if (!tree.contains(x)) throw exception();
                } catch (const exception e) {
                    cout << endl << "ERROR!" << endl;
                    
                    cout << "Expected: ";
                    for (int x : valueSet) cout << x << ", ";
                    cout << endl;
                    
                    cout << "Actual:   ";
                    tree.print();

                    exit(1);
                }
            }
        }
    }

    return 0;
}

/*
Remove implicit bool conversion
Add template specialization for key, value pairs
Add parent nodes for iterator (shared_ptr/weak_ptr? -> find YouTube example)
Add iterator for in-order reading
*/