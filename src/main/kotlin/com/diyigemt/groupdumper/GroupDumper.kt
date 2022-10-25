package com.diyigemt.groupdumper

import com.diyigemt.groupdumper.config.MainConfig
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.GroupTempMessageEvent
import java.io.File
import java.io.FileWriter

object GroupDumper : KotlinPlugin(
  JvmPluginDescription(
    id = "com.diyigemt.groupdunmper",
    name = "Group Dumper",
    version = "1.0.0",
  ) {
    JvmPluginDescription.loadFromResource()
  }
) {

  private val idMatcher = Regex("^\\d+$")

  override fun onEnable() {
    MainConfig.reload()
    GroupDumper.dataFolder.mkdirs()
    if (MainConfig.bot == 0L
      || MainConfig.manager == 0L
      || MainConfig.command.isBlank()
      || MainConfig.template.isBlank()
    ) {
      logger.error("请先配置后再使用")
      return
    }
    GlobalEventChannel.filter {
      it is GroupMessageEvent && it.sender.id == MainConfig.manager
    }.subscribeAlways<GroupMessageEvent> {
      if (it.message.serializeToMiraiCode().startsWith(MainConfig.command)) {
        doDump(it.subject, it.group.id)
        it.subject.sendMessage("群${it.group.id}转储成功")
      }
    }
    GlobalEventChannel.filter {
      it is FriendMessageEvent && it.sender.id == MainConfig.manager
    }.subscribeAlways<FriendMessageEvent> {
      val msg = it.message.serializeToMiraiCode()
      if (msg.startsWith(MainConfig.command)) {
        val id = msg.replace(MainConfig.command, "").trim()
        if (idMatcher.matches(id)) {
          doDump(it.subject, id.toLong())
          it.subject.sendMessage("群${id}转储成功")
        } else {
          sender.sendMessage("群id格式不正确")
        }
      }
    }
    GlobalEventChannel.filter {
      it is GroupTempMessageEvent && it.sender.id == MainConfig.manager
    }.subscribeAlways<GroupTempMessageEvent> {
      if (it.message.serializeToMiraiCode().startsWith(MainConfig.command)) {
        doDump(it.subject, it.group.id)
        it.subject.sendMessage("群${it.group.id}转储成功")
      }
    }
  }

  private suspend fun doDump(contact: Contact, id: Long) {
    kotlin.runCatching {
      Bot.getInstance(MainConfig.bot)
    }.onSuccess {
      val group = it.groups[id]
      if (group == null) {
        contact.sendMessage("机器人(${MainConfig.bot})不在群(${id})中, 操作执行失败")
      } else {
        doSave(
          id,
          group.members
            .sortedByDescending { nm ->
              when(nm.permission) {
                MemberPermission.OWNER -> 100
                MemberPermission.MEMBER -> 1
                MemberPermission.ADMINISTRATOR -> 10
              }
            }
            .map { member ->
            parseTemplate(member.id, member.nameCardOrNick, member.remarkOrNameCard, member.permission)
          },
          group.name
        )
      }
    }.onFailure {
      contact.sendMessage("机器人(${MainConfig.bot})不在线, 操作执行失败")
    }
  }

  private fun parseTemplate(id: Long, nick: String, name: String, memberPermission: MemberPermission): String {
    val permit = when(memberPermission) {
      MemberPermission.OWNER -> "群主"
      MemberPermission.MEMBER -> "群友"
      MemberPermission.ADMINISTRATOR -> "管理"
    }
    return MainConfig.template
      .replace("\$name", name)
      .replace("\$nick", nick)
      .replace("\$qq", id.toString())
      .replace("\$permit", permit)
  }

  private fun doSave(id: Long, list: List<String>, groupName: String) {
    val file = File("${GroupDumper.dataFolderPath}/${id}.txt")
    if (!file.exists()) {
      file.createNewFile()
    }
    val fileWriter = FileWriter(file)
    fileWriter.write("${groupName}($id)\n")
    fileWriter.write(list.joinToString("\n"))
    fileWriter.flush()
  }

}