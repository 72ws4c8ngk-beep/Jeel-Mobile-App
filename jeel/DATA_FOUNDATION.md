# JEEL data foundation

Room 2.8.4 stores users, mentor profiles, and sessions in `jeel_database`.
`JeelRepository` exposes the DAO's suspend writes/lookups and Flow lists without
depending on Compose or changing the existing prototype screens.

## Use from the next UI/ViewModel integration

```kotlin
val repository = JeelRepository.getRepository(context.applicationContext)
// Inside a coroutine:
val id = repository.insertUser(
    UserEntity(fullName = "Example", email = "example@test.example", role = "Mentee")
).toInt()
val saved = repository.getUserById(id)
// Collect these flows from a lifecycle-aware UI or ViewModel:
val mentors = repository.getAllMentors()
val sessions = repository.getSessionsByMentee(id)
```

Insert the mentor's user before its MentorEntity, and insert both the mentee and
mentor before a SessionEntity. Foreign keys enforce these relationships.
Inserts return generated IDs and reject conflicting primary keys. Updates require
the existing entity ID. Email lookup is exact; email uniqueness and authentication
are not implemented by this foundation. UI state still uses prototype data until
the screen owners wire the repository into their ViewModels.

## Build and verify on Windows

Use an installed JDK 17 for the Gradle process (the existing Java bytecode target
remains 11). For this workstation:

```powershell
$env:JAVA_HOME = 'C:\Users\Asayel\.jdks\temurin-17.0.20.1'
.\gradlew.bat clean :app:assembleDebug :app:assembleDebugAndroidTest --no-configuration-cache
# With a running emulator or connected Android device:
.\gradlew.bat :app:connectedDebugAndroidTest --no-configuration-cache
```

`gradle.properties` sets the Gradle JVM locale to English/US because Room's Kotlin
generator emitted Arabic-Indic numeric literals under this Windows locale. These
were reported as invalid `?` tokens in console output. Do not edit generated
`app/build/generated/ksp` files. The build locale does not change the app locale.

The instrumented tests use a separate on-disk test database, close and reopen it,
and verify saved users, mentors, sessions, updates, Flow queries, Unicode text,
missing IDs, and rejection of a session with missing parent records. They do not
clear the production database. Reports are under
`app/build/reports/androidTests/connected/debug/`.
