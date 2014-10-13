package farmacie.service.impl;

import org.hibernate.proxy.HibernateProxy;

public class ServiceHelper {
    public <T> T unproxy(T entity) {
        if (entity != null) {
                if (entity instanceof HibernateProxy) {
                        return (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
                } else {
                        return entity;
                }
        }
        return entity;
    } 
}
