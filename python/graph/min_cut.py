import random, copy
from operator import itemgetter

class Graph: pass

class Vertex:
    def __init__(self, active):
        self.list = []
        self.added = []
        self.active = active
        
def random_cut(g):
    ## Get random edge from list of edges
    randEdge = g.edges[random.randrange(0, len(g.edges))]

    ## Select superVertex and deletedVertex
    if round(random.random()) == 1:
        superVertex, deletedVertex = g.vertices[randEdge[0]], g.vertices[randEdge[1]]
    else:
        superVertex, deletedVertex = g.vertices[randEdge[1]], g.vertices[randEdge[0]]

    ## Connect all edges adjacent to deletedVertex to superVertex
    for num in deletedVertex.list:
        if num == superVertex.list[0]:
            badEdge = [edge for edge in g.edges if (edge[0]==superVertex.list[0] and edge[1]==deletedVertex.list[0])
                                                    or (edge[1]==superVertex.list[0] and edge[0]==deletedVertex.list[0])]
            badEdge[0][2] = False
        else:
            if num not in superVertex.list:
                superVertex.added.append(num)

    deletedVertex.active = False
##    randEdge[2] = False

##    for x in range(0, len(g.vertices)):
##        print(g.vertices[x].list)
##        print(g.vertices[x].added)
##        print(g.vertices[x].active)

##    for edge in g.edges:
##        print(edge)
##    print('')

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
    graph.vertices, graph.edges = [Vertex(False)], []
    
##    for x in range(0, len(newList) + 1):
##        graph.vertices.append([])

    for item in newList:
        numList = list(map(int, item.rstrip('\n').split(' ')))
        graph.vertices.append(Vertex(True))
        graph.vertices[numList[0]].list.append(numList[0])
        
        for edgeNum in numList[1:]:
            if numList[0] < edgeNum:
                edge = [numList[0], edgeNum, True]
            else:
                edge = [edgeNum, numList[0], True]
            
            if not is_in_list(graph.edges, edge):
                graph.edges.append(edge)
            
            graph.vertices[numList[0]].list.append(edgeNum)

    return graph

graph = get_graph_from_file('data/min_cut_1.txt')
random_cut(graph)
for x in range(0, len(graph.vertices)):
    print(graph.vertices[x].list)
    print(graph.vertices[x].added)
    print(graph.vertices[x].active)
for edge in graph.edges:
    print(edge)
print('')

##print(min_cut(graph))
