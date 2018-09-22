#ifndef BINARY_TREE_HPP
#define BINARY_TREE_HPP

#include <memory>

template <typename T>
class BinaryTree {
  struct TreeNode;
  class const_iterator;

  std::unique_ptr<TreeNode> root{nullptr};
  const const_iterator _end{nullptr};
  int _size{0};

 private:
  void printInOrder(const TreeNode *const node) const;
  TreeNode *const find(const T val, TreeNode *const node) const;
  void insert(const T val, TreeNode *const node);
  TreeNode *const erase(const T val, TreeNode *const node);

 public:
  BinaryTree(){};
  explicit BinaryTree(std::initializer_list<T> il) { insert(il); }

  void print() const;

  bool empty() const { return !_size; }
  int size() const { return _size; }

  bool contains(const T val) const { return find(val, root.get()); }
  const_iterator find(const T val) const {
    return const_iterator(find(val, root.get()));
  }

  void insert(const T val);
  void insert(std::initializer_list<T> il) {
    for (auto v : il) insert(v);
  }

  void erase(const T val);
  void erase(std::initializer_list<T> il) {
    for (auto v : il) erase(v);
  }

  void clear();

  const const_iterator begin() const;
  const const_iterator end() const { return _end; }
};

#include "binary_tree.cpp"

#endif /*BINARY_TREE_HPP*/

/*
TODO:
Add template specialization for key, value pairs
Add iterator pre/postfix -- (support reverse iteration)
*/
