def binary_search(li, value):
    def bsearch(li, value, lo, hi):
        if hi - lo < 2: return -1

        midpt = ((hi - lo) // 2) + lo
        if li[midpt] == value: return midpt
        if li[midpt] > value:  return bsearch(li, value, lo, midpt)
        return bsearch(li, value, midpt + 1, hi)
        
        return bsearch(li, value, midpt + 1, hi)

    return bsearch(li, value, 0, len(li))

def binary_two(li, value):
    def bsearch(li, value, lo, hi):
        if hi - lo < 2:
            return lo if li[lo] == value else -1

        midpt = ((hi - lo) // 2) + lo
        if li[midpt] > value: return bsearch(li, value, lo, midpt)
        return bsearch(li, value, midpt, hi)

    return bsearch(li, value, 0, len(li))


test = [1,2,3,4,5]
print(binary_search(test, 0))
print(binary_search(test, 2))
print(binary_search(test, 5))
print(binary_search(test, 8))
print()

print(binary_two(test, 0))
print(binary_two(test, 2))
print(binary_two(test, 5))
print(binary_two(test, 8))