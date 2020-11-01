import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Read {
    List<Record> storedData;

    public List<Record> readFile(){
        File file = new File(
                "C:\\Users\\Hp\\Desktop\\Programming\\Java\\Data Mining\\K-NN (Predicting Leaf)\\data.txt");
        storedData = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String tempString1;
            while ((tempString1 = br.readLine()) != null){
                String[] tempString2 = tempString1.split(",", 0);

                Double d1 = Double.parseDouble(tempString2[0]);
                Double d2 = Double.parseDouble(tempString2[1]);
                Double d3 = Double.parseDouble(tempString2[2]);
                Double d4 = Double.parseDouble(tempString2[3]);
                String name = tempString2[4];

                List<Double> tempList = new ArrayList<>();
                tempList.add(d1);
                tempList.add(d2);
                tempList.add(d3);
                tempList.add(d4);

                Record tempData = new Record(name, tempList);
                storedData.add(tempData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return storedData;
    }
}
