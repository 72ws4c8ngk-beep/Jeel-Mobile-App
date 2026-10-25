# Jeel – System Architecture

## 1. Architecture Overview

Jeel is an Android mentorship application developed using Kotlin, Jetpack Compose, and Material 3.

The current Milestone 2 implementation focuses on UI screens, navigation, and prototype interactions. The proposed architecture will support future integration with application logic and data services.

## 2. Current Implementation

**UI Layer**
- Jetpack Compose screens for authentication, mentees, and mentors.
- Shared Material 3 components and design elements.
- UI state managed using Compose state APIs.

**Navigation**
- Screen transitions currently use custom state-based navigation.
- Navigation Compose and NavHost are proposed for a later development phase.

**Data and Business Logic**
- Sample data is used to demonstrate interface layouts.
- Backend authentication, live session booking, and persistent storage are not currently implemented.

## 3. Proposed Architecture

The future architecture follows the MVVM pattern and separates responsibilities into three layers.

**Presentation Layer**
- Jetpack Compose UI
- ViewModel
- StateFlow
- Navigation Compose

**Domain Layer**
- Application use cases
- Business rules
- Input validation

**Data Layer**
- Repository interfaces and implementations
- Remote data source
- Local data source, if required

## 4. Jetpack Components Plan

| Component | Status |
|---|---|
| Jetpack Compose and Material 3 | Implemented |
| Compose state management | Implemented |
| Navigation Compose | Proposed |
| ViewModel and StateFlow | Proposed |
| Kotlin Coroutines | Limited current use |
| Coil | Optional future integration |
| Hilt | Optional, not selected |
| Retrofit / Room / Firebase | Not yet selected |

## 5. Future Development

Future development may include:
- Structured navigation using NavHost.
- ViewModels for managing screen state.
- Repository-based data access.
- Authentication and persistent user profiles.
- Session booking and management services.
- Integration testing and improved error handling.

## 6. Architecture Diagram

The architecture diagram is documented separately in `docs/architecture.png`.

**Note:** Proposed components are not presented as completed features in Milestone 2.
