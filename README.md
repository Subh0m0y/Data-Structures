# Data Structures
[![codebeat badge](https://codebeat.co/badges/d88e2840-4679-46b7-992e-0fd607553c6e)](https://codebeat.co/projects/github-com-subh0m0y-data-structures)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)]()
[![Travis](https://img.shields.io/travis/rust-lang/rust.svg)]()

This project is meant to house implementations of commonly-used Data Structures
and rarely some uncommon ones. The implementations are in Java.

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

## WARNING
Most of these implementations are **NOT Thread Safe** as opposed to the ones in
`java.util` which are either Thread Safe or are aware of Concurrent Modifications.
These implementations are simply oblivious to multi-threaded access and hence,
respond as it were sequential operations.

## Status

Development has just begun and will probably take a few years to be stable.

The list of Completed Data Structures (implemented and tested):

1. FixedStack
2. LinkedStack
3. FixedQueue
4. LinkedQueue
5. BinarySearchTree (basic structure for other trees to build on)

## General Advice

1. **Regarding Fixed v/s Linked:** In general, the Fixed implementations tend to
perform better than the Linked ones. Therefore, it is advised to use the Fixed
version where-ever possible. If the there is no way to pre-determine the size
requirement, Linked implementations must be used.

2. **For more elements than can be held in an array:** Depending on the VM
configuration, the Linked implementations *may* be able to accommodate more
elements than their Fixed counter parts. However, an informed decision must
be made and the performance must be analysed before accepting this as
a final solution. Maybe you should try reducing the overhead of the data-type
or maybe, splitting the data-set into smaller manageable pieces.

3. **Custom ordering can be implemented:** The data-structures which force their
data-types to be `Comparable` are designed to work with `Comparators`, which
can be used to order the data in any desired way. By default, the natural ordering
is applied.