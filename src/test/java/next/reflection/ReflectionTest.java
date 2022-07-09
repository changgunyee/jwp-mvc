package next.reflection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    // 필드, 메서드, 생성자를 출력한다.
    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        logger.debug("start logging declared fields");
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> logger.debug(field.getName()));

        logger.debug("start logging declared methods");
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> logger.debug(method.getName()));

        logger.debug("start logging declared constructors");
        Arrays.stream(clazz.getDeclaredConstructors()).forEach(field -> logger.debug(field.getName()));
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        Question question = (Question) constructor.newInstance("한비야", "여행", "재밌게 여행하자");

        Assertions.assertThat(question)
                .hasFieldOrPropertyWithValue("writer", "한비야")
                .hasFieldOrPropertyWithValue("title", "여행")
                .hasFieldOrPropertyWithValue("contents", "재밌게 여행하자");
    }

    @Test
    public void privateFieldAccess() throws IllegalAccessException, NoSuchFieldException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = new Student();
        Field nameField = clazz.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(student, "chyee");

        Assertions.assertThat(student.getName()).isEqualTo("chyee");
    }
}
