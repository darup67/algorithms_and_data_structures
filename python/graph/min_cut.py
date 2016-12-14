import random, copy
from operator import itemgetter

class Graph: pass

def random_cut(g):
    ## Get random edge from list of edges
    randEdge = g.edges[random.randrange(0, len(g.edges))]

    ## Connect all edges adjacent to randEdge[0] to randEdge[1]  
    for edge in g.edges:
        if edge[0] == randEdge[0]:
            edge[0] = randEdge[1]
            
        elif edge[1] == randEdge[0]:
            edge[1] = randEdge[1]

##    print('Before:')
##    for edge in g.edges:
##        print(edge)
##    print('')
    
    g.edges[:] = (edge for edge in g.edges if edge[0] != edge[1])
    for edge in g.edges:
        if edge[0] > edge[1]:
            edge[0], edge[1] = edge[1], edge[0]

##    print('After:')
##    for edge in g.edges:
##        print(edge)
##    print('')

    if len(g.edges) < 3:
        for edge in g.edges:
            print(edge)
        print('')

def min_cut(g):
    minimum = 100000000;

    for x in range(0, 100):
        temp_g, n = copy.deepcopy(g), len(g.vertices) - 1
        
        while (n > 2):
            random_cut(temp_g)
            n -= 1

        temp_g.edges.sort(key=itemgetter(0,1))
##        for edge in temp_g.edges:
##            print(edge)
##        print('')

        unique = 1
        for x in range(0, len(temp_g.edges) - 1):
            if temp_g.edges[x][0] != temp_g.edges[x+1][0] or temp_g.edges[x][1] != temp_g.edges[x+1][1]:
                unique += 1
        
        if unique < 2:
            for edge in temp_g.edges:
                print(edge)
            print('')
        
        if unique < minimum:
            minimum = unique

    return minimum

def is_in_list(inputList, inputEdge):
    for edge in inputList:
        if edge[0] == inputEdge[0] and edge[1] == inputEdge[1]:
            return True

    return False

def get_graph_from_file(filename):
    newList, graph = list(open(filename)), Graph()
    graph.vertices, graph.edges = [], []
    
    for x in range(0, len(newList) + 1):
        graph.vertices.append([])

    for item in newList:
        numList = list(map(int, item.rstrip('\n').split(' ')))
        
        for edgeNum in numList[1:]:
            if numList[0] < edgeNum:
                edge = [numList[0], edgeNum]
            else:
                edge = [edgeNum, numList[0]]
            
            if not is_in_list(graph.edges, edge):
                graph.edges.append(edge)
            
            graph.vertices[numList[0]].append(edge)

    return graph

graph = get_graph_from_file('data/min_cut_1.txt')
##for vertex in graph.vertices:
##    print(vertex)
##for edge in graph.edges:
##    print(edge)
##print('')

print(min_cut(graph))
