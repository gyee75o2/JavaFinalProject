package common;

import java.util.List;

@AuthorAnno(author = "何峰彬")
public class Sorter {
    public static <T extends Comparable<T>> void sort(List<T> arrays){
        for(int i = arrays.size() - 1; i > 0; i --) {
            for (int j = 0; j < i; j++) {
                if (arrays.get(j).compareTo(arrays.get(j + 1)) > 0) {
                    T temp = arrays.get(j + 1);
                    arrays.set(j + 1, arrays.get(j));
                    arrays.set(j, temp);
                }
            }
        }
    }

    public static <T extends Comparable<T>> void quickSort(List<T> arrays){
        if(arrays == null || arrays.size() <= 1)
            return;
        else {
            T key = arrays.get(0);
            int count = 0;
            for(int i = 1; i < arrays.size(); i ++){
                if(arrays.get(i).compareTo(key) < 0){
                    T temp = arrays.get(++count);
                    arrays.set(count, arrays.get(i));
                    arrays.set(i, temp);
                }
            }
            arrays.set(0, arrays.get(count));
            arrays.set(count, key);
            quickSort(arrays.subList(0, count));
            quickSort(arrays.subList(count+1, arrays.size()));
        }
    }
}
