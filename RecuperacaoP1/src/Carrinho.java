import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Carrinho {
    private int id;
    private int userId;
    private double total;
    private double discountedTotal;
    private int totalProducts;
    private int totalQuantity;
    private List<ProdutoCarrinho> products;

    public Carrinho() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public double getDiscountedTotal() { return discountedTotal; }
    public void setDiscountedTotal(double discountedTotal) { this.discountedTotal = discountedTotal; }

    public int getTotalProducts() { return totalProducts; }
    public void setTotalProducts(int totalProducts) { this.totalProducts = totalProducts; }

    public int getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }

    public List<ProdutoCarrinho> getProducts() { return products; }
    public void setProducts(List<ProdutoCarrinho> products) { this.products = products; }

    public double getEconomia() {
        return total - discountedTotal;
    }

    @Override
    public String toString() {
        return "Carrinho{" +
                "id=" + id +
                ", userId=" + userId +
                ", total=" + total +
                ", discountedTotal=" + discountedTotal +
                ", totalProducts=" + totalProducts +
                ", totalQuantity=" + totalQuantity +
                ", products=" + products +
                '}';
    }
}