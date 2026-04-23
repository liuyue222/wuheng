package com.wuheng.smart.integration

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wuheng.smart.data.model.FloorInfo
import com.wuheng.smart.data.model.RoomInfo
import com.wuheng.smart.presentation.base.UiDataState
import com.wuheng.smart.presentation.floorzone.FloorZoneContent
import com.wuheng.smart.presentation.theme.WuHengTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 楼层区域页面切换流程集成测试
 */
@RunWith(AndroidJUnit4::class)
class FloorZoneIntegrationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * 测试楼层区域页面楼层切换流程
     */
    @Test
    fun test_floorZoneFloorSwitching() {
        var selectedFloorId: String? = null
        var selectedRoomId: String? = null

        val floors = listOf(
            FloorInfo(1, "FL001", "B1地下室", -1, "100", 3),
            FloorInfo(2, "FL002", "1F一层", 1, "120", 4),
            FloorInfo(3, "FL003", "2F二层", 2, "100", 3)
        )

        val rooms = listOf(
            RoomInfo(1, "RM001", "客厅", "living", "40", 2),
            RoomInfo(2, "RM002", "主卧", "bedroom", "25", 1),
            RoomInfo(3, "RM003", "儿童房", "bedroom", "20", 1),
            RoomInfo(4, "RM004", "卫生间", "bathroom", "8", 1)
        )

        composeTestRule.setContent {
            WuHengTheme {
                FloorZoneContent(
                    floorsState = UiDataState.Success(floors),
                    roomsState = UiDataState.Success(rooms),
                    selectedFloorId = "2",
                    selectedRoomId = "1",
                    onNavigateBack = {},
                    onFloorSelected = { selectedFloorId = it },
                    onRoomSelected = { selectedRoomId = it },
                    onRefresh = {}
                )
            }
        }

        // 验证页面标题
        composeTestRule.onNodeWithText("楼层区域").assertIsDisplayed()

        // 验证当前楼层显示
        composeTestRule.onNodeWithText("1F一层").assertIsDisplayed()

        // 验证房间Chip存在
        composeTestRule.onNodeWithText("客厅").assertIsDisplayed()
        composeTestRule.onNodeWithText("主卧").assertIsDisplayed()
        composeTestRule.onNodeWithText("儿童房").assertIsDisplayed()
        composeTestRule.onNodeWithText("卫生间").assertIsDisplayed()

        // 点击房间切换
        composeTestRule.onNodeWithText("主卧").performClick()
        composeTestRule.waitForIdle()
        assert(selectedRoomId == "2")

        // 点击另一个房间
        composeTestRule.onNodeWithText("儿童房").performClick()
        composeTestRule.waitForIdle()
        assert(selectedRoomId == "3")
    }

    /**
     * 测试楼层区域页面加载状态
     */
    @Test
    fun test_floorZoneLoadingState() {
        composeTestRule.setContent {
            WuHengTheme {
                FloorZoneContent(
                    floorsState = UiDataState.Loading,
                    roomsState = UiDataState.Loading,
                    selectedFloorId = null,
                    selectedRoomId = null,
                    onNavigateBack = {},
                    onFloorSelected = {},
                    onRoomSelected = {},
                    onRefresh = {}
                )
            }
        }

        // 验证加载指示器显示
        composeTestRule.onNode(hasProgressBarRangeInfo()).assertIsDisplayed()
    }

    /**
     * 测试楼层区域页面错误状态
     */
    @Test
    fun test_floorZoneErrorState() {
        var retryClicked = false

        composeTestRule.setContent {
            WuHengTheme {
                FloorZoneContent(
                    floorsState = UiDataState.Error(
                        com.wuheng.smart.data.network.AppException.NetworkError("网络连接失败")
                    ),
                    roomsState = UiDataState.Error(
                        com.wuheng.smart.data.network.AppException.NetworkError("网络连接失败")
                    ),
                    selectedFloorId = null,
                    selectedRoomId = null,
                    onNavigateBack = {},
                    onFloorSelected = {},
                    onRoomSelected = {},
                    onRefresh = { retryClicked = true }
                )
            }
        }

        // 验证错误信息显示
        composeTestRule.onNodeWithText("加载失败").assertIsDisplayed()

        // 验证重试按钮存在并点击
        composeTestRule.onNodeWithText("重试").assertIsDisplayed().performClick()

        composeTestRule.waitForIdle()
        assert(retryClicked)
    }

    /**
     * 测试楼层区域页面数据加载中状态
     */
    @Test
    fun test_floorZoneLoadingWithDataState() {
        val floors = listOf(
            FloorInfo(1, "FL001", "B1地下室", -1, "100", 3),
            FloorInfo(2, "FL002", "1F一层", 1, "120", 4)
        )

        val rooms = listOf(
            RoomInfo(1, "RM001", "客厅", "living", "40", 2),
            RoomInfo(2, "RM002", "主卧", "bedroom", "25", 1)
        )

        composeTestRule.setContent {
            WuHengTheme {
                FloorZoneContent(
                    floorsState = UiDataState.Success(floors),
                    roomsState = UiDataState.LoadingWithData(rooms),
                    selectedFloorId = "2",
                    selectedRoomId = "1",
                    onNavigateBack = {},
                    onFloorSelected = {},
                    onRoomSelected = {},
                    onRefresh = {}
                )
            }
        }

        // 验证楼层选择器显示
        composeTestRule.onNodeWithText("1F一层").assertIsDisplayed()

        // 验证房间Chip显示
        composeTestRule.onNodeWithText("客厅").assertIsDisplayed()
        composeTestRule.onNodeWithText("主卧").assertIsDisplayed()

        // 验证加载指示器显示（在楼层选择器中）
        composeTestRule.onNodeWithText("加载中...").assertIsDisplayed()
    }

    /**
     * 测试温度设定卡片交互
     */
    @Test
    fun test_temperatureCardInteraction() {
        val floors = listOf(
            FloorInfo(2, "FL002", "1F一层", 1, "120", 4)
        )

        val rooms = listOf(
            RoomInfo(1, "RM001", "客厅", "living", "40", 2)
        )

        composeTestRule.setContent {
            WuHengTheme {
                FloorZoneContent(
                    floorsState = UiDataState.Success(floors),
                    roomsState = UiDataState.Success(rooms),
                    selectedFloorId = "2",
                    selectedRoomId = "1",
                    onNavigateBack = {},
                    onFloorSelected = {},
                    onRoomSelected = {},
                    onRefresh = {}
                )
            }
        }

        // 验证温度设定卡片显示
        composeTestRule.onNodeWithText("客厅 温度设定").assertIsDisplayed()

        // 验证温度档位按钮存在
        composeTestRule.onNodeWithText("偏低-").assertIsDisplayed()
        composeTestRule.onNodeWithText("适中").assertIsDisplayed()
        composeTestRule.onNodeWithText("偏高+").assertIsDisplayed()

        // 点击温度档位
        composeTestRule.onNodeWithText("偏低-").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("偏高+").performClick()
        composeTestRule.waitForIdle()
    }

    /**
     * 测试湿度设定卡片交互
     */
    @Test
    fun test_humidityCardInteraction() {
        val floors = listOf(
            FloorInfo(2, "FL002", "1F一层", 1, "120", 4)
        )

        val rooms = listOf(
            RoomInfo(1, "RM001", "客厅", "living", "40", 2)
        )

        composeTestRule.setContent {
            WuHengTheme {
                FloorZoneContent(
                    floorsState = UiDataState.Success(floors),
                    roomsState = UiDataState.Success(rooms),
                    selectedFloorId = "2",
                    selectedRoomId = "1",
                    onNavigateBack = {},
                    onFloorSelected = {},
                    onRoomSelected = {},
                    onRefresh = {}
                )
            }
        }

        // 验证湿度设定卡片显示
        composeTestRule.onNodeWithText("客厅 湿度设定").assertIsDisplayed()

        // 验证湿度档位按钮存在
        composeTestRule.onNodeWithText("偏低-").assertExists()
    }

    /**
     * 测试新风微控卡片交互
     */
    @Test
    fun test_freshAirCardInteraction() {
        val floors = listOf(
            FloorInfo(2, "FL002", "1F一层", 1, "120", 4)
        )

        val rooms = listOf(
            RoomInfo(1, "RM001", "客厅", "living", "40", 2)
        )

        composeTestRule.setContent {
            WuHengTheme {
                FloorZoneContent(
                    floorsState = UiDataState.Success(floors),
                    roomsState = UiDataState.Success(rooms),
                    selectedFloorId = "2",
                    selectedRoomId = "1",
                    onNavigateBack = {},
                    onFloorSelected = {},
                    onRoomSelected = {},
                    onRefresh = {}
                )
            }
        }

        // 验证新风微控卡片显示
        composeTestRule.onNodeWithText("客厅 新风微控").assertIsDisplayed()

        // 验证风速选择按钮存在
        composeTestRule.onNodeWithText("自动").assertIsDisplayed()
        composeTestRule.onNodeWithText("低速").assertIsDisplayed()
        composeTestRule.onNodeWithText("中速").assertIsDisplayed()
        composeTestRule.onNodeWithText("高速").assertIsDisplayed()

        // 点击风速按钮
        composeTestRule.onNodeWithText("高速").performClick()
        composeTestRule.waitForIdle()
    }
}
