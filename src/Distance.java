public class Distance {

    public static double measure(Record point1, Record point2){
        double distance = 0.0;

        distance += Math.pow(point1.values.get(0)-point2.values.get(0), 2);
        distance += Math.pow(point1.values.get(1)-point2.values.get(1), 2);
        distance += Math.pow(point1.values.get(2)-point2.values.get(2), 2);
        distance += Math.pow(point1.values.get(3)-point2.values.get(3), 2);

        distance = Math.sqrt(distance)*1.0;

        return distance;
    }

}
