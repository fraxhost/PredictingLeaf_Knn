import java.util.*;

public class Knn {
    int k;
    int crossValidation;
    List<Record> dataSet;
    List<Record> trainingSet;
    List<Record> testSet;
    List<String> testSetResult;
    List<Double> accuracyList;

    public void run(){
        accuracyList = new ArrayList<>();
        crossValidation = 5;

        read();
        inputK();

        for (int i=0; i<crossValidation; i++) {
            shuffleDataSet();
            divideDataSet();
            classification();
            accuracyCheck();
        }

        accuracyMean();
    }

    private void read() {
        Read r = new Read();
        dataSet = r.readFile();
    }

    private void inputK() {
        System.out.print("Enter value of k: ");
        Scanner myInput = new Scanner(System.in);
        k = myInput.nextInt();
    }

    private void shuffleDataSet() {
        Collections.shuffle(dataSet);
    }

    private void divideDataSet() {
        trainingSet = new ArrayList<>();
        testSet = new ArrayList<>();

        int datasetSize = dataSet.size();
        int trainingSetSize = (datasetSize*9)/10;

        for (int i=0; i<datasetSize; i++) {
            if ( i< trainingSetSize ) {
                trainingSet.add(dataSet.get(i));
            }
            else testSet.add(dataSet.get(i));
        }
    }

    private void classification() {
        testSetResult = new ArrayList<>();

        for (int i=0; i<testSet.size(); i++) {
            int datasetSize = dataSet.size();
            int trainingSetSize = (datasetSize*9)/10;

            double[] distanceArray = new double[trainingSetSize];
            String[] nameArray = new String[trainingSetSize];

            //System.out.println("------ " + testSet.get(i).name + " ------");

            for (int j=0; j<trainingSet.size(); j++) {
                distanceArray[j] = Distance.measure(testSet.get(i), trainingSet.get(j));
                nameArray[j] = trainingSet.get(j).name;
            }

            Sort mySort = new Sort();
            mySort.insertionSort(distanceArray, nameArray);
            testSetResult.add(mySort.nearestNeighbour(k));
        }
    }

    private void printDataSet() {
        for (Record record: dataSet) {
            System.out.print(record.values.get(0) + " - ");
            System.out.print(record.values.get(1) + " - ");
            System.out.print(record.values.get(2) + " - ");
            System.out.print(record.values.get(3) + " - ");
            System.out.println(record.name);
        }
    }

    private void accuracyCheck() {
        int success = 0;
        int failure = 0;

        for(int i=0; i<testSet.size(); i++) {
            if(testSet.get(i).name.equals(testSetResult.get(i))){
                success++;
            }
            else failure++;

            // System.out.println(i + ". " + testSet.get(i).name + " - " + testSetResult.get(i));
        }

        double accuracy = success*100.0/(success+failure);

        accuracyList.add(accuracy);
        //System.out.println("Success Rate: " + accuracy);
    }

    private void accuracyMean() {
        double totalAcc = 0;

        for (Double acc: accuracyList) {
            totalAcc += acc;
        }

        totalAcc = totalAcc/crossValidation;

        System.out.println("Accuracy is " + totalAcc + " %");
    }
}
