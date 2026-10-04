import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PersonalExpenseTracker {

    static ArrayList<Expense> expenses = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n================================");
            System.out.println("     PERSONAL EXPENSE TRACKER");
            System.out.println("================================");
            System.out.println("1. Add Expense");
            System.out.println("2. Categorize Expense");
            System.out.println("3. Delete Expense");
            System.out.println("4. Display All Expenses");
            System.out.println("5. Calculate Total Expense");
            System.out.println("6. Find Highest Expense");
            System.out.println("7. Category-wise Expenses");
            System.out.println("8. Exit");
            System.out.println("================================");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addExpense();
                    break;

                case 2:
                    categorizeExpense();
                    break;

                case 3:
                    deleteExpense();
                    break;

                case 4:
                    displayExpenses();
                    break;

                case 5:
                    calculateTotal();
                    break;

                case 6:
                    findHighestExpense();
                    break;

                case 7:
                    categoryWiseExpenses();
                    break;

                case 8:
                    System.out.println("Thank you for using Personal Expense Tracker!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Add Expense
    public static void addExpense() {

        System.out.print("Enter expense ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter description: ");
        String description = sc.nextLine();

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();

        System.out.println("\nSelect Category:");
        System.out.println("1. FOOD");
        System.out.println("2. TRAVEL");
        System.out.println("3. SHOPPING");
        System.out.println("4. BILLS");
        System.out.println("5. ENTERTAINMENT");
        System.out.println("6. HEALTH");
        System.out.println("7. EDUCATION");
        System.out.println("8. OTHER");

        System.out.print("Enter category: ");
        int categoryChoice = sc.nextInt();

        Category category;

        switch (categoryChoice) {
            case 1:
                category = Category.FOOD;
                break;
            case 2:
                category = Category.TRAVEL;
                break;
            case 3:
                category = Category.SHOPPING;
                break;
            case 4:
                category = Category.BILLS;
                break;
            case 5:
                category = Category.ENTERTAINMENT;
                break;
            case 6:
                category = Category.HEALTH;
                break;
            case 7:
                category = Category.EDUCATION;
                break;
            case 8:
                category = Category.OTHER;
                break;
            default:
                System.out.println("Invalid category!");
                return;
        }

        LocalDate date = LocalDate.now();

        Expense expense = new Expense(
                id, description, amount, category, date
        );

        expenses.add(expense);

        System.out.println("\nExpense added successfully!");
    }

    // Categorize Expense
    public static void categorizeExpense() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }

        System.out.print("Enter expense ID: ");
        int id = sc.nextInt();

        for (Expense expense : expenses) {

            if (expense.getId() == id) {
                System.out.println("Expense Category: "
                        + expense.getCategory());
                return;
            }
        }

        System.out.println("Expense not found.");
    }

    // Delete Expense
    public static void deleteExpense() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }

        System.out.print("Enter expense ID to delete: ");
        int id = sc.nextInt();

        boolean removed = expenses.removeIf(
                expense -> expense.getId() == id
        );

        if (removed) {
            System.out.println("Expense deleted successfully!");
        } else {
            System.out.println("Expense not found.");
        }
    }

    // Display All Expenses
    public static void displayExpenses() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }

        System.out.println("\n========== ALL EXPENSES ==========");

        for (Expense expense : expenses) {
            expense.displayExpense();
        }
    }

    // Calculate Total Expense
    public static void calculateTotal() {

        double total = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        System.out.println("\nTotal Expense: ₹" + total);
    }

    // Find Highest Expense
    public static void findHighestExpense() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }

        Expense highest = expenses.stream()
                .max((e1, e2) ->
                        Double.compare(
                                e1.getAmount(),
                                e2.getAmount()
                        ))
                .orElse(null);

        System.out.println("\n========== HIGHEST EXPENSE ==========");

        if (highest != null) {
            highest.displayExpense();
        }
    }

    // Category-wise Expenses
    public static void categoryWiseExpenses() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }

        HashMap<Category, Double> categoryTotals = new HashMap<>();

        for (Expense expense : expenses) {

            categoryTotals.merge(
                    expense.getCategory(),
                    expense.getAmount(),
                    Double::sum
            );
        }

        System.out.println("\n======= CATEGORY-WISE EXPENSES =======");

        for (HashMap.Entry<Category, Double> entry
                : categoryTotals.entrySet()) {

            System.out.println(
                    entry.getKey() + " : ₹" + entry.getValue()
            );
        }
    }
}