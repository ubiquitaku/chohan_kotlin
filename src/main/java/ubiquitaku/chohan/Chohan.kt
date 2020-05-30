package ubiquitaku.chohan

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class Chohan : JavaPlugin() {

    var pln = "丁半plugin"
    var cho = mutableListOf<Player>()
    var han = mutableListOf<Player>()
    var money = 0
    var game = false

    override fun onEnable() {
        // Plugin startup logic

        // Plugin startup logic
        game = false
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name != "ch") {
            return true
        }
        if (sender !is Player) {
            sender.sendMessage(pln + "コンソールからの実行ができないコマンドです")
            return true
        }
        if (args.size == 0) {
            sender.sendMessage("$pln/ch <金額>　: 丁半を開始します")
            sender.sendMessage("$pln/ch c : 丁に賭けます")
            sender.sendMessage("$pln/ch h : 半に賭けます")
            return true
        }

        if (game) {
            if (args[0] === "c") {
                /*所持金の判定
                if (所持金 <= money) {
                    return true;
                }
                金を取り上げる
                */
                cho.add(sender as Player)
                han.remove(sender)
                sender.sendMessage("あなたは丁に" + money + "円賭けました")
                for (i in cho.indices) {
                    val j = server.getPlayer(cho[i].toString())
                    j!!.sendMessage(sender.getName() + "が丁に賭けました")
                    j!!.sendMessage(ChatColor.RED.toString() + "丁" + cho.size)
                    j!!.sendMessage(ChatColor.BLUE.toString() + "半" + han.size)
                }
                for (i in han.indices) {
                    val j = server.getPlayer(han[i].toString())
                    j!!.sendMessage(sender.getName() + "が丁に賭けました")
                    j!!.sendMessage(ChatColor.RED.toString() + "丁" + cho.size)
                    j!!.sendMessage(ChatColor.BLUE.toString() + "半" + han.size)
                }
                return true
            }
            if (args[0] === "h") {
                /*所持金の判定
                if (所持金 <= money) {
                    return true;
                }
                金を取り上げる
                */
                cho.remove(sender)
                han.add(sender as Player)
                sender.sendMessage(pln + "あなたは半に" + money + "円賭けました")
                for (i in cho.indices) {
                    val j = server.getPlayer(cho[i].toString())
                    j!!.sendMessage(pln)
                    j!!.sendMessage(sender.getName() + "が半に賭けました")
                    j!!.sendMessage(ChatColor.RED.toString() + "丁" + cho.size)
                    j!!.sendMessage(ChatColor.BLUE.toString() + "半" + han.size)
                }
                for (i in han.indices) {
                    val j = server.getPlayer(han[i].toString())
                    j!!.sendMessage(pln)
                    j!!.sendMessage(sender.getName() + "が半に賭けました")
                    j!!.sendMessage(ChatColor.RED.toString() + "丁" + cho.size)
                    j!!.sendMessage(ChatColor.BLUE.toString() + "半" + han.size)
                }
                return true
            }
            return true
        }
        money = try {
            args[0].toInt()
        } catch (money: NumberFormatException) {
            sender.sendMessage(pln + "開発者のミス出なければコマンドに間違いがあります")
            sender.sendMessage("/bd でコマンドのヘルプを確認することができます")
            return true
        }
        Bukkit.broadcastMessage(pln + sender + "が" + money + "円の丁半を開始しました")
        game = true

        Bukkit.getScheduler().runTaskTimer(this, object : Runnable {
            var time = 10 //or any other number you want to start countdown from
            override fun run() {
                if (time == 0) {
                    return
                }
                //                for (final Player player : Bukkit.getOnlinePlayers()) {
//                    player.sendMessage(this.time + " second(s) remains!");
//                }
                time--
            }
        }, 0L, 20L)

        Bukkit.getScheduler().runTaskTimer(this, object : Runnable {
            var time = 5 //or any other number you want to start countdown from
            override fun run() {
                if (time == 0) {
                    return
                }
                for (player in Bukkit.getOnlinePlayers()) {
                    Bukkit.broadcastMessage(pln + "残り" + time + "秒")
                }
                time--
            }
        }, 0L, 20L)
        game = false
        val rnd = Random()
        val dice = rnd.nextBoolean()
        if (dice) {
            Bukkit.broadcastMessage(pln + "丁の勝利")
            for (i in cho.indices) {
                Bukkit.broadcastMessage(server.getPlayer(cho[i].toString()).toString())
                //cに金を配布
            }
        }
        Bukkit.broadcastMessage(pln + "半の勝利")
        for (i in han.indices) {
            Bukkit.broadcastMessage(server.getPlayer(cho[i].toString()).toString())
            //hに金を配布
        }
        cho.removeAll(cho)
        han.removeAll(han)
        return true
    }
}
