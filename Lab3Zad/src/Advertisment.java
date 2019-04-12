import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@ManagedBean
@ApplicationScoped
public class Advertisment {
    private HashMap<String,Integer> clicks = new HashMap<>();
    private List<String> paths = Arrays.asList("https://5.imimg.com/data5/WN/TQ/GLADMIN-24356824/sprite-soft-drink-500x500.png","https://www.leclerc.rzeszow.pl/foto_shop/123/5900497281010_T1.jpg","https://apimarket.pl/466598-thickbox_default/mirinda-orange-napoj-gazowany-225-l.jpg","https://i.pinimg.com/originals/51/90/f2/5190f2e019af3c5e83ba04a23f856fa7.jpg","https://analyzemedia.files.wordpress.com/2013/10/m_pepsi_versiontypo_fullldef_0636.jpg?w=1200&h=800&crop=1","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQlB7KYzqf7qnbrOayVGBadNWjipPLyqwnG8yfUjS2bupJPCNs");
    private String imgPath = paths.get(0);

    public Advertisment() {
        for(String path: paths){
            clicks.put(path,0);
        }
    }

    public HashMap<String, Integer> getClicks() {
        return clicks;
    }

    public void setClicks(HashMap<String, Integer> clicks) {
        this.clicks = clicks;
    }

    public String getImgPath() {
        Random rand = new Random();
        int randNumb = rand.nextInt(paths.size()) + 1;
        imgPath = paths.get(randNumb-1);
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void onClick(String tmpPath){
        Integer val = clicks.get(tmpPath);
        clicks.replace(tmpPath, val, val+1);
    }

    public int numberOfClicks(String tmpPath){
        return clicks.get(tmpPath);
    }
}