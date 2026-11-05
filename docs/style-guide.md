# Jeel – UI Style Guide

## 1. Design Overview
Jeel is a mobile mentorship application developed using Kotlin and Jetpack Compose with Material 3 components.

The UI design follows a clean and consistent visual style based on the Figma design references.

## 2. Color Palette

| Color | Hex Code | Usage |
|---|---|---|
| Navy Blue | `#18354A` | Headings, primary buttons, and navigation elements |
| Cream | `#F8F6F2` | Main screen backgrounds |
| Gold | `#A17B58` | Accent elements and highlights |
| Slate | `#52636B` | Secondary text |
| Mist | `#E9EFF2` | Light background elements |
| Edge | `#E4E4E4` | Borders and dividers |
| Green | `#56867B` | Success indicators |
| Rose | `#F8EAE7` | Error-related backgrounds |
| Warm Beige | `#F2E5D8` | Cards and supporting backgrounds |

## 3. Typography
- Use the bundled Inter family for UI text and Lora for editorial headings.
- Apply clear font hierarchy for headings, subtitles, and body text.
- Maintain readable text sizes and appropriate spacing.
- Use Material 3 typography conventions.

## 4. UI Components
The application uses Jetpack Compose and Material 3 components, including:
- Buttons and text fields
- Cards and information containers
- Navigation elements
- Icons and status indicators
- Dialogs and feedback messages

## 5. Layout and Spacing
- Maintain consistent padding and margins.
- Use rounded corners for cards and buttons.
- Group related information clearly.
- Keep layouts consistent with the Figma design references.

## 6. UI States
The design includes relevant interface states such as:
- Default and selected states
- Input validation and error messages
- Empty and network error screens
- Booking confirmation and feedback screens

## 7. Design References
The original interface designs are available in Figma:

https://www.figma.com/design/q7yH1BK0m47IHYbqiH6w3d/JEEL?node-id=3-233

Screen reference images are stored in `docs/wireframes/`.

## 8. Implementation Notes
The current milestone focuses on the UI prototype and screen navigation. Backend authentication, persistent data storage, and live booking operations are not part of the verified implementation.

The color values listed above reflect the documented Compose theme and should be checked against the final Figma specifications.
