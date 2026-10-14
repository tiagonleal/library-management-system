# Library Management System (Java)

An object-oriented library management system written in **Java** for *Object-Oriented Programming* at Instituto Superior Técnico (2025/26), as a group project.

## Highlights
- Domain model for users, creators and works (**books** and **DVDs**), loan requests, returns and late-return fines.
- **Observer pattern** for user notifications (loan requests and availability) on works they're interested in.
- User behaviour classification and suspension rules based on borrowing history.
- Domain-specific checked exceptions; state saved/restored with Java **serialization**; import from text files.
- Separation between the core domain (`bci-core`) and the text UI layer (`bci-app`).

## Build
Requires the course UI library `po-uilib.jar` on the classpath.
```bash
cd bci-core && make && cd ../bci-app && make
java -cp po-uilib.jar:bci-core/bci-core.jar:bci-app/bci-app.jar bci.app.App
```
Acceptance tests (input, import and expected output files) are in `tests/`.
