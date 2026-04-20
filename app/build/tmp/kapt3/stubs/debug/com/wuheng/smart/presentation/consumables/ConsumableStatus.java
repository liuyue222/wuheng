package com.wuheng.smart.presentation.consumables;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.tooling.preview.Preview;
import com.wuheng.smart.presentation.base.UiDataState;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/wuheng/smart/presentation/consumables/ConsumableStatus;", "", "(Ljava/lang/String;I)V", "NORMAL", "WARNING", "CRITICAL", "app_debug"})
public enum ConsumableStatus {
    /*public static final*/ NORMAL /* = new NORMAL() */,
    /*public static final*/ WARNING /* = new WARNING() */,
    /*public static final*/ CRITICAL /* = new CRITICAL() */;
    
    ConsumableStatus() {
    }
}