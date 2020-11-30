import java.util.*;

public class Knn {
    int k;
    int crossValidation;

    //Dataset Variables
    List<Record> dataSet;
    List<Record> trainingSet;
    List<Record> testSet;
    List<String> testSetResult;

    //Performance Variables
    List<Double> aList;
    List<Double> fList;
    List<Double> pList;
    List<Double> rList;

    public void run() {
        aList = new ArrayList<>();
        fList = new ArrayList<>();
        rList = new ArrayList<>();
        pList = new ArrayList<>();

        crossValidation = 10;

        read();
        inputK();
        shuffleDataSet();

        for (int i=0; i<crossValidation; i++) {
            divideDataSet(i);
            classification();
            performanceMeasure();
        }

        performanceMean();
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
        // Shuffle
        Collections.shuffle(dataSet);
    }

    private void divideDataSet(int set) {
        trainingSet = new ArrayList<>();
        testSet = new ArrayList<>();

        int datasetSize = dataSet.size();
        int eachSet = datasetSize/crossValidation;

        //System.out.println("Test Set: " + set);
        for (int i=0; i<datasetSize; i++) {
            if ( i >= set*eachSet && i < (set+1)*eachSet ) {
                testSet.add(dataSet.get(i));
                //System.out.println(i);
            }
            else trainingSet.add(dataSet.get(i));
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

    private void performanceMeasure() {
        int[][] confusionMatrix = new int[3][3];
        int success = 0;
        int failure = 0;

        for(int i=0; i<testSet.size(); i++) {
            if(testSet.get(i).name.equals(testSetResult.get(i))){
                success++;
            }
            else failure++;

            //Constructing Confusion Matrix
            int a, b;
            if(testSet.get(i).name.equals("Iris-setosa")) a=0;
            else if(testSet.get(i).name.equals("Iris-virginica")) a=1;
            else a=2; //(testSet.get(i).name.equals("Iris-versicolor"))

            if(testSetResult.get(i).equals("Iris-setosa")) b=0;
            else if(testSetResult.get(i).equals("Iris-virginica")) b=1;
            else b=2; //(testSet.get(i).name.equals("Iris-versicolor"))

            confusionMatrix[a][b]++;
        }

        //Print Confusion Matrix
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                System.out.print(confusionMatrix[i][j] + " ");
            }
            System.out.println();
        }

        // Properties
        double TPset=0, FPset=0, FNset=0, TNset=0, Pset=0, Rset=0, Fset=0;
        double TPvirgin=0, FPvirgin=0, FNvirgin=0, TNvirgin=0, Pvirgin=0, Rvirgin=0, Fvirgin=0;
        double TPversi=0, FPversi=0, FNversi=0, TNversi=0, Pversi=0, Rversi=0, Fversi=0;

        TPset = confusionMatrix[0][0];
        FPset = confusionMatrix[1][0] + confusionMatrix[2][0];
        FNset = confusionMatrix[0][1] + confusionMatrix[0][2];
        TNset = confusionMatrix[1][1] + confusionMatrix[1][2] + confusionMatrix[2][1] + confusionMatrix[2][2];
        Pset = TPset * 1.0 / (TPset + FPset);
        Rset = TPset * 1.0 / (TPset + FNset);
        Fset = 2.0 * Pset * Rset / (Pset + Rset);

        TPvirgin = confusionMatrix[1][1];
        FPvirgin = confusionMatrix[0][1] + confusionMatrix[2][1];
        FNvirgin = confusionMatrix[1][0] + confusionMatrix[1][2];
        TNvirgin = confusionMatrix[0][0] + confusionMatrix[0][2] + confusionMatrix[2][0] + confusionMatrix[2][2];
        Pvirgin = TPvirgin * 1.0 / (TPvirgin + FPvirgin);
        Rvirgin = TPvirgin * 1.0 / (TPvirgin + FNvirgin);
        Fvirgin = 2.0 * Pvirgin * Rvirgin / (Pvirgin + Rvirgin);

        TPversi = confusionMatrix[2][2];
        FPversi = confusionMatrix[0][2] + confusionMatrix[1][2];
        FNversi = confusionMatrix[2][0] + confusionMatrix[2][1];
        TNversi = confusionMatrix[0][0] + confusionMatrix[0][1] + confusionMatrix[1][0] + confusionMatrix[1][1];
        Pversi = TPversi * 1.0  / (TPversi + FPversi);
        Rversi = TPversi * 1.0 / (TPversi + FNversi);
        Fversi = 2.0 * Pversi * Rversi / (Pversi + Rversi);

        double Paverage=0, Raverage=0, Faverage=0, Aaverage=0;
        Paverage = ( Pset + Pvirgin + Pversi ) / 3.0 / 0.01;
        Raverage = ( Rset + Rvirgin + Rversi ) / 3.0 / 0.01;
        Faverage = ( Fset + Fvirgin + Fversi ) / 3.0 / 0.01;
        Aaverage = (TPset + TPversi + TPvirgin) / testSetResult.size() / 0.01;

        System.out.print("Precision: " + String.format("%.2f" ,Paverage));
        System.out.print(", Recall: " + String.format("%.2f" ,Raverage));
        System.out.print(", F-Score: " + String.format("%.2f" ,Faverage));
        System.out.println(", Accuracy: " + String.format("%.2f" ,Aaverage));

        // adding performance of each iteration to list
        aList.add(Aaverage);
        fList.add(Faverage);
        rList.add(Raverage);
        pList.add(Paverage);
    }

    private void performanceMean() {
        Double aMean = 0.0;
        Double fMean = 0.0;
        Double rMean = 0.0;
        Double pMean = 0.0;

        for (int i=0; i<aList.size(); i++) {
            aMean += aList.get(i);
            pMean += pList.get(i);
            fMean += fList.get(i);
            rMean += rList.get(i);
        }

        aMean = aMean/crossValidation;
        fMean = fMean/crossValidation;
        rMean = rMean/crossValidation;
        pMean = pMean/crossValidation;

        System.out.println("\n------------------\nFinal Performance: \n------------------");
        System.out.println("Accuracy is " + String.format("%.2f", aMean) + " %");
        System.out.println("Precision is " + String.format("%.2f", pMean) + " %");
        System.out.println("F-Score is " + String.format("%.2f", fMean) + " %");
        System.out.println("Recall is " + String.format("%.2f", rMean) + " %");
    }
}
