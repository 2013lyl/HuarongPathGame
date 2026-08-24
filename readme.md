数字华容道 (5×5 Sliding Puzzle)

一个用 Java Swing 实现的简洁数字华容道游戏。
目标是通过滑动数字方块，将棋盘恢复为顺序排列（1~24，右下角为空）。


1.游戏规则
棋盘为 5×5 网格，包含数字 1~24 和一个空格。

点击与空格相邻的数字方块，即可将其滑入空格。

当所有数字按顺序排列（1 2 3 … 24，空格在右下角）时，游戏胜利。


2.如何运行
前提条件
JDK 17 或更高版本

Gradle（或使用项目附带的 Gradle Wrapper）

步骤
克隆项目：

git clone https://github.com/2013lyl/HuarongPathGame.git

cd HuarongPathGame

使用 Gradle 构建并运行：

(linux)

./gradlew clean build run


3.操作方式
鼠标点击数字方块，若其与空格相邻，则方块滑入空格。

游戏界面会实时显示当前棋盘状态。


4.技术栈
Java Swing – UI 界面

Gradle – 项目构建

5.注意事项
本游戏为手动操作版本，不含提示或自动求解功能，所有移动由玩家完成。

游戏开局随机生成，可能存在无解局面，此时可重新启动游戏。
