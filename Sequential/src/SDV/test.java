package SDV;

import net.sf.sdedit.Main;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//-o /tmp/bfs.pdf -t pdf /tmp/examples/bfs.sd

		Main sdedit = new Main();
		try {
			sdedit.main(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
