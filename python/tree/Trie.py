class Node:
    def __init__(self, char, children=dict(), is_word=False):
        self.char = char
        self.children = children
        self.is_word = is_word

    def add_child(self, node):
        self.children[node.char] = node

class Trie:
    @classmethod
    def __search__(cls, word, is_prefix, node, pos=0):
        if pos == len(word):
            if is_prefix: return True
            return True if node.is_word else False
        if word[pos] not in node.children: return False
        return cls.__search__(word, is_prefix, node.children[word[pos]], pos + 1)

    def __init__(self):
        self.root = Node(None)

    def __contains__(self, prefix):
        return self.__search__(prefix, True, self.root)

    def add(self, word):
        node, pos = self.root, 0
        while pos < len(word):
            new_node = Node(word[pos])
            node.add_child(new_node)
            node, pos = new_node, pos + 1
        node.is_word = True

    def has_word(self, word):
        return self.__search__(word, False, self.root)
