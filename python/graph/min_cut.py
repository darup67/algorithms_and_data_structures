def random_cut(g):
    ## Get random edge from list of edges
    randEdge = g.edges[random.randrange(0, len(g.edges))]

    ## Connect all edges adjacent to randEdge[1] to randEdge[0]
##    for x in range(0, len(g.vertices[randEdge[1]])
##        vertexList = g.vertices[randEdge[1]]
##        vertexList[x][0] = randEdge[0]
                   
    for edge in g.vertices[randEdge[1]]:
        edge[0] = randEdge[0]

    g.vertices[randEdge[0]] += [ edge for edge in g.vertices[randEdge[1]] if edge[0] != edge[1] ]
    g.vertices[randEdge[1]] = []

    for x in range(0, len(g.edges))
        

def min_cut(g):
    remainingVertices = len(g.vertices)

    while (remainingVertices > 2):
        random_cut(g)
        remainingVertices -= 1

    

    
