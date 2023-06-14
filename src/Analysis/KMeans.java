package Analysis;

import java.util.ArrayList;

public class KMeans {
	private int k;
	public KMeans(int cluster) {
		this.k = cluster;
		
	}

    //ex data = (1<an score san student>, 1< tos kun nanu na data na sa tingin tah significance sa score like age>)
    public ArrayList<KMeansData> initClusterPoint(ArrayList<KMeansData> data) {
    	
    	//Kailangan unique an data na ibutang

        ArrayList<Double> xTest = new ArrayList<Double>();
        ArrayList<Double> yTest = new ArrayList<Double>();
        ArrayList<KMeansData> clusterPoints = new ArrayList<KMeansData>();
        for(int i = 0; i< this.k; i++){    
            int randomIndex = (int) Math.floor(Math.random() * data.size());  
            KMeansData temp = data.get(randomIndex);
            while(xTest.contains(temp.getOne()) && yTest.contains(temp.getTwo()) ){
                randomIndex = (int) Math.floor(Math.random() * data.size()); 
                temp = data.get(randomIndex);
            }
            clusterPoints.add(data.get(randomIndex));
            xTest.add(data.get(randomIndex).getOneScale());
            yTest.add(data.get(randomIndex).getTwoScale());
        }
        return clusterPoints;
    }

    //euclidean distance ine, an sum san sqrt difference san duha na points ie ([1,2], [1,3])
    public double euclideanDistance(KMeansData pointA, KMeansData clusterPoint){ 
    	double data1 = Math.abs(pointA.getOneScale()  - clusterPoint.getOneScale());
    	double data2 = Math.abs(pointA.getTwoScale()  - clusterPoint.getTwoScale());
    	
    	return data1 + data2;
    }

    public ArrayList<KMeansClusterPoint> initAssignDataToCluster(ArrayList<KMeansData> data, ArrayList<KMeansData> clusterPoints){
    	ArrayList<KMeansClusterPoint> clusterAssignments = new ArrayList<KMeansClusterPoint>();
        for (int j = 0; j < clusterPoints.size(); j++) {
            clusterAssignments.add(new KMeansClusterPoint(clusterPoints.get(j)));
        }
        for (int i = 0; i < data.size(); i++) {
            //init
            double minDistance = Double.POSITIVE_INFINITY;
            KMeansData closestCluster = null;
            //Kuhaon an distance san cluster pati an data then 
            for (int j = 0; j < clusterPoints.size(	); j++) {
                double newDistance = euclideanDistance(data.get(i), clusterPoints.get(j));
                if(newDistance < minDistance){
                    minDistance = newDistance;
                    closestCluster = clusterPoints.get(j);
                }
            }
            //igAssign kun hain an pinakaHarani na clusterPoint
            for(int k = 0; k < clusterAssignments.size(); k++){
                if(clusterAssignments.get(k).getClusterPoint() ==  closestCluster){
                    clusterAssignments.get(k).addData(data.get(i));
                }
            }
        }
        return clusterAssignments;
    }

    public ArrayList<KMeansData> getNewClusterPoint(ArrayList<KMeansClusterPoint> assignments){
    	ArrayList<KMeansData>  newClusterPoints = new ArrayList<KMeansData>();
    	for(int i = 0; i < assignments.size(); i++) {
    		//Guincheck didi kun 
    		if(assignments.get(i).getData().size() == 1) {
    			newClusterPoints.add(assignments.get(i).getClusterPoint());
    		}else {
    			double sumA = 0, sumB = 0;
                ArrayList<KMeansData> clusters = assignments.get(i).getData();
                for(int k = 0; k < clusters.size(); k++) {
                    sumA += clusters.get(k).getOneScale();
                    sumB += clusters.get(k).getTwoScale();
                }

                double pointA = sumA / clusters.size();
                double pointB = sumB / clusters.size();
                KMeansData k = new KMeansData();
                k.setOneScale(pointA);
                k.setTwoScale(pointB);
                newClusterPoints.add(k);
    		}
        }
        return newClusterPoints;
    }
}

