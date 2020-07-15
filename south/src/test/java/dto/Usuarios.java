package dto;

public class Usuarios {


    public String nome;
    public String id;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String job;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public Usuarios() {
        this.nome = nome;
        this.id = id;
        this.job=job;
    }
}
