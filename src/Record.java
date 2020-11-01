import java.util.ArrayList;
import java.util.List;

public class Record {
    String name;
    List<Double> values;

    public Record(){
        //Empty Constructor
    }

    public Record(String name, List<Double> values){
        this.name = name;

//        Integer i0 = (int) Math.round(values.get(0)*100);
//        Integer i1 = (int) Math.round(values.get(1)*100);
//        Integer i2 = (int) Math.round(values.get(2)*100);
//        Integer i3 = (int) Math.round(values.get(3)*100);

        this.values = new ArrayList<>();
        this.values.add(values.get(0));
        this.values.add(values.get(1));
        this.values.add(values.get(2));
        this.values.add(values.get(3));
    }
}
