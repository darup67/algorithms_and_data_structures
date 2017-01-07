from functools import reduce
from math      import inf    as Infinity
import pprint
pp = pprint.PrettyPrinter(indent=1)

def prim(graph):
    ## MST:       list of vertices currently in MST (uses None for index(0) for consistency)
    ## MSTedges:  list of edges    currently in MST
    ## MSTfilled: boolean indicating whether a given vertex is in the MST
    gv = graph['vertices']
    MST, MSTedges, MSTfilled = [None, gv[1]], [], [False, True]

    ## Initialize MST and MSTFilled
    for i in range(2, len(gv)):
        MST.append([])
        MSTfilled.append(False)

    ## Iterate until MST contains all vertices (all MSTfilled positions are True)
    while not reduce(lambda x, y: x and y, MSTfilled[1:]):
        cheapestEdge = (None, None, Infinity)  ## cheapestEdge initialized as having infinite cost

        for i in range(1, len(MST)):
            for edge in MST[i]:
                ## If this edge weight < current smallest edge weight AND other vertex is not currently in MST
                if edge[1] < cheapestEdge[2] and not MSTfilled[edge[0]]:
                    cheapestEdge = (i, edge[0], edge[1])

##        print(cheapestEdge)
        MSTedges.append(cheapestEdge)               ## add cheapestEdge to MSTedges list
        MST[cheapestEdge[1]] = gv[cheapestEdge[1]]  ## add end vertex of cheapestEdge to MST
        MSTfilled[cheapestEdge[1]] = True           ## indicate the vertex is now part of the MST
    
    return reduce(lambda x, y: x + y[2], MSTedges, 0);

def get_graph_from_file(filename):
    inputList, vertices, edges = list(open(filename)), [None], []

    ## Insert an empty list for all vertices in the input
    ## None used for the 0th index (each numbered vertex resides at its index)
    for i in range(1, int(inputList[0].rstrip('\n').split(' ')[0]) + 1):
        vertices.append([])

    ## Iterate through all edges, append edge to edges and to both vertexes' edge lists
    ## Note different tuple edge representations in edges and vertices
    for rawStr in inputList[1:]:
        strToList = rawStr.rstrip('\n').split(' ')
        startVertex, endVertex, weight = map(int, strToList)

        edges.append((startVertex, endVertex, weight))
        vertices[startVertex].append((endVertex,   weight))
        vertices[endVertex  ].append((startVertex, weight))
    
    return {'vertices': vertices, 'edges': edges}

##graph = [[{'endVertex': 2, 'weight': 1}, {'endVertex': 8, 'weight': 2}], [{'endVertex': 1, 'weight': 1}, {'endVertex': 3, 'weight': 1}], [{'endVertex': 2, 'weight': 1}, {'endVertex': 4, 'weight': 1}], [{'endVertex': 3, 'weight': 1}, {'endVertex': 5, 'weight': 1}], [{'endVertex': 4, 'weight': 1}, {'endVertex': 6, 'weight': 1}], [{'endVertex': 5, 'weight': 1}, {'endVertex': 7, 'weight': 1}], [{'endVertex': 6, 'weight': 1}, {'endVertex': 8, 'weight': 1}], [{'endVertex': 7, 'weight': 1}, {'endVertex': 1, 'weight': 2}]]
graph = get_graph_from_file('data/primData.txt')
print(prim(graph))
