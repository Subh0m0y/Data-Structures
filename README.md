#Data Structures

This project is meant to house implementations of all sorts of
Data Structures. From the common-place to the uncommon and rarely
heard of. The implementations are in Java to make use of the existing
`Collection` and `Iterable` interfaces. Almost everything in this project
is built off these interfaces.

The main aims of this project are:

1. **For educational purposes**: The implementations follow good standards
and although they may not be the only way to do these, they show a *good*
way to do these implementations properly.
2. **For actual use in algorithms**: These Data Structures can be used for
implementation of algorithms in Java. Each and every Data Structure has a
corresponding Test Class (using the TestNG library) and are certified to be free
of common, easy-to-fix and embarrassing bugs. Their *behaviour* is tested to
ensure that no action seems arbitrary (unless that's a requirement).
3. **Provide "Fixed" capacity implementations** of all types of Data Structures
that act as light-weight wrappers around arrays and ensure great performance. Extending
this idea further, there *may* be **unmodifiable** implementations that are just
meant for iteration or fast querying operations and not to addition of more data
than was added the first time and removal.

##WARNING
Most of these implementations are **NOT Thread Safe** as opposed to the ones in
`java.util` which are either Thread Safe or are aware of Concurrent Modifications.
These implementations are simply oblivious to multi-threaded access and hence,
respond as it were sequential operations.

Closely related to this feature is that the Iterators for these Data Structures
are only meant for accessing the elements, and cannot change the contents of data
structure. This allows for greatly simplified code.

These are meant to be used by a Single Thread and for this reason, are designed to
be as fast and as robust as possible.

##Status

Development has just begun and will probably take a few years to be stable.

The list of Completed Data Structures (implemented and tested):

1. FixedStack