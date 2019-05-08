import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class TestGraph {
	public static void main(String[] args) throws IOException {
		URL url = new URL("https://doc.itc.rwth-aachen.de/download/attachments/5800152/adjazenzlisten2.txt");
		URLConnection con = url.openConnection();
		InputStream content = (InputStream)con.getContent();
		
		Graph graph = new Graph(content);
			
//		Graph g = new Graph(new int[]{6,10,1,5,1,4,2,3,2,6,3,4,3,5,4,5,4,2,5,6,6,1});
//		System.out.println(g);
//		System.out.println(g.dfs(1));
//		System.out.println(g.bfs(1));
	}
}
