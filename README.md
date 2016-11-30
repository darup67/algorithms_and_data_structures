# Computer Science: Algorithms and Data Structures
> Implementation of common (and uncommon!) algorithms and data structures in various languages, for my own edification and coding practice

This repo contains all my computer science studies, prep, and general algorithmic experiments.  It combines two previous repos, js_algorithms and js_data_structures.  Seeing as how many algorithms need the data structures to function anyway, it made sense to combine them, and add my work in other languages at the same time.

### Javascript Library
- **Sorting**
 - Insertion sort
 - Quicksort, optimized\*
 - Mergesort, optimized\*
 - Heapsort
- **Divide and Conquer**
 - Integer multiplication (Karatsuba)
- **Graph**
 - DFS, recursive
 - DFS, iterative
 - Topological sort
 - SCC (strongly connected components)
 
###### \* On random integers (n > 1M), outperforms Javascript library sort()

- **Linked lists**
 - Singly, optimized
 - Doubly
- **Trees**
 - Heap
 - AVL (coming soon)


### Python Library
- **Sorting**
 - Mergesort
- **Divide and Conquer**
 - Counting inversions


### Sources
Mostly drawn from and/or inspired by:
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
 - (-) Less emphasis on understanding


### Languages
Most examples will be in one of the following:
- **Javascript/ES6 (Node.js)**
- **Python 3**
- **Java**

My "official" training is in web development, so most examples will be in JS due to time constraints.  In my opinion, the biggest challenge of working with many languages is understanding various compiler/interpreter error messages.  Haskell's interpreter seems to give cryptic error messages at first, but after a week or so of practice, I'd take it any day over the C++ compiler for Visual Studio (ask me again in a few months and maybe you'll get a different answer).  As I can solve most bugs easily with a few `console.log()` statements in Node.js, it remains my go-to environment.


### Quality / Reuse
*Most* of the algorithms have been tested fairly rigorously with very large inputs, and some have been speed-tested and optimized, but I leave it to you to decide their usefulness.  Generally I stopped because I ran out of time, obvious ways to improve, or desire to move on after getting the required output with reasonable runtime.  Should you find an obvious inefficiency, data set that produces erroneous/unreasonably slow output, etc., please let me know.
