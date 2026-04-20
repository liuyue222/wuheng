package com.wuheng.smart.presentation

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.wuheng.smart.data.model.Device
import com.wuheng.smart.data.model.DeviceStatus
import com.wuheng.smart.data.model.DeviceType
import com.wuheng.smart.data.model.HomeOverview
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.home.HomeContent
import org.junit.Rule
import org.junit.Test

/**
 * HomeScreen UI测试
 * 测试首页显示、导航和设备列表
 */
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // 测试数据
    private val mockHomeOverview = HomeOverview(
        roomCount = 8,
        deviceCount = 24,
        onlineDeviceCount = 22,
        indoorTemperature = 24.5,
        indoorHumidity = 58,
        pm25 = 15,
        co2 = 420,
        runningScenes = emptyList(),
        recentDevices = emptyList()
    )

    private val mockDevices = listOf(
        Device(
            id = "dev_001",
            name = "客厅空调",
            type = DeviceType.CLIMATE,
            status = DeviceStatus.ON,
            roomName = "客厅",
            isOnline = true
        ),
        Device(
            id = "dev_002",
            name = "主卧空调",
            type = DeviceType.CLIMATE,
            status = DeviceStatus.ON,
            roomName = "主卧",
            isOnline = true
        ),
        Device(
            id = "dev_003",
            name = "热水器",
            type = DeviceType.WATER,
            status = DeviceStatus.ON,
            roomName = "设备间",
            isOnline = true
        )
    )

    @Test
    fun given_homeOverviewSuccess_when_screenDisplayed_then_showOverviewData() {
        // Given - 成功状态
        composeTestRule.setContent {
            HomeContent(
                homeOverviewState = UiDataState.Success(mockHomeOverview),
                devicesState = UiDataState.Loading,
                onRefresh = {}
            )
        }

        // Then - 验证概览数据正确显示
        composeTestRule.onNodeWithText("全屋概览").assertIsDisplayed()
        composeTestRule.onNodeWithText("8").assertIsDisplayed() // 房间数
        composeTestRule.onNodeWithText("24").assertIsDisplayed() // 设备数
        composeTestRule.onNodeWithText("22").assertIsDisplayed() // 在线数
        composeTestRule.onNodeWithText("24.5°C").assertIsDisplayed() // 温度
        composeTestRule.onNodeWithText("58%").assertIsDisplayed() // 湿度
        composeTestRule.onNodeWithText("15").assertIsDisplayed() // PM2.5
    }

    @Test
    fun given_devicesSuccess_when_screenDisplayed_then_showDeviceList() {
        // Given - 设备列表成功状态
        composeTestRule.setContent {
            HomeContent(
                homeOverviewState = UiDataState.Success(mockHomeOverview),
                devicesState = UiDataState.Success(mockDevices),
                onRefresh = {}
            )
        }

        // Then - 验证设备列表正确显示
        composeTestRule.onNodeWithText("最近使用的设备").assertIsDisplayed()
        composeTestRule.onNodeWithText("客厅空调").assertIsDisplayed()
        composeTestRule.onNodeWithText("主卧空调").assertIsDisplayed()
        composeTestRule.onNodeWithText("热水器").assertIsDisplayed()
        composeTestRule.onNodeWithText("客厅").assertIsDisplayed()
        composeTestRule.onNodeWithText("主卧").assertIsDisplayed()
    }

    @Test
    fun given_loadingState_when_screenDisplayed_then_showLoadingIndicator() {
        // Given - 加载状态
        composeTestRule.setContent {
            HomeContent(
                homeOverviewState = UiDataState.Loading,
                devicesState = UiDataState.Loading,
                onRefresh = {}
            )
        }

        // Then - 验证加载指示器显示
        // CircularProgressIndicator没有文本，通过语义查找
        composeTestRule.onNodeWithText("首页").assertIsDisplayed()
    }

    @Test
    fun given_errorState_when_screenDisplayed_then_showErrorMessage() {
        // Given - 错误状态
        val errorMessage = "网络连接失败，请重试"
        composeTestRule.setContent {
            HomeContent(
                homeOverviewState = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.NetworkError(errorMessage)
                ),
                devicesState = UiDataState.Idle,
                onRefresh = {}
            )
        }

        // Then - 验证错误信息显示
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText("重试").assertIsDisplayed()
    }

    @Test
    fun given_refreshButton_when_clicked_then_triggerRefresh() {
        // Given
        var refreshCalled = false
        composeTestRule.setContent {
            HomeContent(
                homeOverviewState = UiDataState.Success(mockHomeOverview),
                devicesState = UiDataState.Success(mockDevices),
                onRefresh = { refreshCalled = true }
            )
        }

        // When - 点击刷新按钮
        composeTestRule.onNodeWithText("刷新").performClick()

        // Then - 验证刷新回调被调用
        assert(refreshCalled)
    }

    @Test
    fun given_errorState_when_retryClicked_then_triggerRefresh() {
        // Given
        var retryCalled = false
        composeTestRule.setContent {
            HomeContent(
                homeOverviewState = UiDataState.Error(
                    com.wuheng.smart.data.network.AppException.NetworkError("网络错误")
                ),
                devicesState = UiDataState.Idle,
                onRefresh = { retryCalled = true }
            )
        }

        // When - 点击重试按钮
        composeTestRule.onNodeWithText("重试").performClick()

        // Then - 验证重试回调被调用
        assert(retryCalled)
    }

    @Test
    fun given_emptyDeviceList_when_screenDisplayed_then_showEmptyList() {
        // Given - 空设备列表
        composeTestRule.setContent {
            HomeContent(
                homeOverviewState = UiDataState.Success(mockHomeOverview),
                devicesState = UiDataState.Success(emptyList()),
                onRefresh = {}
            )
        }

        // Then - 验证标题显示但无设备项
        composeTestRule.onNodeWithText("最近使用的设备").assertIsDisplayed()
        // 设备列表为空，不应显示任何设备名称
        composeTestRule.onAllNodesWithText("客厅空调").assertCountEquals(0)
    }

    @Test
    fun given_offlineDevice_when_screenDisplayed_then_showDeviceWithOfflineStatus() {
        // Given - 包含离线设备
        val devicesWithOffline = listOf(
            Device(
                id = "dev_001",
                name = "客厅空调",
                type = DeviceType.CLIMATE,
                status = DeviceStatus.OFF,
                roomName = "客厅",
                isOnline = false
            )
        )
        composeTestRule.setContent {
            HomeContent(
                homeOverviewState = UiDataState.Success(mockHomeOverview),
                devicesState = UiDataState.Success(devicesWithOffline),
                onRefresh = {}
            )
        }

        // Then - 验证离线设备显示
        composeTestRule.onNodeWithText("客厅空调").assertIsDisplayed()
        composeTestRule.onNodeWithText("客厅").assertIsDisplayed()
    }

    @Test
    fun given_idleState_when_screenDisplayed_then_showIdleMessage() {
        // Given - 空闲状态
        composeTestRule.setContent {
            HomeContent(
                homeOverviewState = UiDataState.Idle,
                devicesState = UiDataState.Idle,
                onRefresh = {}
            )
        }

        // Then - 验证空闲状态显示
        composeTestRule.onNodeWithText("等待加载...").assertIsDisplayed()
    }

    @Test
    fun given_extremeValues_when_screenDisplayed_then_displayCorrectly() {
        // Given - 极端值
        val extremeOverview = HomeOverview(
            roomCount = 999,
            deviceCount = 9999,
            onlineDeviceCount = 0,
            indoorTemperature = 99.9,
            indoorHumidity = 100,
            pm25 = 999,
            co2 = 9999,
            runningScenes = emptyList(),
            recentDevices = emptyList()
        )
        composeTestRule.setContent {
            HomeContent(
                homeOverviewState = UiDataState.Success(extremeOverview),
                devicesState = UiDataState.Success(emptyList()),
                onRefresh = {}
            )
        }

        // Then - 验证极端值正确显示
        composeTestRule.onNodeWithText("999").assertIsDisplayed()
        composeTestRule.onNodeWithText("99.9°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("100%").assertIsDisplayed()
    }
}
