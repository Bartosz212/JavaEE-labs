import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean()
@SessionScoped()
public class AdvertismentSession {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}