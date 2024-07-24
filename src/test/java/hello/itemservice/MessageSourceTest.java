package hello.itemservice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    /**
     * locale 정보가 없으면 basename에서 설정한 기본 이름 메시지 파일 조회
     * basename으로 messages를 지정했으므로, messages.properties에서 조회
     */
    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    /**
     * 메시지가 없는 경우, -> 기본 메시지
     */
    @Test
    void notFoundMessageCode(){
        assertThatThrownBy(() ->
                ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCodeDefaultMessage(){
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    /**
     * 매개변수 사용
     */
    @Test
    void argumentMessage() {
        String result = ms.getMessage("hello.name",new Object[]{"Spring"},null);
        assertThat(result).isEqualTo("안녕 Spring");
    }

    /**
     * 국제화 파일 선택 1
     */
    @Test
    void defaultLang() {
        // locale null -> messages 사용
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        // locale 정보가 있지만, message_ko가 없으므로 messages 사용
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");

        assertThat(ms.getMessage("hello",null,Locale.ENGLISH)).isEqualTo("hello");
    }
}
