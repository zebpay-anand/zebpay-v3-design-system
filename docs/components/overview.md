# Components Documentation Overview

This document is the analysis snapshot for the current components in:

- `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms`
- `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules`
- `v3/src/main/kotlin/com/zebpay/ui/v3/components/organisms`

Generated on: **2026-04-22**

## Totals

- Files analyzed: **74**
- Public composable APIs detected: **207**
- Public `Z*` component APIs detected: **90**
- Preview annotations detected (`@Preview`, `@ThemePreviews`, `@DevicePreviews`): **105**
- Files with preview annotations: **65**
- Files without preview annotations: **9**

## Layer Summary

| Layer | Files | Public Composables | `Z*` Components | Preview Annotations |
|---|---:|---:|---:|---:|
| `atoms` | 26 | 72 | 37 | 37 |
| `molecules` | 42 | 125 | 51 | 65 |
| `organisms` | 6 | 10 | 2 | 3 |

## Documentation Entry Points

- [Atoms](./atoms.md)
- [Molecules](./molecules.md)
- [Organisms](./organisms.md)
- [Coverage gaps](./gaps.md)

## Machine-Readable Index

- [component-index.json](./component-index.json)
- [component-summary.json](./component-summary.json)

## Counting Method

1. Unit of inventory is **Kotlin source file** inside the three target layer directories.
2. `Public composable APIs` are detected by scanning `@Composable` followed by non-`private` and non-`internal` `fun` declarations.
3. `Z* Components` are a subset of public composable APIs whose function names match `^Z[A-Za-z0-9_]*$`.
4. Preview coverage is measured by annotation occurrence count (`@Preview`, `@ThemePreviews`, `@DevicePreviews`) per file.

## Important Notes

- This analysis is code-structure driven, not semantic/manual curation.
- Some public composables are helper utilities (`remember*`, extension composables, or preview functions), not end-user UI components.
- `organisms/webview` contains infrastructure and utility files, which lowers `Z*`-component count in organisms.

## Suggested Next Iteration

1. Add per-component API docs (purpose, parameters, usage snippet, accessibility notes).
2. Mark each component with status (`stable`, `experimental`, `deprecated`).
3. Add a CI check to regenerate and validate this index on every PR.
