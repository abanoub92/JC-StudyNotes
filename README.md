# 📓 JC Study Notes

<div align="center">

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=flat&logo=jetpackcompose&logoColor=white)
![Min SDK](https://img.shields.io/badge/Min%20SDK-26-brightgreen?style=flat)
![Target SDK](https://img.shields.io/badge/Target%20SDK-36-blue?style=flat)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat)

**Last Updated:** March 12, 2026

</div>

A modern **Android note-taking application** built with **Jetpack Compose**, following clean architecture principles and leveraging the latest Android development stack.

---

## 📖 Table of Contents

- [Overview](#overview)
- [What's New in v1.0](#-whats-new-in-v10)
- [Features](#-features)
- [Future Enhancements](#-future-enhancements)
- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [Navigation](#-navigation)
- [Domain Models](#-domain-models)
- [Domain Repositories](#domain-repositories)
- [Data Layer](#data-layer)
  - [AppDatabase](#appdatabase)
  - [DAOs](#daos)
  - [Repository Implementations](#repository-implementations)
  - [ColorListConverter](#colorlistconverter)
- [Dependency Injection](#dependency-injection)
  - [DatabaseModule](#databasemodule)
  - [RepositoryModule](#repositorymodule)
- [ViewModels](#viewmodels)
- [Screens & UI Components](#screens--ui-components)
  - [Landing Screen](#landing-screen-landingscreenkt)
  - [Subject Screen](#subject-screen-subjectscreenkt)
  - [Task Screen](#task-screen-taskscreenkt)
  - [Session Screen](#session-screen-sessionscreenkt)
  - [Shared Components](#shared-components)
- [Dialog Components](#dialog-components)
- [State Management Pattern](#-state-management-pattern)
- [Utility Classes](#-utility-classes)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Setup](#setup)
- [UI & Theming](#-ui--theming)
- [Dependencies](#-dependencies)
- [Build Configuration](#-build-configuration)
- [Development](#-development)
- [FAQ](#-faq)
- [Contributing](#-contributing)
- [License](#-license)
- [Attribution](#-attribution)

---

## Overview

**JC Study Notes** is an Android application designed to help students create, manage, and organize their study subjects, tasks, and sessions efficiently. The app is built entirely with **Jetpack Compose** for a modern, declarative UI experience, uses **Room** for persistent local storage, and implements an **MVI-inspired state management pattern** for predictable and reactive UI updates.

- **Package:** `com.abanoub.studynotes`
- **Min SDK:** 26 (Android 8.0 Oreo)
- **Target SDK:** 36
- **Compile SDK:** 36.1
- **Version:** 1.0
- **Version Code:** 1

### 📊 Project Statistics

| Metric | Count |
|--------|-------|
| 📱 **Screens** | 4 (Landing, Subject, Task, Session) |
| 🎨 **Composable Files** | 30+ |
| 🗄️ **Database Tables** | 3 (Subject, Task, Session) |
| 📦 **Repositories** | 3 (Subject, Task, Session) |
| 🧩 **ViewModels** | 4 |
| 🎯 **Navigation Routes** | 4 |
| 🔧 **DI Modules** | 2 (Database, Repository) |
| 📝 **Lines of Code** | ~2,500+ |

### 🚀 Quick Start

```bash
# Clone the repository
git clone https://github.com/abanoub92/JCStudyNotes.git

# Open in Android Studio
# File → Open → Select JCStudyNotes folder

# Run the app
# Connect device/emulator (API 26+) and click Run ▶
```

**Requirements:** Android Studio 2025.1.1+, JDK 17+, Android SDK API 26+

### 🛠️ Key Technologies

| Technology | Purpose |
|------------|---------|
| 🎨 **Jetpack Compose** | Modern declarative UI toolkit |
| 🏗️ **Clean Architecture** | Separation of concerns (Domain, Data, Presentation) |
| 💉 **Dagger Hilt** | Dependency injection |
| 🗄️ **Room Database** | Local data persistence |
| 🧭 **Navigation Compose** | Type-safe screen navigation |
| 🔄 **Kotlin Flows** | Reactive data streams |
| 🎯 **MVI Pattern** | Predictable state management |
| 🎨 **Material 3** | Google's latest design system |
| ⚡ **KSP** | Fast annotation processing |
| 📅 **Java Time API** | Modern date/time handling (with desugaring) |

---

## 🆕 What's New in v1.0

### Modern Architecture
- ✨ **MVI-Inspired Architecture**: Dedicated `State` and `Event` classes for `LandingScreen`, `SubjectScreen`, and `TaskScreen`
- 🔄 **Unidirectional Data Flow**: Events flow up from UI → ViewModel, State flows down ViewModel → UI
- 🌊 **Combined Flows**: ViewModels use `Flow.combine()` to merge multiple repository streams into a single reactive state
- 🎯 **Better Separation of Concerns**: UI state management is predictable, testable, and maintainable

### Repository Layer
- 🔍 **Enhanced Filtering**: Methods like `getUpcomingTasksForSubject()` and `getCompletedTasksForSubject()` with built-in filtering
- 📊 **Smart Sorting**: Tasks automatically sorted by due date and priority at repository layer
- 🎯 **Flow Optimization**: Session queries use `Flow.take()` to limit results (10 for subject sessions, 5 for recent sessions)
- 📈 **Aggregated Data**: Real-time calculation of total study hours, goal hours, and subject counts

### ViewModel Enhancements
- 💉 **Multi-Repository Injection**: ViewModels inject multiple repositories for comprehensive data access
- 🔄 **Reactive State Updates**: State automatically updates when any underlying data changes
- 📨 **Event-Driven Actions**: User interactions handled through sealed Event classes
- ⏱️ **State Scoping**: `SharingStarted.WhileSubscribed(5000)` for efficient state management

### UI Components
- 🎨 **Modular Composables**: Screen-specific composable functions organized in `composables/` directories
- 📱 **Landing Screen Components**: `SubjectCard`, `LandingTopBar`, `LandingCounter`, `LandingTaskList`, `LandingSubjectCards`
- 📖 **Subject Screen Components**: `SubjectTopBar`, `SubjectOverview` with progress tracking
- 📝 **Task Screen Components**: `TaskTopBar`, `TaskDatePicker`, `PriorityButton`
- ⏲️ **Session Screen Components**: `SessionTopBar`, `SessionTimer`, `SessionButtons`, `SessionRelatedToSubject`

### Utility Enhancements
- ⏱️ **`toHours()` Extension**: Convert milliseconds to hours with proper formatting
- 📬 **`SnackBarEvent`**: Sealed class for standardized ViewModel → UI communication
- 📅 **`changeMillisToDateString()`**: Format epoch milliseconds to readable date strings
- 🎯 **`Priority` Enum**: Type-safe priority levels with associated colors

### Data Layer
- 🗄️ **Room Database**: Local persistence with type converters for complex data
- 🔗 **Repository Pattern**: Clean separation between data sources and business logic
- 🔄 **Flow-Based APIs**: Reactive data streams for real-time UI updates
- 🎨 **ColorListConverter**: Custom type converter for storing color lists in database

---

## ✨ Features

### 📚 Subject Management
- 📝 Create and manage study **Subjects** with custom gradient colors (5 preset gradients)
- ➕ **Add/Update Subject dialog** with inline form validation and color picker
- 🗑️ **Delete confirmation dialogs** to safely remove subjects
- 🎯 Set goal study hours for each subject
- 📊 Track progress with visual circular progress indicators

### ✅ Task Tracking
- ➕ Create tasks with **priorities** (Low / Medium / High) color-coded for easy identification
- 📅 Set **due dates** with Material 3 date picker
- 📋 View tasks as **Upcoming** or **Completed** lists
- ✔️ Toggle task completion with animated checkboxes
- 🔗 Link tasks to specific subjects
- 📝 Add detailed descriptions to tasks
- 🎯 Auto-sorting by due date and priority

### 📖 Subject Details
- 📱 Dedicated **Subject Screen** with collapsing top bar
- 📊 Goal/progress overview with studied hours vs goal hours
- 📋 Per-subject task lists (upcoming and completed)
- ⏱️ Recent study sessions for the subject (last 10)
- ➕ Quick "Add Task" floating action button
- ✏️ Edit subject details and delete subjects

### ⏲️ Study Session Timer
- ⏱️ Dedicated **Session Screen** with live countdown/stopwatch timer
- 🔗 Link sessions to specific subjects via bottom sheet selector
- ▶️ Start / Pause / Stop controls for sessions
- 💾 Save completed sessions to track study time
- 📋 View session history
- 📊 Sessions automatically update subject study hours

### 🎨 UI/UX Excellence
- 🌈 **Material 3 Design** (Material You) with dynamic colors
- 🌓 **Dark/Light theme** support following system preferences
- ✨ Smooth animations and transitions
- 📱 Modern, intuitive interface built entirely with Jetpack Compose
- 🎨 Custom gradient color schemes for subjects
- 🔤 Google Fonts integration (Ubuntu, Salsa)

### 🏗️ Technical Highlights
- 🏛️ **Clean Architecture** with clear separation of concerns
- 🔄 **MVI-inspired State Management** with unidirectional data flow
- 💉 **Dependency Injection** with Dagger Hilt
- 🗄️ **Room Database** for local data persistence
- 📡 **Flow-based reactive programming** for real-time UI updates
- 🧪 Type-safe navigation with sealed class routes
- 📦 Modular code organization with screen-specific composables

---

## 🔮 Future Enhancements

Planned features for upcoming versions:

- [ ] 📊 **Statistics & Analytics** - Detailed study time analytics with charts
- [ ] 🔔 **Task Reminders** - Push notifications for upcoming due dates
- [ ] 📤 **Data Export/Import** - Backup and restore functionality
- [ ] ☁️ **Cloud Sync** - Synchronize data across devices
- [ ] 🎯 **Pomodoro Timer** - Built-in Pomodoro technique support
- [ ] 🏆 **Achievements** - Gamification with badges and milestones
- [ ] 📱 **Widgets** - Home screen widgets for quick access
- [ ] 🌐 **Multi-language Support** - Localization for different languages
- [ ] 📝 **Notes** - Rich text notes for subjects
- [ ] 🔍 **Search** - Search functionality across all content
- [ ] 📈 **Study Streaks** - Track consecutive study days

---

## 📸 Screenshots

> 🎨 **Visual Preview Coming Soon**  
> Screenshots showcasing the Landing Screen, Subject Details, Task Management, and Session Timer will be added here.

The app features:
- **Material 3 Design** with dynamic colors
- **Smooth animations** and transitions
- **Intuitive navigation** between screens
- **Responsive layouts** optimized for various screen sizes

---

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Design System | Material 3 |
| Navigation | AndroidX Navigation Compose |
| Dependency Injection | Dagger Hilt |
| Local Database | Room |
| Architecture | MVVM + Clean Architecture |
| Async | Kotlin Coroutines & Flow |
| Fonts | Google Fonts (via Compose) |
| Build System | Gradle with Version Catalog |

---

## 📁 Project Structure

```
JCStudyNotes/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/abanoub/studynotes/
│   │       │   ├── MainActivity.kt                    # App entry point, sets up NavHost
│   │       │   ├── StudyNotesApp.kt                   # Application class annotated with @HiltAndroidApp
│   │       │   ├── navigation/
│   │       │   │   ├── NavRoutes.kt                   # Sealed class defining all 4 navigation routes
│   │       │   │   └── Navigation.kt                  # NavHost composable wiring all screens
│   │       │   ├── data/
│   │       │   │   ├── local/
│   │       │   │   │   ├── AppDatabase.kt             # Room database definition (v1)
│   │       │   │   │   ├── SubjectDao.kt              # DAO: upsert, delete, query subjects
│   │       │   │   │   ├── TaskDao.kt                 # DAO: upsert, delete, query tasks
│   │       │   │   │   ├── SessionDao.kt              # DAO: insert, delete, query sessions
│   │       │   │   │   └── ColorListConverter.kt      # Room TypeConverter for List<Int> ↔ String
│   │       │   │   └── repository/
│   │       │   │       ├── SubjectRepositoryImpl.kt   # Concrete Subject repository backed by SubjectDao
│   │       │   │       ├── TaskRepositoryImpl.kt      # Concrete Task repository backed by TaskDao
│   │       │   │       └── SessionRepositoryImpl.kt   # Concrete Session repository backed by SessionDao
│   │       │   ├── di/
│   │       │   │   ├── DatabaseModule.kt              # Hilt module providing AppDatabase & DAOs
│   │       │   │   └── RepositoryModule.kt            # Hilt module binding repository interfaces to impls
│   │       │   ├── domain/
│   │       │   │   ├── DummyData.kt                   # Sample data for previews & testing
│   │       │   │   ├── model/
│   │       │   │   │   ├── Subject.kt                 # Subject Room entity + color helpers
│   │       │   │   │   ├── Task.kt                    # Task Room entity
│   │       │   │   │   └── Session.kt                 # Session Room entity
│   │       │   │   └── repository/
│   │       │   │       ├── SubjectRepository.kt       # Repository interface for subjects
│   │       │   │       ├── TaskRepository.kt          # Repository interface for tasks
│   │       │   │       └── SessionRepository.kt       # Repository interface for sessions
│   │       │   ├── screens/
│   │       │   │   ├── components/                    # Shared reusable UI components
│   │       │   │   │   ├── AddSubjectDialog.kt        # Dialog to add or update a subject
│   │       │   │   │   ├── DeleteDialog.kt            # Generic confirmation delete dialog
│   │       │   │   │   ├── CounterCard.kt             # Reusable stat counter card
│   │       │   │   │   ├── TaskCheckbox.kt            # Animated circular task checkbox
│   │       │   │   │   ├── TaskList.kt                # Shared LazyListScope task list extension
│   │       │   │   │   ├── StudySessionList.kt        # Shared LazyListScope session list extension
│   │       │   │   │   └── SubjectListBottomSheet.kt  # Modal bottom sheet for picking a subject
│   │       │   │   ├── landing/
│   │       │   │   │   ├── LandingScreen.kt           # Main dashboard screen
│   │       │   │   │   ├── LandingViewModel.kt        # ViewModel injecting SubjectRepository
│   │       │   │   │   └── composables/
│   │       │   │   │       ├── LandingTopBar.kt
│   │       │   │   │       ├── LandingCounter.kt
│   │       │   │   │       ├── LandingSubjectCards.kt
│   │       │   │   │       ├── SubjectCard.kt
│   │       │   │   │       └── LandingTaskList.kt
│   │       │   │   ├── subject/
│   │       │   │   │   ├── SubjectScreen.kt           # Per-subject detail screen
│   │       │   │   │   ├── SubjectViewModel.kt        # ViewModel injecting SubjectRepository
│   │       │   │   │   └── composables/
│   │       │   │   │       ├── SubjectTopBar.kt
│   │       │   │   │       └── SubjectOverview.kt
│   │       │   │   ├── task/
│   │       │   │   │   ├── TaskScreen.kt              # Create / edit task screen
│   │       │   │   │   ├── TaskViewModel.kt           # ViewModel injecting TaskRepository
│   │       │   │   │   └── composables/
│   │       │   │   │       ├── TaskTopBar.kt
│   │       │   │   │       ├── PriorityButton.kt
│   │       │   │   │       └── TaskDatePicker.kt
│   │       │   │   ├── session/
│   │       │   │   │   ├── SessionScreen.kt           # Active study session screen
│   │       │   │   │   ├── SessionViewModel.kt        # ViewModel injecting SessionRepository
│   │       │   │   │   └── composables/
│   │       │   │   │       ├── SessionTopBar.kt
│   │       │   │   │       ├── SessionTimer.kt
│   │       │   │   │       ├── SessionRelatedToSubject.kt
│   │       │   │   │       └── SessionButtons.kt
│   │       │   │   └── theme/
│   │       │   │       ├── Color.kt
│   │       │   │       ├── Theme.kt
│   │       │   │       ├── ThemeData.kt
│   │       │   │       └── Type.kt
│   │       │   └── util/
│   │       │       └── Common.kt                      # Priority enum, changeMillisToDateString & shared utilities
│   │       ├── res/                                   # Android resources (drawables, etc.)
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts                               # App-level build config
├── gradle/
│   ├── libs.versions.toml                             # Version catalog (all dependencies)
│   └── wrapper/
├── build.gradle.kts                                   # Root build config
└── settings.gradle.kts                                # Project settings
```

---

## 🏛️ Architecture

This project follows the **MVVM (Model-View-ViewModel)** pattern combined with **Clean Architecture** principles and **MVI-inspired State Management**:

```
┌─────────────────────────────────────────┐
│             UI Layer (Compose)          │
│   Screens / Composables / ViewModels    │
│     State Classes / Event Classes       │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│           Domain Layer                  │
│     Use Cases / Business Logic          │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│            Data Layer                   │
│   Room DB / Repositories / DAOs         │
└─────────────────────────────────────────┘
```

- **UI Layer:** Jetpack Compose screens with ViewModels managing UI state through dedicated **State** data classes and handling user interactions via sealed **Event** classes for unidirectional data flow.
- **Domain Layer:** Repository interfaces and domain models (`Subject`, `Task`, `Session`) that define the business logic contracts.
- **Data Layer:** Room database with DAOs and Repository implementations for data persistence and retrieval.

### State Management Pattern

The app uses an **MVI-inspired** (Model-View-Intent) state management approach:

- **State Classes** (`LandingState`, `SubjectState`): Immutable data classes representing the complete UI state
- **Event Classes** (`LandingEvent`, `SubjectEvent`): Sealed classes defining all possible user actions
- **ViewModels**: Transform events into state updates using Kotlin Flows and `combine` operators
- **Unidirectional Data Flow**: Events flow up from UI → ViewModel, State flows down ViewModel → UI

---

## 🧭 Navigation

Navigation is implemented with **AndroidX Navigation Compose** (`androidx.navigation:navigation-compose`). All routes are defined as a sealed class and wired in a single `NavHost` composable.

### `NavRoutes` (sealed class)

| Route Object | Route Pattern | Arguments |
|---|---|---|
| `LandingRoute` | `"landing"` | — |
| `SubjectRoute` | `"subject/{subjectId}"` | `subjectId: Int?` |
| `TaskRoute` | `"task/{taskId}/{subjectId}"` | `taskId: Int?`, `subjectId: Int?` |
| `SessionRoute` | `"session"` | — |

`SubjectRoute` and `TaskRoute` expose a `createRoute(...)` helper function to build the full route string with arguments.

### `NavHost` composable (`Navigation.kt`)

A top-level composable called directly from `MainActivity`. It:
- Creates a `NavController` via `rememberNavController()`
- Sets `LandingRoute` as the start destination
- Wires each screen with the appropriate navigation callbacks:

| Screen | Navigation callbacks |
|---|---|
| `LandingScreen` | `onSubjectCardClicked`, `onTaskCardClicked`, `onStartSessionButtonClicked` |
| `SubjectScreen` | `onBackButtonClicked`, `onAddTaskButtonClicked`, `onTaskCardClicked` |
| `TaskScreen` | `onBackButtonClicked` |
| `SessionScreen` | `onBackButtonClicked` |

---

## 🗂️ Domain Models

All domain models are also **Room `@Entity`** classes — they serve as both domain objects and database table definitions.

### `Subject`
Represents a study subject the user wants to track.

| Field | Type | Description |
|---|---|---|
| `id` | `Int?` | Auto-generated primary key |
| `name` | `String` | Subject name (e.g. "Physics") |
| `goalHours` | `Float` | Target study hours for this subject |
| `colors` | `List<Int>` | Gradient color list stored as ARGB integers |

> Comes with 5 built-in gradient presets via `Subject.subjectCardColors`.  
> Helper methods `toColorIntList()` and `toColorList()` convert between `List<Color>` and `List<Int>` for UI ↔ DB interop.

---

### `Task`
Represents a to-do item linked to a subject.

| Field | Type | Description |
|---|---|---|
| `id` | `Int?` | Auto-generated primary key |
| `title` | `String` | Short task title |
| `description` | `String` | Optional detailed description |
| `dueDate` | `Long` | Due date as Unix timestamp |
| `priority` | `Int` | Priority level (0 = Low, 1 = Medium, 2 = High) |
| `relatedToSubject` | `String` | Name of the associated subject |
| `isCompleted` | `Boolean` | Completion status |
| `taskSubjectId` | `Int` | Foreign key referencing the associated subject |

---

### `Session`
Represents a single study session log.

| Field | Type | Description |
|---|---|---|
| `id` | `Int?` | Auto-generated primary key |
| `sessionSubjectId` | `Int` | Foreign key referencing the subject studied |
| `relatedToSubject` | `String` | Name of the subject studied |
| `date` | `Long` | Session date as Unix timestamp |
| `duration` | `Long` | Duration of the session in seconds |

---

## 🗃️ Domain Repositories

Repository interfaces are defined in the `domain/repository/` package and act as the contract between the domain and data layers.

### `SubjectRepository`

| Method | Return Type | Description |
|---|---|---|
| `upsertSubject(subject)` | `suspend Unit` | Insert or update a subject |
| `getTotalSubjectsCount()` | `Flow<Int>` | Observe total number of subjects |
| `getTotalGoalHours()` | `Flow<Float>` | Observe sum of all subjects' goal hours |
| `getSubjectById(subjectId)` | `suspend Subject?` | Fetch a single subject by ID |
| `deleteSubject(subjectId)` | `suspend Unit` | Delete a subject by ID |
| `getAllSubjects()` | `Flow<List<Subject>>` | Observe all subjects |

---

### `TaskRepository`

| Method | Return Type | Description |
|---|---|---|
| `upsertTask(task)` | `suspend Unit` | Insert or update a task |
| `deleteTask(taskId)` | `suspend Unit` | Delete a task by ID |
| `deleteTasksBySubjectId(subjectId)` | `suspend Unit` | Delete all tasks for a subject |
| `getTaskById(taskId)` | `suspend Task?` | Fetch a single task by ID |
| `getUpcomingTasksForSubject(subjectId)` | `Flow<List<Task>>` | Observe uncompleted tasks for a specific subject |
| `getCompletedTasksForSubject(subjectId)` | `Flow<List<Task>>` | Observe completed tasks for a specific subject |
| `getAllUpcomingTasks()` | `Flow<List<Task>>` | Observe all uncompleted tasks sorted by due date and priority |

---

### `SessionRepository`

| Method | Return Type | Description |
|---|---|---|
| `insertSession(session)` | `suspend Unit` | Insert a new session |
| `deleteSession(session)` | `suspend Unit` | Delete a specific session |
| `getAllSessions()` | `Flow<List<Session>>` | Observe all sessions |
| `getRecentTenSessionsForSubject(subjectId)` | `Flow<List<Session>>` | Observe the 10 most recent sessions for a subject |
| `getRecentFiveSessions()` | `Flow<List<Session>>` | Observe the 5 most recent sessions across all subjects |
| `getTotalSessionsDuration()` | `Flow<Long>` | Observe total studied duration across all sessions |
| `getTotalSessionsDurationForSubject(subjectId)` | `Flow<Long>` | Observe total studied duration for a subject |
| `deleteSessionsBySubjectId(subjectId)` | `Unit` | Delete all sessions for a subject |

---

## 💾 Data Layer

### `AppDatabase`

A Room `RoomDatabase` subclass annotated with `@Database`. It registers all three entities and uses `ColorListConverter` as a `@TypeConverters`.

| Property | Value |
|---|---|
| Entities | `Subject`, `Task`, `Session` |
| Version | 1 |
| Export Schema | `false` |
| Database file name | `study_notes.db` |

### DAOs

#### `SubjectDao`

| Method | Annotation | Description |
|---|---|---|
| `upsertSubject(subject)` | `@Upsert` | Insert or update a subject |
| `getTotalSubjectsCount()` | `@Query` | Returns `Flow<Int>` — live count of all subjects |
| `getTotalGoalHours()` | `@Query` | Returns `Flow<Float>` — live sum of all goal hours |
| `getSubjectById(subjectId)` | `@Query` | Returns `Subject?` by ID |
| `deleteSubject(subjectId)` | `@Query` | Deletes subject by ID |
| `getAllSubjects()` | `@Query` | Returns `Flow<List<Subject>>` |

#### `TaskDao`

| Method | Annotation | Description |
|---|---|---|
| `upsertTask(task)` | `@Upsert` | Insert or update a task |
| `deleteTask(taskId)` | `@Query` | Delete a task by ID |
| `deleteTasksBySubjectId(subjectId)` | `@Query` | Delete all tasks for a subject |
| `getTaskById(taskId)` | `@Query` | Returns `Task?` by ID |
| `getTasksForSubject(subjectId)` | `@Query` | Returns `Flow<List<Task>>` for a subject |
| `getAllTasks()` | `@Query` | Returns `Flow<List<Task>>` |

#### `SessionDao`

| Method | Annotation | Description |
|---|---|---|
| `insertSession(session)` | `@Insert` | Insert a new session |
| `deleteSession(session)` | `@Delete` | Delete a specific session by object |
| `getAllSessions()` | `@Query` | Returns `Flow<List<Session>>` |
| `getRecentSessionsForSubject(subjectId)` | `@Query` | Returns `Flow<List<Session>>` for a subject |
| `getTotalSessionsDuration()` | `@Query` | Returns `Flow<Long>` — total duration sum |
| `getTotalSessionsDurationForSubject(subjectId)` | `@Query` | Returns `Flow<Long>` — duration sum for a subject |
| `deleteSessionsBySubjectId(subjectId)` | `@Query` | Deletes all sessions for a subject |

### Repository Implementations

Each implementation class lives in `data/repository/` and implements its corresponding domain repository interface. They are injected via constructor injection with the appropriate DAO.

| Class | Implements | Injected DAO |
|---|---|---|
| `SubjectRepositoryImpl` | `SubjectRepository` | `SubjectDao` |
| `TaskRepositoryImpl` | `TaskRepository` | `TaskDao` |
| `SessionRepositoryImpl` | `SessionRepository` | `SessionDao` |

#### Advanced Logic

**TaskRepositoryImpl** includes sorting and filtering logic:

- **`getUpcomingTasksForSubject()`**: Filters tasks by `isCompleted = false` for a specific subject
- **`getCompletedTasksForSubject()`**: Filters tasks by `isCompleted = true` and applies sorting
- **`getAllUpcomingTasks()`**: Filters all incomplete tasks and applies sorting
- **Task Sorting**: Uses `compareBy<Task> { it.dueDate }.thenBy { it.priority }` to sort by due date first, then by priority

**SessionRepositoryImpl** includes flow limiting:

- **`getRecentTenSessionsForSubject()`**: Uses `Flow.take(10)` to limit to 10 most recent sessions
- **`getRecentFiveSessions()`**: Uses `Flow.take(5)` to limit to 5 most recent sessions across all subjects

These implementations demonstrate the **Repository Pattern** benefit: business logic (filtering, sorting, limiting) is encapsulated in the data layer, keeping ViewModels clean.

### `ColorListConverter`

A Room `@TypeConverter` class that serializes and deserializes `List<Int>` (ARGB color integers) to and from a comma-separated `String` for storage in SQLite.

| Method | Direction |
|---|---|
| `fromColorList(colorList: List<Int>): String` | Encode for DB storage |
| `toColorList(colorListString: String): List<Int>` | Decode from DB |

---

## 💉 Dependency Injection

Dependency injection is provided by **Dagger Hilt**, installed into the `SingletonComponent` for app-wide singletons.

### `DatabaseModule`

An `@Module` object (`@InstallIn(SingletonComponent::class)`) that provides:

| Provider | Type | Scope |
|---|---|---|
| `provideAppDatabase(application)` | `AppDatabase` | `@Singleton` |
| `provideSubjectDao(appDatabase)` | `SubjectDao` | `@Singleton` |
| `provideTaskDao(appDatabase)` | `TaskDao` | `@Singleton` |
| `provideSessionDao(appDatabase)` | `SessionDao` | `@Singleton` |

Room is configured with `Room.databaseBuilder(...)` using the database name `study_notes.db`.

### `RepositoryModule`

An abstract `@Module` class (`@InstallIn(SingletonComponent::class)`) that uses `@Binds` to wire each repository interface to its concrete implementation:

| Binding | Interface | Implementation |
|---|---|---|
| `bindSubjectRepository` | `SubjectRepository` | `SubjectRepositoryImpl` |
| `bindTaskRepository` | `TaskRepository` | `TaskRepositoryImpl` |
| `bindSessionRepository` | `SessionRepository` | `SessionRepositoryImpl` |

---

## 🧠 ViewModels

Each screen has a dedicated `@HiltViewModel` that receives its repository dependencies via `@Inject` constructor injection.

| ViewModel | Screen | Injected Repositories |
|---|---|---|
| `LandingViewModel` | `LandingScreen` | `SubjectRepository`, `SessionRepository`, `TaskRepository` |
| `SubjectViewModel` | `SubjectScreen` | `SubjectRepository`, `TaskRepository`, `SessionRepository` |
| `TaskViewModel` | `TaskScreen` | `TaskRepository`, `SubjectRepository` |
| `SessionViewModel` | `SessionScreen` | `SessionRepository`, `SubjectRepository` |

ViewModels are obtained in their respective screens using `hiltViewModel()` from `androidx.hilt.navigation.compose`.

### State Management

ViewModels use **combined Kotlin Flows** to create reactive state:
- Multiple repository flows are combined using `combine()` operator
- State updates automatically when any underlying data changes
- `StateFlow` exposes immutable state to the UI
- Event handling is done through dedicated event methods that update state

---

## 🖥️ Screens & UI Components

### Landing Screen (`LandingScreen.kt`)
The main dashboard of the app. Displays a scrollable summary of the user's study activity.

**State managed locally:**
- `isAddSubjectDialogOpen` — controls visibility of the Add Subject dialog
- `isDeleteDialogOpen` — controls visibility of the Delete Session confirmation dialog
- `subjectName`, `goalHours`, `selectedColors` — form state passed to `AddSubjectDialog`

**Sections (top to bottom):**
1. **Top App Bar** — Displays the "Study Notes" title.
2. **Counter Row** — Three `CounterCard`s showing:
   - Total subject count
   - Total studied hours
   - Goal study hours
3. **Subject Cards** — A horizontal `LazyRow` of gradient `SubjectCard`s with an **Add (+)** button that opens `AddSubjectDialog`.
4. **Start Study Session Button** — Full-width CTA button.
5. **Upcoming Tasks List** — Uses the shared `taskList` extension to list pending tasks with checkboxes.
6. **Recent Study Sessions List** — Uses the shared `studySessionList` extension to list past sessions with a delete action that opens `DeleteDialog`.

---

### Subject Screen (`SubjectScreen.kt`)
The per-subject detail screen. Opened when the user taps on a subject card from the dashboard.

**State managed locally:**
- `isEditSubjectDialogOpen` — controls visibility of the Edit Subject dialog
- `isDeleteSubjectDialogOpen` — controls visibility of the Delete Subject confirmation dialog
- `isDeleteDialogOpen` — controls visibility of the Delete Session confirmation dialog
- `subjectName`, `goalHours`, `selectedColors` — form state passed to `AddSubjectDialog` for editing

**Behavior:**
- Uses `exitUntilCollapsedScrollBehavior` to collapse the large top bar on scroll
- `ExtendedFloatingActionButton` expands/collapses dynamically based on scroll position (`isFabExtended`)

**Sections (top to bottom):**
1. **Collapsing Top Bar** (`SubjectTopBar`) — Shows the subject name with back, edit, and delete actions.
2. **Subject Overview** (`subjectOverview`) — Two `CounterCard`s (Goal Hours & Studied Hours) plus a circular progress indicator showing percentage progress.
3. **Upcoming Tasks** — Shared `taskList` for pending tasks.
4. **Completed Tasks** — Shared `taskList` for completed tasks.
5. **Recent Study Sessions** — Shared `studySessionList` with per-session delete action.
6. **FAB** — `ExtendedFloatingActionButton` labelled "Add Task".

---

### Task Screen (`TaskScreen.kt`)
A form screen for creating a new task or editing an existing one. Navigated to from the Subject Screen FAB or by tapping a task in any task list.

**State managed locally:**
- `title` — task title input
- `description` — task description input
- `isDeleteTaskDialogOpen` — controls visibility of the Delete Task confirmation dialog
- `taskTitleError` — derived validation error for the title field

**Validation:**
- Title cannot be blank
- Title must be between 4 and 30 characters
- The **Save** button is disabled while `taskTitleError` is non-null

**Sections (top to bottom):**
1. **Top Bar** (`TaskTopBar`) — Shows "Task" title with a back button; when editing an existing task (`isTaskExist = true`) also shows a `TaskCheckbox` (to toggle completion) and a delete icon.
2. **Title field** — `OutlinedTextField` with inline error supporting text.
3. **Description field** — Multi-line `OutlinedTextField`.
4. **Due Date row** — Displays the selected date alongside a calendar `IconButton` to open a date picker.
5. **Priority selector** — A horizontal row of three `PriorityButton`s (Low / Medium / High); the selected priority is highlighted with a white border.
6. **Related Subject row** — Shows the linked subject name with a dropdown `IconButton` for selection.
7. **Save button** — Full-width `Button` at the bottom; enabled only when the title is valid.

---

### Session Screen (`SessionScreen.kt`)
A dedicated screen for running and logging an active study session. Navigated to via the "Start Study Session" button on the Landing Screen.

**State managed locally:**
- `isSubjectBottomSheetOpen` — controls visibility of the `SubjectListBottomSheet`
- `relatedToSubject` — the name of the subject currently linked to the session
- `isDeleteSessionDialogOpen` — controls visibility of the Delete Session confirmation dialog

**Behavior:**
- Uses `rememberModalBottomSheetState` to drive the subject picker sheet
- Hides the bottom sheet with an animation via `bottomSheetState.hide()` on subject selection

**Sections (top to bottom):**
1. **Top Bar** (`SessionTopBar`) — Shows "Study Session" title with a back navigation button.
2. **Timer** (`sessionTimer`) — A large square `AspectRatio(1f)` area containing a `250dp` circular border and a centered time display (e.g. `00:05:32`).
3. **Related to Subject** (`sessionRelatedToSubject`) — Shows the currently selected subject name with a dropdown arrow `IconButton` that opens the `SubjectListBottomSheet`.
4. **Session Buttons** (`sessionButtons`) — A horizontal row with three equal-weight buttons: **Cancel**, **Start**, and **Finish**.
5. **Session History** — Shared `studySessionList` extension rendering the session history with per-row delete action that opens `DeleteDialog`.

---

### Shared Components

#### `CounterCard`
An `ElevatedCard` that displays a single statistic with a title and a large numeric value.

```
┌──────────────────┐
│   Subject Count  │
│        5         │
└──────────────────┘
```

#### `TaskCheckbox`
A custom animated circular checkbox used in task list items. Shows a checkmark icon with an `AnimatedVisibility` transition when a task is completed. Supports a customizable border color driven by the task's `Priority`.

#### `taskList` (shared `LazyListScope` extension)
Located in `screens/components/TaskList.kt`. Renders a titled, scrollable task list inside any `LazyColumn`. Shows an empty-state illustration when there are no tasks. Used by both `LandingScreen` and `SubjectScreen`.

**Parameters:**

| Parameter | Type | Description |
|---|---|---|
| `title` | `String` | Section heading |
| `taskList` | `List<Task>` | Tasks to display |
| `emptyListText` | `String` | Message shown when list is empty |
| `onTaskClicked` | `(Int?) -> Unit` | Called with the task ID when tapped |
| `onCheckboxClicked` | `(Task) -> Unit` | Called when the checkbox is toggled |

#### `studySessionList` (shared `LazyListScope` extension)
Located in `screens/components/StudySessionList.kt`. Renders a titled, scrollable session list inside any `LazyColumn`. Shows an empty-state illustration when there are no sessions. Used by both `LandingScreen` and `SubjectScreen`.

**Parameters:**

| Parameter | Type | Description |
|---|---|---|
| `title` | `String` | Section heading |
| `sessionList` | `List<Session>` | Sessions to display |
| `emptyListText` | `String` | Message shown when list is empty |
| `onDeleteSession` | `(Session) -> Unit` | Called when the delete icon is tapped |

#### `SubjectListBottomSheet`
A Material 3 `ModalBottomSheet` used to pick a subject from a scrollable list. Used in both `TaskScreen` and `SessionScreen`.

**Features:**
- Animated drag handle with a configurable title above a `HorizontalDivider`
- `LazyColumn` of clickable subject rows
- Shows a prompt message (`"Ready to begin? First, add a subject."`) when the subject list is empty

**Parameters:**

| Parameter | Type | Description |
|---|---|---|
| `sheetState` | `SheetState` | Controls sheet expand/collapse animation |
| `isOpen` | `Boolean` | Controls sheet visibility |
| `subjects` | `List<Subject>` | Subjects to display in the list |
| `bottomSheetTitle` | `String` | Header title (default: `"Related to subject"`) |
| `onSubjectClicked` | `(Subject) -> Unit` | Called when a subject row is tapped |
| `onDismissRequest` | `() -> Unit` | Called when the sheet is dismissed |

---

## 🗨️ Dialog Components

### `AddSubjectDialog`
A Material 3 `AlertDialog` used for creating or updating a study subject.

**Features:**
- **Color picker row** — displays the 5 gradient presets from `Subject.subjectCardColors` as circular swatches; the selected one is highlighted with a black border.
- **Subject Name field** — `OutlinedTextField` with inline validation:
  - Cannot be blank
  - Must be between 2 and 20 characters
- **Goal Study Hours field** — `OutlinedTextField` with numeric keyboard and inline validation:
  - Cannot be blank
  - Must be a valid number between 1 and 1000
- Errors are shown as supporting text and only displayed once the user has started typing.

**Parameters:**

| Parameter | Type | Description |
|---|---|---|
| `isOpen` | `Boolean` | Controls dialog visibility |
| `title` | `String` | Dialog title (default: `"Add/Update Subject"`) |
| `selectedColors` | `List<Color>` | Currently selected gradient |
| `subjectName` | `String` | Current subject name input |
| `goalHours` | `String` | Current goal hours input |
| `onColorChange` | `(List<Color>) -> Unit` | Called when a color swatch is tapped |
| `onSubjectNameChange` | `(String) -> Unit` | Called on name text change |
| `onGoalHoursChange` | `(String) -> Unit` | Called on hours text change |
| `onDismissRequest` | `() -> Unit` | Called when dialog is dismissed |
| `onConfirmButtonClick` | `() -> Unit` | Called when the Save/Confirm button is clicked |

---

### `DeleteDialog`
A generic Material 3 `AlertDialog` for confirming a destructive action (e.g., deleting a study session).

**Features:**
- Configurable `title` and `description` text
- **Delete** confirm button and **Cancel** dismiss button
- Reusable for any delete confirmation across the app

**Parameters:**

| Parameter | Type | Description |
|---|---|---|
| `isOpen` | `Boolean` | Controls dialog visibility |
| `title` | `String` | Dialog title |
| `description` | `String` | Warning message shown to the user |
| `onDismissRequest` | `() -> Unit` | Called when dialog is dismissed |
| `onConfirmButtonClick` | `() -> Unit` | Called when the Delete button is clicked |

---

### Landing Composables

| Composable | Description |
|---|---|
| `LandingTopBar` | Material 3 `TopAppBar` with the app title |
| `LandingCounter` | Row of three `CounterCard`s for key stats |
| `LandingSubjectCards` | Header + horizontal scrolling list of `SubjectCard`s |
| `SubjectCard` | A 150×150dp gradient card showing subject name and a book image |
| `landingTaskList` | `LazyListScope` extension rendering the task section with empty-state illustration |

> **Note:** Study session rendering is handled by the shared `studySessionList` extension from `screens/components/StudySessionList.kt`, which is used directly in `LandingScreen`.

### Subject Composables

| Composable | Description |
|---|---|
| `SubjectTopBar` | Material 3 `LargeTopAppBar` with collapsing scroll behavior; includes back, edit, and delete action buttons |
| `subjectOverview` | `LazyListScope` extension rendering Goal Hours & Studied Hours `CounterCard`s alongside a circular progress indicator |

### Task Composables

| Composable | Description |
|---|---|
| `TaskTopBar` | Material 3 `TopAppBar` showing "Task" title, back navigation, and — when editing — a `TaskCheckbox` toggle and a delete action button |
| `PriorityButton` | A colored, clickable `Box` used as a pill-style priority selector; highlights the selected priority with a rounded white border |
| `TaskDatePicker` | A Material 3 `DatePickerDialog` wrapper with configurable confirm/dismiss button labels; only selectable dates from today onward (`SelectableDates` validation) |

### Session Composables

| Composable | Description |
|---|---|
| `SessionTopBar` | Material 3 `TopAppBar` showing "Study Session" title and a back navigation button |
| `sessionTimer` | `LazyListScope` extension rendering a square `AspectRatio(1f)` area with a `250dp` circular border and a large centered timer text |
| `sessionRelatedToSubject` | `LazyListScope` extension rendering a labelled row showing the currently selected subject with a dropdown `IconButton` to open the subject picker sheet |
| `sessionButtons` | `LazyListScope` extension rendering a horizontal row of three equal-width `Button`s: **Cancel**, **Start**, and **Finish** |

### Screen-Specific Composables

The app organizes composables by screen in dedicated `composables/` subdirectories for better modularity:

#### Landing Screen Composables (`screens/landing/composables/`)
- **`SubjectCard.kt`** — Individual subject card with gradient background and book icon, clickable to navigate to subject details
- **`LandingTopBar.kt`** — Top app bar with app title and navigation controls
- **`LandingCounter.kt`** — Dashboard-style counter cards displaying metrics
- **`LandingTaskList.kt`** — Task list component with completion checkboxes
- **`LandingSubjectCards.kt`** — Grid layout for subject cards with "Add Subject" button

#### Subject Screen Composables (`screens/subject/composables/`)
- **`SubjectTopBar.kt`** — Top bar with back navigation and action buttons (edit, delete)
- **`SubjectOverview.kt`** — Progress visualization with circular indicator, showing studied hours vs goal hours with percentage-based progress

#### Task Screen Composables (`screens/task/composables/`)
- **`TaskTopBar.kt`** — Top bar with back navigation, save button, and delete action
- **`TaskDatePicker.kt`** — Material3 date picker dialog for selecting task due dates
- **`PriorityButton.kt`** — Color-coded priority level selector (Low/Medium/High) with selected state visualization

#### Session Screen Composables (`screens/session/composables/`)
- **`SessionTopBar.kt`** — Top bar with back navigation for session screen
- **`SessionTimer.kt`** — Large visual timer display with countdown/stopwatch functionality
- **`SessionButtons.kt`** — Start/Stop/Pause controls with save and cancel options
- **`SessionRelatedToSubject.kt`** — Subject selection interface showing currently linked subject

---

## 🔄 State Management Pattern

The app follows an **MVI-inspired (Model-View-Intent)** state management pattern for complex screens, providing unidirectional data flow and predictable state updates.

### State Classes

Each screen with complex state has a dedicated data class representing the complete UI state:

#### `LandingState`
```kotlin
data class LandingState(
    val totalSubjectCount: Int = 0,
    val totalStudyHours: Float = 0f,
    val totalGoalStudyHours: Float = 0f,
    val subjects: List<Subject> = emptyList(),
    val subjectName: String = "",
    val goalStudyHours: String = "",
    val subjectCardColors: List<Color> = Subject.subjectCardColors.random(),
    val session: Session? = null
)
```

#### `SubjectState`
```kotlin
data class SubjectState(
    val currentSubjectId: Int? = null,
    val subjectName: String = "",
    val goalStudyHours: String = "",
    val studyHours: Float = 0f,
    val subjectCardColors: List<Color> = Subject.subjectCardColors.random(),
    val recentSession: List<Session> = emptyList(),
    val upcomingTasks: List<Task> = emptyList(),
    val completedTasks: List<Task> = emptyList(),
    val session: Session? = null,
    val progress: Float = 0f
)
```

### Event Classes

User actions are modeled as sealed class hierarchies:

#### `LandingEvent`
```kotlin
sealed class LandingEvent {
    data object SaveSubject : LandingEvent()
    data object DeleteSession : LandingEvent()
    data class OnDeleteSessionButtonClick(val session: Session): LandingEvent()
    data class OnTaskIsCompleteChange(val task: Task): LandingEvent()
    data class OnSubjectCardColorChange(val colors: List<Color>): LandingEvent()
    data class OnSubjectNameChange(val name: String): LandingEvent()
    data class OnGoalStudyHoursChange(val hours: String): LandingEvent()
}
```

#### `SubjectEvent`
```kotlin
sealed class SubjectEvent {
    data object UpdateSubject : SubjectEvent()
    data object DeleteSubject : SubjectEvent()
    data object DeleteSession : SubjectEvent()
    data object UpdateProgress : SubjectEvent()
    data class OnTaskIsCompleteChange(val task: Task) : SubjectEvent()
    data class OnSubjectCardColorChange(val colors: List<Color>) : SubjectEvent()
    data class OnSubjectNameChange(val name: String) : SubjectEvent()
    data class OnGoalStudyHoursChange(val hours: String) : SubjectEvent()
    data class OnDeleteSessionButtonClick(val session: Session) : SubjectEvent()
}
```

### Flow Architecture

ViewModels use Kotlin `Flow.combine()` to merge multiple data sources into a single state stream:

```kotlin
val state = combine(
    _state,
    subjectRepository.getAllSubjects(),
    sessionRepository.getTotalSessionsDuration()
    // ... other flows
) { state, subjects, totalDuration ->
    state.copy(
        subjects = subjects,
        totalStudyHours = totalDuration.toHours()
        // ... other updates
    )
}.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = LandingState()
)
```

This ensures:
- **Reactive updates**: UI automatically reflects database changes
- **Single source of truth**: State is always consistent
- **Lifecycle awareness**: Flows stop when UI is not visible

---

## 🔧 Utility Classes

### `Priority` (enum)
Located in `util/Common.kt`. Maps an integer priority value to a human-readable label and a color.

| Enum | Title | Color | Value |
|---|---|---|---|
| `LOW` | Low | 🟢 Green | 0 |
| `MEDIUM` | Medium | 🟠 Orange | 1 |
| `HIGH` | High | 🔴 Red | 2 |

> Use `Priority.fromInt(value)` to safely convert an `Int` to a `Priority`, defaulting to `MEDIUM` if out of range.

---

### Extension Functions

#### `changeMillisToDateString` (Long?)
Located in `util/Common.kt`. Converts a nullable `Long` Unix timestamp (milliseconds) to a human-readable date string formatted as `dd MMM yyyy` (e.g. `06 Mar 2026`).

- If the value is `null`, it defaults to the **current date**.
- Uses `java.time` APIs (requires core library desugaring for API < 26).

```kotlin
// Example usage
datePickerState.selectedDateMillis.changeMillisToDateString() // "06 Mar 2026"
```

#### `toHours` (Long)
Located in `util/Common.kt`. Converts a `Long` duration in **milliseconds** to **hours** as a `Float`, formatted to 2 decimal places.

```kotlin
// Example usage
totalDurationMillis.toHours() // 2.50
```

---

### `SnackBarEvent` (sealed class)
Located in `util/Common.kt`. Used by ViewModels to communicate with UI for showing snackbars or triggering navigation.

```kotlin
sealed class SnackBarEvent {
    data class ShowSnackBar(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ): SnackBarEvent()
    
    data object NavigateUp: SnackBarEvent()
}
```

ViewModels expose a `SharedFlow<SnackBarEvent>` that the UI collects to show feedback messages or navigate back.

---

### `DummyData` (`domain/DummyData.kt`)
Provides pre-populated lists of `Subject`, `Task`, and `Session` objects for use in Compose previews and UI development before a real database is wired up.

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Narwhal | 2025.1.1 or newer (recommended: latest stable)
- **JDK 17** or newer
- **Android SDK** with API level 26+
- **Kotlin** 2.x support enabled in Android Studio

### Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/abanoub92/JCStudyNotes.git
   cd JCStudyNotes
   ```

2. **Open in Android Studio:**
   - Launch Android Studio
   - Select **Open** and navigate to the cloned folder
   - Wait for Gradle sync to complete

3. **Run the app:**
   - Connect an Android device or start an emulator (API 26+)
   - Click the **Run ▶** button or press `Shift + F10`

> **Note:** Dynamic colors (Material You) require Android 12 (API 31) or above. On older devices, the app falls back to the predefined light/dark color schemes.

---

## 🎨 UI & Theming

The app uses **Material 3** (Material You) design system with full support for:

- **Dynamic Color** — adapts colors to the user's wallpaper on Android 12+
- **Dark / Light mode** — follows system theme
- **Custom Color Schemes** — defined via [Material Theme Builder](https://material-foundation.github.io/material-theme-builder/)

### Typography & Fonts

Custom fonts are loaded using the **Compose Google Fonts** library:

| Font | Usage |
|---|---|
| **Ubuntu** | Body and general text |
| **Salsa** | Display / accent text |

Fonts are fetched at runtime through Google's font provider, so no font files need to be bundled in the APK.

### Subject Gradient Colors

Five gradient presets are defined in `Color.kt` and exposed via `Subject.subjectCardColors`:

| Gradient | Colors |
|---|---|
| `gradient1` | Shades of blue |
| `gradient2` | Shades of purple |
| `gradient3` | Shades of pink/red |
| `gradient4` | Shades of teal/green |
| `gradient5` | Shades of orange |

---

## 📦 Dependencies

All dependencies are managed through the **Gradle Version Catalog** (`gradle/libs.versions.toml`):

### Core
| Library | Version |
|---|---|
| Kotlin | 2.3.10 |
| AndroidX Core KTX | 1.17.0 |
| Activity Compose | 1.12.4 |
| Lifecycle Runtime KTX | 2.10.0 |
| Lifecycle Runtime Compose | 2.10.0 |

### Jetpack Compose
| Library | Version |
|---|---|
| Compose BOM | 2026.02.01 |
| Material 3 | (via BOM) |
| UI Tooling | (via BOM) |

### Navigation
| Library | Version |
|---|---|
| AndroidX Navigation Compose | 2.9.7 |

### Dependency Injection
| Library | Version |
|---|---|
| Dagger Hilt | 2.59.2 |
| AndroidX Hilt Navigation Compose | 1.3.0 |

### Database
| Library | Version |
|---|---|
| Room Runtime | 2.8.4 |
| Room KTX | 2.8.4 |

### Fonts & Utilities
| Library | Version |
|---|---|
| Compose Google Fonts | 1.10.4 |
| Desugar JDK Libs | 2.1.5 |

### Build Plugins
| Plugin | Version |
|---|---|
| Android Gradle Plugin (AGP) | 9.0.1 |
| KSP | 2.3.5 |

---

## ⚙️ Build Configuration

### Java & Kotlin
- **Source/Target Compatibility:** Java 17
- **Core Library Desugaring:** Enabled (for using newer Java APIs on older Android versions)

### Build Types

| Build Type | Minification |
|---|---|
| `debug` | Disabled |
| `release` | Disabled (can be enabled with ProGuard) |

### Code Generation (KSP)
The project uses **Kotlin Symbol Processing (KSP)** for annotation processing, which is faster than KAPT. KSP is used by:
- **Room** — generates database boilerplate
- **Hilt** — generates DI components

---

## 💻 Development

### Project Architecture Principles

The project follows these key architectural principles:

1. **Separation of Concerns**
   - Domain layer: Business logic and models
   - Data layer: Repositories and data sources
   - Presentation layer: ViewModels and UI

2. **Dependency Rule**
   - Domain layer has no dependencies on other layers
   - Data layer depends on domain
   - Presentation layer depends on domain and data

3. **Reactive Programming**
   - Use `Flow` for reactive data streams
   - Collect flows in UI using `collectAsState()`
   - Combine multiple flows in ViewModels

4. **State Management**
   - Unidirectional data flow (MVI-inspired)
   - Events flow up from UI to ViewModel
   - State flows down from ViewModel to UI

### Code Organization

```
📦 com.abanoub.studynotes/
├── 📂 data/             # Data layer
│   ├── local/           # Room database (DAOs, entities, converters)
│   └── repository/      # Repository implementations
├── 📂 di/               # Dependency injection modules
├── 📂 domain/           # Domain layer
│   ├── model/           # Domain models (also Room entities)
│   └── repository/      # Repository interfaces
├── 📂 navigation/       # Navigation routes and graph
├── 📂 screens/          # Presentation layer
│   ├── components/      # Shared UI components
│   ├── landing/         # Landing screen + ViewModel
│   ├── subject/         # Subject screen + ViewModel
│   ├── task/            # Task screen + ViewModel
│   ├── session/         # Session screen + ViewModel
│   └── theme/           # Material 3 theme configuration
└── 📂 util/             # Utility classes and extensions
```

### Best Practices

- ✅ Use `@HiltViewModel` for ViewModels
- ✅ Inject repository interfaces, not implementations
- ✅ Use sealed classes for events and complex UI states
- ✅ Use `LazyListScope` extensions for reusable list items
- ✅ Keep composables small and focused
- ✅ Extract screen-specific composables to `composables/` subdirectories
- ✅ Use `remember` and `rememberSaveable` appropriately
- ✅ Prefer `StateFlow` over `LiveData`
- ✅ Use KSP instead of KAPT for faster builds

### Building the Project

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

### Common Issues & Troubleshooting

#### Gradle Sync Fails
- Ensure you have JDK 17 or newer installed
- Check internet connection (for dependency downloads)
- Try `File → Invalidate Caches and Restart`

#### Build Errors
- Clean and rebuild: `./gradlew clean build`
- Check that AGP and Kotlin versions are compatible
- Ensure KSP plugin version matches Kotlin version

#### Room Database Issues
- If database schema changes, increment version number in `@Database` annotation
- Provide migration strategy or use `.fallbackToDestructiveMigration()`
- Clear app data if testing in development

#### Compose Preview Not Showing
- Ensure preview functions use `@Preview` annotation
- Check that composable has no required parameters (use default values)
- Try `Build → Refresh All Previews`

---

## 🤝 Contributing

This is a personal learning project, but suggestions and feedback are welcome!

### How to Contribute

1. **Fork the repository**
2. **Create a feature branch** (`git checkout -b feature/AmazingFeature`)
3. **Commit your changes** (`git commit -m 'Add some AmazingFeature'`)
4. **Push to the branch** (`git push origin feature/AmazingFeature`)
5. **Open a Pull Request**

### Coding Standards

- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic
- Write composable functions that are small and focused
- Use sealed classes for events and complex state
- Keep ViewModels clean and focused on state management
- Write unit tests for business logic

### Reporting Issues

If you find a bug or have a feature request:

1. Check if the issue already exists
2. Create a new issue with a clear title and description
3. Include steps to reproduce (for bugs)
4. Add relevant labels

---

## ❓ FAQ

### Q: Does this app sync data to the cloud?
**A:** Currently, no. All data is stored locally on your device using Room database. Cloud sync is planned for a future release.

### Q: Can I export my study data?
**A:** Data export functionality is not yet implemented but is planned for a future version.

### Q: Why does the app require Android 8.0 (API 26) or higher?
**A:** The app uses modern Android APIs and Jetpack Compose, which requires a minimum SDK of 26. This also allows us to use desugared Java Time API for better date/time handling.

### Q: Does the app work offline?
**A:** Yes! The app works completely offline. All data is stored locally on your device.

### Q: How do I change the app theme?
**A:** The app automatically follows your system theme (Light/Dark). On Android 12+, it also adapts to your wallpaper colors using Material You dynamic theming.

### Q: Can I backup my data?
**A:** Manual backup is not currently available. The data is stored in the app's private storage. Data export/import functionality is planned for a future release.

### Q: Is the app available on Google Play Store?
**A:** This is currently a personal learning project and is not published on the Play Store. You can build and install it from source.

---

## 📄 License

```
MIT License

Copyright (c) 2026 Abanoub

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 🙏 Attribution

This project is based on the **[StudySmart](https://github.com/kotstantin-dev/StudySmart)** project by **[kotlang-dev](https://github.com/kotlang-dev)**.  
All original concepts, design patterns, and architectural ideas are credited to the original author.  
This repository is a personal study and learning adaptation of that work.

---

## 📞 Contact

- **Developer:** Abanoub
- **GitHub:** [@abanoub92](https://github.com/abanoub92)
- **Project Link:** [JCStudyNotes](https://github.com/abanoub92/JCStudyNotes)

---

## 🌟 Acknowledgments

- **Google** - For Jetpack Compose and Android development tools
- **Material Design Team** - For Material 3 design system
- **kotlang-dev** - For the original StudySmart project inspiration
- **Android Developer Community** - For countless tutorials and resources
- **JetBrains** - For Kotlin programming language

---

<div align="center">

### Made with ❤️ using Jetpack Compose

**[⬆ Back to Top](#-jc-study-notes)**

---

![Kotlin](https://img.shields.io/badge/Made%20with-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Built%20with-Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)

**Last Updated:** March 12, 2026 | **Version:** 1.0

</div>
