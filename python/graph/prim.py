import pprint
pp = pprint.PrettyPrinter(indent=1)

def prim(graph):
    visited, pathLengths, result = [0], [0], []

    while (len(visited) < len(graph)):
        currPath = {'length': 1000000}

        for vertex in visited:
            for edge in graph[vertex]:
                if ((edge['endVertex'] - 1) not in visited):
                    length = pathLengths[visited.index(vertex)] + edge['weight']
                    
                    if (length < currPath['length']):
                        currPath = {'endVertex': edge['endVertex'] - 1, 'length': length}

        visited.append(currPath['endVertex'])
        pathLengths.append(currPath['length'])

    for vertex in visited:
        result.append({'vertex': vertex + 1, 'pathLength': pathLengths.pop(0)})
    
    return sorted(result, key=lambda k: k['vertex'])

def get_graph_from_file(filename):
    inputList, vertexList, edgeList = list(open(filename)), [None], []

    ## Insert an empty list for all vertices in the input
    ## None used for the 0th index (each numbered vertex resides at its index)
    for i in range(1, int(inputList[0].rstrip('\n').split(' ')[0]) + 1):
        vertexList.append([])

    ## Iterate through all edges, append edge to edgeList and to both vertexes' edge lists
    ## See tuple representations for edges in edgeList vs. edges in vertex lists
    for rawStr in inputList[1:]:
        strToList = rawStr.rstrip('\n').split(' ')
        startVertex, endVertex, weight = map(int, strToList)

        edgeList.append((startVertex, endVertex, weight))
        vertexList[startVertex].append((endVertex,   weight))
        vertexList[endVertex  ].append((startVertex, weight))
    
    return {'vertices': vertexList, 'edges': edgeList}

##graph = [[{'endVertex': 2, 'weight': 1}, {'endVertex': 8, 'weight': 2}], [{'endVertex': 1, 'weight': 1}, {'endVertex': 3, 'weight': 1}], [{'endVertex': 2, 'weight': 1}, {'endVertex': 4, 'weight': 1}], [{'endVertex': 3, 'weight': 1}, {'endVertex': 5, 'weight': 1}], [{'endVertex': 4, 'weight': 1}, {'endVertex': 6, 'weight': 1}], [{'endVertex': 5, 'weight': 1}, {'endVertex': 7, 'weight': 1}], [{'endVertex': 6, 'weight': 1}, {'endVertex': 8, 'weight': 1}], [{'endVertex': 7, 'weight': 1}, {'endVertex': 1, 'weight': 2}]]
graph = get_graph_from_file('data/primData.txt')
pp.pprint(graph)            
