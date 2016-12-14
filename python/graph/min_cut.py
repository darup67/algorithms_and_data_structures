import random, copy

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

##    print('Before:')
##    for edge in g:
##        print(edge)
##    print('')
    
    g[:] = (edge for edge in g if edge[0] != edge[1])

##    print('After:')
##    for edge in g:
##        print(edge)
##    print('')

    if len(g) < 3:
        for edge in g:
            print(edge)
        print('')


def min_cut(g, n):
    minimum = 100000000;

    for x in range(0, 100):
        temp_g, temp_n = copy.deepcopy(g), n

##        for edge in temp_g:
##            print(edge)
##        print('')
        
        while (temp_n > 2):
            random_cut(temp_g)
            temp_n -= 1

        unique = 0
        for i in range(0, len(temp_g)):
            for j in range (i + 1, len(temp_g)):
                if temp_g[i][0] == temp_g[j][0] and temp_g[i][1] == temp_g[j][1]:
                    break
                elif temp_g[i][0] == temp_g[j][1] and temp_g[i][1] == temp_g[j][0]:
                    break
            else:
                unique += 1
        
        if unique < minimum:
            minimum = unique

    return minimum


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
##for edge in graph:
##    print(edge)
##print('')

print(min_cut(graph, numVertices))

