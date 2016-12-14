import random, copy
from operator import itemgetter

class Graph: pass

class Vertex:
    def __init__(self, active):
        self.original = []
        self.current = []
        self.contains = []
        self.active = active
        
def random_cut(g):
    ## Get random edge from available edges
    edgeList = []

    for v in g.vertices:
        if v.active:
            for num in v.current:
                edgeList.append([v.contains[0], num])

##    print(edgeList)

    ## Select random edge
    randEdge = edgeList[random.randrange(0, len(edgeList))]
##    print('canary 1')

    ## Set superVertex and deletedVertex
    superVertex, deletedVertex = g.vertices[randEdge[0]], g.vertices[randEdge[1]]
##    print('canary 2')

    ## Add deletedVertex.contains to superVertex.contains
    superVertex.contains += deletedVertex.contains
##    print('canary 3')

    ## Connect all edges adjacent to deletedVertex to superVertex
    superVertex.current += [num for num in deletedVertex.current if num != superVertex.contains[0]]
##    for y in deletedVertex.current:
##        if y != superVertex.contains[0]:
##            superVertex.current.append(y)

##    print('canary 4')

##    deletedVertex.contains = [deletedVertex.contains[0]]
    deletedVertex.active = False

    ## Renumber all references to deleted vertex
    for v in g.vertices:
        if v.active:
            for x in range(0, len(v.current)):
                if v.current[x] == deletedVertex.contains[0]:
                    v.current[x] = superVertex.contains[0]
            v.current = [num for num in v.current if num != v.contains[0]]

##    print('canary 5')

##    for x in range(0, len(graph.vertices)):
##        print(g.vertices[x].original)
##        print(g.vertices[x].current)
##        print(g.vertices[x].contains)
##        print(g.vertices[x].active)
##        print('')

def active_count(g):
    count = 0

    for v in g.vertices:
        if v.active:
            count += 1

    return count

def min_cut(g):
    minimum = 100000000;
    
    for x in range(0, len(g.vertices) - 3):
        random_cut(g)

    for v in g.vertices:
        if v.active:
            return len(v.current)

def is_in_list(inputList, inputEdge):
    for edge in inputList:
        if edge[0] == inputEdge[0] and edge[1] == inputEdge[1]:
            return True

    return False

def get_graph_from_file(filename): 
    newList, graph = list(open(filename)), Graph()
    graph.vertices, graph.edges = [Vertex(False)], []
    
##    for x in range(0, len(newList) + 1):
##        graph.vertices.append([])

    for item in newList:
        numList = list(map(int, item.rstrip('\n').split(' ')))
        graph.vertices.append(Vertex(True))
        graph.vertices[numList[0]].contains.append(numList[0])
        
        for edgeNum in numList[1:]:
##            if numList[0] < edgeNum:
##                edge = [numList[0], edgeNum, True]
##            else:
##                edge = [edgeNum, numList[0], True]
##            
##            if not is_in_list(graph.edges, edge):
##                graph.edges.append(edge)
            
            graph.vertices[numList[0]].original.append(edgeNum)
            
        graph.vertices[numList[0]].current = graph.vertices[numList[0]].original[:]

    return graph

graph = get_graph_from_file('data/min_cut_1.txt')
print(min_cut(graph))

for x in range(0, len(graph.vertices)):
    print(graph.vertices[x].original)
    print(graph.vertices[x].current)
    print(graph.vertices[x].contains)
    print(graph.vertices[x].active)
    print('')

##print(min_cut(graph))
