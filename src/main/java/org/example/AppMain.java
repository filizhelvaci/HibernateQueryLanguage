package org.example;

import org.example.model.Customer;
import org.example.model.CustomerDetail;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

//import javax.persistence.Query;
import org.hibernate.query.Query;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AppMain {
    public static void main(String[] args) {

       Customer customer=new Customer();
       customer.setFirstName("Ali");
       customer.setLastName("Akkiraz");

       CustomerDetail customerDetail=new CustomerDetail();
       customerDetail.setAddress("Ankara");
       customerDetail.setPhone("03214568542");
       customerDetail.setRecordDate(new Date());

       customer.setCustomerDetail(customerDetail);
       customerDetail.setCustomer(customer);

       Customer customer1=new Customer("Enes","Sait");
       CustomerDetail customerDetail1=new CustomerDetail("Eskisehir","05468526352",new Date());

       customerDetail1.setCustomer(customer1);
       customer1.setCustomerDetail(customerDetail1);

        Session session= HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;

        try {
            transaction=session.beginTransaction();
            session.save(customer);
            session.save(customer1);

            System.out.println("//----------- Select-------------------");
            Query query=session.createQuery("FROM Customer");
            //List<Customer> customerList=query1.getResultList(); yukarıda import javax.persistence.Query; kullanırsak sorguyu burda bu şekilde kullanabiliriz.
            //yoksa hibernaten kendi dilini kullnaırsak sqli yazarken farklı şekillerde yazmamız gerekir.
            List<Customer> customerList=query.list();
            for (Customer customerr : customerList){
                System.out.println(customerr.getFirstName()+" "+customerr.getLastName());
            }

            System.out.println("//----------- Where-------------------");
            Query query1=session.createQuery("FROM Customer WHERE id=:id");
            query1.setLong("id",1);

            Customer customer4=(Customer) query1.uniqueResult();
            System.out.println(customer4.getFirstName()+" "+customer4.getLastName());

            /*
            System.out.println("//----------- Delete-------------------");
            Query query2=session.createQuery("DELETE FROM Customer WHERE id=:id");
            query2.setLong("id",1);

            int queryResult=query2.executeUpdate();
            System.out.println("Customer delete sonucu: "+queryResult);

            Query query3=session.createQuery("DELETE FROM CustomerDetail WHERE id=:id");
            query3.setLong("id",1);

            int queryyResult=query3.executeUpdate();
            System.out.println("CustomerDetail delete sonucu: "+queryyResult);
*/
            System.out.println("//----------- AVG SUM MIN MAX COUNT-------------------");
            Query query4=session.createQuery("SELECT count(*) FROM Customer");

            Long resultCount=(Long)query4.uniqueResult();
            System.out.println("Count: "+resultCount);

            System.out.println("//----------- JOIN-------------------");
            Query query6 = session.createQuery(
                    "SELECT c.firstname, c.lastname " +
                            "FROM Customer c " +
                            "INNER JOIN CustomerDetail d " +
                            "ON c.customerId = d.customerId");

            List<Customer> customerList6 = query6.list();
            for ( Customer cust6  :  customerList6  ) {
                System.out.println(cust6.getFirstName() + " " + cust6.getLastName() + " " + cust6.getCustomerDetail().getAddress());
            }


            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            System.out.println("Hata: "+e);
        }finally {
            session.close();
        }
    }

}
