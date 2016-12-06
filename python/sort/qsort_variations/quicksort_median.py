import math

count = 0

def quicksort(inputList, lo=0, hi=None):
    global count

    ## Default value for hi (not possible to assign in parameter list)
    if hi == None:
        hi = len(inputList)  ## the hi value for range is exclusive

    ## Base case: if the subarray inputList[lo:hi] is of length 1 or less, return
    if hi - lo < 2:
        return

    ## Median-of-three calculation
    mid = math.floor(((hi - 1) - lo) / 2) + lo
##    print('{} {} {}'.format(inputList[lo], inputList[mid], inputList[hi - 1]))

    if inputList[lo] >= inputList[mid] and inputList[lo] <= inputList[hi - 1]:
        pivotChoice = lo
    elif inputList[lo] >= inputList[hi - 1] and inputList[lo] <= inputList[mid]:
        pivotChoice = lo
    elif inputList[mid] >= inputList[lo] and inputList[mid] <= inputList[hi - 1]:
        pivotChoice = mid
    elif inputList[mid] >= inputList[hi - 1] and inputList[mid] <= inputList[lo]:
        pivotChoice = mid
    else:
        pivotChoice = hi - 1
    
    ## Choose pivot as pivotChoice, swap with lo
    inputList[pivotChoice], inputList[lo] = inputList[lo], inputList[pivotChoice]

    ## Naive pivot selection (intentional for this example)
    pivot, i = lo, lo  ## where i is the index of the last value lower than the pivot

    ## Partition: swap all values lower than pivot into the partition [lo + 1 ... i]
    ## i gets pre-incremented to simplify the swap
    for j in range(lo + 1, hi):
        if inputList[j] < inputList[pivot]:
            i = i + 1
            inputList[j], inputList[i] = inputList[i], inputList[j]

    ## Swap pivot into ith position
    inputList[pivot], inputList[i] = inputList[i], inputList[pivot]

    ## Increment comparison count
    count += hi - lo - 1;

    ## Recursively sort subarrays to the left and right of pivot (in-place)
    quicksort(inputList, lo, i)    ## quicksort inputList[lo, i - 1]
    quicksort(inputList, i+1, hi)  ## quicksort inputList[i + 1, hi]

def get_list_from_file(filename):
    newList, result = list(open(filename)), []

    for item in newList:
        result.append(int(item.rstrip('\n')))
    
    return result

def validate(inputList):
    for x in range(0, len(inputList) - 1):
        if inputList[x] > inputList[x + 1]:
            return False;
    return True

testList = get_list_from_file('../data/QuickSort.txt')
quicksort(testList)
print(validate(testList))
print(count)
