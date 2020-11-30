public class Sort {
    double[] distanceArray;
    String[] nameArray;

    public void insertionSort(double[] array1, String[] array2) {
        for (int i = 1; i < array1.length; i++) {
            double current = array1[i];
            String currentName = array2[i];

            int j = i - 1;
            while(j >= 0 && current < array1[j]) {
                array1[j+1] = array1[j];
                array2[j+1] = array2[j];
                j--;
            }
            array1[j+1] = current;
            array2[j+1] = currentName;
        }

        distanceArray = array1.clone();
        nameArray = array2.clone();
    }

    public String nearestNeighbour(int k) {

        for (int i=0; i< k; i++) {
            //System.out.println((i+1) + "- " + nameArray[i] + ": " + distanceArray[i]);
        }

        //System.out.println("\n---");

        int setosa=0;
        int virginica=0;
        int versicolor=0;

        for (int i=0; i< k; i++) {
            if (nameArray[i].equals("Iris-setosa")) setosa++;
            if (nameArray[i].equals("Iris-versicolor")) versicolor++;
            if (nameArray[i].equals("Iris-virginica")) virginica++;
        }

        //System.out.println("Setosa=" + setosa + ", Versicolor=" + versicolor + ", Virginica=" + virginica);

        if (setosa > virginica && setosa > versicolor) return "Iris-setosa";
        else if (virginica > setosa && virginica > versicolor) return "Iris-virginica";
        else return "Iris-versicolor";
    }
}