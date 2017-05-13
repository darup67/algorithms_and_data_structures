from HashMap import HashMap

map = HashMap()
map[0] = 'there'
map[32] = 'here'
map[64] = 'everywhere'

print(0 in map)
print('there' in map)
print()

for key, value in map:
    print('{}: {}'.format(key, value))
print('End of hash table\n')

del map[32]
for key, value in map:
    print('{}: {}'.format(key, value))
print('End of hash table\n')

del map[64]
for key, value in map:
    print('{}: {}'.format(key, value))
print('End of hash table\n')

del map[0]
for key, value in map:
    print('{}: {}'.format(key, value))
print('End of hash table\n')
