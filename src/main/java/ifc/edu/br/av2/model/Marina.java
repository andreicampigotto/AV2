package ifc.edu.br.av2.model;

public class Marina {
    
    private long id;
    private int totalVagas;

    public Marina(int totalVagas) {
        this.totalVagas = totalVagas;
    }

    public Marina() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTotalVagas() {
        return totalVagas;
    }

    public void setTotalVagas(int totalVagas) {
        this.totalVagas = totalVagas;
    }
    
}
