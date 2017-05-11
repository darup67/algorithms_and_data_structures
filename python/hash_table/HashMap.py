class ListNode:
    def __init__(self, data):
        self.data = data
        self.next = None

class HashMap:
    __MAX_CHAIN = 8
    __EXPANSION_FACTOR = 4

    def __init__(self, size=32):
        self.__data = [None for x in range(size)]

    def __getindex__(self, key):
        return (len(self.__data) - 1) & hash(key)

    def __contains__(self, key):
        return True if self[key] is not None else False

    def __getitem__(self, key):
        node = self.__data[self.__getindex__(key)]
        while node is not None:
            if node.data[0] == key: return node.data[1]
            node = node.next
        return None

    def __setitem__(self, key, value):
        index = self.__getindex__(key)
        node, prev = self.__data[index], None

        if node is None:
            self.__data[index] = ListNode((key, value))
        else:
            chain_length = 1
            while node is not None:
                if node.data[0] == key:
                    node.data = key, value
                    return
                prev = node
                node = node.next
                chain_length += 1
            
            prev.next = ListNode((key, value))
            if chain_length > self.__MAX_CHAIN: self.__expand__()

    def __delitem__(self, key):
        index = self.__getindex__(key)
        node = self.__data[index]

        if node is None: raise KeyError(key)
        if node.data[0] == key: self.__data[index] = node.next
        else:
            while node.next is not None:
                if node.next.data[0] == key:
                    node.next = node.next.next
                    return
                node = node.next
            raise KeyError(key)

    def __iter__(self):
        for node in self.__data:
            while node is not None:
                yield node.data
                node = node.next

    def __expand__(self):
        'Rehash table if chains grow too long'
        new_table = HashMap(len(self.__data) * self.__EXPANSION_FACTOR)
        for key, value in self:
            self[key] = value
        self = new_table
