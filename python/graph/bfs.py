from collections import deque

class GraphNode:
    def __init__(self, label, edges=set()):
        self.label = label
        self.edges = edges

def bfs(search_node):
    visited = set([search_node])
    queue = deque([search_node])

    while len(queue) > 0:
        curr_node = queue.popleft()
        yield curr_node.label

        for node in curr_node.edges:
            if node not in visited:
                queue.append(node)
                visited.add(node)

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

for label in bfs(node1): print(label)