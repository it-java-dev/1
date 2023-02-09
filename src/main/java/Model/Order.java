package Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private List<Client> ordersList = new ArrayList<>();

    public Order() {
    }

    public void addOrderToList(Client client) {
        if (!ordersList.contains(client)) {
            ordersList.add(client);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Client> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Client> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!getId().equals(order.getId())) return false;
        return getOrdersList() != null ? getOrdersList().equals(order.getOrdersList()) : order.getOrdersList() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getOrdersList() != null ? getOrdersList().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", ordersList=").append(ordersList);
        sb.append('}');
        return sb.toString();
    }
}
