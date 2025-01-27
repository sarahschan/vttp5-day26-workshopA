package sg.edu.nus.iss.paf_day26_workshopA.model;

public class Game {
    
    private String id;
    private String gid;
    private String name;
    
    
    public Game() {
    }

    public Game(String id, String gid, String name) {
        this.id = id;
        this.gid = gid;
        this.name = name;
    }


    @Override
    public String toString() {
        return "Game [id=" + id + ", gid=" + gid + ", name=" + name + "]";
    }
    

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getGid() {
        return gid;
    }
    public void setGid(String gid) {
        this.gid = gid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
