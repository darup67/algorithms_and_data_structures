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
    listA_count, listB_count, result = 0, 0, []
    
    while (len(listA) > listA_count or len(listB) > listB_count):
        if (len(listA) == listA_count):
            result.append(listB[listB_count])
            listB_count += 1
            
        elif (len(listB) == listB_count):
            result.append(listA[listA_count])
            listA_count += 1
            
        else:
            if (listA[listA_count] < listB[listB_count]):
                result.append(listA[listA_count])
                listA_count += 1
                
            else:
                result.append(listB[listB_count])
                listB_count += 1

    return result
