## Summary

This PR proposes a UI-focused Material 3 Expressive refresh for Aurora Store. It is based on upstream `master` at `e17b1a4a2be9c325dbced6946b5cc91e83c13b02` and is maintained in my public fork: [aryan-j/AuroraStore](https://github.com/aryan-j/AuroraStore/tree/codex/material-3-expressive-ui-proposal).

The goal is to show the redesign on a real device and invite maintainer feedback on whether this direction belongs in Aurora Store, should be split into smaller changes, or should remain a separately maintained variant.

## Before and after

Captured from Aurora Store 4.8.4 and the UI proposal on the same Nothing Phone (2). Catalog contents can change between sessions.

### For You

![Stock and Material 3 Expressive For You comparison](https://raw.githubusercontent.com/aryan-j/AuroraStore/codex/aurora-ui-showcase/showcase/comparisons/comparison-for-you.png)

### Top Charts

![Stock and Material 3 Expressive Top Charts comparison](https://raw.githubusercontent.com/aryan-j/AuroraStore/codex/aurora-ui-showcase/showcase/comparisons/comparison-top-charts.png)

### Categories

![Stock and Material 3 Expressive Categories comparison](https://raw.githubusercontent.com/aryan-j/AuroraStore/codex/aurora-ui-showcase/showcase/comparisons/comparison-categories.png)

### App details

![Stock and Material 3 Expressive app details comparison](https://raw.githubusercontent.com/aryan-j/AuroraStore/codex/aurora-ui-showcase/showcase/comparisons/comparison-app-details.png)

### Search

![Material 3 full-screen search and list results](https://raw.githubusercontent.com/aryan-j/AuroraStore/codex/aurora-ui-showcase/showcase/comparisons/feature-search.png)

## What changed

- Reworked the Apps and Games landing pages, keeping For You, Top Charts, Categories, and chart filters accessible from the same page.
- Added a floating bottom navigation and integrated full-screen search, with Material 3 list rows for suggestions and results.
- Rebuilt category and chart rows around Material 3 list components, with consistent spacing, rounded outer corners, and contained icons.
- Refreshed app details with a screenshot carousel, compact metric pills including version, clearer section portals, and an expandable featured review.
- Updated menus, icon buttons, connected actions, navigation, progress feedback, spacing, and motion to use Material 3 / Expressive patterns.

## Behavior and minimum Android version

The changes are concentrated in Compose presentation. Store repositories, network/data flows, update workers, downloads, installation, and the upstream startup/page-retention policy are left intact. The branch does include a small cancellation improvement for stale typed search suggestions, a preferred display refresh-rate request, and a baseline profile for the redesigned UI; these are disclosed in the patch notes.

The initial preview set `minSdk` to 26. That was higher than needed. The current `androidx.compose.material3:material3:1.5.0-alpha29` artifact declares API 24, so this proposal now sets `minSdk` to 24. See the [Material 3 release notes](https://developer.android.com/jetpack/androidx/releases/compose-material3) and [AndroidX minimum SDK guidance](https://developer.android.com/jetpack/androidx/versions). No single animation change is what forces API 26, and removing an animation alone would not restore the upstream API 23 floor. Supporting API 23 while keeping the current Compose stack would require replacing or pinning the AndroidX Compose artifacts that declare API 24, then porting any Expressive APIs that differ in the compatible versions. I would appreciate guidance on whether API 24 is acceptable here or whether API 23 support should be a hard requirement.

## Branding, provenance, and AI assistance

This proposal branch preserves Aurora Store's current app name and launcher artwork. It is an independent fork and is not affiliated with or endorsed by Aurora OSS. The GPL-3.0 license permits source modification under its terms but does not itself grant trademark rights. I am not presenting the preview as an official build or promoting an APK for general installation while asking for maintainer guidance on branding and distribution.

I used Codex AI assistance while implementing and documenting this contribution, as requested by the upstream project README. I reviewed the changes and can explain or revise them. The UI was installed and inspected on a Nothing Phone (2); the API 24 adjustment and branding-neutral proposal branch were prepared after the visual capture, so I am not claiming a separate device run of that exact branch.

## Maintainer feedback

Would you be open to this UI direction? If so, would you prefer the work split into smaller PRs? If an independent variant is a better fit, I can maintain it under distinct branding and follow your guidance on attribution and naming.
