import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: pschwarz
 * Date: 07/09/2013
 * Time: 21:37
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    private static final Collection<Integer> EVENS_FROM_TWO_TO_TEN = Arrays.asList(2, 4, 6, 8, 10);

    private static final Collection<Integer> ONE_TO_TEN = new ArrayList<>( asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

    private <T> ArrayList<T> list(T... array)
    {
        ArrayList<T> list = new ArrayList<>();
        for(T n : array)
            list.add(n);
        return list;
    }

    @Test
    public void test_removeIf()
    {
        Predicate<Integer> isEven = x -> (x % 2) == 0;

        Collection<Integer> numbers = list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        numbers.removeIf( isEven );

        assertEquals( list(1, 3, 5, 7, 9), numbers);
    }

    @Test
    public void test_forEach_with_consuming_lambda()
    {
        List<Integer> numbers = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        StringBuilder builder = new StringBuilder();

        numbers.forEach( n ->
        {
            builder.append(n * n);
            builder.append(" ");
        });

        assertEquals("1 4 9 16 25 36 49 64 81 100 ", builder.toString());
    }

    @Test
    public void test_forEach_with_consumer()
    {
        List<Integer> numbers = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        StringBuilder builder = new StringBuilder();

        Consumer<Integer> appendSquareToBuilder = n ->
        {
            builder.append( n * n );
            builder.append(" ");
        };

        numbers.forEach( appendSquareToBuilder );

        assertEquals("1 4 9 16 25 36 49 64 81 100 ", builder.toString());
    }

    @Test
    public void test_map_square_onto_stream()
    {
        List<Integer> numbers = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Function<Integer,Integer> square =  n -> n * n;

        List<Integer> squaredNumbers =
            numbers.stream()
                   .map(square)
                   .collect(toList());

        assertEquals( asList(1, 4, 9, 16, 25, 36, 49, 64, 81, 100), squaredNumbers);
    }

    Integer sumAll(List<Integer> numbers)
    {
        return numbers.stream().reduce(0, (x, y) -> x + y);
    }

    @Test
    public void test_reduce_with_lambda()
    {
        List<Integer> numbers = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int sigma = sumAll(numbers);

        assertEquals( (10 * 11) / 2, sigma);

    }

    BinaryOperator<Integer> addition = (x, y) -> x + y;

    Integer addAll(List<Integer> numbers)
    {
        return numbers.stream().reduce(0, addition);
    }

    @Test
    public void test_reduce_with_binary_operator()
    {
        List<Integer> numbers = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int sigma = addAll(numbers);

        assertEquals( (10 * 11) / 2, sigma);

    }

    List<Integer> sort(List<Integer> numbers)
    {
        return numbers.stream().sorted().collect( toList() );
    }

    @Test
    public void test_sort()
    {
        List<Integer> numbers = asList(4, 7, 9, 1, 3, 5, 2, 8, 6, 10);

        List<Integer> sortedNumbers = sort(numbers);

        assertEquals( asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), sortedNumbers);
    }

}
