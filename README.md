# CIT300 - Data Structure and Graph Performance Analyzer

CIT300 Assignment 2 is a four-member, object-oriented Java console application for exploring data structures, searching algorithms, and their performance. This working project contains only S.M. Insath's implementation; other members' modules are pending.

## Technologies and source structure

Standard Java only, with no third-party dependencies or GUI frameworks. Use JDK 11 or newer (`String.repeat` is used for ASCII charts); the refactored project was verified with JDK 17. Development uses Visual Studio Code and Windows PowerShell.

```text
src/
  insath/
    ArrayOperations.java
    SearchingOperations.java
    PerformanceAnalyzer.java
  integration/
    Main.java
    ConsoleUI.java
README.md
.gitignore
out/                         # Generated during compilation; ignored by Git
```

## S.M. Insath's responsibilities and completed functionality

- Dynamic integer array: insertion, deletion of the first matching value, search, display, resizing, and empty-array handling.
- Linear and binary search: result indices, examined-element counts, and elapsed time in nanoseconds.
- Performance comparison: console tables, Big-O reporting, and ASCII bars showing examined-element counts.
- Main menu, integration foundation, integer input validation, and consistent ASCII console formatting.

Linear search examines elements sequentially and has O(n) worst-case time. Binary search halves the remaining sorted range and has O(log n) worst-case search time. Both the search menu and performance comparison sort a copy before binary search, preserving the original array order. Binary search indices refer to the sorted copy. Its measured search time excludes copying and sorting; preparing the sorted copy adds O(n log n) sorting time. Comparison counts represent examined elements, not every individual Java comparison expression. Single-run nanosecond measurements vary with JVM and system conditions.

## Menu and pending team components

| Option | Component | Responsibility / status |
| --- | --- | --- |
| 1 | Array Operations | S.M. Insath - available |
| 2 | Stack Operations | N.D.F. Hainiya - pending |
| 3 | Queue Operations | N.D.F. Hainiya - pending |
| 4 | Linked List Operations | H.M. Harshad - pending |
| 5 | Searching Operations | S.M. Insath - available |
| 6 | Graph Operations, BFS, DFS | A.L.M. Arshad - pending |
| 7 | Performance Comparison | Search comparison available; graph comparison pending |
| 8 | Display All Results | Displays current array; full integration pending |
| 9 | Exit | Available |

Pending module options display a clear message. No Stack, Queue, Linked List, or Graph implementation is included.

## Compile and run in Windows PowerShell

From the project root:

```powershell
javac -d out (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp out integration.Main
```

## Demonstration sequence

1. Select Array Operations, display the empty array, and insert `30`, `10`, `50`, `20`, `40` (the fifth value exercises resizing).
2. Search for `20`, delete `10`, and display the remaining values.
3. Select Searching Operations and run both searches for `20`. Binary search shows a sorted copy; display the array again to confirm its original order is preserved.
4. Run Performance Comparison for `20` and a missing value, and inspect the table and bars.
5. Enter invalid text at an integer prompt and verify that it prompts again. Visit all pending menu options and exit with option 9.

## Future integration and collaboration

Each responsible member will independently provide and test their own package: `hainiya` (StackOperations and QueueOperations), `harshad` (LinkedListOperations), and `arshad` (GraphOperations, BFS, and DFS). The team leader will review and integrate those modules, connect their menus, extend Display All Results, and add graph performance measurements once Arshad's implementation is available. These future source files have intentionally not been created.

The intended repository is https://github.com/insath07/CIT300-Data-Structure-Graph-Analyzer and Insath's planned feature branch is `feature/insath-array-search-performance`. This working folder currently has no `.git` metadata. Review and transfer the changes into the actual repository before using the commands below. If the feature branch already exists, use `git switch feature/insath-array-search-performance` instead of creating it. Commit with your own configured identity; preserve teammates' files and genuine contribution history.

```powershell
git status
git switch -c feature/insath-array-search-performance
git diff -- src README.md .gitignore
git add -A -- src README.md .gitignore
git diff --cached --check
git diff --cached
git commit -m "Refactor Insath modules into Java packages"
git push -u origin feature/insath-array-search-performance
```

Review the staged diff for unrelated work before committing, then open a genuine pull request for review. No commits, pushes, or remote-history changes are performed automatically.
