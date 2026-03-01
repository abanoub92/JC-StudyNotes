# 📓 JC Study Notes

A modern **Android note-taking application** built with **Jetpack Compose**, following clean architecture principles and leveraging the latest Android development stack.

---

## 📖 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Setup](#setup)
- [UI & Theming](#ui--theming)
- [Dependencies](#dependencies)
- [Build Configuration](#build-configuration)

---

## Overview

**JC Study Notes** is an Android application designed to help users create, manage, and organize their study notes efficiently. The app is built entirely with **Jetpack Compose** for a modern, declarative UI experience, and uses **Room** for persistent local storage.

- **Package:** `com.abanoub.studynotes`
- **Min SDK:** 26 (Android 8.0 Oreo)
- **Target SDK:** 36
- **Version:** 1.0

---

## ✨ Features

- 📝 Create and manage study notes
- 🗃️ Local data persistence with Room Database
- 🎨 Material 3 design with dynamic color support
- 🌗 Light & Dark theme support
- 🔤 Custom Google Fonts (Ubuntu & Salsa)
- 🧭 Type-safe navigation with Compose Destinations
- 💉 Dependency injection with Dagger Hilt
- ⚡ Edge-to-edge UI experience

---

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Design System | Material 3 |
| Navigation | Compose Destinations |
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
│   │       │   ├── MainActivity.kt          # App entry point
│   │       │   └── ui/
│   │       │       └── theme/
│   │       │           ├── Color.kt         # Color tokens
│   │       │           ├── Theme.kt         # App theme definition
│   │       │           ├── ThemeData.kt     # Light & dark color schemes
│   │       │           └── Type.kt          # Typography & Google Fonts
│   │       ├── res/                         # Android resources
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts                     # App-level build config
├── gradle/
│   ├── libs.versions.toml                   # Version catalog (all dependencies)
│   └── wrapper/
├── build.gradle.kts                         # Root build config
└── settings.gradle.kts                      # Project settings
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

- **UI Layer:** Jetpack Compose screens, state holders (ViewModels), and navigation via Compose Destinations.
- **Domain Layer:** Use cases that encapsulate business logic, keeping the UI and data layers decoupled.
- **Data Layer:** Room database with DAOs and Repository pattern for data access.

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

### Jetpack Compose
| Library | Version |
|---|---|
| Compose BOM | 2026.02.01 |
| Material 3 | (via BOM) |
| UI Tooling | (via BOM) |

### Navigation
| Library | Version |
|---|---|
| Compose Destinations | 2.3.0 |

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
- **Compose Destinations** — generates type-safe navigation code

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

<div align="center">
  Made with ❤️ using Jetpack Compose
</div>

