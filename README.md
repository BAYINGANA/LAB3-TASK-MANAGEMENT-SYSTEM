# TASK-MANAGEMENT-SYSTEM

## Project Status:

This is a fully functional Maven-based Task Management System in Java.

## What Was Done

### 1. Project Restructuring
- ✅ Moved project to proper Maven structure (`src/main/java`, `src/test/java`)
- ✅ Organized all source files into correct packages
- ✅ Cleaned up old directory structure
- ✅ Added File persistance

### 2. File Organization

**Main Application (21 compiled classes)**
- 1 Entry point (Main.java)
- 9 Model classes (TaskCatalog, ProjectCatalog, UserCatalog, etc.)
- 4 Service classes (ProjectService, TaskService, UserService, ReportService)
- 1 Interface (CompletablesReport)
- 2 Utility classes (ConsoleMenu, ValidationUtils)
- 4 Custom Exception classes

**Test Files**
- 4 Test stubs in proper src/test/java structure

### 3. Maven Configuration
- pom.xml configured for Java 21
- Dependencies: JUnit Jupiter 5.10.0, AssertJ 3.24.2
- Build plugins: Compiler, Surefire, Shade (for executable JAR)
- Standard Maven directory layout

### 4. Error Resolution
- ✅ Fixed all package declaration mismatches
- ✅ Corrected all import statements
- ✅ Removed unused imports
- ✅ Added missing getter methods
- ✅ Zero compilation errors

## Project Structure

```
TASK-MANAGEMENT-SYSTEM/
├── pom.xml
├── README.md
├── src/
│   ├── main/java/
│   │   ├── Main.java
│   │   ├── interfaces/
│   │   ├── models/
│   │   ├── services/
│   │   └── utils/
│   ├── data/
│   │   └── projects_data.json
│   └── test/java/
│       ├── models/
│       └── services/
├── target/
│   └── classes/
└── bin/
```

## How to Use

### Compile with Maven
```bash
# Install Java 21 and set JAVA_HOME, then:
mvn clean compile
mvn package
java -jar target/TASK-MANAGEMENT-SYSTEM-1.0-SNAPSHOT.jar
```

### Compile with javac (No Maven Required)
```bash
javac -d target/classes -sourcepath src/main/java ^
  src/main/java/Main.java ^
  src/main/java/models/*.java ^
  src/main/java/services/*.java ^
  src/main/java/utils/*.java ^
  src/main/java/utils/exceptions/*.java ^
  src/main/java/interfaces/*.java

java -cp target/classes Main
```


## Persistence & Data Saving

### Automatic File Persistence
- All users, projects, and tasks are automatically loaded from `src/data/projects_data.json` on startup.
- All data is automatically saved to the same file on exit.
- The system uses a simple, robust, line-based format for persistence (no external libraries required).
- File operations use Java NIO and functional stream processing for reliability and performance.

### Error Handling & Console Feedback
- If the data file is missing, the system starts with empty data and prints an info message.
- If any line in the file is malformed, it is skipped and a warning is printed.
- On every save or load, a clear `[INFO]` message is printed to the console.
- Any file IO errors are reported with `[ERROR]` messages.

## Features

### User Management
- Create users (admin/regular)
- View, update, delete users
- Activate/deactivate users
- Search users by name

### Project Management
- Create projects (software/hardware)
- Display all projects
- Update project details
- Delete projects

### Task Management
- Create tasks with project assignment
- Assign tasks to users
- Update task status
- Delete tasks

### Reports
- Project completion summaries
- Task completion statistics
- User workload analysis

## Compilation Status

- **Total Classes**: 21 main + 4 test stubs
- **Compilation Errors**: 0
- **Compilation Warnings**: 0
- **Build Status**: ✅ SUCCESS

## Maven Features

The project now includes:

1. **Standard Maven Directory Layout**
   - Source code in `src/main/java`
   - Tests in `src/test/java`
   - Build output in `target`

2. **Proper pom.xml**
   - Java 21 target
   - Test dependencies
   - Build plugins for compilation, testing, and packaging

3. **Executable JAR Creation**
   - Maven Shade plugin configured
   - Creates fat JAR with all dependencies
   - Automatically sets main class

4. **Test Support**
   - JUnit Jupiter framework included
   - Test directory structure ready
   - Surefire plugin for test execution
