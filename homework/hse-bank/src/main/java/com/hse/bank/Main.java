package com.hse.bank;

import com.hse.bank.commands.Command;
import com.hse.bank.commands.decorator.TimedCommandDecorator;
import com.hse.bank.commands.impl.*;
import com.hse.bank.di.DIContainer;
import com.hse.bank.domain.BankAccount;
import com.hse.bank.domain.Category;
import com.hse.bank.domain.Operation;
import com.hse.bank.facade.*;
import com.hse.bank.factories.DomainFactory;
import com.hse.bank.importexport.visitor.ExportVisitor;
import com.hse.bank.importexport.visitor.impl.CsvExportVisitor;
import com.hse.bank.importexport.visitor.impl.JsonExportVisitor;
import com.hse.bank.repository.Repository;
import com.hse.bank.repository.impl.InMemoryRepository;
import com.hse.bank.repository.proxy.RepositoryProxy;
import com.hse.bank.utils.ConsoleReader;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    private static BankAccountFacade accountFacade;
    private static CategoryFacade categoryFacade;
    private static OperationFacade operationFacade;
    private static AnalyticsFacade analyticsFacade;

    public static void main(String[] args) {
        initializeDependencies();
        showMainMenu();
    }

    private static void initializeDependencies() {
        DIContainer container = configureDIContainer();
        accountFacade = container.resolve(BankAccountFacade.class);
        categoryFacade = container.resolve(CategoryFacade.class);
        operationFacade = container.resolve(OperationFacade.class);
        analyticsFacade = container.resolve(AnalyticsFacade.class);
    }

    private static DIContainer configureDIContainer() {
        DIContainer container = new DIContainer();

        Repository<BankAccount> accountRepo = new RepositoryProxy<>(
                new InMemoryRepository<>(), new InMemoryRepository<>()
        );
        Repository<Category> categoryRepo = new RepositoryProxy<>(
                new InMemoryRepository<>(), new InMemoryRepository<>()
        );
        Repository<Operation> operationRepo = new RepositoryProxy<>(
                new InMemoryRepository<>(), new InMemoryRepository<>()
        );

        DomainFactory factory = new DomainFactory();

        BankAccountFacade bankAccountFacade = new BankAccountFacade(accountRepo, factory);
        CategoryFacade categoryFacade = new CategoryFacade(categoryRepo, factory);
        OperationFacade operationFacade = new OperationFacade(operationRepo, accountRepo, factory);
        AnalyticsFacade analyticsFacade = new AnalyticsFacade(operationRepo, categoryRepo, accountRepo);

        container.registerSingleton(BankAccountFacade.class, bankAccountFacade);
        container.registerSingleton(CategoryFacade.class, categoryFacade);
        container.registerSingleton(OperationFacade.class, operationFacade);
        container.registerSingleton(AnalyticsFacade.class, analyticsFacade);

        return container;
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== ВШЭ-Банк: Учет финансов ===");
            System.out.println("1. Управление счетами");
            System.out.println("2. Управление категориями");
            System.out.println("3. Управление операциями");
            System.out.println("4. Аналитика и отчеты");
            System.out.println("5. Экспорт данных");
            System.out.println("6. Пересчет балансов");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = ConsoleReader.readInt();

            switch (choice) {
                case 1:
                    showAccountMenu();
                    break;
                case 2:
                    showCategoryMenu();
                    break;
                case 3:
                    showOperationMenu();
                    break;
                case 4:
                    showAnalyticsMenu();
                    break;
                case 5:
                    showExportMenu();
                    break;
                case 6:
                    recalculateBalances();
                    break;
                case 0:
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private static void showAccountMenu() {
        while (true) {
            System.out.println("\n=== Управление счетами ===");
            System.out.println("1. Создать счет");
            System.out.println("2. Просмотреть все счета");
            System.out.println("3. Найти счет по ID");
            System.out.println("4. Обновить название счета");
            System.out.println("5. Удалить счет");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = ConsoleReader.readInt();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    listAccounts();
                    break;
                case 3:
                    findAccount();
                    break;
                case 4:
                    updateAccount();
                    break;
                case 5:
                    deleteAccount();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Введите название счета: ");
        String name = ConsoleReader.readLine();
        System.out.print("Введите начальный баланс: ");
        double balance = ConsoleReader.readDouble();

        Command command = new CreateAccountCommand(accountFacade, name, balance);
        Command timedCommand = new TimedCommandDecorator(command);
        timedCommand.execute();
    }

    private static void listAccounts() {
        Command command = new ListAccountsCommand(accountFacade);
        Command timedCommand = new TimedCommandDecorator(command);
        timedCommand.execute();
    }

    private static void findAccount() {
        System.out.print("Введите ID счета: ");
        String id = ConsoleReader.readLine();

        Command command = new FindAccountCommand(accountFacade, id);
        Command timedCommand = new TimedCommandDecorator(command);
        timedCommand.execute();
    }

    private static void updateAccount() {
        System.out.print("Введите ID счета для обновления: ");
        String id = ConsoleReader.readLine();
        System.out.print("Введите новое название: ");
        String newName = ConsoleReader.readLine();

        Command command = new UpdateAccountCommand(accountFacade, id, newName);
        Command timedCommand = new TimedCommandDecorator(command);
        timedCommand.execute();
    }

    private static void deleteAccount() {
        System.out.print("Введите ID счета для удаления: ");
        String id = ConsoleReader.readLine();

        Command command = new DeleteAccountCommand(accountFacade, id);
        Command timedCommand = new TimedCommandDecorator(command);
        timedCommand.execute();
    }

    private static void showCategoryMenu() {
        while (true) {
            System.out.println("\n=== Управление категориями ===");
            System.out.println("1. Создать категорию дохода");
            System.out.println("2. Создать категорию расхода");
            System.out.println("3. Просмотреть все категории");
            System.out.println("4. Найти категорию по ID");
            System.out.println("5. Удалить категорию");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = ConsoleReader.readInt();

            switch (choice) {
                case 1:
                    createCategory(Category.CategoryType.INCOME);
                    break;
                case 2:
                    createCategory(Category.CategoryType.EXPENSE);
                    break;
                case 3:
                    listCategories();
                    break;
                case 4:
                    findCategory();
                    break;
                case 5:
                    deleteCategory();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private static void createCategory(Category.CategoryType type) {
        System.out.print("Введите название категории: ");
        String name = ConsoleReader.readLine();

        Command command = new CreateCategoryCommand(categoryFacade, type, name);
        Command timedCommand = new TimedCommandDecorator(command);
        timedCommand.execute();
    }

    private static void listCategories() {
        List<Category> categories = categoryFacade.getAllCategories();
        System.out.println("\n=== Все категории ===");
        for (Category category : categories) {
            System.out.printf("ID: %s, Тип: %s, Название: %s%n",
                    category.getId(), category.getType(), category.getName());
        }
    }

    private static void findCategory() {
        System.out.print("Введите ID категории: ");
        String id = ConsoleReader.readLine();

        categoryFacade.getCategory(id).ifPresentOrElse(
                category -> System.out.printf("Найдена категория: %s (%s)%n",
                        category.getName(), category.getType()),
                () -> System.out.println("Категория не найдена")
        );
    }

    private static void deleteCategory() {
        System.out.print("Введите ID категории для удаления: ");
        String id = ConsoleReader.readLine();

        if (categoryFacade.deleteCategory(id)) {
            System.out.println("Категория удалена");
        } else {
            System.out.println("Категория не найдена");
        }
    }

    private static void showOperationMenu() {
        while (true) {
            System.out.println("\n=== Управление операциями ===");
            System.out.println("1. Создать операцию дохода");
            System.out.println("2. Создать операцию расхода");
            System.out.println("3. Просмотреть все операции");
            System.out.println("4. Просмотреть операции по счету");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = ConsoleReader.readInt();

            switch (choice) {
                case 1:
                    createOperation(Operation.OperationType.INCOME);
                    break;
                case 2:
                    createOperation(Operation.OperationType.EXPENSE);
                    break;
                case 3:
                    listAllOperations();
                    break;
                case 4:
                    listOperationsByAccount();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private static void createOperation(Operation.OperationType type) {
        System.out.print("Введите ID счета: ");
        String accountId = ConsoleReader.readLine();
        System.out.print("Введите сумму: ");
        double amount = ConsoleReader.readDouble();
        System.out.print("Введите ID категории: ");
        String categoryId = ConsoleReader.readLine();
        System.out.print("Введите описание: ");
        String description = ConsoleReader.readLine();

        Command command = new CreateOperationCommand(operationFacade, type, accountId, amount, categoryId, description);
        Command timedCommand = new TimedCommandDecorator(command);
        timedCommand.execute();
    }

    private static void listAllOperations() {
        List<Operation> operations = operationFacade.getAllOperations();
        System.out.println("\n=== Все операции ===");
        for (Operation operation : operations) {
            System.out.printf("ID: %s, Тип: %s, Сумма: %.2f, Дата: %s%n",
                    operation.getId(), operation.getType(), operation.getAmount(), operation.getDate());
        }
    }

    private static void listOperationsByAccount() {
        System.out.print("Введите ID счета: ");
        String accountId = ConsoleReader.readLine();

        List<Operation> operations = operationFacade.getOperationsByAccount(accountId);
        System.out.println("\n=== Операции по счету ===");
        for (Operation operation : operations) {
            System.out.printf("Тип: %s, Сумма: %.2f, Дата: %s, Описание: %s%n",
                    operation.getType(), operation.getAmount(), operation.getDate(), operation.getDescription());
        }
    }

    private static void showAnalyticsMenu() {
        while (true) {
            System.out.println("\n=== Аналитика и отчеты ===");
            System.out.println("1. Баланс за период");
            System.out.println("2. Группировка по категориям");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = ConsoleReader.readInt();

            switch (choice) {
                case 1:
                    showBalanceAnalytics();
                    break;
                case 2:
                    showCategoryAnalytics();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private static void showBalanceAnalytics() {
        System.out.println("Анализ баланса за последние 30 дней:");
        double balanceDiff = analyticsFacade.calculateBalanceDifference(
                LocalDateTime.now().minusDays(30), LocalDateTime.now()
        );
        System.out.printf("Разница доходов и расходов: %.2f%n", balanceDiff);
    }

    private static void showCategoryAnalytics() {
        System.out.println("Группировка операций по категориям за последние 30 дней:");
        var categoryGroups = analyticsFacade.groupOperationsByCategory(
                LocalDateTime.now().minusDays(30), LocalDateTime.now()
        );
        var categoryNames = analyticsFacade.getCategoryNames();

        for (var entry : categoryGroups.entrySet()) {
            String categoryName = categoryNames.get(entry.getKey());
            System.out.printf("Категория: %s, Сумма: %.2f%n", categoryName, entry.getValue());
        }
    }

    private static void recalculateBalances() {
        Command command = new RecalculateBalancesCommand(analyticsFacade);
        Command timedCommand = new TimedCommandDecorator(command);
        timedCommand.execute();
    }

    private static void showExportMenu() {
        while (true) {
            System.out.println("\n=== Экспорт данных ===");
            System.out.println("1. Экспорт в CSV");
            System.out.println("2. Экспорт в JSON");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            int choice = ConsoleReader.readInt();

            switch (choice) {
                case 1:
                    exportToCsv();
                    break;
                case 2:
                    exportToJson();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private static void exportToCsv() {
        ExportVisitor exporter = new CsvExportVisitor();
        exportData(exporter, "CSV");
    }

    private static void exportToJson() {
        ExportVisitor exporter = new JsonExportVisitor();
        exportData(exporter, "JSON");
    }

    private static void exportData(ExportVisitor exporter, String format) {
        accountFacade.getAllAccounts().forEach(exporter::visit);
        categoryFacade.getAllCategories().forEach(exporter::visit);
        operationFacade.getAllOperations().forEach(exporter::visit);

        System.out.println("\n=== Экспорт в " + format + " ===");
        System.out.println(exporter.getResult());
    }
}