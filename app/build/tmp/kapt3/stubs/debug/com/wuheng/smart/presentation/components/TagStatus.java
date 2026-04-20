package com.wuheng.smart.presentation.components;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Dp;
import com.wuheng.smart.presentation.theme.*;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/wuheng/smart/presentation/components/TagStatus;", "", "(Ljava/lang/String;I)V", "NORMAL", "WARNING", "ERROR", "INFO", "DEFAULT", "app_debug"})
public enum TagStatus {
    /*public static final*/ NORMAL /* = new NORMAL() */,
    /*public static final*/ WARNING /* = new WARNING() */,
    /*public static final*/ ERROR /* = new ERROR() */,
    /*public static final*/ INFO /* = new INFO() */,
    /*public static final*/ DEFAULT /* = new DEFAULT() */;
    
    TagStatus() {
    }
}