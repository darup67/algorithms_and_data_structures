#include <iostream>
#include <memory>


template <typename T>
class BinaryTree {
    struct TreeNode {
        std::unique_ptr<TreeNode> left{nullptr}, right{nullptr};
        TreeNode* parent{nullptr};
        const T val;

        explicit TreeNode(const T v) : val(v) {}
        explicit TreeNode(const T v, TreeNode *const p) : val(v), parent(p) {}
    };

    class const_iterator {
        const TreeNode* node;
        
    public:
        using iterator_category = std::input_iterator_tag;
        using value_type = const T;
        using difference_type = const T;
        using pointer = const T*;
        using reference = const T&;

        explicit const_iterator(TreeNode* n) : node(n) {}

        const_iterator& operator++() {
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

        const_iterator operator++(int) {
            const_iterator retVal{*this};
            ++(*this);
            return retVal;
        }

        inline bool operator==(const_iterator& other) const { return node == other.node; }
        inline bool operator!=(const_iterator& other) const { return !(*this == other); }
        inline reference operator*() const { return node->val; }
    };

    std::unique_ptr<TreeNode> root{nullptr};
    const const_iterator _end{nullptr};
    int _size{0};

private:
    void printInOrder(const TreeNode *const node) const {
        if (!node) return;
        printInOrder(node->left.get());
        std::cout << node->val << ", ";
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
                node->left = std::make_unique<TreeNode>(val, node);
                ++_size;
            } else {
                insert(val, node->left.get());
            }
        } else if (node->val < val) {
            if (!node->right) {
                node->right = std::make_unique<TreeNode>(val, node);
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
                replacement->left = std::unique_ptr<TreeNode>(node->left.release());
                if (replacement->left) replacement->left->parent = replacement;

                return replacement;
            }

            // Else, replace both subtrees and repair prev--x-->repl->right to prev->right
            replacement = prev->left.release();
            prev->left = std::unique_ptr<TreeNode>(replacement->right.release());
            if (prev->left) prev->left->parent = prev;

            replacement->left = std::unique_ptr<TreeNode>(node->left.release());
            replacement->right = std::unique_ptr<TreeNode>(node->right.release());
            if (replacement->left) replacement->left->parent = replacement;
            if (replacement->right) replacement->right->parent = replacement;

            return replacement;
        }
        if (node->val > val) {
            auto retNode{erase(val, node->left.get())};
            if (retNode != node->left.get()) {
                if (retNode) retNode->parent = node;
                node->left = std::unique_ptr<TreeNode>(retNode);
            }
        } else {
            auto retNode{erase(val, node->right.get())};
            if (retNode != node->right.get()) {
                if (retNode) retNode->parent = node;
                node->right = std::unique_ptr<TreeNode>(retNode);
            }
        }
        return node;
    }

public:
    BinaryTree() {};
    explicit BinaryTree(std::initializer_list<T> il) {
        insert(il);
    };

    void print() const {
        printInOrder(root.get());
        std::cout << std::endl;
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

    inline const_iterator find(const T val) const {
        return const_iterator(find(val, root.get()));
    }

    void insert(const T val) {
        if (!root) {
            root = std::make_unique<TreeNode>(val);
            ++_size;
        } else {
            insert(val, root.get());
        }
    }

    void insert(std::initializer_list<T> il) {
        for (auto v : il) insert(v);
    }
    
    void erase(const T val) {
        TreeNode *const newRoot = erase(val, root.get());
        if (newRoot != root.get()) {
            if (newRoot) newRoot->parent = nullptr;
            root = std::unique_ptr<TreeNode>(newRoot);
        }
    };

    void erase(std::initializer_list<T> il) {
        for (auto v : il) erase(v);
    }

    void clear() {
        root.reset();
        _size = 0;
    }

    const const_iterator begin() const {
        if (empty()) return _end;

        auto retNode{root.get()};
        while (retNode->left) retNode = retNode->left.get();
        return const_iterator(retNode);
    }

    const const_iterator end() const {
        return _end;
    }
};
