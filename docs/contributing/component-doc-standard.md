# Component Documentation Standard

Use this template when adding detailed docs for a component.

## Required Fields

1. `Component name`
2. `Layer` (`atom`, `molecule`, `organism`)
3. `Source file path`
4. `Public API signature`
5. `Status` (`stable`, `experimental`, `deprecated`)
6. `Preview availability`

## Recommended Sections

1. Purpose
2. When to use
3. When not to use
4. API parameters table
5. State and behavior notes
6. Theming hooks (`ZTheme`, color/typography/shape dependencies)
7. Accessibility notes (content descriptions, semantics, touch targets)
8. Example usage
9. Known caveats
10. Related components

## Naming Conventions

- Component composables should follow `Z*` naming.
- Preview composables should follow `Preview*` naming.
- Keep one primary component per file when possible.

## Quality Checklist

- Public API is documented.
- At least one preview is present for UI components.
- Accessibility behavior is documented.
- Documentation links back to source file and showcase screen when available.
