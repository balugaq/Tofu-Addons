# AGENTS.md — Tofu-Addons (TofuMCAddon)

## TL;DR
- 这是个 **Minecraft / Slimefun4 插件**（Bukkit 平台），不是 Web 应用。
- 用 Maven 构建：`mvn clean package`，产物在 `target/`，jar 名形如 `TofuMCAddon vX.Y.Z.jar`。
- 所有物品在 `TofuMCAddon.java` 的 `onEnable()` 里集中注册；新增物品复制既有模板即可。
- **不能直接跑测试**：需要装了 Slimefun 的 Minecraft 服务端（Paper/Spigot 1.18.1）才能加载，本机无运行环境。
- **不要提交 / 推送 / 改写 git 历史**：提交权归主人，AI 只把改动留到可验收状态。

## 项目是什么
`TofuMCAddon` 是 Slimefun4 的一个附属插件（Addon），汉化并改版了「豆腐工艺」配方，加入大量中国特色小吃 / 豆腐制品。玩家通过 Slimefun 研究树，从基础「豆腐」一路做到「麻婆豆腐」「杂烩豆腐汤」等，部分物品带特殊机制（随机传送、驱散药水、点燃、清怪等）。

## 技术栈与依赖
- 语言：**Java 16**（`pom.xml` 中 `maven.compiler.source/target = 16`）
- 平台：Bukkit/Spigot API `1.18.1-R0.1-SNAPSHOT`（`provided` 作用域，构建时不打进 jar）
- 核心依赖：**Slimefun4** `2024.3`（`provided`，必须装在服务端）
- 构建：Maven，默认 goal = `clean package`
- 自定义贴图：Slimefun Dough 库的 `PlayerHead` / `PlayerSkin`，用 base64 生成定制 skull

## 目录结构
```
pom.xml
src/main/java/me/tofumc/tofumcaddon/
  ├─ TofuMCAddon.java        # 主类，实现 SlimefunAddon；onEnable 注册全部物品与配方
  ├─ SkullUtil.java          # 用 base64/hash/url 生成自定义头颅贴图
  ├─ items/                  # 带特殊机制的 SlimefunItem 子类
  │   ├─ ChaosPearl.java     # 豆制珍珠：右击随机传送
  │   ├─ Antidote.java       # 豆奶：饮用移除全部药水效果
  │   ├─ FreshFlesh.java     # 五平饼：食用回满饥饿、扣饱食度
  │   ├─ Quickdote.java      # 味增汤：右击移除药水效果
  │   └─ MobIncapacitator.java # 豆腐能涌动核心：放置后削弱周围低血量怪物
  └─ events/
      └─ PhoenixDown.java    # 地狱豆腐：Listener，定时给手持者点火
src/main/resources/
  ├─ plugin.yml              # Bukkit 插件描述（name/main/depend/version 由 pom 注入）
  └─ config.yml              # 运行时配置（当前仅 auto-update）
```

## 关键约定与模式
1. **注册集中式**：所有物品、配方都在 `TofuMCAddon.onEnable()` 一处硬编码注册；分类 `ItemGroup` 叫「豆腐工艺」。新增物品先复制文件里 `//Template For Item` 注释的骨架。
2. **两类物品**：
   - 普通物品：直接 `new SlimefunItem(...).register(this)`，无额外行为。
   - 行为物品：继承 `SlimefunItem`，在 `preRegister()` 里 `addItemHandler(...)`（如 `ItemUseHandler`、`ItemConsumptionHandler`、`BlockPlaceHandler`）。
3. **配方类型**：多数用 `RecipeType.ENHANCED_CRAFTING_TABLE`；豆奶/豆浆用 `RecipeType.JUICER`；豆腐块压缩用 `RecipeType.COMPRESSOR`。纯配方（不产新物品）用 `RecipeType.X.register(input, output)`。
4. **贴图**：普通物品用原版 `Material`；自定义 skull 用 `SkullUtil.getByBase64(...)`。
5. **命名键**：`SlimefunItemStack` 的 id 形如 `TOFU_XXX`（全大写 + 下划线），展示名用 `§` 颜色码（`§f` 白、`§4` 红、`§6` 金、`§9` 蓝…）。

## 新增一个物品的标准步骤
1. 在 `items/` 下新建类（需特殊机制则继承 `SlimefunItem` 并覆写 `preRegister`；否则直接在 `onEnable` 注册）。
2. 在 `TofuMCAddon.onEnable()` 内定义 `SlimefunItemStack` 与配方数组，再 `register(this)`。
3. 若配方依赖其它物品作材料，被依赖项必须**先于**当前项注册（按文件现有顺序排）。
4. 重新打包：`mvn clean package`。
5. **无法本地运行验证**——必须提醒主人在带 Slimefun 的 1.18.1 服务端实测；不要伪造运行结论。

## 红线 / 注意事项
- **不提交、不推送、不改写 git 历史**：本仓库提交权归主人，AI 只留到可验收状态。
- **不要改动 `target/`**（编译产物）和 `*.iml`（IDE 文件），由 Maven / IDE 自行管理。
- 不要在 `plugin.yml` 手写 `version`——它由 `pom.xml` 的 `${project.version}` 注入。
- `PhoenixDown` 用 `scheduleSyncRepeatingTask(0L, 20L)` 每秒扫在线玩家主手物品；新增定时任务务必走 Bukkit 调度器，注意性能与作用域（避免每秒全服遍历过大）。
- 装饰性物品（如豆腐盾 `TOFU_AEGIS`）对 `ItemMeta` 做了 unbreakable / banner 等硬编码修改，改动时保留其 `ItemFlag` 隐藏逻辑。

## 验收方式
- 静态核对：`mvn -o compile` 能过（需本地仓库已缓存 spigot-api / slimefun4 依赖，或联网）。
- 玩法 / 视觉验收：必须由主人在真实服务端完成；AI 只做代码级核对，并明确标注「待主人服务端实测」。
