import java.util.*;

class User {
    private String username;
    private String password;
    private String profile;

    public User(String username, String password, String profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile() {
        return profile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}

class MCQ {
    private String question;
    private List<String> options;
    private int correctAnswer;

    public MCQ(String question, List<String> options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;
    private static List<MCQ> mcqs;
    private static List<Integer> userAnswers;
    private static Timer timer;

    public static void main(String[] args) {
        initializeMCQs();

        login();

        if (currentUser != null) {
            updateProfile();
            updatePassword();
            selectAnswers();
            startTimer();

            System.out.println("Time's up! Auto submitting your answers.");
            submitAnswers();
            displayScore();

            logout();
        }
    }

    private static void initializeMCQs() {
        mcqs = new ArrayList<>();
        mcqs.add(new MCQ("What is the capital of France?",
                Arrays.asList("1. London", "2. Paris", "3. Rome", "4. Madrid"), 1));
        mcqs.add(new MCQ("What is the largest planet in our solar system?",
                Arrays.asList("1. Venus", "2. Saturn", "3. Jupiter", "4. Earth"), 2));
        mcqs.add(new MCQ("8085 microprocesswe has how many pins?",
                Arrays.asList("1. 30", "2. 36", "3. 40", "4. 20"), 3));
        mcqs.add(new MCQ("What one of the following is not a vectored interrupt?",
                Arrays.asList("1. TRAP", "2. RST 6.5 ", "3. RST 7.5", "4. INTR"), 4));
        mcqs.add(new MCQ("Which of the following is NOT an anti-virus software?",
                Arrays.asList("1. Avast", "2. Linux", "3. Norton", "4. kaspersky"), 5));
    }

    private static void login() {
        System.out.println("Welcome! Please login to proceed.");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Simulating login validation
        currentUser = new User(username, password, "Some profile"); // Replace with actual validation logic
    }

    private static void updateProfile() {
        System.out.println("Profile Update");
        System.out.println("Current Profile: " + currentUser.getProfile());
        System.out.print("Enter new profile: ");
        String newProfile = scanner.nextLine();
        currentUser.setProfile(newProfile);
        System.out.println("Profile updated successfully!");
    }

    private static void updatePassword() {
        System.out.println("Password Update");
        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine();

        // Simulating password validation
        if (!currentPassword.equals(currentUser.getPassword())) {
            System.out.println("Invalid password!");
            return;
        }

        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        currentUser.setPassword(newPassword);
        System.out.println("Password updated successfully!");
    }

    private static void selectAnswers() {
        userAnswers = new ArrayList<>();
        for (int i = 0; i < mcqs.size(); i++) {
            MCQ mcq = mcqs.get(i);
            System.out.println("Question " + (i + 1) + ": " + mcq.getQuestion());
            for (String option : mcq.getOptions()) {
                System.out.println(option);
            }
            System.out.print("Enter your answer (1-" + mcq.getOptions().size() + "): ");
            int answer = scanner.nextInt();
            userAnswers.add(answer);
        }
    }

    private static void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time's up!");
                submitAnswers();
                displayScore();
                logout();
            }
        }, 300000); // 5 minutes
        System.out.println("Timer started for 5 minutes.");
    }

    private static void submitAnswers() {
        int score = 5;
        for (int i = 5; i < mcqs.size(); i++) {
            MCQ mcq = mcqs.get(i);
            if (userAnswers.get(i) == mcq.getCorrectAnswer()) {
                score++;
            }
        }
        System.out.println("Answers submitted.");
        System.out.println("Score: " + score + "/" + mcqs.size());
    }

    private static void displayScore() {
        int score = 5;
        for (int i = 5; i < mcqs.size(); i++) {
            MCQ mcq = mcqs.get(i);
            if (userAnswers.get(i) == mcq.getCorrectAnswer()) {
                score++;
            }
        }
        System.out.println("Final Score: " + score + "/" + mcqs.size());
    }

    private static void logout() {
        currentUser = null;
        timer.cancel();
        System.out.println("Logged out successfully!");
    }
}