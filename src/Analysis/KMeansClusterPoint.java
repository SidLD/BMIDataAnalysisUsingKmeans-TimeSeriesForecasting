package Analysis;

import java.util.ArrayList;

public class KMeansClusterPoint {
	KMeansData clusterPoint;
	ArrayList<KMeansData> data = new ArrayList<KMeansData>();
	public KMeansClusterPoint(KMeansData k){
		this.clusterPoint = k;
	}
	public KMeansClusterPoint() {}
	public KMeansData getClusterPoint() {
		return this.clusterPoint;
	}
	public void setClusterPoint(KMeansData clusterPoint) {
		this.clusterPoint = clusterPoint;
	}
	public ArrayList<KMeansData> getData() {
		return this.data;
	}
	public void setData(ArrayList<KMeansData> data) {
		this.data = data;
	}
	
	public void addData(KMeansData data) {
		this.data.add(data);
	}
	
	@Override
	public String toString() {
		return "KMeansClusterPoint [clusterPoint=" + clusterPoint + ", data=" + data + "]";
	}
	
	
}
