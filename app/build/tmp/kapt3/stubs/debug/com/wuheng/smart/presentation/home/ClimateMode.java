package com.wuheng.smart.presentation.home;

import com.wuheng.smart.data.model.*;
import com.wuheng.smart.data.network.ApiResult;
import com.wuheng.smart.data.network.TokenManager;
import com.wuheng.smart.data.repository.HomeRepository;
import com.wuheng.smart.presentation.base.BaseViewModel;
import com.wuheng.smart.presentation.base.UiDataState;
import com.wuheng.smart.presentation.home.components.DeviceCardUiState;
import com.wuheng.smart.presentation.home.components.DeviceType;
import com.wuheng.smart.presentation.home.components.WeatherModeSelectorUiState;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/wuheng/smart/presentation/home/ClimateMode;", "", "(Ljava/lang/String;I)V", "COOLING", "VENTILATION", "HEATING", "app_debug"})
public enum ClimateMode {
    /*public static final*/ COOLING /* = new COOLING() */,
    /*public static final*/ VENTILATION /* = new VENTILATION() */,
    /*public static final*/ HEATING /* = new HEATING() */;
    
    ClimateMode() {
    }
}