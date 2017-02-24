#Data Structures

This project is meant to house implementations of all sorts of
Data Structures. From the common-place to the uncommon and rarely
heard of. The implementations are in Java.

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
that act as light-weight wrappers around arrays and ensure great performance.

##WARNING
Most of these implementations are **NOT Thread Safe** as opposed to the ones in
`java.util` which are either Thread Safe or are aware of Concurrent Modifications.
These implementations are simply oblivious to multi-threaded access and hence,
respond as it were sequential operations.

##Status

Development has just begun and will probably take a few years to be stable.

The list of Completed Data Structures (implemented and tested):

1. FixedStack
2. LinkedStack
3. FixedQueue
4. LinkedQueue