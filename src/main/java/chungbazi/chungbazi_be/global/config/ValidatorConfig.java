package chungbazi.chungbazi_be.global.config;


import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.community.repository.PostRepository;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
public class ValidatorConfig {
    @Bean
    public Map<Class<?>, JpaRepository<?, Long>> repositoryMap(
            PostRepository postRepository
    ) {
        Map<Class<?>, JpaRepository<?, Long>> map = new HashMap<>();
        map.put(Post.class, postRepository);
        return map;
    }

    @Bean
    public Map<Class<?>, ErrorStatus> errorStatusMap(){
        Map<Class<?>, ErrorStatus> map = new HashMap<>();
        map.put(Post.class, ErrorStatus.NOT_FOUND_POST);
        return map;
    }
}
