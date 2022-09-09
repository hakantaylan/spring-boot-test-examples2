package com.example.nplusone;

import com.example.nplusone.config.TestDataSourceConfig;
import com.example.nplusone.model.Appointment;
import com.example.nplusone.model.Doctor;
import com.example.nplusone.model.Patient;
import com.example.nplusone.repository.DoctorRepository;
import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.assertj.core.api.Assertions;
import org.hibernate.annotations.QueryHints;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Import(TestDataSourceConfig.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NPlus1DetectionTest2 {

    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private EntityManager entityManager;

    private TransactionTemplate transactionTemplate;

    private static final int N = 2; //because we have 2 OneToMany relations

    @BeforeAll
    void setUp() {
        transactionTemplate = new TransactionTemplate(transactionManager);
        List<Doctor> doctors = new LinkedList<>();
        for (int i = 1; i < 4; i++) {
            Doctor doctor = new Doctor();
            doctor.setName("House" + i);
            doctor.addAppointment(new Appointment());
            doctor.addAppointment(new Appointment());
            doctor.addAppointment(new Appointment());
            doctor.addAppointment(new Appointment());
            doctor.addAppointment(new Appointment());
            doctor.addPatient(new Patient("P1"));
            doctor.addPatient(new Patient("P2"));
            doctor.addPatient(new Patient("P3"));
            doctor.addPatient(new Patient("P4"));
            doctor.addPatient(new Patient("P5"));
            doctors.add(doctor);
        }
        doctorRepository.saveAll(doctors);

    }

    @BeforeEach
    public void beforeEach() {
        QueryCountHolder.clear();
    }

    @AfterAll
    void cleanUp() {
        doctorRepository.deleteAll();
    }

    @Test
    public void testFindById() {
        transactionTemplate.execute(status -> {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("Look at logs printed to console to examine how many select clauses executed during test");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            Optional<Doctor> byId = doctorRepository.findById(1L);
            List<Doctor> doctors = List.of(byId.get());
            for (Doctor doctor : doctors) {
                assertThat(doctor.getAppointments()).hasSizeGreaterThan(0);
                assertThat(doctor.getPatients()).hasSizeGreaterThan(0);
            }
            return doctors;
        });

        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long selectCount = queryCount.getSelect();
//		Assertions.assertThat(selectCount).isEqualTo(N +1);  // For fetch = LAZY
        Assertions.assertThat(selectCount).isEqualTo(1);  // For fetch = EAGER
    }

    @Test
    public void testFindAll() {
        transactionTemplate.execute(status -> {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("Look at logs printed to console to examine how many select clauses executed during test");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            List<Doctor> doctors = doctorRepository.findAll();
            for (Doctor doctor : doctors) {
                assertThat(doctor.getAppointments()).hasSizeGreaterThan(0);
                assertThat(doctor.getPatients()).hasSizeGreaterThan(0);
            }
            return doctors;
        });

        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long selectCount = queryCount.getSelect();
//		assertThat(selectCount).isEqualTo(3*N + 1);  // For fetch = LAZY list returns 3 records with N relations
        assertThat(selectCount).isEqualTo(3 * N + 1);  // For fetch = EAGER list returns 3 records with N relations

    }

    @Test
    void testQueryMethod1() {
        List<Doctor> doctors = entityManager.createQuery("select d from Doctor d", Doctor.class).getResultList();
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long selectCount = queryCount.getSelect();
//		assertThat(selectCount).isEqualTo(1);       // For fetch = LAZY does not load lazy relations only loads main entity
        assertThat(selectCount).isEqualTo(3 * N + 1); // For fetch = EAGER list returns 3 records with N relations
    }

    @Test
    public void testQueryMethod() {
        transactionTemplate.execute(status -> {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("Look at logs printed to console to examine how many select clauses executed during test");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            List<Doctor> doctors = entityManager.createQuery("select d from Doctor d", Doctor.class).getResultList();
            for (Doctor doctor : doctors) {
                assertThat(doctor.getAppointments()).hasSizeGreaterThan(0);
                assertThat(doctor.getPatients()).hasSizeGreaterThan(0);
            }
            return doctors;
        });

        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long selectCount = queryCount.getSelect();
//		assertThat(selectCount).isEqualTo(3*N + 1);  // For fetch = LAZY list returns 3 records with N relations
        assertThat(selectCount).isEqualTo(3 * N + 1);  // For fetch = EAGER list returns 3 records with N relations

    }

    @Test
    public void testQueryWithJoinFetch() {
        transactionTemplate.execute(status -> {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("Look at logs printed to console to examine how many select clauses executed during test");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            TypedQuery<Doctor> query = entityManager.createQuery("select d from Doctor d left join fetch d.appointments left join fetch d.patients", Doctor.class);
            List<Doctor> doctors = query.getResultList();
            for (Doctor doctor : doctors) {
                assertThat(doctor.getAppointments()).hasSizeGreaterThan(0);
                assertThat(doctor.getPatients()).hasSizeGreaterThan(0);
            }
            return doctors;
        });

        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long selectCount = queryCount.getSelect();
//		assertThat(selectCount).isEqualTo(1);  // For fetch = LAZY list returns 3 records with N relations
        assertThat(selectCount).isEqualTo(1);  // For fetch = EAGER list returns 3 records with N relations

    }

    @Test
    public void testQueryWithDistinct() {
        transactionTemplate.execute(status -> {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("Look at logs printed to console to examine how many select clauses executed during test");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            TypedQuery<Doctor> query = entityManager.createQuery("select distinct d from Doctor d left join fetch d.appointments left join fetch d.patients", Doctor.class);
            query.setHint(QueryHints.PASS_DISTINCT_THROUGH, false);
            List<Doctor> doctors = query.getResultList();
            for (Doctor doctor : doctors) {
                assertThat(doctor.getAppointments()).hasSizeGreaterThan(0);
                assertThat(doctor.getPatients()).hasSizeGreaterThan(0);
            }
            return doctors;
        });

        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long selectCount = queryCount.getSelect();
//		assertThat(selectCount).isEqualTo(1);  // For fetch = LAZY list returns 3 records with N relations
        assertThat(selectCount).isEqualTo(1);  // For fetch = EAGER list returns 3 records with N relations

    }
}
