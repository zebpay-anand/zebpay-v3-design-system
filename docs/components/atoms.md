# Atoms Components

Source: v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms

## Snapshot

- Files: **26**
- Public composable APIs detected: **72**
- Public Z* component APIs detected: **37**
- Preview annotations detected: **37**

## Subcategory Breakdown

| Subcategory | Files | Z Components | Preview Annotations |
|---|---:|---:|---:|
| `container` | 1 | 1 | 1 |
| `field` | 1 | 1 | 1 |
| `header` | 2 | 4 | 6 |
| `icon` | 4 | 7 | 8 |
| `label` | 5 | 8 | 7 |
| `loader` | 1 | 2 | 2 |
| `misc` | 4 | 3 | 2 |
| `navigation` | 1 | 1 | 0 |
| `pills` | 1 | 1 | 1 |
| `selections` | 1 | 4 | 3 |
| `seperator` | 1 | 2 | 1 |
| `shimmer` | 3 | 0 | 2 |
| `tab` | 1 | 3 | 3 |

## File Inventory

| Component ID | File | Z Components | Public Composables | Preview Annotations |
|---|---|---|---|---:|
| `atoms/container/ZSlideContainer` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/container/ZSlideContainer.kt` | `ZSlideContainer` | `ZSlideContainer` | 1 |
| `atoms/field/ZDecorationItemAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/field/ZDecorationItemAtom.kt` | `ZFieldDecorationItem` | `ZFieldDecorationItem, PreviewZFieldDecorationItem` | 1 |
| `atoms/header/ZHeaderActionAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/header/ZHeaderActionAtom.kt` | `ZToolbarIconAction, ZToolbarTextAction, ZAppBarSearchBox` | `ZToolbarIconAction, PreviewZActionIcon, PreviewZGradientActionIcon, ZToolbarTextAction, PreviewZTextAction, ZAppBarSearchBox, PreviewSearchAction` | 4 |
| `atoms/header/ZHeaderToggleAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/header/ZHeaderToggleAtom.kt` | `ZToolbarToggleAction` | `ZToolbarToggleAction, PreviewZToggleButton, PreviewZToggleButtonOnGradient` | 2 |
| `atoms/icon/ZBorderedIconButton` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/icon/ZBorderedIconButton.kt` | `ZBorderedIconBox` | `ZBorderedIconBox, PreviewZBorderedIconBox` | 1 |
| `atoms/icon/ZIconAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/icon/ZIconAtom.kt` | `ZIcon, ZGradientIcon, ZImage` | `ZIcon, ZGradientIcon, ZImage` | 3 |
| `atoms/icon/ZIconButton` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/icon/ZIconButton.kt` | `ZIconButton, ZGradientIconButton` | `ZIconButton, ZGradientIconButton, PreviewZButtonIcon` | 1 |
| `atoms/icon/ZIllustrationIcon` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/icon/ZIllustrationIcon.kt` | `ZIllustrationIcon` | `ZIllustrationIcon, PreviewZIllustrationIcon, PreviewZIllustrationIconRegular, PreviewZIllustrationIconLarge` | 3 |
| `atoms/label/ZAmountLabel` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/label/ZAmountLabel.kt` | `ZAmountLabel` | `textToAstrixSize, ZAmountLabel` | 1 |
| `atoms/label/ZCommonLabelAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/label/ZCommonLabelAtom.kt` | `ZLabel, ZCommonLabel, ZCommonGradientLabel, ZConstraintCommonLabel` | `defaultConfig, ZLabel, ZCommonLabel, ZCommonGradientLabel, ZConstraintCommonLabel, PreviewZCommonLabel, PreviewZCommonGradientLabel` | 2 |
| `atoms/label/ZCyclicAnimatedText` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/label/ZCyclicAnimatedText.kt` | - | `CyclicAnimatedText` | 1 |
| `atoms/label/ZIndicatorLabel` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/label/ZIndicatorLabel.kt` | `ZIndicatorLabel, ZAmountIndicatorLabel` | `ZIndicatorLabel, ZAmountIndicatorLabel, PreviewZAmountIndicatorLabel, PreviewZIndicatorLabel` | 2 |
| `atoms/label/ZTextLabelToggle` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/label/ZTextLabelToggle.kt` | `ZTextLabelToggle` | `ZTextLabelToggle` | 1 |
| `atoms/loader/ZLoaderAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/loader/ZLoaderAtom.kt` | `ZCircularLoader, ZHorizontalProgress` | `ZCircularLoader, ZHorizontalProgress` | 2 |
| `atoms/misc/ZIndicatorAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/misc/ZIndicatorAtom.kt` | `ZIndicator` | `ZIndicator` | 0 |
| `atoms/misc/ZPieChartAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/misc/ZPieChartAtom.kt` | `ZAmountValuePieChart` | `ZAmountValuePieChart, PreviewZPieChart` | 1 |
| `atoms/misc/ZProgressStoppageSlider` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/misc/ZProgressStoppageSlider.kt` | `ZProgressStoppageSlider` | `ZProgressStoppageSlider, StoppageSliderPreview` | 1 |
| `atoms/misc/ZSpaces` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/misc/ZSpaces.kt` | - | `BlankWidth, BlankHeight` | 0 |
| `atoms/navigation/ZNavItemAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/navigation/ZNavItemAtom.kt` | `ZNavItem` | `colors, ZNavItem, adaptiveTextSize` | 0 |
| `atoms/pills/ZPillsAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/pills/ZPillsAtom.kt` | `ZPill` | `ZPill` | 1 |
| `atoms/selections/ZSelectionAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/selections/ZSelectionAtom.kt` | `ZRadioButton, ZCheckbox, ZFavouritesIcon, ZNotificationIcon` | `ZRadioButton, PreviewZRadioButton, ZCheckbox, PreviewZCheckbox, ZFavouritesIcon, ZNotificationIcon, PreviewMiscIcon` | 3 |
| `atoms/seperator/ZSepratorAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/seperator/ZSepratorAtom.kt` | `ZHorizontalDivider, ZGradientDivider` | `ZHorizontalDivider, ZGradientDivider, PreviewZDivider` | 1 |
| `atoms/shimmer/Shimmer` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/shimmer/Shimmer.kt` | - | `rememberShimmerState` | 0 |
| `atoms/shimmer/ShineHolder` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/shimmer/ShineHolder.kt` | - | `ShineHolder` | 1 |
| `atoms/shimmer/ZShimmerCardAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/shimmer/ZShimmerCardAtom.kt` | - | `ShimmerCard` | 1 |
| `atoms/tab/ZTabAtom` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/atoms/tab/ZTabAtom.kt` | `ZPrimaryTab, ZSecondaryTab, ZTertiaryTab` | `ZPrimaryTab, ZSecondaryTab, ZTertiaryTab, PreviewZPrimaryTab, PreviewZSecondaryTab, PreviewZTertiaryTab` | 3 |
