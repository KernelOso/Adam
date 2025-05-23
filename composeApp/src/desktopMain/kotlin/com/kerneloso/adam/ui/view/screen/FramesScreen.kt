package com.kerneloso.adam.ui.view.screen

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.framesScreen_button_newLens
import adam.composeapp.generated.resources.framesScreen_searchBar_byName
import adam.composeapp.generated.resources.framesScreen_tableHeader_id
import adam.composeapp.generated.resources.framesScreen_tableHeader_name
import adam.composeapp.generated.resources.framesScreen_tableHeader_price
import adam.composeapp.generated.resources.framesScreen_windowTitle
import adam.composeapp.generated.resources.lensScreen_button_newLens
import adam.composeapp.generated.resources.lensScreen_searchBar_byName
import adam.composeapp.generated.resources.lensScreen_tableHeader_id
import adam.composeapp.generated.resources.lensScreen_tableHeader_name
import adam.composeapp.generated.resources.lensScreen_tableHeader_price
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.kerneloso.adam.domain.model.Frame
import com.kerneloso.adam.ui.ComposeWindowHolder
import com.kerneloso.adam.ui.component.formTextField
import com.kerneloso.adam.ui.component.obfuscateView
import com.kerneloso.adam.ui.component.simpleButton
import com.kerneloso.adam.ui.component.tableHeader
import com.kerneloso.adam.ui.component.tableItem
import com.kerneloso.adam.ui.component.viewTemplateWithNavigationBar
import com.kerneloso.adam.ui.view.window.frameFormWindow
import com.kerneloso.adam.ui.viewmodel.FramesViewModel
import com.kerneloso.adam.util.longToPrice
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource

class FramesScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        // Change size and position of the window
        val window = ComposeWindowHolder.window
        val winWidth = 1080
        val windHeight = 800
        LaunchedEffect(Unit) {
            resizeAndCenterWindow(
                window = window,
                with = winWidth,
                height = windHeight
            )
        }
        window.title = stringResource(Res.string.framesScreen_windowTitle)

        //View Model
        val viewModel = remember { (FramesViewModel()) }

        //Search Bar
        var searchText by remember { mutableStateOf("") }

        //List of lens
        var framesList: List<Frame> = viewModel.searchFrames(searchText)

        //View Obfuscated State
        var isViewObfuscated by remember { mutableStateOf(false) }

        //Views triggers :
        // Edit product View
        var openEditFrameForm by remember { mutableStateOf(false) }
        var frameArg by remember { mutableStateOf(Frame()) }

        // New Product View
        var openNewFrameForm by remember { mutableStateOf(false) }

        viewTemplateWithNavigationBar {

            val (container) = createRefs()

            com.kerneloso.adam.ui.component.container(
                shapeRadius = 6.dp,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                borderColor = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxSize(0.96f)
                    .constrainAs(container) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {

                val (crSearchBar, crList, crButton) = createRefs()

                searchBar(
                    searchNameValue = searchText,
                    onSearchNameValueChange = { searchText = it },
                    modifier = Modifier
                        .constrainAs(crSearchBar) {
                            top.linkTo(parent.top)
                            bottom.linkTo(crList.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                )

                //ListContainer
                com.kerneloso.adam.ui.component.container(
                    shapeRadius = 0.dp,
                    borderColor = MaterialTheme.colorScheme.tertiary,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.7f)
                        .constrainAs(crList) {
                            top.linkTo(crSearchBar.bottom)
                            bottom.linkTo(crButton.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Column {
                        tableHeaderRow(
                            modifier = Modifier
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(framesList) { frame ->
                                tableItemRow(
                                    frame = frame,
                                    modifier = Modifier
                                        .clickable {
                                            frameArg = frame
                                            openEditFrameForm = true
                                        }
                                )
                            }
                        }
                    }
                }

                simpleButton(
                    text = stringResource(Res.string.framesScreen_button_newLens),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(60.dp)
                        .onClick {
                            openNewFrameForm = true
                        }
                        .constrainAs(crButton) {
                            top.linkTo(crList.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
            }
        }

        if (isViewObfuscated) {
            obfuscateView()
        }

        //Open Edit Lens Window
        if (openEditFrameForm) {
            isViewObfuscated = true
            frameFormWindow(
                onClose = { openEditFrameForm = false; isViewObfuscated = false },
                viewmodel = viewModel,
                frame = frameArg
            )
        }

        //Open New Lens Window
        if (openNewFrameForm) {
            isViewObfuscated = true
            frameFormWindow(
                onClose = { openNewFrameForm = false; isViewObfuscated = false },
                viewmodel = viewModel,
            )
        }
    }
}

@Composable
private fun searchBar(
    modifier: Modifier = Modifier,

    searchNameValue: String = "",
    onSearchNameValueChange: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth(0.9f)
    ) {
        formTextField(
            value = searchNameValue,
            onValueChange = { onSearchNameValueChange(it) },
            label = stringResource(Res.string.framesScreen_searchBar_byName),
            modifier = Modifier
                .weight(6f)
                .height(60.dp)
        )
    }
}

@Composable
private fun tableHeaderRow(
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()

    ) {
        val headerHeight = 40.dp
        val headerTextStyle = MaterialTheme.typography.titleSmall

        tableHeader(
            textStyle = headerTextStyle,
            text = stringResource(Res.string.framesScreen_tableHeader_id),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = stringResource(Res.string.framesScreen_tableHeader_name),
            modifier = Modifier
                .height(headerHeight)
                .weight(5f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = stringResource(Res.string.framesScreen_tableHeader_price),
            modifier = Modifier
                .height(headerHeight)
                .weight(4f)
        )
    }
}

@Composable
private fun tableItemRow(
    modifier: Modifier = Modifier,
    frame: Frame
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {

        val headerHeight = 40.dp
        val headerTextStyle = MaterialTheme.typography.titleSmall

        //Id
        tableItem(
            textStyle = headerTextStyle,
            text = frame.id.toString(),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        //Name
        tableItem(
            textStyle = headerTextStyle,
            text = frame.name,
            modifier = Modifier
                .height(headerHeight)
                .weight(5f)
        )

        //Price
        tableItem(
            textStyle = headerTextStyle,
            text = "$ ${longToPrice(frame.price)}",
            modifier = Modifier
                .height(headerHeight)
                .weight(4f)
        )
    }

}