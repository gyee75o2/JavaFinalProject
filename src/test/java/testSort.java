import common.Sorter;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class testSort {
    @Test
    public void testBubbleSort(){
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < 1000; i ++){
            result.add(i);
        }
        List<Integer> random = new ArrayList<>(result);
        for(int i = 0; i < 1000; i ++){
            Collections.shuffle(random);
            Sorter.sort(random);
            assertEquals(random, result);
        }
    }

    @Test
    public void testQuickSort(){
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < 1000; i ++){
            result.add(i);
        }
        List<Integer> random = new ArrayList<>(result);
        for(int i = 0; i < 1000; i ++){
            Collections.shuffle(random);
            Sorter.quickSort(random);
            assertEquals(random, result);
        }
    }
}
