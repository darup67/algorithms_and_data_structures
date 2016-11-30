def mergesort(inputList):
    head = inputList[0 : len(inputList) // 2]
    tail = inputList[len(inputList) // 2 : len(inputList)]

    if (len(head) > 1):
        head = mergesort(head)
    if (len(tail) > 1):
        tail = mergesort(tail)
    
    result = merge(head, tail)
    return result

def merge(listA, listB):
    result = []
    
    while (len(listA) > 0 or len(listB) > 0):
        if (len(listA) == 0):
            result.append(listB.pop(0))
        elif (len(listB) == 0):
            result.append(listA.pop(0))
        else:
            if (listA[0] < listB[0]):
                result.append(listA.pop(0))
            else:
                result.append(listB.pop(0))

    return result

print(mergesort([5,5785,3,6,88,2,5675,1,24,90]))
