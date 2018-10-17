package com.github.fangming.springboot.jdbc.service;

import com.github.fangming.springboot.jdbc.common.ConnectionPool;
import com.github.fangming.springboot.jdbc.dao.RegistrationDaoImpl;
import com.github.fangming.springboot.jdbc.model.Registration;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class RegistrationServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    private final RegistrationDaoImpl registrationDao;
    private final ConnectionPool connectionPool;

    @Autowired
    public RegistrationServiceImpl(RegistrationDaoImpl registrationDao, ConnectionPool connectionPool) {
        this.registrationDao = registrationDao;
        this.connectionPool = connectionPool;
    }

    public void initTable() {
        Connection conn = connectionPool.getConnection();
        try {
            registrationDao.initTable(conn);
        } catch (Exception ignored) {

        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    public void create(Registration registration) {
        Connection conn = connectionPool.getConnection();
        try {
            registrationDao.create(conn, registration);
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    public Registration getById(Long id) {
        Connection conn = connectionPool.getConnection();
        Registration registration = null;
        try {
            registration = registrationDao.getById(conn, id);
        } catch (Exception e) {
            logger.error("Get by Id failure.");
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return registration;
    }

    public List<Registration> getAll() {
        Connection conn = connectionPool.getConnection();
        List<Registration> registrations = Lists.newArrayList();
        try {
            registrations.addAll(registrationDao.getAll(conn));
        } catch (Exception e) {
            logger.error("Get by Id failure.");
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return registrations;
    }

    public void update(Registration registration) {
        Connection conn = connectionPool.getConnection();
        try {
            registrationDao.update(conn, registration);
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }


    public String dirtyRead(int transactionIsolationLevel) throws Exception {
        Object[] monitors = {new Object(), new Object()};
        boolean[] states = {false, false};
        StringBuffer result = new StringBuffer();

        Registration registration = this.getById(1L);
        result.append("/*****************************************/<br/>");
        result.append("Initial value: ").append(registration.toString()).append("*/<br/>");
        result.append("/*****************************************/<br/>");

        Thread t1 = new Thread(() -> {
            result.append(Thread.currentThread().getName() + " start...<br/>");
            try {
                synchronized (monitors[0]) {
                    while (!states[0]) {
                        monitors[0].wait();
                    }
                }
                result.append(Thread.currentThread().getName()).append(" Querying data...<br/>");

                Connection queryConn = connectionPool.getConnection();
                Registration middleRegistration = null;
                try {
                    middleRegistration = registrationDao.getById(queryConn, 1L);
                } catch (Exception e) {
                    logger.error("Get by Id failure.");
                } finally {
                    connectionPool.releaseConnection(queryConn);
                }

                result.append("Middle transaction value: ").append(middleRegistration.toString()).append("<br/>");

                synchronized (monitors[1]) {
                    states[1] = true;
                    monitors[1].notifyAll();
                }
                result.append(Thread.currentThread().getName()).append(" done...<br/>");
            } catch (Exception e) {
                System.out.println("some error happen.");
                e.printStackTrace();
            }
        }, "DataQueryThread");

        Thread t2 = new Thread(() -> {
            result.append(Thread.currentThread().getName()).append(" start...<br/>");
            result.append(Thread.currentThread().getName()).append(" updating data...<br/>");

            Registration newRegistration = new Registration(1L, "Jack", "Fang", 28);
            Connection updateConn = connectionPool.getConnection();
            try {
                registrationDao.update(updateConn, newRegistration);
                states[0] = true;
                synchronized (monitors[0]) {
                    monitors[0].notify();
                }
                result.append(Thread.currentThread().getName()).append(" updated data...<br/>");
                synchronized (monitors[1]) {
                    while (!states[1]) {
                        monitors[1].wait();
                    }
                }
                result.append(Thread.currentThread().getName()).append(" rollback...<br/>");
                updateConn.rollback();
            } catch (Exception e) {
                try {
                    updateConn.rollback();
                } catch (SQLException ignored) {
                }
            } finally {
                connectionPool.releaseConnection(updateConn);
            }
            result.append(Thread.currentThread().getName()).append(" done...<br/>");

        }, "DataUpdateThread");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        registration = this.getById(1L);
        result.append("/*****************************************/<br/>");
        result.append("The last value: ").append(registration.toString()).append("*/<br/>");
        result.append("/*****************************************/<br/>");

        result.append("Testing done............<br/>");

        return result.toString();

    }
}
