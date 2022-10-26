package com.diyigemt.groupdumper.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object MainConfig: AutoSavePluginConfig("main") {

  @ValueDescription("指定运行的机器人的qq号")
  val bot: Long by value(0L)

  @ValueDescription("响应谁的消息")
  val manager: Long by value(0L)

  @ValueDescription("启动指令")
  val command: String by value("dump group")

  @ValueDescription("启动指令")
  val template: String by value("\$permit \$qq \$nick")
}