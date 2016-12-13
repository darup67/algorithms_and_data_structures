import random

numVertices = 0

def random_cut(g):
    ## Get random edge from list of edges
    randEdge = g[random.randrange(0, len(g))]

    ## Connect all edges adjacent to randEdge[0] to randEdge[1]  
    for edge in g:
        if edge[0] == randEdge[0]:
            edge[0] = randEdge[1]
            
        elif edge[1] == randEdge[0]:
            edge[1] = randEdge[1]

    print('Before:')
    for edge in g:
        print(edge)
    print('')
    
    g[:] = (edge for edge in g if edge[0] != edge[1] and not is_in_list(g, edge))

    print('After:')
    for edge in g:
        print(edge)
    print('')


def min_cut(g):
    global numVertices
    while (numVertices > 2):
        random_cut(g)
        numVertices -= 1

    return len(g)


def is_in_list(inputList, inputEdge):
    for edge in inputList:
        if edge[0] == inputEdge[1] and edge[1] == inputEdge[0]:
            return True

    return False


def get_graph_from_file(filename):
    global numVertices
    newList, result = list(open(filename)), []

    for item in newList:
        numVertices += 1
        numList = item.rstrip('\n').split(' ')

        for edgeNum in numList[1:]:
            edge = [numList[0], edgeNum]
            if (not is_in_list(result, edge)):
                result.append(edge)

    return result

graph = get_graph_from_file('data/min_cut_1.txt')
for edge in graph:
    print(edge)

print('')
print(min_cut(graph))

