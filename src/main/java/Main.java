

import Model.Client;
import Model.Order;
import Model.Product;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            emf = Persistence.createEntityManagerFactory("JPAOrder");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: Add client");
                    System.out.println("2: Add product");
                    System.out.println("3: Make order");
                    System.out.println("4: View all orders");
                    System.out.println("5: View all clients and orders");
                    System.out.print("--> ");
                    String result = sc.nextLine();

                    switch (result) {
                        case "1":
                            addClient(sc);
                            break;
                        case "2":
                            addProduct(sc);
                            break;
                        case "3":
                            makeOrder(sc);
                            break;
                        case "4":
                            viewAllOrders();
                            break;
                        case "5":
                            viewAll();
                            break;
                        default:
                            return;
                    }
                }

            } finally {
                emf.close();
                em.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    static void addClient(Scanner sc) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        Client c = new Client(name, email);

        try {
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
            System.out.println("Done your id: " + c.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }


    }

    static void addProduct(Scanner sc) {
        System.out.print("Enter product name: ");
        String productName = sc.nextLine();
        System.out.print("Enter product company: ");
        String company = sc.nextLine();
        System.out.print("Enter price: ");
        String strPrice = sc.nextLine();
        Integer price = Integer.parseInt(strPrice);
        Product product = new Product(productName, company, price);

        try {
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
            System.out.println("Done product id: " + product.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }

    }


    static void makeOrder(Scanner sc) {
        System.out.print("Enter id of client: ");
        Long clientId = Long.valueOf(sc.nextLine());
        Client client = null;
        Order order = new Order();
        boolean check = true;
        while (check) {
            try {
                System.out.print("Enter id of product: ");
                Long productId = Long.valueOf(sc.nextLine());
                TypedQuery<Client> clientQuery = em.createQuery("SELECT c FROM Client c WHERE c.id = :id", Client.class);
                TypedQuery<Product> productQuery = em.createQuery("SELECT p FROM Product p WHERE p.id = :id", Product.class);
                clientQuery.setParameter("id", clientId);
                productQuery.setParameter("id", productId);
                client = clientQuery.getSingleResult();
                Product product = productQuery.getSingleResult();
                client.addProduct(product);

                System.out.println("Add another product?\n");
                System.out.println("1: Yes.");
                System.out.println("2: No.");
                String answer = sc.nextLine();
                if (answer.equals("2")) {
                    check = false;
                }


            } catch (NoResultException nre) {
                System.out.println("There is no such client or product!");
                return;
            } catch (NonUniqueResultException nure) {
                System.out.println("There are more than one such names!");
                return;
            }
        }

        order.addOrderToList(client);
        try {
            em.getTransaction().begin();
            em.persist(client);
            em.persist(order);
            em.getTransaction().commit();
            System.out.println("Done order id: " + order.getId());
        } catch (Exception e) {
            em.getTransaction().rollback();
        }

    }

    static void viewAllOrders() {
        Query query = em.createQuery("SELECT o FROM Order o", Order.class);
        List<Order> listOrders = query.getResultList();

        for (Order order : listOrders) {
            System.out.println(order);
        }
    }

    static void viewAll() {
        Query queryClient = em.createQuery("SELECT c FROM Client c", Client.class);
        Query queryProduct = em.createQuery("SELECT p FROM Product p", Product.class);
        List<Client> listOfClients = queryClient.getResultList();
        List<Product> listOfProducts = queryProduct.getResultList();


        for (Client c : listOfClients) {
            System.out.println(c);
        }

        for (Product p : listOfProducts) {
            System.out.println(p);
        }


    }
}
