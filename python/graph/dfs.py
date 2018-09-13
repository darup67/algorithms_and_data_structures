class GraphNode:
    def __init__(self, label, edges=set()):
        self.label = label
        self.edges = edges

def dfs(search_node, visited=set()):
    visited.add(search_node)
    yield search_node.label
    for node in search_node.edges:
        if node not in visited:
            yield from dfs(node, visited)

node1 = GraphNode(1)
node2 = GraphNode(2)
node3 = GraphNode(3)
node4 = GraphNode(4)
node5 = GraphNode(5)

node1.edges = set([node2, node3, node5])
node2.edges = set([node1, node4])
node3.edges = set([node1, node4])
node4.edges = set([node2, node3])
node5.edges = set([node1])

for label in dfs(node1): print(label)