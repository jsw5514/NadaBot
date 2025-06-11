package com.bot;

//토큰 별도 저장용
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        String envFilePath = "src/main/resources/token.env";

        // Properties 객체를 이용해 .env 파일 읽기
        Properties envProps = new Properties();
        try {
            envProps.load(Files.newBufferedReader(Paths.get(envFilePath)));
            // 프라이빗 토큰 읽기
            String privateToken = envProps.getProperty("PRIVATE_TOKEN");

            JDA jda = JDABuilder.createDefault(privateToken)
                    .setActivity(Activity.playing("충격과 공포다 거지깽깽이들아"))
                    .addEventListeners(new CommandManager())
                    .build();
            
            jda.awaitReady();
            System.out.println("봇 실행 완료");
        } catch (IOException e) {
            System.err.println(".env 파일을 읽는 도중 에러가 발생했습니다.");
            e.printStackTrace();
        }


    }
}