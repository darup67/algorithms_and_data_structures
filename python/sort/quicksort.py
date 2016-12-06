def quicksort(inputList, lo, hi):
    print(" ")
    print("lo, hi: {} {}".format(lo, hi))

    diff = hi - lo
    if diff < 2:
        print(diff)
        return
    
    pivot = lo
    i = lo + 1

    for j in range(lo + 1, hi):
        if inputList[j] < inputList[pivot]:
            inputList[j], inputList[i] = inputList[i], inputList[j]
            print("swap at {},{}: {} <-> {}".format(i, j, inputList[i], inputList[j]))
            i = i + 1

    inputList[pivot], inputList[i-1] = inputList[i-1], inputList[pivot]
    print("pivot swap at {},{}: {} <-> {}".format(pivot, i-1, inputList[i-1], inputList[pivot]))
    print(inputList)
    
    quicksort(inputList, lo, i)
    quicksort(inputList, i, hi)

testList = [4, 3, 7, 2, 6, 9, 1]
quicksort(testList, 0, len(testList))
print(testList)
