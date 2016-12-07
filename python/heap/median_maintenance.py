from heapq import heappush, heappop

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

##        print(leftHeap)
##        print(rightHeap)
##        print(medians)
        
    return medians;

print(maintain_median([7,2,9,6,3]))
