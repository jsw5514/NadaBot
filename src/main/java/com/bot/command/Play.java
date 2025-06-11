package com.bot.commands;

import com.sedmelluq.lavaplayer.player.AudioPlayer;
import com.sedmelluq.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.lavaplayer.track.*;
import com.sedmelluq.lavaplayer.tools.FriendlyException;

import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Play implements Command {

    private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();

    static {
        AudioSourceManagers.registerRemoteSources(playerManager);
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        String url = event.getOption("query").getAsString();
        Guild guild = event.getGuild();
        AudioChannel voiceChannel = event.getMember().getVoiceState().getChannel();

        if (voiceChannel == null) {
            event.reply("❌ 먼저 음성 채널에 들어가주세요.").setEphemeral(true).queue();
            return;
        }

        // 연결
        AudioManager audioManager = guild.getAudioManager();
        if (!audioManager.isConnected()) {
            audioManager.openAudioConnection(voiceChannel);
        }

        // 플레이어 생성 및 연결
        AudioPlayer player = playerManager.createPlayer();
        audioManager.setSendingHandler(new LavaPlayerSendHandler(player));

        // 트랙 로딩
        playerManager.loadItem(url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                player.startTrack(track, false);
                event.reply("▶️ 재생 중: `" + track.getInfo().title + "`").queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack track = playlist.getSelectedTrack() != null
                        ? playlist.getSelectedTrack()
                        : playlist.getTracks().get(0);

                player.startTrack(track, false);
                event.reply("▶️ 재생 중: `" + track.getInfo().title + "` (재생목록)").queue();
            }

            @Override
            public void noMatches() {
                event.reply("❌ 해당 URL의 트랙을 찾을 수 없습니다.").queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                event.reply("⚠️ 트랙 로드 실패: " + e.getMessage()).queue();
            }
        });
    }
}

