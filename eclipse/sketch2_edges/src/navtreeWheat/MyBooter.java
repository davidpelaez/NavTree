package navtreeWheat;
import java.awt.*;

public class MyBooter extends Frame {
	
	public MyBooter(){
		super("Embedded PApplet");

        setLayout(new BorderLayout());
        NTApplet2D embed = new NTApplet2D();
        add(embed, BorderLayout.CENTER);

        // important to call this whenever embedding a PApplet.
        // It ensures that the animation thread is started and
        // that other internal variables are properly set.
        embed.init();
        this.setSize(1250,600);
        this.show();
	}
	
	public static void main(String[] args){
		MyBooter app = new MyBooter();
	}

}


