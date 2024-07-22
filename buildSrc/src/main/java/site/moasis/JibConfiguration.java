package site.moasis;

import com.google.cloud.tools.jib.gradle.JibExtension;
import org.gradle.api.Project;
import java.util.Collections;
import java.util.List;

public class JibConfiguration {

    public static void buildSpringDockerImage(Project project, JibExtension jibExtension, String imageName) {
        jibExtension.from(from -> {
            from.setImage("eclipse-temurin:17-jre-focal");
            from.platforms(platforms -> platforms.platform(platform -> {
                platform.setArchitecture(project.findProperty("jibArchitecture") != null
                    ? (String) project.findProperty("jibArchitecture")
                    : "amd64");
                platform.setOs("linux");
            }));
        });

        jibExtension.to(to -> to.setImage(imageName + ":latest"));

        jibExtension.getContainer().setJvmFlags(List.of("-XX:+AlwaysPreTouch"));
        jibExtension.getContainer().setPorts(List.of("8080"));
        jibExtension.getContainer().setEnvironment(Collections.singletonMap("SPRING_OUTPUT_ANSI_ENABLED", "ALWAYS"));
        jibExtension.getContainer().getCreationTime().set("USE_CURRENT_TIMESTAMP");
    }
}
