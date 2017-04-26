from BinaryTree import BinaryTree

tree = BinaryTree()
for x in range(10, 0, -1): tree.insert(x)
tree.delete(9)
tree.replace(8,50)

print(1 in tree)
print(0 in tree)
print()
for value in tree: print(value)