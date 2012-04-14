#Guice Test Enricher for Arquillian

* Injection of Guice dependencies into test classes
* Support for testing JPA through JpaPersistExtension
* Auto packaging the guice artifacts.

## Code Example

Each Guice enabled test must be annotated with @GuiceConfiguration, the annotion allows to specify
multiple modules which will be used for resolving the injected dependencies

### Guice Module

```
public class AppointmentModule implements Module {

    public void configure(Binder binder) {

        binder.bind(AppointmentRepository.class)
                .annotatedWith(Names.named("appointmentRepository"))
                .to(AppointmentRepositoryImpl.class);
        binder.bind(AppointmentService.class)
                .annotatedWith(Names.named("appointmentService"))
                .to(AppointmentServiceImpl.class);
    }
}
```

Test example

```
@RunWith(Arquillian.class)
@GuiceConfiguration(AppointmentModule.class)
public class AppointmentServiceImplTestCase {

    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "guice-test.jar")
                .addClasses(Appointment.class,
                        AppointmentRepository.class, AppointmentRepositoryImpl.class,
                        AppointmentService.class, AppointmentServiceImpl.class,
                        AppointmentModule.class);
    }

    @Inject
    @Named("appointmentService")
    private AppointmentService appointmentService;

    @Test
    public void testGetAll() {

        Appointment appointment1 = createAppointment("Important", "Work", new Date());
        Appointment appointment2 = createAppointment("Do not forget", "Work", new Date());

        appointmentService.add(appointment1);
        appointmentService.add(appointment2);

        List<Appointment> result = appointmentService.getAll();
        assertNotNull("Method returned null.", result);
        assertEquals("Invalid element count, 2 appointments were expected.", 2, result.size());
    }

    private Appointment createAppointment(String name, String location, Date date) {

        Appointment appointment = new Appointment();
        appointment.setName(name);
        appointment.setLocation(location);
        appointment.setDate(date);
        return appointment;
    }
}
```

## TODO

* Unit tests
* Configuring the extension through arquillian.xml for specifying the Guice artifact version.