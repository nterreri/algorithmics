# algorithmics

This is a collection of solutions to algorithmics exercieses.
The chief purposes were to: 
1. play around with implementing data structures
2. Become more familiar with JUnit for testing

The implementations for various data structures and corresponding behaviour was mostly provided by the repository's owner's algorithmics lecturer at UCL Dr Jens Krinke (http://www0.cs.ucl.ac.uk/staff/J.Krinke/). The challenges were to add or modify behaviour or changing the implementation to be more efficient without altering the behvaiour, therefore only a small part of the code within was authored by the owner of the present repository.

There are multiple instances in the code where the implentation could have been more efficient, or follow good design principles better. For example, there are instances where code used across multiple methods was not extracted, and there is one instance of vicious recursiveness (recursive method calling auxiliary recursive method calling parent recursive method). Again, the classes defined within this repository are not meant to reflect state-of-the-art implementations, but were meant to display an understanding of possible implementations of data structures in Java.

All done in Java (8), the folder tree structure reflects the package organization automatically handled by the Eclipse IDE.

The files under an /src/ folder are the Java source code files while the ones under /bin/ have been compiled for debugging within Eclipse.
