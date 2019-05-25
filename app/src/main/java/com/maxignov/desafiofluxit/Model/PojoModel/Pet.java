package com.maxignov.desafiofluxit.Model.PojoModel;

public class Pet {
    private String id;
    private String name;
    private String status;

    public Pet(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  "{ Id: " + getId() +
                "/ Name: " + getName() +
                "/ Status: " + getStatus() +
                "}\n";
    }
}
