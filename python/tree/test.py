from BinaryTree import BinaryTree
from random import randrange

tree = BinaryTree()
test_list = [randrange(0, 1000) for x in range(100)]
for x in test_list: tree.insert(x)
test_list.sort()

for i in range(50):
    index = randrange(0, len(test_list))
    tree.delete(test_list[index])
    test_list.remove(test_list[index])
    try:
        assert test_list == [x for x in tree]
    except AssertionError:
        tree_list = [x for x in tree]
        print(len(tree_list), len(test_list))
        print()
        for x, y in zip(tree_list, test_list):
            print(x, y)
        break

# root = tree.get_root()
# print(root)
# print(root.left.data)
# print(root.left.right.data)
# print(root.data)
# print(root.right.data)