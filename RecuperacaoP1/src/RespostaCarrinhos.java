import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespostaCarrinhos {
    private List<Carrinho> carts;
    private int total;
    private int skip;
    private int limit;

    public RespostaCarrinhos() {}

    public List<Carrinho> getCarts() { return carts; }
    public void setCarts(List<Carrinho> carts) { this.carts = carts; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public int getSkip() { return skip; }
    public void setSkip(int skip) { this.skip = skip; }

    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    @Override
    public String toString() {
        return "RespostaCarrinhos{" +
                "carts=" + carts +
                ", total=" + total +
                ", skip=" + skip +
                ", limit=" + limit +
                '}';
    }
}