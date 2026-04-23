# 五恒智能控制系统 - 发布检查清单

## 📋 文档信息

- **项目名称**: 五恒智能控制系统 APP (WuHeng Smart)
- **包名**: `com.wuheng.smart`
- **当前版本**: v1.0.0
- **最后更新**: 2026-04-23

---

## 🚀 发布前检查项

### 1. 代码检查

- [ ] 所有功能开发完成并通过自测
- [ ] 代码审查（Code Review）已完成
- [ ] 所有TODO和FIXME已处理或已记录
- [ ] 无调试代码残留（如Log.d、println等）
- [ ] 无硬编码的测试数据
- [ ] 敏感信息已移除（API密钥、密码等）

### 2. 版本管理

- [ ] 版本号已更新（`versionMajor.versionMinor.versionPatch`）
- [ ] versionCode已自动递增
- [ ] 版本变更日志已更新
- [ ] 版本标签已创建（Git Tag）

```bash
# 创建版本标签示例
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

### 3. 签名配置检查

- [ ] Release签名密钥库存在（`wuheng-release.jks`）
- [ ] `local.properties`中签名配置正确
- [ ] 签名密钥库已备份到安全位置
- [ ] 签名密钥库密码安全存储

```properties
# local.properties 签名配置检查
RELEASE_STORE_FILE=wuheng-release.jks
RELEASE_STORE_PASSWORD=wuheng2024
RELEASE_KEY_ALIAS=wuheng-release
RELEASE_KEY_PASSWORD=wuheng2024
```

### 4. 构建配置检查

- [ ] `build.gradle.kts`中版本号正确
- [ ] 多渠道配置正确
- [ ] API地址配置正确（Release环境）
- [ ] ProGuard/R8混淆规则已配置
- [ ] 构建缓存已清理（可选）

### 5. 资源文件检查

- [ ] 应用图标所有尺寸已准备
- [ ] 启动图已配置
- [ ] 多语言资源完整
- [ ] 图片资源已压缩
- [ ] 无用资源已清理

### 6. 权限检查

- [ ] 只申请必要的权限
- [ ] 权限说明已配置（Android 6.0+）
- [ ] 隐私政策链接已配置

### 7. 测试验证

- [ ] 单元测试全部通过
- [ ] 集成测试全部通过
- [ ] UI测试全部通过
- [ ] 真机测试通过（至少3台不同设备）
- [ ] 性能测试通过（启动时间、内存占用）
- [ ] 网络异常测试通过

---

## 🔨 构建步骤

### 方式一：命令行构建（推荐）

#### 1. 清理构建缓存

```bash
./gradlew clean
```

#### 2. 打印版本信息

```bash
./gradlew printVersionInfo
```

#### 3. 构建单个渠道Release包

```bash
# 官方渠道
./gradlew assembleOfficialRelease

# 华为渠道
./gradlew assembleHuaweiRelease

# 小米渠道
./gradlew assembleXiaomiRelease

# OPPO渠道
./gradlew assembleOppoRelease

# VIVO渠道
./gradlew assembleVivoRelease

# 应用宝渠道
./gradlew assembleYingyongbaoRelease

# Beta测试渠道
./gradlew assembleBetaRelease
```

#### 4. 构建所有渠道Release包

```bash
./gradlew buildAllRelease
```

#### 5. 构建Debug包（用于测试）

```bash
./gradlew assembleOfficialDebug
```

### 方式二：Android Studio构建

1. 打开Android Studio
2. 选择 `Build` -> `Generate Signed Bundle / APK`
3. 选择 `APK`
4. 选择Release签名密钥库（`wuheng-release.jks`）
5. 选择Build Variant（如：officialRelease）
6. 点击 `Finish`

---

## 📦 输出文件

### APK输出路径

```
app/build/outputs/apk/
├── official/
│   ├── release/
│   │   └── WuHeng_official_release_v1.0.0_2_2026-04-23.apk
│   └── debug/
│       └── WuHeng_official_debug_v1.0.0-debug_2_2026-04-23.apk
├── huawei/
│   └── release/
│       └── WuHeng_huawei_release_v1.0.0_2_2026-04-23.apk
├── xiaomi/
│   └── release/
│       └── WuHeng_xiaomi_release_v1.0.0_2_2026-04-23.apk
├── oppo/
│   └── release/
│       └── WuHeng_oppo_release_v1.0.0_2_2026-04-23.apk
├── vivo/
│   └── release/
│       └── WuHeng_vivo_release_v1.0.0_2_2026-04-23.apk
├── yingyongbao/
│   └── release/
│       └── WuHeng_yingyongbao_release_v1.0.0_2_2026-04-23.apk
└── beta/
    └── release/
        └── WuHeng_beta_release_v1.0.0-beta_2_2026-04-23.apk
```

### 文件名格式

```
WuHeng_{flavorName}_{buildTypeName}_v{versionName}_{versionCode}_{date}.apk
```

---

## ✅ 测试验证项

### 1. 安装测试

- [ ] APK可以正常安装
- [ ] 覆盖安装正常（从旧版本升级）
- [ ] 全新安装正常
- [ ] 安装包大小正常（< 100MB）

### 2. 启动测试

- [ ] 应用可以正常启动
- [ ] 启动时间正常（< 3秒）
- [ ] 启动无崩溃
- [ ] 启动无ANR

### 3. 功能测试

- [ ] 登录/注册功能正常
- [ ] 首页数据显示正常
- [ ] 设备控制功能正常
- [ ] 场景切换功能正常
- [ ] 消息通知功能正常
- [ ] 个人中心功能正常

### 4. 网络测试

- [ ] 正常网络下功能正常
- [ ] 弱网环境下有提示
- [ ] 断网重连正常
- [ ] 网络切换正常（WiFi/4G/5G）

### 5. 兼容性测试

- [ ] Android 8.0 (API 26) 正常
- [ ] Android 10 (API 29) 正常
- [ ] Android 12 (API 31) 正常
- [ ] Android 13 (API 33) 正常
- [ ] 不同分辨率屏幕正常
- [ ] 横竖屏切换正常

### 6. 性能测试

- [ ] 内存占用正常（< 200MB）
- [ ] CPU占用正常（< 30%）
- [ ] 无内存泄漏
- [ ] 流畅度正常（> 55 FPS）

### 7. 签名验证

```bash
# 验证APK签名
jarsigner -verify -verbose -certs app/build/outputs/apk/official/release/WuHeng_official_release_v1.0.0_2_2026-04-23.apk

# 查看APK信息
aapt dump badging app/build/outputs/apk/official/release/WuHeng_official_release_v1.0.0_2_2026-04-23.apk
```

---

## 📤 发布流程

### 1. 内部测试

- [ ] 上传至内部测试平台（Firebase App Distribution / TestFlight）
- [ ] 邀请内部测试人员
- [ ] 收集反馈并修复问题

### 2. 灰度发布

- [ ] 选择灰度渠道（如：Beta渠道）
- [ ] 设置灰度比例（如：5% -> 20% -> 50%）
- [ ] 监控崩溃率和用户反馈
- [ ] 逐步扩大灰度范围

### 3. 正式发布

- [ ] 各应用商店上传
  - [ ] 华为应用市场
  - [ ] 小米应用商店
  - [ ] OPPO应用商店
  - [ ] VIVO应用商店
  - [ ] 应用宝
  - [ ] 其他渠道
- [ ] 应用商店审核通过
- [ ] 正式发布上线

---

## 📊 发布后监控

### 1. 崩溃监控

- [ ] 崩溃率 < 0.1%
- [ ] 无严重崩溃问题
- [ ] 崩溃日志已收集

### 2. 性能监控

- [ ] 启动时间正常
- [ ] ANR率 < 0.01%
- [ ] 内存占用正常

### 3. 用户反馈

- [ ] 监控用户评论
- [ ] 收集用户反馈
- [ ] 处理用户问题

---

## 🆘 回滚方案

### 触发条件

- 崩溃率 > 1%
- 严重功能缺陷
- 用户投诉集中

### 回滚步骤

1. 立即暂停新版本推广
2. 联系应用商店下架问题版本
3. 上传上一稳定版本
4. 通知用户更新
5. 紧急修复问题

---

## 📝 版本历史

| 版本 | 日期 | 变更内容 | 发布人 |
|------|------|----------|--------|
| v1.0.0 | 2026-04-23 | 初始发布 | - |

---

## 📞 联系方式

- **技术支持**: dev@wuheng.com
- **紧急联系**: +86-xxx-xxxx-xxxx

---

## ✅ 发布确认

发布完成后，请确认以下事项：

- [ ] 所有渠道APK已上传
- [ ] 应用商店审核已通过
- [ ] 应用已上线
- [ ] 监控已配置
- [ ] 团队已通知

**发布日期**: _______________

**发布人**: _______________

**确认人**: _______________
