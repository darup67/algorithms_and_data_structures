def quicksort(inputList, lo=0, hi=None):
    ## Default value for hi (not possible to assign in parameter list)
    if hi == None:
        hi = len(inputList)  ## the hi value for range is exclusive

    ## Base case: if the subarray inputList[lo:hi] is of length 1 or less, return
    if hi - lo < 2:
        return

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

testList = get_list_from_file('data/QuickSort.txt')
quicksort(testList)
print(validate(testList))
