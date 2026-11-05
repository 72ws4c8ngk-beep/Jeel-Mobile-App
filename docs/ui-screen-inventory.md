# JEEL — 26-screen UI

Design reference: [JEEL — 26 Screens + States](https://www.figma.com/design/q7yH1BK0m47IHYbqiH6w3d/JEEL?node-id=3-233).

This revision contains presentation code and local prototype navigation. Authentication, email, booking, Zoom joining, saving, and request processing are not connected to services. State examples are explicit preview inputs, not simulated network operations.

## Screen inventory

| # | Screen | Composable file |
|---|---|---|
| 1 | Splash Screen | SplashScreen.kt |
| 2 | Welcome | WelcomeScreen.kt |
| 3 | Login | LoginScreen.kt |
| 4 | Sign Up | SignUpScreen.kt |
| 5 | Profile Onboarding | ProfileOnboardingScreen.kt |
| 6 | Forgot Password | ForgotPasswordScreen.kt |
| 7 | Mentee Home | MenteeHomeScreen.kt |
| 8 | Explore Mentors | ExploreScreen.kt |
| 9 | Mentor Filters | FilterScreen.kt |
| 10 | Mentor Details | MentorDetailScreen.kt |
| 11 | Book Session · Reschedule | BookingScreen.kt |
| 12 | Booking Confirmation | BookingConfirmationScreen.kt |
| 13 | My Sessions | MenteeSessionsScreen.kt |
| 14 | Session Details | SessionDetailsScreen.kt |
| 15 | Session Review | SessionReviewScreen.kt |
| 16 | Notifications | NotificationsScreen.kt |
| 17 | My Profile | MyProfileScreen.kt |
| 18 | Edit Mentee Profile | MenteeEditProfileScreen.kt |
| 19 | Edit Mentor Profile | MentorEditProfileScreen.kt |
| 20 | My Progress | ProgressScreen.kt |
| 21 | Mentor Home | MentorHomeScreen.kt |
| 22 | Mentorship Requests | RequestsScreen.kt |
| 23 | Request Details | RequestDetailScreen.kt |
| 24 | Manage Availability | AvailabilityScreen.kt |
| 25 | Mentor Sessions | MentorSessionsScreen.kt |
| 26 | Settings | SettingsScreen.kt |

## Merged and removed screens

- Role Selection is inside Sign Up.
- Check Email, resend enabled, and the countdown appearance are inside Forgot Password; the timer is a static visual example.
- Mentor and Mentee onboarding share ProfileOnboardingScreen and one onboarding route.
- My Profile, Session Details, and Notifications each have one role-aware screen.
- Booking and rescheduling share BookingScreen; rescheduling is a presentation mode.
- Language, notification preferences, and account settings are inside Settings.
- Mentor reviews remain inside Mentor Details and My Profile.
- Find Match is removed; Home links to Explore Mentors.
- Booking and network errors are inline state components, not separate routes.
- Notifications and Settings retain independent routes.

## Android Studio previews

Open ui/preview/JeelScreenPreviews.kt in the app source and switch to Design or Split. JeelSceneProvider supplies 150 named previews: 26 defaults and 124 role/state alternatives. LocalUiScene is a composition-local presentation input with a default of Default; the running app does not show a state-selection control.

## Visual foundation

The existing local photos and brand drawing are retained. Inter and Lora are bundled as local font resources; their OFL licenses are in docs/licenses. Cards, inputs, buttons, and spacing reuse the project's Compose components. Screens scroll on smaller devices.
