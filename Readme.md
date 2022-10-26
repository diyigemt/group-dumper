## 声明

<h3>一切开发旨在学习，请勿用于非法用途</h3>

- group-dumper 是一款免费且开放源代码的软件，仅供学习和娱乐用途使用。
- group-dumper 不会通过任何方式强制收取费用，或对使用者提出物质条件。
- group-dumper 由整个开源社区维护，并不是属于某个个体的作品，所有贡献者都享有其作品的著作权。

## 许可证

详见 https://github.com/diyigemt/group-dumper/blob/master/LICENSE

group-dumper 继承 [Mirai](https://github.com/mamoe/mirai) 使用 AGPLv3 协议开源。为了整个社区的良性发展，我们强烈建议您做到以下几点：

- 间接接触到 group dumper 的软件使用 AGPLv3 开源
- **不鼓励，不支持一切商业使用**

请注意，由于种种原因，开发者可能在任何时间**停止更新**或**删除项目**。

### 衍生软件需声明引用

- 若引用 group-dumper 发布的软件包而不修改 group-dumper ，则衍生项目需在描述的任意部位提及使用 group-dumper 。
- 若修改 group-dumper 源代码再发布，或参考 group-dumper 内部实现发布另一个项目，则衍生项目必须在文章首部或 'group-dumper' 相关内容首次出现的位置明确声明来源于本仓库 ([group-dumper](https://github.com/diyigemt/group-dumper))。
- 不得扭曲或隐藏免费且开源的事实。

## Statement

<h3>All development is for learning, please do not use it for illegal purposes</h3>

- group dumper is a free and open source software for learning and entertainment purposes only.
- group dumper will not compulsorily charge fees or impose material conditions on users in any way.
- group dumper is maintained by the entire open source community and is not a work belonging to an individual. All contributors enjoy the copyright of their work.

## License

See https://github.com/diyigemt/group-dumper/blob/master/LICENSE for details

group-dumper inherits [Mirai](https://github.com/mamoe/mirai) Open source using AGPLv3 protocol. For the healthy development of the entire community, we strongly recommend that you do the following:

- Software indirectly exposed to group-dumper uses AGPLv3 open source
- **Does not encourage and does not support all commercial use**

Please note that for various reasons, developers may **stop updating** or **deleting** projects at any time.

### Derivative software needs to declare and quote

- If you quote the package released by group dumper without modifying group-dumper , the derivative project needs to mention group-dumper in any part of the description.
- If the group-dumper source code is modified and then released, or another project is released by referring to group-dumper's internal implementation, the derivative project must be clearly stated in the first part of the article or at the location where 'group-dumper' related content first appears from this repository ([group-dumper](https://github.com/diyigemt/group-dumper)).
- The fact that it is free and open source must not be distorted or hidden.

## 介绍

group dumper是基于mirai-console的插件。

**本插件依赖的最低mirai-console版本为2.11.1**

功能就是将群里所有群友的qq号保存下来, 防止炸群以后联系不上

## 配置文件

bot: 指定执行功能的机器人的qq号, 保存群的**前提**是**机器人已经加入这个群**

manager: 相应谁的指令

command: 执行功能的指令, 当信息以这个字符串开头时, 执行插件功能

template: 信息保存模板, 实际执行指令时会将模板中的占位符替换成具体内容

1. $permit: 群友权限, 包括群主、管理和群员
2. $qq: 群友qq号
3. $nick: 群友备注或者qq昵称

## 使用例

插件生效范围为好友消息和群消息, 以下语境以默认配置文件中的command和template进行

好友消息: 以`manager`配置的qq号向机器人发送好友消息, 格式为`dump group 11111`, 则将机器人
所加的群:`11111`中所有联系人信息保存在`./data/com.diyigemt.groupdumper/11111.txt`中

群消息: 以`manager`配置的qq号在机器人已加入并想要保存的群中发送消息`dump group`, 则将本群联系人信息保存, 位置同上