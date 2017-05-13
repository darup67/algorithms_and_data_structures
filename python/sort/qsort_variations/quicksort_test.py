from random import randrange

def quicksort(li):
    qsort(li, 0, len(li))

def qsort(li, lo, hi):
    if hi - lo < 2: return

    swap_point = lo
    pivot_point = randrange(lo, hi)
    pivot_value = li[pivot_point]

    li[lo], li[pivot_point] = li[pivot_point], li[lo]

    for i in range(lo + 1, hi):
        if li[i] < pivot_value:
            swap_point += 1
            li[i], li[swap_point] = li[swap_point], li[i]
    
    li[lo], li[swap_point] = li[swap_point], li[lo]

    qsort(li, lo, swap_point)
    qsort(li, swap_point + 1, hi)

test = [randrange(0,1000) for i in range(100)]
# test = [5,3,7,1,9,6,0]
quicksort(test)
print(test)
    