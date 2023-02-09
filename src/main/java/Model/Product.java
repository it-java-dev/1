package Model;


import javax.persistence.*;
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String productName;
    private String company;
    @Column(nullable = false)
    private Integer price;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public Product() {
    }

    public Product(String productName, String company, Integer price) {
        this.productName = productName;
        this.company = company;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!getId().equals(product.getId())) return false;
        if (!getProductName().equals(product.getProductName())) return false;
        if (getCompany() != null ? !getCompany().equals(product.getCompany()) : product.getCompany() != null)
            return false;
        if (!getPrice().equals(product.getPrice())) return false;
        return getClient().equals(product.getClient());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getProductName().hashCode();
        result = 31 * result + (getCompany() != null ? getCompany().hashCode() : 0);
        result = 31 * result + getPrice().hashCode();
        result = 31 * result + getClient().hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(id);
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", company='").append(company).append('\'');
        sb.append(", price=").append(price);
        sb.append(", client=").append(client);
        sb.append('}');
        return sb.toString();
    }
}