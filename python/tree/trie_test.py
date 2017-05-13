from Trie import Trie

trie = Trie()
trie.add('who')
trie.add('what')
trie.add('where')
trie.add('when')
trie.add('why')
trie.add('how')

print('what' in trie)
print('how' in trie)
print('howard' in trie)
print('ho' in trie)
print(trie.has_word('ho'))
print(trie.has_word('how'))