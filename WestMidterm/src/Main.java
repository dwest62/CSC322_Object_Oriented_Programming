import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

public class Main {
    
    public static void main(String[] args) {
    
//      Create PrintWriter, loop 1000 times and write randomized faculty member details to file.
        try (PrintWriter p = new PrintWriter("FacultySalaries.txt")) {
            for (int i = 0; i < 1000; i++) {
                p.println(getRandomFacultyMember());
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
/**
* Gets random salary from specific faculty subclass salary range
 * @param faculty faculty to get salary from
 * @return random salary within faculty range
*/
    public static double getRandomSalary(FacultyMember faculty) {
        SalaryRange salaryRange = faculty.getSalaryRange();
        double lower = salaryRange.getLowerBound();
        double upper = salaryRange.getUpperBound();
        double difference = upper - lower;
        return (Math.random() * difference) + lower;
    }
    
/**
* Gets random faculty member of type AssociateProfessor, AssistantProfessor, or FullProfessor. Note: there may be a way
 * to reduce duplicate code here.
*/
    public static FacultyMember getRandomFacultyMember() throws InvocationTargetException, NoSuchMethodException,
                                                                    InstantiationException, IllegalAccessException {
        final String FNAME = "FirstName" + FacultyMember.n;
        final String LNAME = "LastName" + FacultyMember.n;
        int rand  = (int) Math.round(Math.random() * 2);
        
        System.out.println(getInstanceOf(AssociateProfessor.class));
        return getInstanceOf(AssociateProfessor.class);
        
//        switch (rand) {
//            case 0 -> {
//                return new AssociateProfessor(FNAME, LNAME);
//            }
//            case 1 -> {
//                AssistantProfessor assistantProfessor =
//                    new AssistantProfessor(FNAME, LNAME);
//                assistantProfessor.setSalary(getRandomSalary(assistantProfessor));
//                return assistantProfessor;
//            }
//            default -> {
//                FullProfessor fullProfessor =
//                    new FullProfessor(FNAME, LNAME);
//                fullProfessor.setSalary(getRandomSalary(fullProfessor));
//                return fullProfessor;
//            }
//        }
    }
    public static <T> T getInstanceOf(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException,
                                                           InstantiationException, IllegalAccessException {
        
        return clazz.getDeclaredConstructor(String.class, String.class).newInstance("Test", "Test");
    }
    
}