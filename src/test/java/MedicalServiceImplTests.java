import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class MedicalServiceImplTests {
    @BeforeAll
    public static void started() {
        System.out.println("Tests started");
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println("Tests completed");
    }

    @BeforeEach
    public void init() {
        System.out.println("Test started");
    }

    @AfterEach
    public void finished() {
        System.out.println("Test completed");
    }

    @Test
    public void testBloodPressureIsNormal() {
        PatientInfo patientInfo = new PatientInfo(UUID.randomUUID().toString(),
                "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));

        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById(patientInfo.getId())).thenReturn(patientInfo);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        Mockito.doNothing().when(alertService).send(message);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkBloodPressure(patientInfo.getId(), new BloodPressure(120, 80));

        Mockito.verify(alertService, Mockito.times(0)).send(message);
    }

    @Test
    public void testBloodPressureIsNotNormal() {
        PatientInfo patientInfo = new PatientInfo(UUID.randomUUID().toString(),
                "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));

        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById(patientInfo.getId())).thenReturn(patientInfo);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        Mockito.doNothing().when(alertService).send(message);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkBloodPressure(patientInfo.getId(), new BloodPressure(90, 40));

        Mockito.verify(alertService, Mockito.times(1)).send(message);
    }

    @Test
    void testTemperatureIsNormal() {
        PatientInfo patientInfo = new PatientInfo(UUID.randomUUID().toString(),
                "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));

        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById(patientInfo.getId())).thenReturn(patientInfo);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        Mockito.doNothing().when(alertService).send(message);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkTemperature(patientInfo.getId(), new BigDecimal("36.6"));

        Mockito.verify(alertService, Mockito.times(0)).send(message);
    }

    @Test
    void testTemperatureIsNotNormal() {
        PatientInfo patientInfo = new PatientInfo(UUID.randomUUID().toString(),
                "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));

        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById(patientInfo.getId())).thenReturn(patientInfo);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        Mockito.doNothing().when(alertService).send(message);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkTemperature(patientInfo.getId(), new BigDecimal("32.5"));

        Mockito.verify(alertService, Mockito.times(1)).send(message);
    }
}
