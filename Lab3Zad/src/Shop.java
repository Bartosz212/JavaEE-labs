import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.IntStream;

@ManagedBean(name = "shop")
@SessionScoped
public class Shop {

    private String name;
    private String mail;
    private Integer age;
    private String sex = null;
    private String education;
    private Integer height;

    private Integer bust;
    private String cup;
    private Integer hips;
    private Integer waistFemale;

    private Integer chest;
    private Integer waistMale;

    private String question1;
    private String question2;
    private String question3;
    private String[] question4;

    private String[] question4AnsFemale = {"garsonki","bluzki","spódniczki","spodnie"};
    private String[] question4AnsMale = {"spodnie","spodenki","garnitury","koszule","krawaty"};

    private boolean valid = false;

    public String getQuestion4String(){
        return Arrays.toString(question4);
    }

    public String[] getQuestion4() {
        return question4;
    }

    public void setQuestion4(String[] question4) {
        this.question4 = question4;
    }

    public String[] getQuestion4AnsFemale() {
        return question4AnsFemale;
    }

    public void setQuestion4AnsFemale(String[] question4AnsFemale) {
        this.question4AnsFemale = question4AnsFemale;
    }

    public String[] getQuestion4AnsMale() {
        return question4AnsMale;
    }

    public void setQuestion4AnsMale(String[] question4AnsMale) {
        this.question4AnsMale = question4AnsMale;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    public boolean getValid(){
        return valid;
    }

    public void isValid(){
        setValid(true);
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }


    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getBust() {
        return bust;
    }

    public void setBust(Integer bust) {
        this.bust = bust;
    }

    public Integer getHips() {
        return hips;
    }

    public void setHips(Integer hips) {
        this.hips = hips;
    }

    public Integer getWaistFemale() {
        return waistFemale;
    }

    public void setWaistFemale(Integer waistFemale) {
        this.waistFemale = waistFemale;
    }

    public Integer getChest() {
        return chest;
    }

    public void setChest(Integer chest) {
        this.chest = chest;
    }

    public Integer getWaistMale() {
        return waistMale;
    }

    public void setWaistMale(Integer waistMale) {
        this.waistMale = waistMale;
    }
}
