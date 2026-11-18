package com.auca.main;

import com.auca.abstracts.AcademicMember;
import com.auca.abstracts.Person;
import com.auca.administrator.Administrator;
import com.auca.assessment.Assessment;
import com.auca.bookloan.BookLoan;
import com.auca.classroom.Classroom;
import com.auca.course.Course;
import com.auca.department.Department;
import com.auca.enrollment.Enrollment;
import com.auca.exceptions.BookUnavailableException;
import com.auca.exceptions.DuplicateRecordException;
import com.auca.exceptions.PrerequisiteMissingException;
import com.auca.exceptions.RecordNotFoundException;
import com.auca.exceptions.ValidationException;
import com.auca.feepayment.FeePayment;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;
import com.auca.teacher.Teacher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static final int DEPARTMENT = 1;
    static final int STUDENT = 2;
    static final int TEACHER = 3;
    static final int ADMINISTRATOR = 4;
    static final int COURSE = 5;
    static final int CLASSROOM = 6;
    static final int ENROLLMENT = 7;
    static final int ASSESSMENT = 8;
    static final int PAYMENT = 9;
    static final int BOOK = 10;
    static final int LOAN = 11;

    static final String[] NAMES = {"", "Department", "Student", "Teacher", "Administrator",
            "Course", "Classroom", "Enrollment", "Assessment", "Fee payment",
            "Library book", "Book loan"};

    static final String[] KINDS = {"", "TTTTTD", "TTTTTTTSTCTTTTTTTC", "TTTTTTTSTCTTTTTTTC",
            "TTTTTTTTTTTC", "TTTTSS", "TTTTTC", "TSSTCC", "TSCTTT", "TSTTCT", "TTTTTT", "TSSTTD"};

    static final int[][] STEP_STARTS = {{}, {0, 2, 4}, {0, 6, 12}, {0, 6, 12}, {0, 6, 9},
            {0, 2, 4}, {0, 2, 4}, {0, 2, 4}, {0, 2, 4}, {0, 2, 4}, {0, 2, 4}, {0, 2, 4}};

    static final String[][] STEP_TITLES = {
            {},
            {"Basic details", "Location details", "Contact details"},
            {"Personal details", "Academic details", "Student details"},
            {"Personal details", "Academic details", "Teacher details"},
            {"Personal details", "Staff details", "Employment details"},
            {"Course identity", "Course details", "Course relations"},
            {"Room identity", "Room location", "Capacity details"},
            {"Identity & student", "Course & date", "Semester & status"},
            {"Identity & enrollment", "Type & maximum", "Result & date"},
            {"Identity & student", "Amount & date", "Method & receipt"},
            {"Identity & ISBN", "Title & author", "Copies"},
            {"Identity & student", "Book & borrow date", "Due & return"}};

    static final String[] YES_NO = {"YES", "NO"};
    static final String[] ACADEMIC_STATUS = {"ACTIVE", "INACTIVE"};
    static final String[] EMPLOYMENT_TYPES = {"FULL_TIME", "PART_TIME", "CONTRACT"};
    static final String[] ENROLLMENT_STATUS = {"ACTIVE", "COMPLETED", "CANCELLED"};
    static final String[] SEMESTERS = {"FALL", "SPRING", "SUMMER"};
    static final String[] ASSESSMENT_TYPES = {"QUIZ", "ASSIGNMENT", "MIDTERM", "FINAL_EXAM", "PROJECT"};
    static final String[] PAYMENT_METHODS = {"CASH", "MOBILE_MONEY", "BANK_TRANSFER", "CARD"};

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Department> departments = new ArrayList<>();
    static ArrayList<Student> students = new ArrayList<>();
    static ArrayList<Teacher> teachers = new ArrayList<>();
    static ArrayList<Administrator> administrators = new ArrayList<>();
    static ArrayList<Course> courses = new ArrayList<>();
    static ArrayList<Classroom> classrooms = new ArrayList<>();
    static ArrayList<Enrollment> enrollments = new ArrayList<>();
    static ArrayList<Assessment> assessments = new ArrayList<>();
    static ArrayList<FeePayment> payments = new ArrayList<>();
    static ArrayList<LibraryBook> books = new ArrayList<>();
    static ArrayList<BookLoan> loans = new ArrayList<>();

    static String lastError = "";

    public static void main(String[] args) {

        int choice;

        do {
            displayMenu();
            choice = readInt("Select an option: ");

            try {
                runMenuChoice(choice);
            } catch (ValidationException | DuplicateRecordException
                     | RecordNotFoundException | PrerequisiteMissingException
                     | BookUnavailableException e) {
                System.out.println("\n! " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("\n! Unexpected problem: " + e.getMessage());
                System.out.println("  Returning to the main menu.");
            }
        } while (choice != 0);
    }

    public static void runMenuChoice(int choice)
            throws ValidationException, DuplicateRecordException,
            RecordNotFoundException, PrerequisiteMissingException,
            BookUnavailableException {

        switch (choice) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                registerFromMenu(choice);
                break;
            case 12:
                returnBook();
                break;
            case 13:
                viewAllRecords();
                break;
            case 14:
                searchById();
                break;
            case 15:
                updateRecord();
                break;
            case 16:
                showStudentAcademicSummary();
                break;
            case 17:
                showPaymentHistory();
                break;
            case 18:
                showAvailableBooks();
                break;
            case 19:
                demonstratePolymorphism();
                break;
            case 0:
                System.out.println("\nThank you for using AUCA_ID School Management System.");
                break;
            default:
                System.out.println("\n! Invalid menu choice. Enter a number from 0 to 19.");
        }
    }

    public static void displayMenu() {

        System.out.println();
        System.out.println("============== AUCA_ID SCHOOL MANAGEMENT ==============");
        System.out.println(String.format("%-34s%s", " 1. Register Department", "11. Borrow Library Book"));
        System.out.println(String.format("%-34s%s", " 2. Register Student", "12. Return Library Book"));
        System.out.println(String.format("%-34s%s", " 3. Register Teacher", "13. View All Records"));
        System.out.println(String.format("%-34s%s", " 4. Register Administrator", "14. Search Record by ID"));
        System.out.println(String.format("%-34s%s", " 5. Register Course", "15. Update Existing Record"));
        System.out.println(String.format("%-34s%s", " 6. Register Classroom", "16. Student Academic Summary"));
        System.out.println(String.format("%-34s%s", " 7. Enroll Student", "17. Student Payment History"));
        System.out.println(String.format("%-34s%s", " 8. Record Assessment", "18. View Available Books"));
        System.out.println(String.format("%-34s%s", " 9. Record Fee Payment", "19. Demonstrate Polymorphism"));
        System.out.println(String.format("%-34s%s", "10. Register Library Book", " 0. Exit"));
    }

    public static String readString(String message) {
        System.out.print(message);

        if (!sc.hasNextLine()) {
            System.out.println();
            System.out.println("Input ended. Closing the application.");
            System.exit(0);
        }

        return sc.nextLine().trim();
    }

    public static int readInt(String message) {
        while (true) {
            String input = readString(message);

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("! Please enter a valid whole number.");
            }
        }
    }

    public static int readNumber(String message) {
        String input = readString(message);

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String two(int number) {
        return String.format("%02d", number);
    }

    public static String yesNo(boolean value) {
        if (value) {
            return "YES";
        }
        return "NO";
    }

    public static String[] copyOf(String[] source) {
        String[] result = new String[source.length];

        for (int i = 0; i < source.length; i++) {
            result[i] = source[i];
        }

        return result;
    }

    public static String[] merge(String[] first, String[] second) {
        String[] result = new String[first.length + second.length];

        for (int i = 0; i < first.length; i++) {
            result[i] = first[i];
        }

        for (int i = 0; i < second.length; i++) {
            result[first.length + i] = second[i];
        }

        return result;
    }

    public static String shortLabel(String label) {
        int position = label.indexOf(" [");

        if (position >= 0) {
            return label.substring(0, position);
        }

        return label;
    }

    public static String boxLine(String text) {
        return String.format("| %-58s |", text);
    }

    public static ArrayList<Identifiable> recordsOfType(int type) {

        ArrayList<Identifiable> list = new ArrayList<>();

        switch (type) {
            case DEPARTMENT:
                list.addAll(departments);
                break;
            case STUDENT:
                list.addAll(students);
                break;
            case TEACHER:
                list.addAll(teachers);
                break;
            case ADMINISTRATOR:
                list.addAll(administrators);
                break;
            case COURSE:
                list.addAll(courses);
                break;
            case CLASSROOM:
                list.addAll(classrooms);
                break;
            case ENROLLMENT:
                list.addAll(enrollments);
                break;
            case ASSESSMENT:
                list.addAll(assessments);
                break;
            case PAYMENT:
                list.addAll(payments);
                break;
            case BOOK:
                list.addAll(books);
                break;
            case LOAN:
                list.addAll(loans);
                break;
            default:
                break;
        }

        return list;
    }

    public static ArrayList<Identifiable> getAllRecords() {

        ArrayList<Identifiable> records = new ArrayList<>();

        for (int type = DEPARTMENT; type <= LOAN; type++) {
            records.addAll(recordsOfType(type));
        }

        return records;
    }

    public static ArrayList<Person> getAllPeople() {

        ArrayList<Person> people = new ArrayList<>();

        people.addAll(students);
        people.addAll(teachers);
        people.addAll(administrators);

        return people;
    }

    public static Identifiable findRecord(int type, String id) {

        for (Identifiable record : recordsOfType(type)) {
            if (record.getId().equalsIgnoreCase(id)) {
                return record;
            }
        }

        return null;
    }

    public static Department findDepartment(String id) {
        return (Department) findRecord(DEPARTMENT, id);
    }

    public static Student findStudent(String id) {
        return (Student) findRecord(STUDENT, id);
    }

    public static Teacher findTeacher(String id) {
        return (Teacher) findRecord(TEACHER, id);
    }

    public static Course findCourse(String id) {
        return (Course) findRecord(COURSE, id);
    }

    public static Enrollment findEnrollment(String id) {
        return (Enrollment) findRecord(ENROLLMENT, id);
    }

    public static LibraryBook findBook(String id) {
        return (LibraryBook) findRecord(BOOK, id);
    }

    public static String label(int type, Identifiable record) {

        String id = "[" + record.getId() + "] ";

        switch (type) {
            case STUDENT:
                Student student = (Student) record;
                return id + student.getDisplayName() + " (" + student.getStudentNumber() + ")";
            case TEACHER:
                Teacher teacher = (Teacher) record;
                return id + teacher.getDisplayName() + " - " + teacher.getDepartment().getDepartmentName();
            case COURSE:
                Course course = (Course) record;
                return id + course.getCourseCode() + " - " + course.getCourseTitle();
            case ENROLLMENT:
                Enrollment enrollment = (Enrollment) record;
                return id + enrollment.getStudent().getDisplayName() + " - "
                        + enrollment.getCourse().getCourseTitle()
                        + " (" + enrollment.getSemester() + ", " + enrollment.getEnrollmentStatus() + ")";
            case BOOK:
                LibraryBook book = (LibraryBook) record;
                return id + book.getTitle() + " (" + book.getAvailableCopies() + " available)";
            case LOAN:
                BookLoan loan = (BookLoan) record;
                return id + loan.getStudent().getDisplayName() + " - " + loan.getBook().getTitle()
                        + " (due " + loan.getDueDate() + ")";
            default:
                return id + record.getDisplayName();
        }
    }

    public static String describe(int type, String id) {

        Identifiable record = findRecord(type, id);

        if (record == null) {
            return "(none)";
        }

        return label(type, record);
    }

    public static String[] valuesOf(int type, Identifiable record) {

        switch (type) {
            case DEPARTMENT:
                Department department = (Department) record;
                String head = "";
                if (department.getHeadTeacher() != null) {
                    head = department.getHeadTeacher().getId();
                }
                return new String[]{department.getDepartmentId(), department.getDepartmentName(),
                        department.getDepartmentCode(), department.getOfficeLocation(),
                        department.getContactEmail(), head};
            case STUDENT:
                Student student = (Student) record;
                return new String[]{student.getPersonId(), student.getFirstName(), student.getLastName(),
                        student.getEmail(), student.getPhoneNumber(), student.getDateOfBirth().toString(),
                        student.getMemberNumber(), student.getDepartment().getDepartmentId(),
                        student.getJoinDate().toString(), student.getAcademicStatus(),
                        student.getInstitutionalEmail(), student.getAdvisorName(),
                        student.getStudentNumber(), student.getProgramName(),
                        student.getAdmissionDate().toString(), String.valueOf(student.getStudyYear()),
                        student.getGuardianPhone(), yesNo(student.isScholarshipStatus())};
            case TEACHER:
                Teacher teacher = (Teacher) record;
                return new String[]{teacher.getPersonId(), teacher.getFirstName(), teacher.getLastName(),
                        teacher.getEmail(), teacher.getPhoneNumber(), teacher.getDateOfBirth().toString(),
                        teacher.getMemberNumber(), teacher.getDepartment().getDepartmentId(),
                        teacher.getJoinDate().toString(), teacher.getAcademicStatus(),
                        teacher.getInstitutionalEmail(), teacher.getAdvisorName(),
                        teacher.getEmployeeNumber(), teacher.getSpecialization(),
                        teacher.getHireDate().toString(), teacher.getSalary().toPlainString(),
                        teacher.getOfficeNumber(), teacher.getEmploymentType()};
            case ADMINISTRATOR:
                Administrator administrator = (Administrator) record;
                return new String[]{administrator.getPersonId(), administrator.getFirstName(),
                        administrator.getLastName(), administrator.getEmail(),
                        administrator.getPhoneNumber(), administrator.getDateOfBirth().toString(),
                        administrator.getStaffNumber(), administrator.getJobTitle(),
                        administrator.getAssignedOffice(), administrator.getEmploymentDate().toString(),
                        String.valueOf(administrator.getAccessLevel()), yesNo(administrator.isActive())};
            case COURSE:
                Course course = (Course) record;
                return new String[]{course.getCourseId(), course.getCourseCode(), course.getCourseTitle(),
                        String.valueOf(course.getCreditHours()), course.getDepartment().getDepartmentId(),
                        course.getAssignedTeacher().getId()};
            case CLASSROOM:
                Classroom classroom = (Classroom) record;
                return new String[]{classroom.getClassroomId(), classroom.getBuildingName(),
                        classroom.getRoomNumber(), String.valueOf(classroom.getCapacity()),
                        String.valueOf(classroom.getFloorNumber()), yesNo(classroom.isAvailable())};
            case ENROLLMENT:
                Enrollment enrollment = (Enrollment) record;
                return new String[]{enrollment.getEnrollmentId(), enrollment.getStudent().getId(),
                        enrollment.getCourse().getId(), enrollment.getEnrollmentDate().toString(),
                        enrollment.getSemester(), enrollment.getEnrollmentStatus()};
            case ASSESSMENT:
                Assessment assessment = (Assessment) record;
                return new String[]{assessment.getAssessmentId(), assessment.getEnrollment().getId(),
                        assessment.getAssessmentType(), String.valueOf(assessment.getMaximumScore()),
                        String.valueOf(assessment.getObtainedScore()),
                        assessment.getAssessmentDate().toString()};
            case PAYMENT:
                FeePayment payment = (FeePayment) record;
                return new String[]{payment.getPaymentId(), payment.getStudent().getId(),
                        payment.getAmount().toPlainString(), payment.getPaymentDate().toString(),
                        payment.getPaymentMethod(), payment.getReceiptNumber()};
            case BOOK:
                LibraryBook book = (LibraryBook) record;
                return new String[]{book.getBookId(), book.getIsbn(), book.getTitle(), book.getAuthor(),
                        String.valueOf(book.getTotalCopies()), String.valueOf(book.getAvailableCopies())};
            default:
                BookLoan loan = (BookLoan) record;
                String returned = "";
                if (loan.getReturnedDate() != null) {
                    returned = loan.getReturnedDate().toString();
                }
                return new String[]{loan.getLoanId(), loan.getStudent().getId(), loan.getBook().getId(),
                        loan.getBorrowedDate().toString(), loan.getDueDate().toString(), returned};
        }
    }

    public static void applyValues(int type, Identifiable record, String[] v) {

        switch (type) {
            case DEPARTMENT:
                Department department = (Department) record;
                department.setDepartmentName(v[1]);
                department.setDepartmentCode(v[2]);
                department.setOfficeLocation(v[3]);
                department.setContactEmail(v[4]);
                department.setHeadTeacher(findTeacher(v[5]));
                break;
            case STUDENT:
                Student student = (Student) record;
                student.setFirstName(v[1]);
                student.setLastName(v[2]);
                student.setEmail(v[3]);
                student.setPhoneNumber(v[4]);
                student.setDateOfBirth(LocalDate.parse(v[5]));
                student.setMemberNumber(v[6]);
                student.setDepartment(findDepartment(v[7]));
                student.setJoinDate(LocalDate.parse(v[8]));
                student.setAcademicStatus(v[9]);
                student.setInstitutionalEmail(v[10]);
                student.setAdvisorName(v[11]);
                student.setStudentNumber(v[12]);
                student.setProgramName(v[13]);
                student.setAdmissionDate(LocalDate.parse(v[14]));
                student.setStudyYear(Integer.parseInt(v[15]));
                student.setGuardianPhone(v[16]);
                student.setScholarshipStatus(v[17].equals("YES"));
                break;
            case TEACHER:
                Teacher teacher = (Teacher) record;
                teacher.setFirstName(v[1]);
                teacher.setLastName(v[2]);
                teacher.setEmail(v[3]);
                teacher.setPhoneNumber(v[4]);
                teacher.setDateOfBirth(LocalDate.parse(v[5]));
                teacher.setMemberNumber(v[6]);
                teacher.setDepartment(findDepartment(v[7]));
                teacher.setJoinDate(LocalDate.parse(v[8]));
                teacher.setAcademicStatus(v[9]);
                teacher.setInstitutionalEmail(v[10]);
                teacher.setAdvisorName(v[11]);
                teacher.setEmployeeNumber(v[12]);
                teacher.setSpecialization(v[13]);
                teacher.setHireDate(LocalDate.parse(v[14]));
                teacher.setSalary(new BigDecimal(v[15]));
                teacher.setOfficeNumber(v[16]);
                teacher.setEmploymentType(v[17]);
                break;
            case ADMINISTRATOR:
                Administrator administrator = (Administrator) record;
                administrator.setFirstName(v[1]);
                administrator.setLastName(v[2]);
                administrator.setEmail(v[3]);
                administrator.setPhoneNumber(v[4]);
                administrator.setDateOfBirth(LocalDate.parse(v[5]));
                administrator.setStaffNumber(v[6]);
                administrator.setJobTitle(v[7]);
                administrator.setAssignedOffice(v[8]);
                administrator.setEmploymentDate(LocalDate.parse(v[9]));
                administrator.setAccessLevel(Integer.parseInt(v[10]));
                administrator.setActive(v[11].equals("YES"));
                break;
            case COURSE:
                Course course = (Course) record;
                course.setCourseCode(v[1]);
                course.setCourseTitle(v[2]);
                course.setCreditHours(Integer.parseInt(v[3]));
                course.setDepartment(findDepartment(v[4]));
                course.setAssignedTeacher(findTeacher(v[5]));
                break;
            case CLASSROOM:
                Classroom classroom = (Classroom) record;
                classroom.setBuildingName(v[1]);
                classroom.setRoomNumber(v[2]);
                classroom.setCapacity(Integer.parseInt(v[3]));
                classroom.setFloorNumber(Integer.parseInt(v[4]));
                classroom.setAvailable(v[5].equals("YES"));
                break;
            case ENROLLMENT:
                Enrollment enrollment = (Enrollment) record;
                enrollment.setEnrollmentDate(LocalDate.parse(v[3]));
                enrollment.setSemester(v[4]);
                enrollment.setEnrollmentStatus(v[5]);
                break;
            case ASSESSMENT:
                Assessment assessment = (Assessment) record;
                assessment.setAssessmentType(v[2]);
                assessment.setMaximumScore(Double.parseDouble(v[3]));
                assessment.setObtainedScore(Double.parseDouble(v[4]));
                assessment.setAssessmentDate(LocalDate.parse(v[5]));
                break;
            case PAYMENT:
                FeePayment payment = (FeePayment) record;
                payment.setAmount(new BigDecimal(v[2]));
                payment.setPaymentDate(LocalDate.parse(v[3]));
                payment.setPaymentMethod(v[4]);
                payment.setReceiptNumber(v[5]);
                break;
            case BOOK:
                LibraryBook book = (LibraryBook) record;
                book.setIsbn(v[1]);
                book.setTitle(v[2]);
                book.setAuthor(v[3]);
                break;
            default:
                BookLoan loan = (BookLoan) record;
                loan.setBorrowedDate(LocalDate.parse(v[3]));
                loan.setDueDate(LocalDate.parse(v[4]));
                break;
        }
    }

    public static boolean isMutable(int type, int i) {

        if (i == 0) {
            return false;
        }

        if (type == ENROLLMENT && (i == 1 || i == 2)) {
            return false;
        }

        if (type == ASSESSMENT && i == 1) {
            return false;
        }

        if (type == PAYMENT && i == 1) {
            return false;
        }

        if (type == BOOK && (i == 4 || i == 5)) {
            return false;
        }

        if (type == LOAN && (i == 1 || i == 2 || i == 5)) {
            return false;
        }

        return true;
    }

    public static String[] fieldLabels(int type) {

        String nameRule = Registrable.MIN_NAME_LENGTH + "-" + Registrable.MAX_NAME_LENGTH + " chars";
        String phoneRule = Registrable.MIN_PHONE_LENGTH + "-" + Registrable.MAX_PHONE_LENGTH + " digits, optional +";

        String[] person = {
                "Person ID [String | unique | " + Identifiable.MIN_ID_LENGTH + "-" + Identifiable.MAX_ID_LENGTH + " chars]",
                "First name [String | " + nameRule + "]",
                "Last name [String | " + nameRule + "]",
                "Email [String | unique | email]",
                "Phone [String | " + phoneRule + "]",
                "Date of birth [LocalDate | YYYY-MM-DD]"};

        String[] member = {
                "Member number [String | unique | 4-30 chars]",
                "Department [Department | select]",
                "Join date [LocalDate | YYYY-MM-DD]",
                "Academic status",
                "Institutional email [String | unique | email]",
                "Advisor name [String | 2-120 chars]"};

        switch (type) {
            case DEPARTMENT:
                return new String[]{
                        "Department ID [String | unique | 3-30 chars]",
                        "Department name [String | 2-60 chars]",
                        "Department code [String | unique | 2-20 chars]",
                        "Office location [String | 2-60 chars]",
                        "Contact email [String | email]",
                        "Head teacher [Teacher | assigned later]"};
            case STUDENT:
                return merge(merge(person, member), new String[]{
                        "Student number [String | unique | 4-30 chars]",
                        "Program name [String | 2-60 chars]",
                        "Admission date [LocalDate | YYYY-MM-DD]",
                        "Study year [int | 1-6]",
                        "Guardian phone [String | " + phoneRule + "]",
                        "Scholarship"});
            case TEACHER:
                return merge(merge(person, member), new String[]{
                        "Employee number [String | unique | 4-30 chars]",
                        "Specialization [String | 2-60 chars]",
                        "Hire date [LocalDate | YYYY-MM-DD]",
                        "Salary [BigDecimal | greater than 0]",
                        "Office number [String | 1-20 chars]",
                        "Employment type"});
            case ADMINISTRATOR:
                return merge(person, new String[]{
                        "Staff number [String | unique | 4-30 chars]",
                        "Job title [String | 2-60 chars]",
                        "Assigned office [String | 2-60 chars]",
                        "Employment date [LocalDate | YYYY-MM-DD]",
                        "Access level [int | 1-5]",
                        "Active"});
            case COURSE:
                return new String[]{
                        "Course ID [String | unique | 3-30 chars]",
                        "Course code [String | unique | 2-20 chars]",
                        "Course title [String | 2-60 chars]",
                        "Credit hours [int | 1-6]",
                        "Department [Department | select]",
                        "Assigned teacher [Teacher | select]"};
            case CLASSROOM:
                return new String[]{
                        "Classroom ID [String | unique | 3-30 chars]",
                        "Building name [String | 2-60 chars]",
                        "Room number [String | 1-20 chars]",
                        "Capacity [int | greater than 0]",
                        "Floor number [int | 0 or more]",
                        "Available"};
            case ENROLLMENT:
                return new String[]{
                        "Enrollment ID [String | unique | 3-30 chars]",
                        "Student [Student | select]",
                        "Course [Course | select]",
                        "Enrollment date [LocalDate | YYYY-MM-DD]",
                        "Semester",
                        "Enrollment status"};
            case ASSESSMENT:
                return new String[]{
                        "Assessment ID [String | unique | 3-30 chars]",
                        "Enrollment [Enrollment | select active]",
                        "Assessment type",
                        "Maximum score [double | greater than 0]",
                        "Obtained score [double | 0 to maximum]",
                        "Assessment date [LocalDate | YYYY-MM-DD]"};
            case PAYMENT:
                return new String[]{
                        "Payment ID [String | unique | 3-30 chars]",
                        "Student [Student | select]",
                        "Amount [BigDecimal | greater than 0]",
                        "Payment date [LocalDate | YYYY-MM-DD]",
                        "Payment method",
                        "Receipt number [String | unique | 3-30 chars]"};
            case BOOK:
                return new String[]{
                        "Book ID [String | unique | 3-30 chars]",
                        "ISBN [String | unique | 10-17 digits or -]",
                        "Title [String | 2-60 chars]",
                        "Author [String | 2-60 chars]",
                        "Total copies [int | 0 or more]",
                        "Available copies [int | 0 to total copies]"};
            default:
                return new String[]{
                        "Loan ID [String | unique | 3-30 chars]",
                        "Student [Student | select]",
                        "Book [LibraryBook | select available]",
                        "Borrowed date [LocalDate | YYYY-MM-DD]",
                        "Due date [LocalDate | after borrowed date]",
                        "Returned date [LocalDate | set on return]"};
        }
    }

    public static String[] fieldChoices(int type, int i) {

        if ((type == STUDENT || type == TEACHER) && i == 9) {
            return ACADEMIC_STATUS;
        }

        if (type == STUDENT && i == 17) {
            return YES_NO;
        }

        if (type == TEACHER && i == 17) {
            return EMPLOYMENT_TYPES;
        }

        if (type == ADMINISTRATOR && i == 11) {
            return YES_NO;
        }

        if (type == CLASSROOM && i == 5) {
            return YES_NO;
        }

        if (type == ENROLLMENT && i == 4) {
            return SEMESTERS;
        }

        if (type == ENROLLMENT && i == 5) {
            return ENROLLMENT_STATUS;
        }

        if (type == ASSESSMENT && i == 2) {
            return ASSESSMENT_TYPES;
        }

        if (type == PAYMENT && i == 4) {
            return PAYMENT_METHODS;
        }

        return new String[0];
    }

    public static int relatedKind(int type, int i) {

        if ((type == STUDENT || type == TEACHER) && i == 7) {
            return DEPARTMENT;
        }

        if (type == COURSE && i == 4) {
            return DEPARTMENT;
        }

        if (type == COURSE && i == 5) {
            return TEACHER;
        }

        if (type == ENROLLMENT && i == 1) {
            return STUDENT;
        }

        if (type == ENROLLMENT && i == 2) {
            return COURSE;
        }

        if (type == ASSESSMENT && i == 1) {
            return ENROLLMENT;
        }

        if ((type == PAYMENT || type == LOAN) && i == 1) {
            return STUDENT;
        }

        return BOOK;
    }

    public static String derivedNote(int type) {

        if (type == DEPARTMENT) {
            return "Not assigned (assign later with Update Existing Record)";
        }

        return "Not returned (set by Return Library Book)";
    }

    public static String displayValue(int type, int i, String[] v) {

        char kind = KINDS[type].charAt(i);

        if (kind == 'D') {
            if (v[i].isEmpty()) {
                return derivedNote(type);
            }

            if (type == DEPARTMENT) {
                return describe(TEACHER, v[i]);
            }

            return v[i];
        }

        if (kind == 'S') {
            if (v[i].isEmpty()) {
                return "(not selected)";
            }

            return describe(relatedKind(type, i), v[i]);
        }

        if (v[i].isEmpty()) {
            return "(empty)";
        }

        return v[i];
    }

    public static String checkLength(String s, int min, int max, String name)
            throws ValidationException {

        String text = "";

        if (s != null) {
            text = s.trim();
        }

        if (text.isEmpty()) {
            throw new ValidationException(name + " cannot be blank.");
        }

        if (text.length() < min || text.length() > max) {
            throw new ValidationException(name + " must be between " + min + " and " + max + " characters.");
        }

        return text;
    }

    public static String checkEmail(String s, String name) throws ValidationException {

        String text = checkLength(s, 5, Registrable.MAX_EMAIL_LENGTH, name);

        if (!text.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ValidationException(name + " is not a valid email address (example: alice@example.com).");
        }

        return text;
    }

    public static String checkPhone(String s, String name) throws ValidationException {

        String text = checkLength(s, 1, 30, name);

        if (!text.matches("\\+?[0-9]{" + Registrable.MIN_PHONE_LENGTH + "," + Registrable.MAX_PHONE_LENGTH + "}")) {
            throw new ValidationException(name + " must contain " + Registrable.MIN_PHONE_LENGTH + " to "
                    + Registrable.MAX_PHONE_LENGTH + " digits, with an optional leading +.");
        }

        return text;
    }

    public static LocalDate parseDate(String s) throws ValidationException {

        try {
            return LocalDate.parse(s.trim());
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid date: use YYYY-MM-DD (example: 2004-05-16).");
        }
    }

    public static String checkPastDate(String s, String name) throws ValidationException {

        LocalDate date = parseDate(s);

        if (!date.isBefore(LocalDate.now())) {
            throw new ValidationException(name + " must be in the past.");
        }

        return date.toString();
    }

    public static String checkNotFutureDate(String s, String name) throws ValidationException {

        LocalDate date = parseDate(s);

        if (date.isAfter(LocalDate.now())) {
            throw new ValidationException(name + " cannot be in the future.");
        }

        return date.toString();
    }

    public static String checkInt(String s, int min, int max, String name) throws ValidationException {

        try {
            int number = Integer.parseInt(s.trim());

            if (number < min || number > max) {
                throw new ValidationException(name + " must be between " + min + " and " + max + ".");
            }

            return String.valueOf(number);
        } catch (NumberFormatException e) {
            throw new ValidationException(name + " must be a whole number.");
        }
    }

    public static String checkMoney(String s, String name) throws ValidationException {

        try {
            BigDecimal amount = new BigDecimal(s.trim());

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidationException(name + " must be greater than zero.");
            }

            return amount.toPlainString();
        } catch (NumberFormatException e) {
            throw new ValidationException(name + " must be a valid amount (example: 150000.50).");
        }
    }

    public static double parseScore(String s, String name) throws ValidationException {

        try {
            double score = Double.parseDouble(s.trim());

            if (Double.isNaN(score) || Double.isInfinite(score)) {
                throw new ValidationException(name + " must be a valid number.");
            }

            return score;
        } catch (NumberFormatException e) {
            throw new ValidationException(name + " must be a valid number (example: 78.5).");
        }
    }

    public static String checkChoice(int type, int i, String value) throws ValidationException {

        String[] options = fieldChoices(type, i);

        for (String option : options) {
            if (option.equalsIgnoreCase(value)) {
                return option;
            }
        }

        throw new ValidationException("Choose one of the listed options.");
    }

    public static boolean valueTaken(int type, int i, String value, String selfId) {

        for (Identifiable record : recordsOfType(type)) {
            if (record.getId().equalsIgnoreCase(selfId)) {
                continue;
            }

            String[] values = valuesOf(type, record);

            if (values[i].equalsIgnoreCase(value)) {
                return true;
            }
        }

        return false;
    }

    public static boolean personValueTaken(int i, String value, String selfId) {

        return valueTaken(STUDENT, i, value, selfId)
                || valueTaken(TEACHER, i, value, selfId)
                || valueTaken(ADMINISTRATOR, i, value, selfId);
    }

    public static boolean memberValueTaken(int i, String value, String selfId) {

        return valueTaken(STUDENT, i, value, selfId)
                || valueTaken(TEACHER, i, value, selfId);
    }

    public static boolean activeEnrollmentExists(String studentId, String courseId,
                                                 String semester, String selfId) {

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId().equalsIgnoreCase(selfId)) {
                continue;
            }

            if (enrollment.isActive()
                    && enrollment.getStudent().getId().equalsIgnoreCase(studentId)
                    && enrollment.getCourse().getId().equalsIgnoreCase(courseId)
                    && enrollment.getSemester().equalsIgnoreCase(semester)) {
                return true;
            }
        }

        return false;
    }

    public static String validateField(int type, int i, String s, String[] v, String selfId)
            throws ValidationException, DuplicateRecordException, BookUnavailableException {

        char kind = KINDS[type].charAt(i);

        if (kind == 'D') {
            return s;
        }

        String name = shortLabel(fieldLabels(type)[i]);

        if (s == null || s.trim().isEmpty()) {
            throw new ValidationException(name + " cannot be blank.");
        }

        String value = s.trim();

        if (kind == 'C') {
            value = checkChoice(type, i, value);
        } else if (kind == 'S') {
            int related = relatedKind(type, i);

            if (findRecord(related, value) == null) {
                throw new ValidationException("Select an existing " + NAMES[related].toLowerCase()
                        + " from the list.");
            }
        } else {
            value = validateTextField(type, i, value, selfId, name);
        }

        checkRelations(type, i, value, v, selfId);

        return value;
    }

    public static String validateTextField(int type, int i, String value, String selfId, String name)
            throws ValidationException, DuplicateRecordException {

        if ((type == STUDENT || type == TEACHER || type == ADMINISTRATOR) && i < 6) {
            return validatePersonField(type, i, value, selfId);
        }

        if ((type == STUDENT || type == TEACHER) && i >= 6 && i < 12) {
            return validateMemberField(i, value, selfId);
        }

        switch (type) {
            case DEPARTMENT:
                return validateDepartmentField(i, value, selfId, name);
            case STUDENT:
                return validateStudentField(i, value, selfId, name);
            case TEACHER:
                return validateTeacherField(i, value, selfId, name);
            case ADMINISTRATOR:
                return validateAdministratorField(i, value, selfId, name);
            case COURSE:
                return validateCourseField(i, value, selfId, name);
            case CLASSROOM:
                return validateClassroomField(i, value, selfId, name);
            case ENROLLMENT:
                return validateEnrollmentField(i, value, selfId, name);
            case ASSESSMENT:
                return validateAssessmentField(i, value, selfId, name);
            case PAYMENT:
                return validatePaymentField(i, value, selfId, name);
            case BOOK:
                return validateBookField(i, value, selfId, name);
            default:
                return validateLoanField(i, value, selfId, name);
        }
    }

    public static String validatePersonField(int type, int i, String value, String selfId)
            throws ValidationException, DuplicateRecordException {

        switch (i) {
            case 0:
                String id = checkLength(value, Identifiable.MIN_ID_LENGTH, Identifiable.MAX_ID_LENGTH, "Person ID");
                if (personValueTaken(0, id, selfId)) {
                    throw new DuplicateRecordException("Person ID " + id + " already exists. Please enter a different ID.");
                }
                return id;
            case 1:
                return checkLength(value, Registrable.MIN_NAME_LENGTH, Registrable.MAX_NAME_LENGTH, "First name");
            case 2:
                return checkLength(value, Registrable.MIN_NAME_LENGTH, Registrable.MAX_NAME_LENGTH, "Last name");
            case 3:
                String email = checkEmail(value, "Email");
                if (personValueTaken(3, email, selfId)) {
                    throw new DuplicateRecordException("Email " + email + " is already used by another person.");
                }
                return email;
            case 4:
                return checkPhone(value, "Phone");
            default:
                return checkPastDate(value, "Date of birth");
        }
    }

    public static String validateMemberField(int i, String value, String selfId)
            throws ValidationException, DuplicateRecordException {

        switch (i) {
            case 6:
                String number = checkLength(value, 4, 30, "Member number");
                if (memberValueTaken(6, number, selfId)) {
                    throw new DuplicateRecordException("Member number " + number + " already exists.");
                }
                return number;
            case 8:
                return checkNotFutureDate(value, "Join date");
            case 10:
                String email = checkEmail(value, "Institutional email");
                if (memberValueTaken(10, email, selfId)) {
                    throw new DuplicateRecordException("Institutional email " + email + " already exists.");
                }
                return email;
            default:
                return checkLength(value, 2, 120, "Advisor name");
        }
    }

    public static String validateDepartmentField(int i, String value, String selfId, String name)
            throws ValidationException, DuplicateRecordException {

        switch (i) {
            case 0:
                String id = checkLength(value, 3, 30, name);
                if (valueTaken(DEPARTMENT, 0, id, selfId)) {
                    throw new DuplicateRecordException("Department ID " + id + " already exists.");
                }
                return id;
            case 1:
                return checkLength(value, 2, 60, name);
            case 2:
                String code = checkLength(value, 2, 20, name);
                if (valueTaken(DEPARTMENT, 2, code, selfId)) {
                    throw new DuplicateRecordException("Department code " + code + " already exists.");
                }
                return code;
            case 3:
                return checkLength(value, 2, 60, name);
            default:
                return checkEmail(value, name);
        }
    }

    public static String validateStudentField(int i, String value, String selfId, String name)
            throws ValidationException, DuplicateRecordException {

        switch (i) {
            case 12:
                String number = checkLength(value, 4, 30, name);
                if (valueTaken(STUDENT, 12, number, selfId)) {
                    throw new DuplicateRecordException("Student number " + number + " already exists.");
                }
                return number;
            case 13:
                return checkLength(value, 2, 60, name);
            case 14:
                return checkNotFutureDate(value, name);
            case 15:
                return checkInt(value, 1, 6, name);
            default:
                return checkPhone(value, name);
        }
    }

    public static String validateTeacherField(int i, String value, String selfId, String name)
            throws ValidationException, DuplicateRecordException {

        switch (i) {
            case 12:
                String number = checkLength(value, 4, 30, name);
                if (valueTaken(TEACHER, 12, number, selfId)) {
                    throw new DuplicateRecordException("Employee number " + number + " already exists.");
                }
                return number;
            case 13:
                return checkLength(value, 2, 60, name);
            case 14:
                return checkNotFutureDate(value, name);
            case 15:
                return checkMoney(value, name);
            default:
                return checkLength(value, 1, 20, name);
        }
    }

    public static String validateAdministratorField(int i, String value, String selfId, String name)
            throws ValidationException, DuplicateRecordException {

        switch (i) {
            case 6:
                String number = checkLength(value, 4, 30, name);
                if (valueTaken(ADMINISTRATOR, 6, number, selfId)) {
                    throw new DuplicateRecordException("Staff number " + number + " already exists.");
                }
                return number;
            case 7:
                return checkLength(value, 2, 60, name);
            case 8:
                return checkLength(value, 2, 60, name);
            case 9:
                return checkNotFutureDate(value, name);
            default:
                return checkInt(value, 1, 5, name);
        }
    }

    public static String validateCourseField(int i, String value, String selfId, String name)
            throws ValidationException, DuplicateRecordException {

        switch (i) {
            case 0:
                String id = checkLength(value, 3, 30, name);
                if (valueTaken(COURSE, 0, id, selfId)) {
                    throw new DuplicateRecordException("Course ID " + id + " already exists.");
                }
                return id;
            case 1:
                String code = checkLength(value, 2, 20, name);
                if (valueTaken(COURSE, 1, code, selfId)) {
                    throw new DuplicateRecordException("Course code " + code + " already exists.");
                }
                return code;
            case 2:
                return checkLength(value, 2, 60, name);
            default:
                return checkInt(value, 1, 6, name);
        }
    }

    public static String validateClassroomField(int i, String value, String selfId, String name)
            throws ValidationException, DuplicateRecordException {

        switch (i) {
            case 0:
                String id = checkLength(value, 3, 30, name);
                if (valueTaken(CLASSROOM, 0, id, selfId)) {
                    throw new DuplicateRecordException("Classroom ID " + id + " already exists.");
                }
                return id;
            case 1:
                return checkLength(value, 2, 60, name);
            case 2:
                return checkLength(value, 1, 20, name);
            case 3:
                return checkInt(value, 1, 100000, name);
            default:
                return checkInt(value, 0, 200, name);
        }
    }

    public static String validateEnrollmentField(int i, String value, String selfId, String name)
            throws ValidationException, DuplicateRecordException {

        if (i == 0) {
            String id = checkLength(value, 3, 30, name);
            if (valueTaken(ENROLLMENT, 0, id, selfId)) {
                throw new DuplicateRecordException("Enrollment ID " + id + " already exists.");
            }
            return id;
        }

        return checkNotFutureDate(value, name);
    }

    public static String validateAssessmentField(int i, String value, String selfId, String name)
            throws ValidationException, DuplicateRecordException {

        switch (i) {
            case 0:
                String id = checkLength(value, 3, 30, name);
                if (valueTaken(ASSESSMENT, 0, id, selfId)) {
                    throw new DuplicateRecordException("Assessment ID " + id + " already exists.");
                }
                return id;
            case 3:
                double maximum = parseScore(value, name);
                if (maximum <= 0) {
                    throw new ValidationException(name + " must be greater than zero.");
                }
                return String.valueOf(maximum);
            case 4:
                double obtained = parseScore(value, name);
                if (obtained < 0) {
                    throw new ValidationException(name + " cannot be negative.");
                }
                return String.valueOf(obtained);
            default:
                return checkNotFutureDate(value, name);
        }
    }

    public static String validatePaymentField(int i, String value, String selfId, String name)
            throws ValidationException, DuplicateRecordException {

        switch (i) {
            case 0:
                String id = checkLength(value, 3, 30, name);
                if (valueTaken(PAYMENT, 0, id, selfId)) {
                    throw new DuplicateRecordException("Payment ID " + id + " already exists.");
                }
                return id;
            case 2:
                return checkMoney(value, name);
            case 3:
                return checkNotFutureDate(value, name);
            default:
                String receipt = checkLength(value, 3, 30, name);
                if (valueTaken(PAYMENT, 5, receipt, selfId)) {
                    throw new DuplicateRecordException("Receipt number " + receipt + " already exists.");
                }
                return receipt;
        }
    }

    public static String validateBookField(int i, String value, String selfId, String name)
            throws ValidationException, DuplicateRecordException {

        switch (i) {
            case 0:
                String id = checkLength(value, 3, 30, name);
                if (valueTaken(BOOK, 0, id, selfId)) {
                    throw new DuplicateRecordException("Book ID " + id + " already exists.");
                }
                return id;
            case 1:
                String isbn = checkLength(value, 10, 17, name);
                if (!isbn.matches("[0-9Xx-]+")) {
                    throw new ValidationException(name + " may contain only digits, hyphens and X.");
                }
                if (valueTaken(BOOK, 1, isbn, selfId)) {
                    throw new DuplicateRecordException("ISBN " + isbn + " already exists.");
                }
                return isbn;
            case 2:
                return checkLength(value, 2, 60, name);
            case 3:
                return checkLength(value, 2, 60, name);
            default:
                return checkInt(value, 0, 100000, name);
        }
    }

    public static String validateLoanField(int i, String value, String selfId, String name)
            throws ValidationException, DuplicateRecordException {

        switch (i) {
            case 0:
                String id = checkLength(value, 3, 30, name);
                if (valueTaken(LOAN, 0, id, selfId)) {
                    throw new DuplicateRecordException("Loan ID " + id + " already exists.");
                }
                return id;
            case 3:
                return checkNotFutureDate(value, name);
            default:
                return parseDate(value).toString();
        }
    }

    public static void checkRelations(int type, int i, String value, String[] v, String selfId)
            throws ValidationException, DuplicateRecordException, BookUnavailableException {

        if (type == COURSE && i == 5) {
            Department department = findDepartment(v[4]);
            Teacher teacher = findTeacher(value);

            if (department == null) {
                throw new ValidationException("Choose the department before the teacher.");
            }

            if (!teacher.getDepartment().getId().equalsIgnoreCase(department.getId())) {
                throw new ValidationException("Teacher must belong to the selected department.");
            }
        }

        if (type == ENROLLMENT && (i == 4 || i == 5)) {
            boolean activeNew = true;

            if (i == 5) {
                activeNew = value.equals("ACTIVE");
            } else if (v[5].equals("COMPLETED") || v[5].equals("CANCELLED")) {
                activeNew = false;
            }

            if (activeNew && !v[1].isEmpty() && !v[2].isEmpty()) {
                String semester = v[4];

                if (i == 4) {
                    semester = value;
                }

                if (!semester.isEmpty() && activeEnrollmentExists(v[1], v[2], semester, selfId)) {
                    throw new DuplicateRecordException("This student already has an active enrollment in this course for "
                            + semester + ".");
                }
            }
        }

        if (type == ASSESSMENT && i == 1 && selfId.isEmpty()) {
            Enrollment enrollment = findEnrollment(value);

            if (!enrollment.isActive()) {
                throw new ValidationException("Only an active enrollment can be assessed.");
            }
        }

        if (type == ASSESSMENT && i == 4 && !v[3].isEmpty()) {
            if (Double.parseDouble(value) > Double.parseDouble(v[3])) {
                throw new ValidationException("Obtained score cannot be greater than the maximum score.");
            }
        }

        if (type == BOOK && i == 5 && !v[4].isEmpty()) {
            if (Integer.parseInt(value) > Integer.parseInt(v[4])) {
                throw new ValidationException("Available copies cannot exceed total copies.");
            }
        }

        if (type == LOAN && i == 2 && selfId.isEmpty()) {
            LibraryBook book = findBook(value);

            if (book.getAvailableCopies() <= 0) {
                throw new BookUnavailableException("No copy of \"" + book.getTitle()
                        + "\" is available to borrow.");
            }
        }

        if (type == LOAN && i == 4 && !v[3].isEmpty()) {
            if (!LocalDate.parse(value).isAfter(LocalDate.parse(v[3]))) {
                throw new ValidationException("Due date must be after the borrowed date.");
            }
        }
    }

    public static int findInvalidField(int type, String[] v, String selfId) {

        for (int i = 0; i < v.length; i++) {
            if (KINDS[type].charAt(i) == 'D') {
                continue;
            }

            try {
                validateField(type, i, v[i], v, selfId);
            } catch (ValidationException | DuplicateRecordException | BookUnavailableException e) {
                lastError = "Field " + two(i + 1) + ": " + e.getMessage();
                return i;
            }
        }

        return -1;
    }

    public static boolean fixInvalidFields(int type, String[] v, String selfId) {

        while (true) {
            int bad = findInvalidField(type, v, selfId);

            if (bad < 0) {
                return true;
            }

            System.out.println("! " + lastError);
            System.out.println("  Please correct field " + two(bad + 1) + " now (type CANCEL to undo the change).");

            String result = askField(type, bad, v, selfId);

            if (!result.equals("OK")) {
                return false;
            }
        }
    }

    public static String askField(int type, int i, String[] v, String selfId) {

        String prompt = two(i + 1) + " " + fieldLabels(type)[i];
        char kind = KINDS[type].charAt(i);

        if (kind == 'S') {
            return askRelatedRecord(type, i, v, selfId);
        }

        if (kind == 'C') {
            return askChoice(type, i, v, selfId, prompt);
        }

        return askText(type, i, v, selfId, prompt);
    }

    public static String askText(int type, int i, String[] v, String selfId, String prompt) {

        while (true) {
            String shown = prompt + " : ";

            if (!v[i].isEmpty()) {
                shown = prompt + " (current: " + v[i] + ") : ";
            }

            String line = readString(shown);

            if (line.equalsIgnoreCase("CANCEL")) {
                return "CANCEL";
            }

            if (line.equalsIgnoreCase("BACK")) {
                return "BACK";
            }

            if (line.isEmpty() && !v[i].isEmpty()) {
                return "OK";
            }

            try {
                v[i] = validateField(type, i, line, v, selfId);
                return "OK";
            } catch (ValidationException | DuplicateRecordException | BookUnavailableException e) {
                System.out.println("! " + e.getMessage());
                System.out.println("  Please re-enter field " + two(i + 1) + ".");
            }
        }
    }

    public static String askChoice(int type, int i, String[] v, String selfId, String prompt) {

        String[] options = fieldChoices(type, i);
        String hint = "[";

        for (int k = 0; k < options.length; k++) {
            if (k > 0) {
                hint = hint + " / ";
            }

            hint = hint + (k + 1) + " " + options[k];
        }

        hint = hint + "]";

        while (true) {
            String shown = prompt + " " + hint + " : ";

            if (!v[i].isEmpty()) {
                shown = prompt + " " + hint + " (current: " + v[i] + ") : ";
            }

            String line = readString(shown);

            if (line.equalsIgnoreCase("CANCEL")) {
                return "CANCEL";
            }

            if (line.equalsIgnoreCase("BACK")) {
                return "BACK";
            }

            if (line.isEmpty() && !v[i].isEmpty()) {
                return "OK";
            }

            String selected = "";

            try {
                int number = Integer.parseInt(line);

                if (number >= 1 && number <= options.length) {
                    selected = options[number - 1];
                }
            } catch (NumberFormatException e) {
                selected = line;
            }

            try {
                v[i] = validateField(type, i, selected, v, selfId);
                return "OK";
            } catch (ValidationException e) {
                System.out.println("! Invalid choice. Enter a number from 1 to " + options.length + ".");
                System.out.println("  Please re-enter field " + two(i + 1) + ".");
            } catch (DuplicateRecordException | BookUnavailableException e) {
                System.out.println("! " + e.getMessage());
                System.out.println("  Please re-enter field " + two(i + 1) + ".");
            }
        }
    }

    public static void fillRelatedOptions(int kind, int type, String[] v,
                                          ArrayList<String> labels, ArrayList<String> ids) {

        labels.clear();
        ids.clear();

        for (Identifiable record : recordsOfType(kind)) {

            if (kind == TEACHER && type == COURSE) {
                Teacher teacher = (Teacher) record;

                if (!teacher.getDepartment().getId().equalsIgnoreCase(v[4])) {
                    continue;
                }
            }

            if (kind == ENROLLMENT && !record.isActive()) {
                continue;
            }

            labels.add(label(kind, record));
            ids.add(record.getId());
        }
    }

    public static String askRelatedRecord(int type, int i, String[] v, String selfId) {

        int kind = relatedKind(type, i);
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();

        while (true) {
            fillRelatedOptions(kind, type, v, labels, ids);

            String kindName = NAMES[kind];
            System.out.println();
            System.out.println("+----------------- CHOOSE " + kindName.toUpperCase() + " -----------------+");

            if (ids.isEmpty()) {
                System.out.println("| No " + kindName.toLowerCase() + " is available to choose yet.");

                if (kind == TEACHER && type == COURSE) {
                    System.out.println("| Only teachers of the selected department are listed.");
                }
            }

            for (int k = 0; k < ids.size(); k++) {
                System.out.println("| " + (k + 1) + " " + labels.get(k));
            }

            System.out.println("| " + (ids.size() + 1) + " [+] Register a new " + kindName.toLowerCase());
            System.out.println("| 0 Cancel " + NAMES[type].toLowerCase() + " registration");

            if (!v[i].isEmpty()) {
                System.out.println("| ENTER keeps: " + describe(kind, v[i]));
            }

            System.out.println("+------------------------------------------------------------+");

            String line = readString(two(i + 1) + " " + shortLabel(fieldLabels(type)[i]) + " - Select option: ");

            if (line.equalsIgnoreCase("BACK")) {
                return "BACK";
            }

            if (line.equalsIgnoreCase("CANCEL")) {
                return "CANCEL";
            }

            if (line.isEmpty() && !v[i].isEmpty()) {
                return "OK";
            }

            int number;

            try {
                number = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("! Please enter a number from the list.");
                continue;
            }

            if (number == 0) {
                return "CANCEL";
            }

            if (number >= 1 && number <= ids.size()) {
                try {
                    v[i] = validateField(type, i, ids.get(number - 1), v, selfId);
                    return "OK";
                } catch (ValidationException | DuplicateRecordException | BookUnavailableException e) {
                    System.out.println("! " + e.getMessage());
                    continue;
                }
            }

            if (number == ids.size() + 1) {
                registerNested(kind, NAMES[type].toLowerCase() + " registration");
                continue;
            }

            System.out.println("! Invalid selection. Choose a number shown in the list.");
        }
    }

    public static void registerNested(int kind, String returnTo) {

        try {
            String savedId = runForm(kind);

            if (savedId != null) {
                System.out.println("\n" + NAMES[kind] + " " + savedId + " saved. Returning to " + returnTo + "...");
            } else {
                System.out.println("\nRegistration cancelled. Returning to " + returnTo + "...");
            }
        } catch (PrerequisiteMissingException e) {
            System.out.println("\n! " + e.getMessage());
            System.out.println("  Returning to " + returnTo + "...");
        }
    }

    public static int countAvailable(int kind) {

        int count = 0;

        for (Identifiable record : recordsOfType(kind)) {
            if (kind == ENROLLMENT && !record.isActive()) {
                continue;
            }

            count++;
        }

        return count;
    }

    public static boolean ensureRecords(int kind, String purpose) {

        while (countAvailable(kind) == 0) {

            String name = NAMES[kind].toLowerCase();

            if (kind == ENROLLMENT) {
                name = "active enrollment";
            }

            System.out.println();
            System.out.println("No " + name + " exists. Register a " + name + " before " + purpose + ".");
            System.out.println("1. Register " + NAMES[kind].toLowerCase() + " now");
            System.out.println("2. Return to menu");

            int choice = readNumber("Select option: ");

            if (choice == 1) {
                registerNested(kind, "the original task");
            } else if (choice == 2 || choice == 0) {
                return false;
            } else {
                System.out.println("! Invalid option. Enter 1 or 2.");
            }
        }

        return true;
    }

    public static boolean checkPrerequisites(int type) {

        switch (type) {
            case COURSE:
                return ensureRecords(DEPARTMENT, "registering a course")
                        && ensureRecords(TEACHER, "registering a course");
            case ENROLLMENT:
                return ensureRecords(STUDENT, "enrolling a student")
                        && ensureRecords(COURSE, "enrolling a student");
            case ASSESSMENT:
                return ensureRecords(ENROLLMENT, "recording an assessment");
            case PAYMENT:
                return ensureRecords(STUDENT, "recording a fee payment");
            case LOAN:
                return ensureRecords(STUDENT, "borrowing a library book")
                        && ensureRecords(BOOK, "borrowing a library book");
            default:
                return true;
        }

    }

    public static void printBanner(int type, int step) {

        String[] titles = STEP_TITLES[type];
        String line = "+------------------------------------------------------------+";

        System.out.println();
        System.out.println(line);
        System.out.println(boxLine("AUCA_ID / SCHOOL MANAGEMENT"));
        System.out.println(boxLine(String.format("%-46s%12s", NAMES[type].toUpperCase() + " REGISTRATION",
                "Step " + step + " of 4")));
        System.out.println(line);
        System.out.println(boxLine(String.format("%-29s%-29s", "[1] " + titles[0], "[2] " + titles[1])));
        System.out.println(boxLine(String.format("%-29s%-29s", "[3] " + titles[2], "[4] Review & confirm")));
        System.out.println(line);
        System.out.println(boxLine("Enter the requested value. Type BACK to return;"));
        System.out.println(boxLine("CANCEL to discard the unsaved " + NAMES[type].toLowerCase() + "."));
        System.out.println(line);
    }

    public static void printReview(int type, String[] v) {

        String[] titles = STEP_TITLES[type];
        int[] starts = STEP_STARTS[type];
        String[] labels = fieldLabels(type);

        System.out.println();
        System.out.println("+---------------- REVIEW " + NAMES[type].toUpperCase() + " ----------------+");

        for (int i = 0; i < v.length; i++) {
            for (int s = 0; s < 3; s++) {
                if (starts[s] == i) {
                    System.out.println("| " + titles[s].toUpperCase());
                }
            }

            System.out.println("|   " + two(i + 1) + " " + String.format("%-22s", shortLabel(labels[i]))
                    + ": " + displayValue(type, i, v));
        }

        System.out.println("+------------------------------------------------------------+");
        System.out.println("| [1] Save " + NAMES[type].toLowerCase() + "   [2] Edit field   [0] Cancel");
        System.out.println("+------------------------------------------------------------+");
    }

    public static int collectStep(int type, String[] v, int from, int to) {

        int i = from;

        while (i < to) {

            if (KINDS[type].charAt(i) == 'D') {
                System.out.println(two(i + 1) + " " + shortLabel(fieldLabels(type)[i]) + " : "
                        + displayValue(type, i, v));
                i++;
                continue;
            }

            String result = askField(type, i, v, "");

            if (result.equals("CANCEL")) {
                return 2;
            }

            if (result.equals("BACK")) {
                if (i == from) {
                    return 1;
                }

                i--;

                while (i > from && KINDS[type].charAt(i) == 'D') {
                    i--;
                }
            } else {
                i++;
            }
        }

        return 0;
    }

    public static String runForm(int type) throws PrerequisiteMissingException {

        if (!checkPrerequisites(type)) {
            throw new PrerequisiteMissingException("A required record is missing, so the "
                    + NAMES[type].toLowerCase() + " registration was stopped.");
        }

        int fieldCount = KINDS[type].length();
        String[] v = new String[fieldCount];

        for (int i = 0; i < fieldCount; i++) {
            v[i] = "";
        }

        int[] starts = STEP_STARTS[type];
        int step = 0;

        while (step < 3) {

            printBanner(type, step + 1);
            System.out.println("[STEP " + (step + 1) + "/4] " + STEP_TITLES[type][step].toUpperCase());

            int to = fieldCount;

            if (step < 2) {
                to = starts[step + 1];
            }

            int result = collectStep(type, v, starts[step], to);

            if (result == 2) {
                System.out.println("\nRegistration cancelled. Nothing was saved.");
                return null;
            }

            if (result == 1) {
                if (step == 0) {
                    System.out.println("You are already at the first step.");
                } else {
                    step--;
                }
            } else {
                step++;
            }
        }

        return reviewLoop(type, v);
    }

    public static String reviewLoop(int type, String[] v) {

        while (true) {

            printBanner(type, 4);
            printReview(type, v);

            int choice = readNumber("Select option: ");

            if (choice == 0) {
                System.out.println("\nRegistration cancelled. Nothing was saved.");
                return null;
            }

            if (choice == 2) {
                editField(type, v);
                continue;
            }

            if (choice != 1) {
                System.out.println("! Invalid option. Enter 1, 2 or 0.");
                continue;
            }

            if (!fixInvalidFields(type, v, "")) {
                continue;
            }

            try {
                String id = saveRecord(type, v);
                System.out.println("\n+ " + NAMES[type] + " " + id + " registered successfully.");
                return id;
            } catch (ValidationException | BookUnavailableException | DuplicateRecordException e) {
                System.out.println("\n! Could not save: " + e.getMessage());
                System.out.println("  Use Edit field to correct the record.");
            }
        }
    }

    public static void editField(int type, String[] v) {

        int number = readNumber("Field number to edit (01-" + two(v.length) + "): ");

        if (number < 1 || number > v.length) {
            System.out.println("! Invalid field number.");
            return;
        }

        int i = number - 1;

        if (KINDS[type].charAt(i) == 'D') {
            System.out.println("! This field is set automatically and cannot be edited here.");
            return;
        }

        System.out.println("Field " + two(number) + " " + shortLabel(fieldLabels(type)[i])
                + " - old value: " + displayValue(type, i, v));

        String[] backup = copyOf(v);
        String result = askField(type, i, v, "");

        if (!result.equals("OK")) {
            System.out.println("Edit cancelled. The old value was kept.");
            return;
        }

        if (!fixInvalidFields(type, v, "")) {
            for (int k = 0; k < v.length; k++) {
                v[k] = backup[k];
            }

            System.out.println("Change undone. The old values were restored.");
        }
    }

    public static String saveRecord(int type, String[] v)
            throws ValidationException, DuplicateRecordException, BookUnavailableException {

        switch (type) {
            case DEPARTMENT: {
                Department department = new Department(v[0], v[1], v[2], v[3], v[4], findTeacher(v[5]));
                department.validate();
                departments.add(department);
                break;
            }
            case STUDENT: {
                Student student = new Student(v[0], v[1], v[2], v[3], v[4], LocalDate.parse(v[5]),
                        v[6], findDepartment(v[7]), LocalDate.parse(v[8]), v[9], v[10], v[11],
                        v[12], v[13], LocalDate.parse(v[14]), Integer.parseInt(v[15]), v[16],
                        v[17].equals("YES"));
                student.validate();
                students.add(student);
                break;
            }
            case TEACHER: {
                Teacher teacher = new Teacher(v[0], v[1], v[2], v[3], v[4], LocalDate.parse(v[5]),
                        v[6], findDepartment(v[7]), LocalDate.parse(v[8]), v[9], v[10], v[11],
                        v[12], v[13], LocalDate.parse(v[14]), new BigDecimal(v[15]), v[16], v[17]);
                teacher.validate();
                teachers.add(teacher);
                break;
            }
            case ADMINISTRATOR: {
                Administrator administrator = new Administrator(v[0], v[1], v[2], v[3], v[4],
                        LocalDate.parse(v[5]), v[6], v[7], v[8], LocalDate.parse(v[9]),
                        Integer.parseInt(v[10]), v[11].equals("YES"));
                administrator.validate();
                administrators.add(administrator);
                break;
            }
            case COURSE: {
                Course course = new Course(v[0], v[1], v[2], Integer.parseInt(v[3]),
                        findDepartment(v[4]), findTeacher(v[5]));
                course.validate();
                courses.add(course);
                break;
            }
            case CLASSROOM: {
                Classroom classroom = new Classroom(v[0], v[1], v[2], Integer.parseInt(v[3]),
                        Integer.parseInt(v[4]), v[5].equals("YES"));
                classroom.validate();
                classrooms.add(classroom);
                break;
            }
            case ENROLLMENT: {
                Enrollment enrollment = new Enrollment(v[0], findStudent(v[1]), findCourse(v[2]),
                        LocalDate.parse(v[3]), v[4], v[5]);
                enrollment.validate();
                enrollments.add(enrollment);
                break;
            }
            case ASSESSMENT: {
                Assessment assessment = new Assessment(v[0], findEnrollment(v[1]), v[2],
                        Double.parseDouble(v[3]), Double.parseDouble(v[4]), LocalDate.parse(v[5]));
                assessment.validate();
                assessments.add(assessment);
                break;
            }
            case PAYMENT: {
                FeePayment payment = new FeePayment(v[0], findStudent(v[1]), new BigDecimal(v[2]),
                        LocalDate.parse(v[3]), v[4], v[5]);
                payment.validate();
                payments.add(payment);
                break;
            }
            case BOOK: {
                LibraryBook book = new LibraryBook(v[0], v[1], v[2], v[3],
                        Integer.parseInt(v[4]), Integer.parseInt(v[5]));
                book.validate();
                books.add(book);
                break;
            }
            default: {
                LibraryBook book = findBook(v[2]);
                BookLoan loan = new BookLoan(v[0], findStudent(v[1]), book, LocalDate.parse(v[3]),
                        LocalDate.parse(v[4]), null);
                loan.validate();

                if (book.getAvailableCopies() <= 0) {
                    throw new BookUnavailableException("No copy of \"" + book.getTitle()
                            + "\" is available to borrow.");
                }

                book.setAvailableCopies(book.getAvailableCopies() - 1);
                loans.add(loan);
                break;
            }
        }

        return v[0];
    }

    public static void registerFromMenu(int type) throws PrerequisiteMissingException {

        while (true) {
            String id = runForm(type);

            if (id == null) {
                return;
            }

            System.out.println("  [1] Register another  [2] View " + NAMES[type].toLowerCase() + "  [0] Main menu");
            int choice = readNumber("Select option: ");

            if (choice == 1) {
                continue;
            }

            if (choice == 2) {
                Identifiable record = findRecord(type, id);

                if (record != null) {
                    ((Registrable) record).printDetails();
                }
            }

            return;
        }
    }

    public static void returnBook()
            throws RecordNotFoundException, ValidationException {

        System.out.println("\n========== RETURN LIBRARY BOOK ==========");

        ArrayList<BookLoan> activeLoans = new ArrayList<>();

        for (BookLoan loan : loans) {
            if (loan.isActive()) {
                activeLoans.add(loan);
            }
        }

        if (activeLoans.isEmpty()) {
            throw new RecordNotFoundException("There is no active loan to return.");
        }

        System.out.println("Active loans:");

        for (int i = 0; i < activeLoans.size(); i++) {
            System.out.println((i + 1) + ". " + label(LOAN, activeLoans.get(i)));
        }

        System.out.println("0. Cancel");

        int choice = readNumber("Select loan: ");

        if (choice == 0) {
            System.out.println("Return cancelled.");
            return;
        }

        if (choice < 1 || choice > activeLoans.size()) {
            throw new RecordNotFoundException("Invalid loan selection.");
        }

        BookLoan loan = activeLoans.get(choice - 1);
        LocalDate returnedDate = null;

        while (returnedDate == null) {
            String line = readString("Returned date (LocalDate, YYYY-MM-DD, CANCEL to stop): ");

            if (line.equalsIgnoreCase("CANCEL")) {
                System.out.println("Return cancelled. Stock was not changed.");
                return;
            }

            try {
                LocalDate date = parseDate(line);

                if (date.isAfter(LocalDate.now())) {
                    throw new ValidationException("Returned date cannot be in the future.");
                }

                if (date.isBefore(loan.getBorrowedDate())) {
                    throw new ValidationException("Returned date cannot be before the borrowed date ("
                            + loan.getBorrowedDate() + ").");
                }

                returnedDate = date;
            } catch (ValidationException e) {
                System.out.println("! " + e.getMessage());
            }
        }

        System.out.println("\nReturn summary: " + label(LOAN, loan) + " returned on " + returnedDate);
        System.out.println("1. Confirm return   0. Cancel");

        if (readNumber("Select option: ") != 1) {
            System.out.println("Return cancelled. Stock was not changed.");
            return;
        }

        LibraryBook book = loan.getBook();

        if (book.getAvailableCopies() >= book.getTotalCopies()) {
            throw new ValidationException("Stock records show all copies are already in the library.");
        }

        loan.setReturnedDate(returnedDate);
        loan.validate();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        System.out.println("\n+ Loan " + loan.getLoanId() + " returned successfully.");
        System.out.println("  Available copies of " + book.getTitle() + ": " + book.getAvailableCopies()
                + " of " + book.getTotalCopies());
        loan.printDetails();
    }

    public static void viewAllRecords() {

        System.out.println("\n========== ALL REGISTERED RECORDS ==========");

        for (int type = DEPARTMENT; type <= LOAN; type++) {

            ArrayList<Identifiable> list = recordsOfType(type);

            System.out.println("\n---------- " + NAMES[type].toUpperCase() + " (" + list.size() + ") ----------");

            if (list.isEmpty()) {
                System.out.println("No " + NAMES[type].toLowerCase() + " records registered.");
            }

            for (Identifiable record : list) {
                ((Registrable) record).printDetails();
                System.out.println("-------------------------");
            }
        }

        readString("\nPress ENTER to return to the menu...");
    }

    public static void searchById() {

        System.out.println("\n========== SEARCH RECORD BY ID ==========");

        String id = readString("Enter record ID (blank to cancel): ");

        if (id.isEmpty()) {
            System.out.println("Search cancelled.");
            return;
        }

        boolean found = false;

        for (int type = DEPARTMENT; type <= LOAN; type++) {
            for (Identifiable record : recordsOfType(type)) {
                if (record.getId().equalsIgnoreCase(id)) {
                    found = true;
                    System.out.println("\nRecord found (" + NAMES[type] + "): " + label(type, record));
                    ((Registrable) record).printDetails();
                }
            }
        }

        if (!found) {
            System.out.println("No record found with ID: " + id);
        }
    }

    public static void updateRecord() throws RecordNotFoundException {

        System.out.println("\n========== UPDATE EXISTING RECORD ==========");

        for (int type = DEPARTMENT; type <= LOAN; type++) {
            System.out.println(type + ". " + NAMES[type]);
        }

        System.out.println("0. Cancel");

        int type = readNumber("Select record type: ");

        if (type == 0) {
            System.out.println("Update cancelled.");
            return;
        }

        if (type < DEPARTMENT || type > LOAN) {
            throw new RecordNotFoundException("Invalid record type selection.");
        }

        ArrayList<Identifiable> list = recordsOfType(type);

        if (list.isEmpty()) {
            throw new RecordNotFoundException("No " + NAMES[type].toLowerCase() + " record exists to update.");
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + label(type, list.get(i)));
        }

        System.out.println("0. Cancel");

        int choice = readNumber("Select record: ");

        if (choice == 0) {
            System.out.println("Update cancelled.");
            return;
        }

        if (choice < 1 || choice > list.size()) {
            throw new RecordNotFoundException("Invalid record selection.");
        }

        Identifiable record = list.get(choice - 1);
        String selfId = record.getId();
        String[] values = valuesOf(type, record);
        String[] original = copyOf(values);
        String[] labels = fieldLabels(type);
        boolean changed = false;

        while (true) {

            System.out.println("\n---------- UPDATE " + NAMES[type].toUpperCase() + " " + selfId + " ----------");

            for (int i = 0; i < values.length; i++) {
                String note = "";

                if (!isMutable(type, i) && !(type == DEPARTMENT && i == 5)) {
                    note = "  (locked)";
                }

                System.out.println(two(i + 1) + " " + String.format("%-22s", shortLabel(labels[i]))
                        + ": " + displayValue(type, i, values) + note);
            }

            int number = readNumber("Field number to edit (0 to finish): ");

            if (number == 0) {
                break;
            }

            if (number < 1 || number > values.length) {
                System.out.println("! Invalid field number.");
                continue;
            }

            int index = number - 1;

            boolean headTeacherField = type == DEPARTMENT && index == 5;

            if (!isMutable(type, index) && !headTeacherField) {
                System.out.println("! This field is locked and cannot be changed.");
                continue;
            }

            String[] backup = copyOf(values);
            String result;

            if (headTeacherField) {
                result = chooseHeadTeacher(values);
            } else {
                result = askField(type, index, values, selfId);
            }

            if (result.equals("OK")) {
                if (fixInvalidFields(type, values, selfId)) {
                    changed = true;
                } else {
                    for (int k = 0; k < values.length; k++) {
                        values[k] = backup[k];
                    }

                    System.out.println("Change undone. The old values were restored.");
                }
            } else {
                System.out.println("No change made to that field.");
            }
        }

        if (!changed) {
            System.out.println("No changes were made.");
            return;
        }

        System.out.println("\nThe record will be changed to:");

        for (int i = 0; i < values.length; i++) {
            System.out.println(two(i + 1) + " " + String.format("%-22s", shortLabel(labels[i]))
                    + ": " + displayValue(type, i, values));
        }

        System.out.println("1. Confirm and save changes   0. Discard changes");

        if (readNumber("Select option: ") != 1) {
            System.out.println("Changes discarded. The record was not modified.");
            return;
        }

        applyValues(type, record, values);

        try {
            ((Registrable) record).validate();
            System.out.println("\n+ " + NAMES[type] + " " + selfId + " updated successfully.");
            ((Registrable) record).printDetails();
        } catch (ValidationException e) {
            applyValues(type, record, original);
            System.out.println("\n! Update rejected: " + e.getMessage());
            System.out.println("  The original record was restored.");
        }
    }

    public static String chooseHeadTeacher(String[] values) {

        ArrayList<Teacher> candidates = new ArrayList<>();

        for (Teacher teacher : teachers) {
            if (teacher.getDepartment().getId().equalsIgnoreCase(values[0])) {
                candidates.add(teacher);
            }
        }

        if (candidates.isEmpty()) {
            System.out.println("! No teacher belongs to this department yet. Register a teacher in it first.");
            return "NONE";
        }

        System.out.println("\nTeachers of this department:");

        for (int i = 0; i < candidates.size(); i++) {
            System.out.println((i + 1) + ". " + label(TEACHER, candidates.get(i)));
        }

        System.out.println("0. No head teacher");

        int choice = readNumber("Select head teacher: ");

        if (choice == 0) {
            values[5] = "";
            return "OK";
        }

        if (choice < 1 || choice > candidates.size()) {
            System.out.println("! Invalid selection.");
            return "NONE";
        }

        values[5] = candidates.get(choice - 1).getId();
        return "OK";
    }

    public static Student pickStudent() throws RecordNotFoundException {

        if (students.isEmpty()) {
            throw new RecordNotFoundException("No student is registered yet.");
        }

        System.out.println("\nStudents:");

        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + label(STUDENT, students.get(i)));
        }

        System.out.println("0. Cancel");

        int choice = readNumber("Select student: ");

        if (choice == 0) {
            return null;
        }

        if (choice < 1 || choice > students.size()) {
            throw new RecordNotFoundException("Invalid student selection.");
        }

        return students.get(choice - 1);
    }

    public static void showStudentAcademicSummary() throws RecordNotFoundException {

        System.out.println("\n========== STUDENT ACADEMIC SUMMARY ==========");

        Student student = pickStudent();

        if (student == null) {
            System.out.println("Cancelled.");
            return;
        }

        student.printDetails();

        System.out.println("\n---------- ENROLLMENTS ----------");

        int enrollmentCount = 0;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().equals(student)) {
                enrollmentCount++;
                System.out.println(enrollmentCount + ". " + label(ENROLLMENT, enrollment)
                        + " - " + enrollment.getCourse().getCreditHours() + " credit hours");
            }
        }

        if (enrollmentCount == 0) {
            System.out.println("No enrollments recorded for this student.");
        }

        System.out.println("\n---------- ASSESSMENTS ----------");

        double totalObtained = 0;
        double totalMaximum = 0;
        int assessmentCount = 0;

        for (Assessment assessment : assessments) {
            if (assessment.getEnrollment().getStudent().equals(student)) {
                assessmentCount++;
                totalObtained = totalObtained + assessment.getObtainedScore();
                totalMaximum = totalMaximum + assessment.getMaximumScore();

                double percentage = assessment.getObtainedScore() * 100 / assessment.getMaximumScore();

                System.out.println(assessmentCount + ". " + assessment.getAssessmentType()
                        + " in " + assessment.getEnrollment().getCourse().getCourseTitle()
                        + ": " + assessment.getObtainedScore() + "/" + assessment.getMaximumScore()
                        + " (" + String.format("%.1f", percentage) + "%)");
            }
        }

        if (assessmentCount == 0) {
            System.out.println("No assessments recorded for this student.");
        } else {
            System.out.println("Overall: " + totalObtained + "/" + totalMaximum
                    + " (" + String.format("%.1f", totalObtained * 100 / totalMaximum) + "%)");
        }

        readString("\nPress ENTER to return to the menu...");
    }

    public static void showPaymentHistory() throws RecordNotFoundException {

        System.out.println("\n========== STUDENT PAYMENT HISTORY ==========");

        Student student = pickStudent();

        if (student == null) {
            System.out.println("Cancelled.");
            return;
        }

        BigDecimal total = BigDecimal.ZERO;
        int count = 0;

        for (FeePayment payment : payments) {
            if (payment.getStudent().equals(student)) {
                count++;
                total = total.add(payment.getAmount());
                payment.printDetails();
                System.out.println("-------------------------");
            }
        }

        if (count == 0) {
            System.out.println("No payment records found for " + student.getDisplayName() + ".");
        }

        System.out.println("Payments made: " + count);
        System.out.println("Total paid: " + total);

        readString("\nPress ENTER to return to the menu...");
    }

    public static void showAvailableBooks() {

        System.out.println("\n========== AVAILABLE LIBRARY BOOKS ==========");

        int count = 0;

        for (LibraryBook book : books) {
            if (book.getAvailableCopies() > 0) {
                count++;
                book.printDetails();
                System.out.println("-------------------------");
            }
        }

        if (count == 0) {
            System.out.println("No book has an available copy right now.");
        }

        readString("\nPress ENTER to return to the menu...");
    }

    public static void demonstratePolymorphism() {

        System.out.println("\n========== POLYMORPHISM DEMONSTRATION ==========");

        ArrayList<Person> people = getAllPeople();

        System.out.println("\n---------- PEOPLE (Person references) ----------");

        if (people.isEmpty()) {
            System.out.println("No people registered.");
        }

        for (Person person : people) {

            System.out.println("\nName: " + person.getDisplayName());
            System.out.println("ID: " + person.getId());
            System.out.println("Role: " + person.getRoleDescription());
            System.out.println("Active: " + person.isActive());

            if (person instanceof AcademicMember) {
                AcademicMember member = (AcademicMember) person;
                System.out.println("Academic category: " + member.getAcademicCategory());
                System.out.println("Department: " + member.getDepartment().getDepartmentName());
            }

            System.out.println("Summary: " + person.getRegistrationSummary());
        }

        System.out.println("\n---------- ALL RECORDS (Identifiable references) ----------");

        ArrayList<Identifiable> records = getAllRecords();

        if (records.isEmpty()) {
            System.out.println("No records registered.");
        }

        for (Identifiable record : records) {
            System.out.println("\nID: " + record.getId());
            System.out.println("Name: " + record.getDisplayName());
            System.out.println("Active: " + record.isActive());

            if (record instanceof Registrable) {
                Registrable registrable = (Registrable) record;
                System.out.println("Summary: " + registrable.getRegistrationSummary());
            }
        }

        readString("\nPress ENTER to return to the menu...");
    }
}