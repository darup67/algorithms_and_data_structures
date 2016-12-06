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
    for j in range(lo + 1, hi):
        if inputList[j] < inputList[pivot]:
            i = i + 1
            inputList[j], inputList[i] = inputList[i], inputList[j]

    ## Swap pivot into ith position
    inputList[pivot], inputList[i] = inputList[i], inputList[pivot]

    ## Recursively sort subarrays to the left and right of pivot
    quicksort(inputList, lo, i)    ## quicksort from lo to i - 1 (pivot at i)
    quicksort(inputList, i+1, hi)  ## quicksort from i + 1 to hi

testList = [49, 4, 3, 22, 7, 45, 2, 6, 11, 9, 17, 1, 37]
quicksort(testList)
print(testList)
