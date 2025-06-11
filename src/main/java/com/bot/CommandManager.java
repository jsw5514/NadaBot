package com.bot;

import com.bot.command.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import org.jetbrains.annotations.NotNull;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        event.getJDA().updateCommands().addCommands(
                Commands.slash("play",   "URL 또는 검색어로 음악을 재생합니다.")
                        .addOption(net.dv8tion.jda.api.interactions.commands.OptionType.STRING, "query", "검색어 또는 URL", true),
                Commands.slash("skip",   "현재 곡 스킵"),
                Commands.slash("stop",   "재생 중지"),
                Commands.slash("pause",  "일시정지"),
                Commands.slash("resume", "재시작"),
                Commands.slash("queue",  "대기열 보기")
        ).queue();

        System.out.println("✅ Slash 명령어 등록 완료");
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String cmd = event.getName();
        Command command;

        switch (cmd) {
            case "play":   command = new Play();   break;
            case "skip":   command = new Skip();   break;
            case "stop":   command = new Stop();   break;
            case "pause":  command = new Pause();  break;
            case "resume": command = new Resume(); break;
            case "queue":  command = new Queue();  break;
            default:
                event.reply("알 수 없는 명령어입니다.").setEphemeral(true).queue();
                return;
        }

        command.handle(event);
    }
}
