package isthereanydeal.app.common.annotation;

import org.springframework.stereotype.Service;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface Application {
}

// GPT의 도움을 받아 작성한 코드입니다.