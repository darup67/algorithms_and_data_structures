#include <iostream>
#include <memory>
#include <set>

using namespace std;


template <typename T>
class BinaryTree {
    struct TreeNode {
        unique_ptr<TreeNode> left{nullptr}, right{nullptr};
        TreeNode* parent{nullptr};
        const T val;

        explicit TreeNode(const T v) : val(v) {}
        explicit TreeNode(const T v, TreeNode *const p) : val(v), parent(p) {}
    };

    class iterator {
        const TreeNode* node;
        
    public:
        using iterator_category = bidirectional_iterator_tag;
        using value_type = T;
        using difference_type = T;
        using pointer = T*;
        using reference = T&;

        explicit iterator(TreeNode* n) : node(n) {}

        inline const T& operator*() const { return node->val; }
        inline bool operator!=(iterator& it) const { return node != it.node; }
        
        iterator& operator++() {
            // Node is the root or has a right child - descend to successor node
            if (!node->parent || node->right) {
                node = node->right.get();
                while (node && node->left) node = node->left.get();

            // Node is on the left - ascend once
            } else if (!node->parent->right || node->parent->right.get() != node) {
                node = node->parent;

            // Node is on the right
            } else {

                // Ascend to the parent of the root of this right branch
                auto currVal{node->val}; 
                while (node->parent && node->parent->right.get() == node) node = node->parent;
                node = node->parent;
                
                // If we ascend to the root, and it is the current node (node == root) or less than the current node (node == endNode)
                if (node && node->val <= currVal) node = nullptr;
            }

            return *this;
        }
    };

    unique_ptr<TreeNode> root{nullptr};
    const iterator _end{nullptr};
    int _size{0};

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
            if (!node->left) {
                node->left = make_unique<TreeNode>(val, node);
                ++_size;
            } else {
                insert(val, node->left.get());
            }
        } else if (node->val < val) {
            if (!node->right) {
                node->right = make_unique<TreeNode>(val, node);
                ++_size;
            } else {
                insert(val, node->right.get());
            }
        }
    }

    TreeNode *const erase(const T val, TreeNode *const node) {
        if (!node) { return nullptr; }
        if (node->val == val) {
            if (_size) --_size;

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
                if (replacement->left) replacement->left->parent = replacement;

                return replacement;
            }

            // Else, replace both subtrees and repair prev--x-->repl->right to prev->right
            replacement = prev->left.release();
            prev->left = unique_ptr<TreeNode>(replacement->right.release());
            if (prev->left) prev->left->parent = prev;

            replacement->left = unique_ptr<TreeNode>(node->left.release());
            replacement->right = unique_ptr<TreeNode>(node->right.release());
            if (replacement->left) replacement->left->parent = replacement;
            if (replacement->right) replacement->right->parent = replacement;

            return replacement;
        }
        if (node->val > val) {
            auto retNode{erase(val, node->left.get())};
            if (retNode != node->left.get()) {
                if (retNode) retNode->parent = node;
                node->left = unique_ptr<TreeNode>(retNode);
            }
        } else {
            auto retNode{erase(val, node->right.get())};
            if (retNode != node->right.get()) {
                if (retNode) retNode->parent = node;
                node->right = unique_ptr<TreeNode>(retNode);
            }
        }
        return node;
    }

public:
    BinaryTree() {};

    void print() const {
        printInOrder(root.get());
        cout << endl;
    }

    inline bool empty() const {
        return !_size;
    }

    inline int size() const {
        return _size;
    }

    inline bool contains(const T val) const {
        return find(val, root.get());
    }

    void insert(const T val) {
        if (!root) {
            root = make_unique<TreeNode>(val);
            ++_size;
        } else {
            insert(val, root.get());
        }
    }
    
    void erase(const T val) {
        TreeNode *const newRoot = erase(val, root.get());
        if (newRoot != root.get()) {
            if (newRoot) newRoot->parent = nullptr;
            root = unique_ptr<TreeNode>(newRoot);
        }
    };

    const iterator begin() const {
        if (empty()) return _end;

        auto retNode{root.get()};
        while (retNode->left) retNode = retNode->left.get();
        return iterator(retNode);
    }

    const iterator end() const {
        return _end;
    }
};


int main(int argc, char const *argv[])
{
    BinaryTree<int> tree;
    set<int> valueSet;

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
                cout << x << ', ';
                ++j;
                if (j > tree.size() + 10) break;
            }

            exit(1);
        }
    }
}

/*
Replace rand() with better random func in tests
Add template specialization for key, value pairs
Add iterator postfix ++
Add iterator pre/postfix -- (support reverse iteration)
Add const_iterator
*/