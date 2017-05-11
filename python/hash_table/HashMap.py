class ListNode:
    def __init__(self, data):
        self.data = data
        self.next = None

class HashMap:
    __MAX_CHAIN = 8

    def __init__(self):
        self.data = [None for x in range(32)]

    def __getindex__(self, key):
        return (len(self.data) - 1) & hash(key)

    def __contains__(self, key):
        return True if self.__getitem__(key) is not None else False
    
    def __getitem__(self, key):
        node = self.data[self.__getindex__(key)]
        while node is not None:
            if node.data[0] == key: return node.data[1]
            node = node.next
        return None
    
    def __setitem__(self, key, value):
        index = self.__getindex__(key)
        node, prev = self.data[index], None

        if node is None:
            self.data[index] = ListNode((key, value))
        else:
            while node is not None:
                if node.data[0] == key:
                    node.data = key, value
                    return
                prev = node
                node = node.next
            prev.next = ListNode((key, value))

    def __iter__(self):
        for node in self.data:
            while node is not None:
                yield node.data
                node = node.next