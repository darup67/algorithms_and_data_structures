def radix_sort(arr):
    def count_sort(arr, pos):
        data = [[] for x in range(256)]
        for string in arr:
            data[ord(string[pos])].append(string)
        return [string for string_list in data for string in string_list]

    maxlen = 0
    for string in arr:
        if len(string) > maxlen:
            maxlen = len(string)

    data = [[] for x in range(maxlen + 1)]
    for string in arr:
        data[len(string)].append(string)

    for i in range(maxlen + 1):
        for j in range(i):
            data[i] = count_sort(data[i], j)

    out_gen = (string for string_list in data for string in string_list)
    for i in range(len(arr)):
        arr[i] = next(out_gen)

test = ['ho', 'there', 'hi', 'bet', 'bag', 'what']
radix_sort(test)
for string in test: print(string)