import java.util.*;

import java.util.*;

public class MiniDatabase {
    private final Map<String, List<Map<String, Object>>> tables = new HashMap<>();
    private final Map<String, List<String>> tableColumns = new HashMap<>(); // Хранение колонок таблиц
    private int uniqueId = 1;

    // Метод для создания таблицы
    public void createTable(String tableName, List<String> columns) {
        if (tables.containsKey(tableName)) {
            throw new IllegalArgumentException("Table " + tableName + " already exists!");
        }
        tables.put(tableName, new ArrayList<>());
        tableColumns.put(tableName, columns); // Сохранение списка колонок
        System.out.println("Table " + tableName + " created successfully!");
    }

    // Метод для генерации уникального ID
    private int generateUniqueId() {
        return uniqueId++;
    }

    // Метод для добавления строки с использованием Scanner
    public void insertWithScanner(String tableName) {
        if (!tables.containsKey(tableName)) {
            throw new IllegalArgumentException("Table " + tableName + " does not exist!");
        }

        Scanner scanner = new Scanner(System.in);
        Map<String, Object> row = new HashMap<>();

        System.out.println("Adding a new row to table: " + tableName);
        row.put("id", generateUniqueId()); // Генерация уникального ID

        // Получение списка колонок для таблицы
        List<String> columns = tableColumns.get(tableName);
        for (String column : columns) {
            if (!column.equals("id")) { // Пропускаем id
                System.out.print("Enter value for column '" + column + "': ");
                String value = scanner.nextLine();
                row.put(column, value);
            }
        }

        tables.get(tableName).add(row);
        System.out.println("Row inserted: " + row);
    }

    // Метод для чтения данных из таблицы
    public List<Map<String, Object>> select(String tableName) {
        if (!tables.containsKey(tableName)) {
            throw new IllegalArgumentException("Table " + tableName + " does not exist!");
        }
        return tables.get(tableName);
    }

    // Метод для обновления строки в таблице
    public void update(String tableName, String column, Object value, Map<String, Object> newData) {
        if (!tables.containsKey(tableName)) {
            throw new IllegalArgumentException("Table " + tableName + " does not exist!");
        }
        List<Map<String, Object>> table = tables.get(tableName);
        for (Map<String, Object> row : table) {
            if (row.containsKey(column) && row.get(column).equals(value)) {
                row.putAll(newData);
                System.out.println("Row updated in " + tableName);
            }
        }
    }

    // Метод для удаления строки из таблицы
    public void delete(String tableName, String column, Object value) {
        if (!tables.containsKey(tableName)) {
            throw new IllegalArgumentException("Table " + tableName + " does not exist!");
        }
        List<Map<String, Object>> table = tables.get(tableName);
        table.removeIf(row -> row.containsKey(column) && row.get(column).equals(value));
        System.out.println("Rows deleted from " + tableName);
    }

    // Печать всех данных таблицы
    public void printTable(String tableName) {
        if (!tables.containsKey(tableName)) {
            throw new IllegalArgumentException("Table " + tableName + " does not exist!");
        }
        List<Map<String, Object>> table = tables.get(tableName);
        System.out.println("Table: " + tableName);
        for (Map<String, Object> row : table) {
            System.out.println(row);
        }
    }

    public static void main(String[] args) {
        MiniDatabase db = new MiniDatabase();

        // Создание таблицы "users"
        db.createTable("users", Arrays.asList("id", "name", "age"));

        // Добавление данных с помощью Scanner
        db.insertWithScanner("users");
        db.insertWithScanner("users");

        // Печать таблицы
        db.printTable("users");

        // Удаление записи
        db.delete("users", "id", 1);
        System.out.println("\nAfter delete:");
        db.printTable("users");
    }
}

