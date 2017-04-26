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
        def do_deletion(curr, parent, went_left):
            if data == curr.data:
                # If curr is a leaf node
                if curr.left is None and curr.right is None:
                    if went_left: parent.left = None
                    else:         parent.right = None
                # If curr only has a left subtree
                elif curr.right is None:
                    if went_left: parent.left = curr.left
                    else:         parent.right = curr.left
                # If curr only has a right subtree
                elif curr.left is None:
                    if went_left: parent.left = curr.right
                    else:         parent.right = curr.right
                # If curr has both subtrees
                else:
                    raise NotImplementedError
                
            elif data < curr.data:
                if curr.left is None: raise RuntimeError
                else: do_deletion(curr.left, curr, True)
            else:
                if curr.right is None: raise RuntimeError
                else: do_deletion(curr.right, curr, False)
        
        if self.__root is None: raise RuntimeError
        elif self.__root.left is None and self.__root.right is None: self.__root = None
        else: do_deletion(self.__root, None, False)