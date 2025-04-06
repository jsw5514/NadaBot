package org.example;

//토큰 별도 저장용
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
//        String currentDirectory = System.getProperty("user.dir");
//        System.out.println("현재 작업 디렉터리: " + currentDirectory);
//        System.out.println("Hello World!");

        String envFilePath = "src/main/resources/token.env";

        // Properties 객체를 이용해 .env 파일 읽기
        Properties envProps = new Properties();
        try {
            envProps.load(Files.newBufferedReader(Paths.get(envFilePath)));
            // 프라이빗 토큰 읽기
            String privateToken = envProps.getProperty("PRIVATE_TOKEN");
            //System.out.println("프라이빗 토큰: " + privateToken); //debug output

            JDABuilder.createDefault(privateToken)
                    .enableIntents(
                            GatewayIntent.MESSAGE_CONTENT,
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_VOICE_STATES
                    )
                    .addEventListeners(new Bot())
                    .build();

        } catch (IOException e) {
            System.err.println(".env 파일을 읽는 도중 에러가 발생했습니다.");
            e.printStackTrace();
        }


    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().equals("!ping")) {
            event.getChannel().sendMessage("Pong!").queue();
        }
    }
}