import pprint
pp = pprint.PrettyPrinter(indent=1)

## is vertex1 connected to vertex2?
def is_connected(gv, vertex1, vertex2):

    ## Instantiate visited with False for every vertex position
    visited = [None]
    for i in range(1, len(gv)):
        visited.append(False)

    ## Inner depth-first search
    def DFS(vertex):
        visited[vertex] = True

        for edge in gv[vertex]:
            if not visited[edge[0]]:
                DFS(edge[0])

##    pp.pprint(gv)
    DFS(vertex1)
##    print(vertex1, vertex2, visited[vertex2])
##    print()
    return visited[vertex2]

## Kruskal's MST-based clustering algorithm
def max_spacing_cluster(graph, k):
    gv, ge = graph['vertices'], graph["edges"]
    count, current = len(gv) - 1, 0  ## count == num clusters, current == edge list index

    ## Sort edges by weight
    ge.sort(key=lambda k: k[2])
##    pp.pprint(ge)
##    print()

    ## Iterate until number of clusters == k
    while count > k:
        if not is_connected(gv, ge[current][0], ge[current][1]):  ## If new edge would not form a cycle (endpoints not already connected)

            ## Add edge to both vertices as (endVertex, weight)
            gv[ge[current][0]].append((ge[current][1], ge[current][2]))
            gv[ge[current][1]].append((ge[current][0], ge[current][2]))

            ## Decrement cluster count
            count -= 1

        ## Increment edge list index
        current += 1

    ## Iterate until we find the first disconnected pair of vertices
    while is_connected(gv, ge[current][0], ge[current][1]):
        current += 1

    ## Return the distance between the first disconnected pair of vertices
    return ge[current][2];

def get_graph_from_file(filename):
    inputList, vertices, edges = list(open(filename)), [None], []

    ## Insert an empty list for all vertices in the input
    ## None used for the 0th index (each numbered vertex resides at its index)
    for i in range(1, int(inputList[0].rstrip('\n').split(' ')[0]) + 1):
        vertices.append([])

    ## Iterate through all edges in input, append each edge to list "edges"
    ## Note different tuple edge representations in edges and vertices
    for rawStr in inputList[1:]:
        strToList = rawStr.rstrip('\n').split(' ')
        startVertex, endVertex, weight = map(int, strToList)

        edges.append((startVertex, endVertex, weight))
    
    return {'vertices': vertices, 'edges': edges}

graph = get_graph_from_file('data/clusteringData.txt')
print(max_spacing_cluster(graph, k=4))
