from HashMap import HashMap

map = HashMap()
map[0] = 'there'
map[32] = 'here'
map[0] = 'everywhere'

print(0 in map)
print('there' in map)
print(map[0])
print(map[32])
print(map[64])

for key, value in map:
    print('{}: {}'.format(key, value))