import com.sun.org.apache.xpath.internal.operations.String;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * java8 stream api
 * <p>
 * <p>
 * 1.Stream关注的是对数据的运算.与cpu打交道
 * 定义在java.util.stream类下
 * Stream 不会存储元素
 * <p>
 * <p>
 * method  赛选与切片
 */
public class StreamTest {
    public static void main(String[] args) {
        //集合
        List<String> list = new LinkedList<String>();
        final Stream<String> stream1 = list.parallelStream();

        //数组
        int[] ints = new int[5];
        IntStream stream = Arrays.stream(ints);

        //of
        final Stream<Object> of = Stream.of();


        //无限流
        Stream.iterate(0, t -> t + 2).forEach(System.out::println);
    }


    /**
     *
     * 终止操作api allMatch  //是否全部匹配
     *            anyMatch  //是否存在
     *            noneMatch //是否没有
     *
     *         finaany
     *         finafirst
     *
     *         count
     *
     *         max  C
     *         min
     *
     *
     * 规约
     *  reduce(0,Inter.sum())
     *
     * 收集
     * collect  一半使用collectors
     */
    @Test
    public void te() {
        List<Integer> strings = Arrays.asList(1,2);
        Optional<Integer> reduce = strings.stream().reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {

                return integer+integer2;
            }
        });

    }
}
