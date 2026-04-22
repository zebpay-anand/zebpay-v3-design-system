package com.zebpay.ui.v3.components.templates.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zebpay.ui.v3.R
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.card.ZActionInfoCard
import com.zebpay.ui.v3.components.resource.ZIcons


data class TradeButtonAction(
    val label: String,
    val action: TradeBlockerAction,
)

sealed interface TradeBlockerAction {

    data object GoBack : TradeBlockerAction

    data object OpenOrder : TradeBlockerAction

}

sealed interface TradeBlock {
    data object TradeUnavailable:TradeBlock
    data object TradeDailyLimit:TradeBlock
    data object TradeMonthlyLimit:TradeBlock
    data object TradeOrderLimit:TradeBlock
}


private val TradeBlock.icon: ZIcon
    get() = when (this) {
        TradeBlock.TradeUnavailable ->ZIcons.ic_glass_error.asZIcon
        else -> ZIcons.ic_glass_info.asZIcon
    }

private val TradeBlock.headline: String
    @Composable
    get() = when (this) {
        TradeBlock.TradeDailyLimit -> stringResource(id = R.string.blocker_trade_headline_daily_exhausted)
        TradeBlock.TradeMonthlyLimit -> stringResource(id = R.string.blocker_trade_headline_monthly_exhausted)
        TradeBlock.TradeOrderLimit -> stringResource(id = R.string.blocker_trade_headline_order_exhausted)
        TradeBlock.TradeUnavailable -> stringResource(id = R.string.blocker_trade_headline_unavailable)
    }

private val TradeBlock.subtext: String
    @Composable
    get() = when (this) {
        TradeBlock.TradeDailyLimit -> stringResource(id = R.string.blocker_trade_subtext_daily_exhausted)
        TradeBlock.TradeMonthlyLimit -> stringResource(id = R.string.blocker_trade_subtext_monthly_exhausted)
        TradeBlock.TradeOrderLimit -> stringResource(id = R.string.blocker_trade_subtext_order_exhausted)
        TradeBlock.TradeUnavailable -> stringResource(id = R.string.blocker_trade_subtext_unavailable)
    }

private val TradeBlock.primaryAction: TradeButtonAction
@Composable
get() = when (this) {
        TradeBlock.TradeOrderLimit -> TradeButtonAction(
            label = stringResource(R.string.blocker_view_open_order),
            action = TradeBlockerAction.OpenOrder,
        )
        else ->  TradeButtonAction(
            label = stringResource(R.string.blocker_go_back),
            action = TradeBlockerAction.GoBack,
        )
    }


@Composable
fun ZUserTradeBlockerCard(
    modifier: Modifier = Modifier,
    tradeBlock: TradeBlock,
    action: (TradeBlockerAction) -> Unit,
) {
    val primaryAction = tradeBlock.primaryAction
    ZActionInfoCard(
        modifier = modifier,
        heading = tradeBlock.headline,
        subtext = tradeBlock.subtext,
        primaryText = primaryAction.label,
        prefixIcon = tradeBlock.icon,
        onClick = {
            action.invoke(primaryAction.action)
        },
    )
}