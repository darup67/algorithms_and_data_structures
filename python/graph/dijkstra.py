import pprint
pp = pprint.PrettyPrinter(indent=1)

def dijkstra(graph):
    visited, pathLengths, result = [0], [0], []

    while (len(visited) < len(graph)):
        currPath = {'length': 1000000}

        for vertex in visited:
            for edge in graph[vertex]:
                if ((edge['endVertex'] - 1) not in visited):
                    length = pathLengths[visited.index(vertex)] + edge['weight']
                    
                    if (length < currPath['length']):
                        currPath = ({'endVertex': edge['endVertex'] - 1, 'length': length})

        visited.append(currPath['endVertex'])
        pathLengths.append(currPath['length'])

    for vertex in visited:
        result.append({'vertex': vertex + 1, 'pathLength': pathLengths.pop(0)})
    
    return sorted(result, key=lambda k: k['vertex'])

graph = [[{'endVertex': 2, 'weight': 1}, {'endVertex': 8, 'weight': 2}], [{'endVertex': 1, 'weight': 1}, {'endVertex': 3, 'weight': 1}], [{'endVertex': 2, 'weight': 1}, {'endVertex': 4, 'weight': 1}], [{'endVertex': 3, 'weight': 1}, {'endVertex': 5, 'weight': 1}], [{'endVertex': 4, 'weight': 1}, {'endVertex': 6, 'weight': 1}], [{'endVertex': 5, 'weight': 1}, {'endVertex': 7, 'weight': 1}], [{'endVertex': 6, 'weight': 1}, {'endVertex': 8, 'weight': 1}], [{'endVertex': 7, 'weight': 1}, {'endVertex': 1, 'weight': 2}]]
pp.pprint(dijkstra(graph))            
