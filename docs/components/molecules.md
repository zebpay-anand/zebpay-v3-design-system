# Molecules Components

Source: v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules

## Snapshot

- Files: **42**
- Public composable APIs detected: **125**
- Public Z* component APIs detected: **51**
- Preview annotations detected: **65**

## Subcategory Breakdown

| Subcategory | Files | Z Components | Preview Annotations |
|---|---:|---:|---:|
| `banner` | 2 | 4 | 3 |
| `bottomsheet` | 4 | 4 | 7 |
| `button` | 5 | 6 | 6 |
| `card` | 3 | 6 | 6 |
| `dropdown` | 1 | 1 | 1 |
| `empty` | 1 | 0 | 1 |
| `field` | 8 | 10 | 11 |
| `header` | 1 | 1 | 4 |
| `keyword` | 1 | 0 | 1 |
| `list` | 4 | 5 | 7 |
| `navigation` | 1 | 2 | 2 |
| `pager` | 1 | 0 | 1 |
| `pills` | 1 | 0 | 1 |
| `refresh` | 1 | 1 | 3 |
| `tab` | 1 | 1 | 1 |
| `table` | 1 | 1 | 1 |
| `tags` | 2 | 3 | 4 |
| `toast` | 2 | 3 | 3 |
| `tooltip` | 2 | 3 | 2 |

## File Inventory

| Component ID | File | Z Components | Public Composables | Preview Annotations |
|---|---|---|---|---:|
| `molecules/banner/ZBannerMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/banner/ZBannerMolecule.kt` | `ZBannerInfoContainer, ZSummaryBanner, ZAppGraphicBanner` | `ZBannerInfoContainer, ZSummaryBanner, ZAppGraphicBanner, PreviewZSummaryBanner, PreviewZAppGraphicBanner` | 2 |
| `molecules/banner/ZInfoBannerMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/banner/ZInfoBannerMolecule.kt` | `ZInfoBanner` | `ZInfoBanner, PreviewZNote` | 1 |
| `molecules/bottomsheet/ZBottomSheetContainerMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/bottomsheet/ZBottomSheetContainerMolecule.kt` | `ZBottomSheetContainer, ZBottomSheetListContainer` | `ZBottomSheetContainer, ZBottomSheetListContainer, PreviewContainer, PreviewGradientContainer` | 4 |
| `molecules/bottomsheet/ZBottomSheetFooterMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/bottomsheet/ZBottomSheetFooterMolecule.kt` | - | `RowScope.ZBottomSheetFooterMolecule` | 1 |
| `molecules/bottomsheet/ZBottomSheetGradientHeaderMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/bottomsheet/ZBottomSheetGradientHeaderMolecule.kt` | `ZBottomSheetGradientHeader` | `ZBottomSheetGradientHeader` | 1 |
| `molecules/bottomsheet/ZBottomSheetHeaderMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/bottomsheet/ZBottomSheetHeaderMolecule.kt` | `ZBottomSheetHeader` | `ZBottomSheetHeader, PreviewZBottomSheetHeader` | 1 |
| `molecules/button/ZPrimaryButtonMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/button/ZPrimaryButtonMolecule.kt` | `ZPrimaryButton` | `getPrimaryColors, ZPrimaryButton, PreviewZPrimaryButton` | 1 |
| `molecules/button/ZSecondaryButtonMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/button/ZSecondaryButtonMolecule.kt` | `ZOutlineButton` | `getOutlineColor, ZOutlineButton, PreviewZOutlineButton` | 1 |
| `molecules/button/ZShineButtonMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/button/ZShineButtonMolecule.kt` | `ZShineButton` | `ZShineButton` | 1 |
| `molecules/button/ZSwipeButtonMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/button/ZSwipeButtonMolecule.kt` | `ZSwipeButton, ZSwipeButtonPreview` | `colors, rememberSwipeToConfirmState, ZSwipeButton, ZSwipeButtonPreview` | 1 |
| `molecules/button/ZTextButtonMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/button/ZTextButtonMolecule.kt` | `ZTextButton` | `ZTextButton, PreviewMediumZTextButton, PreviewBigZTextButton` | 2 |
| `molecules/card/ZBlockerCardMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/card/ZBlockerCardMolecule.kt` | `ZBlockerCard, ZCompleteBlockerCard, ZToastBlockerCard` | `ZBlockerCard, ZCompleteBlockerCard, ZToastBlockerCard` | 2 |
| `molecules/card/ZCardMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/card/ZCardMolecule.kt` | `ZCardRow` | `ZCardRow, PreviewZCardRow` | 1 |
| `molecules/card/ZInfoCardMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/card/ZInfoCardMolecule.kt` | `ZInfoCard, ZActionInfoCard` | `ZInfoCard, ZActionInfoCard` | 3 |
| `molecules/dropdown/ZDropdownMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/dropdown/ZDropdownMolecule.kt` | `ZDropdownItem` | `ZDropdownItem, PreviewZDropdownItem` | 1 |
| `molecules/empty/ZEmptyStateMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/empty/ZEmptyStateMolecule.kt` | - | `EmptyStateView, PreviewEmptyState` | 1 |
| `molecules/field/ZCounterFieldMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/field/ZCounterFieldMolecule.kt` | `ZToggleCounterField, ZCounterField, ZCounterOutlineField` | `ZToggleCounterField, ZCounterField, ZCounterOutlineField` | 1 |
| `molecules/field/ZCountryCodeFieldMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/field/ZCountryCodeFieldMolecule.kt` | `ZCountryCodeField` | `borderColor, backgroundColor, textColor, labelColor, dropdownIconColor, colors, ZCountryCodeField` | 1 |
| `molecules/field/ZFormFieldMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/field/ZFormFieldMolecule.kt` | `ZFormField` | `ZFormField` | 1 |
| `molecules/field/ZInputFieldMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/field/ZInputFieldMolecule.kt` | `ZInputField` | `borderColor, backgroundColor, textColor, labelColor, textTopColor, placeholderColor, leadingTextColor, trailingTextColor, leadingIconColor, trailingIconColor, textBottomColor, ctaTextColor, colors, ZInputField` | 2 |
| `molecules/field/ZOutlineInputFieldMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/field/ZOutlineInputFieldMolecule.kt` | `ZOutlineInputField` | `ZOutlineInputField` | 0 |
| `molecules/field/ZPinFieldMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/field/ZPinFieldMolecule.kt` | `ZPinField` | `borderColor, backgroundColor, textColor, labelTopColor, topIconColor, labelBottomColor, bottomIconColor, ctaTextColor, colors, ZPinField, PreviewZPinField, PreviewZPinFieldError, PreviewZPinFieldDisabled, PreviewZPinFieldMasked` | 4 |
| `molecules/field/ZSearchTextMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/field/ZSearchTextMolecule.kt` | `ZSearchField` | `ZSearchField` | 1 |
| `molecules/field/ZTradeFormFieldMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/field/ZTradeFormFieldMolecule.kt` | `ZTradeFormField` | `ZTradeFormField, PreviewZTradeFromField` | 1 |
| `molecules/header/ZHeaderMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/header/ZHeaderMolecule.kt` | `ZToolbar` | `ZToolbar` | 4 |
| `molecules/keyword/ZkeyboardMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/keyword/ZkeyboardMolecule.kt` | - | `CustomKeyboard` | 1 |
| `molecules/list/ZGridItemMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/list/ZGridItemMolecule.kt` | - | `PreviewZGridColumn` | 1 |
| `molecules/list/ZListContainerMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/list/ZListContainerMolecule.kt` | `ZLazyListContainer, ZColumnListContainer` | `ZLazyListContainer, ZColumnListContainer, PreviewZLazyListContainer, PreviewZColumnListContainer` | 2 |
| `molecules/list/ZListItemMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/list/ZListItemMolecule.kt` | `ZListItemRow, ZListLabelItem` | `ZListItemRow, ZListLabelItem, PreviewZListItem, PreviewSelectionItem, PreviewSubSectionItem` | 3 |
| `molecules/list/ZRowItemContainerMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/list/ZRowItemContainerMolecule.kt` | `ZRowItemContainer` | `ZRowItemContainer, PreviewZRowItemContainer` | 1 |
| `molecules/navigation/ZNavigationMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/navigation/ZNavigationMolecule.kt` | `ZNavigationBar, ZNavigationBarPreview` | `ZNavigationBar, ZNavigationBarPreview, PreviewZNavigationBarWithoutFab` | 2 |
| `molecules/pager/ZHorizontalPagerMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/pager/ZHorizontalPagerMolecule.kt` | - | `PreviewZCircularCardPager` | 1 |
| `molecules/pills/ZPillsMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/pills/ZPillsMolecule.kt` | - | `rememberPillState, PillShowcasePreview` | 1 |
| `molecules/refresh/ZPullToRefreshMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/refresh/ZPullToRefreshMolecule.kt` | `ZPullToRefreshContainer` | `ZPullToRefreshContainer, rememberZPullToRefreshState` | 3 |
| `molecules/tab/ZTabMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/tab/ZTabMolecule.kt` | `ZTabBar` | `rememberZTabState, ZTabBar, PreviewZBarVariant` | 1 |
| `molecules/table/ZTableItemMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/table/ZTableItemMolecule.kt` | `ZTableItemRow` | `ZTableItemRow, PreviewZTableItem` | 1 |
| `molecules/tags/ZPnLTagMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/tags/ZPnLTagMolecule.kt` | - | `PnLTag` | 1 |
| `molecules/tags/ZStatusTagMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/tags/ZStatusTagMolecule.kt` | `ZPnlStatusTag, ZStatusTag, ZStatusLightTag` | `ZPnlStatusTag, ZStatusTag, ZStatusLightTag` | 3 |
| `molecules/toast/ZNotesMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/toast/ZNotesMolecule.kt` | `ZNote` | `ZNote, PreviewZNote` | 1 |
| `molecules/toast/ZToastMolecule` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/toast/ZToastMolecule.kt` | `ZToast, ZTopToast` | `ZToast, ZTopToast, PreviewZToast, PreviewZTopToast` | 2 |
| `molecules/tooltip/ZCustomTooltipMolecules` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/tooltip/ZCustomTooltipMolecules.kt` | `ZCustomTooltipPopup, ZTooltipPopup` | `ZCustomTooltipPopup, ZTooltipPopup` | 1 |
| `molecules/tooltip/ZToolTipMolecules` | `v3/src/main/kotlin/com/zebpay/ui/v3/components/molecules/tooltip/ZToolTipMolecules.kt` | `ZTooltipPopup` | `ZTooltipPopup, TooltipPopup, BubbleBackgroundPreview` | 1 |
