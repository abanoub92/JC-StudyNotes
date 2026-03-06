# 📓 JC Study Notes

A modern **Android note-taking application** built with **Jetpack Compose**, following clean architecture principles and leveraging the latest Android development stack.

---

## 📖 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [Navigation](#navigation)
- [Domain Models](#domain-models)
- [Screens & UI Components](#screens--ui-components)
  - [Landing Screen](#landing-screen-landingscreenkt)
  - [Subject Screen](#subject-screen-subjectscreenkt)
  - [Task Screen](#task-screen-taskscreenkt)
  - [Session Screen](#session-screen-sessionscreenkt)
  - [Shared Components](#shared-components)
- [Dialog Components](#dialog-components)
- [Utility Classes](#utility-classes)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Setup](#setup)
- [UI & Theming](#ui--theming)
- [Dependencies](#dependencies)
- [Build Configuration](#build-configuration)
- [Attribution](#attribution)

---

## Overview

**JC Study Notes** is an Android application designed to help students create, manage, and organize their study subjects, tasks, and sessions efficiently. The app is built entirely with **Jetpack Compose** for a modern, declarative UI experience, and uses **Room** for persistent local storage.

- **Package:** `com.abanoub.studynotes`
- **Min SDK:** 26 (Android 8.0 Oreo)
- **Target SDK:** 36
- **Version:** 1.0

---

## ✨ Features

- 📚 Create and manage study **Subjects** with custom gradient colors
- ➕ **Add/Update Subject dialog** with inline form validation and color picker
- 🗑️ **Delete confirmation dialogs** to safely remove subjects or study sessions
- ✅ Track **Tasks** with priorities (Low / Medium / High) and completion status — viewable as Upcoming or Completed lists
- 📋 Dedicated **Subject Screen** with collapsing top bar, goal/progress overview, and per-subject task & session lists
- 📝 **Task Screen** to create or edit tasks — with title/description fields, due date picker, priority selector, and subject linking
- ⏱️ Log and review **Study Sessions** per subject
- ⏲️ Dedicated **Session Screen** with a live countdown timer, subject selector bottom sheet, Start / Cancel / Finish controls, and a session history list
- 📅 Material 3 **Date Picker** dialog in the Task Screen with future-only date validation
- 📊 Dashboard with live counters (subject count, studied hours, goal hours)
- 🗃️ Local data persistence with Room Database
- 🎨 Material 3 design with dynamic color support
- 🌗 Light & Dark theme support
- 🔤 Custom Google Fonts (Ubuntu & Salsa)
- 🧭 Type-safe navigation with **AndroidX Navigation Compose** (`NavHost` + sealed `NavRoutes`)
- 💉 Dependency injection with Dagger Hilt
- ⚡ Edge-to-edge UI experience

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
│   │       │   ├── MainActivity.kt                    # App entry point
│   │       │   ├── navigation/
│   │       │   │   ├── NavRoutes.kt                   # Sealed class defining all 4 navigation routes
│   │       │   │   └── Navigation.kt                  # NavHost composable wiring all screens
│   │       │   ├── domain/
│   │       │   │   ├── DummyData.kt                   # Sample data for previews & testing
│   │       │   │   └── model/
│   │       │   │       ├── Subject.kt                 # Subject domain model
│   │       │   │       ├── Task.kt                    # Task domain model
│   │       │   │       └── Session.kt                 # Study session domain model
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
│   │       │   │   │   └── composables/
│   │       │   │   │       ├── LandingTopBar.kt       # Top app bar
│   │       │   │   │       ├── LandingCounter.kt      # Stats row (subjects/hours)
│   │       │   │   │       ├── LandingSubjectCards.kt # Horizontal subject card list
│   │       │   │   │       ├── SubjectCard.kt         # Single gradient subject card
│   │       │   │   │       └── LandingTaskList.kt     # Landing-specific task list composable
│   │       │   │   ├── subject/
│   │       │   │   │   ├── SubjectScreen.kt           # Per-subject detail screen
│   │       │   │   │   └── composables/
│   │       │   │   │       ├── SubjectTopBar.kt       # Collapsing large top bar with actions
│   │       │   │   │       └── SubjectOverview.kt     # Goal/studied hours + circular progress
│   │       │   │   ├── task/
│   │       │   │   │   ├── TaskScreen.kt              # Create / edit task screen
│   │       │   │   │   └── composables/
│   │       │   │   │       ├── TaskTopBar.kt          # Top bar with checkbox & delete actions
│   │       │   │   │       ├── PriorityButton.kt      # Colored priority selector button
│   │       │   │   │       └── TaskDatePicker.kt      # Material 3 date picker dialog wrapper
│   │       │   │   ├── session/
│   │       │   │   │   ├── SessionScreen.kt           # Active study session screen
│   │       │   │   │   └── composables/
│   │       │   │   │       ├── SessionTopBar.kt       # Top bar with back navigation
│   │       │   │   │       ├── SessionTimer.kt        # Circular countdown timer display
│   │       │   │   │       ├── SessionRelatedToSubject.kt # Subject selector row
│   │       │   │   │       └── SessionButtons.kt      # Start / Cancel / Finish button row
│   │       │   │   └── theme/
│   │       │   │       ├── Color.kt                   # Full Material 3 color tokens
│   │       │   │       ├── Theme.kt                   # App theme definition
│   │       │   │       ├── ThemeData.kt               # Light & dark color schemes
│   │       │   │       └── Type.kt                    # Typography & Google Fonts
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

This project follows the **MVVM (Model-View-ViewModel)** pattern combined with **Clean Architecture** principles:

```
┌─────────────────────────────────────────┐
│             UI Layer (Compose)          │
│   Screens / Composables / ViewModels    │
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

- **UI Layer:** Jetpack Compose screens, state holders (ViewModels), and navigation via **AndroidX Navigation Compose**.
- **Domain Layer:** Use cases that encapsulate business logic, keeping the UI and data layers decoupled. Contains the core data models (`Subject`, `Task`, `Session`).
- **Data Layer:** Room database with DAOs and Repository pattern for data access.

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

### `Subject`
Represents a study subject the user wants to track.

| Field | Type | Description |
|---|---|---|
| `id` | `Int` | Unique identifier |
| `name` | `String` | Subject name (e.g. "Physics") |
| `goalHours` | `Float` | Target study hours for this subject |
| `colors` | `List<Color>` | Gradient colors for the subject card |

> Comes with 5 built-in gradient presets via `Subject.subjectCardColors`.

---

### `Task`
Represents a to-do item linked to a subject.

| Field | Type | Description |
|---|---|---|
| `id` | `Int` | Unique identifier |
| `title` | `String` | Short task title |
| `description` | `String` | Optional detailed description |
| `dueDate` | `Long` | Due date as Unix timestamp |
| `priority` | `Int` | Priority level (0 = Low, 1 = Medium, 2 = High) |
| `relatedToSubject` | `String` | Name of the associated subject |
| `isCompleted` | `Boolean` | Completion status |
| `taskSubjectId` | `Int` | ID of the associated subject |

---

### `Session`
Represents a single study session log.

| Field | Type | Description |
|---|---|---|
| `id` | `Int` | Unique identifier |
| `sessionSubjectId` | `Int` | ID of the subject studied |
| `relatedToSubject` | `String` | Name of the subject studied |
| `date` | `Long` | Session date as Unix timestamp |
| `duration` | `Long` | Duration of the session (in hours) |

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

### `changeMillisToDateString` (extension function)
Located in `util/Common.kt`. Converts a nullable `Long` Unix timestamp (milliseconds) to a human-readable date string formatted as `dd MMM yyyy` (e.g. `06 Mar 2026`).

- If the value is `null`, it defaults to the **current date**.
- Uses `java.time` APIs (requires core library desugaring for API < 26).

```kotlin
// Example usage
datePickerState.selectedDateMillis.changeMillisToDateString() // "06 Mar 2026"
```

---

### `DummyData` (`domain/DummyData.kt`)
Provides pre-populated lists of `Subject`, `Task`, and `Session` objects for use in Compose previews and UI development before a real database is wired up.

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Hedgehog or newer (recommended: latest stable)
- **JDK 17** or newer
- **Android SDK** with API level 26+
- **Kotlin** 2.x support enabled in Android Studio

### Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/JCStudyNotes.git
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

<div align="center">
  Made with ❤️ using Jetpack Compose
</div>
