from heapq import heappush, heappop
from functools import reduce

def maintain_median(inputList):
    leftHeap, rightHeap, medians = [-inputList[0]], [], [inputList[0]]

    for x in range(1, len(inputList)):
        if inputList[x] < -leftHeap[0]:
            heappush(leftHeap, -inputList[x])
            if len(leftHeap) > len(rightHeap) + 1:
                heappush(rightHeap, -heappop(leftHeap))

        else:
            heappush(rightHeap, inputList[x])
            if len(rightHeap) > len(leftHeap) + 1:
                heappush(leftHeap, -heappop(rightHeap))

        if len(medians) % 2 == 0:
            if len(leftHeap) > len(rightHeap):
                medians += [-leftHeap[0]]
            else:
                medians += [rightHeap[0]]
        else:
            medians += [-leftHeap[0]]
        
    return medians;

def sum_mod(inputList, modVal):
    sum = reduce(lambda x,y: x+y, inputList)
    return sum % modVal

def get_list_from_file(filename):
    newList, result = list(open(filename)), []

    for item in newList:
        result.append(int(item.rstrip('\n')))
    
    return result

medianList = maintain_median(get_list_from_file('data/Median.txt'))
print(sum_mod(medianList, 10000))
