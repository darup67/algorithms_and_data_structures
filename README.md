# Computer Science: Algorithms and Data Structures
> Implementation of various algorithms and data structures in various languages, for my own edification and coding practice

### Javascript Library
* **Sorting**
  * Insertion sort
  * Quicksort, optimized\*
  * Mergesort, optimized\*
  * Heapsort
* **Divide and Conquer**
  * Integer multiplication (Karatsuba)
* **Graph**
  * DFS, recursive
  * DFS, iterative
  * Topological sort
  * Strongly connected components (Kosaraju)
* **Greedy**
  * Scheduling by priority

###### \* On random integers (n > 1M), outperforms Javascript library sort()

* **Linked lists**
  * Singly
  * Doubly
* **Heap/Priority Queue**
* **Hash Tables**
  * 2SUM problem (ES6 Sets)


### Python Library
* **Sorting**
  * Mergesort
  * Quicksort
* **Divide and Conquer**
  * Counting inversions
* **Graph**
  * Shortest paths (Dijkstra)
  * Min-cut (Karger)
  * Minimum spanning tree (Prim)
  - Max k-clustering (Kruskal)
- **Heap/Priority Queue**
  - Median maintenance


### Java Library
- **Graph**
  - 8-puzzle (A\*)
  - Clustering, implicit graph
- **Deque** (combined stack/queue via doubly linked list)
- **kd-tree**
- **Union-find**
  - Quick-find
  - Quick-union
  - Weighted quick-union with path compression (O(log\* n))
  - Percolation (see https://en.wikipedia.org/wiki/Percolation_theory)


### Primary Sources
- **_The Algorithm Design Manual_ (Steven S. Skiena)**
  - (+) Great resource and reference manual, great place to start
  - (+) Wide range of problems covered and other sources for further study
  - (-) Many examples use C code instead of pseudocode/explanations
- **Stanford's algorithms specialization on Coursera**
  - (+) Excellent/deep theoretical background
  - (+) Some difficult and non-trivial programming problems (n > 5M)
  - (-) Not as practical (lots of theory, proofs, etc.)
  - (-) Little coverage of data structures
- **Princeton's algorithms course on Coursera**
  - (+) Good mix of algorithms and data structures
  - (+) Practical: shorter videos, more questions, concise examples
  - (-) Less emphasis on proofs


### Languages
Most examples will be in one of the following:
- **Javascript/ES6 (Node.js)**
- **Python 3**
- **Java**


### Quality / Reuse
*Most* of the algorithms have been tested fairly rigorously with very large inputs, and some have been speed-tested and optimized, but I leave it to you to decide their usefulness.  Generally I stopped because I ran out of time, obvious ways to improve, or desire to move on after demonstrating correctness within the required time bounds.  Should you find an obvious inefficiency, data set that produces erroneous/unreasonably slow output, etc., please let me know.
