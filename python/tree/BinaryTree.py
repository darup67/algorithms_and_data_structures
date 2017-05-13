class Node:
    def __init__(self, data, parent=None):
        self.data = data
        self.parent = parent
        self.left = None
        self.right = None

class BinaryTree:
    def __init__(self):
        self.__root = None
        self.__length = 0

    def __len__(self):
        return self.__length

    def __contains__(self, data):
        def find_data(curr):
            if data == curr.data: return True
            elif data < curr.data:
                if curr.left is None: return False
                else: return find_data(curr.left)
            else:
                if curr.right is None: return False
                else: return find_data(curr.right)

        if self.__root is None: return False
        return find_data(self.__root)

    def __iter__(self):
        def list_gen(curr):
            if curr.left is not None: yield from list_gen(curr.left)
            yield curr.data
            if curr.right is not None: yield from list_gen(curr.right)
        
        if self.__root is None: return iter(())
        return list_gen(self.__root)

    def get_root(self):
        return self.__root
    
    def insert(self, data):
        def do_insertion(curr):
            if data < curr.data:
                if curr.left is None: curr.left = Node(data, curr)
                else: do_insertion(curr.left)
            else:
                if curr.right is None: curr.right = Node(data, curr)
                else: do_insertion(curr.right)

        if self.__root is None: self.__root = Node(data)
        else: do_insertion(self.__root)
        self.__length += 1

    def replace(self, data, new_data):
        self.delete(data)
        self.insert(new_data)
    
    def delete(self, data):
        def replace_with_next_highest(node):
            def get_left(curr):
                while curr.left is not None: curr = curr.left
                return curr

            assert node.right is not None

            if node.right.left is None:
                # print('Leaf node')
                node.data = node.right.data
                if node.right.right is None: node.right = None  # Leaf node
                else:                                           # Only has a right subtree
                    node.right.right.parent = node
                    node.right = node.right.right
            else:
                # print('Tree node')
                next_highest = get_left(node.right)
                node.data = next_highest.data
                if next_highest.right is None: next_highest.parent.left = None  # Leaf node
                else:                                                           # Only has a right subtree
                    next_highest.right.parent = next_highest.parent
                    next_highest.parent.left = next_highest.right
            # print('Replaced {} with {}'.format(data, node.data))
        
        def do_deletion(curr, parent, went_left):
            if data == curr.data:
                # print('Commencing delete of {}'.format(data))
                # If curr is a leaf node
                if curr.left is None and curr.right is None:
                    if went_left: parent.left = None
                    else:         parent.right = None
                # If curr only has a left subtree
                elif curr.right is None:
                    if went_left: parent.left = curr.left
                    else:         parent.right = curr.left
                    curr.left.parent = parent
                # If curr only has a right subtree
                elif curr.left is None:
                    if went_left: parent.left = curr.right
                    else:         parent.right = curr.right
                    curr.right.parent = parent
                # If curr has both subtrees
                else:
                    replace_with_next_highest(curr)
                    
            elif data < curr.data:
                if curr.left is None: raise RuntimeError('Data not in tree')
                else: do_deletion(curr.left, curr, True)
            else:
                if curr.right is None: raise RuntimeError('Data not in tree')
                else: do_deletion(curr.right, curr, False)
        
        if self.__root is None: raise RuntimeError('Deletion on empty tree')
        if data == self.__root.data:
            # print('Deleting root')
            if   self.__root.left == None and self.__root.right == None: self.__root = None
            elif self.__root.right == None:                              self.__root = self.__root.left
            elif self.__root.left == None:                               self.__root = self.__root.right
            else:                                                        replace_with_next_highest(self.__root)
        else: do_deletion(self.__root, None, True)
        self.__length -= 1